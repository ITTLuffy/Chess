import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Canvas extends JPanel implements ActionListener, KeyListener {

    private int righe = 21;
    private int colonne = 19;

    private int quadretto = 32; // pixel
    private int w = colonne * quadretto;
    private int h = righe * quadretto;

    private Image immagineMuretti;
    private Image immagineFantasmaBlu;
    private Image immagineFantasmaArancio;
    private Image immagineFantasmaRosso;
    private Image immagineFantasmaRosa;

    private Image immaginePacmanUp;
    private Image immaginePacmanDown;
    private Image immaginePacmanSinistra;
    private Image immaginePacmanDestra;

    private String[] mappaMuretti = {
            "XXXXXXXXXXXXXXXXXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X                 X",
            "X XX X XXXXX X XX X",
            "X    X       X    X",
            "XXXX XXXX XXXX XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXrXX X XXXX",
            "O       bpo       O",
            "XXXX X XXXXX X XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXXXX X XXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X  X     P     X  X",
            "XX X X XXXXX X X XX",
            "X    X   X   X    X",
            "X XXXXXX X XXXXXX X",
            "X                 X",
            "XXXXXXXXXXXXXXXXXXX"
    };

    ArrayList<Blocco> muri_array;
    ArrayList<Blocco> cibo_array;
    ArrayList<Blocco> fant_array;
    Blocco pacman;

    Timer gameLoop;

    public Canvas() {
        setPreferredSize(new Dimension(w, h));
        setBackground(Color.black);
        addKeyListener(this); // aggiungo il keylistener
        setFocusable(true); // per il keylistener

        // carico le immagini
        // immagineMuretti
        immagineMuretti = new ImageIcon(getClass().getResource("./immagini/wall.png")).getImage();

        // fantasmi
        immagineFantasmaBlu = new ImageIcon(getClass().getResource("./immagini/blueGhost.png")).getImage();
        immagineFantasmaArancio = new ImageIcon(getClass().getResource("./immagini/orangeGhost.png")).getImage();
        immagineFantasmaRosso = new ImageIcon(getClass().getResource("./immagini/redGhost.png")).getImage();
        immagineFantasmaRosa = new ImageIcon(getClass().getResource("./immagini/pinkGhost.png")).getImage();

        // pacman
        immaginePacmanUp = new ImageIcon(getClass().getResource("./immagini/pacmanUp.png")).getImage();
        immaginePacmanDown = new ImageIcon(getClass().getResource("./immagini/pacmanDown.png")).getImage();
        immaginePacmanSinistra = new ImageIcon(getClass().getResource("./immagini/pacmanLeft.png")).getImage();
        immaginePacmanDestra = new ImageIcon(getClass().getResource("./immagini/pacmanRight.png")).getImage();

        init();
        gameLoop = new Timer(50, this); // 50 = delay this = pacman

        gameLoop.start(); // inizia il timer

    }

    public void init() {
        muri_array = new ArrayList<>();
        cibo_array = new ArrayList<>();
        fant_array = new ArrayList<>();

        for (int i = 0; i < righe; i++) {
            for (int j = 0; j < colonne; j++) {
                String colonna = mappaMuretti[i];
                // singolo carattere
                char carattereMappaMuretti = colonna.charAt(j);

                // posizione iniziale
                int x = j * quadretto; // numero di colonne x quadretto
                int y = i * quadretto; // numero di righe x quadretto

                if (carattereMappaMuretti == 'X') { // muro
                    Blocco muro = new Blocco(x, y, quadretto, quadretto, immagineMuretti);
                    muri_array.add(muro);
                } else if (carattereMappaMuretti == 'b') { // fantasma BLU
                    Blocco fantasma = new Blocco(x, y, quadretto, quadretto, immagineFantasmaBlu);
                    fant_array.add(fantasma);
                } else if (carattereMappaMuretti == 'o') { // fantasma ARANCIO
                    Blocco fantasma = new Blocco(x, y, quadretto, quadretto, immagineFantasmaArancio);
                    fant_array.add(fantasma);
                } else if (carattereMappaMuretti == 'p') { // fantasma ROSA
                    Blocco fantasma = new Blocco(x, y, quadretto, quadretto, immagineFantasmaRosa);
                    fant_array.add(fantasma);
                } else if (carattereMappaMuretti == 'r') { // fantasma ROSSO
                    Blocco fantasma = new Blocco(x, y, quadretto, quadretto, immagineFantasmaRosso);
                    fant_array.add(fantasma);
                } else if (carattereMappaMuretti == 'P') { // pacman
                    pacman = new Blocco(x, y, quadretto, quadretto, immaginePacmanDestra);
                } else if (carattereMappaMuretti == ' ') { // cibo
                    Blocco cibo = new Blocco(x + 14, y + 14, 4, 4, null);
                    cibo_array.add(cibo);
                }
            }
        }

    }

    public void cammina() {
        // movimento pacman
        pacman.x += pacman.velocitàX;
        pacman.y += pacman.velocitàY;

        // collisione con i muri
        for (Blocco muro : muri_array) {
            if (collisioneConMuro(pacman, muro)) {
                pacman.x -= pacman.velocitàX;
                pacman.y -= pacman.velocitàY;
            }
        }
    }

    public boolean collisioneConMuro(Blocco a, Blocco b) {
        return (a.x < b.x + b.w && a.x + a.w > b.x && a.y < b.y + b.h && a.y + a.h > b.y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        disegna(g);
    }

    public void disegna(Graphics g) {
        g.drawImage(pacman.image, pacman.x, pacman.y, pacman.w, pacman.h, null);
        for (Blocco fant : fant_array) {
            g.drawImage(fant.image, fant.x, fant.y, fant.w, fant.h, null);
        }

        for (Blocco muro : muri_array) {
            g.drawImage(muro.image, muro.x, muro.y, muro.w, muro.h, null);

        }
        g.setColor(Color.white);
        for (Blocco cibo : cibo_array) {
            g.fillRect(cibo.x, cibo.y, cibo.w, cibo.h);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cammina();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // System.out.println("KeyEvent: " + e.getKeyCode());
        // movimento pacman
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                pacman.aggiornaPosizione('U');
                pacman.image = immaginePacmanUp;
            }
            case KeyEvent.VK_DOWN -> {
                pacman.aggiornaPosizione('D');
                pacman.image = immaginePacmanDown;
            }
            case KeyEvent.VK_LEFT -> {
                pacman.aggiornaPosizione('L');
                pacman.image = immaginePacmanSinistra;
            }
            case KeyEvent.VK_RIGHT -> {
                pacman.aggiornaPosizione('R');
                pacman.image = immaginePacmanDestra;
            }
            default -> {
            }
        }
    }

}
