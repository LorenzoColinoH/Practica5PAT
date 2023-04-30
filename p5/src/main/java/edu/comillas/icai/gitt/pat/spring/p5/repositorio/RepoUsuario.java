package edu.comillas.icai.gitt.pat.spring.p5.repositorio;

import edu.comillas.icai.gitt.pat.spring.p5.entidad.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface RepoUsuario extends CrudRepository<Usuario, Long> {
    Usuario findByCredenciales(String credenciales);
}
