package br.com.clinicatakeo.clinicatakeo.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.clinicatakeo.clinicatakeo.controller.dto.ConsultaDto;
import br.com.clinicatakeo.clinicatakeo.controller.dto.PacienteDto;
import br.com.clinicatakeo.clinicatakeo.controller.form.AtualizacaoPacienteForm;
import br.com.clinicatakeo.clinicatakeo.controller.form.PacienteForm;
import br.com.clinicatakeo.clinicatakeo.model.Consulta;
import br.com.clinicatakeo.clinicatakeo.model.Paciente;
import br.com.clinicatakeo.clinicatakeo.model.PlanoDeSaude;
import br.com.clinicatakeo.clinicatakeo.repository.ConsultaRepository;
import br.com.clinicatakeo.clinicatakeo.repository.PacienteRepository;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	@GetMapping("/findAll")
	@Cacheable(value = "listaDePacientes")
	public Page<PacienteDto> consultarTodos(@PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao) {
		
		Page<Paciente> pacientes = pacienteRepository.findAll(paginacao);
		
		return PacienteDto.converter(pacientes);
	}
	
	@GetMapping("/planoDeSaude={planoDeSaude}")
	@Cacheable(value = "listaDePacientesPorPlano")
	public ResponseEntity<Page<PacienteDto>> consultarPorPlano(@PathVariable String planoDeSaude,
			@PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao) {
		PlanoDeSaude plano = PlanoDeSaude.valueOf(planoDeSaude);
		Optional<Page<Paciente>> pacientes = pacienteRepository.findByPlanoDeSaude(plano, paginacao);
		
		if(pacientes.isPresent()) {
			return ResponseEntity.ok(PacienteDto.converter(pacientes.get())); 			
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}/consultarHistorico")
	public ResponseEntity<Page<ConsultaDto>> consultarHistoricoDeConsultas(@PathVariable Long id, 
			@PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao) {
		Optional<Paciente> paciente = pacienteRepository.findById(id);
		
		if(paciente.isPresent()) {
			Optional<Page<Consulta>> consultas = consultaRepository.findByPaciente(paciente, paginacao);
			
			if(consultas.isPresent()) {
				return ResponseEntity.ok(ConsultaDto.converter(consultas.get()));
			}
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@PostMapping
	@CacheEvict(value = {"listaDePacientes", "listaDePacientesPorPlano"}, allEntries = true)
	public ResponseEntity<PacienteDto> cadastrar(@RequestBody @Valid PacienteForm form, UriComponentsBuilder uriBuilder) {
		Paciente paciente = form.converter();
		pacienteRepository.save(paciente);
		
		URI uri = uriBuilder.path("paciente/{id}").buildAndExpand(paciente.getId()).toUri();
		return ResponseEntity.created(uri).body(new PacienteDto(paciente));
	}
	
	@Transactional
	@PutMapping("/{id}")
	@CacheEvict(value = {"listaDePacientes", "listaDePacientesPorPlano"}, allEntries = true)
	public ResponseEntity<PacienteDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoPacienteForm form) {
		Optional<Paciente> optional = pacienteRepository.findById(id);
		
		if(optional.isPresent()) {
			Paciente paciente = form.atualizar(id, pacienteRepository);			
			return ResponseEntity.ok(new PacienteDto(paciente));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@DeleteMapping("/{id}")
	@CacheEvict(value = {"listaDePacientes", "listaDePacientesPorPlano"}, allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Paciente> optional = pacienteRepository.findById(id);
		
		if(optional.isPresent()) {
			pacienteRepository.deleteById(id);			
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
