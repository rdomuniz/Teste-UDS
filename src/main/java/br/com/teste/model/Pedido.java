package br.com.teste.model;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import br.com.teste.model.enums.Personalizacoes;
import br.com.teste.model.enums.Sabores;
import br.com.teste.model.enums.Tamanhos;
import lombok.Data;

@Data
@Entity(name = "PEDIDO")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 3951279906463186472L;

	@Id
	@Column(name = "PEDIDO_ID", nullable = false, length = 9)
	@NotNull(message = "ID deve ser informado!", groups = ONUpdate.class)
	@Null(message = "ID não deve ser informado!", groups = ONCreate.class)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PEDIDO_SQ")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TAMANHO", nullable = false, length = 50)
	@NotNull(message = "Tamanho deve ser informado!", groups = {ONCreate.class, ONUpdate.class})
	private Tamanhos tamanho;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "SABOR", nullable = false, length = 50)
	@NotNull(message = "Sabor deve ser informado!", groups = {ONCreate.class, ONUpdate.class})
	private Sabores sabor;
	
	@JoinTable(
		name = "PEDIDO_PERSONALIZACAO",
		joinColumns = @JoinColumn(name = "PEDIDO_ID", foreignKey = @ForeignKey(name = "PEDIDO_FK"))
	)
	@Column(name = "PERSONALIZACAO")
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Personalizacoes.class, fetch = FetchType.LAZY)
	private List<Personalizacoes> personalizacoes = new ArrayList<>();
	
}
