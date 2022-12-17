package controller;

public class Request {

    private final String type;
    private final String key;
    private final String value;

    public Request(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = !(value == null) && !value.isBlank() ? value : null;
    }

    public String getType() {
        return this.type;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
