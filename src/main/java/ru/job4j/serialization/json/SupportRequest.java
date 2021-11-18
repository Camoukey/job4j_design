package ru.job4j.serialization.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SupportRequest {
    private boolean completed = false;
    private int totalCost;
    private String address;
    private Agent executor;
    private ArrayList<String> completedWorks = new ArrayList<>();

    public SupportRequest(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SupportRequest{"
                + "completed=" + completed
                + ", totalCost=" + totalCost
                + ", address='" + address + '\''
                + ", executor=" + executor
                + ", completedWorks=" + completedWorks
                + '}';
    }

    public boolean isCompleted() {
        return completed;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getCompletedWorks() {
        return completedWorks;
    }

    public void addCompletedWork(String completedWork) {
        this.completedWorks.add(completedWork);
    }

    public void setCompleted() {
        this.completed = true;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public void setExecutor(Agent executor) {
        this.executor = executor;
    }

    public static void main(String[] args) {
        SupportRequest request =  new SupportRequest("ул. Пушкина");
        Agent agent = new Agent("Петя", "техник");
        request.setExecutor(agent);
        request.setCompleted();
        request.setTotalCost(1000);
        request.addCompletedWork("Обжим коннектора");
        request.addCompletedWork("Настройка роутера");

        /* Преобразуем объект person в json-строку. */
        Gson gson = new GsonBuilder().create();
        System.out.println(gson.toJson(request));

        /* Модифицируем json-строку */
        String customRequest =
                "{"
                    + "\"address\":\"ул. Ленина\","
                    + "\"executor\":"
                    + "{"
                        + "\"name\":\"Дима\","
                        + "\"position\":\"старший техник\""
                    + "},"
                    + "\"completed\":false,"
                    + "\"totalCost\":250,"
                    + "\"completedWorks\":"
                    + "[\"Обжим коннектора\",\"Настройка роутера\"]"
                    + "}";
        request = gson.fromJson(customRequest, SupportRequest.class);
        System.out.println(request);

        /*Создаем JSONObject для класса SupportRequest*/
        JSONObject jsonRequest = new JSONObject();
        /*Создаем JSONObject для класса Agent*/
        JSONObject jsonAgent = new JSONObject();

        /*Добавляем поля в json объект*/
        jsonAgent.put("name", agent.getName());
        jsonAgent.put("position", agent.getPosition());
        jsonRequest.put("completed", request.isCompleted());
        jsonRequest.put("totalCost", request.getTotalCost());
        jsonRequest.put("address", request.getAddress());
        jsonRequest.put("executor", jsonAgent);
        jsonRequest.put("completedWorks", request.getCompletedWorks());

        /*Конвертируем JSONObject в json-строку*/
        String requestJsonString = jsonRequest.toString();
        System.out.println(requestJsonString);
    }
}
