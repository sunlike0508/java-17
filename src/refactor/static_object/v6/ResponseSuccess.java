package refactor.static_object.v6;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;

public non-sealed class ResponseSuccess extends Response{

    private static final int STATUS_CODE = 200;

    ResponseSuccess(HttpExchange exchange) {
       super(exchange);
    }

    @Override
    protected int httpStatusCode() {
        return STATUS_CODE;
    }
}
