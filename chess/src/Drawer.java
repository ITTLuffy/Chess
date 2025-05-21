import javax.swing.*;

public class Drawer {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Chess");

        Canvas c = new Canvas();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(700, 790);
        frame.getContentPane().add(c);
        frame.setVisible(true);

        frame.setLocationRelativeTo(null);

        frame.setResizable(false);

    }

}
