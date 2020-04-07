package com.skyvn.ten.api;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.Utils;
import com.skyvn.ten.bean.AccountBO;
import com.skyvn.ten.bean.AttentionSourrssBO;
import com.skyvn.ten.bean.AuthTypeBO;
import com.skyvn.ten.bean.BankBO;
import com.skyvn.ten.bean.BankCardBO;
import com.skyvn.ten.bean.BannerBO;
import com.skyvn.ten.bean.CodeImgBO;
import com.skyvn.ten.bean.GongGaoBO;
import com.skyvn.ten.bean.HuanKuanBO;
import com.skyvn.ten.bean.IdCardInfoBO;
import com.skyvn.ten.bean.IndexBO;
import com.skyvn.ten.bean.KeFuBO;
import com.skyvn.ten.bean.LablesBO;
import com.skyvn.ten.bean.LiveKeyBO;
import com.skyvn.ten.bean.LoginSuressBO;
import com.skyvn.ten.bean.OrderBO;
import com.skyvn.ten.bean.OrderDetailsBO;
import com.skyvn.ten.bean.RecommendBO;
import com.skyvn.ten.bean.StatusBO;
import com.skyvn.ten.bean.StsTokenBean;
import com.skyvn.ten.bean.VersionBO;
import com.skyvn.ten.util.EquipmentUtil;
import com.skyvn.ten.util.rx.RxResultHelper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

public class HttpServerImpl {

    private static HttpService service;

    /**
     * 获取代理对象
     *
     * @return
     */
    public static HttpService getService() {
        if (service == null)
            service = ApiManager.getInstance().configRetrofit(HttpService.class, HttpService.URL);
        return service;
    }


    /**
     * 获取图片验证码
     */
    public static Observable<CodeImgBO> getCodeImg() {
        return getService().getCodeImg().compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取短信验证码
     */
    public static Observable<String> getVerificationCode(String key, String phone, String imageCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("key", key);
        params.put("phone", phone);
        params.put("verificationCode", imageCode);
        return getService().getVerificationCode(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 登录
     */
    public static Observable<LoginSuressBO> loginUser(double localGpsLat, double loginLongitude, String phone, String verificationCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("deviceId", DeviceUtils.getAndroidID());
        params.put("deviceName", EquipmentUtil.getSystemDevice());
        params.put("localGpsLat", localGpsLat + "");
        params.put("localGpsLong", loginLongitude + "");
        params.put("phone", phone);
        params.put("verificationCode", verificationCode);
        return getService().loginUser(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 退出登录
     */
    public static Observable<String> exitLogin() {
        Map<String, Object> params = new HashMap<>();
        return getService().exitLogin(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取推荐页面是否显示
     */
    public static Observable<String> getAppRecommend() {
        return getService().getAppRecommend().compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取产品
     */
    public static Observable<OrderDetailsBO> getOrderProduct() {
        return getService().getOrderProduct().compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取推荐图片广告
     */
    public static Observable<RecommendBO> getRemomImg() {
        return getService().getRecommendImg().compose(RxResultHelper.httpRusult());
    }


    /**
     * 确认借款
     */
    public static Observable<String> confirm(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("productId", id);
        return getService().confirm(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取首页页面显示
     */
    public static Observable<IndexBO> getIndex() {
        return getService().getIndex().compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询首页金额范围
     */
    public static Observable<String> getIndexFanwei() {
        return getService().getIndexPayFanwei().compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询首页借款天数
     */
    public static Observable<List<String>> getIndexDays() {
        return getService().getDays().compose(RxResultHelper.httpRusult());
    }


    /**
     * 提交反馈
     */
    public static Observable<String> addOperateApplicationFeedback(String content, String contact, String images) {
        Map<String, Object> params = new HashMap<>();
        params.put("content", content);
        params.put("contact", contact);
        params.put("imageOssUrl", images);
        return getService().addOperateApplicationFeedback(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 去认证
     */
    public static Observable<AttentionSourrssBO> getFirstAuth() {
        return getService().getFirstAuth().compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取银行卡
     */
    public static Observable<BankCardBO> getBankCard() {
        return getService().getBankCard().compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取所有认证项的认证状态
     */
    public static Observable<List<AuthTypeBO>> getAuthList() {
        return getService().getAuthList().compose(RxResultHelper.httpRusult());
    }


    /**
     * 绑定银行卡
     */
    public static Observable<AttentionSourrssBO> bindBankCard(String bankName, String cardNo, String name, String subbranch, String code, String cardType) {
        Map<String, Object> params = new HashMap<>();
        params.put("bank", bankName);
        params.put("cardNo", cardNo);
        params.put("name", name);
        params.put("subbranch", subbranch);
        params.put("code", code);
        params.put("cardType", cardType);
        return getService().bindBankCard(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 提交个人资料
     */
    public static Observable<AttentionSourrssBO> commitPersonMsg(String xueli, String hunyin, String zinvNum, String juzhuTime,
                                                                 String juzhuAddress, String zalo, String facebook) {
        Map<String, Object> params = new HashMap<>();
        params.put("education", xueli);
        params.put("marriage", hunyin);
        params.put("child", zinvNum);
        params.put("liveMonth", juzhuTime);
        params.put("liveAddress", juzhuAddress);
        params.put("zalo", zalo);
        params.put("facebook", facebook);
        return getService().commitClientInfo(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 提交个人资料2
     */
    public static Observable<AttentionSourrssBO> addClientInfoAuth(String birthday, String gender, String idCardNo, String realName,
                                                                   String xueli, String hunyin, String zinvNum, String juzhuTime,
                                                                   String juzhuAddress, String zalo, String facebook) {
        Map<String, Object> params = new HashMap<>();
        params.put("education", xueli);
        params.put("marriage", hunyin);
        params.put("child", zinvNum);
        params.put("liveMonth", juzhuTime);
        params.put("liveAddress", juzhuAddress);
        params.put("zalo", zalo);
        params.put("facebook", facebook);
        params.put("birthday", birthday + " 00:00:00");
        params.put("gender", gender);
        params.put("idCardNo", idCardNo);
        params.put("realName", realName);
        return getService().addClientInfoAuth(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 实名认证资料
     */
    public static Observable<AttentionSourrssBO> commitIdCard(String birthday, String gender, String idCardFrontOssUrl,
                                                              String idCardBackOssUrl, String idCardNo, String realName) {
        Map<String, Object> params = new HashMap<>();
        params.put("birthday", birthday + " 00:00:00");
        params.put("gender", gender);
        params.put("idCardFrontOssUrl", idCardFrontOssUrl);
        params.put("idCardBackOssUrl", idCardBackOssUrl);
        params.put("idCardNo", idCardNo);
        params.put("realName", realName);
        return getService().commitIdCard(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 实名认证2
     */
    public static Observable<AttentionSourrssBO> addClientIdcardAuth(String idCardFrontOssUrl, String idCardBackOssUrl) {
        Map<String, Object> params = new HashMap<>();
        params.put("idCardFrontOssUrl", idCardFrontOssUrl);
        params.put("idCardBackOssUrl", idCardBackOssUrl);
        return getService().addClientIdcardAuth(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 提交公司资料
     */
    public static Observable<AttentionSourrssBO> commitComplanyInfo(String company, String companyAddress, String companyTel,
                                                                    String pretaxIncome, String profession, String workMonth,
                                                                    String workPicOssUrl) {
        Map<String, Object> params = new HashMap<>();
        params.put("company", company);
        params.put("companyAddress", companyAddress);
        params.put("companyTel", companyTel);
        params.put("pretaxIncome", pretaxIncome);
        params.put("profession", profession);
        params.put("workMonth", workMonth);
        params.put("workPicOssUrl", workPicOssUrl);
        return getService().commitCompanyInfo(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 提交短信认证
     */
    public static Observable<AttentionSourrssBO> addClientSms1414(String imageUrl, String remark) {
        Map<String, Object> params = new HashMap<>();
        params.put("sms1414OssUrl", imageUrl);
        params.put("remark", remark);
        return getService().addClientSms1414(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 提交驾照认证
     */
    public static Observable<AttentionSourrssBO> commitJiaZhaoInfo(String imageUrl1, String imageUrl2) {
        Map<String, Object> params = new HashMap<>();
        params.put("drivingLicenseFrontOssUrl", imageUrl1);
        params.put("drivingLicenseBackOssUrl", imageUrl2);
        return getService().commitJiaZhaoInfo(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 提交小视频认证
     */
    public static Observable<AttentionSourrssBO> commitVideoInfo(String videoUrl) {
        Map<String, Object> params = new HashMap<>();
        params.put("videoOssUrl", videoUrl);
        return getService().addVideoInfo(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 提交紧急联系人认证
     */
    public static Observable<AttentionSourrssBO> commitContactInfo(String nameOne, String nameTwo,
                                                                   String phoneOne, String phoneTwo, int relationOne, int relationTwo) {
        Map<String, Object> params = new HashMap<>();
        params.put("inContactOne", 1);
        params.put("inContactTwo", 1);
        params.put("nameOne", nameOne);
        params.put("nameTwo", nameTwo);
        params.put("phoneOne", phoneOne);
        params.put("phoneTwo", phoneTwo);
        params.put("relationOne", relationOne);
        params.put("relationTwo", relationTwo);
        return getService().addClientContactInfo(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 提交通讯录认证
     */
    public static Observable<AttentionSourrssBO> commitContactList(String contactBOS) {
        Map<String, Object> params = new HashMap<>();
        params.put("addressListOssUrl", contactBOS);
        return getService().addContactListInfo(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 提交短信记录认证
     */
    public static Observable<AttentionSourrssBO> addClientSmsRecordAuth(String url) {
        Map<String, Object> params = new HashMap<>();
        params.put("smsRecordOssUrl", url);
        return getService().addClientSmsRecordAuth(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 提交活体检测认证
     */
    public static Observable<AttentionSourrssBO> addClientActiveAuth(String base64Img) {
        Map<String, Object> params = new HashMap<>();
        params.put("activeOssUrl", base64Img);
        return getService().addClientActiveAuth(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取返回的文案信息
     */
    public static Observable<String> getBackMsg(String code) {
        return getService().getCopyWriter(code + "").compose(RxResultHelper.httpRusult());
    }

    /**
     * 跳过
     */
    public static Observable<AttentionSourrssBO> jumpAuth(String code) {
        return getService().JumpAuth(code + "").compose(RxResultHelper.httpRusult());
    }


    /**
     * 获取枚举数据
     */
    public static Observable<List<LablesBO>> getSysLables(int parentId) {
        return getService().getSysLabels(parentId + "").compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取所有银行
     */
    public static Observable<List<BankBO>> getSysBanks() {
        return getService().getSysBanks().compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取所有客服
     */
    public static Observable<List<KeFuBO>> getCustomerServicesByApplicationId() {
        return getService().getCustomerServicesByApplicationId().compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询待确认订单
     */
    public static Observable<OrderBO> getMyConfirmLoan() {
        return getService().getMyConfirmLoan(1, 2000).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询已完成订单
     */
    public static Observable<OrderBO> getMyEndLoan() {
        return getService().getMyEndLoan(1, 2000).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询待还款订单
     */
    public static Observable<OrderBO> getMyRepayLoan() {
        return getService().getMyRepayLoan(1, 2000).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取首页banner
     */
    public static Observable<BannerBO> getHomeBanner() {
        return getService().getHomeBanner().compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取首页轮播图
     */
    public static Observable<List<BannerBO>> getHomeImgList() {
        return getService().getHomeCarouse().compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取首页公告列表
     */
    public static Observable<List<GongGaoBO>> getNoticeList() {
        return getService().getNoticeList().compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取公告
     */
    public static Observable<List<GongGaoBO>> getNotices() {
        return getService().getNotices().compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取金额和期限列表
     */
    public static Observable<List<String>> getPayNumOrDays(String code) {
        return getService().getPayNumOrDays(code).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询当天提交的申请
     */
    public static Observable<StatusBO> getMyApplys() {
        return getService().getMyApply().compose(RxResultHelper.httpRusult());
    }


    /**
     * 提交借款订单
     */
    public static Observable<Object> addApplys(String days, String endAmount, String startAmount) {
        Map<String, Object> params = new HashMap<>();
        params.put("days", days);
        params.put("endAmount", endAmount);
        params.put("startAmount", startAmount);
        return getService().addOrderApply(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 查询订单详情
     */
    public static Observable<OrderDetailsBO> getMyLoan(String id) {
        return getService().getMyLoan(id).compose(RxResultHelper.httpRusult());
    }


    /**
     * 查询还款流水
     */
    public static Observable<HuanKuanBO> getMyRepaySerial(String id) {
        return getService().getMyRepaySerial(id, "1", "2000").compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询租户收款账户
     */
    public static Observable<List<AccountBO>> getUserPayNums(String tenantId) {
        return getService().getUserPayNums(tenantId).compose(RxResultHelper.httpRusult());
    }


    /**
     * 查询租户客服
     */
    public static Observable<List<KeFuBO>> getUserContact(String tenantId) {
        return getService().getUserContact(tenantId).compose(RxResultHelper.httpRusult());
    }


    /**
     * 再借一次
     */
    public static Observable<String> loanAgain(String tenantId) {
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        return getService().loanAgain(params).compose(RxResultHelper.httpRusult());
    }

    /**
     * 查询oss 配置
     */
    public static Observable<StsTokenBean> getOssInfo(int type, String key) {
        return getService().getOssInfo(type + "", key).compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取最后一个订单
     */
    public static Observable<OrderDetailsBO> getMyLastLoan() {
        return getService().getMyLastLoan().compose(RxResultHelper.httpRusult());
    }


    /**
     * 确认提现
     */
    public static Observable<String> withDraw(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return getService().withDraw(params).compose(RxResultHelper.httpRusult());
    }


    /**
     * 提交图片
     */
    public static Observable<String> updateFile(File file) {
        File compressedImageFile;
        try {
            compressedImageFile = new Compressor(Utils.getApp()).setQuality(30).compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            compressedImageFile = file;
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), compressedImageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        return getService().updateFile(body).compose(RxResultHelper.httpRusult());
    }

    /**
     * 提交视频
     */
    public static Observable<String> updateVideo(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("video/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        return getService().updateFile(body).compose(RxResultHelper.httpRusult());
    }

    /**
     * 检查更新
     */
    public static Observable<VersionBO> checkUpdate() {
        return getService().checkUpdate().compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取活体密钥
     */
    public static Observable<LiveKeyBO> getSaaSActiveKey() {
        return getService().getSaaSActiveKey().compose(RxResultHelper.httpRusult());
    }

    /**
     * 获取用户信息
     */
    public static Observable<LoginSuressBO> getUserInfo() {
        return getService().getUserInfo().compose(RxResultHelper.httpRusult());
    }

    /**
     * 识别身份证信息
     */
    public static Observable<IdCardInfoBO> getIdCardInfo(String zhengmian, String beimian) {
        return getService().getIdCardInfo(beimian, zhengmian).compose(RxResultHelper.httpRusult());
    }

    public static Observable<String> getRealName() {
        return getService().getRealName().compose(RxResultHelper.httpRusult());
    }
}
