package br.com.clinicatakeo.clinicatakeo.controller;


import java.net.URI;
import java.time.LocalDateTime;
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
import br.com.clinicatakeo.clinicatakeo.controller.dto.MedicoDto;
import br.com.clinicatakeo.clinicatakeo.controller.form.AtualizarMedicoForm;
import br.com.clinicatakeo.clinicatakeo.controller.form.MedicoForm;
import br.com.clinicatakeo.clinicatakeo.model.Consulta;
import br.com.clinicatakeo.clinicatakeo.model.Medico;
import br.com.clinicatakeo.clinicatakeo.repository.ConsultaRepository;
import br.com.clinicatakeo.clinicatakeo.repository.MedicoRepository;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	@GetMapping("/findAll")
	@Cacheable(value = "listaDeMedicos")
	public Page<MedicoDto> consultarTodos(@PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao) {
		Page<Medico> medicos = medicoRepository.findAll(paginacao);
		
		return MedicoDto.converter(medicos);
	}
	
	@GetMapping("/especialidade={especialidade}")
	@Cacheable(value = "listaMedicosPorEspecialidade")
	public ResponseEntity<Page<MedicoDto>> consultarPorEspecialidade(@PathVariable String especialidade,
			@PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao) {
		Optional<Page<Medico>> medicos = medicoRepository.findByEspecialidade(especialidade, paginacao);
		
		if(medicos.isPresent()) {
			return ResponseEntity.ok(MedicoDto.converter(medicos.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}/consultasAgendadas")
	public ResponseEntity<Page<ConsultaDto>> consultarTodasConsultasAgendadas(@PathVariable Long id, 
			@PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao) {
		Optional<Medico> medico = medicoRepository.findById(id);
		
		if(medico.isPresent()) {
			Optional<Page<Consulta>> consultas = consultaRepository.findByMedico(medico.get(), paginacao);
			
			if(consultas.isPresent()) {
				return ResponseEntity.ok(ConsultaDto.converter(consultas.get()));
			}
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}/consultasDeHoje")
	public ResponseEntity<Page<ConsultaDto>> consultasConsultasDeHoje(@PathVariable Long id, 
			@PageableDefault(sort = "id", direction = Direction.ASC) Pageable paginacao) {
		Optional<Medico> medico = medicoRepository.findById(id);
		
		if(medico.isPresent()) {
			Optional<Page<Consulta>> consultas = consultaRepository.findByMedicoAndDataEHora(medico.get(),
					LocalDateTime.now(), paginacao);
			
			if(consultas.isPresent()) {
				return ResponseEntity.ok(ConsultaDto.converter(consultas.get()));
			}
			
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@PostMapping
	@CacheEvict(value = {"listaDeMedicos", "listaMedicosPorEspecialidade"}, allEntries = true)
	public ResponseEntity<MedicoDto> cadastrar(@RequestBody @Valid MedicoForm form, UriComponentsBuilder uriBuilder) {
		Medico medico = form.converter();
		medicoRepository.save(medico);
		
		URI uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		return ResponseEntity.created(uri).body(new MedicoDto(medico));
	}
	
	@Transactional
	@PutMapping("/{id}")
	@CacheEvict(value = {"listaDeMedicos", "listaMedicosPorEspecialidade"}, allEntries = true)
	public ResponseEntity<MedicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizarMedicoForm form) {
		Optional<Medico> optional = medicoRepository.findById(id);
		
		if(optional.isPresent()) {
			Medico medico = form.atualizar(id, medicoRepository);
			return ResponseEntity.ok(new MedicoDto(medico));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@DeleteMapping("/{id}")
	@CacheEvict(value = {"listaDeMedicos", "listaMedicosPorEspecialidade"}, allEntries = true)
	public ResponseEntity<MedicoDto> remover(@PathVariable Long id) {
		Optional<Medico> optional = medicoRepository.findById(id);

		if(optional.isPresent()) {
			medicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
