package com.wuhenjian.tanchishe_fight_gold.func;

import com.wuhenjian.tanchishe_fight_gold.entity.IP;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 爬虫，爬取代理IP网页，获取代理IP
 * @author SwordNoTrace
 * @date 2017/8/17 23:23
 */
public class Crawler {

    private static final Logger logger = Logger.getLogger(Crawler.class);

    /** 要爬取的页面 */
    private String proxyUrl;
    /** 金币链接 */
    private String goldUrl;

    public Crawler(String proxyUrl, String goldUrl) {
        this.proxyUrl = proxyUrl;
        this.goldUrl = goldUrl;
    }

    /**
     * 爬取代理IP网页，获取代理IP
     * @return ip对象结果集
     * @throws IOException 获取网页异常
     */
    public List<IP> getProxyIp() throws Exception {
        String html = HttpUtil.getHtmlByGet(proxyUrl, null);
        Document document = Jsoup.parse(html);
        Element ipList = document.getElementById("ip_list");
        Elements tbody = ipList.getElementsByTag("tbody");
        Element element = tbody.get(0);
        Elements children = element.children();
        List<IP> ips = new ArrayList<>();
        for (Element e : children) {
            Elements echilren = e.children();
            IP ip = new IP();
            for (int i = 0; i < echilren.size(); i++) {
                switch (i) {
                    case 0 :
                        String country = echilren.get(0).getElementsByTag("img").get(0).attr("alt");
                        ip.setCountry(country);
                        break;
                    case 1 :
                        String url = echilren.get(1).text();
                        ip.setUri(url);
                        break;
                    case 2 :
                        String port = echilren.get(2).text();
                        ip.setPort(Integer.valueOf(port));
                        break;
                    case 3 :
                        String addr = echilren.get(3).getElementsByTag("a").get(0).text();
                        ip.setServerAddr(addr);
                        break;
                    case 4 :
                        String anonymous = echilren.get(4).text();
                        ip.setAnonymous(anonymous);
                        break;
                    case 5 :
                        String type = echilren.get(5).text();
                        ip.setType(type);
                        break;
                    case 6 :
                        String speed = echilren.get(6).getElementsByAttribute("title").get(0).attr("title");
                        ip.setSpeed(speed);
                        break;
                    case 7 :
                        String connectTime = echilren.get(7).getElementsByAttribute("title").get(0).attr("title");
                        ip.setConnectTime(connectTime);
                        break;
                    case 8 :
                        String aliveTime = echilren.get(8).text();
                        ip.setType(aliveTime);
                        break;
                    case 9 :
                        String checkTime = echilren.get(9).text();
                        ip.setType(checkTime);
                        break;
                }
            }
            ips.add(ip);
        }
        return ips;
    }

    /**
     * 抓金币
     * @param ipList 代理IP集合
     * @return 点击次数
     */
    public int catchTheCoin(String nickname, List<IP> ipList){
        int count = 0;
        for (IP ip : ipList) {
            String html = null;
            try {
                html = HttpUtil.getHtmlByGet(goldUrl, ip);
            } catch (Exception e) {
                logger.error("代理IP访问金币链接发生异常！", e);
                System.out.println("***↓↓↓↓↓↓↓↓↓↓↓↓↓↓***");
                System.out.println(e.getCause().getMessage());
                System.out.println("***↑↑↑↑↑↑↑↑↑↑↑↑↑↑***");
            }
            Document document = Jsoup.parse(html);
            Element elementById = document.getElementById("nickname");
            if (elementById != null && nickname.equals(elementById.text())) {
                count++;
            }
        }
        return count;
    }
}
