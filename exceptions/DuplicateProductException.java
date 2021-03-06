package exceptions;

public class DuplicateProductException extends Exception {
    public DuplicateProductException() {
        super("Nu se poate adauga produsul deoarece exista deja!");
    }
}
