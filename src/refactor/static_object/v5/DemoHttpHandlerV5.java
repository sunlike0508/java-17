package refactor.static_object.v5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import refactor.static_object.Task;


/**
 *. 이렇게 하면 ResponseSuccess 라는 객체는 200을 숨겨줬고(캡슐화), 책임분리가 잘되었다.
 * <p>이 상태에서 나머지 created, not found도 똑같이 만들수 있다.</p>
 * <p>근데 만들면서 보니 created, not found는 ResponseSuccess 복사하면서 만들었네? 그리고 안에 코드값만 변경했네?</p>
 * <p>공통의 추상객체가 나오겠는데?</p>
 */
public class DemoHttpHandlerV5 implements HttpHandler {


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

         new ResponseSuccess(exchange).send("Hello, World");
    }


    private void handleItem(HttpExchange exchange, String method, Long id) throws IOException {

        Task task = findTask(id);

        if(task == null) {
            new ResponseNotFound(exchange).send("");
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
        new ResponseSuccess(exchange).send("");
    }


    private void handleDetail(HttpExchange exchange, Task task) throws IOException {
        new ResponseSuccess(exchange).send(toJson(task));
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

        new ResponseCreated(exchange).send(toJson(task));
    }

    private void handleList(HttpExchange exchange) throws IOException {
        new ResponseSuccess(exchange).send(toJson(tasks));
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
