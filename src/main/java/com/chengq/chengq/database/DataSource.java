package com.chengq.chengq.database;

import java.util.HashMap;
import java.util.Map;

public class DataSource {
    private static Map<String, Map<String, String>> data = new HashMap<>();

    static {
        Map<String, String> data1 = new HashMap<>();
        data1.put("password", "123");
        data1.put("role", "user");
        data1.put("permission", "view");

        Map<String, String> data2 = new HashMap<>();
        data2.put("password", "123");
        data2.put("role", "admin");
        data2.put("permission", "view,edit");

        data.put("admin", data2);
        data.put("user", data1);
    }

    public static Map<String, Map<String, String>> getData() {
        return data;
    }
}
