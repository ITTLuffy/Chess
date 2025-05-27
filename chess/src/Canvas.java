import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;


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
    private final ArrayList<Piece> pezziBianchi;
    private final ArrayList<Piece> pezziNeri;


    // TIMER per gestire il tempo (da gestire rapid, blitz, bullet, ecc.)
    // private Timer t;

    // click
    private int contaClick = 0;

    // pezzo selezionato
    private Piece pezzoSelezionato = null;

    // pezzo da muovere
    private int destinazioneRow, destinazioneCol;

    // boolean x colore cella scacchiera
    // true --> bianco
    // false --> nero
    // private boolean coloreCella = false;


    public Canvas() {
        // sfondo grigio
        setBackground(Color.GRAY);

        // inizializzo i vari array e arrayList, tra cui la scacchiera
        scacchiera = new int[8][8];

        // INIZIALMENTE avevo fatto 1 arrayList per ogni pezzo, ovvero x i pedoni, i re, ecc.
        // Ho deciso di creare 2 arrayList, uno per i pezzi bianchi e uno per i pezzi neri
        // Tutto questo x semplificare il codice e renderlo più leggibile
        pezziBianchi = new ArrayList<>();
        pezziNeri = new ArrayList<>();

        // pezzi aggiunti agli arraylist e posizionati nelle coordinate iniziali corrette
        int p = 6;
        pezziBianchi.add(new Pawn(true, p, 0)); // pedone bianco
        pezziBianchi.add(new Pawn(true, p, 1)); // pedone bianco
        pezziBianchi.add(new Pawn(true, p, 2)); // pedone bianco
        pezziBianchi.add(new Pawn(true, p, 3)); // pedone bianco
        pezziBianchi.add(new Pawn(true, p, 4)); // pedone bianco
        pezziBianchi.add(new Pawn(true, p, 5)); // pedone bianco
        pezziBianchi.add(new Pawn(true, p, 6)); // pedone bianco
        pezziBianchi.add(new Pawn(true, p, 7)); // pedone bianco

        int p1 = 1;
        pezziNeri.add(new Pawn(false, p1, 0)); // pedone nero
        pezziNeri.add(new Pawn(false, p1, 1)); // pedone nero
        pezziNeri.add(new Pawn(false, p1, 2)); // pedone nero
        pezziNeri.add(new Pawn(false, p1, 3)); // pedone nero
        pezziNeri.add(new Pawn(false, p1, 4)); // pedone nero
        pezziNeri.add(new Pawn(false, p1, 5)); // pedone nero
        pezziNeri.add(new Pawn(false, p1, 6)); // pedone nero
        pezziNeri.add(new Pawn(false, p1, 7)); // pedone nero

        pezziNeri.add(new King(false, 0, 4)); // re nero
        pezziBianchi.add(new King(true, 7, 4)); // re bianco

        pezziNeri.add(new Bishop(false, 0, 2)); // alfiere nero
        pezziNeri.add(new Bishop(false, 0, 5)); // alfiere nero
        pezziBianchi.add(new Bishop(true, 7, 2)); // alfiere bianco
        pezziBianchi.add(new Bishop(true, 7, 5)); // alfiere bianco

        pezziNeri.add(new Knight(false, 0, 1)); // cavallo nero
        pezziNeri.add(new Knight(false, 0, 6)); // cavallo nero
        pezziBianchi.add(new Knight(true, 7, 1)); // cavallo bianco
        pezziBianchi.add(new Knight(true, 7, 6)); // cavallo bianco

        pezziNeri.add(new Rook(false, 0, 0)); // torre nero
        pezziNeri.add(new Rook(false, 0, 7)); // torre nero
        pezziBianchi.add(new Rook(true, 7, 0)); // torre bianco
        pezziBianchi.add(new Rook(true, 7, 7)); // torre bianco

        pezziNeri.add(new Queen(false, 0, 3)); // regina nero
        pezziBianchi.add(new Queen(true, 7, 3)); // regina bianco

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
        // Possibilità di evidenziare una cella di rosso quando si clicca tasto destro su un pezzo
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    // Azione per il click del tasto destro
                    System.out.println("Hai cliccato con il tasto destro!");
                    // Coordinate del click
                    int mouseX = e.getX();
                    int mouseY = e.getY();

                    // Trovo la cella
                    int col = (mouseX - margine_lati) / dimCella;
                    int row = (mouseY - margine_sopra) / dimCella;

                    // Evidenzio la cella selezionata
                    if (row >= 0 && row < 8 && col >= 0 && col < 8) {
                        // Se già evidenziata, la deseleziona
                        if (scacchiera[row][col] == 2) {
                            scacchiera[row][col] = 0;
                        } else {
                            scacchiera[row][col] = 2;
                        }
                    }

                    // Ridisegno la cella
                    repaint(col * dimCella + margine_lati, row * dimCella + margine_sopra, dimCella, dimCella);
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
                // calcolo le coordinate della cella
                int x = col * dimCella + margine_lati;
                int y = row * dimCella + margine_sopra;

                // colori alternati
                if ((row + col) % 2 == 0) {
                    g.setColor(new Color(108, 187, 60));
                } else {
                    g.setColor(new Color(92, 168, 42));
                }

                if (scacchiera[row][col] == 2) {
                    g.setColor(new Color(255, 0, 0, 128));
                    g.drawRect(x, y, dimCella, dimCella);
                }

                // disegno la cella
                g.fillRect(col * dimCella + margine_lati, row * dimCella + margine_sopra, dimCella, dimCella);

            }

        }

        // disegno i pezzi bianchi
        for (Piece p : pezziBianchi) {
            p.draw(g, margine_sopra, margine_lati);
        }

        // disegno i pezzi neri
        for (Piece p : pezziNeri) {
            p.draw(g, margine_sopra, margine_lati);
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
            for (Piece p : pezziBianchi) {
                if (p.getRow() == row && p.getCol() == col) {
                    System.out.println("Hai selezionato: " + p.getClass().getName());
                    pezzoSelezionato = p;
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

        // MOVIMENTO PEZZO CORRETTO
        if(!pezzoSelezionato.isValidMove(destinazioneRow, destinazioneCol)){
            System.out.println("Mossa non valida");
            return;
        }

        // VERIFICO SE LA CELLA È OCCUPATA
        if (eOccupato(destinazioneRow, destinazioneCol)) {
            System.out.println("Cella occupata");
            return;
        }

        // VERIFICO SE IL PEZZO È BLOCCATO
        if (pezzoBloccato(destinazioneRow, destinazioneCol)) {
            System.out.println("Il pezzo è bloccato da un altro pezzo");
            return;
        }

        // VERIFICO SE IL RE È IN SCACCO E QUINDI PINNATO

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

    // Metodo per verificare se una cella è occupata
    public Piece mettiPezzo(int row, int col) {
        for (Piece p : pezziBianchi) {
            if (p.getRow() == row && p.getCol() == col) {
                return p;
            }
        }
        return null;
    }

    public boolean eOccupato(int row, int col) {
        // controlla se la cella è occupata
        return mettiPezzo(row, col) != null;
    }

    // Metodo per verificare se lo spostamento è impedito da un pezzo in mezzo
    // Se pezzi come le torri, gli alfieri e la regina si muovono in diagonale, in verticale o in orizzontale

    public boolean pezzoBloccato(int destRow, int destCol) {
        int startRow = pezzoSelezionato.getRow();
        int startCol = pezzoSelezionato.getCol();

        // Torre --> movimento orizzontale/verticale
        if (pezzoSelezionato.getClass() == Rook.class) {
            return controllaDiagonale(startRow, startCol, destRow, destCol);
        }
        // Alfiere --> movimento diagonale
        else if (pezzoSelezionato.getClass() == Bishop.class) {
            return controllaDiagonale(startRow, startCol, destRow, destCol);
        }
        // Regina --> movimento orizzontale/verticale o diagonale
        else if (pezzoSelezionato.getClass() == Queen.class) {
            // Orizzontale o verticale
            if (startRow == destRow || startCol == destCol) {
                return controllaDiagonale(startRow, startCol, destRow, destCol);
            }
            // Diagonale
            else {
                return controllaDiagonale(startRow, startCol, destRow, destCol);
            }
        }

        // Altri pezzi
        return false;
    }

    private boolean controllaDiagonale(int startRow, int startCol, int destRow, int destCol) {
        // Calcolo il passo di movimento per riga e colonna
        int rowStep = (destRow > startRow) ? 1 : -1;
        int colStep = (destCol > startCol) ? 1 : -1;

        // Imposto la prima casella da controllare (dopo quella di partenza)
        int row = startRow + rowStep;
        int col = startCol + colStep;

        // Controllo tutte le caselle lungo il percorso
        while (row != destRow && col != destCol) {
            if (eOccupato(row, col)) {
                return true; // Path is blocked
            }
            row += rowStep;
            col += colStep;
        }

        return false; // Percorso libero
    }

    private boolean sottoScacco() {
        // Controlla se il re è sotto scacco
        // Da implementare
        return false;
    }

    private boolean rePinnato() {
        // Controlla se il re è pinato
        // Da implementare
        return false;
    }

    private boolean arrocco() {
        // arrocco lungo
        // arrocco corto
    }

    private boolean enPassant() {
        // en Passant
    }



}