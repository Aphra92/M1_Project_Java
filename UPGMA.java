import java.util.ArrayList;

public class UPGMA {
	
//----------------------
// variables d'instance 
//----------------------
    
    /**
     * matrice de distance
	 **/
	
	private float[][] similarite;
	 
	/**
	 * Arbre représentant l'ordre des séquences à aligner en parcours postfix
	 **/
	  
	private Arbre arbre;
	
	
	/**
	 * Liste représentant l'ordre des séquences
	 **/
	
	private ArrayList<Integer> list;
	
//---------------------------
// constructeur
//---------------------------
      
	public UPGMA(int gap, ParseFasta Arrayseq) {
		// Initialise la matrice de similarité
		iniSimilarite(gap, Arrayseq);
		// Construit la liste qui va servir a construire l'arbre
		list = new ArrayList<Integer>();
		 /* Construction de la liste en parcourant la matrice de similarité
			A chaque tour de boucle, on cherche le max dans la matrice et on récupère les numéros de séquence correspondante */
		while (list.size() < ((Arrayseq.getTaille())*2)-2){
			float max = 0;
			float min =  -10000000;
			int seq1 = -1;
			int seq2 = -1;
			int seq1min = -1;
			int seq2min = -1;
			int seq1max = -1;
			int seq2max = -1;
			for (int i = 0 ; i < Arrayseq.getTaille(); i++ ) {
				for (int j = i+1; j < Arrayseq.getTaille(); j++ ) {
					if (similarite[i][j] > min && similarite[i][j] < 0) {
						min = similarite[i][j];
						seq1min = i;
						seq2min = j;
						
					}
					if (similarite[i][j] > max) {
						max = similarite[i][j];
						seq1max = i;
						seq2max = j;
					}
				}
			}
			if (max != 0){
				seq1 = seq1max;
				seq2 = seq2max;
			}
			else {
				seq1 = seq1min;
				seq2 = seq2min;
			}
			
		// Ajout des numeros des séquences avec le max dans la liste pour construire l'arbre
			list.add(seq1);
			list.add(seq2);
		/*
		 * Modification de la matrice pour passer à l'étape suivante
		 * On regroupe les seq1 et seq2 dans la colone de la seq1
		 * Et on re calcul les distances avec les autres colonnes.
		 */ 
			//showM();
			update_similarite(Arrayseq, seq1, seq2);
			
		}
		createguide();
	}
	
//---------------------------------------
// methodes
//---------------------------------------	
	/**
	 * Initialise la matrice de similarité
	 **/
	 
	private void iniSimilarite(int gap, ParseFasta Arrayseq) {
		similarite = new float[Arrayseq.getTaille()][Arrayseq.getTaille()];
		  
		// Initialisation de la matrice à 0
		for ( int k = 0; k < Arrayseq.getTaille(); k++) {
			for ( int m = 0; m < Arrayseq.getTaille(); m++ ) {
				similarite[k][m] = 0;
			}
		}
		  
		// Remplissage de la moitiée de la matrice avec les scores de similarité 
		for ( int i = 0 ; i < Arrayseq.getTaille(); i++ ) {
			for (int j = i+1; j < Arrayseq.getTaille(); j++ ) {
				Alignement align = new AlignementSimple(gap, Arrayseq.getSeq(i), Arrayseq.getSeq(j));
				similarite[i][j] = align.getScore_a();
			}
		}
	}

	/**
	 * Met à jour la matrice de similarité
	 **/	
	
	private void update_similarite(ParseFasta Arrayseq, int seq1, int seq2){
		float a = 0;
		float b = 0;
		for (int i = 0 ; i < Arrayseq.getTaille(); i++ ) {
			if (similarite[seq1][i] != 0 ) {
				a = similarite[seq1][i];
			}
			else {
				a = similarite[i][seq1];
			}
			if ( similarite[seq2][i] != 0 ){
				b = similarite[seq2][i];
			}
			else {
				b = similarite[i][seq2];
			}
			if (similarite[i][seq2] != 0) {
				similarite[i][seq2] = (float)( a + b ) / 2;
			}
			if (similarite[seq2][i] != 0) {
				similarite[seq2][i] = (float)( a + b ) / 2;
			}
		}
		for (int i = 0 ; i < Arrayseq.getTaille(); i++ ) {
			similarite[seq1][i] = 0;
			similarite[i][seq1] = 0;
		}
	}
	
	/**
	 * Crée l'arbre guide
	 **/
	 
	private void createguide() {
		// Initialisation de l'arbre
		arbre = new Arbre(-1);
		arbre.setGauche(new Arbre(list.get(list.size() - 1)));
		arbre.setDroit(new Arbre(list.get(list.size() - 2)));
		
		// Remplissage de l'arbre
		int id = 3;
		while (id <= list.size()){
			arbre.RemplirValInt(list.get(list.size() - id), list.get(list.size() - id - 1) );
			id = id + 2;
		}
	}
	 
	/**
	 * Retourne l'arbre UPGMA
	 **/
	 
	public Arbre getArbre() {
		return arbre;
	}
	
	/**
	 * Retourne la taile de la liste
	 **/
	 
	public int getTaille() {
		return list.size();
	}
	
	//////////////////////////////////////////
				// A SUPPRIMER //
	/**
	 * Permet d'afficher les matrices qui se construise
	 **/
	 
	public void showM() {
		for (int i = 0 ; i < similarite.length; i++ ) {
			for (int j = 0; j < similarite.length;j++ ) {
				String a = String.format("%3.3f | \t", similarite[i][j]);
				System.out.print(a);
				}
			System.out.print("\n"); 
		}
		System.out.print("\n");
			
		//Afficher la liste qui garde l'ordre des alignements
		for(int j = 0; j < list.size(); j++){
			System.out.println("donnée à l'indice " + j + " = " + list.get(j).toString());
		}
		System.out.print("\n");
	}
 }
      

