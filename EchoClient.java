import java.io.*;
import java.net.*;

public class EchoClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9090;

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);

                System.out.println("You sent: '" + userInput + "'");
                System.out.println("Your IP address: " + socket.getLocalAddress().getHostAddress());
                System.out.println("Receiving port : " +socket.getLocalPort() );
                System.out.println("Your sending port: " + SERVER_PORT);
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + SERVER_IP);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    SERVER_IP);
            System.exit(1);
        }
    }
}
