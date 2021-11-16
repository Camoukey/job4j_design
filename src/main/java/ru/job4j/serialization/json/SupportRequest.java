package ru.job4j.serialization.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class SupportRequest {
    boolean completed = false;
    int totalCost;
    String address;
    Agent executor;
    ArrayList<String> completedWorks = new ArrayList<>();

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
                    + "\"totalCost\":0,"
                    + "\"completedWorks\":"
                    + "[]"
                    + "}";
        request = gson.fromJson(customRequest, SupportRequest.class);
        System.out.println(request);
    }
}

class Agent {
    private String name;
    private String position;

    public Agent(String name, String position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public String toString() {
        return "Agent{"
                + "name='" + name + '\''
                + ", position='" + position + '\''
                + '}';
    }
}
