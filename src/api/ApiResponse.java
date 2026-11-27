
package api;

public class ApiResponse<T> {

    private final boolean success;
    private final int statusCode;
    private final String message;
    private final T data;

    public ApiResponse(
            boolean success,
            int statusCode,
            String message,
            T data
    ) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static <T> ApiResponse<T> success(
            String message,
            T data
    ) {
        return new ApiResponse<>(true, 200, message, data);
    }

    public static <T> ApiResponse<T> error(
            int statusCode,
            String message
    ) {
        return new ApiResponse<>(false, statusCode, message, null);
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
