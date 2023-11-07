package com.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Preparer {
	
	@SuppressWarnings("resource")
	public static void coupie(String input, String output) throws IOException {
		File in = new File(input);
		File out = new File(output);
				
		try {
			
			FileWriter fw = new FileWriter(out);
			PrintWriter pw = new PrintWriter(fw);
			Scanner scan = new Scanner(in);
			while(scan.hasNextLine()) {
				String texte = scan.nextLine();
				pw.println(texte);
			}
			pw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
    @SuppressWarnings("unused")
	public static String coupier(String sourceDirectory, String destinationDirectory, int i) throws IOException  {
    	
    	File folder = new File(sourceDirectory);
    	while(new File(destinationDirectory+i).exists()) {
    		i++;
    	}
    	File distination = new File(destinationDirectory+i);
    	distination.mkdir();
    	File[] dossiers = folder.listFiles();
    	
    	for (File dossier:dossiers) {
    		String D_Name = dossier.getName();
    		File[] files = dossier.listFiles();
    		for (File file : files) {
    			
    			String from = sourceDirectory+"\\"+D_Name+"\\"+file.getName();
    			String to = destinationDirectory+(i-1)+"\\"+file.getName();
    			coupie(from,to );
    		}
    	}
    	return destinationDirectory+(i-1);
    }
    	
    	  
    
    
    public static void renommerEtDeplacerFichiers(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
                    
            File[] SousDossier = folder.listFiles(File::isDirectory);
            
            for (File dossier : SousDossier) {
            	String DossierName = dossier.getName();
            	
            	File[] fichiers = dossier.listFiles();
            	
            	for (File fichier : fichiers) {
            		
            		// Renomer 
            		String newName = fichier.getName() +"__" +DossierName ;
            		if(! new File(newName).exists()) {
	                	File newFile = new File(dossier, newName);
	               		 if (fichier.renameTo(newFile)) {
	                            System.out.println("Renamed: " + fichier.getName() + " to " + newFile.getName());
	                        } else {
	                            System.err.println("Failed to rename: " + fichier.getName());
	                        }
            		}else {
                    	System.out.println("Deja renomer");
                    }
            		 

                     
            		
            	}
            }
            
        } else {
            System.err.println("Le dossier spécifié n'existe pas ou n'est pas un dossier.");
        }
    } 
    
    
    
    
    
    public static void renommerEtDeplacerFichiers(String folderPath, int i) throws IOException {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] subfolders = folder.listFiles(File::isDirectory);
            
            for (int subfolderIndex = 0; subfolderIndex < subfolders.length; subfolderIndex++) {
                File subfolder = subfolders[subfolderIndex];
                File[] filesInSubfolder = subfolder.listFiles();
                int numFilesInSubfolder = filesInSubfolder.length;
                
                int filesPerSubfolder = numFilesInSubfolder / i;
                int remainingFiles = numFilesInSubfolder % i;
                
                for (int j = 1; j <= i; j++) {
                    int filesToRename = (j <= remainingFiles) ? (filesPerSubfolder + 1) : filesPerSubfolder;
                    
                    for (int k = 0; k < filesToRename; k++) {
                        File file = filesInSubfolder[(j - 1) * filesPerSubfolder + k];
                        String FName = file.getName();
                        String newName = FName+"__" + subfolder.getName() + "__" + j;
                        // Renomer 
                        if(! new File(newName).exists()) {
                        	File newFile = new File(subfolder, newName);
                            
                            if (file.renameTo(newFile)) {
                                System.out.println("Renamed: " + file.getName() + " to " + newFile.getName());
                            } else {
                                System.err.println("Failed to rename: " + file.getName());
                            }
                        }else {
                        	System.out.println("Deja renomer");
                        }
                    }
                }
            }
        } else {
            System.err.println("Le dossier spécifié n'existe pas ou n'est pas un dossier.");
        }
    }
	
	
}
