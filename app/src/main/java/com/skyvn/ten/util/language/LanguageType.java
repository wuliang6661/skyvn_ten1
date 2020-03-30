package com.skyvn.ten.util.language;

/**
 * Created by dumingwei on 2018/5/31 0031.
 */
public enum LanguageType {

    CHINESE("zh"),   //中文
    ENGLISH("en"),
    THAILAND("vi");   //越南语

    private String language;

    LanguageType(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language == null ? "" : language;
    }
}
