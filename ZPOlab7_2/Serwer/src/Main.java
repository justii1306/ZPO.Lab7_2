import java.io.*;
import java.net.*;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

class Main {

    public static void main(String argv[]) {
        try {
            String clientSentence; //Przygotuj stringa, którego odbierzesz od klienta
            ServerSocket welcomeSocket = new ServerSocket(6789); //Ustal na jakim porcie nasłuchujesz

            while (true) {
                Socket connectionSocket = welcomeSocket.accept(); //Czekaj na połączenie
                BufferedReader inFromClient = 
                        new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); //Przygotuj buffer do którego wpisujesz dane od klienta
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream()); //Przygotuj buffer z którego wypisujesz dane do klienta
                clientSentence = inFromClient.readLine(); //Odczytaj dane od klienta
                System.out.println("Received: " + clientSentence); //Pokaż co odczytałeś
                String[] pairOfNumbers = clientSentence.split(" "); //Rozdziel to według spacji
                int start = Integer.valueOf(pairOfNumbers[0]); //Pierwsza rzecz jest początkiem przedziału
                int end = Integer.valueOf(pairOfNumbers[1]); //Druga końcem przedziału

                Primes threads = new Primes(start, end); //Stwórz nowy obiekt Primes
                while(!threads.isDone()) {} //Poczekaj aż skończy obliczenia
                long timerStop = System.nanoTime(); //Zatrzymaj stoper (w klasie Primes go odpaliliśmy)
                double estimatedTime = (double) (timerStop-threads.getTimerStart()) / 1_000_000_000.0; //Przekonwertuj z nanosekund
                outToClient.writeBytes("Prime numbers in range: " + threads.getResult() + 
                        ". Time needed to calculate : " + estimatedTime + "ms." + '\n'); //Wyślij do klienta wyniki
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        } catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
}
