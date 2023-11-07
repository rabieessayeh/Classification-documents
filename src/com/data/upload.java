package com.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Classifieur.XMLMap;


public class upload {
	
	
	
	public static void saveMotsUniques(ArrayList<String> mots, String fileName) {
		
		System.out.println("Traitement des mots uniques ...");
		
		
	    try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
	    	
			for (String mot : mots) {
				writer.print(mot+" ");
					
				}

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	     
	}
	
	
	
    public static List<String> RecuppererMotsUniques(String filePath) {
        List<String> wordList = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineWords = line.split("\\s+");
                
                for (String word : lineWords ) {
                	wordList.add(word);
                	
                }
                
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordList;
    }
    


    // Chargement des donnees
	public static Map<String, StringBuilder> MotsParClass(ArrayList<String> traine) {
        File[] dossier = new File[traine.size()];
        for (int k = 0; k <traine.size(); k++) {
        	dossier[k] = new File(traine.get(k));
        }

        System.out.println("Chargement ...");
        int NBR_Fichier = dossier.length;
        Map <String, Integer> cls = new HashMap<String, Integer>();
        Map <String, StringBuilder> data = new HashMap<String, StringBuilder>();
        ArrayList<String> MotsUniques = new ArrayList<String>();

        
        for (File file : dossier) {

        	String fName = file.getName();
        	
        	if(fName.split("__").length >= 2) {
        		String cla = fName.split("__")[1]; // nom de class
        	        	
	        	StringBuilder content = new StringBuilder("");
	        	
	        	// calculer le nombre de fichier dans la class
	    		int occurrence = cls.getOrDefault(cla, 0);
	    		cls.put(cla,  (occurrence + 1));
	        		       		
	        	
	        	try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	                String line;
	                while ((line = br.readLine()) != null) {
	
	                	String Texte = line.replaceAll("[\\[\\]{}&$#%+=`!?^\"\\_~'.:><)(;*/,|-]", "").toLowerCase();
	                	Texte = Texte.replaceAll("[\\x00-\\x1F]", "");
	                	Texte = new String(Stemming.stemText(Texte));
	                	content.append( Texte+" ");
 
	
	                }
	            } catch (IOException e) {
	                System.err.println("Erreur de lecture du fichier: " + fName);
	                e.printStackTrace();
	            }
	        	
	        	//Traitement des mots uniques
	        	String[] mots = new String(content).split("\\s+");
	        	for (String mot : mots) {
	        		if( ! MotsUniques.contains(mot)) {
	        			MotsUniques.add(mot);
	        		}
	        	}
	        	
	               	
	        	// Garder les mots de chque class
	        	StringBuilder Con = data.getOrDefault(cla, new StringBuilder(""));
	        	data.put(cla, Con.append(content));
        	}
        }
                	
        
        // Calcule de probabilite pour chaque class
       Map<String, Double> ClassProba = new HashMap<String, Double>();
       for ( Entry<String, Integer> en : cls.entrySet()) {
    	   ClassProba.put(en.getKey(), (double) en.getValue()/NBR_Fichier );
       	}
    	   
        XMLMap.saveClassProba(ClassProba, "maps\\NB_Class_data.xml");
        
        saveMotsUniques(MotsUniques, "maps\\MotsUniques");
              
        System.out.println("Chargement avec succes ");
        return data;
	}

	
	

	
	public static Map<String, ArrayList<String>> ClassFilesPathe(String chemin){
		Map<String, ArrayList<String>> data = new HashMap<String, ArrayList<String>>();
				
		File[] dossiers = new File(chemin).listFiles();

		for (File dossier: dossiers) {
			String Class = dossier.getName();
			ArrayList<String> paths = new ArrayList<String>();
			for (File fichier : dossier.listFiles()) {
				String path = fichier.getAbsolutePath();
				paths.add(path);
			}
			data.put(Class, paths);
		}
		
		
		
		
		return data;
	}


		
	
	
	public static ArrayList<String> file(String chemin) {
		ArrayList<String> mots = new ArrayList<String>();
	
	    try {
	        BufferedReader br = new BufferedReader(new FileReader(chemin));
	        
	        String line;
	        while ((line = br.readLine()) != null) {
	        	line = line.replaceAll("[\\[\\]{}&$#%+=`!?^\"\\_~'.:><)(;*/,|-]", "").toLowerCase();
	        	line = line.replaceAll("[\\x00-\\x1F]", "");
	        	line = new String(Stemming.stemText(line));
	        	
	        	String[] lineWords = line.split("\\s+");
	            
	            for (String word : lineWords ) {
	            	mots.add(word);
	            	
	            }
	            
	        }
	
	        br.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return mots;
	}






}