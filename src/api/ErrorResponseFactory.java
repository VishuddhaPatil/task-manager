
package api;

import exception.ApiException;

public class ErrorResponseFactory {

    private ErrorResponseFactory() {
    }

    public static <T> ApiResponse<T> create(Exception exception) {

        if (exception instanceof ApiException) {

            ApiException apiException = (ApiException) exception;

            return ApiResponse.error(
                    apiException.getStatusCode(),
                    apiException.getMessage()
            );
        }

        return ApiResponse.error(
                500,
                "Internal server error"
        );
    }
}
