import java.io.*;
import java.net.*;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

class Main {

    public static void main(String argv[]) {
        try {
            String clientSentence;
            ServerSocket welcomeSocket = new ServerSocket(6789);

            while (true) {
                Socket connectionSocket = welcomeSocket.accept();
                BufferedReader inFromClient =
                        new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                clientSentence = inFromClient.readLine();
                System.out.println("Received: " + clientSentence);
                String[] pairOfNumbers = clientSentence.split(" ");
                int start = Integer.valueOf(pairOfNumbers[0]);
                int end = Integer.valueOf(pairOfNumbers[1]);

                Primes threads = new Primes(start, end);
                while(!threads.isDone()) {}
                long timerStop = System.nanoTime();
                double estimatedTime = (double) (timerStop-threads.getTimerStart()) / 1_000_000_000.0;
                outToClient.writeBytes("Prime numbers in range: " + threads.getResult() +
                        ". Time needed to calculate : " + estimatedTime + "ms." + '\n');
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
}