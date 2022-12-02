package br.com.gregoriohd.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gregoriohd.model.Candidato;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, String> {

}
