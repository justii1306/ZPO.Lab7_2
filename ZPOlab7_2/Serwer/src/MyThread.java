import static java.lang.Math.sqrt;

public class MyThread extends NotifyingThread {
    private int start;
    private int end;
    private int primeNumbers;

    public MyThread(int start, int end) {
        System.out.println("Thread " + this.getName() + " is calculating interval [" + start + ";" + end + "].");
        this.start = start;
        this.end = end;
        this.primeNumbers = 0;
    }

    private int checkPrimes(){
        int result = 0;
        for (int i = this.start; i <= this.end; i++)
            if (isPrime(i))
                ++result;
        return result;
    }

    private static boolean isPrime(int number){
        if (number == 1) return false;
        if ((number == 2) || (number == 3)) return true;
        if (number % 2 == 0) return false;
        for (int i = 3; i <= sqrt(number); i+=2)
            if (number % i == 0) return false;
        return true;
    }

    public int getPrimeNumbers() {
        return primeNumbers;
    }

    public void setPrimeNumbers(int primeNumbers) {
        this.primeNumbers = primeNumbers;
    }

    @Override
    public void doRun() {
        setPrimeNumbers(checkPrimes());
    }
}