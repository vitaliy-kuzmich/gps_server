package vitaliy.kuzmich.excp;

public class WrongFormatException extends RuntimeException {
    public WrongFormatException(String err) {
        super(err);
    }
}
