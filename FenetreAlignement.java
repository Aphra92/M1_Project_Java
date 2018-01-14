import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.text.*;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter.HighlightPainter;

public class FenetreAlignement extends JFrame {

//----------------------
// variables d'instance 
//----------------------	
	
	String align;
	StyledDocument document;
	JTextPane textPane;
	
//---------------------------
// constructeur
//---------------------------

	public FenetreAlignement(UPGMA upgma) {
		getToStringAlignement(upgma);
		makedocument();
		initialize();
	}

//-------------------------
// methodes
//-------------------------
	
	private void getToStringAlignement(UPGMA upgma) {
		// Récupération de l'alignement sous forme de string
		Alignement aln = upgma.getArbre().getAlignement();
		align = aln.toString();
	}
	
	private void makedocument() {
		// Crée un tableau pour comparer a chaque caractère
        String[] lettersToEdit = new String[]{"A", "T", "C", "G"};
        
        // Crée une arraylist de caractère pour récupéré tous les caractères de align
        ArrayList<String> strings = new ArrayList<>();
        
        // Recupère le texte de align et le met dans l'arrayList
        for (int i = 0; i < align.length(); i++) {
            strings.add(align.charAt(i) + "");
        }
        
        // Crée un document
		document = new DefaultStyledDocument();
		
		// Crée un JTextPane
		textPane = new JTextPane(document);
		
		// Styles par default
		Style racine = textPane.getStyle("default");
		StyleConstants.setFontFamily(racine, "Courier"); 
		StyleConstants.setFontSize(racine, 16);
		StyleConstants.setAlignment(racine, StyleConstants.ALIGN_RIGHT);
		
        try {
		  document.insertString(document.getLength(), align, racine);
		} catch (BadLocationException badLocationException) {
		  System.err.println("Bad insert");
		}
        
        // Crée des surligneurs
        Highlighter h = textPane.getHighlighter();
        DefaultHighlightPainter painterA = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
        DefaultHighlightPainter painterT = new DefaultHighlighter.DefaultHighlightPainter(Color.cyan);
		DefaultHighlightPainter painterC = new DefaultHighlighter.DefaultHighlightPainter(Color.green);
		DefaultHighlightPainter painterG = new DefaultHighlighter.DefaultHighlightPainter(Color.yellow);
		
		// Parcours le string et surligne la position qui correspond dans le document
		int position = 0;
		for (String s1 : strings) {				
			for (String s2 : lettersToEdit) {
				if (s2.equals(s1)) {	
					if (s1.equals(lettersToEdit[0])) {
						try {
							h.addHighlight(position, position+1, painterA);
							} catch (BadLocationException ble) {
								//
							}
                    }
                    if (s1.equals(lettersToEdit[1])) {
						try {
							h.addHighlight(position, position+1, painterT);
							} catch (BadLocationException ble) {
								//
							}
                    }
                    if (s1.equals(lettersToEdit[2])) {
						try {
							h.addHighlight(position, position+1, painterC);
							} catch (BadLocationException ble) {
								//
							}
                    }
                    if (s1.equals(lettersToEdit[3])) {
						try {
							h.addHighlight(position, position+1, painterG);
							} catch (BadLocationException ble) {
								//
							}
                    }
                }
            }
            position++;
        }
	}
	
	private void initialize() {
        // Création de la frame
        setTitle(" Résultats alignement multiple ");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		// Crée un Jpanel
		JPanel noWrapPanel = new JPanel( new BorderLayout() );
		// Empeche le wrap sur le jpanel
		noWrapPanel.add(textPane);
		JScrollPane scrollPane = new JScrollPane(noWrapPanel);
		
		// Position par defaut
		textPane.setCaretPosition(0);
		// Ajout du textpanel
		noWrapPanel.add(textPane);
        // Ajout ScrollPane
        add(scrollPane, BorderLayout.CENTER);
        
        textPane.setEditable(false);
        setSize(800,500); 							
		setLocationRelativeTo(null); 
		setResizable(true); 
		setVisible(true);		
	}					
}
