package refactor.static_object.v6;

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
 * 이렇게 Response라는 추상 객체가 나온다.
 * <P>결국 핵심은 리팩토링 전에는 어떤 값을 가져와야 한다. 왜 가져오냐? 가져온 값을 어떤 요청을 하기 위해서 그 값을 보내야 한다. 이게 중점이었는데</P>
 * <p>이제는 나는(httppHandler) 보내는 요청만 할꺼니까 지지든 볶든 값이 어쨌든 너가 알아서 보내! 라고 책임을 떠넘기는 것.</p>
 * <p>이렇게 되면 핸들러는 httpcode 값이 알게 뭐야? 나는 get, post에 따라 send만 호출하는 역할만 하면 되는데?</p>
 * <p>뭐 http 코드가 200이든 send안에서 outputstream을 쓰든 그건 니가 알아서 해</p>
 * <p>관점을 바꾸자.</p>
 * <p>어떻게 이걸 만들어서 보내지? 이게 아니라 어떻게 하면 이걸 떠넘기지?</p>
 */
public class DemoHttpHandlerV6 implements HttpHandler {

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
