package br.com.clinicatakeo.clinicatakeo.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Consulta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime dataEHora;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Medico medico;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Paciente paciente;
	
	private String cid;
	private String diagnostico;
	
	@Enumerated(EnumType.STRING)
	private StatusConsulta status;
	
	public Consulta(@NotNull @NotBlank LocalDateTime dataEHota, Medico medico, Paciente paciente) {
		this.dataEHora = dataEHota;
		this.medico = medico;
		this.paciente = paciente;
		this.status = StatusConsulta.AGENDADA;
		this.cid = "";
		this.diagnostico = "";
	}

}
