package com.chidemgames.protectthesurvivors.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chidemgames.protectthesurvivors.entities.Cena;
import com.chidemgames.protectthesurvivors.entities.Jogo;

public class GameManager {

	private String token;
	private int moedas = 200;
	private int level;
	private int xp;
	private String nome;
	private float score = 0;

	private float limitXP = 20f;

	public static final int MOEDAS = 1;
	public static final int LEVEL = 2;
	public static final int XP = 3;
	public static final int SCORE = 4;

	private List<Cena> cenas;
	private Jogo jogo;
	private Map<Integer, ArrayList<String>> comands;

	private GameManager(){}

	private static GameManager INSTANCE = new GameManager();

	public static GameManager getInstance(){
		return INSTANCE;
	}

	public String getNome() {
		return nome;
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public List<Cena> getCenas() {
		return cenas;
	}

	public void setCenas(List<Cena> cenas) {
		this.cenas = cenas;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getToken() {
		return token;
	}

	public Map<Integer, ArrayList<String>> getComands() {
		return comands;
	}

	public void setComands(Map<Integer, ArrayList<String>> comands) {
		this.comands = comands;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getMoedas() {
		return moedas;
	}

	public void setMoedas(int moedas) {
		this.moedas = moedas;
	}

	public void increment(float increase, int atributo){
		switch(atributo){
			case MOEDAS:
				this.moedas += increase;
				break;
			case LEVEL:
				this.level += increase;
				break;
			case XP:
				this.xp += increase;
				System.out.println("Increase xp: " + xp);
				if (xp / limitXP > 1){
					this.xp = 0;
					increment(1, LEVEL);
					System.out.println("Level up!! " + level);
				}
				break;
		}
	}

	public void decrement(float increase, int atributo){
		switch(atributo){
		case MOEDAS:
			this.moedas -= increase;
			break;
		case LEVEL:
			this.level -= increase;
			break;
		case XP:
			this.xp -= increase;
			break;
	}	}

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

}
