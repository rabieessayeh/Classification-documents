import java.io.IOException;
import java.util.NavigableMap;
import java.util.Scanner;

import com.data.upload;

import Classifieur.KNN;
import Classifieur.NaiveBayes;
import metric.CroseValidation;

public class app {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {   
		
		while(true) {
			System.out.println("\n\n\t\t\tChoisit une option : \n");
			System.out.println("Test KNN : 1 \t  Test Naive Bayes : 2 \t Validation Croiser : 3 \n");
			System.out.println("\t\t\t0 pour quiter ");
			int choix = new Scanner(System.in).nextInt();
			
			if (choix == 1) {
				KNN.Test();	
			}
			
			if (choix == 2) {
				NaiveBayes.Test();
			}
			
			if (choix == 3) {
				CroseValidation.Start();
			}
			
			if (choix == 0) {
				System.out.println("A bien tot :)");
				break;
			}
			
			
		}
		
		
	}


}


