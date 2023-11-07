package com.data;

import java.util.ArrayList;
import java.util.List;

public class StopWord {
	public static ArrayList<String> SW = upload.file("data\\stopwords.txt");
	
	public static  ArrayList<String> Start(ArrayList<String> mots){
		           
		// supprimer les stop words et les stocker dans la liste NoStopWords
            List<String> NoStopWords = new ArrayList<String>() ;
			
			for (String mot : mots) {
				if (! SW.contains(mot)) {
					NoStopWords.add(mot);
				}
			}
			
	        ArrayList<String> resultat = new ArrayList<String>();
	        
	        for (String mot : NoStopWords) {
	            resultat.add(mot);
	            
	        }
	        
	        

      return resultat ;
	}
	

}
