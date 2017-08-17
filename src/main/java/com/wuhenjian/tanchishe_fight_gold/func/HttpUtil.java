package com.wuhenjian.tanchishe_fight_gold.func;

import com.wuhenjian.tanchishe_fight_gold.entity.IP;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author SwordNoTrace
 * @date 2017/8/17 23:56
 */
public class HttpUtil {

    private HttpGet get;

    private HttpPost post;

    private HttpClient client;

    public HttpUtil(String url) {
        client = HttpClients.createDefault();
        get = new HttpGet(url);
        post = new HttpPost(url);
    }

    /**
     * 通过不同请求得到页面
     * @param request 请求
     * @return 页面
     * @throws IOException 发生异常
     */
    private String getHtml(HttpUriRequest request) throws IOException {
        HttpEntity entity = client.execute(request).getEntity();
        InputStream inputStream = entity.getContent();
        StringBuilder builder = new StringBuilder();
        int i;
        byte[] bytes = new byte[1024];
        while ((i = inputStream.read(bytes)) != -1) {
            builder.append(bytes);
        }
        return builder.toString();
    }

    public String getHtmlByGet() throws IOException {
        return getHtml(get);
    }

    public String getHtmlByPost() throws IOException {
        return getHtml(post);
    }

    /**
     * 设置代理
     * @param ip 代理ip
     * @param request 请求方式
     */
    public void setProxy(HttpRequestBase request, IP ip) {
        RequestConfig config = RequestConfig.copy(RequestConfig.DEFAULT).setProxy(new HttpHost(ip.getUri(), ip.getPort())).build();//配置代理
        request.setConfig(config);
    }


}
