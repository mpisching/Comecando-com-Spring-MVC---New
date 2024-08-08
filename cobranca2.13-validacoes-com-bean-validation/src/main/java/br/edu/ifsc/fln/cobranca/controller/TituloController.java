package br.edu.ifsc.fln.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
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
	
	/* melhorando o método novo para criar um combo dinâmico (para o status do Título) para o thymeleaf na página web*/
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("CadastroTitulo");
		/*
		 * O objeto a seguir deve ser instanciado ao abrir o novo mv, 
		 * haja vista que ele será usado para a validação de dados e referenciado por th:object na view (th:object="${titulo}") 
		 */
		mv.addObject(new Titulo()); 
		return mv;
	}
	
	//método salvar melhorado para retornar um ModelAndView, ou seja, retorna a view e alguns dados Model. Utilizado com Thymeleaf
	//adicionando o Validation Bean @Validated para validar os dados
	//adição do Errors para notificar qualquer erro de validação na view
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(@Validated Titulo titulo, Errors errors) {
		ModelAndView mv = new ModelAndView("CadastroTitulo");
		if (errors.hasErrors()) {
			return mv;
		}
		titulos.save(titulo);
		mv.addObject("mensagem", "Título salvo com sucesso!");
		return mv;
	}
	
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatusTitulo() {
		return Arrays.asList(StatusTitulo.values()); 
	}
	
	@RequestMapping
	public ModelAndView pesquisar() {
		List<Titulo> todosTitulos = titulos.findAll();
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		mv.addObject("titulos", todosTitulos);
		return mv;
	}
 }
