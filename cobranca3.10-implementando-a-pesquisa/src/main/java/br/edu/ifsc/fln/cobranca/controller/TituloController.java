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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifsc.fln.cobranca.model.StatusTitulo;
import br.edu.ifsc.fln.cobranca.model.Titulo;
import br.edu.ifsc.fln.cobranca.repository.Titulos;
import br.edu.ifsc.fln.cobranca.repository.filter.TituloFilter;
import br.edu.ifsc.fln.cobranca.service.CadastroTituloService;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	private static final String CADASTRO_VIEW = "CadastroTitulo";
	
	//@Autowired
	//private Titulos titulos; //não está sendo mais utilizado no controller, só na camada de serviço
	
	@Autowired
	private CadastroTituloService cadastroTituloService;
	
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Titulo()); 
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		
		try { //o tratamento de exceções foi colocado para evitar de armazenar uma data inválida
			//titulos.save(titulo); //essa instrução foi substituída pelo salvar de service
			cadastroTituloService.salvar(titulo);
			attributes.addFlashAttribute("mensagem", "Título salvo com sucesso!"); 
			return "redirect:/titulos/novo";
		//} catch (DataIntegrityViolationException ex) { //obtem a excecao lançada no salvar de service
		} catch (IllegalArgumentException ex) { 
			//errors.rejectValue("dataVencimento", null, "Formato de data inválido!"); //substituir pela linha a seguir para obter a mensagem lançada no service
			errors.rejectValue("dataVencimento", null, ex.getMessage());
			return CADASTRO_VIEW;
		}
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo) {	
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(titulo);
		return mv;
	}
	
	@RequestMapping(value="{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		//titulos.deleteById(codigo); //substituir pelo excluir do service
		cadastroTituloService.excluir(codigo);
		
		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso");
		return "redirect:/titulos";
	}
	
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatusTitulo() {
		return Arrays.asList(StatusTitulo.values()); 
	}
	
	//a declaração @ResponseBody quer dizer que esse método não vai retornar uma outra view como nos outros casos (métodos), e sim um mensagem no corpo da resposta
	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo) {
		//System.out.println(">>>>> codigo: " + codigo);
		//vai chamar um método do service para executar um médodo para atualizar o status do titulo
		return cadastroTituloService.receber(codigo);
		
	}
	
	
	//adicionando um parâmetro para fazer uma pesquisa com filtro
	//@RequestParam(defaultValue = "%") --> quer dizer que se o usuário não informar nada na pesquisa ele vai utilizar o coringa % na pesquisa
	
	//public ModelAndView pesquisar() {
	//public ModelAndView pesquisar(@RequestParam(defaultValue="%") String descricao) {
	//melhorando o método pesquisar para receber um filtro
		//@ModelAttribute - avisa ao spring para criar um objeto TituloFilter e chamar de filtro
	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") TituloFilter filtro) {
		//List<Titulo> todosTitulos = titulos.findAll();
		//as duas linhas a seguir serão levadas para o service 
		//String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		//List<Titulo> todosTitulos = titulos.findByDescricaoContaining(filtro.getDescricao()); //usando uma pesquisa customizada deque foi declarada na interface Titulos (repository)
		List<Titulo> todosTitulos = cadastroTituloService.filtrar(filtro);
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		mv.addObject("titulos", todosTitulos);
		return mv;
	}
	
		
 }
