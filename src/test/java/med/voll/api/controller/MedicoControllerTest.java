package med.voll.api.controller;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoJson;

    @Test
    @DisplayName("Deve devolver código HTTP 400 quando informações estão invalidas")
    @WithMockUser
    void cadastrar_HTTP400 () throws Exception {
        assertThat(mvc.perform(post("/medicos")).andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


//    @Test
//    @DisplayName("Deve devolver código HTTP 200 quando informações estão validas")
//    @WithMockUser
//    void cadastrar_HTTP200 () throws Exception {
//        int rn = new Random().nextInt();
//        String nome = "MedTeste" + rn;
//        String email = "teste@email" + rn;
//
//        String telefone = String.valueOf((int) (Math.random() * 1000000000));
//        String crm = String.valueOf((int) (Math.random() * 100000));
//
//        Especialidade especialidade = Especialidade.CARDIOLOGIA;
//        DadosEndereco dadosEndereco = new DadosEndereco("logradouro", "bairro", "12123123", "cidade", "uf", "complemento", "numero");
//        Endereco endereco = new Endereco(dadosEndereco);
//
//        DadosDetalhamentoMedico dadosDetalhamentoMedico = new DadosDetalhamentoMedico(null, nome, email, crm, telefone, especialidade, endereco);
//
//        var response = mvc.perform(post("/medicos").contentType(MediaType.APPLICATION_JSON).content(dadosCadastroMedicoJson.write(new DadosCadastroMedico(nome, email, telefone, crm, especialidade, dadosEndereco)).getJson())).andReturn().getResponse();
//
//
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
//        assertThat(response.getContentAsString()).isEqualTo(dadosDetalhamentoMedicoJson.write(dadosDetalhamentoMedico).getJson());
//    }


    @Test
    @DisplayName("Deve devolver código HTTP 204 quando inativar o Médico")
    @WithMockUser
    void deletar_HTTP204 () throws Exception {
        int id = 99999;
        assertThat(mvc.perform(delete("/medicos/" + id)).andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}