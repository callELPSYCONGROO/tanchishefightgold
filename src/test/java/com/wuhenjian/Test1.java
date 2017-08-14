package com.wuhenjian;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.io.IOException;
import java.net.*;

/**
 * @author SwordNoTrace
 * @date 2017/8/14 23:22
 */
public class Test1 {

    @Test
    public void asd() throws IOException, URISyntaxException {
        HttpGet get = new HttpGet("http://tanchishe.me/s/?c=Ae9mD-8ce36");//访问网页
        HttpClient client = HttpClients.createDefault();//客户端
        RequestConfig requestConfig = RequestConfig.copy(RequestConfig.DEFAULT).setProxy(new HttpHost("", 8080)).build();//配置代理
        get.setConfig(requestConfig);
        HttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity);
        Document document = Jsoup.parse(html);
        Element element = document.getElementById("nickname");
        String nickname = element.text();
        if ("無痕剑onepiece".equals(nickname)) {
            System.out.println("成功！");
        }
    }

}
