import static java.lang.Math.ceil;
import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

public class Primes implements ThreadCompleteListener{
    private int result;
    private long timerStart;
    private boolean done;
    private static int counter;

    public Primes(int trueStart, int trueEnd) throws InterruptedException {
        int middlePoint = (int) floor((trueEnd - trueStart) * (3.0/4.0)) + trueStart; //Rozdziel przedział (dzielenie przez dwa nie jest optymalne, bo mniejsze liczby łatwiej obliczyć)
        counter = 0; //Zmienna mówiąca ile wątków skończyło pracę, są dwa wątki, więc jeśli counter == 2, to skończyliśmy obliczenia
        setResult(0); //Zerujemy wynik
        setDone(false); //Ustawiamy, że nie skończyliśmy obliczeń (bo dopiero je zaczynamy)
        NotifyingThread thread1 = new MyThread(trueStart, middlePoint); //Daj pierwszą (większą, ale łatwiejszą) część jednemu wątkowi
        thread1.addListener(this); //Nasłuchuj żeby sprawdzić czy skończył pracę
        NotifyingThread thread2 = new MyThread(middlePoint, trueEnd); //Daj drugą (mniejszą, ale cięższą) część drugiemu wątkowi
        thread2.addListener(this); //Nasłuchuj żeby sprawdzić czy skończył pracę

        setTimerStart(System.nanoTime()); //Odpal stoper
        thread1.start(); //Zacznij wykonywać obliczenia, wykonaj metodę run() z klasy NotifyingThread
        thread2.start(); //Zacznij wykonywać obliczenia, wykonaj metodę run() z klasy NotifyingThread
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public long getTimerStart() {
        return timerStart;
    }

    public void setTimerStart(long timerStart) {
        this.timerStart = timerStart;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Primes.counter = counter;
    }

    @Override
    public void notifyOfThreadComplete(Thread thread) { //Kiedy wątek skończy pracę to wykona tą metodę
        counter++; //Zwiększy counter sygnalizując, że zakończył pracę
        if(counter == 2) setDone(true); //Sprawdz czy ten drugi wątek też zakończył pracę (jeśli tak, to counter == 2), jeśli zakończył, to ustaw zmienną Done jako prawdę
        MyThread myThread = (MyThread) thread; //Zmienna, żeby można było pobrać nazwę wątku
        System.out.println(thread.getName() + " is adding: " + myThread.getPrimeNumbers()); //Wypisz nazwę wątku, oraz ile liczb pierwszych znalazł
        setResult(getResult() + myThread.getPrimeNumbers()); //Ustaw wynik jako to co już było w wyniku (jeśli inny wątek już coś do niego dodał) oraz dodaj do niego ilość znalezionych liczb obecnego wątku
    }
}
