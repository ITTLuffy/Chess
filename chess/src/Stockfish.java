import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Stockfish {

    // Variabili per gestire il processo di Stockfish
    private final Process stockfishProcess;
    // Stream per comunicare con Stockfish
    private final BufferedWriter stockfishWriter;
    // Stream per leggere le risposte di Stockfish
    private final BufferedReader stockfishReader;

    // Costruttore che avvia il processo di Stockfish
    public Stockfish(String pathToStockfish) throws IOException {
        // Avvia il processo di Stockfish
        ProcessBuilder pb = new ProcessBuilder(pathToStockfish);
        stockfishProcess = pb.start();
        // Inizializza gli stream per comunicare con Stockfish
        stockfishWriter = new BufferedWriter(new OutputStreamWriter(stockfishProcess.getOutputStream()));
        stockfishReader = new BufferedReader(new InputStreamReader(stockfishProcess.getInputStream()));
    }

    // Invia un comando a Stockfish
    public void sendCommand(String command) throws IOException {
        stockfishWriter.write(command + "\n");
        // pulisco il writer
        stockfishWriter.flush();
    }

    // Legge la risposta di Stockfish fino a una riga che contiene una certa keyword
    public String readResponse(String waitFor) throws IOException {
        // Linea di risposta
        String linea;
        // StringBuilder per accumulare la risposta
        StringBuilder response = new StringBuilder();
        // Legge le righe fino a trovare quella che contiene la keyword
        while ((linea = stockfishReader.readLine()) != null) {
            // Aggiunge la linea alla risposta e vado a capo
            response.append(linea).append("\n");
            // Se la linea contiene la keyword, esce dal ciclo
            if (linea.contains(waitFor)) {
                break;
            }
        }
        // Restituisce la risposta accumulata
        return response.toString();
    }

    public String getBestMove(String fen) throws IOException {
        sendCommand("uci");
        readResponse("uciok");  // aspetta conferma UCI

        sendCommand("isready");
        readResponse("readyok");

        sendCommand("position fen " + fen);
        sendCommand("go movetime 1000");  // calcola per 1 secondo

        String bestMove = "";
        String line;
        while ((line = stockfishReader.readLine()) != null) {
            if (line.startsWith("bestmove")) {
                bestMove = line.split(" ")[1];
                break;
            }
        }
        return bestMove;
    }

    // Metodo per chiudere il processo di Stockfish
    public void close() throws IOException {
        sendCommand("quit");
        stockfishWriter.close();
        stockfishReader.close();
        stockfishProcess.destroy();
    }


}
