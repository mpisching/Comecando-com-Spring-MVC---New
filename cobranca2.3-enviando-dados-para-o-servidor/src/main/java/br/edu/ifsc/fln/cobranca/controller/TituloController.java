package br.edu.ifsc.fln.cobranca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.edu.ifsc.fln.cobranca.model.Titulo;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
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
		return "CadastroTitulo";
	}
 }
