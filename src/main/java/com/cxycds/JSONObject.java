package com.cxycds;

import java.util.HashMap;

/**
 * Created by leicheng on 2020/6/3.
 */
public class JSONObject {
    private HashMap<String, Object> map;

    public JSONObject(HashMap<String, Object> map) {
        this.map = map;
    }

    public JSONObject() {
        this.map = new HashMap<String, Object>(4);
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Object> map) {
        this.map = map;
    }
}
