package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<Integer, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> queue = new ConcurrentHashMap<>();


    @Override
    public Resp process(Req req) {
        if (validate(req)) {
            add(req);
            return null;
        }
        return new Resp(queue.get(req.id()).get(req.theme()).poll(), 200);
    }

    private boolean validate(Req req) {
        return queue.get(req.id()).putIfAbsent(req.theme(), new ConcurrentLinkedQueue<>()) == null;
    }

    private void add(Req req) {
        queue.get(req.id()).get(req.theme()).add(req.text());
    }
}