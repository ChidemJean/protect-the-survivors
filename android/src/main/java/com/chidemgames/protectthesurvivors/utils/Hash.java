package com.chidemgames.protectthesurvivors.utils;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Base64;

public class Hash {

	public static String saltMaisSenhaCript(String senha, String salt){
		try {
			MessageDigest d = MessageDigest.getInstance("SHA-256");
			try {
				String senhaSalt = salt + senha;
				byte[] dBy = senhaSalt.getBytes("UTF-8");
				senhaSalt = Base64.encodeToString(dBy, Base64.DEFAULT);
				return senhaSalt;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String randomSALT() {

		String lista = new String();
		String salt = new String();

		for (int i = 65; i < 91; i++) {
			char a = (char) i;
			lista += String.valueOf(a);
		}
		for (int i = 48; i < 58; i++) {
			char a = (char) i;
			lista += String.valueOf(a);
		}
		for (int i = 0; i < 13; i++){
			int r = (int)(Math.random() * lista.length());
			int to = 1 + (int)(Math.random() * 2);
			if (to == 1){
				salt += String.valueOf(lista.charAt(r)).toLowerCase();
			}else {
				salt += lista.charAt(r);
			}
		}

		System.out.println("Salt: " + salt);

		return salt;

	}

}
