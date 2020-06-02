package vn.byt.qlds.config;

import java.util.HashMap;
import java.util.Map;

public class MappingCategory {

    public static final Map<String, Integer> RELATIONSHIP_MAPPING = new HashMap<String, Integer>() {{
        put("0", 1);
        put("1", 2);
        put("21", 3);
        put("22", 4);
        put("3", 5);
        put("4", 6);
        put("5", 7);
        put("6", 8);
    }};

    public static final Map<String, Integer> ETHNIC_MAPPING = new HashMap<String, Integer>() {{
        for (int i = 1; i <= 55; i++) {
            String key = i < 10 ? "0" + i : i + "";
            put(key, i);
        }
    }};

    public static final Map<String, Integer> RESIDENCE_STATUS_MAPPING = new HashMap<String, Integer>() {{
        put("1", 1);
        put("2", 2);
        put("3", 3);
    }};

    public static final Map<String, Integer> EDU_MAPPING = new HashMap<String, Integer>() {{
        put("0", 1);
        put("00", 2);
        put("CS", 3);
        put("PT", 4);
        put("TH", 5);
    }};

    public static final Map<String, Integer> TECHNICAL_MAPPING = new HashMap<String, Integer>() {{
        put("00", 1);
        put("A0", 2);
        put("A1", 3);
        put("B", 4);
        put("C", 5);
        put("D", 6);
        put("E", 7);
        put("F", 8);
    }};

    public static final Map<String, Integer> MARITAL_MAPPING = new HashMap<String, Integer>() {{
        put("1", 1);
        put("2", 2);
        put("4", 4);
        put("5", 5);
        put("6", 6);
    }};

    public static final Map<String, Integer> GENDER_MAPPING = new HashMap<String, Integer>() {{
        put("0", 0);
        put("1", 1);
    }};

    public static final Map<String, Integer> INVALID_MAPPING = new HashMap<String, Integer>() {{
        put("0", 1);
        put("CN", 2);
        put("EN", 3);
        put("GN", 4);
        put("VN", 5);
    }};

    public static final Map<String, Integer> CONTRACEPTIVE_MAPPING = new HashMap<String, Integer>() {{
        put("0", 1);
        put("1", 2);
        put("1/1", 3);
        put("19", 4);
        put("2", 5);
        put("3", 6);
        put("4", 7);
        put("5", 8);
        put("6", 9);
        put("7", 10);
        put("7/7", 11);
        put("79", 12);
        put("8", 13);
        put("N", 14);
        put("S", 15);
        put("T", 16);
    }};
}
