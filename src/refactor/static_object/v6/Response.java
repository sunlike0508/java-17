package refactor.static_object.v6;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;

public abstract sealed class Response permits ResponseSuccess, ResponseNotFound, ResponseCreated {


    private final HttpExchange exchange;

    Response(HttpExchange exchange) {
        this.exchange = exchange;
    }


    protected void send(String content) throws IOException {
        exchange.sendResponseHeaders(httpStatusCode(), content.getBytes().length);

        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(content.getBytes());
        outputStream.flush();
        outputStream.close();
    }


    protected abstract int httpStatusCode();
}
