package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

    @MockBean
    private AgendaDeConsultas agendaDeConsultas;

    @Test
    @DisplayName("Deve devolver código HTTP 400 quando informações estão invalidas")
    @WithMockUser
    void agendar_HTTP400 () throws Exception {
        var response = mvc.perform(post("/consultas")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve devolver código HTTP 200 quando informações estão validas")
    @WithMockUser
    void agendar_HTTP200 () throws Exception {
        Long idMedico = 2L;
        Long idPaciente = 5L;
        LocalDateTime data = LocalDateTime.now().plusHours(1);
        Especialidade especialidade = Especialidade.CARDIOLOGIA;
        DadosDetalhamentoConsulta dadosDetalhamentoConsulta = new DadosDetalhamentoConsulta(null, idMedico, idPaciente, data);

        when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamentoConsulta);

        var response = mvc.perform(post("/consultas").contentType(MediaType.APPLICATION_JSON).content(dadosAgendamentoConsultaJson.write(new DadosAgendamentoConsulta(idMedico, idPaciente, data, especialidade)).getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        var jsonEsperado = dadosDetalhamentoConsultaJson.write(dadosDetalhamentoConsulta).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}