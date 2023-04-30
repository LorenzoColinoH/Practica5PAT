package edu.comillas.icai.gitt.pat.spring.p5.controlador;

import edu.comillas.icai.gitt.pat.spring.p5.entidad.Contador;
import edu.comillas.icai.gitt.pat.spring.p5.entidad.Operacion;
import edu.comillas.icai.gitt.pat.spring.p5.entidad.Usuario;
import edu.comillas.icai.gitt.pat.spring.p5.repositorio.RepoContador;
import edu.comillas.icai.gitt.pat.spring.p5.repositorio.RepoUsuario;
import edu.comillas.icai.gitt.pat.spring.p5.servicio.ServicioContadores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ControladorContadores {
    @Autowired
    ServicioContadores servicioContadores;
    @Autowired
    RepoUsuario repoUsuarios;
    @Autowired
    RepoContador repoContador;

    // Devuelve todos los contadores
    @GetMapping("/contadores") 
    @ResponseStatus(HttpStatus.OK) 
    public Iterable<Contador> getContadores(){
        return repoContador.findAll();
    }

    @PostMapping("/contadores") 
    @ResponseStatus(HttpStatus.CREATED)
    public Contador crea(@RequestBody Contador contadorNuevo ,@RequestHeader("Authorization") String credenciales){
    
        //TODO Ejercicio 6 de https://apicai.github.io/web-ejercicios-pat-2023/tema8/persistencia/spring-data-servicios.html

        Usuario usuario = servicioContadores.autentica(credenciales);


        Contador c = servicioContadores.crea(contadorNuevo,usuario);
        return c;
    }

    @GetMapping("/contadores/{nombre}")  
    public Contador lee(@PathVariable String nombre,@RequestHeader("Authorization") String credenciales) {
        //TODO Ejercicio 6 de https://apicai.github.io/web-ejercicios-pat-2023/tema8/persistencia/spring-data-servicios.html
        Usuario usuario = servicioContadores.autentica(credenciales);
        return servicioContadores.lee(nombre, usuario);                            
    }

    @PutMapping("/contadores/{nombre}/incremento/{incremento}")
    public ResponseEntity<Contador> incrementa(@PathVariable String nombre,
                               @PathVariable Long incremento,
                               @RequestHeader("Authorization") String credenciales) {
        
        return ResponseEntity.ok().body(servicioContadores.incrementa(repoContador.findByNombre(nombre),incremento,servicioContadores.autentica(credenciales)));
    }
    @GetMapping(value="/usuario/{credenciales}")
    public Usuario getUsuarioByCredenciales(@RequestHeader("Authorization") String credenciales) {
        Usuario usuario = servicioContadores.autentica(credenciales);
        return usuario; 
    }

    @DeleteMapping("/contadores/{nombre}")
    public ResponseEntity<Object> borra(@PathVariable String nombre,
                      @RequestHeader("Authorization") String credenciales) {
        //TODO Ejercicio 6 de https://apicai.github.io/web-ejercicios-pat-2023/tema8/persistencia/spring-data-servicios.html
        Usuario usuario = servicioContadores.autentica(credenciales);
        servicioContadores.borra(servicioContadores.lee(nombre,usuario),usuario);
        return ResponseEntity.ok().body(null);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String manejarExcepcionNombreDuplicado(DataIntegrityViolationException ex){
        return "El nombre del contador coincide con otro ya registrado en nuestra base de datos";
    }

    @GetMapping("/contadores/{nombre}/operaciones")
    public List<Operacion> operaciones(@PathVariable String nombre,
                                       @RequestHeader("Authorization") String credenciales) {
        //TODO Ejercicio 1 de https://apicai.github.io/web-ejercicios-pat-2023/tema8/persistencia/spring-data-relaciones.html
                                        
        return servicioContadores.operaciones(servicioContadores.lee(nombre, servicioContadores.autentica(credenciales)));
    }



}

