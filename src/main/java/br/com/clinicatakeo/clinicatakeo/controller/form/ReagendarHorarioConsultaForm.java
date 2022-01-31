package br.com.clinicatakeo.clinicatakeo.controller.form;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.clinicatakeo.clinicatakeo.model.Consulta;
import br.com.clinicatakeo.clinicatakeo.repository.ConsultaRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReagendarHorarioConsultaForm {

	@NotBlank
	@NotNull
	private String novaDataEHora;

	public Consulta atualizar(Long id, ConsultaRepository consultaRepository) {
		Consulta consulta = consultaRepository.getById(id);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		consulta.setDataEHora(LocalDateTime.parse(novaDataEHora, formatter));
		
		return consulta;
	}
		
}
