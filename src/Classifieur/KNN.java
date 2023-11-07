package Classifieur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import com.data.StopWord;
import com.data.upload;



public class KNN {
	public static Map<String, Map<String, Integer>> data = XMLMap.loadKNN("maps\\\\KNN_data.xml");
		
	@SuppressWarnings("resource")
	public static void Test() throws IOException {
		if(data == null || data.isEmpty()) {
			System.out.println("Donner le chemin de dossier pour entrainer le module: ");
			String chemin = new Scanner(System.in).nextLine();
			
			Map<String, ArrayList<String>> files = upload.ClassFilesPathe(chemin);
			ArrayList<String> Paths = new ArrayList<String>();
			
			for (String Idx : files.keySet()) {
				Paths.addAll(files.get(Idx));
			}
			data = Feet(Paths);
			XMLMap.saveKNN(data, "maps\\KNN_data.xml");
		}
		
		System.out.println("Donner le chemin de fichier : ");
		String cheminf = new Scanner(System.in).nextLine();
		
		String ClassPredit = Predict(data, cheminf);
		
		System.out.println("Mon model dis que c'est : "+ClassPredit);
		
	}
	
	
	
	@SuppressWarnings("resource")
	public static Map<String, Map<String,Integer>> Feet(ArrayList<String> train) throws IOException {
		Map<String, Map<String, Integer>> Obj;
    	
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
    	 
    	 Obj = CalculeOcurence(MapMots);
    	 
    	 return Obj;
	}
	
		
	
	
	public static Map<String,Integer> Occurance(ArrayList<String> listeDeMots){
	    Map<String, Integer> occurrenceMap = new HashMap<>();
	    
	    for (String mot : listeDeMots) {
	    	
	        int occurrence = occurrenceMap.getOrDefault(mot,   0);
	        occurrenceMap.put(mot, (occurrence + 1)); 
	    } 
	    return occurrenceMap;
	}
	
	
	
	
	public static Map<String, Map<String, Integer>> CalculeOcurence(Map<String, ArrayList<String>> MotsParClass) {
		
		Map<String, Map<String, Integer>> MapOcc = new HashMap<String, Map<String,Integer>>();

		for ( Entry<String, ArrayList<String>> en : MotsParClass.entrySet() ) {
			MapOcc.put(en.getKey(), Occurance(en.getValue()));
		}
		
		return MapOcc;
		
	}
	
	
	
	
	
    public static double cosines(Map<String, Integer> v1, Map<String, Integer> v2) {
        double dotProduct = 0.0;
        double magnitudeV1 = 0.0;
        double magnitudeV2 = 0.0;
        
        

        for (String term : v1.keySet()) {
            if (v2.containsKey(term)) {
                dotProduct += v1.get(term) * v2.get(term);
            }
            magnitudeV1 += Math.pow(v1.get(term), 2);
        }

        for (String term : v2.keySet()) {
            magnitudeV2 += Math.pow(v2.get(term), 2);
        }

        if (magnitudeV1 == 0.0 || magnitudeV2 == 0.0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(magnitudeV1) * Math.sqrt(magnitudeV2));
    }
	
	

    public static  String Predict(Map<String, Map<String, Integer>> data, String TestF) {
    	Map<String,Double> fileNames = new HashMap<String, Double>();
    	
		ArrayList<String> mots = upload.file(TestF);

		mots = StopWord.Start(mots);
		

        for (String fileName : data.keySet()) {
            Map<String, Integer> fileVector = data.get(fileName);
            
            double similarity = cosines(Occurance(mots), fileVector);
            fileNames.put(fileName, similarity);
        }

     // Triez la liste en fonction des valeurs décroissantes
        
        List<Map.Entry<String, Double>> list = new ArrayList<>(fileNames.entrySet());
        
        Collections.sort(list, (entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()));
        String ClassPredit= null;
        for (Map.Entry<String, Double> entry : list) {
        	ClassPredit = entry.getKey();
        	break;
        }
        
        return ClassPredit;
         
    }
	

}
