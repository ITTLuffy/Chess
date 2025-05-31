
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Queen extends Piece {

    private Image immagine;

    public Queen(boolean colore, int row, int col) {
        super(colore, row, col);

        try {
            if (colore) {
                immagine = ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/Chess_qlt45.svg.png")));
                System.out.println("Immagine caricata");
            } else {
                immagine = ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/Chess_qdt45.svg.png")));
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

    public boolean getColor() {
        return super.getColor();
    }

    public void draw(Graphics g, int margineSopra, int margineLato) {
        int tileSize = 80;
        int x = col * tileSize + margineLato;
        int y = row * tileSize + margineSopra;

        g.drawImage(immagine, x, y, tileSize, tileSize, null);
    }

    @Override
    public boolean isValidMove(int destinazioneRow, int destinazioneCol) {
        // La regina si muove come un alfiere e come una torre
        if(Math.abs(destinazioneRow - this.row) == Math.abs(destinazioneCol - this.col)) { // Gestisco le diagonali
            return true;
        } else if ((destinazioneCol != this.col && destinazioneRow == this.row) || // Gestisco le righe e le colonne
                (destinazioneRow != this.row && destinazioneCol == this.col)) {
            return true;
        }
        return false;
    }

    @Override
    public char getFENChar() {
        return 'Q'; // Carattere FEN per la regina
    }

}
