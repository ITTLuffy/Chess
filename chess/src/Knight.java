
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Knight {

    private Image immagine;
    private final boolean colore;
    private int row;
    private int col;

    public Knight(boolean colore, int row, int col) {
        this.col = col;
        this.colore = colore;
        this.row = row;

        try {
            if (colore) {
                immagine = ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/Chess_nlt45.svg.png")));
                System.out.println("Immagine caricata");
            } else {
                immagine = ImageIO.read(Objects.requireNonNull(getClass().getResource("immagini/Chess_ndt45.svg.png")));
                System.out.println("Immagine caricata");
            }
        } catch (IOException | IllegalArgumentException ex) {
            System.out.println("Errore nel caricamento dell'immagine: " + ex.getMessage());
        } catch (Exception e) {
            System.out.println("Errore generico: " + e.getMessage());
        }


    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }


    public void draw(Graphics g, int margineSopra, int margineLato) {
        int tileSize = 80;
        int x = col * tileSize + margineLato;
        int y = row * tileSize + margineSopra;

        g.drawImage(immagine, x, y, tileSize, tileSize, null);
    }

}
