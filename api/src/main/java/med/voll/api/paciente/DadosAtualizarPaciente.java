package med.voll.api.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.Endereco;

public record DadosAtualizarPaciente(@NotNull Long id,String nome, String telefone, @Valid Endereco endereco) {
	
}
