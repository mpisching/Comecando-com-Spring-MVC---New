package br.edu.ifsc.fln.cobranca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsc.fln.cobranca.model.Titulo;

public interface Titulos extends JpaRepository<Titulo, Long>{
	
}
