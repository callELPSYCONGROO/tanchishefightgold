package com.wuhenjian.tanchishe_fight_gold.entity;

/**
 * @author SwordNoTrace
 * @date 2017/8/17 23:46
 */
public class IP {

    private String uri;

    private Integer port;

    private String serverAddr;

    private String anonymous;

    private String type;

    private Integer speed;

    private String aliveTime;

    private String checkTime;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public String getAliveTime() {
        return aliveTime;
    }

    public void setAliveTime(String aliveTime) {
        this.aliveTime = aliveTime;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }
}
