package br.com.clinicatakeo.clinicatakeo.controller.dto;

import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Page;

import br.com.clinicatakeo.clinicatakeo.model.Consulta;
import lombok.Getter;

@Getter
public class ConsultaDto {
	
	private String dataEHora;
	private String nomeMedico;
	private String nomePaciente;
	private String cid;
	private String diagnostico;
	private String status;

	public ConsultaDto(Consulta consulta) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		this.dataEHora =  consulta.getDataEHora().format(formatter);
		this.nomeMedico = consulta.getMedico().getNome();
		this.nomePaciente = consulta.getPaciente().getNome();
		this.cid = consulta.getCid();
		this.diagnostico = consulta.getDiagnostico();
		this.status = consulta.getStatus().toString();
	}

	public static Page<ConsultaDto> converter(Page<Consulta> consultas) {
		return consultas.map(ConsultaDto::new);
	}

}
