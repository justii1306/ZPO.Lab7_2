import static java.lang.Math.ceil;
import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

public class Primes implements ThreadCompleteListener{
    private int result;
    private long timerStart;
    private boolean done;
    private static int counter;

    public Primes(int trueStart, int trueEnd) throws InterruptedException {
        int middlePoint = (int) floor((trueEnd - trueStart) * (3.0/4.0)) + trueStart;
        counter = 0;
        setResult(0);
        setDone(false);
        NotifyingThread thread1 = new MyThread(trueStart, middlePoint);
        thread1.addListener(this);
        NotifyingThread thread2 = new MyThread(middlePoint, trueEnd);
        thread2.addListener(this);

        setTimerStart(System.nanoTime());
        thread1.start();
        thread2.start();
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
    public void notifyOfThreadComplete(Thread thread) {
        counter++;
        if(counter == 2) setDone(true);
        MyThread myThread = (MyThread) thread;
        System.out.println(thread.getName() + " is adding: " + myThread.getPrimeNumbers());
        setResult(getResult() + myThread.getPrimeNumbers());
    }
}
