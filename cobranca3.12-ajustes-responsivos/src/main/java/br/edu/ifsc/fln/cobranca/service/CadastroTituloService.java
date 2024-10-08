package br.edu.ifsc.fln.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.edu.ifsc.fln.cobranca.model.StatusTitulo;
import br.edu.ifsc.fln.cobranca.model.Titulo;
import br.edu.ifsc.fln.cobranca.repository.Titulos;
import br.edu.ifsc.fln.cobranca.repository.filter.TituloFilter;

/*
 * Esta classe está sendo criada com o propósito de fornecer serviços (regras de negócio) entre o controller e o modelo de negócios
 * Criar uma camada de serviço é importante para casos de querer usar regras de negócios específicas para outros sistemas por exemplo, como por exemplo, ao excluir um titulo, notificar a uma outra aplicação. Se deixa-se no controller estariamos muito amarrados, haja vista que esse controller é para o mvc web
 */
@Service //com essa anotação esta classe passa a se tornar também um componente do spring, ou seja, significa que ele pode ser injetado também em outras classes 
		//A injeção é feita com a anotação @autowired, por exemplo, na classe Controller
public class CadastroTituloService {

	@Autowired
	private Titulos titulos;//repositório
	
	public void salvar(Titulo titulo) {
		try {
			titulos.save(titulo);
		} catch (DataIntegrityViolationException ex) {
			throw new IllegalArgumentException("Formato de data inválida!");
		}
	}
	
	public void excluir(Long codigo) {
		titulos.deleteById(codigo );
	}

	public String receber(Long codigo) {
		Titulo titulo = titulos.findById(codigo).get();
		titulo.setStatus(StatusTitulo.RECEBIDO);
		titulos.save(titulo);
		
		return StatusTitulo.RECEBIDO.getDescricao();
	}
	
	public List<Titulo> filtrar(TituloFilter filtro) {
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		return titulos.findByDescricaoContaining(descricao);
	}
}
