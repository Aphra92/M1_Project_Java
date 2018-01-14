import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.io.IOException;
import java.io.File;

public class ParseFasta {
	
//----------------------
// variables d'instance 
//----------------------

   /**
	* liste des séquences
	**/
	
    private ArrayList<Sequence> Arrayseq;

//---------------------------------------
// constructeur
//---------------------------------------
    
    /**
	 * Recoit un nom de fichier au format fasta, parcours le fichier et récupère chaque sequence avec son nom associé
	 * Les séquences sont stockées dans une Arraylist
	 **/
 
	public ParseFasta (File fichier) {
		BufferedReader lecteur = null;
		try {
			lecteur = new BufferedReader(new FileReader(fichier));
			String ligne;
			Arrayseq = new ArrayList<Sequence>();
			String name ="default";
			String sequence = "default";
			
			while ((ligne = lecteur.readLine())!=null) {
				boolean b = Pattern.matches("^>.*$", ligne);
				if (b) {	// Si c'est vrai, on est au niveau d'un chevron donc on mémorise le nom de la séquence
					name = ligne.substring(1);
				}
				else {
					sequence = ligne;
				}
				if (name.matches("default") == false && b == false) {
					Sequence seq;
					seq = new Sequence(name,sequence);
					Arrayseq.add(seq);
				}
			}
			lecteur.close();
		} catch(IOException e) {
			System.out.println("Fichier fasta non trouvé");		// Retourne une erreur si le nom indiqué ne correspond a aucun fichier présent dans le dossier
		}
  }

//---------------------------------------
// methodes
//---------------------------------------
	
	/**
	 * Retourne la liste des séquences
	 * @return la liste des séquences
	 **/

	public ArrayList<Sequence> getArrayseq() {  
		return Arrayseq;
	}
	
	/**
	 * Retourne la taille de la liste des séquences
	 * @return la liste des séquences
	 **/
	 
	public int getTaille() {  
		return Arrayseq.size();
	}
	 
	/**
	 * Retourne une séquence
	 * @return une séquence
	 **/
	 
	public Sequence getSeq(int num) {  
		return getArrayseq().get(num);
	}
	
	/**
	 * @Override toString
	 **/
	 
	public String toString() {
		StringBuilder Array = new StringBuilder();
		String NEW_LINE = System.getProperty("line.separator");
		
		for(int j = 0; j < Arrayseq.size(); j++){
			Array.append("Sequence à l'indice " + j + "\t   " + Arrayseq.get(j) + NEW_LINE);
		}
		return Array.toString();
	}
} 

		

