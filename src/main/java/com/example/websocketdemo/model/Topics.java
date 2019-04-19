package com.example.websocketdemo.model;

import java.util.HashMap;
import java.util.Map;

public class Topics {
    private static Map<String, Integer> sizes = new HashMap<>();

    public static void updateSizes(String topicName, boolean inc) {
        if (inc)
            if (sizes.containsKey(topicName))
                sizes.replace(topicName, sizes.get(topicName) + 1);
            else
                sizes.put(topicName, 1);
        else
            if (sizes.get(topicName) > 1)
                sizes.replace(topicName, sizes.get(topicName) - 1);
            else
                sizes.remove(topicName);
    }

    public static Map<String, Integer> getSizes() {
        return sizes;
    }
}
