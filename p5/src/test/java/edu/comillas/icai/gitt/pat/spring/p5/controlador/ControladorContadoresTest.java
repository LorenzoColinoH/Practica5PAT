package edu.comillas.icai.gitt.pat.spring.p5.controlador;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.service.invoker.UrlArgumentResolver;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
class ControladorContadoresTest {
    private final TestRestTemplate restTemplate = new TestRestTemplate("admin@e.m", "admin");

    @Test
    public void contadorExistenteTest() {
        String contador = "{\"nombre\":\"visitas\",\"valor\":0}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.exchange(
                "http://localhost:8080/contadores", HttpMethod.POST,
                new HttpEntity<>(contador, headers), String.class);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/contadores/visitas",
                HttpMethod.GET, HttpEntity.EMPTY, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(contador, response.getBody());
    }

    @Test
    public void contadorNoExistenteTest() {
        // When ...
        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:8080/contadores/no-existo",
                HttpMethod.GET, HttpEntity.EMPTY, Void.class);
        // Then ...
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


    @Test
    public void contadorConValorInvalidoTest() {
        String contador = "{\"nombre\":\"contadorTest\",\"valor\":No es un numero}";  
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/contadores",
                HttpMethod.POST, new HttpEntity<>(contador, headers), String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());  
    }


    @Test
    public void createContadorTest() {
        String contador = "{\"nombre\":\"contadorTest\",\"valor\":10000}";  
        HttpHeaders headers = new HttpHeaders(); 
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/contadores",
                HttpMethod.POST, new HttpEntity<>(contador, headers), String.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode()); 
        Assertions.assertEquals(contador, response.getBody());
    }



    @Test 
    public void incrementoTest(){
        String contador = "{\"nombre\":\"contadorTest\",\"valor\":30}";  
        String contadorYaIncrementado = "{\"nombre\":\"contadorTest\",\"valor\":60}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.exchange(
                "http://localhost:8080/contadores", HttpMethod.POST,
                new HttpEntity<>(contador, headers), String.class);
        
                ResponseEntity<String> response = restTemplate.exchange(
            "http://localhost:8080/contadores/contadorTest/incremento/30",  
            HttpMethod.PUT, HttpEntity.EMPTY, String.class);
        
            Assertions.assertEquals(contadorYaIncrementado, response.getBody());  
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void incrementoFallidoTest(){
        String contador = "{\"nombre\":\"contadorTest\",\"valor\":200}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.exchange(
            "http://localhost:8080/contadores/contadorTest", HttpMethod.POST,
            new HttpEntity<>(contador, headers),String.class);
        ResponseEntity<String> response = restTemplate.exchange(
            "http://localhost:8080/contadores/contadorTest/incremento/Algo diferente a un Long",
            HttpMethod.PUT, HttpEntity.EMPTY, String.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void borrarElemento(){
        String contador = "{\"nombre\":\"contadorTest\",\"valor\":300}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.exchange(
            "http://localhost:8080/contadores/contadorTest", HttpMethod.POST,
            new HttpEntity<>(contador, headers),String.class);
        ResponseEntity<String> response = restTemplate.exchange(
            "http://localhost:8080/contadores/contadorTest",  
            HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
        Assertions.assertEquals(null, response.getBody());
    }

}