package br.com.clinicatakeo.clinicatakeo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.clinicatakeo.clinicatakeo.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

	Optional<Page<Medico>> findByEspecialidade(String especialidade, Pageable paginacao);

}
