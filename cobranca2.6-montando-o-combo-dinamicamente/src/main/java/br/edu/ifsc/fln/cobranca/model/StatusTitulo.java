package br.edu.ifsc.fln.cobranca.model;

public enum StatusTitulo {
	PENDENTE("Pendente"),
	RECEBIDO("Recebido"),
	CANCELADO("Cancelado");//adicionado só para fins de teste - usado para mostra o combo dinâmico com thymeleaf

	private String descricao;
	
	StatusTitulo(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
