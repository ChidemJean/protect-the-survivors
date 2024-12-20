package com.chidemgames.protectthesurvivors.callbacks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chidemgames.protectthesurvivors.entities.Cena;
import com.chidemgames.protectthesurvivors.entities.Jogo;
import com.chidemgames.protectthesurvivors.entities.Objeto;

public interface IRequestActionCallback {

	public int atualizarJogador(String token, String nome, String email, String senha, String tipo, int moeda);
	
	public int salvarJogo(String token, String nome, int xp, int pontuacao, int level, String tipoJogo);
	public List<Jogo> buscarJogos(String nome);
	public int removerJogo(String token, int id);
	public int updateJogo(String token, int id, int xp, int pontuacao, int level);
	
	public int atualizarCena(String token, int id, String tipo, int pontuacao, boolean isStateSaved);
	public List<Cena> buscarCenas(String token, String nome);
	
	public List<Objeto> buscarObjetos(String token, int id);
	public int salvarOjetos(String token, List<Objeto> objetos, int idCena);
	public int removerObjetos(String token, List<Objeto> objetos, int idCena);
	
	public Map<Integer, ArrayList<String>> currentCommands();
}
