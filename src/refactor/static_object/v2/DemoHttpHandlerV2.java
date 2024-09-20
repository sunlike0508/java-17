package refactor.static_object.v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import refactor.static_object.Task;


/**
 * 2차로는 공통으로 쓰고 싶어서 하나의 클래스로 생성해서 상수를 모아놓음
 */

public class DemoHttpHandlerV2 implements HttpHandler {

    /**
     * <p>그러면 HttpStatus가 클래스면 아래와 같이 쓸수 있는데 이게 맞는걸까?</p>
     * <p>HttpStatus httpStatus = new HttpStatus(); </p>
     * <p>객체지행은 객체끼리 협업을 하는 프로그램인데 이건 객체도 아니고 아무것도 아닌 그냥 울타리 느낌</p>
     * <p>막 모아놓고 공용으로 쓰기위한 하나의 수단으로 사용. 클래스가 그런 용도인가? </p>
     */

    //HttpStatus httpStatus = new HttpStatus();

    List<Task> tasks = new ArrayList<Task>();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        
        String path = exchange.getRequestURI().getPath();
        
        if(path.equals("/tasks")) {
            handleCollection(exchange, method);
            return;
        }

        if(path.startsWith("/tasks")) {
            Long id = Long.parseLong(path.substring("/tasks/".length()));
            handleItem(exchange, method, id);
            return;
        }

        send(exchange, HttpStatus.HTTP_STATUS_OK, "Hello, World");
    }


    private void handleItem(HttpExchange exchange, String method, Long id) throws IOException {

        Task task = findTask(id);

        if(task == null) {
            send(exchange, HttpStatus.HTTP_STATUS_NOT_FOUND, "");
            return;
        }

        if(method.equals("GET")) {
            handleDetail(exchange, task);
        }

        if(method.equals("PUT")) {
            handleUpdate(exchange, task);
        }
    }


    private void handleUpdate(HttpExchange exchange, Task task) throws IOException {
        tasks.remove(task);
        send(exchange, HttpStatus.HTTP_STATUS_OK, "");
    }


    private void handleDetail(HttpExchange exchange, Task task) throws IOException {
        send(exchange, HttpStatus.HTTP_STATUS_OK, toJson(task));
    }


    private void handleCollection(HttpExchange exchange, String method) throws IOException {

        if(method.equals("GET")) {
            handleList(exchange);
        }

        if(method.equals("POST")) {
            handleCreate(exchange);
        }
    }


    private void handleCreate(HttpExchange exchange) throws IOException {
        String body = getBody(exchange);

        Task task = toTask(body);
        task.setId(generateId());
        tasks.add(task);

        send(exchange, HttpStatus.HTTP_STATUS_CREATED, toJson(task));
    }

    private void handleList(HttpExchange exchange) throws IOException {
        send(exchange, HttpStatus.HTTP_STATUS_OK, toJson(tasks));
    }

    private void send(HttpExchange exchange, int statusCode, String content) throws IOException {

        exchange.sendResponseHeaders(statusCode, content.getBytes().length);

        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(content.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private Task findTask(Long id) {
        return tasks.stream().filter(task -> task.getId().equals(id)).findFirst().orElse(null);
    }


    private String toJson(Object object) {
        /**
         * 원래는 objectmapper로 변환하는건데 그냥 tostringd으로 변경 어차피 의미없음
         */
        return object.toString();
    }


    private Long generateId() {
        return System.currentTimeMillis();
    }


    private Task toTask(String body) {
        /**
         * 원래는 objectmapper로 변환하는건데 그냥 body 값 세팅해주는 예시로 변경
         */
        Task task = new Task();
        task.setBody(body);

        return task;
    }


    private String getBody(HttpExchange exchange) {
        InputStream inputStream = exchange.getRequestBody();
        return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
    }




}
