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

import br.com.clinicatakeo.clinicatakeo.controller.form.EnderecoForm;
import br.com.clinicatakeo.clinicatakeo.controller.form.MedicoForm;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MedicoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void deveriaRetornarUmaListaDeMedicos() throws Exception {
		URI uri = new URI("/medicos/findAll");
		
		mockMvc
			.perform(MockMvcRequestBuilders.get(uri)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void deveriaRetornarUmaListaDeMedicosPorEspecialidade() throws Exception {
		URI uri = new URI("/medicos/especialidade=Pediatra");
		
		mockMvc
			.perform(MockMvcRequestBuilders.get(uri)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void deveriaRetornarUmaListaDeConsultasPorMedico() throws Exception {
		URI uri = new URI("/medicos/6/consultasAgendadas");
		
		mockMvc
			.perform(MockMvcRequestBuilders.get(uri))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void deveriaCriarUmMedico() throws Exception {
		URI uri = new URI("/medicos");
		EnderecoForm enderecoForm = new EnderecoForm("Rua teste", "123", "Teste", "12345-678", "Teste");
		MedicoForm medicoForm = new MedicoForm("Teste", enderecoForm, "123456789", "Endocrinologista");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

		mockMvc
			.perform(MockMvcRequestBuilders.post(uri)
					.content(ow.writeValueAsString(medicoForm))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void deveriaAtualizarUmMedico() throws Exception {
		URI uri = new URI("/medicos/3");
		EnderecoForm enderecoForm = new EnderecoForm("Rua teste", "123", "Teste", "12345-678", "Teste");
		MedicoForm medicoForm = new MedicoForm("Teste", enderecoForm, "123456789", "Oncologista");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		mockMvc
			.perform(MockMvcRequestBuilders.put(uri)
					.content(ow.writeValueAsString(medicoForm))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
