package med.voll.api.paciente;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.endereco.Endereco;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Paciente {

	public Paciente (DadosCadastroPaciente dados) {
		this.nome = dados.nome();
		this.email = dados.email();
		this.telefone = dados.telefone();
		this.cpf = dados.cpf();
		this.endereco = new Endereco(dados.endereco());
		this.ativo = true;
	}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private String cpf;
	@Embedded
	private Endereco endereco;
	private boolean ativo;
	
	public void atualizar(DadosAtualizarPaciente dadosAtualizar) {
		if(null != dadosAtualizar.nome()) {
			this.nome = dadosAtualizar.nome();			
		}
		if (null != dadosAtualizar.telefone()) {
			this.telefone = dadosAtualizar.telefone();
		}
		if (null != dadosAtualizar.endereco()) {
			this.endereco = dadosAtualizar.endereco();
		}
	}
	
	public void excluir() {
		this.ativo = false;
	}
}
