import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class King extends Piece {

    private Image immagine;

    public King(boolean colore, int row, int col) {
        super(colore, row, col);

        try {
            if (colore) {
                immagine = ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/Chess_klt45.svg.png")));
                System.out.println("Immagine caricata");
            } else {
                immagine = ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/Chess_kdt45.svg.png")));
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
        // Può muoversi in qualsiasi direzione, ma solo di una casella
        int diffRow = Math.abs(destinazioneRow - this.row); // valore assoluto differenza
        int diffCol = Math.abs(destinazioneCol - this.col); // valore assoluto differenza

        // Se la differenza è 1 in una direzione e 0 nell'altra, è un movimento valido
        if (diffRow <= 1 && diffCol <= 1) {

            
            // Escludo la possibilità che il re stia fermo nella stessa casella
            return diffRow != 0 || diffCol != 0;
        }

        return false;
    }

    @Override
    public char getFENChar() {
        return 'K'; // Carattere FEN per il re
    }

}
