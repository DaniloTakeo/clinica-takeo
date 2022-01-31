package br.com.clinicatakeo.clinicatakeo.controller.form;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.clinicatakeo.clinicatakeo.model.Consulta;
import br.com.clinicatakeo.clinicatakeo.model.Medico;
import br.com.clinicatakeo.clinicatakeo.model.Paciente;
import br.com.clinicatakeo.clinicatakeo.repository.MedicoRepository;
import br.com.clinicatakeo.clinicatakeo.repository.PacienteRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaForm {

	@NotNull
	@NotBlank
	private String dataEHota;
	
	private Long idMedico;
	private Long idPaciente;

	public Consulta convereter(PacienteRepository pacienteRepository, MedicoRepository medicoRepository) {
		Medico medico = medicoRepository.findById(idMedico).get();
		Paciente paciente = pacienteRepository.findById(idPaciente).get();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		
		return new Consulta(LocalDateTime.parse(this.dataEHota, formatter), medico, paciente);
	}
	
}
