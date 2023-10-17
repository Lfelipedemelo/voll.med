package med.voll.api.controller;

import med.voll.api.paciente.Paciente;

public record DadosListagemPaciente(String nome, String email, String cpf) {
	
	public DadosListagemPaciente(Paciente paciente) {
		this(paciente.getNome(), paciente.getEmail(), paciente.getCpf());
	}

}
