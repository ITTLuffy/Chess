import javax.swing.JFrame;

public class Drawer {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Chess");

        Canvas c = new Canvas();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(690, 790);
        frame.add(c);
        frame.setVisible(true);

        frame.setLocationRelativeTo(null);

        frame.setResizable(false);

    }

}
