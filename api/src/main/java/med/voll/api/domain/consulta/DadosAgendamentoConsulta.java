package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;

public record DadosAgendamentoConsulta(
		Long idMedico,
		@NotNull Long idPaciente,
		@JsonProperty("especialidade")
		Especialidade especialidade,
		@NotNull @Future LocalDateTime data) {
}
