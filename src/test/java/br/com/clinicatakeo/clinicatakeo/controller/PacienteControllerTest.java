package br.com.clinicatakeo.clinicatakeo.controller;

import java.net.URI;

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

import br.com.clinicatakeo.clinicatakeo.controller.form.AtualizacaoPacienteForm;
import br.com.clinicatakeo.clinicatakeo.controller.form.EnderecoForm;
import br.com.clinicatakeo.clinicatakeo.controller.form.PacienteForm;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PacienteControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void deveriaDevolverUmaListaDePacientes() throws Exception {
		URI uri = new URI("/paciente/findAll");
		
		mockMvc
			.perform(MockMvcRequestBuilders.get(uri)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void deveriaDevolverUmaListaDePacientesPorPlanoDeSaude() throws Exception {
		URI uri = new URI("/paciente/planoDeSaude=PLANO2");
		
		mockMvc
			.perform(MockMvcRequestBuilders.get(uri)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void deveriaCriarUmPaciente() throws Exception {
		URI uri = new URI("/paciente");
		EnderecoForm enderecoForm = new EnderecoForm("Rua teste", "123", "Teste", "12345-678", "Teste");
		PacienteForm pacienteForm = new PacienteForm("Teste", enderecoForm, "PLANO2");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		mockMvc
			.perform(MockMvcRequestBuilders.post(uri)
					.content(ow.writeValueAsString(pacienteForm))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void deveriaAtualizarUmPaciente() throws Exception {
		URI uri = new URI("/paciente/6");
		EnderecoForm enderecoForm = new EnderecoForm("Rua teste2", "123", "Teste", "12345-678", "Teste");
		AtualizacaoPacienteForm atualizacaoForm = new AtualizacaoPacienteForm("Teste", enderecoForm, "PLANO2");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		mockMvc
			.perform(MockMvcRequestBuilders.put(uri)
					.content(ow.writeValueAsString(atualizacaoForm))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
