package com.chidemgames.protectthesurvivors.daos.external;

import java.io.IOException;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.chidemgames.protectthesurvivors.entities.Jogo;
import com.chidemgames.protectthesurvivors.utils.Convert;
import com.chidemgames.protectthesurvivors.utils.Status;

public class JogoDAO {

	private static final String URL = "http://192.168.0.14:8080/PTSWebS/services/JogoWS?wsdl";
	private static final String NAMESPACE = "http://ws.psurvivors.com";
	
	private static final String SAVE = "save";
	private static final String REMOVE = "remove";
	private static final String UPDATE = "update";
	private static final String FINDBYID = "findById";
	private static final String FINDALLCONQUISTAS = "findAllConquistas";
	private static final String FINDALLCENAS = "findAllCenas";
	private static final String FINDALLHABILIDADES = "findAllHabilidades";
	
	public int save(String token, String nome, int xp, int pontuacao, int level, String tipoJogo){
		
		SoapObject salvar = new SoapObject(NAMESPACE, SAVE);
		
		salvar.addProperty("token", token);
		salvar.addProperty("nome", nome);
		salvar.addProperty("xp", xp);
		salvar.addProperty("pontuacao", pontuacao);
		salvar.addProperty("level", level);
		salvar.addProperty("tipoJogo", tipoJogo);
		
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
	
	public int remove(String token, int id){
		
		SoapObject remover = new SoapObject(NAMESPACE, REMOVE);
		
		remover.addProperty("token", token);
		remover.addProperty("id", id);
		
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

	public int update(String token, int id, int xp, int pontuacao, int level){
		
		SoapObject atualizar = new SoapObject(NAMESPACE, UPDATE);
		
		atualizar.addProperty("token", token);
		atualizar.addProperty("id", id);
		atualizar.addProperty("xp", xp);
		atualizar.addProperty("pontuacao", pontuacao);
		atualizar.addProperty("level", level);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.implicitTypes = true;
		envelope.setOutputSoapObject(atualizar);
		
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
	
	public Jogo findById(int id){
		
		SoapObject byId = new SoapObject(NAMESPACE, FINDBYID);
		
		byId.addProperty("id", id);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.implicitTypes = true;
		envelope.setOutputSoapObject(byId);
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			
			http.call("urn:" + FINDBYID, envelope);
			SoapObject resposta = (SoapObject) envelope.getResponse();
			return Convert.paraJogo(resposta);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}
	
}
