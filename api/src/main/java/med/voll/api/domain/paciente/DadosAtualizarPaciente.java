package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.Endereco;

public record DadosAtualizarPaciente(@NotNull Long id,String nome, String telefone, @Valid Endereco endereco) {
	
}
