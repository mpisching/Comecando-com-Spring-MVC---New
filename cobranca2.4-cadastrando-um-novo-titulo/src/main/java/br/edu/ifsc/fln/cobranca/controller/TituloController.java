package br.edu.ifsc.fln.cobranca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.edu.ifsc.fln.cobranca.model.Titulo;
import br.edu.ifsc.fln.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	@Autowired
	private Titulos titulos;//usa o Spring IOC, injetando o objeto para o Controller - os métodos do repositório
	
	//@RequestMapping("/titulos/novo")
	@RequestMapping("/novo")
	public String novo() {
		return "CadastroTitulo";
	}
	
	//@RequestMapping(value = "/titulos", method = RequestMethod.POST)
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(Titulo titulo) {
		// TODO: Salvar no banco de dados
		System.out.println(">>> " + titulo.getDescricao());
		
		titulos.save(titulo);
		
		return "CadastroTitulo";
	}
 }
