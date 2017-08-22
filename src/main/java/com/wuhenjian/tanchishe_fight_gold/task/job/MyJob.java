package com.wuhenjian.tanchishe_fight_gold.task.job;

import com.wuhenjian.tanchishe_fight_gold.entity.IP;
import com.wuhenjian.tanchishe_fight_gold.func.Crawler;
import com.wuhenjian.tanchishe_fight_gold.util.PropertiesUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * @author SwordNoTrace
 * @date 2017/8/22 19:45
 */
public class MyJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
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
            throw new JobExecutionException("代理IP为空");
        }
        crawler.catchTheCoin(nickname, ipList);
    }

}
