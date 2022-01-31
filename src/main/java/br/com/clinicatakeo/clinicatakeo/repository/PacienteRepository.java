package br.com.clinicatakeo.clinicatakeo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.clinicatakeo.clinicatakeo.model.Paciente;
import br.com.clinicatakeo.clinicatakeo.model.PlanoDeSaude;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
	
	Optional<Page<Paciente>> findByPlanoDeSaude(PlanoDeSaude planoDeSaude, Pageable paginacao);

}
