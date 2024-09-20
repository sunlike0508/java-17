package refactor.static_object.v5;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;

public class ResponseNotFound {

    private static final int STATUS_CODE = 404;

    private final HttpExchange exchange;

    ResponseNotFound(HttpExchange exchange) {
        this.exchange = exchange;
    }


    public void send(String content) throws IOException {
        exchange.sendResponseHeaders(STATUS_CODE, content.getBytes().length);

        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(content.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
