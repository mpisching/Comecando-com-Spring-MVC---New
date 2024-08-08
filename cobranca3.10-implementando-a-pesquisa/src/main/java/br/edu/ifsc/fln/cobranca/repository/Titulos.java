package br.edu.ifsc.fln.cobranca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsc.fln.cobranca.model.Titulo;

public interface Titulos extends JpaRepository<Titulo, Long>{
	//personalizando uma consulta com Spring Data JPA - usando findBy[atribute name]Containing
	public List<Titulo> findByDescricaoContaining(String descricao); //a instrução sql fica por conta do Spring Data JPA neste caso - ver a documentação
}
