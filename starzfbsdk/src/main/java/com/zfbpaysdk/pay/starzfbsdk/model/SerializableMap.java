package com.zfbpaysdk.pay.starzfbsdk.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by ASUS on 2017/6/21.
 */
public class SerializableMap implements Serializable {
    private Map<String,String> map;
    public Map<String,String> getMap()
    {
        return map;
    }
    public void setMap(Map<String,String> map)
    {
        this.map=map;
    }
}
