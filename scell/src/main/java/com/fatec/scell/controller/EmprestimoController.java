package com.fatec.scell.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.scell.model.AlunoRepository;
import com.fatec.scell.model.Emprestimo;
import com.fatec.scell.model.EmprestimoRepository;
import com.fatec.scell.model.Livro;
import com.fatec.scell.model.LivroRepository;
import com.fatec.scell.model.Servico;


@RestController
@RequestMapping(path = "/emprestimos")

	public class EmprestimoController 
	{
		@Autowired 
		private EmprestimoRepository emprestimoRepository;
		@Autowired 
		private LivroRepository livroRepository;
		@Autowired 
		private AlunoRepository alunoRepository;
		@Autowired 
		private Servico servico;
		/**
		 * quando o usuario digita localhost:8080/emprestimo/cadastrar
		 *
		 * @param emprestimo
		 * @return o html /RegistrarEmprestimo
		*/
		@GetMapping("/cadastrar")
		
		public ModelAndView registrarEmprestimo(Emprestimo emprestimo) 
		{
			ModelAndView mv = new ModelAndView("registrarEmprestimo");
			mv.addObject("emprestimo", emprestimo);
			return mv;
		}
		
		@GetMapping("/consulta")
		public ModelAndView listar() 
		{
			ModelAndView modelAndView = new ModelAndView("consultarEmprestimo");
			modelAndView.addObject("emprestimos", emprestimoRepository.findAll());
			return modelAndView;
		}
		
		@GetMapping("/delete/{id}")
		public ModelAndView delete(@PathVariable("id") Long id) {
			emprestimoRepository.deleteById(id);
			ModelAndView modelAndView = new ModelAndView("consultarEmprestimo");
			modelAndView.addObject("emprestimos", emprestimoRepository.findAll());
			return modelAndView;
		}
		
		@PostMapping("/save")
		public ModelAndView save(@Valid Emprestimo emprestimo, BindingResult result) {
			ModelAndView modelAndView = new ModelAndView("consultarEmprestimo");
			if (result.hasErrors()) {
				return new ModelAndView("registrarEmprestimo");
			}
			try {
				Emprestimo jaExiste = null;
				jaExiste = emprestimoRepository.findByIsbn(emprestimo.getIsbn());
				if (jaExiste == null) {
					emprestimoRepository.save(emprestimo);
					modelAndView = new ModelAndView("consultarEmprestimo");
					modelAndView.addObject("emprestimos", emprestimoRepository.findAll());
					return modelAndView;
				} else {
					return new ModelAndView("registrarEmprestimo");
				}
			} catch (Exception e) {
				System.out.println("erro ===> " + e.getMessage());
				return modelAndView; // captura o erro mas nao informa o motivo.
			}
		}
		
		@PostMapping("/update/{id}")
		public ModelAndView atualiza(@PathVariable("id") Long id, @Valid Emprestimo emprestimo, BindingResult result) {

			if (result.hasErrors()) {
				emprestimo.setId(id);
				return new ModelAndView("atualizaEmprestimo");
			}
			Emprestimo umEmprestimo = emprestimoRepository.findById(id).get();
			umEmprestimo.setRa(emprestimo.getRa());
			umEmprestimo.setIsbn(emprestimo.getIsbn());
			umEmprestimo.setDataEmprestimo();
			umEmprestimo.setDataDevolucaoPrevista();
			emprestimoRepository.save(umEmprestimo);
			ModelAndView modelAndView = new ModelAndView("consultarEmprestimo");
			modelAndView.addObject("emprestimos", emprestimoRepository.findAll());
			return modelAndView;
		
		
		}
} 
