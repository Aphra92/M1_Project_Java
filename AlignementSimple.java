import java.lang.Math;
import java.util.ArrayList;

public class AlignementSimple extends Alignement {
	
//----------------------
// variables d'instance 
//----------------------

	/**
     * Score de l'alignement
	 **/
	  
	protected int score_a;
	
//---------------------------
// constructeur
//---------------------------
      
	/**
	 * Ajoute une séquence comme étant un alignement 
	 **/
	public AlignementSimple(Sequence seq1) {
		alignement = new ArrayList<Sequence>();
		alignement.add(seq1);
	}
	
	/**
	 * Initialise (avec les valeurs de gap) et remplit les matrices score et chemin 
	 * et créer un alignement entre deux séquences
	 **/
	 
	public AlignementSimple(int gap, Sequence seq1, Sequence seq2) {
		alignement = new ArrayList<Sequence>();
		// Initialise les matrice de score et de chemin
		Initializematrice(gap, seq1, seq2);
		// Rempli la matrice de score et rempli la matrice chemin avec diag, gauche ou haut en fonction du score
		remplirmatrice(gap, seq1, seq2);
		// Aligne les deux séquences recus
		alignement(seq1, seq2);
		// Calcul le score de l'alignement
		calculate_score(gap);
	}
	
	/**
	 * Initialise (avec les valeurs de gap) et remplit les matrices score et chemin 
	 * et créer un alignement entre deux alignements composés d'une seule séquence
	 **/
	 
	public AlignementSimple(int gap, Alignement aln1, Alignement aln2) {
		alignement = new ArrayList<Sequence>();
		// Récupère les seq de l'alignement
		Sequence seq1 = aln1.getseq(0);
		Sequence seq2 = aln2.getseq(0);
		// Initialise les matrice de score et de chemin
		Initializematrice(gap, seq1, seq2);
		// Rempli la matrice de score et rempli la matrice chemin avec diag, gauche ou haut en fonction du score
		remplirmatrice(gap, seq1, seq2);
		// Aligne les deux séquences recus
		alignement(seq1, seq2);
		// Calcul le score de l'alignement
		calculate_score(gap);
	}

//-------------------------
// methodes
//-------------------------

	/**
	 * Initialise les matrices de score et de chemin
	 * On met les scores de gap sur la première ligne et colone de la matrice score et gauche ou haut pour la matrice chemin
	 **/
	 
	private void Initializematrice(int gap, Sequence seq1, Sequence seq2){
		score = new float[seq1.getsize()+1][seq2.getsize()+1];
		chemin = new String[seq1.getsize()+1][seq2.getsize()+1];
		
		for ( int i = 0; i <= seq1.getsize(); i++ ) {
			score[i][0] = -i * gap;
			chemin[i][0] = "gauche";
		}
		for ( int j = 0; j <= seq2.getsize(); j++ ) {
			score[0][j] = -j * gap;
			chemin[0][j] = "haut";
		}
	 }
	 
    /**
	 * Rempli les matrice de score et de chemin
	 * Calcul un score entre les caractère d'une position et compare ce score avec le score de création de gap pour la position
	 **/
	 
	private void remplirmatrice(int gap, Sequence seq1, Sequence seq2) {
		float s;
		for ( int i = 1; i <= seq1.getsize(); i++ ) {
			for ( int j = 1; j <= seq2.getsize(); j++ ) {
				
				s = getScore(seq1.getCol().getchar(i-1), seq2.getCol().getchar(j-1));
				
				score[i][j] = Math.max(score[i-1][j-1] + s , Math.max(score[i-1][j] - gap, score[i][j-1] - gap));

				if ( score[i][j] == score[i-1][j-1] + s ) {
					chemin[i][j] = "diag";
				}
				else {
					if ( score[i][j] == score[i-1][j] - gap ) {
						chemin[i][j] = "gauche";
					}
					else {
						chemin[i][j] = "haut";
					}
				}
			}
		}
	}

	/**
	 * Aligne les sequences et les ajoute a l'alignement
	 **/
	 	
	private void alignement(Sequence seq1, Sequence seq2) {
		Colone col1 = seq1.getCol();
		Colone col2 = seq2.getCol();
		
		int i = col1.getsize();
		int j = col2.getsize();
		
		// Initialise le compteur
		int cpt = getcpt(i,j);

		StringBuilder chain1 = new StringBuilder();
		StringBuilder chain2 = new StringBuilder();
		
		for (int k = cpt - 1; k >= 0; k--) {
			if ( chemin[i][j] == "diag" ) {
				chain1.insert(0, col1.getchar(i-1));
				chain2.insert(0, col2.getchar(j-1));
				i--;
				j--;
			}
			else {
				if ( chemin [i][j]	== "gauche" ) {
					chain1.insert(0, col1.getchar(i-1));
					chain2.insert(0, '-');
					i--;
				}
				else {
					if ( chemin [i][j]	== "haut" ) {
						chain1.insert(0, '-');
						chain2.insert(0, col2.getchar(j-1));
						j--;
					}
				}
			}
		}
		alignement.add(new Sequence(seq1.getName(),chain1.toString()));
		alignement.add(new Sequence(seq2.getName(),chain2.toString()));
	}
}




