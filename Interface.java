import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import java.io.File;
 
public class Interface extends JFrame implements ActionListener {

//----------------------
// variables d'instance 
//----------------------

	// Boutons a afficher dans la fenetre graphique
	private JButton select;
	private JButton aln;
	private JButton gap;	
	
	// Fenetres
	private FenetreGap askgap;
	private FenetreAlignement alignement;
	
	// Attributs pour la selection du fichier fasta
	private JFileChooser ask;
	private File fichier;

//---------------------------
// constructeur
//---------------------------

	public Interface(){
		super();
		//Appel la méthode pour initialiser la fenetre
		initialize(); 
	}
 
//-------------------------
// methodes
//-------------------------

	private void initialize(){
		setTitle("Alignement Multiple"); 		//Titre de l'application
		setSize(280,120); 						//Taille de la fenêtre
		setLocationRelativeTo(null); 			//On centre la fenêtre sur l'écran
		setResizable(true); 					//On autorise la redimensionnement de la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
		setContentPane(buildContentPane());		// Défini ce qu'il va y avoir dans la fenêtre
	}
	
	private JPanel buildContentPane(){
		// Crée un JPanel
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout()); 	// Défini un layout
		panel.setBackground(Color.white);	// Change la couleur du fond

		// Ajoute des boutons dans la fenêtre
		select = new JButton("Select");
		select.addActionListener(this);		// Ajoute un listener pour le bouton
		panel.add(select);					// Ajoute le bouton au panel
		gap = new JButton("Option");
		gap.addActionListener(this);
		panel.add(gap);
		gap.setEnabled(false);
		aln = new JButton("Alignement !");
		aln.addActionListener(this);
		panel.add(aln);
		aln.setEnabled(false);				// Le bouton alignement est désactiver par défault
		
		// Ajoute du texte dans la fenêtre
		JLabel label = new JLabel("by Hadrien Oubert & Ludivine Schuk");
		panel.add(label);
		
		return panel;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		
		if(source == select){
			// Ouvre un menu pour selectionner un fichier et suavegarde le fichier pour l'utiliser après
			ask = new JFileChooser(new File("."));
			ask.showOpenDialog(null);
			fichier = ask.getSelectedFile();
			gap.setEnabled(true);
		} 
		else {
			if(source == gap){
				askgap = new FenetreGap();
				askgap.setVisible(true);
				aln.setEnabled(true);
			}
			else {
				if (source == aln) {
					int gap = askgap.getgap();
					
					// Récupération des Séquences
					ParseFasta Arrayseq = new ParseFasta(fichier);
					//System.out.println(Arrayseq);
					
					// Test d'alignement type NWS entre deux sequences
					//Alignement test = new AlignementSimple(gap, Arrayseq.getSeq(20), Arrayseq.getSeq(19));
					//System.out.println(test);
					
					// Création d'un objet UPGM qui crée un arbre en fonction des scores d'alignement 2 à 2
					UPGMA upgma = new UPGMA (gap, Arrayseq);
					//System.out.println(upgma.getArbre());
					//upgma.getArbre().ParcoursPostfixe();
					
					// Récupération de l'arbre et parcours de l'arbre avec la fonction Align pour réaliser l'alignement multiple
					upgma.getArbre().Align(gap, Arrayseq);
					
					// Affichage de l'alignement multiple
					//System.out.println(upgma.getArbre().getAlignement());
					
					alignement = new FenetreAlignement(upgma);
				}
			}
		}
	}
}
