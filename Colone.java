import java.util.ArrayList;

public class Colone {
	
//----------------------
// variables d'instance 
//----------------------
    
	/**
	 * Nom de la colone
	 **/
	 
	private ArrayList<Character> colone;

//---------------------------------------
// constructeur
//---------------------------------------
	
	/**
	 * cree une Colone de caractère
	 * Recoit un string et construit l'arraylist de char
	 * @param colone, le nom de la colone
	 **/

	/* On parcours tout les éléments de la chaine de caractère et on les ajoute dans l'arrayliste */ 
	public Colone(String seq) {
		colone = new ArrayList<Character>();
		for (int i = 0; i < seq.length(); i++){	
			colone.add(seq.charAt(i));
		}
	}
	
	/**
	 * Copie la colone
	 **/

	public Colone(Colone seq) {
		colone = new ArrayList<Character>();
		for (int i = 0; i < seq.getsize(); i++){
			colone.add(seq.getchar(i));
		}
	}
	
	/**
	 * Initialise une colone vide
	 **/
	 
	public Colone() {
		colone = new ArrayList<Character>();
	}

//---------------------------------------
// methodes
//---------------------------------------
	
	/**
	 * Retourne un caractere de l'arraylist
	 **/
	 
	public char getchar(int i) {
		return colone.get(i);
	}
	
	/**
	 * Ajoute un caractère en position 0
	 **/
	 
	public void addchar(char i) {
		colone.add(0, i);
	}
	
	/**
	 * Retourne la taille de la colone
	 **/
	 
	 public int getsize() {
		return colone.size();
	}
	
	/**
	 * @Override toString pour afficher la colone sous la forme d'une chaine de caractère
	 **/
	
	public String toString() {
		StringBuilder seq = new StringBuilder();
		for (int i = 0; i <colone.size(); i++) { 
			seq.append(colone.get(i));
		}
		return seq.toString();
	}
}
