package br.com.teste.model.administracao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.teste.model.ONCreate;
import br.com.teste.model.ONUpdate;
import br.com.teste.model.enums.AcessoDoSistema;
import br.com.teste.model.enums.PermissaoDoSistema;
import br.com.teste.model.view.listagem.administracao.GrupoDePermissaoView;
import lombok.Data;

@Data
@Entity(name = "GRUPO_PERMISSAO")
public class GrupoDePermissao implements Serializable {

	private static final long serialVersionUID = -3432410652816316071L;

	@Id
	@JsonView(GrupoDePermissaoView.class)
	@Column(name = "GRUPO_PERMISSAO_ID", nullable = false, length = 4)
	@NotNull(message = "ID deve ser informado!", groups = ONUpdate.class)
	@Null(message = "ID não deve ser informado!", groups = ONCreate.class)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GRUPO_PERMISSAO_SQ")
	private Long id;
	
	@JsonView(GrupoDePermissaoView.class)
	@Column(name = "DESCRICAO", nullable = false, length = 100)
	@NotBlank(message = "Descrição deve ser informado!", groups = {ONCreate.class, ONUpdate.class})
	@Size(message = "Descrição ultrapassou 100 caracteres!", max = 100, groups = {ONCreate.class, ONUpdate.class})
	private String descricao;
	
	@JsonView(GrupoDePermissaoView.class)
	@NotNull(message = "Deve ser informado se é ativo!", groups = {ONCreate.class, ONUpdate.class})
	@Column(name = "ATIVO", nullable = false)
	private Boolean ativo;
	
	@JoinTable(
		name = "GRUPO_PERMISSAO_ACESSO",
		joinColumns = @JoinColumn(name = "GRUPO_PERMISSAO_ID", foreignKey = @ForeignKey(name = "GRUPO_PERMISSAO_FK"))
	)
	@Column(name = "ACESSO")
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = AcessoDoSistema.class, fetch = FetchType.LAZY)
	private List<AcessoDoSistema> acessos = new ArrayList<>();
	
	@JoinTable(
		name = "GRUPO_PERMISSAO_PERMISSAO",
		joinColumns = @JoinColumn(name = "GRUPO_PERMISSAO_ID", foreignKey = @ForeignKey(name = "GRUPO_PERMISSAO_FK"))
	)
	@Column(name = "PERMISSAO")
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = PermissaoDoSistema.class, fetch = FetchType.LAZY)
	private List<PermissaoDoSistema> permissoes = new ArrayList<>();
	
	public GrupoDePermissao() {}
	public GrupoDePermissao(Long id) {
		this.id = id;
	}
	
}