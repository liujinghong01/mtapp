package com.mockito.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

/**
 * Created by lrd on 2017/10/14.
 */
public class JsonFileReadTest {

    @Test
    public void test1(){
        String JsonContext = new FileUtil().ReadFile(this.getClass().getResource("/")+"apkinfo.json");
        JSONArray jsonArray = JSONArray.fromObject(JsonContext);
        int size = jsonArray.size();
        System.out.println("Size: " + size);
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println("[" + i + "]name=" + jsonObject.get("name"));
            System.out.println("[" + i + "]package_name=" + jsonObject.get("package_name"));
            System.out.println("[" + i + "]check_version=" + jsonObject.get("check_version"));
        }
    }
}
