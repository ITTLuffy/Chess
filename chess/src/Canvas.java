import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
// import java.util.Timer;
import javax.swing.JPanel;


// Logica --> si hanno 2 click
// 1. selezionare il pezzo
// 2. selezionare la cella di destinazione
// IMPLEMENTARE DRAG E DROP?
// Idea, serverino con socket, che possa giocare con più persone
// I pezzi del nero verranno gestiti da un AI di nome STOCKFISH, da implementare

// gestire il sistema a turni bianco e nero con timer

public class Canvas extends JPanel {

    // matrice x scacchiera
    private final int[][] scacchiera;

    // margini della scacchiera
    private final int margine_sopra = 55;
    private final int margine_lati = 23;

    // dimensione della cella
    private final int dimCella = 80;

    // arrayList x i pezzi
    private final ArrayList<Pawn> pedoni;
    private final ArrayList<King> re;
    private final ArrayList<Bishop> alfieri;
    private final ArrayList<Knight> cavalli;
    private final ArrayList<Rook> torri;
    private final ArrayList<Queen> regine;

    // TIMER per gestire il tempo (da gestire rapid, blitz, bullet, ecc.)
    // private Timer t;

    // click
    private int contaClick = 0;

    // pezzo selezionato
    private Piece pezzoSelezionato = null;

    // pezzo da muovere
    private int destinazioneRow, destinazioneCol;


    public Canvas() {
        // sfondo grigio
        setBackground(Color.GRAY);

        // inizializzo i vari array e arrayList, tra cui la scacchiera
        scacchiera = new int[8][8];
        pedoni = new ArrayList<>();
        re = new ArrayList<>();
        alfieri = new ArrayList<>();
        cavalli = new ArrayList<>();
        torri = new ArrayList<>();
        regine = new ArrayList<>();

        // pezzi aggiunti agli arraylist e posizionati nelle coordinate iniziali corrette
        int p = 6;
        pedoni.add(new Pawn(true, p, 0)); // pedone bianco
        pedoni.add(new Pawn(true, p, 1)); // pedone bianco
        pedoni.add(new Pawn(true, p, 2)); // pedone bianco
        pedoni.add(new Pawn(true, p, 3)); // pedone bianco
        pedoni.add(new Pawn(true, p, 4)); // pedone bianco
        pedoni.add(new Pawn(true, p, 5)); // pedone bianco
        pedoni.add(new Pawn(true, p, 6)); // pedone bianco
        pedoni.add(new Pawn(true, p, 7)); // pedone bianco

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

        // listener pezzi
        // DRAG AND DROP?
        addMouseListener(new MouseAdapter() {
            // metodo per quando si preme il mouse
            @Override
            public void mouseClicked(MouseEvent e) {
                // dico all'utente di selezionare il pezzo da muovere
                System.out.println("Seleziona il pezzo da muovere");

                // coordinate click
                int mouseX = e.getX();
                int mouseY = e.getY();

                // riga e colonna
                int col = (mouseX - margine_lati) / dimCella;
                int row = (mouseY - margine_sopra) / dimCella;

                // se la cella è valida
                if (row >= 0 && row < 8 && col >= 0 && col < 8) {
                    // stampo quale cella è stata cliccata (row, col), utilizzando il metodo converti
                    System.out.print(convertiMossa(row, col) + " ");

                    // condizioni per muovere il pezzo
                    if (contaClick == 0) {
                        pezzoSelezionato = selezionaPezzo(row, col); // SELEZIONO IL PEZZO
                        if (pezzoSelezionato != null) { // se il pezzo è selezionato
                            System.out.println("Pezzo selezionato"); // ack
                            contaClick = 1; // incremento il contatore
                        }
                    } else if (contaClick == 1 && pezzoSelezionato != null) {
                        destinazioneRow = row; // setto la riga di destinazione
                        destinazioneCol = col; // setto la colonna di destinazione
                        muoviPezzo(); // MUOVO IL PEZZO
                        contaClick = 0; // reset per il prossimo movimento
                    }
                }

            }

        });
    }

    @Override

    protected void paintComponent(Graphics g) {
        // chiamata al metodo della super
        super.paintComponent(g);

        // disegno la scacchiera
        for (int row = 0; row < scacchiera.length; row++) {
            for (int col = 0; col < scacchiera[0].length; col++) {

                // colori alternati
                if ((row + col) % 2 == 0) {
                    g.setColor(new Color(108, 187, 60));
                } else {
                    g.setColor(new Color(92, 168, 42));
                }

                // disegno la cella
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

        // disegno le regine
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

    // metodo per inserire la conversione in mosse di scacchi
    // COLONNE --> a - h
    // RIGHE --> 1-8
    public String convertiMossa(int row, int col) {
        char c = (char) ('a' + col); // trasformo in lettere a - h
        int r = 8 - row; // trasformo in numeri
        return "" + c + r; // ritorno in notazione
    }

    // metodo che seleziona un pezzo
    public Piece selezionaPezzo(int row, int col) {
        // logica per muovere il pezzo
        // 1. controlla se la mossa è valida --> DA FARE
        // 2. muovi il pezzo --> OKAY
        // 3. aggiorna la scacchiera --> OKAY
        // 4. ridisegna la scacchiera --> OKAY
        // 5. rendere possibile muovere + pezzi


        // seleziona il pezzo
        int clickMassimi = 2;
        if (contaClick < clickMassimi) {
            // click 1
            contaClick++;

            // seleziona il pezzo
            for (Pawn p : pedoni) {
                if (p.getRow() == row && p.getCol() == col) {
                    System.out.println("Hai selezionato un pedone!");
                    pezzoSelezionato = p;
                    return pezzoSelezionato;
                }
            }

            for (Bishop b : alfieri) {
                if (b.getRow() == row && b.getCol() == col) {
                    System.out.println("Hai selezionato un alfiere!");
                    pezzoSelezionato = b;
                    return pezzoSelezionato;
                }
            }

            for (Queen q : regine) {
                if (q.getRow() == row && q.getCol() == col) {
                    System.out.println("Hai selezionato una regina!");
                    pezzoSelezionato = q;
                    return pezzoSelezionato;
                }
            }

            for (Knight n : cavalli) {
                if (n.getRow() == row && n.getCol() == col) {
                    System.out.println("Hai selezionato un cavallo!");
                    pezzoSelezionato = n;
                    return pezzoSelezionato;
                }

            }

            for (Rook r : torri) {
                if (r.getRow() == row && r.getCol() == col) {
                    System.out.println("Hai selezionato una torre!");
                    pezzoSelezionato = r;
                    return pezzoSelezionato;
                }
            }

            for (King k : re) {
                if (k.getRow() == row && k.getCol() == col) {
                    System.out.println("Hai selezionato il re!");
                    pezzoSelezionato = k;
                    return pezzoSelezionato;
                }

            }

        }

        // se il pezzo non è selezionato
        return null;

    }

    // metodo per muovere il pezzo
    public void muoviPezzo() {
        // verifico che la mossa sia valida
        if(!pezzoSelezionato.isValidMove(destinazioneRow, destinazioneCol)){
            System.out.println("Mossa non valida");
            return;
        }

        // posizione iniziale del pezzo
        int oldRow = pezzoSelezionato.getRow();
        int oldCol = pezzoSelezionato.getCol();

        // cambio la posizione del pezzo
        pezzoSelezionato.setRow(destinazioneRow);
        pezzoSelezionato.setCol(destinazioneCol);

        // aggiorno la scacchiera
        scacchiera[oldRow][oldCol] = 0; // metto a zero la posizione vecchia
        scacchiera[destinazioneRow][destinazioneCol] = 1; // metto a uno la posizione nuova

        // azzero il pezzo selezionato
        pezzoSelezionato = null;
        // ack all'utente
        System.out.println("Pezzo messo in " + convertiMossa(destinazioneRow, destinazioneCol));
        // ridisegno solo le due celle interessate
        int x1 = oldCol * dimCella + margine_lati;
        int y1 = oldRow * dimCella + margine_sopra;
        int x2 = destinazioneCol * dimCella + margine_lati;
        int y2 = destinazioneRow * dimCella + margine_sopra;

        repaint(x1, y1, dimCella, dimCella);
        repaint(x2, y2, dimCella, dimCella);

    }


}