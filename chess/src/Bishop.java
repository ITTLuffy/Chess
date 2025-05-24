
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Bishop extends Piece{
    private Image immagine;

    public Bishop(boolean colore, int row, int col) {
        super(colore, row, col);

        try {
            if (colore) {
                immagine = ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/Chess_blt45.svg.png")));
                System.out.println("Immagine caricata");
            } else {
                immagine = ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/Chess_bdt45.svg.png")));
                System.out.println("Immagine caricata");
            }
        } catch (IOException | IllegalArgumentException ex) {
            System.out.println("Errore nel caricamento dell'immagine: " + ex.getMessage());
        } catch (Exception e) {
            System.out.println("Errore generico: " + e.getMessage());
        }

    }

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


    public void draw(Graphics g, int margineSopra, int margineLato) {
        int tileSize = 80;
        int x = col * tileSize + margineLato;
        int y = row * tileSize + margineSopra;

        g.drawImage(immagine, x, y, tileSize, tileSize, null);
    }

    @Override
    public boolean isValidMove(int destinazioneRow, int destinazioneCol) {
        // Se c'è un pezzo davanti

        // Va solo in diagonale; ci si sposta di x di row e x di col
        // uso il valore assoluto per vedere se la differenza è uguale
        if(Math.abs(destinazioneRow - this.row) == Math.abs(destinazioneCol - this.col)) {
            return true;
        }

        return false;
    }

    @Override
    public char getFENChar() {
        return 'B'; // Carattere FEN per l'alfiere
    }
}
