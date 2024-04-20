import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class EchoClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8080;

        try (Socket echoSocket = new Socket(host, port);
             OutputStream out = echoSocket.getOutputStream();
             BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.write((userInput + '\n').getBytes(StandardCharsets.UTF_8));
                out.flush();
                System.out.println("echo: " + in.readLine());
            }

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + host);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("I/O exception: " + e);
            System.exit(1);
        }
    }
}