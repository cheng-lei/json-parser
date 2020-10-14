package com.cxycds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leicheng on 2020/6/3.
 */
public class JSONArray {
    List<JSONObject> list;

    public JSONArray(List<JSONObject> list) {
        this.list = list;
    }

    public JSONArray() {
        list = new ArrayList<JSONObject>(0);
    }

    public List<JSONObject> getList() {
        return list;
    }

    public void setList(List<JSONObject> list) {
        this.list = list;
    }
}
