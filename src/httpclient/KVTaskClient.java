package httpclient;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {

    private final String URL = "http://localhost:8080/";
    private String apiToken;

    public KVTaskClient() throws IOException, InterruptedException {
        register();
    }

    private void register() { // Авторизация APITOKEN
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest build = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "register"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(build, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                apiToken = response.body();
            } else {
                throw new RuntimeException("Не удалось получить API_TOKEN. Код ошибки: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса /register возникла ошибка.\n" +
                    "Проверьте, адрес и повторите попытку.");
        }
    }



    public void save(String key, String value){ // Сохранение на KVServer
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest build = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "save/" + key + "?API_TOKEN=" + apiToken))
                    .POST(HttpRequest.BodyPublishers.ofString(value))
                    .build();
            httpClient.send(build, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    public String load(String key) { // Загрузка данных с KVServer
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest build = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "load/" + key + "?API_TOKEN=" + apiToken))
                    .GET()
                    .build();
            return httpClient.send(build, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}