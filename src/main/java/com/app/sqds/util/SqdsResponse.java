package com.app.sqds.util;

import java.util.HashMap;

public class SqdsResponse extends HashMap<String, Object> {

    public SqdsResponse code(int value) {
        this.put("code", value);
        return this;
    }

    public SqdsResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public SqdsResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    public SqdsResponse success() {
        this.code(0);
        return this;
    }

    public SqdsResponse fail() {
        this.code(2);
        return this;
    }
}
