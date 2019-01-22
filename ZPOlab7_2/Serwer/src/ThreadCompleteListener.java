public interface ThreadCompleteListener { //Interfjes potrzebny do stworzenia nasłuchiwacza w klasie NotifyingThread
    void notifyOfThreadComplete(final Thread thread);
}
