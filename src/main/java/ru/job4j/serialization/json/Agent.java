package ru.job4j.serialization.json;

class Agent {
    private String name;
    private String position;

    public Agent(String name, String position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Agent{"
                + "name='" + name + '\''
                + ", position='" + position + '\''
                + '}';
    }
}
