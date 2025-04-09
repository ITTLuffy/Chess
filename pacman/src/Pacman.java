import javax.swing.JPanel;
import javax.swing.JFrame;

public class Pacman {

    public static void main(String[] args) {

        // dim gioco
        int righe = 21;
        int colonne = 19;

        int quadretto = 32; // pixel
        int w = colonne * quadretto;
        int h = righe * quadretto;

        JFrame f = new JFrame("Pac man");

        // risoluzione
        f.setSize(w, h);

        // "location"
        f.setLocationRelativeTo(null);

        // fatto che non si possa modificare la risoluzione
        f.setResizable(false);

        // come chiudere e fermare il programma
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // panel
        Canvas c = new Canvas();
        // lo aggiungo al frame
        f.add(c);

        f.pack(); // sistemo la size se necessario

        // rendo visibile
        f.setVisible(true);

    }

}
