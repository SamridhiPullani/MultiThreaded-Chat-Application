import java.io.*;
import java.net.*;
import java.util.*;

public class server {
    private static final int PORT = 1234;
    private static Set<Socket> clientSockets = new HashSet<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started...");

        while (true) {
            Socket socket = serverSocket.accept();
            clientSockets.add(socket);
            new ClientHandler(socket).start();
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;
        private BufferedReader input;
        private PrintWriter output;

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        }

        public void run() {
            try {
                String message;
                while ((message = input.readLine()) != null) {
                    for (Socket s : clientSockets) {
                        if (s != socket) {
                            new PrintWriter(s.getOutputStream(), true).println("Client says: " + message);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}