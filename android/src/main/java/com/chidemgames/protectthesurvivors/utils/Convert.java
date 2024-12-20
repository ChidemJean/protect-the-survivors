package com.chidemgames.protectthesurvivors.utils;

import org.ksoap2.serialization.SoapObject;

import com.chidemgames.protectthesurvivors.entities.Cena;
import com.chidemgames.protectthesurvivors.entities.Conquista;
import com.chidemgames.protectthesurvivors.entities.Habilidade;
import com.chidemgames.protectthesurvivors.entities.Jogador;
import com.chidemgames.protectthesurvivors.entities.Jogo;
import com.chidemgames.protectthesurvivors.entities.Objeto;

public class Convert {

	public static Jogo paraJogo (SoapObject object){

		Jogo jogo = new Jogo();

		jogo.setIdJogo(Integer.parseInt(object.getProperty("idJogo").toString()));
		jogo.setLevel(Integer.parseInt(object.getProperty("level").toString()));
		jogo.setPontuacaoTotal(Integer.parseInt(object.getProperty("pontuacaoTotal").toString()));
		jogo.setTipoDeJogo(object.getProperty("tipoDeJogo").toString());
		jogo.setXp(Integer.parseInt(object.getProperty("xp").toString()));

		return jogo;

	}

	public static Jogador paraJogador(SoapObject object){

		Jogador jogador = new Jogador();

		jogador.setIdJogador(Integer.parseInt(object.getProperty("idJogador").toString()));
		jogador.setNome(object.getProperty("nome").toString());
		jogador.setEmail(object.getProperty("email").toString());
		jogador.setMoeda(Integer.parseInt(object.getProperty("moeda").toString()));

		return jogador;

	}

	public static Conquista paraConquista(SoapObject object){

		Conquista conquista = new Conquista();

		conquista.setIdConquista(Integer.parseInt(object.getProperty("idConquista").toString()));
		conquista.setDescricao(object.getProperty("descricao").toString());

		return conquista;

	}

	public static Habilidade paraHabilidade(SoapObject object){

		Habilidade habilidade = new Habilidade();

		habilidade.setIdHabilidade(Integer.parseInt(object.getProperty("idHabilidade").toString()));
		habilidade.setDescricao(object.getProperty("descricao").toString());
		habilidade.setNome(object.getProperty("nome").toString());
		habilidade.setConstant(Boolean.parseBoolean(object.getProperty("isConstat").toString()));
		habilidade.setTempRest(Float.parseFloat(object.getProperty("tempRest").toString()));

		return habilidade;
	}

	public static Cena paraCena(SoapObject object){

		Cena cena = new Cena();

		cena.setIdCena(Integer.parseInt(object.getProperty("idCena").toString()));
		cena.setPontuacaoCena(Integer.parseInt(object.getProperty("pontuacaoCena").toString()));
		cena.setStateSaved(Boolean.parseBoolean(object.getProperty("isStateSaved").toString()));
		cena.setTipoDeCena(object.getProperty("tipoDeCena").toString());

		return cena;

	}

	public static Objeto paraObjeto(SoapObject object){

		Objeto objeto = new Objeto();

		objeto.setIdObjeto(Integer.parseInt(object.getProperty("idObjeto").toString()));
		objeto.setPosicaoX(Float.parseFloat(object.getProperty("posicaoX").toString()));
		objeto.setPosicaoY(Float.parseFloat(object.getProperty("posicaoY").toString()));
		objeto.setAngle(Float.parseFloat(object.getProperty("angle").toString()));
		objeto.setLife(Integer.parseInt(object.getProperty("life").toString()));

		return objeto;

	}

}
