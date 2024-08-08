package br.edu.ifsc.fln.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifsc.fln.cobranca.model.StatusTitulo;
import br.edu.ifsc.fln.cobranca.model.Titulo;
import br.edu.ifsc.fln.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	private static final String CADASTRO_VIEW = "CadastroTitulo";
	
	@Autowired
	private Titulos titulos;//usa o Spring IOC, injetando o objeto para o Controller - os métodos do repositório
	
	/* melhorando o método novo para criar um combo dinâmico (para o status do Título) para o thymeleaf na página web*/
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
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
	//aula 2.14 - adição do Redirect Attribute do Spring MVC para limpar os campos e exibir a mensagem 'titulo salvo...' após salvar os dados de um título
	// o tipo de dados de retorno do método será substituído por String para otimizar o uso do redirect, pois não é necessário retornar um ModelAndView que já foi definido no método novo
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
		// não será necessário o uso do mv, podemos retornar apenas uma string para o uso do redirect, que vai considerar o ModelAndView do método novo(), responsável agora para iniciar o objeto  mv
		//ModelAndView mv = new ModelAndView("CadastroTitulo");
		if (errors.hasErrors()) {
			return CADASTRO_VIEW;//aqui retorna o nome da view, por outro lado, se essa condição não for satisfeita haverá um retorno para o redirect
		}
		titulos.save(titulo);
		// as duas linhas a seguir serão substituída por instruções para limpar os campos e continuar exibindo a mensagem de sucesso 
		// após redirecionar para a url /titulos/novo usando o redirect do Spring MVC
		//mv.addObject("mensagem", "Título salvo com sucesso!");
		//return mv;
		//ModelAndView mv2 = new ModelAndView("redirect:/titulos/novo"); //redireciona para renderizar a página novamente e assim limpar os campos
		attributes.addFlashAttribute("mensagem", "Título salvo com sucesso!"); //usa attributes para exibir a mensagem após salvar um título. Não fosse isso, a tela seria renderizada novamente e não viriamos a mensagem de sucesso
		// return mv2+*
		return "redirect:/titulos/novo"; //defini o retorno para o redirect, que chamará o método novo() e irá criar um mv novamente
	}
	
	@RequestMapping("{codigo}")
	//public ModelAndView edicao(@PathVariable Long codigo) { // a seguir o codigo é melhorado... passando um parâmetro codigo e o Spring criando um objeto título e já executando o método getOne para encontrar um objeto (isso acontece graças ao JpaReporitory
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo) {	
		//System.out.println(">>>>> codigo recebido: " + codigo);
		//Titulo titulo = titulos.getReferenceById(codigo);// getOne(codigo) --> deprecated
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(titulo);
		return mv;
	}
	
	@RequestMapping(value="{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		titulos.deleteById(codigo);
		
		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso");
		return "redirect:/titulos";
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
