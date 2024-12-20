package com.chidemgames.protectthesurvivors.callbacks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.chidemgames.protectthesurvivors.daos.external.JogadorDAO;
import com.chidemgames.protectthesurvivors.daos.external.JogoDAO;
import com.chidemgames.protectthesurvivors.entities.Cena;
import com.chidemgames.protectthesurvivors.entities.Jogo;
import com.chidemgames.protectthesurvivors.entities.Objeto;

public class RequestActionCallback implements IRequestActionCallback{

	public static final JogadorDAO jogadorDAO = new JogadorDAO();
	public static final JogoDAO jogoDAO = new JogoDAO();
	
	@Override
	public int atualizarJogador(String token, String nome, String email, String senha, String tipo, int moeda) {
		jogadorDAO.update(token, nome, email, senha, tipo, moeda);
		return 0;
	}

	@Override
	public int salvarJogo(String token, String nome, int xp, int pontuacao,	int level, String tipoJogo) {
		return 	jogoDAO.save(token, nome, xp, pontuacao, level, tipoJogo);
	}

	@Override
	public List<Jogo> buscarJogos(String nome) {
		
		return jogadorDAO.findAllJogos(nome);
		
	}

	@Override
	public int removerJogo(String token, int id) {
		return jogoDAO.remove(token, id);
		
	}

	@Override
	public int updateJogo(String token, int id, int xp, int pontuacao, int level) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int atualizarCena(String token, int id, String tipo, int pontuacao,
			boolean isStateSaved) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Cena> buscarCenas(String token, String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Objeto> buscarObjetos(String token, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int salvarOjetos(String token, List<Objeto> objetos, int idCena) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removerObjetos(String token, List<Objeto> objetos, int idCena) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<Integer, ArrayList<String>> currentCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
