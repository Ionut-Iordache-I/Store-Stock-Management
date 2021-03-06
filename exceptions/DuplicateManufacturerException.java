package exceptions;

public class DuplicateManufacturerException extends Exception {
    public DuplicateManufacturerException() {
        super("Nu se poate adauga producatorul deoarece exista deja!");
    }
}
