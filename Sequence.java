import java.util.ArrayList;

public class Sequence {
	
//----------------------
// variables d'instance 
//----------------------
    
	/**
	 * Nom de la sequence
	 **/
	
	private String name;

	/**
	 * Stock la chaine de caractère correspondant à la séquence sous forme d'un objet colone
	 **/
	
	private Colone seq;
	
//---------------------------------------
// constructeur
//---------------------------------------

    /**
	 * Cree une Sequence
	 * Recoit le nom et la séquence sous forme de string et transforme la seq en colone
	 **/
	 
	public Sequence(String name, String seq) {
		this.name = name;
		this.seq = new Colone(seq);
	}
	
	/**
	 * Crée une séquence avec juste un nom et une colone vide
	 **/
	 
	public Sequence(String name) {
		this.name = name;
		this.seq = new Colone();
	}
	
	/**
	 * Cree une nouvelle instance de sequence par copie
	 **/
	 
	public Sequence(Sequence seq) {
		this.name = seq.getName();
		this.seq = new Colone(seq.getCol());
	}
	
//---------------------------------------
// methodes
//---------------------------------------
	
	/**
	 * Restitue le nom de la séquence
	 * @return le nom de la séquence
	 **/
	
	public String getName(){
		return name;
	}

	/**
	 * Restitue la colone de la séquence
	 * @return la colone
	 **/
	
	public Colone getCol(){
		return seq;
	}
	
	/**
	 * Restitue la taille de la colone
	 **/
	
	public int getsize(){
		return seq.getsize();
	}

	/**
	 * @Override toString
	 **/
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		String NEW_LINE = System.getProperty("line.separator");
		
			string.append(name + "\t" + seq  + NEW_LINE);

		return string.toString();
	}
}
	




