package br.edu.ifsc.fln.cobranca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsc.fln.cobranca.model.Titulo;

/*
 * JpaRepository<T, ID> --> T = Entidade = Titulo; ID = Tipo de Dados da Chave Primária da Entidade
 * */
public interface Titulos extends JpaRepository<Titulo, Long>{
	//com essa interface todas as operações crud já serão fornecidas pelo Spring Data
}
