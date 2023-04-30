package edu.comillas.icai.gitt.pat.spring.p5.servicio;

import edu.comillas.icai.gitt.pat.spring.p5.entidad.Contador;
import edu.comillas.icai.gitt.pat.spring.p5.entidad.Operacion;
import edu.comillas.icai.gitt.pat.spring.p5.entidad.Usuario;

import java.util.List;

public interface ServicioContadores {
    Usuario autentica(String credenciales);
    Contador lee(String nombre, Usuario usuario);
    Contador crea(Contador contadorNuevo, Usuario usuario);
    Contador incrementa(Contador contador, Long incremento, Usuario usuario);
    void borra(Contador contador, Usuario usuario);
    List<Operacion> operaciones(Contador contador);
}
