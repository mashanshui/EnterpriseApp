package com.shenhesoft.enterpriseapp.net;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author 张继淮
 * @date 2017/9/27
 * @description 网络请求结果处理
 */

public class RequestParams {
    private ArrayList<BaseParams> params;

    public RequestParams addParams(String key, Object value) {
        if (params == null) {
            params = new ArrayList<>();
        }
        params.add(new BaseParams(key, value));
        return this;
    }

    public String toJson() {
        String json;
        if (params != null && params.size() > 0) {
            Gson gson = new Gson();
            json = gson.toJson(toMap(params));
        } else {
            json = "{}";
        }
        return json;
    }

    private Map<String, Object> toMap(ArrayList<BaseParams> params) {
        HashMap<String, Object> result = new HashMap<>();
        for (BaseParams param : params) {
            result.put(param.getKey(), param.getValue());
        }
        return result;
    }


    public RequestBody getRequestBodyFromJson() {
        return RequestBody.create(MediaType.parse("application/json;charset=utf-8"), toJson());
    }


    public boolean isValid() {
        for (BaseParams param : params) {
            if (null == param.getValue()) {
                return false;
            }
        }
        return true;
    }

    public static final class BaseParams {
        private String key;

        public BaseParams(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        private Object value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
