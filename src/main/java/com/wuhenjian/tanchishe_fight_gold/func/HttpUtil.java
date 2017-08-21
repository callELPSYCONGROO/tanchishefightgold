package com.wuhenjian.tanchishe_fight_gold.func;

import com.wuhenjian.tanchishe_fight_gold.entity.IP;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author SwordNoTrace
 * @date 2017/8/17 23:56
 */
public class HttpUtil {

    private HttpUtil(){}

    public static String getHtmlByGet(String url, IP proxy) throws Exception {
        HttpGet get = new HttpGet(url);
        if (proxy != null) {
            setProxy(get, proxy);
        }
        return getHtml(get);
    }

    public static String getHtmlByPost(String url, IP proxy) throws Exception {
        HttpPost post = new HttpPost(url);
        if (proxy != null) {
            setProxy(post, proxy);
        }
        return getHtml(post);
    }

    /**
     * 设置代理
     * @param ip 代理ip
     * @param request 请求方式
     */
    private static void setProxy(HttpRequestBase request, IP ip) {
        RequestConfig config = RequestConfig.copy(RequestConfig.DEFAULT).setProxy(new HttpHost(ip.getUri(), ip.getPort())).build();//配置代理
        request.setConfig(config);
    }

    /**
     * 通过不同请求得到页面
     * @param request 请求
     * @return 页面
     * @throws IOException 发生异常
     */
    private static String getHtml(HttpUriRequest request) throws Exception {
        Header[] headers = request.getHeaders("Content-Type");
        String charset = null;
        for (Header header : headers) {
            String headerVal = header.getValue();
            if (headerVal.startsWith("text/html;")) {
                charset = headerVal.substring(headerVal.lastIndexOf("=") + 1);
            }
        }
        if (charset == null) {
            throw new Exception("未获取到页面字符集");
        }
        HttpEntity entity = HttpClients.createDefault().execute(request).getEntity();
        return EntityUtils.toString(entity,charset);
    }

}
