package ru.job4j.pooh;

public class Req {
    private final String method;
    private final String mode;
    private final String theme;
    private final String text;
    private final Integer id;

    private Req(String method, String mode, String theme, String text, Integer id) {
        this.method = method;
        this.mode = mode;
        this.theme = theme;
        this.text = text;
        this.id = id;
    }

    public static Req of(String content) {
        String method  = parseMethod(content);
        String mode = parseMethod(content);
        String[] arr = content.split("/");
        int id = 0;
        if (method.equals("GET") && mode.equals("topic")) {
            id = Integer.parseInt(arr[3]);
        }
        return new Req(method, mode, arr[2], null, id);

    }

    public String method() {
        return method;
    }

    public String mode() {
        return mode;
    }

    public String text() {
        return text;
    }
    public String theme() {
        return theme;
    }
    public int id() {
        return id;
    }

    private static String parseMethod(String content) {
        if (content.contains("GET") || content.contains("POST")) {
            if (content.contains("GET")) {
                return "GET";
            }
            return "POST";
        } else if (content.contains("queue") || content.contains("topic")) {
            if (content.contains("queue")) {
                return "queue";
            }
            return "topic";
        }
        throw new IllegalArgumentException("No correct request");
    }
}