package com.chidemgames.protectthesurvivors.utils;

public class Status {

	public static final int NAO_ENCONTRADO = -100;
	public static final int EXECUTADO_COM_SUCESSO = -101;
	public static final int ERRO_INTERNO = -102;
	public static final int JA_EXISTE = -103;
	public static final int SEM_PERMISSAO = -104;
	public static final int SENHA_INCORRETA = -105;
	public static final int SEM_RESPOSTA = -106;
	
	public static String getMessage(int status, String object){
		
		String message = null;
		
		switch (status){
		
			case NAO_ENCONTRADO:
				message = object + " n�o encontrado! ";
				break;
				
			case EXECUTADO_COM_SUCESSO:
				message = object + " salvo com sucesso! ";
				break;
				
			case ERRO_INTERNO:
				message = " Ocorreu um erro! ";
				break;
				
			case JA_EXISTE:
				message = object + " n�o dispon�vel! ";
				break;
				
			case SEM_PERMISSAO:
				message = " Voc� n�o tem permiss�es ";
				break;
				
			case SENHA_INCORRETA:
				message = " Senha incorreta ";
				break;
				
			case SEM_RESPOSTA:
				message = " Servidor n�o est� respondendo! ";
				break;
		}
		
		return message;
		
	}
	
}
