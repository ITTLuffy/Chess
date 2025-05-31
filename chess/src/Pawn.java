import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Pawn extends Piece{

    // variabili per l'immagine
    private Image immagine;

    public Pawn(boolean colore, int row, int col) {
        super(colore, row, col); // richiamo il costruttore della classe astratta Piece
        // prendo il colore e passo alla variabile immagine l'immagine corretta
        try {
            if (colore) {
                immagine = ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/Chess_plt45.svg.png")));
                System.out.println("Immagine caricata"); // ack
            } else {
                immagine = ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/Chess_pdt45.svg.png")));
                System.out.println("Immagine caricata"); // ack
            }
        } catch (IOException | IllegalArgumentException ex) {
            System.out.println("Errore nel caricamento dell'immagine: " + ex.getMessage());
        } catch (Exception e) {
            System.out.println("Errore generico: " + e.getMessage());
        }

    }

    // getter e setter
    public int getRow() {
        return super.getRow();
    }

    public void setRow(int row) {
        super.setRow(row);
    }

    public int getCol() {
        return super.getCol();
    }

    public void setCol(int col) {
        super.setCol(col);
    }

    public boolean getColor() {
        return super.getColor();
    }

    // metodo per disegnare il pezzo
    public void draw(Graphics g, int margineSopra, int margineLato) {
        int tileSize = 80;
        int x = col * tileSize + margineLato;
        int y = row * tileSize + margineSopra;

        g.drawImage(immagine, x, y, tileSize, tileSize, null);
    }

    @Override
    public boolean isValidMove(int destinazioneRow, int destinazioneCol) { // COSIDERIAMO SOLO I BIANCHI
        // pedone può muoversi solo in avanti
        if (destinazioneCol != col) {
            return false;
        }

        int dRow = destinazioneRow - getRow();// delta row

        if (getColor()) { // BIANCO
            if (dRow == -1) return true; // movimento singolo in avanti
            if (getRow() == 6 && dRow == -2) return true; // movimento doppio in avanti
        } else { // NERO
            if (dRow == 1) return true; // movimento singolo in avanti
            if (getRow() == 1 && dRow == 2) return true; // movimento doppio in avanti
        }

        return false;
    }

    @Override
    public char getFENChar() {
        return 'P'; // Carattere FEN per il pedone
    }


}
