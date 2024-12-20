package com.chidemgames.protectthesurvivors.daos.external;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.chidemgames.protectthesurvivors.entities.Conquista;
import com.chidemgames.protectthesurvivors.entities.Habilidade;
import com.chidemgames.protectthesurvivors.entities.Jogador;
import com.chidemgames.protectthesurvivors.entities.Jogo;
import com.chidemgames.protectthesurvivors.utils.Convert;
import com.chidemgames.protectthesurvivors.utils.Status;

public class JogadorDAO {

	private static final String URL = "http://192.168.0.14:8080/PTSWebS/services/JogadorWS?wsdl";
	private static final String NAMESPACE = "http://ws.psurvivors.com";
	
	private static final String SAVE = "save";
	private static final String REMOVE = "remove";
	private static final String UPDATE = "update";
	private static final String LOGAR = "logar";
	private static final String GETSALT = "getSalt";
	private static final String GETTOKEN = "getToken";
	private static final String FINDBYNAME = "findByName";
	private static final String FINDBYID = "findById";
	private static final String FINDALL = "findAll";
	private static final String FINDALLJOGOS = "findAllJogos";
	private static final String FINDALLHABILIDADES = "findAllHabilidades";
	private static final String FINDALLCONQUISTAS = "findAllConquistas";
	
	public JogadorDAO(){}
	
	public int logar(String nome, String senha){
		
		SoapObject logar = new SoapObject(NAMESPACE, LOGAR);
		
		logar.addProperty("nome", nome);
		logar.addProperty("senha", senha);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.implicitTypes = true;
		envelope.setOutputSoapObject(logar);
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			
			http.call("urn:" + LOGAR, envelope);
			SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
			return Integer.parseInt(resposta.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Status.SEM_RESPOSTA;
		
	}
	
	public String getToken(String nome){
		
		SoapObject getToken = new SoapObject(NAMESPACE, GETTOKEN);
		
		getToken.addProperty("nome", nome);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.implicitTypes = true;
		envelope.setOutputSoapObject(getToken);
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			
			http.call("urn:" + GETTOKEN, envelope);
			SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
			return resposta.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
		
	}
	
	public String getSalt(String nome){
		
		SoapObject getSalt = new SoapObject(NAMESPACE, GETSALT);
		
		getSalt.addProperty("nome", nome);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.implicitTypes = true;
		envelope.setOutputSoapObject(getSalt);
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			
			http.call("urn:" + GETSALT, envelope);
			SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
			
			return resposta.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
		
	}
	
	public int save(String nome, String email, String senha, String tipo){
		
		SoapObject salvar = new SoapObject(NAMESPACE, SAVE);
		
		salvar.addProperty("nome", nome);
		salvar.addProperty("email", email);
		salvar.addProperty("senha", senha);
		salvar.addProperty("tipo", tipo);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.implicitTypes = true;
		envelope.setOutputSoapObject(salvar);
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			
			http.call("urn:" + SAVE, envelope);
			SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
			return Integer.parseInt(resposta.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Status.SEM_RESPOSTA;
		
	}
	
	public int remove(String token, String nome){
		
		SoapObject remover = new SoapObject(NAMESPACE, REMOVE);
		
		remover.addProperty("token", token);
		remover.addProperty("nome", nome);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.implicitTypes = true;
		envelope.setOutputSoapObject(remover);
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			
			http.call("urn:" + REMOVE, envelope);
			SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
			return Integer.parseInt(resposta.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Status.SEM_RESPOSTA;
		
	}
	
	public int update(String token, String nome, String email, String senha, String tipo, int moeda){
		
		SoapObject update = new SoapObject(NAMESPACE, UPDATE);
		
		update.addProperty("token", token);
		update.addProperty("nome", nome);
		update.addProperty("email", email);
		update.addProperty("senha", senha);
		update.addProperty("tipo", tipo);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.implicitTypes = true;
		envelope.setOutputSoapObject(update);
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			
			http.call("urn:" + UPDATE, envelope);
			SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
			return Integer.parseInt(resposta.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Status.SEM_RESPOSTA;
		
	}
	
	public Jogador findById(int id){

		SoapObject findById = new SoapObject(NAMESPACE, FINDBYID);
		
		findById.addProperty("id", id);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.implicitTypes = true;
		envelope.setOutputSoapObject(findById);
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			
			http.call("urn:" + FINDBYID, envelope);
			SoapObject resposta = (SoapObject) envelope.getResponse();
			return Convert.paraJogador(resposta);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public Jogador findByName(String name){
		
		SoapObject findByName = new SoapObject(NAMESPACE, FINDBYNAME);
		
		findByName.addProperty("name", name);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.implicitTypes = true;
		envelope.setOutputSoapObject(findByName);
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			
			http.call("urn:" + FINDBYNAME, envelope);
			SoapObject resposta = (SoapObject) envelope.getResponse();
			return Convert.paraJogador(resposta);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Jogador> findAll(){

		List<Jogador> jogadores = new ArrayList<Jogador>();
		
		SoapObject findAll = new SoapObject(NAMESPACE, FINDALL);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.implicitTypes = true;
		envelope.setOutputSoapObject(findAll);
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			
			http.call("urn:" + FINDALL, envelope);
			Vector<SoapObject> resposta = (Vector<SoapObject>) envelope.getResponse();
			
			for (SoapObject soapObject : resposta) {
				
				jogadores.add(Convert.paraJogador(soapObject));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return jogadores;
		
	}
	
	public List<Jogo> findAllJogos(String name){

		List<Jogo> jogos = new ArrayList<Jogo>();
		
		SoapObject findAllJogos = new SoapObject(NAMESPACE, FINDALLJOGOS);
		
		findAllJogos.addProperty("name", name);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.implicitTypes = true;
		envelope.setOutputSoapObject(findAllJogos);
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			
			http.call("urn:" + FINDALLJOGOS, envelope);
			Vector<SoapObject> resposta = (Vector<SoapObject>) envelope.getResponse();
			
			for (SoapObject soapObject : resposta) {
				
				jogos.add(Convert.paraJogo(soapObject));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return jogos;
		
	}
	
	public List<Habilidade> findAllHabilidades(String name){

		List<Habilidade> habilidades = new ArrayList<Habilidade>();
		
		SoapObject findAllHabilidades = new SoapObject(NAMESPACE, FINDALLHABILIDADES);
		
		findAllHabilidades.addProperty("name", name);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.implicitTypes = true;
		envelope.setOutputSoapObject(findAllHabilidades);
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			
			http.call("urn:" + FINDALLHABILIDADES, envelope);
			Vector<SoapObject> resposta = (Vector<SoapObject>) envelope.getResponse();
			
			for (SoapObject soapObject : resposta) {
				
				habilidades.add(Convert.paraHabilidade(soapObject));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return habilidades;
		
	}
	
	public List<Conquista> findAllConquistas(String name){
	
		List<Conquista> conquistas = new ArrayList<Conquista>();
		
		SoapObject findAllConquistas = new SoapObject(NAMESPACE, FINDALLCONQUISTAS);
		
		findAllConquistas.addProperty("name", name);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.implicitTypes = true;
		envelope.setOutputSoapObject(findAllConquistas);
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			
			http.call("urn:" + FINDALLCONQUISTAS, envelope);
			Vector<SoapObject> resposta = (Vector<SoapObject>) envelope.getResponse();
			
			for (SoapObject soapObject : resposta) {
				
				conquistas.add(Convert.paraConquista(soapObject));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return conquistas;
		
	}
	
}
