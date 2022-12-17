package controller;

public class Response {

    private final String response;
    private final String value;
    private final String reason;


    public Response(String responseType) {
        this(responseType, "", "");
    }

    public Response(String response, String value, String reason) {
        this.response = response;
        this.value = !(value == null) && !value.isBlank() ? value: null;
        this.reason = !(reason == null) && !reason.isBlank() ? reason : null;
    }

    public String getResponse() {
        return this.response;
    }

    public String getValue() {
        return this.value;
    }

    public String getReason() {
        return this.reason;
    }
}
