package com.chidemgames.protectthesurvivors.entities;

import java.util.List;


public class Jogador {

	private int idJogador;
	
	private String nome;
	
	private String tipoDeJogador;

	private String email;
	
	private String senha;
	
	private int moeda;
				
	public int getIdJogador() {
		return idJogador;
	}

	public void setIdJogador(int idJogador) {
		this.idJogador = idJogador;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipoDeJogador() {
		return tipoDeJogador;
	}

	public void setTipoDeJogador(String tipoDeJogador) {
		this.tipoDeJogador = tipoDeJogador;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Jogador(String nome, String tipoDeJogador, String email, String senha) {
		this.nome = nome;
		this.tipoDeJogador = tipoDeJogador;
		this.email = email;
		this.senha = senha;
	}

	public Jogador() {}

	public int getMoeda() {
		return moeda;
	}

	public void setMoeda(int moeda) {
		this.moeda = moeda;
	}

}
