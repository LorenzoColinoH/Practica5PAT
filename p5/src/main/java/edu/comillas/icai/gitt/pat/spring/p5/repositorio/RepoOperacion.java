package edu.comillas.icai.gitt.pat.spring.p5.repositorio;

import edu.comillas.icai.gitt.pat.spring.p5.entidad.Operacion;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RepoOperacion extends CrudRepository<Operacion, Long> {
    public List<Operacion> findAllByContadorId(Long contadorId);
}
