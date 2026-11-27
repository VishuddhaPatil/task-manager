
package exception;

public class ValidationException extends ApiException {

    public ValidationException(String message) {
        super(message, 400);
    }
}
