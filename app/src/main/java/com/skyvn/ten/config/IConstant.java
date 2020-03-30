package com.skyvn.ten.config;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2020/1/913:45
 * desc   :  配置参数类
 * version: 1.0
 */
public interface IConstant {

    //语言类型
    String LANGUAGE_TYPE = "language_type";


    /**
     * 界面风格  1 :风格1  2：风格2
     */
    int STYLE = 2;


    /**
     * AppID
     */
    String appid = "1243515447200206849";

    /**
     * 租户id
     */
    String zuhuID = "1243514988580982785";

    /**
     * 服务器接口地址
     */
    String URL = "http://47.96.126.117:9986/";

//    app打包：
//    百倍云：
//    saas-app：currentApplicationId：1237328537956216833
//    接口地址：http://47.96.126.117:9989
//    租户app：currentApplicationId：1237613520948129793 ，currentTenantId：1234763145872871425
//    接口地址：http://47.96.126.117:9986
//    客户：
//    saas-app：currentApplicationId：1242415496784642049
//    接口地址：http://tsa.fengyunv40.com
//    租户app：currentApplicationId：1242353191250165761 ，currentTenantId：1242352620035608577
//    接口地址：http://tta.fengyunv40.com

}
