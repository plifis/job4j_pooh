package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();


    @Override
    public Resp process(Req req) {
        if (validate(req.theme())) {
            add(req);
            return null;
        } else {
            return new Resp(queue.get(req.theme()).poll(), 200);
        }
    }

    private boolean validate(String theme) {
       return queue.putIfAbsent(theme, new ConcurrentLinkedQueue<>()) == null;
    }


    private void add(Req req) {
        queue.get(req.theme()).add(req.text());
    }
}