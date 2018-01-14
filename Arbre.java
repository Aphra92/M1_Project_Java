import java.util.ArrayList;

public class Arbre {
	
//----------------------
// variables d'instance 
//----------------------

    /**
     * Valeur du noeud de l'arbre
	 **/
	 
    private int valeur;
 
    /**
     * Fils gauche
	 **/
	    
    private Arbre gauche;
 
	/**
     * Fils droit
	 **/
	    
    private Arbre droit;

    /**
     * Alignement du noeud de l'arbre
	 **/
	     
    private Alignement alignement;

//---------------------------------------
// constructeur
//---------------------------------------

    /**
     * Construit un Arbre en assignant une valeur int
	 **/
	    
    public Arbre(int x) {
        valeur = x;
    }

//---------------------------------------
// methodes
//---------------------------------------

    /**
     * Récupère la valeur de l'arbre
	 **/
	 	
    public int getValeur() {
        return(valeur);
    }

    /**
     * Permet d'acceder à l'arbre Gauche
	 **/
	 
    public Arbre getSousArbreGauche() {
        return(gauche);
    }   

    /**
     * Permet d'acceder à l'arbre Droit
	 **/
	 
    public Arbre getSousArbreDroit() {
        return(droit);
    }

    /**
     * Récupère l'alignement de l'arbre
	 **/
	     
    public Alignement getAlignement() {
		return alignement;
	}

    /**
     * Récupère l'alignement de Gauche
	 **/
	 	
	public Alignement getAlignGauche() {
		return getSousArbreGauche().getAlignement();
	}

    /**
     * Récupère l'alignement de Droite
	 **/
	 	
	public Alignement getAlignDroit() {
		return getSousArbreDroit().getAlignement();
	}

    /**
     * Place l'arbre a gauche
	 **/
	     
    public void setGauche (Arbre a) {
		gauche = a;
    }

    /**
     * Place l'arbre a Droite
	 **/
	     
    public void setDroit (Arbre a) {
		droit = a;
    }

    /**
     * Rempli les feuilles de l'arbre avec des alignements composés d'une seule sequence
	 **/
	 
    public void setAlignement(Sequence seq1) {
		alignement = new AlignementSimple(seq1);
	}

    /**
     * Remplit l'alignement de l'arbre avec un alignement multiple
	 **/
	 
	public void setAlignement(int gap, Alignement Align1, Alignement Align2) {
		alignement = new AlignementMulti(gap, Align1, Align2);
	}

    /**
     * Remplit l'alignement de l'arbre avec un alignement simple
	 **/
	 	
	public void setAlignementS(int gap, Alignement Align1, Alignement Align2) {
		alignement = new AlignementSimple(gap, Align1, Align2);
	}

    /**
     * Rempli l'arbre selon les valeurs de la liste UPGMA
	 **/
	  
	public void RemplirValInt(int val1, int val2) {
		if (getSousArbreGauche() != null)
			getSousArbreGauche().RemplirValInt(val1, val2);
		if (getSousArbreDroit() != null)
			getSousArbreDroit().RemplirValInt(val1, val2);
		if (getValeur() == val1 && getSousArbreGauche() == null && getSousArbreDroit() == null) {
			setGauche(new Arbre(val1));
			setDroit(new Arbre(val2));
		} 
	}

    /**
     * Parcours l'arbre en postfix
     * Si feuille => on appel setAlignement pour mettre la sequence qui correspond a la valeur
     * Si noeud entre deux feuille ==> on appel SetAlignementS pour faire un alignement Needleman-Wunsch
     * Sinon on fait un alignement multiple entre les deux noeuds fils
	 **/
	 
	public void Align(int gap, ParseFasta Arrayseq) {
		if (getSousArbreGauche() != null)
			getSousArbreGauche().Align(gap, Arrayseq);
		if (getSousArbreDroit() != null)
			getSousArbreDroit().Align(gap, Arrayseq);
		if (getSousArbreGauche() == null && getSousArbreDroit() == null) {
			setAlignement(Arrayseq.getSeq(getValeur()));
		}
		if (getSousArbreGauche() != null && getSousArbreDroit() != null) {
			if (gauche.getSousArbreGauche() == null && gauche.getSousArbreDroit() == null && droit.getSousArbreGauche() == null && droit.getSousArbreDroit() == null){
				setAlignementS(gap, getAlignDroit(), getAlignGauche());
			}
			else {
				setAlignement(gap, getAlignDroit(), getAlignGauche());
			}
		} 
	}

	/**
     * Affiche l'arbre selon un parcours postfixe
     **/
     
	public void ParcoursPostfixe() {
		if (getSousArbreGauche() != null)
			getSousArbreGauche().ParcoursPostfixe();
		if (getSousArbreDroit() != null)
			getSousArbreDroit().ParcoursPostfixe();
		System.out.println(getValeur());
    }
    
	/**
	 * @Override toString
	 **/
	public String toString() {
        return toString("\t");
    }
    
	public String toString(String s) {
		if (gauche!=null) {
			if (droit!=null) 
				return(s+valeur+"\n"+gauche.toString(s+"\t")+droit.toString(s+"\t"));
			else
				return(s+valeur+"\n"+gauche.toString(s+"\t")+"\n");
		}
        else 
			if (droit!=null) 
				return(s+valeur+"\n\n"+droit.toString(s+"\t"));
		else
			return(s+valeur+"\n");
    }
} 

