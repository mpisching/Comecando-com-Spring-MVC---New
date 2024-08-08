package br.edu.ifsc.fln.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifsc.fln.cobranca.model.StatusTitulo;
import br.edu.ifsc.fln.cobranca.model.Titulo;
import br.edu.ifsc.fln.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	@Autowired
	private Titulos titulos;//usa o Spring IOC, injetando o objeto para o Controller - os métodos do repositório
	
//	//@RequestMapping("/titulos/novo")
//	@RequestMapping("/novo")
//	public String novo() {
//		return "CadastroTitulo";
//	}
	
	/* melhorando o método novo para criar um combo dinâmico (para o status do Título) para o thymeleaf na página web*/
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("CadastroTitulo");
		//mv.addObject("todosStatusTitulo", StatusTitulo.values());//retorna todos os valores do enum StatusTitulo na forma de array
		
		return mv;
	}
	
//	//@RequestMapping(value = "/titulos", method = RequestMethod.POST)
//	@RequestMapping(method = RequestMethod.POST)
//	public String salvar(Titulo titulo) {
//		// TODO: Salvar no banco de dados
//		System.out.println(">>> " + titulo.getDescricao());
//		
//		titulos.save(titulo);
//		
//		return "CadastroTitulo";
//	}
	
	//método salvar melhorado para retornar um ModelAndView, ou seja, retorna a view e alguns dados Model. Utilizado com Thymeleaf
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(Titulo titulo) {
		// TODO: Salvar no banco de dados
		titulos.save(titulo);
		ModelAndView mv = new ModelAndView("CadastroTitulo");
		//mv.addObject("mensagem", "Título salvo com sucesso!"); //o atributo "mensagem" e utilizado no html com thymeleaf
		//mv.addObject("todosStatusTitulo", StatusTitulo.values());//adicionado para redesenhar o combo dinâmico com as opções do status após salvar e redesenhar a página, mas
															     // essa linha será melhorada para evitar de ter que escrever esse código novamente em cada método. Ver o método todosStausTitulo()
		return mv;
	}
	
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatusTitulo() {
		return Arrays.asList(StatusTitulo.values()); 
	}
	
	@RequestMapping
	public String pesquisar() {
		return "PesquisaTitulos";
	}
 }
