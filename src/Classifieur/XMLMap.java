package Classifieur;


import java.util.Map;
import java.util.HashMap;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.Map.Entry;

public class XMLMap {
	
	
	
	
	public static void saveClassProba(Map<String, Double> classProba, String filePath) {
        Properties properties = new Properties();
        classProba.forEach((key, value) -> properties.setProperty(key, String.valueOf(value)));

        try (OutputStream output = new FileOutputStream(filePath)) {
            properties.storeToXML(output, "Map Data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }
	
	
	
	
    public static Map<String, Double> loadClassProba(String filePath) {
        Map<String, Double> map = new HashMap<>();

        try (InputStream input = new FileInputStream(filePath)) {
            Properties properties = new Properties();
            properties.loadFromXML(input);

            properties.forEach((key, value) -> map.put((String) key, Double.parseDouble((String) value)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
    
    

 // Save et Loade pour Naive Bayes

    public static void saveNB(Map<String, Map<String, Double>> data, String filepath) throws IOException {
		
		try (FileWriter fileWriter = new FileWriter(new String(filepath))){
			
			fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + System.lineSeparator());
	        fileWriter.write("<Probabilite>" + System.lineSeparator());
	
	        for (java.util.Map.Entry<String, Map<String, Double>> classEntry : data.entrySet()) {
	            String className = classEntry.getKey();
	            Map<String, Double> probabilities = classEntry.getValue();
	
	            fileWriter.write("    <Class name=\"" + className + "\">" + System.lineSeparator());
	
	            for (Entry<String, Double> wordEntry : probabilities.entrySet()) {
	                String word = wordEntry.getKey();
	                Double probability = wordEntry.getValue();
	
	                fileWriter.write("        <word name=\"" + word + "\">" + probability + "</word>"
	                        + System.lineSeparator());
	            }
	
	            fileWriter.write("    </Class>" + System.lineSeparator());
	        }
	
	        fileWriter.write("</Probabilite>");
	        
	  	}
		
	}
	
	
	
	public static Map<String, Map<String, Double>> loadNB(String FilePath)  {
		System.out.println("Chargement des donnees ...");
	    Map<String, Map<String, Double>> wordProbabilities = new HashMap<>();
	
	    try {
	        File xmlFile = new File(FilePath);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(xmlFile);
	        doc.getDocumentElement().normalize();
	
	        NodeList classNodes = doc.getElementsByTagName("Class");
	
	        for (int temp = 0; temp < classNodes.getLength(); temp++) {
	            Node classNode = classNodes.item(temp);
	
	            if (classNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element classElement = (Element) classNode;
	                String className = classElement.getAttribute("name");
	
	                Map<String, Double> wordProbabilitiesForClass = new HashMap<>();
	                NodeList wordNodes = classElement.getElementsByTagName("word");
	
	                for (int i = 0; i < wordNodes.getLength(); i++) {
	                    Node wordNode = wordNodes.item(i);
	
	                    if (wordNode.getNodeType() == Node.ELEMENT_NODE) {
	                        Element wordElement = (Element) wordNode;
	                        String word = wordElement.getAttribute("name");
	                        double probability = Double.parseDouble(wordElement.getTextContent());
	                        wordProbabilitiesForClass.put(word, probability);
	                    }
	                }
	
	                wordProbabilities.put(className, wordProbabilitiesForClass);
	            }
	        }
	        System.out.println("Chargement avec succees");
	        
	    }catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return wordProbabilities;
	
	    
	}
		
	
	// Save et Loade pour KNN
	
	public static void saveKNN(Map<String, Map<String, Integer>> data, String filepath) throws IOException {
		
		try (FileWriter fileWriter = new FileWriter(new String(filepath))){
			
			fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + System.lineSeparator());
	        fileWriter.write("<Occurence>" + System.lineSeparator());
	
	        for (java.util.Map.Entry<String, Map<String, Integer>> classEntry : data.entrySet()) {
	            String className = classEntry.getKey();
	            Map<String, Integer> Ocurances = classEntry.getValue();
	
	            fileWriter.write("    <Class name=\"" + className + "\">" + System.lineSeparator());
	
	            for (Entry<String, Integer> wordEntry : Ocurances.entrySet()) {
	                String word = wordEntry.getKey();
	                int ocurence = wordEntry.getValue();
	
	                fileWriter.write("        <word name=\"" + word + "\">" + ocurence + "</word>"
	                        + System.lineSeparator());
	            }
	
	            fileWriter.write("    </Class>" + System.lineSeparator());
	        }
	
	        fileWriter.write("</Occurence>");
	        
	  	}
		
	}
 
	
	
	
	
	public static Map<String, Map<String, Integer>> loadKNN(String FilePath)  {
		System.out.println("Chargement des donnees ...");
	    Map<String, Map<String, Integer>> wordOcurances = new HashMap<>();
	
	    try {
	        File xmlFile = new File(FilePath);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(xmlFile);
	        doc.getDocumentElement().normalize();
	
	        NodeList classNodes = doc.getElementsByTagName("Class");
	
	        for (int temp = 0; temp < classNodes.getLength(); temp++) {
	            Node classNode = classNodes.item(temp);
	
	            if (classNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element classElement = (Element) classNode;
	                String className = classElement.getAttribute("name");
	
	                Map<String, Integer> wordOcuranceForClass = new HashMap<>();
	                NodeList wordNodes = classElement.getElementsByTagName("word");
	
	                for (int i = 0; i < wordNodes.getLength(); i++) {
	                    Node wordNode = wordNodes.item(i);
	
	                    if (wordNode.getNodeType() == Node.ELEMENT_NODE) {
	                        Element wordElement = (Element) wordNode;
	                        String word = wordElement.getAttribute("name");
	                        int occurance = Integer.parseInt(wordElement.getTextContent());
	                        wordOcuranceForClass.put(word, occurance);
	                    }
	                }
	
	                wordOcurances.put(className, wordOcuranceForClass);
	            }
	        }
	        System.out.println("Chargement avec succees");
	        
	    }catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return wordOcurances;
	
	    
	}
		
	
    
}
