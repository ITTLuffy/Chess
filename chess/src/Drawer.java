import javax.swing.*;
import java.awt.*;

public class Drawer {

    public static void main(String[] args) {

        // Creazione della finestra principale
        JFrame frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 770);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Testo di benvenuto
        String html = "<html><br><div style='text-align: center;'>Benvenuto in <br>CHESS</div></html>";
        JLabel label = new JLabel(html, JLabel.CENTER);
        label.setFont(new Font("Times new Roman", Font.BOLD, 40));
        label.setBackground(Color.GRAY);
        label.setForeground(Color.BLACK);
        label.setOpaque(true);
        frame.add(label, BorderLayout.NORTH);

        // Pulsante nuova partita
        JPanel pulsantiera = new JPanel(new GridBagLayout());
        pulsantiera.setBackground(Color.GRAY);
        JButton nuovaPartita = new JButton("Nuova partita");
        nuovaPartita.setFont(new Font("Times new Roman", Font.BOLD, 30));
        nuovaPartita.setBackground(new Color(108, 187, 60));
        nuovaPartita.setForeground(Color.BLACK);
        nuovaPartita.setFocusPainted(false); // rimuovo il bordino bianco che mi dà fastidio
        pulsantiera.add(nuovaPartita);
        frame.add(pulsantiera, BorderLayout.CENTER);

        // Copyright e autore
        String html2 = "<html><div style='text-align: center;'>A cura di: Alessio Ferrari </div>" +
                "<div style='text-align: center;'>&copy; 2025 <br></html>";
        JLabel label2 = new JLabel(html2, JLabel.CENTER);
        label2.setFont(new Font("Times new Roman", Font.BOLD, 20));
        label2.setBackground(Color.GRAY);
        label2.setForeground(Color.BLACK);
        label2.setOpaque(true);
        frame.add(label2, BorderLayout.SOUTH);

        // Nome giocatore nero e bianco
        String html3 = "<html><div style='text-align: center;'>Giocatore Nero: <em>Stockfish</html>";
        String html4 = "<html><div style='text-align: center;'>Giocatore Bianco: <em>Io</html>";
        JLabel gNero = new JLabel(html3, JLabel.CENTER);
        JLabel gBianco = new JLabel(html4, JLabel.CENTER);
        gNero.setBackground(Color.gray);
        gBianco.setBackground(Color.gray);

        // Inizio partita
        nuovaPartita.addActionListener(e -> {
            frame.getContentPane().removeAll();  // Rimuove tutto ciò che era visibile prima
            frame.getContentPane().add(gNero, BorderLayout.NORTH);
            frame.getContentPane().add(gBianco, BorderLayout.SOUTH);
            frame.getContentPane().add(new Canvas(), BorderLayout.CENTER);  // Aggiunge la scacchiera
            frame.revalidate();
            frame.repaint();
        });


        frame.setVisible(true);

    }

}
