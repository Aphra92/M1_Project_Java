import java.lang.Math;
import java.util.ArrayList;

class Alignement {		
	
//----------------------
// variables d'instance 
//----------------------

	/**
     * Matrice de score pour créer l'alignement
	 **/
	 
	protected float[][] score;
	
	/**
     * Matrice de parcours pour créer l'alignement
	 **/
	 
	protected String[][] chemin;

	/**
     * Liste des séquences alignées
	 **/
	 
	protected ArrayList<Sequence> alignement;

	/**
     * Score de l'alignement
	 **/
	  
	protected int score_a;

//---------------------------
// constructeur
//---------------------------

//-------------------------
// methodes
//-------------------------
	 
	//abstract protected void Initializematrice();
	 
	//abstract protected void remplirmatrice();
	
	//abstract protected void alignement();
	
	/**
     * Récupère un score : 1 si les caractères sont égaux, 0 sinon
	 **/
	  
	protected float getScore(char BA1, char BA2) {
		if (BA1 != '-' && BA2 != '-'){
			if (BA1 == BA2) {
				 return 1;
			 }
			else return 0;
		}
		else return 0;
	 }

	/**
     * Récupère le score de l'alignement
     * Parcours la matrice de chemin et ajoute + 1 a chaque fois qu'elle change de case
     * (Bactracking)
	 **/
	 	 
	protected int getcpt(int i, int j) {
		int cpt = 0;
		while ( i != 0 || j!= 0 ) {
			if ( chemin[i][j] == "diag" ) {
				i--;
				j--;
				cpt ++;
			}
			else {
				if ( chemin [i][j]	== "gauche" ) {
					i--;
					cpt ++ ;
				}
				else /*( chemin [i][j] == "haut" )*/ {
					j--;
					cpt ++;
				}
			}
		}
		return cpt;
	}
	
	/**
     * Calcul le score en parcourant l'alignement (Implementer juste pour l'alignement simple)
	 **/
	 
	protected void calculate_score(int gap) {
		score_a = 0;
		for (int i = 0; i < getAlign().get(0).getCol().getsize(); i++) {
			if (alignement.get(0).getCol().getchar(i) == alignement.get(1).getCol().getchar(i)){
				score_a += 1;
			}
			if (alignement.get(0).getCol().getchar(i) == '-' || alignement.get(1).getCol().getchar(i) == '-'){
				score_a -= gap;
			}
		}
	}

	/**
     * Récupère l'alignement
	 **/
	 
	public ArrayList<Sequence> getAlign() {
		return alignement;
	}

	/**
     * Récupère le score de l'alignement
	 **/
	public int getScore_a() {
		return score_a;
	}

	/**
     * Récupère le nombre de séquence dans un alignement
	 **/
	 
	public int getnb() {
		return alignement.size();
	}

	/**
     * Récupère la taille en nombre de caractère de l'alignement
	 **/
	 
	public int getsize() {
		return alignement.get(0).getsize();
	}

	/**
     * Récupère une séquence de l'alignement
	 **/
	 	
	public Sequence getseq(int i) {
		return alignement.get(i);
	}

	/**
     * @Override toString
	 **/
	 
	public String toString() {
		StringBuilder Align = new StringBuilder();
		
		for(int j = 0; j < alignement.size(); j++){
			Align.append(alignement.get(j));
		}
		return Align.toString();
	}
}




