import java.awt.Image;

public class Blocco {
    int x;
    int y;
    int w;
    int h;
    Image image;
    int quadretto = 32; // pixel

    int xIniziale;
    int yIniziale;

    char direzione = 'U';
    int velocitàX = 0;
    int velocitàY = 0;

    public Blocco() {
    }

    public Blocco(int x, int y, int w, int h, Image image) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.image = image;
        this.xIniziale = x;
        this.yIniziale = y;
    }

    public void aggiornaPosizione(char direzione) {
        char direzionePrecedente = this.direzione;

        this.direzione = direzione;

        aggiornaVelocita();

        // faccio il movimento
        this.y += velocitàY;
        this.x += velocitàX;

        for (Blocco muro : muri_array) {
            if (collisioneConMuro(this, muro)) {
                // se c'è una collisione, annullo il movimento
                this.x -= velocitàX;
                this.y -= velocitàY;
                this.direzione = direzionePrecedente; // ripristino la direzione precedente
                aggiornaVelocita();
            }
        }

    }


    public void aggiornaVelocita() {
        switch (this.direzione) {
            case 'U':
                this.velocitàX = 0;
                this.velocitàY = -quadretto / 4;
                break;
            case 'D':
                this.velocitàX = 0;
                this.velocitàY = quadretto / 4;
                break;
            case 'L':
                this.velocitàX = -quadretto / 4;
                velocitàY = 0;
                break;
            case 'R':
                this.velocitàX = quadretto / 4;
                this.velocitàY = 0;
                break;
        }
    }

}
