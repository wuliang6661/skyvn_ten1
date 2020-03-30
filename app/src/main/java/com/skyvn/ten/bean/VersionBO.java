package com.skyvn.ten.bean;

public class VersionBO {


    /**
     * forceUpdate : 1
     * version : 5
     * downloadUrl : www.baidu.com1
     */

    private int forceUpdate;
    private int version;
    private String downloadUrl;

    public int getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(int forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
