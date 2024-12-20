package com.chidemgames.protectthesurvivors.daos.internal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.chidemgames.protectthesurvivors.entities.Jogador;

public class JogadorDAO {
	
	public static final String NOME_TABELA = "usuario";
	public static final String COLUNA_CODIGO = "usuario_id";	
	public static final String COLUNA_NOME = "usuario_nome";	
	public static final String COLUNA_SEXO = "usuario_sexo";	
	public static final String COLUNA_DT_NASC = "usuario_dt_nasc";
	
	
	public static final String SCRIPT_CRIACAO_USUARIO = "CREATE TABLE "+NOME_TABELA+" ( "
	+COLUNA_CODIGO+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
	+COLUNA_NOME+" TEXT, "
	+COLUNA_SEXO+" TEXT, "
	+COLUNA_DT_NASC+" TEXT);";
	
	public static final String SCRIPT_DROP_USUARIO = "DROP TABLE IF EXISTS "+NOME_TABELA+";";

	private SQLiteDatabase bd;
	
	private static JogadorDAO instance;
	
	public JogadorDAO(Context ctx) {
		bd = SQLHelper.getInstance(ctx).getWritableDatabase();
	}
	
	public static JogadorDAO getInstance(Context ctx) {
		if(instance==null) {
			instance = new JogadorDAO(ctx);
		}
		return instance;		
	}

	public void salvar(Jogador usuario) {
		ContentValues values = this.criarValues(usuario);		
		bd.insert(NOME_TABELA, null, values);
	}
	
	public void atualizar(Jogador usuario) {
		ContentValues values = this.criarValues(usuario);
		String where = JogadorDAO.COLUNA_CODIGO+"= ?";
		String[] whereValues = {usuario.getIdJogador()+""};
		bd.update(NOME_TABELA, values, where, whereValues);
	}
	
	public void excluir(Jogador usuario) {
		String where = this.COLUNA_CODIGO + "=?";
		String id = String.valueOf(usuario.getIdJogador());
		String[] whereArgs = new String[] { id };
		bd.delete(this.NOME_TABELA, where, whereArgs);
	}
	
	public List<Jogador> listar() {
		List<Jogador> listaUsuarios = new ArrayList<Jogador>();
		String consulta = "SELECT * FROM " + NOME_TABELA + ";";
		Cursor c = bd.rawQuery(consulta, null);
		
		Jogador user;
		if(c.moveToFirst()) {
			do{
				user = new Jogador();
				user.setNome(c.getString(c.getColumnIndex(COLUNA_NOME)));
				String dataStr = c.getString(c.getColumnIndex(COLUNA_DT_NASC));
				listaUsuarios.add(user);				
			}while(c.moveToNext());
			
		}		
		
		return listaUsuarios;		
	}
	
	private ContentValues criarValues(Jogador usuario) {
		ContentValues values = new ContentValues();
		values.put(COLUNA_NOME, usuario.getNome());
	
		return values;
	}	
	
	private String dateToString(Date data) {
		SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
		String dataFormatada = format.format(data);
		Log.i("DATA String", dataFormatada);
		return dataFormatada;		
	}
	
	private Date stringToDate(String dataStr) {
		SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
		Date dataFormatada = new Date();
		try {
			dataFormatada = format.parse(dataStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dataFormatada;
	}
}
