package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class MedicoControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJson;

	@Autowired
	private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoJson;

	@MockBean
	private MedicoRepository repository;

	@Test
	@DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
	@WithMockUser
	void cadastrarCenario1() throws Exception {
		MockHttpServletResponse response = mvc.perform(post("/medicos")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	@WithMockUser
	@DisplayName("Deve devolver codigo http 200 quando informacoes validas")
	void cadastrarMedicoCenario2() throws IOException, Exception {
		DadosDetalhamentoMedico dadosDetalhamento = new DadosDetalhamentoMedico(null, "medico", "medico@email.com",
				"12345", "48999999999", Especialidade.CARDIOLOGIA, new Endereco(dadosEndereco()), true);
		DadosCadastroMedico dadosCadastro = new DadosCadastroMedico("medico", "medico@email.com",
				"48999999999", "12345", Especialidade.CARDIOLOGIA, dadosEndereco());
		when(repository.save(any())).thenReturn(new Medico(dadosCadastro));

		MockHttpServletResponse response = mvc
				.perform(post("/medicos").contentType(MediaType.APPLICATION_JSON)
						.content(dadosCadastroMedicoJson.write(dadosCadastro).getJson()))
				.andReturn().getResponse();

		String jsonEsperado = dadosDetalhamentoMedicoJson.write(dadosDetalhamento).getJson();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

	}

	private DadosEndereco dadosEndereco() {
		return new DadosEndereco("rua xpto", "bairro", "00000000", "Brasilia", "DF", null, null);
	}

}
