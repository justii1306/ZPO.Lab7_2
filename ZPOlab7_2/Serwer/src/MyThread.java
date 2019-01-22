import static java.lang.Math.sqrt;

public class MyThread extends NotifyingThread {
    private int start;
    private int end;
    private int primeNumbers;

    public MyThread(int start, int end) {
        System.out.println("Thread " + this.getName() + " is calculating interval [" + start + ";" + end + "]."); //Napisz jaki przedział sprawdzamy
        this.start = start;
        this.end = end;
        this.primeNumbers = 0;
    }

    private int checkPrimes(){
        int result = 0;
        for (int i = this.start; i <= this.end; i++) //Przeszukaj cały przedział
            if (isPrime(i)) //Jeśli liczba jest liczbą pierwszą
                ++result; //Zwiększ wynik
        return result;
    }

    private static boolean isPrime(int number){ //Metoda sprawdzająca czy dana liczba jest liczbą pierwszą
        if (number == 1) return false; //1 nie jest pierwsza
        if ((number == 2) || (number == 3)) return true; //2 i 3 jest
        if (number % 2 == 0) return false; //Liczby parzyste nie są pierwsze
        for (int i = 3; i <= sqrt(number); i+=2) //Podziel sprawdzaną liczbę przez wszystkie liczby nieparyste większe od 3 i mniejsze od pierwiastka z sprawdzanej liczby 
            if (number % i == 0) return false;
        return true; //To będzie mieło miejsce tylko jeśli wszystkie poprzednie if'y nie były prawdziwe, oznacza to, że liczba jest pierwsza
    }

    public int getPrimeNumbers() {
        return primeNumbers;
    }

    public void setPrimeNumbers(int primeNumbers) {
        this.primeNumbers = primeNumbers;
    }

    @Override
    public void doRun() { //Tu rozpoczynamy
        setPrimeNumbers(checkPrimes()); //Ustaw zmienną PrimeNumber (liczba znalezionych liczb pierwszych) jako wynik metody checkPrimes()
    }
}
