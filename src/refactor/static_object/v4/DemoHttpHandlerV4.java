package refactor.static_object.v4;

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
 * v3의 단점을 극복하기 위해 ResponseSuccess라는 객체를 만들어보자.
 * <p>이렇게 하면 DemoHttpHandler은 성공 응답을 보내는 책임까지 가지고 있었으나 이것을 ResponseSuccess에게 책임을 전가했다.</p>
 * <p>이 다음으로 생각해보면 exchange를 메소드 인자로 전달할 필요가 있을까? </p>
 * <p>메소드 인자로 전달한다는 이야기는 메소드 인자로 전달하는 쪽이 책임이 있다는 이야기</p>
 * <p>따라서 인자가 아닌 ResponseSuccess가 멤벼변수로 가질수 있게 생성자에 전달하자.</p>
 * <p>이때 exchage를 또는 content 둘 중 어느 것을 필드로 가져갈지에 따라 객체의 책임이 달라진다.</p>
 */

public class DemoHttpHandlerV4 implements HttpHandler {

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

         new ResponseSuccess().send(exchange, "Hello, World");
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
        new ResponseSuccess().send(exchange, "");
    }


    private void handleDetail(HttpExchange exchange, Task task) throws IOException {
        new ResponseSuccess().send(exchange, toJson(task));
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
        new ResponseSuccess().send(exchange, toJson(tasks));
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
