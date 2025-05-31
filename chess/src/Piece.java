import java.awt.*;

// classe astratta per i pezzi degli scacchi

// cos'è una classe astratta --> è una classe che non può essere istanziata, serve solamente per definire
// metodi e variabili comuni a più classi in questo caso i pezzi degli scacchi
public abstract class Piece {
    // 3 variabili per la posizione del pezzo e il colore
    protected int row;
    protected int col;
    protected boolean color;

    // costruttore della classe
    public Piece(boolean color, int row, int col) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

    // getter e setter
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

    public boolean getColor() {
        return color;
    }

    // metodo astratto per disegnare il pezzo
    public abstract void draw(Graphics g, int margineSopra, int margineLato);

    // metodo astratto per verificare se la mossa è valida o meno
    public abstract boolean isValidMove(int destinazioneRow, int destinazioneCol);

    // Metodo astratto per ottenere il carattere FEN del pezzo
    public abstract char getFENChar();
}
