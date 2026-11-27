
package exception;

public class ServiceUnavailableException extends ApiException {

    public ServiceUnavailableException(String message) {
        super(message, 503);
    }
}
