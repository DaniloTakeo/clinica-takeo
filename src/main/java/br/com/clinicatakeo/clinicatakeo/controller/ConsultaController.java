package br.com.clinicatakeo.clinicatakeo.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.clinicatakeo.clinicatakeo.controller.dto.ConsultaDto;
import br.com.clinicatakeo.clinicatakeo.controller.form.ConsultaForm;
import br.com.clinicatakeo.clinicatakeo.controller.form.DiagnosticoConsultaForm;
import br.com.clinicatakeo.clinicatakeo.controller.form.ReagendarHorarioConsultaForm;
import br.com.clinicatakeo.clinicatakeo.model.Consulta;
import br.com.clinicatakeo.clinicatakeo.model.StatusConsulta;
import br.com.clinicatakeo.clinicatakeo.repository.ConsultaRepository;
import br.com.clinicatakeo.clinicatakeo.repository.MedicoRepository;
import br.com.clinicatakeo.clinicatakeo.repository.PacienteRepository;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@GetMapping("/findAll")
	public Page<ConsultaDto> consultarTodas(@PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao) {
		Page<Consulta> consultas = consultaRepository.findAll(paginacao);
		
		return ConsultaDto.converter(consultas);
	}
	
	@GetMapping("/findConsultasAgendadas")
	public ResponseEntity<Page<ConsultaDto>> consultarConsultasAgendadas(@PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao) {
		Page<Consulta> consultas = consultaRepository.findByStatus(StatusConsulta.AGENDADA, paginacao);
		
		return ResponseEntity.ok(ConsultaDto.converter(consultas));
	}
	
	@Transactional
	@PostMapping
	public ResponseEntity<ConsultaDto> cadastrar(@RequestBody @Valid ConsultaForm form, UriComponentsBuilder uriBuilder) {
		Consulta consulta = form.convereter(pacienteRepository, medicoRepository);
		consultaRepository.save(consulta);
		
		URI uri = uriBuilder.path("/consultas/{id}").buildAndExpand(consulta.getId()).toUri();
		return ResponseEntity.created(uri).body(new ConsultaDto(consulta));
	}
	
	
	@Transactional
	@PatchMapping("/{id}/realizarDiagnostico")
	public ResponseEntity<ConsultaDto> realizarDiagnosticoDaConsulta(@PathVariable Long id, @RequestBody @Valid DiagnosticoConsultaForm form) {
		Optional<Consulta> optional = consultaRepository.findById(id);
		
		if(optional.isPresent()) {
			Consulta consulta = form.atualizar(id, consultaRepository);
			
			return ResponseEntity.ok(new ConsultaDto(consulta));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@PatchMapping("/{id}/cancelarConsulta")
	public ResponseEntity<ConsultaDto> cancelarConsulta(@PathVariable Long id) {
		Optional<Consulta> optional = consultaRepository.findById(id);
		
		if(optional.isPresent()) {
			Consulta consulta = consultaRepository.getById(id);
			
			consulta.setStatus(StatusConsulta.CANCELADA);
			return ResponseEntity.ok(new ConsultaDto(consulta));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@PatchMapping("/{id}/reagendarHorario")
	public ResponseEntity<ConsultaDto> reagendarHorario(@PathVariable Long id, @RequestBody @Valid ReagendarHorarioConsultaForm form) {
		Optional<Consulta> optional = consultaRepository.findById(id);
		
		if(optional.isPresent()) {
			Consulta consulta = form.atualizar(id, consultaRepository);
			
			return ResponseEntity.ok(new ConsultaDto(consulta));
		}
		
		return ResponseEntity.notFound().build();
	}
}
