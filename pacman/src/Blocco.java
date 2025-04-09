import java.awt.Image;

public class Blocco {
    int x;
    int y;
    int w;
    int h;
    Image image;
    
    int xIniziale;
    int yIniziale;

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
    
    
}
