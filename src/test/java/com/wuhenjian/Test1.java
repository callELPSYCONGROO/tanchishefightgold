package com.wuhenjian;

import com.wuhenjian.tanchishe_fight_gold.entity.IP;
import com.wuhenjian.tanchishe_fight_gold.func.Crawler;
import com.wuhenjian.tanchishe_fight_gold.util.PropertiesUtil;
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
import org.quartz.JobExecutionException;

import java.io.*;
import java.net.*;
import java.util.List;

/**
 * @author SwordNoTrace
 * @date 2017/8/14 23:22
 */
public class Test1 {

    @Test
    public void asd() throws IOException, URISyntaxException {
        HttpGet get = new HttpGet("http://tanchishe.me/s/?c=Ae9mD-8ce36");//访问网页
        HttpClient client = HttpClients.createDefault();//客户端
        RequestConfig requestConfig = RequestConfig.copy(RequestConfig.DEFAULT).setProxy(new HttpHost("180.168.179.193", 8080)).build();//配置代理
        get.setConfig(requestConfig);
        HttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity, "UTF-8");
        Document document = Jsoup.parse(html);
        Element element = document.getElementById("nickname");
        String nickname = element.text();
        if ("無痕剑onepiece".equals(nickname)) {
            System.out.println("成功！");
        } else {
            System.out.println(nickname);
        }
        FileWriter out = new FileWriter(new File("C:\\Users\\SwordNoTrace\\Desktop\\t.html"));
        out.write(html);
        out.flush();
    }

    @Test
    public void asdd() throws Exception {
        String proxyUrl = PropertiesUtil.getString("PROXY_WEB_URL");
        String goldUrl = PropertiesUtil.getString("GOLD_URL");
        String nickname = PropertiesUtil.getString("NICKNAME");
        Crawler crawler = new Crawler(proxyUrl, goldUrl);
        List<IP> ipList = null;
        try {
            ipList = crawler.getProxyIp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ipList == null || ipList.size() == 0) {
            throw new Exception("代理IP为空");
        }
        int i = crawler.catchTheCoin(nickname, ipList);
        System.out.println("*****************"+i+"*****************");
    }

}
