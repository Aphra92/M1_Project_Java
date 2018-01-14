import java.lang.Math;
import java.util.ArrayList;

// A deplacer avec le main
import java.lang.IndexOutOfBoundsException;
import javax.swing.*;

public class AlignementMulti extends Alignement {
	
//----------------------
// variables d'instance 
//----------------------

//---------------------------
// constructeur
//---------------------------
	
	/**
	 * Initialise (avec les valeurs de gap) et remplit les matrices score et chemin 
	 * et créer un alignement entre deux alignements
	 **/
	 
	public AlignementMulti(int gap, Alignement aln1, Alignement aln2) {
		alignement = new ArrayList<Sequence>();

		// Initialise les matrice de score et de chemin
		Initializematrice(gap, aln1, aln2);
		// Rempli la matrice de score et rempli la matrice chemin avec diag, gauche ou haut en fonction du score
		remplirmatrice(gap, aln1, aln2);

		// Aligne les deux séquences recus
		alignement(aln1, aln2);
	}
	
//-------------------------
// methodes
//-------------------------

	/**
	 * Initialise les matrices de score et de chemin
	 * On met les scores de gap sur la première ligne et colone de la matrice score et gauche ou haut pour la matrice chemin
	 **/

	private void Initializematrice(int gap, Alignement aln1, Alignement aln2){
		score = new float[aln1.getsize()+1][aln2.getsize()+1];
		chemin = new String[aln1.getsize()+1][aln2.getsize()+1];
		for ( int i = 0; i <= aln1.getsize(); i++ ) {
			score[i][0] = -i * gap;
			chemin[i][0] = "gauche";
		}
		for ( int j = 0; j <= aln2.getsize(); j++ ) {
			score[0][j] = -j * gap;
			chemin[0][j] = "haut";
		}
	 }
	 
    /**
	 * Rempli les matrice de score et de chemin
	 * Calcul un score entre les caractère d'une position et compare ce score avec le score de création de gap pour la position
	 **/
	 
	private void remplirmatrice(int gap, Alignement aln1, Alignement aln2) {
		// On remplit les matrices score et alignement
		float s = 0;
		for ( int i = 1; i <= aln1.getsize(); i++ ) {
			for ( int j = 1; j <= aln2.getsize(); j++ ) {
				for (int k = 0; k < aln1.getnb(); k++){
					for (int l = 0; l < aln2.getnb(); l++){
					s += getScore(aln1.getseq(k).getCol().getchar(i-1), aln2.getseq(l).getCol().getchar(j-1)); // Somme le score pour chaque combinaison de caractère de la position
					}
				}
				s = (s / (aln1.getnb() * aln2.getnb()));
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
			s = 0;
		}
		
	}

	/**
	 * Aligne les sequences et les ajoute a l'alignement
	 **/
	 	
		private void alignement(Alignement aln1, Alignement aln2) {
		int i = aln1.getsize();
		int j = aln2.getsize();
		
		// Initialise les séquences de l'alignement avec des colones nulles
		for (int x = 0; x < aln1.getnb(); x++){
			alignement.add(new Sequence(aln1.getseq(x).getName()));
		}
		for (int y = 0; y < aln2.getnb(); y++) {
			alignement.add(new Sequence(aln2.getseq(y).getName()));
		}
		
		int cpt = getcpt(i,j);
		
		// Pour chaque position, on ajoute un caractère dans la séquence qui correspond
		for (int k = cpt - 1; k >= 0; k--) {
			if ( chemin[i][j] == "diag" ) {
				for (int x = 0; x < aln1.getnb(); x++) {
					alignement.get(x).getCol().addchar(aln1.getseq(x).getCol().getchar(i-1));
				}
				int z = 0;
				for (int y = aln1.getnb(); y < aln1.getnb() + aln2.getnb(); y++) {
					alignement.get(y).getCol().addchar(aln2.getseq(z).getCol().getchar(j-1));
					z += 1;
				}				
				i--;
				j--;
			}
			else {
				if ( chemin [i][j]	== "gauche" ) {
					for (int x = 0; x < aln1.getnb(); x++){
						alignement.get(x).getCol().addchar(aln1.getseq(x).getCol().getchar(i-1));
					}
					for (int y = aln1.getnb(); y < aln1.getnb() + aln2.getnb(); y++) {
						alignement.get(y).getCol().addchar('-');
					}
					i--;
				}
				else {
					if ( chemin [i][j]	== "haut" ) {
						for (int x = 0; x < aln1.getnb(); x++){	
							alignement.get(x).getCol().addchar('-');
						}
						int z = 0;
						for (int y = aln1.getnb(); y < aln1.getnb() + aln2.getnb(); y++) {
							alignement.get(y).getCol().addchar(aln2.getseq(z).getCol().getchar(j-1));
							z += 1;
						}
						j--;
					}
				}
			}
		}
	}
	
//---------------------------------------
// MAIN
//---------------------------------------
	
	public static void main(String[] args) {
		// Test création de colone
		// Colone col = new Colone("atcgtcge");
		// System.out.println(col);

//////////////////////////////////////////////////////////////////////////////////////////////////////		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				//On crée une nouvelle instance de notre JDialog
				Interface fenetre = new Interface();
				fenetre.setVisible(true);		//On la rend visible
			}
		});
/////////////////////////////////////////////////////////////////////////////////////////////////////	
	}
}




