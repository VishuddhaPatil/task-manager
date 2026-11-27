
package exception;

public class BadGatewayException extends ApiException {

    public BadGatewayException(String message) {
        super(message, 502);
    }
}
