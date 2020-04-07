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
    int STYLE = 1;


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
    String URL = "http://47.96.126.117:9986/tenant-app-api/";

//    /**
//     * AppID
//     */
//    String appid = "1247072961388298241";
//
//    /**
//     * 租户id
//     */
//    String zuhuID = "1247044891616104449";
//
//    /**
//     * 服务器接口地址
//     */
//    String URL = "http://tta.fengyunv40.com/tenant-app-api/";

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


//    客户红豆派：
//    currentTenantId=1244523862079459329 , currentApplicationId=1244525334364901378 , app名称＝红豆派
//    接口地址：http://tta.fengyunv40.com
//
//    客户大闸蟹：
//    currentTenantId=1244531543242592258 , currentApplicationId＝1244531720264044545 , app名称＝大闸蟹
//    接口地址：http://tta.fengyunv40.com
//
//    客户海王：
//    currentApplicationId=1244526410950463489  app 名称＝海王
//    接口地址：http://tsa.fengyunv40.com
}
