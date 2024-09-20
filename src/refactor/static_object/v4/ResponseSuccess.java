package refactor.static_object.v4;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;

public class ResponseSuccess {

    /**
     * 이렇게 하면 200이라는 코드는 완벽한 ResponseSuccess 객체의 책임이 된다.
     * <p>따라서 굳이 변수로 선언할 필요 없고</p>
     * <p>exchange.sendResponseHeaders(200, content.getBytes().length);</p>
     * <p>이렇게 바로 써도 된다.</p>
     */
    private static final int STATUS_CODE = 200;

    public void send(HttpExchange exchange, String content) throws IOException {
        exchange.sendResponseHeaders(STATUS_CODE, content.getBytes().length);

        //exchange.sendResponseHeaders(200, content.getBytes().length);


        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(content.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
