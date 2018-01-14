import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FenetreGap extends JFrame implements ActionListener {

//----------------------
// variables d'instance 
//----------------------

	private JTextField valeur;
	private Integer gap;
	private JLabel label;
	private JButton askgap;

//---------------------------
// constructeur
//---------------------------

	public FenetreGap(){
		super();
		build(); // On initialise notre fenêtre
	}

//-------------------------
// methodes
//-------------------------

	private void build(){
		setTitle("Entrez une valeur de gap !"); 	// Titre de la fenêtre
		setSize(320,100); 							// Taille
		setLocationRelativeTo(null); 				// On centre la fenêtre sur l'écran
		setResizable(false); 						// On ne permet pas le redimensionnement
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // On dit à l'application de se fermer lors du clic sur la croix
		setContentPane(buildContentPane());
	}
	
	private JPanel buildContentPane() {
		JPanel panel = new JPanel();		// Crée un panel pour la fenêtre
		panel.setLayout(new BorderLayout());
		valeur = new JTextField();			// Crée un champs de texte
		valeur.setColumns(4); 				// Taille de la zone de texte
		valeur.setText("10");
		panel.add(valeur, BorderLayout.WEST);
		label = new JLabel("GAP");
		panel.add(label, BorderLayout.CENTER);
		askgap = new JButton("Valider");
		askgap.addActionListener(this);
		panel.add(askgap, BorderLayout.EAST);
		return panel;
	}
	
	public Integer getgap(){
		return gap;
	}
	
	public JLabel getLabel(){
		return label;
	}
	
	public void actionPerformed(ActionEvent e) {
		gap = Integer.parseInt(valeur.getText());
		super.dispose(); // Cache la fenetre quand on clique sur valider
	}
}
