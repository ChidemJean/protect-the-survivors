package com.chidemgames.protectthesurvivors.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.chidemgames.protectthesurvivors.PTSGame.TypeScene;

public class CommandsManager {

	private Map<Integer, ArrayList<String>> comandos = new HashMap<Integer, ArrayList<String>>();
	private static CommandsManager INSTANCE = new CommandsManager();
	private ArrayList<String> helpCommands = new ArrayList<String>();
	
	public static CommandsManager getInstance() {
		return INSTANCE;
	}
	
	public Map<Integer, ArrayList<String>> getCurrentCommands(TypeScene scene){
		
		helpCommands.clear();
		
		switch (scene){
			case MENU:
				ArrayList<String> s = new ArrayList<String>();
				
				s.add("ranking");
				helpCommands.add("Ranking - Para acessar o ranking");
				s.add("fila");
				helpCommands.add("Fila - Para mostrar a fila");
				s.add("comandos");
				helpCommands.add("Comandos - Para ver os comandos do contexto");
				s.add("jogar");
				helpCommands.add("Jogar - Para iniciar o jogo");
				s.add("sair");
				helpCommands.add("Sair - Para sair do app");
				s.add("logar");
				helpCommands.add("Logar - Para entrar com uma conta");
				
				comandos.put(1, new ArrayList<String>(s));
				
				s.clear();
				break;
				
			case LOADING:
				ArrayList<String> sj = new ArrayList<String>();
				
				sj.add("senha");
				helpCommands.add("Senha - Para senha");
				sj.add("nome");
				helpCommands.add("Nome - Para o nome");
				sj.add("entrar");
				helpCommands.add("Entrar - Para entrar");
				sj.add("cadastro");
				helpCommands.add("Cadastro - Para criar uma nova conta");
				sj.add("sair");
				helpCommands.add("Sair - para voltar para o menu");
				
				comandos.put(1, new ArrayList<String>(sj));
				sj.clear();
				break;
				
			case GAME:
				ArrayList<String> sl = new ArrayList<String>();
				
				sl.add("in");
				helpCommands.add("pular - Para o objeto pular");
				sl.add("cor");
				helpCommands.add("cor - Para mudar a cor de fundo");
				sl.add("direita");
				helpCommands.add("direita - Para o objeto andar para direita");
				sl.add("esquerda");
				helpCommands.add("esquerda - Para o objeto andar para esquerda");
				sl.add("sair");
				helpCommands.add("voltar - para voltar para o menu");
				sl.add("out");
				sl.add("baixo");
				sl.add("cima");
				
				comandos.put(1, new ArrayList<String>(sl));
				sl.clear();
				break;
		}
		
		return comandos;
	}
	
	public ArrayList<String> getHelpCommands(){
		return helpCommands;
	}
	
	
}
