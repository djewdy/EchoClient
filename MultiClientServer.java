import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class MultiClientServer {

    private static final int PORT = 9090;
    private static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to client at " + clientSocket.getRemoteSocketAddress());
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Message from " + clientSocket.getRemoteSocketAddress() + ": " + inputLine);
                    System.out.println("Server's receiving port: " + clientSocket.getLocalPort());
                    System.out.println("Client's sending port: " + clientSocket.getPort());
                }
            } catch (IOException e) {
                System.err.println("Exception in ClientHandler: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
