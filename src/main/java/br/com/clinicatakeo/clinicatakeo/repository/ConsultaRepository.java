package br.com.clinicatakeo.clinicatakeo.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.clinicatakeo.clinicatakeo.model.Consulta;
import br.com.clinicatakeo.clinicatakeo.model.Medico;
import br.com.clinicatakeo.clinicatakeo.model.Paciente;
import br.com.clinicatakeo.clinicatakeo.model.StatusConsulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	Page<Consulta> findByStatus(StatusConsulta agendada, Pageable paginacao);

	Optional<Page<Consulta>> findByMedico(Medico medico, Pageable paginacao);

	Optional<Page<Consulta>> findByMedicoAndDataEHora(Medico medico, LocalDateTime data, Pageable paginacao);

	Optional<Page<Consulta>> findByPaciente(Optional<Paciente> paciente, Pageable paginacao);

}
