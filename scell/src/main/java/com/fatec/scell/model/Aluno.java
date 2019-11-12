package com.fatec.scell.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.NaturalId;

@Entity(name = "Aluno")
public class Aluno {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NaturalId
	@Column(nullable = false, length = 13)
	@NotEmpty(message = "O RA deve ser preenchido") // o atributo nao pode ser nulo e o tamanho > zero
	
	private String ra;

	@Column(nullable = false, length = 100)
	@NotEmpty(message = "O email deve ser preenchido")

	private String email;
	@Column(nullable = false)
	@NotNull(message = "Nome invalido")
	@Size(min = 5, max = 50, message = "Nome deve ter entre 5 e 50 caracteres")
	private String nome;

	public Aluno() 
	{
		
	}

	public Aluno(String ra, String nome, String email) 
	{
		this.ra = ra;
		this.nome = nome;
		this.email = email;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public Long getId() 
	{
		return id;
	}

	public String getRa() 
	{
		return ra;
	} 

	public void setRa(String ra) 
	{ 
		this.ra = ra;
	}

	public String getNome() 
	{
		return nome;
	}

	public void setNome(String nome) 
	{
		this.nome = nome;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}
}