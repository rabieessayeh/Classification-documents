package com.data;

public class Progres {
	
    public static void Afficher(int currentStep, int totalSteps) {
        int barLength = 50; // Longueur de la barre de progression
        int progress = (int) ((double) currentStep / totalSteps * barLength);

        StringBuilder progressBar = new StringBuilder("Progress: [");
        
        for (int i = 0; i < barLength; i++) {
            if (i < progress) {
                progressBar.append("=");
            } else {
                progressBar.append(" ");
            }
        }
        
        progressBar.append("] " + (int)(((double)currentStep / totalSteps) * 100) + "%");

        System.out.print("\r" + progressBar.toString());
        
        if (currentStep == totalSteps) {
            System.out.println(); // Ajoute un saut de ligne à la fin de la progression.
        }
    }

}
