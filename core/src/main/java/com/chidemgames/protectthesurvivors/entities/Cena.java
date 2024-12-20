package com.chidemgames.protectthesurvivors.entities;

import java.util.List;

public class Cena {

	private int idCena;
	
	private String tipoDeCena;
	
	private int pontuacaoCena;
	
	private Jogo jogo;
	
	private boolean isStateSaved;
	
	public int getIdCena() {
		return idCena;
	}

	public void setIdCena(int idCena) {
		this.idCena = idCena;
	}

	public String getTipoDeCena() {
		return tipoDeCena;
	}

	public void setTipoDeCena(String tipoDeCena) {
		this.tipoDeCena = tipoDeCena;
	}

	public int getPontuacaoCena() {
		return pontuacaoCena;
	}

	public void setPontuacaoCena(int pontuacaoCena) {
		this.pontuacaoCena = pontuacaoCena;
	}

	public boolean isStateSaved() {
		return isStateSaved;
	}

	public void setStateSaved(boolean isStateSaved) {
		this.isStateSaved = isStateSaved;
	}

	public Cena(String tipoDeCena, int pontuacaoCena, boolean isStateSaved, Jogo jogo) {
		this.tipoDeCena = tipoDeCena;
		this.pontuacaoCena = pontuacaoCena;
		this.isStateSaved = isStateSaved;
		this.jogo = jogo;
	}

	public Cena() {}
	
}
