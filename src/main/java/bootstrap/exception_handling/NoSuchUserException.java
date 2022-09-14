package bootstrap.exception_handling;

// При создании Exception, мы будем передавать String message в конструктор
public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String message) {
        super(message);
    }
}
