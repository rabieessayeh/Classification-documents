package metric;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

import com.data.upload;

import Classifieur.KNN;
import Classifieur.NaiveBayes;


public class CroseValidation {
	
	public static String chemin = "data\\origine";
	public static String destination = "data\\CroseValidation";
	public static int NbrFolder = 5;
	
	
	@SuppressWarnings({ "resource", "removal" })
	public static void Start() throws IOException {
		System.out.println("Donne moi le chemin de dossier");
		chemin = new Scanner(System.in).nextLine();
		
		System.out.println("Donne moi le nom du model NB ou KNN");
		String Model = new Scanner(System.in).nextLine();
		
		System.out.println("Donne moi le nombre de folder");
		NbrFolder = new Scanner(System.in).nextInt();
		
		Map<String, ArrayList<String>> ClassFilePath = upload.ClassFilesPathe(chemin);
		Map<Integer, ArrayList<String>> Folder = splitCrossValidation(ClassFilePath, NbrFolder);
		
        ArrayList<String> Actuell = new ArrayList<String>();
        ArrayList<String> Predit = new ArrayList<String>();
		ArrayList<Double> F1Score = new ArrayList<>();
		
		//Entrainement et test
		for (int i : Folder.keySet()) {
			ArrayList<String> Train = new ArrayList<String>();
			ArrayList<String> Test = Folder.get(i);
			
			for (int j : Folder.keySet()) {
				if(j != i) {
					Train.addAll(Folder.get(j));
				}
			}

			System.out.println("=======    Folder  "+new Integer(i+1)+" de "+Folder.size()+" ========");
			
			if(Model.equals("NB")) {
		        Map<String, Map<String, Double>> Prob = NaiveBayes.Feet(Train);

		        for (String TestF : Test) {
		        	String ClassPredit = NaiveBayes.predictClass(Prob, TestF);
		        	String ClassReel = TestF.split("__")[1];
		        	
		        	Actuell.add(ClassReel);
		        	Predit.add(ClassPredit);
		        }
		        
		        // F1 Score
		        
		        double F1 = Evaluation.calculateF1Score(Actuell, Predit);
		        F1Score.add(F1);
			
			
			
			}else if (Model.equals("KNN")) {
				
				Map<String, Map<String, Integer>> data = KNN.Feet(Train);
				
				for (String TestF : Test) {
		        	String ClassPredit = KNN.Predict(data, TestF);
		        	String ClassReel = TestF.split("__")[1];
		        	
		        	Actuell.add(ClassReel);
		        	Predit.add(ClassPredit);
		        }
		        
				
		        // F1 Score
		        double F1 = Evaluation.calculateF1Score(Actuell, Predit);
		        F1Score.add(F1);
				
			}

		}
		
		//Affichage de resultat
		Double Moyenne = 0.0;
		for (int i = 0; i< NbrFolder; i++) {
			Moyenne += F1Score.get(i);
			System.out.print("F"+i+" : "+F1Score.get(i)+"\t");
		}
		System.out.println();
		System.out.println("Le Moyenne de F1 Score : "+ Moyenne/NbrFolder);
	
	}
	
	
	
    public static Map<Integer, ArrayList<String>> splitCrossValidation(Map<String, ArrayList<String>> dataMap, int numFolds) {
        Map<Integer, ArrayList<String>> crossValidationData = new HashMap<>();

        // Initialize random number generator
        Random rand = new Random();

        for (int i = 0; i < numFolds; i++) {
            crossValidationData.put(i, new ArrayList<>());
        }

        for (Map.Entry<String, ArrayList<String>> entry : dataMap.entrySet()) {
            ArrayList<String> files = entry.getValue();

            for (String file : files) {
                int foldIndex = rand.nextInt(numFolds); // Randomly assign to a fold
                crossValidationData.get(foldIndex).add(file);
            }
        }

        return crossValidationData;
    }
	
	

}
