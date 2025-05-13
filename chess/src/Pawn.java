import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Pawn {

    private Image immagine;
    private boolean colore;
    private int row;
    private int col;

    public Pawn(boolean colore, int row, int col) {
        this.colore = colore;
        this.row = row;
        this.col = col;

        try {
            if (colore) {
                immagine = ImageIO.read(getClass().getResource("immagini/Chess_plt45.svg.png"));
                System.out.println("Immagine caricata");
            } else {
                immagine = ImageIO.read(getClass().getResource("immagini/Chess_pdt45.svg.png"));
                System.out.println("Immagine caricata");
            }
        } catch (IOException | IllegalArgumentException ex) {
            System.out.println("Errore nel caricamento dell'immagine: " + ex.getMessage());
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

        if (colore) {
            g.drawImage(immagine, x, y, tileSize, tileSize, null);
        } else if (!colore) {
            g.drawImage(immagine, x, y, tileSize, tileSize, null);
        }
    }

}
