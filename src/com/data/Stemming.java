package com.data;


import org.tartarus.snowball.ext.englishStemmer;

public class Stemming {
	
    public static StringBuilder stemText(String text) {
    	englishStemmer stemmer = new englishStemmer(); 
    	StringBuilder TextStem = new StringBuilder();
    	
    	String[] mots = text.split("\\s+");
    	
    	for ( String mot: mots) {
    	
    		stemmer.setCurrent(mot);
        	stemmer.stem();
        	String stemmedWord = stemmer.getCurrent();
        	TextStem.append( stemmedWord  + " ");
        	  
    	}
    	
    	return TextStem;
    }
    
    
    
    public static String StemMOt(String mot) {
    	englishStemmer stemmer = new englishStemmer(); 
    	stemmer.setCurrent(mot);
    	stemmer.stem();
    	return stemmer.getCurrent();
    }

}
