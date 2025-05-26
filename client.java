package MultiThread;
import java.io.*;
import java.net.*;

public class client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        new Thread(() -> {
            try {
                String serverMsg;
                while ((serverMsg = input.readLine()) != null) {
                    System.out.println(serverMsg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        String userInput;
        while ((userInput = console.readLine()) != null) {
            output.println(userInput);
        }
    }
}