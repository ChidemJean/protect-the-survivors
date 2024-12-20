package com.chidemgames.protectthesurvivors.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class LevenshteinDistance {  
	
	private static Map<Integer, ArrayList<String>> comandos;
	private static ArrayList<String> words;
	
	private static LinkedList<String> copyFila;
	private static LinkedList<String> tempFila;
	
	private static int currentWords = 0;
	private static int comandExist = 0;
	
	public static void prepareDistanceOfString(ArrayList<String> palavras, Map<Integer, ArrayList<String>> comandosMap, LinkedList<String> fila) {	
		tempFila = new LinkedList<String>();
		currentWords = 0;
		comandExist = 0;
		comandos = comandosMap;
		words = palavras;
		copyFila = fila;
		tempFila.addAll(fila);
	}
	
	public LevenshteinDistance(){}
	
	public static int getNumWords(){
		return currentWords;
	}
	
	public static void initializeMatcher() {
											
		
		LinkedList<String> queue = new LinkedList<String>();

		for (String word : words) {
			
			queue.addAll(copyFila);
			
			String[] listWords = word.split(", |\\ |\\. |\\.|\\,");
									
			int numWordsRecognized = 0;
			
			for (String wordUnique: listWords){
				Set<Integer> chaves = comandos.keySet(); 
				Iterator<Integer> iterator = chaves.iterator();
		        while (iterator.hasNext()) {  
		            Integer chave = iterator.next();
		            if(chave != null)  {
		            	ArrayList<String> lis = comandos.get(chave);
		            	for (String string : lis) {
							double distance = computeLevenshteinDistance(wordUnique, string);
							if (distance <= (string.length() * 0.4)) {
								numWordsRecognized++;
								insertOnQueue(string, wordUnique, chave, lis.indexOf(string), queue);
							}
						}
		            	lis = null;
		            }
		        }  
		        iterator = null;
		        chaves = null;
			}		
			
			if (currentWords < numWordsRecognized) {
				currentWords = numWordsRecognized;
				tempFila.clear();
				tempFila.addAll(queue);
			}
			queue.clear();
		}
		
		copyFila = null;
		copyFila = tempFila;
		tempFila = null;
		
		
	}
	
	public static void insertOnQueue(String command, String word, Integer chave, int index, LinkedList<String> fila){
		
		String commandId = chave +"-"+ index;
	
		if(!fila.isEmpty()){
			String lastComandoId = fila.getLast();
			String[] comandoLast = lastComandoId.split("-");
			int lastKey = Integer.parseInt(comandoLast[0]);
			int lastIndex = Integer.parseInt(comandoLast[1]);
			boolean exist = fila.contains(commandId);

			if (exist) {
				comandExist += 1;
			}else if( lastKey == chave - 1 || chave == 1 ) {
				fila.offer(commandId);
			}
		}else {
			if(chave==1) {
				fila.offer(commandId);
			}
		}
	}
	
	public static LinkedList<String> getQueue(){
		return copyFila;
	}
	
	public static int getComandsExists(){
		return comandExist;
	}
	
    private static int minimum(int a, int b, int c) {                            
        return Math.min(Math.min(a, b), c);                                      
    }                                                                            
                                                                                 
    public static int computeLevenshteinDistance(String str1,String str2) {      
        int[][] distance = new int[str1.length() + 1][str2.length() + 1];        
                                                                                 
        for (int i = 0; i <= str1.length(); i++)                                 
            distance[i][0] = i;                 
        
        for (int j = 1; j <= str2.length(); j++)                                 
            distance[0][j] = j;                   
                                                                                 
        for (int i = 1; i <= str1.length(); i++)                                 
            for (int j = 1; j <= str2.length(); j++)                             
                distance[i][j] = minimum(                                        
                        distance[i - 1][j] + 1,                                  
                        distance[i][j - 1] + 1,                                  
                        distance[i - 1][j - 1] + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1));
        
        return distance[str1.length()][str2.length()];                           
    }                                                                            
}
