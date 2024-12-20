package com.chidemgames.protectthesurvivors.entities;


import java.util.List;

public class Jogo {

	private int idJogo;
	
	private int pontuacaoTotal;
	
	private String tipoDeJogo;
	
	private String jogador;
	
	private int level;
	
	private int xp;
	
	public int getIdJogo() {
		return idJogo;
	}

	public void setIdJogo(int idJogo) {
		this.idJogo = idJogo;
	}

	public int getPontuacaoTotal() {
		return pontuacaoTotal;
	}

	public void setPontuacaoTotal(int pontuacaoTotal) {
		this.pontuacaoTotal = pontuacaoTotal;
	}

	public String getTipoDeJogo() {
		return tipoDeJogo;
	}

	public void setTipoDeJogo(String tipoDeJogo) {
		this.tipoDeJogo = tipoDeJogo;
	}

	public String getJogador() {
		return jogador;
	}

	public void setJogador(String jogador) {
		this.jogador = jogador;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}
	
	public Jogo(String tipoDeJogo, String jogador, int pontuacao) {
		this.pontuacaoTotal = pontuacao;
		this.tipoDeJogo = tipoDeJogo;
		this.jogador = jogador;
		this.level = 1;
		this.xp = 0;
	}

	public Jogo() {}
	
}
