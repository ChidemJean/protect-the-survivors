package com.chidemgames.protectthesurvivors.daos.internal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {
	
	public static final String NOME_BANCO = "cadastroDB";
	public static final int VERSAO = 1;	
	
	private static SQLHelper instance = null;
	
	private SQLHelper(Context ctx) {
		super(ctx, NOME_BANCO, null, VERSAO);
	}
	
	public static SQLHelper getInstance(Context ctx) {
		if(instance==null) {
			instance = new SQLHelper(ctx);
		}
		return instance;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {		
		db.execSQL(JogadorDAO.SCRIPT_CRIACAO_USUARIO);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(JogadorDAO.SCRIPT_DROP_USUARIO);
		this.onCreate(db);
	}
}