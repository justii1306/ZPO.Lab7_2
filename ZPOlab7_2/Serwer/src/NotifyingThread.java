import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class NotifyingThread extends Thread{
    private final Set<ThreadCompleteListener> listeners = new CopyOnWriteArraySet<ThreadCompleteListener>(); //Przygotowujemy nasłuchiwacz, który będzie przechowywał informacje, czy wątki zakończyły pracę

    public final void addListener(final ThreadCompleteListener listener) { //Metoda dodająca wątek do nasłuchiwania
        listeners.add(listener); 
    }

    public final void removeListener(final ThreadCompleteListener listener) { //Metoda usuwająca z nasłuchiwania (nie używamy)
        listeners.remove(listener); 
    }

    private final void notifyListeners() { //Metoda informująca, że wątek skończył pracę
        for (ThreadCompleteListener listener : listeners) {
            listener.notifyOfThreadComplete(this);
        }
    }

    @Override
    public final void run() { 
        try {
            doRun(); //Wykonaj metodę doRun() z klasy MyThread
        } finally {
            notifyListeners(); //Po wykonaniu pracy poinformuj, że skończyliśmy pracę
        }
    }

    public abstract void doRun();
}
