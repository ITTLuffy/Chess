
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Rook extends Piece {

    private Image immagine;
    private boolean haMosso = false; // Movimento

    public Rook(boolean colore, int row, int col) {
        super(colore, row, col);

        //
        try {
            if (colore) {
                immagine = ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/Chess_rlt45.svg.png")));
                System.out.println("Immagine caricata");
            } else {
                immagine = ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/Chess_rdt45.svg.png")));
                System.out.println("Immagine caricata");
            }
        } catch (IOException | IllegalArgumentException ex) {
            System.out.println("Errore nel caricamento dell'immagine: " + ex.getMessage());
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

    public boolean getHaMosso() {
        return haMosso;
    }

    public void setHaMosso(boolean haMosso) {
        this.haMosso = haMosso;
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
        
        // Può muoversi solo in orizzontale o verticale (SI PUO FARE ANCHE CON UN IF)
        return (destinazioneCol != this.col && destinazioneRow == this.row) || (destinazioneRow != this.row && destinazioneCol == this.col);
    }

    @Override
    public char getFENChar() {
        return 'R'; // Carattere FEN per la torre
    }

}


