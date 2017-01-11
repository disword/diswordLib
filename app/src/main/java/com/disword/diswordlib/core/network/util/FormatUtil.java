package com.disword.diswordlib.core.network.util;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by disword on 17/1/10.
 */

public class FormatUtil {

    public static String combineURL(HashMap<String, String> params) {
        if (params == null)
            return "";
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        StringBuffer sb = null;
        try {
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                String key = entry.getKey();
                String val = entry.getValue();
                if (sb == null) {
                    sb = new StringBuffer();
                    sb.append("?");
                } else
                    sb.append("&");
                sb.append(key);
                sb.append("=");
                sb.append(URLEncoder.encode(val, "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (sb != null)
            return sb.toString();
        return "";
    }

    public static String combineJSON(HashMap<String, String> params) {
        if (params == null)
            return "";
        String result = JSON.toJSONString(params);
        System.out.println("json = " + result);
        return result;
    }
}
