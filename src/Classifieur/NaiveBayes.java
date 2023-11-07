package Classifieur;

import com.data.StopWord;
import com.data.upload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;



public class NaiveBayes {

	public static Map<String, Map<String, Double>> data = XMLMap.loadNB("maps\\NB_data.xml");
	public static  Map<String, Double> ClassPro = XMLMap.loadClassProba( "maps\\NB_Class_data.xml" );
	
	
	
	@SuppressWarnings("resource")
	public static void Test() throws IOException {
		
		if(data == null || data.isEmpty()) {
			
			System.out.println("Donner le chemin de dossier pour entrainer le module: ");
			String chemin = new Scanner(System.in).nextLine();
			
			data = Feet(chemin);
			XMLMap.saveNB(data, "maps\\NB_data.xml");
		}
		
		System.out.println("Donner le chemin fichier : ");
		String cheminf = new Scanner(System.in).nextLine();
		
		String ClassPredit = predictClass(data, cheminf);
		
		System.out.println("Mon model dis que c'est : "+ClassPredit);
		
	}
	
	
	
	

	@SuppressWarnings("resource")
	public static Map<String, Map<String, Double>> Feet(ArrayList<String> train) throws IOException {
		Map<String, Map<String, Double>> Obj;
    	
    	 Map<String, StringBuilder> MapOcc = upload.MotsParClass(train);

    	 
    	 Map<String, ArrayList<String>> MapMots = new HashMap<String, ArrayList<String>>();
    	 
    	 for (Entry<String, StringBuilder> mc : MapOcc.entrySet()) {
    		 
    		 String[] mots = new String(mc.getValue()).split(" ");
     		 ArrayList<String> list = new ArrayList<>();
     		 for( String mot: mots) {
     			list.add(mot);
     		 }
     		 
     		// Suppression des stop words
     		MapMots.put(mc.getKey(),StopWord.Start(list) ) ;
    	 }     	 
    	 
    	 Obj = ProbabiliteMots(MapMots);
    	 
    	 return Obj;
		
	}
	
	
	
	@SuppressWarnings("resource")
	public static Map<String, Map<String, Double>> Feet(String chemin) throws IOException {
		Map<String, Map<String, Double>> Obj;
		
		Map<String, ArrayList<String>> files = upload.ClassFilesPathe(chemin);
		ArrayList<String> Paths = new ArrayList<String>();
		
		for (String Idx : files.keySet()) {
			Paths.addAll(files.get(Idx));
		}
    	
    	 Map<String, StringBuilder> MapOcc = upload.MotsParClass(Paths);
    	 
    	 Map<String, ArrayList<String>> MapMots = new HashMap<String, ArrayList<String>>();
    	 
    	 for (Entry<String, StringBuilder> mc : MapOcc.entrySet()) {
    		 
    		 String[] mots = new String(mc.getValue()).split(" ");
     		 ArrayList<String> list = new ArrayList<>();
     		 for( String mot: mots) {
     			list.add(mot);
     		 }
     		 
     		// Suppression des stop words
     		MapMots.put(mc.getKey(),StopWord.Start(list) ) ;
    	 }
    	 
    	 Obj = ProbabiliteMots(MapMots);
    	 
    	 return Obj;
		
	}
	
	
	
	
    @SuppressWarnings("resource")
	public static  String predictClass(Map<String, Map<String, Double>> data, String chemin) throws IOException {
		ArrayList<String> mots = upload.file(chemin);

		mots = StopWord.Start(mots);
    	
    	double maxProbability = Double.NEGATIVE_INFINITY;
        String predictedClass = null;
        

        
                
        for (Map.Entry<String, Map<String, Double>> classEntry : data.entrySet()) {
            String className = classEntry.getKey();
            Map<String, Double> classData = classEntry.getValue();
            
         // Probabilité de la classe

            double classProbability = ClassPro.get(className); 

            for (String word : mots) {
                if (classData.containsKey(word)) {
                    classProbability *= classData.get(word);
                }
            }

            if (classProbability > maxProbability) {
                maxProbability = classProbability;
                predictedClass = className;
            }
        }
        return predictedClass;
    }
 
    
    
    
	public static Map<String,Double> Occurance(List<String> listeDeMots){
	    Map<String, Double> occurrenceMap = new HashMap<>();
	    
	    for (String mot : listeDeMots) {
	    	
	        Double occurrence = occurrenceMap.getOrDefault(mot,  (double) 0);
	        occurrenceMap.put(mot, (occurrence + 1));
	        
	    } 

	    return occurrenceMap;
	}
	
	
	
	
	
	public static Map<String,Double> Pro(List<String> listeDeMots){
	    
	    List<String> MotsUniques = upload.RecuppererMotsUniques("maps\\MotsUniques");
	    int M = MotsUniques.size();
	    int N = listeDeMots.size();
	  

	    
	    Map<String, Double> occurrenceMap = Occurance(listeDeMots);
	    Map<String, Double> MapPro = new HashMap<>();
	    
	    // Calcule de Probabilite P_W_C = ( Nk + 1 ) / ( N + M )
	    for (String mot : MotsUniques) {
	    	double Nk = 0;
	    	if(occurrenceMap.containsKey(mot)) {
	    		Nk = occurrenceMap.get(mot);	
	    	}
	    	
	    	double proba = (Nk + 1) / (M + N);
	    	MapPro.put(mot, proba);
	    	
	    }
	    
	    return MapPro;
	}
	
	
	
	
	
	public static Map<String, Map<String, Double>> ProbabiliteMots( Map<String, ArrayList<String>> data) throws IOException{
		Map<String, Map<String, Double>> pro = new HashMap<String, Map<String,Double>>();

		System.out.println("Calcul de probabilitee");

		for(Entry<String, ArrayList<String>> en: data.entrySet()) {
			
			pro.put(en.getKey(), Pro(en.getValue()));
			
		}
		
		return pro;
	}

}
