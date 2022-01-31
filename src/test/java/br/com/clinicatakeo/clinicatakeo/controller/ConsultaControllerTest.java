package br.com.clinicatakeo.clinicatakeo.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import br.com.clinicatakeo.clinicatakeo.controller.form.ConsultaForm;
import br.com.clinicatakeo.clinicatakeo.controller.form.DiagnosticoConsultaForm;
import br.com.clinicatakeo.clinicatakeo.controller.form.ReagendarHorarioConsultaForm;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ConsultaControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void deveriaRetornarUmaListaDeConsultas() throws Exception {
		URI uri = new URI("/consultas/findAll");
		
		mockMvc
			.perform(MockMvcRequestBuilders.get(uri)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void deveriaRetornarUmaListaDeConsultasAgendadas() throws Exception {
		URI uri = new URI("/consultas/findConsultasAgendadas");
		
		mockMvc
			.perform(MockMvcRequestBuilders.get(uri)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void deveriaCriarUmaConsulta() throws Exception {
		URI uri = new URI("/consultas");
		ConsultaForm consulta = new ConsultaForm(LocalDateTime.now().
				format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")).toString(), 6l, 6l);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		mockMvc
			.perform(MockMvcRequestBuilders.post(uri)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(ow.writeValueAsString(consulta)))
			.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void deveriaRealizarODiagnosticoDaConsulta() throws Exception {
		URI uri = new URI("/consultas/3/realizarDiagnostico");
		DiagnosticoConsultaForm diagnostico = new DiagnosticoConsultaForm("54.4", "cegueira monocular");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		mockMvc
			.perform(MockMvcRequestBuilders.patch(uri)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(ow.writeValueAsString(diagnostico)))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void deveriaCancelarUmaConsulta() throws Exception {
		URI uri = new URI("/consultas/4/cancelarConsulta");
		
		mockMvc
			.perform(MockMvcRequestBuilders.patch(uri))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void deveriaReagendarOHorarioDeUmaConsulta() throws Exception {
		URI uri = new URI("/consultas/4/reagendarHorario");
		ReagendarHorarioConsultaForm form = new ReagendarHorarioConsultaForm(LocalDateTime.now().
				format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")).toString());
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

		mockMvc
			.perform(MockMvcRequestBuilders.patch(uri)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(ow.writeValueAsString(form)))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
