import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Timer;
import javax.swing.JPanel;

public class Canvas extends JPanel {

    // matrice x scachiera
    private int[][] scacchiera;
    // margini della scacchiera
    private int margine_sopra = 55;
    private int margine_lati = 17;
    // dimensione della cella
    private int dimCella = 80;
    // arrayList x i pezzi
    private ArrayList<Pawn> pedoni;
    private ArrayList<King> re;
    private ArrayList<Bishop> alfieri;
    private ArrayList<Knight> cavalli;
    private ArrayList<Rook> torri;
    private ArrayList<Queen> regine;
    private Timer t;


    public Canvas() {
        scacchiera = new int[8][8];
        pedoni = new ArrayList<>();
        re = new ArrayList<>();
        alfieri = new ArrayList<>();
        cavalli = new ArrayList<>();
        torri = new ArrayList<>();
        regine = new ArrayList<>();

        int p = 6;
        pedoni.add(new Pawn(true, p, 0));  // pedone bianco
        pedoni.add(new Pawn(true, p, 1));  // pedone bianco
        pedoni.add(new Pawn(true, p, 2));  // pedone bianco
        pedoni.add(new Pawn(true, p, 3));  // pedone bianco
        pedoni.add(new Pawn(true, p, 4));  // pedone bianco
        pedoni.add(new Pawn(true, p, 5));  // pedone bianco
        pedoni.add(new Pawn(true, p, 6));  // pedone bianco
        pedoni.add(new Pawn(true, p, 7));  // pedone bianco

        int p1 = 1;
        pedoni.add(new Pawn(false, p1, 0)); // pedone nero
        pedoni.add(new Pawn(false, p1, 1)); // pedone nero
        pedoni.add(new Pawn(false, p1, 2)); // pedone nero
        pedoni.add(new Pawn(false, p1, 3)); // pedone nero
        pedoni.add(new Pawn(false, p1, 4)); // pedone nero
        pedoni.add(new Pawn(false, p1, 5)); // pedone nero
        pedoni.add(new Pawn(false, p1, 6)); // pedone nero
        pedoni.add(new Pawn(false, p1, 7)); // pedone nero

        re.add(new King(false, 0, 4)); // re nero
        re.add(new King(true, 7, 4)); // re bianco
        
        pedoni.add(new Pawn(false, p1, 7)); // regina nero
        pedoni.add(new Pawn(false, p1, 7)); // regina bianco

        alfieri.add(new Bishop(false, 0, 2)); // alfiere nero
        alfieri.add(new Bishop(false, 0, 5)); // alfiere nero
        alfieri.add(new Bishop(true, 7, 2)); // alfiere bianco
        alfieri.add(new Bishop(true, 7, 5)); // alfiere bianco

        cavalli.add(new Knight(false, 0, 1)); // cavallo nero
        cavalli.add(new Knight(false, 0, 6)); // cavallo nero
        cavalli.add(new Knight(true, 7, 1)); // cavallo bianco
        cavalli.add(new Knight(true, 7, 6)); // cavallo bianco

        torri.add(new Rook(false, 0, 0)); // torre nero
        torri.add(new Rook(false, 0, 7)); // torre nero
        torri.add(new Rook(true, 7, 0)); // torre bianco
        torri.add(new Rook(true, 7, 7)); // torre bianco

        regine.add(new Queen(false, 0, 3)); // regina nero
        regine.add(new Queen(true, 7, 3)); // regina bianco

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < scacchiera.length; row++) {
            for (int col = 0; col < scacchiera[0].length; col++) {

                if ((row + col) % 2 == 0) {
                    g.setColor(new Color(108, 187, 60));
                } else {
                    g.setColor(new Color(92, 168, 42));
                }

                g.fillRect(col * dimCella + margine_lati, row * dimCella + margine_sopra, dimCella, dimCella);

            }

        }

        // disegno i pedoni
        for (Pawn p : pedoni) {
            p.draw(g, margine_sopra, margine_lati);
        }

        // disegno i re
        for (King k : re) {
            k.draw(g, margine_sopra, margine_lati);
        }

        // disegno gli alfieri
        for (Bishop b : alfieri) {
            b.draw(g, margine_sopra, margine_lati);
        }

        // disegno i cavalli
        for (Knight k : cavalli) {
            k.draw(g, margine_sopra, margine_lati);
        }

        // disegno le torri
        for (Rook r : torri) {
            r.draw(g, margine_sopra, margine_lati);
        }

        for (Queen q : regine) {
            q.draw(g, margine_sopra, margine_lati);
        }




        // Lettere a - h
        g.setColor(Color.BLACK);
        g.setFont(g.getFont().deriveFont(18f));
        for (int col = 0; col < 8; col++) {
            char lettera = (char) ('a' + col);
            int x = col * dimCella + margine_lati + dimCella / 2 - 5;
            int y = margine_sopra + 8 * dimCella + 20; // sotto la scacchiera
            g.drawString(String.valueOf(lettera), x, y);
        }

        // Numeri 8-1
        for (int row = 0; row < 8; row++) {
            int numero = 8 - row;
            int x = margine_lati - 15;
            int y = row * dimCella + margine_sopra + dimCella / 2 + 5;
            g.drawString(String.valueOf(numero), x, y);
        }

        
    }

}
