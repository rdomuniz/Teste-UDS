package br.com.teste.model.administracao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;

import br.com.teste.model.ONCreate;
import br.com.teste.model.ONUpdate;
import br.com.teste.model.enums.ClassificacaoDoUsuario;
import br.com.teste.model.enums.StatusDoRegistro;
import br.com.teste.model.view.listagem.administracao.UsuarioView;
import lombok.Data;

@Data
@Entity(name = "USUARIO")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 18921923831647093L;

	@Id
	@JsonView(UsuarioView.class)
	@Column(name = "USUARIO_ID", nullable = false, length = 5)
	@NotNull(message = "ID deve ser informado!", groups = ONUpdate.class)
	@Null(message = "ID não deve ser informado!", groups = ONCreate.class)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "USUARIO_SQ")
	private Long id;
	
	@JsonView(UsuarioView.class)
	@Column(name = "NOME", nullable = false, length = 100)
	@NotBlank(message = "Nome deve ser informado!", groups = {ONCreate.class, ONUpdate.class})
	@Size(message = "Nome ultrapassou 100 caracteres!", max = 100, groups = {ONCreate.class, ONUpdate.class})
	private String nome;
	
	@JsonView(UsuarioView.class)
	@Column(name = "LOGIN", length = 20)
	@NotBlank(message = "Login deve ser informado!", groups = {ONCreate.class, ONUpdate.class})
	@Size(message = "Login ultrapassou 20 caracteres!", max = 20, groups = {ONCreate.class, ONUpdate.class})
	private String login;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "SENHA", nullable = false, length = 100)
	@NotBlank(message = "Senha deve ser informado!", groups = {ONCreate.class})
	private String senha;

	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotBlank(message = "Confirmação de senha deve ser informado!", groups = {ONCreate.class})
	private String confirmacaoDeSenha;
	
	@JsonView(UsuarioView.class)
	@Enumerated(EnumType.STRING)
	@Column(name = "CLASSIFICACAO", length = 50)
	private ClassificacaoDoUsuario classificacao;
	
	@JsonView(UsuarioView.class)
	@Column(name = "ADMINISTRADOR", nullable = false)
	@NotNull(message = "Deve ser informado se é administrador!", groups = {ONCreate.class, ONUpdate.class})
	private Boolean administrador;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_AUTENTICACAO", nullable = true)
	private Date dataDeAutenticacao;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS_REGISTRO", nullable = false, length = 50)
	@NotNull(message = "Status deve ser informado!", groups = {ONCreate.class, ONUpdate.class})
	private StatusDoRegistro statusDoRegistro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_INCLUSAO", nullable = false, updatable = false)
	private Date dataDeInclusao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_ALTERACAO", nullable = false)
	private Date dataDeAlteracao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_EXCLUSAO", nullable = true)
	private Date dataDeExclusao;
	
	public void apreencheAtualizacao(Usuario usuario) {
		setNome(usuario.getNome());
		setClassificacao(usuario.getClassificacao());
		setStatusDoRegistro(usuario.getStatusDoRegistro());
		setAdministrador(usuario.getAdministrador());
	}

	@JsonIgnore
	public boolean isInativo() {
		return StatusDoRegistro.INATIVO.compareTo(getStatusDoRegistro()) == 0;
	}
	
}