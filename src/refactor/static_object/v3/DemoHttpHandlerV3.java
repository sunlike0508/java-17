package refactor.static_object.v3;

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
 * <p>그래서 나름 이펙티브 자바도 읽고 나름의 고수(?)라고 생각하면 HttpStatus를 Enum으로 변경한다.</p>
 * <p>나름 좋아보이지만, 아직 애매하다. 과연 이건 캡슐화도 잘되어 있다고 보이는가?</p>
 * <p>HttpStatus가 능동적으로 무언가를 하는 객체로 보이기에는 다소 무리가 있다.</p>
 * <p>단지 자기가 가지고 있는 데이터만 필요한 객체에게 값을 전달하는 역할만 하고 있다. 즉, 협력하는 객체가 아니다.</p>
 * <p>그러면 DemoHttpHandler가 HttpStatus.OK.getValue()의 값을 알필요가 있을까?</p>
 * <p>단순히 성공한 응답을 보내라! 라는 요청만 하면 된다.</p>
 */

public class DemoHttpHandlerV3 implements HttpHandler {

    /**
     * 이런식으로 쓸수도 있다.
     */
    public String getSuccessDescription() {
        return HttpStatus.OK.getDescription();
    }


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

        send(exchange, HttpStatus.OK.getValue(), "Hello, World");
    }


    private void handleItem(HttpExchange exchange, String method, Long id) throws IOException {

        Task task = findTask(id);

        if(task == null) {
            send(exchange, HttpStatus.NOT_FOUND.getValue(), "");
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
        send(exchange, HttpStatus.OK.getValue(), "");
    }


    private void handleDetail(HttpExchange exchange, Task task) throws IOException {
        send(exchange, HttpStatus.OK.getValue(), toJson(task));
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

        send(exchange, HttpStatus.CAEATED.getValue(), toJson(task));
    }

    private void handleList(HttpExchange exchange) throws IOException {
        send(exchange, HttpStatus.OK.getValue(), toJson(tasks));
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
