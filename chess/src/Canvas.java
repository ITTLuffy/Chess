import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

public class Canvas extends JPanel {

    // matrice x scacchiera, 8 righe e 8 colonne
    // 0 --> cella vuota
    // 1 --> cella occupata da un pezzo
    // 2 --> cella evidenziata (quando si clicca con il tasto destro)
    private final int[][] scacchiera;

    // margini della scacchiera
    private final int margine_sopra = 25;
    private final int margine_lati = 20;

    // dimensione cella
    private final int dimCella = 80;

    // arrayList x i pezzi
    private final ArrayList<Piece> pezziBianchi;
    private final ArrayList<Piece> pezziNeri;

    // TIMER per gestire il tempo
    private Timer tNero;
    private Timer tBianco;
    // tempo in secondi per i giocatori
    private int tempoBianco = 600;
    private int tempoNero = 600;
    // gestione turni
    private boolean turnoBianco = true; // true --> turno bianco, false --> turno nero
    private int nTurni = 0; // per contare il numero di turni

    // click
    private int contaClick = 0;

    // pezzo selezionato
    private Piece pezzoSelezionato = null;

    // coordinate di destinazione del pezzo selezionato
    private int destinazioneRow, destinazioneCol;

    public Canvas() {
        // sfondo grigio
        setBackground(Color.GRAY);

        // inizializzo i vari array e arrayList, tra cui la scacchiera
        scacchiera = new int[8][8];

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

        tBianco = new Timer(1000, e -> {
            if (turnoBianco) {
                tempoBianco--; // tempo che scorre
            }
            // Ridisegna per aggiornare il display del tempo
            repaint();
            if (tempoBianco <= 0) {
                tBianco.stop();
                JOptionPane.showMessageDialog(this, "Tempo scaduto per il Bianco! Nero vince.");
            }

        });

        tNero = new Timer(1000, e -> {
            if (!turnoBianco) {
                tempoNero--; // tempo che scorre
            }
            // Ridisegna per aggiornare il display del tempo
            repaint(); //
            if (tempoNero <= 0) {
                tNero.stop();
                JOptionPane.showMessageDialog(this, "Tempo scaduto per il Nero! Bianco vince.");
            }
        });

        // listener pezzi
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

                    // condizioni per muovere il pezzo o selezionarlo
                    if (contaClick == 0) {
                        pezzoSelezionato = selezionaPezzo(row, col); // SELEZIONO IL PEZZO
                        if (pezzoSelezionato != null) { // se il pezzo è selezionato
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

        // Avvio il timer del primo giocatore
        tBianco.start();
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

                // cella rossa
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
        g.setColor(Color.BLACK); // colore
        g.setFont(new Font("Times new Roman", Font.PLAIN, 25)); // dimensione del font
        for (int col = 0; col < 8; col++) { // ciclo per le colonne
            char lettera = (char) ('a' + col); // converto in lettere da 'a' a 'h'
            int x = col * dimCella + margine_lati + dimCella / 2 - 5; // al centro della cella
            int y = margine_sopra + 8 * dimCella + 25; // sotto la scacchiera
            g.drawString(String.valueOf(lettera), x, y); // String.valueOf() per convertire il char in String
        }

        // Numeri 8-1
        for (int row = 0; row < 8; row++) { // ciclo per le righe
            int numero = 8 - row; // converto in numeri
            int x = margine_lati - 17; // a sinistra della scacchiera
            int y = row * dimCella + margine_sopra + dimCella / 2 + 5; // al centro della cella
            g.drawString(String.valueOf(numero), x, y);
        }

        // Stampo il tempo rimanente per i giocatori
        g.setColor(Color.BLACK);
        g.setFont(new Font("Times new Roman", Font.BOLD, 30));
        String tempoBiancoStr = String.format("B: %02d:%02d", tempoBianco / 60, tempoBianco % 60);
        String tempoNeroStr = String.format("N: %02d:%02d", tempoNero / 60, tempoNero % 60);
        // String.format() per formattare il tempo in minuti e secondi
        // %02d:%02d significa che voglio 2 cifre per i minuti e 2 cifre per i secondi, con zeri iniziali se necessario

        // Posizionamento a destra della scacchiera
        int xTimer = margine_lati + 8 * dimCella + 10;
        int yBianco = margine_sopra + 300;
        int yNero = margine_sopra + 350;

        // Disegno i tempi
        g.drawString(tempoBiancoStr, xTimer, yBianco);
        g.drawString(tempoNeroStr, xTimer, yNero);

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
        // 1 click --> seleziona il pezzo
        // 2 click --> muove il pezzo

        // seleziona il pezzo
        int clickMassimi = 2;

        if (contaClick < clickMassimi) {
            // click 1
            contaClick++;

            // seleziona il pezzo BIANCO
            for (Piece p : pezziBianchi) {
                if (p.getRow() == row && p.getCol() == col) {
                    System.out.println("Hai selezionato: " + p.getClass().getName()); // Pezzo selezionato
                    pezzoSelezionato = p;
                    return pezzoSelezionato;
                }
            }

            // seleziona il pezzo NERO
            for (Piece p : pezziNeri) {
                if (p.getRow() == row && p.getCol() == col) {
                    System.out.println("Hai selezionato: " + p.getClass().getName()); // Pezzo selezionato
                    pezzoSelezionato = p;
                    return pezzoSelezionato;
                }
            }

        }

        // se il pezzo non è selezionato
        System.out.println("Nessun pezzo selezionato in " + convertiMossa(row, col));
        // resetto il contatore dei click
        contaClick = 0;
        return null;
    }

    // metodo per muovere il pezzo
    public void muoviPezzo() {
        // gestione timer
        if (turnoBianco) {
            tBianco.stop();
        } else {
            tNero.stop();
        }

        // CONTROLLO SCACCO
        if (turnoBianco && sottoScaccoBianco()) {
            System.out.println("Mossa non valida: Re Bianco sotto scacco!");
            if (!turnoBianco) tNero.start();
            else tBianco.start();
            return;
        }
        if (!turnoBianco && sottoScaccoNero()) {
            System.out.println("Mossa non valida: Re Nero sotto scacco!");
            if (!turnoBianco) tNero.start();
            else tBianco.start();
            return;
        }

        // TURNO DEL GIOCATORE
        if (pezzoSelezionato.getColor() != turnoBianco) {
            System.out.println("Non è il tuo turno!");
            contaClick = 0;
            pezzoSelezionato = null;
            return;
        }

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

        // VERIFICO SE IL PEZZO È PINNATO

        // posizione iniziale del pezzo
        int oldRow = pezzoSelezionato.getRow();
        int oldCol = pezzoSelezionato.getCol();

        // Gestione dell'arrocco
        boolean isArrocco = false; // VALIDITÀ

        if (pezzoSelezionato instanceof King) {
            King re = (King) pezzoSelezionato;

            // Verifica se è un movimento di arrocco (2 caselle orizzontali)
            if (Math.abs(destinazioneCol - oldCol) == 2 && destinazioneRow == oldRow) {
                // Determina se è arrocco corto (verso destra) o lungo (verso sinistra)
                boolean isArroccoCorto = destinazioneCol > oldCol;

                // Colonna della torre coinvolta nell'arrocco
                int torreCol;

                if (isArroccoCorto) {
                    torreCol = 7;
                } else {
                    torreCol = 0;
                }

                // Trova la torre nella posizione corretta
                Piece possibileTorre = trovaPezzo(oldRow, torreCol); // Trovo la torre

                // Verifica che ci sia una torre nella posizione corretta
                if (possibileTorre instanceof Rook) {
                    Rook torre = (Rook) possibileTorre;

                    // Verifica che re e torre non si siano già mossi --> L'ARROCCO NON SAREBBE VALIDO
                    if (!re.getHaMosso() && !torre.getHaMosso()) {

                        // Verifica che non ci siano pezzi tra il re e la torre
                        boolean percorsoLibero = true;

                        int inizio = Math.min(oldCol, torreCol) + 1; // Inizio del percorso da controllare
                        int fine = Math.max(oldCol, torreCol); // Fine del percorso da controllare

                        for (int col = inizio; col < fine; col++) {
                            if (eOccupato(oldRow, col)) { // Controllo le celle nel percorso
                                percorsoLibero = false;
                                break;
                            }
                        }

                        // Se non ci sono pezzi in mezzo
                        if (percorsoLibero) {
                            // Esegui l'arrocco
                            isArrocco = true;

                            // Nuova posizione della torre dopo l'arrocco
                            int nuovaColTorre;
                            if (isArroccoCorto) { // arrocco corto
                                    nuovaColTorre = oldCol + 1;
                            } else { // arrocco lungo
                                    nuovaColTorre = oldCol - 1;
                            }

                            // Muovi la torre
                            torre.setCol(nuovaColTorre);
                            torre.setHaMosso(true);
                            // Aggiorna la scacchiera per la torre
                            scacchiera[oldRow][torreCol] = 0; // metto a zero la posizione vecchia della torre
                            scacchiera[oldRow][nuovaColTorre] = 1; // metto a uno la posizione nuova della torre

                            // Celle interessate che necessitano il repaint
                            int xTorre1 = torreCol * dimCella + margine_lati;
                            int yTorre1 = oldRow * dimCella + margine_sopra;
                            int xTorre2 = nuovaColTorre * dimCella + margine_lati;
                            int yTorre2 = oldRow * dimCella + margine_sopra;

                            // repaint
                            repaint(xTorre1, yTorre1, dimCella, dimCella);
                            repaint(xTorre2, yTorre2, dimCella, dimCella);

                            // Stampo l'arrocco eseguito
                            System.out.println("Arrocco " + (isArroccoCorto ? "corto" : "lungo") + " eseguito");
                        } else {
                            System.out.println("Arrocco non possibile: ci sono pezzi tra il re e la torre");
                            return;
                        }
                    } else {
                        System.out.println("Arrocco non possibile: re o torre si sono già mossi");
                        return;
                    }
                } else {
                    System.out.println("Arrocco non possibile: torre non trovata");
                    return;
                }
            }

            // Imposta che il re si è mosso
            re.setHaMosso(true);
        }

        // Se è una torre, segna che si è mossa (per l'arrocco)
        if (pezzoSelezionato instanceof Rook) {
            ((Rook) pezzoSelezionato).setHaMosso(true);
        }

        // cambio la posizione del pezzo
        pezzoSelezionato.setRow(destinazioneRow);
        pezzoSelezionato.setCol(destinazioneCol);

        // aggiorno la scacchiera
        scacchiera[oldRow][oldCol] = 0; // metto a zero la posizione vecchia
        scacchiera[destinazioneRow][destinazioneCol] = 1; // metto a uno la posizione nuova

        // azzero il pezzo selezionato
        pezzoSelezionato = null;
        // ack all'utente
        if (!isArrocco) {
            System.out.println("Pezzo messo in " + convertiMossa(destinazioneRow, destinazioneCol));
        }

        // ridisegno solo le due celle interessate
        int x1 = oldCol * dimCella + margine_lati;
        int y1 = oldRow * dimCella + margine_sopra;
        int x2 = destinazioneCol * dimCella + margine_lati;
        int y2 = destinazioneRow * dimCella + margine_sopra;

        repaint(x1, y1, dimCella, dimCella);
        repaint(x2, y2, dimCella, dimCella);

        // Cambia turno
        turnoBianco = !turnoBianco;
        // Incremento il numero di turni
        nTurni++;

        // Avvia il timer del nuovo giocatore
        if (turnoBianco) {
            tBianco.start();
        } else {
            tNero.start();
        }

    }

    // Metodo per mettere un pezzo nella scacchiera
    public Piece mettiPezzo(int row, int col) {
        for (Piece p : pezziBianchi) {
            if (p.getRow() == row && p.getCol() == col) {
                return p;
            }
        }

        for (Piece p : pezziNeri) {
            if (p.getRow() == row && p.getCol() == col) {
                return p;
            }
        }
        return null;
    }

    // Metodo per verificare se una cella è occupata
    public boolean eOccupato(int row, int col) {
        // controlla se la cella è occupata
        return mettiPezzo(row, col) != null;
    }

    // Metodo per verificare se lo spostamento è impedito da un pezzo in mezzo
    // Se pezzi come le torri, gli alfieri e la regina si muovono in diagonale, in verticale o in orizzontale

    public boolean pezzoBloccato(int destRow, int destCol) {
        // row e col di partenza
        int startRow = pezzoSelezionato.getRow();
        int startCol = pezzoSelezionato.getCol();

        // Torre --> movimento orizzontale/verticale
        if (pezzoSelezionato instanceof Rook) {
            return controllaPercorso(startRow, startCol, destRow, destCol);
        }
        // Alfiere --> movimento diagonale
        else if (pezzoSelezionato instanceof Bishop) {
            return controllaPercorso(startRow, startCol, destRow, destCol);
        }
        // Regina --> movimento orizzontale/verticale o diagonale
        else if (pezzoSelezionato instanceof Queen) {
            return controllaPercorso(startRow, startCol, destRow, destCol);
        }

        // Altri pezzi (Cavallo, Re, Pedone) non hanno bisogno di questo controllo
        return false;
    }

    // Metodo per controllare se il percorso tra due celle è libero
    private boolean controllaPercorso(int startRow, int startCol, int destRow, int destCol) {
        // Calcola il passo di movimento
        int rowStep = 0;
        int colStep = 0;

        // Direzione di movimento del pezzo sulla scacchiera
        if (startRow != destRow) { // righe
            rowStep = (destRow > startRow) ? 1 : -1;
        }

        if (startCol != destCol) { // colonne
            colStep = (destCol > startCol) ? 1 : -1;
        }

        // RIGA E LA COLONNA da controllare
        int row = startRow + rowStep;
        int col = startCol + colStep;

        // Controlla tutte le caselle lungo il percorso
        while (row != destRow || col != destCol) {

            // Controlla se la casella è occupata
            if (eOccupato(row, col)) {
                return true; // Percorso bloccato
            }

            // Avanza alla casella successiva
            row += rowStep;
            col += colStep;
        }

        return false; // Percorso libero
    }

    // Metodo per trovare un pezzo specifico
    public Piece trovaPezzo(int row, int col) {
        // Cerca il pezzo nell'ArrayList di pezzi
        for (Piece p : pezziBianchi) { //
            if (p.getRow() == row && p.getCol() == col) {
                return p;
            }
        }
        for (Piece p : pezziNeri) {
            if (p.getRow() == row && p.getCol() == col) {
                return p;
            }
        }
        return null; // Nessun pezzo trovato
    }

    // Controlla se il re bianco è sotto scacco
    private boolean sottoScaccoBianco() {
        // Trovo il re bianco
        King reBianco = null;
        for (Piece p : pezziBianchi) {
            if (p instanceof King) {
                reBianco = (King) p;
                break;
            }
        }
        // Se null
        if (reBianco == null)
            return false;

        // Verifica se qualche pezzo nero minaccia il re bianco
        for (Piece p : pezziNeri) {
            if (p.isValidMove(reBianco.getRow(), reBianco.getCol())) {
                return true;
            }
        }
        return false;
    }

    // Controlla se il re nero è sotto scacco
    private boolean sottoScaccoNero() {
        // Trovo il re nero
        King reNero = null;
        for (Piece p : pezziNeri) {
            if (p instanceof King) {
                reNero = (King) p;
                break;
            }
        }
        // Se null
        if (reNero == null)
            return false;

        // Verifica se qualche pezzo bianco minaccia il re nero
        for (Piece p : pezziBianchi) {
            if (p.isValidMove(reNero.getRow(), reNero.getCol())) {
                return true;
            }
        }
        return false;
    }



    private boolean rePinnato() {
        // Controlla se il re è pinnato
        
        // Trovo le coordinate del re bianco
        for (Piece p : pezziBianchi) {
            if (p instanceof King) {
                int col = p.getCol();
                int row = p.getRow();
            }
        }

        
        return false;
    }

    private boolean enPassant() {
        // en Passant
        // Da implementare
        return false;
    }

    private boolean stallo() {
        // Se ci sono solo i re in gioco e nessun altro pezzo
        // Oppure se ci sono i re in gioco con 1 alfiere o 1 cavallo
        if (pezziBianchi.size() == 1 && pezziNeri.size() == 1) {
            // Solo i re in gioco
            return true;
        }

        return false;
    }

    private boolean scaccoMatto() {
        // Scacco matto
        // Da implementare
        return false;
    }

    private boolean draw() {
        // Draw
        // Da implementare
        return false;
    }

    private boolean gestioneTurni() {
        // Inizia il bianco
        tBianco.start();
        if (turnoBianco) {
            System.out.println("Turno del Nero");
        }
        return false;
    }





}