package com.skyvn.ten.api;

import com.skyvn.ten.bean.AccountBO;
import com.skyvn.ten.bean.AttentionSourrssBO;
import com.skyvn.ten.bean.AuthTypeBO;
import com.skyvn.ten.bean.BankBO;
import com.skyvn.ten.bean.BankCardBO;
import com.skyvn.ten.bean.BannerBO;
import com.skyvn.ten.bean.BaseResult;
import com.skyvn.ten.bean.CodeImgBO;
import com.skyvn.ten.bean.ContaceBO;
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
import com.skyvn.ten.config.IConstant;
import com.skyvn.ten.util.rx.RxResultHelper;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

import static com.skyvn.ten.api.HttpServerImpl.getService;

/**
 * Created by wuliang on 2017/3/9.
 * <p>
 * 此处存放后台服务器的所有接口数据
 */

public interface HttpService {

    //    String URL = "http://tta.fengyunv40.com/";   //正式服
    String URL = IConstant.URL;   //测试服
//    String URL = "http://mapi.platform.yinghezhong.com/";  //测试服2
//    String URL = "http://api.open.yinghezhong.com/";  //正式环境
//    String URL = "http://mapi.open.yinghezhong.com/";  //正式环境2


    /**
     * 获取图片验证码
     */
    @POST("clientUser/getCodeImg")
    Observable<BaseResult<CodeImgBO>> getCodeImg();

    /**
     * 获取短信验证码
     */
    @POST("clientUser/getVerificationCode")
    Observable<BaseResult<String>> getVerificationCode(@Body Map<String, Object> params);

    /**
     * 登录接口
     */
    @POST("clientUser/login")
    Observable<BaseResult<LoginSuressBO>> loginUser(@Body Map<String, Object> params);

    /**
     * 退出登录
     */
    @POST("clientUser/exit")
    Observable<BaseResult<String>> exitLogin(@Body Map<String, Object> params);

    /**
     * 获取首页显示
     */
    @GET("index/index")
    Observable<BaseResult<IndexBO>> getIndex();

    /**
     * 查询首页金额范围
     */
    @GET("operateApplicationSetting/getTenantAppHomeLoanAmount")
    Observable<BaseResult<String>> getIndexPayFanwei();

    /**
     * 查询首页借款天数
     */
    @GET("operateApplicationSetting/getTenantAppHomeLoanDays")
    Observable<BaseResult<List<String>>> getDays();

    /**
     * 提交反馈
     */
    @POST("operateApplicationFeedback/addOperateApplicationFeedback")
    Observable<BaseResult<String>> addOperateApplicationFeedback(@Body Map<String, Object> params);

    /**
     * 绑定银行卡
     */
    @POST("clientUserBankCard/addClientSmsRecordAuth")
    Observable<BaseResult<AttentionSourrssBO>> bindBankCard(@Body Map<String, Object> params);

    /**
     * 获取银行卡
     */
    @GET("clientUserBankCard/getBankCard")
    Observable<BaseResult<BankCardBO>> getBankCard();

    /**
     * 获取认证项的认证状态
     */
    @GET("clientAuthStatus/getAuthList")
    Observable<BaseResult<List<AuthTypeBO>>> getAuthList();

    /**
     * 提交个人资料
     */
    @POST("clientUserInfo/addClientInfoAuthTwo")
    Observable<BaseResult<AttentionSourrssBO>> commitClientInfo(@Body Map<String, Object> params);

    /**
     * 提交个人资料2
     */
    @POST("clientUserIdcard/addClientInfoAuth")
    Observable<BaseResult<AttentionSourrssBO>> addClientInfoAuth(@Body Map<String, Object> params);

    /**
     * 身份证验证(实名认证2)
     */
    @POST("clientUserIdcard/addClientIdcardAuth")
    Observable<BaseResult<AttentionSourrssBO>> addClientIdcardAuth(@Body Map<String, Object> params);


    /**
     * 实名认证资料
     */
    @POST("clientUserIdcard/addClientIdcardAuthTwo")
    Observable<BaseResult<AttentionSourrssBO>> commitIdCard(@Body Map<String, Object> params);

    /**
     * 提交公司信息
     */
    @POST("clientUserInfo/addCompanyInfoAuth")
    Observable<BaseResult<AttentionSourrssBO>> commitCompanyInfo(@Body Map<String, Object> params);

    /**
     * 提交驾照信息
     */
    @POST("clientDrivingLicense/addDrivingLicenseAuth")
    Observable<BaseResult<AttentionSourrssBO>> commitJiaZhaoInfo(@Body Map<String, Object> params);

    /**
     * 提交短信1414信息
     */
    @POST("clientSms1414/addClientSms1414Auth")
    Observable<BaseResult<AttentionSourrssBO>> addClientSms1414(@Body Map<String, Object> params);

    /**
     * 提交小视频认证
     */
    @POST("clientIdcardVideo/addIdcardVideoAuth")
    Observable<BaseResult<AttentionSourrssBO>> addVideoInfo(@Body Map<String, Object> params);

    /**
     * 提交紧急联系人认证
     */
    @POST("clientUserContact/addClientContactAuth")
    Observable<BaseResult<AttentionSourrssBO>> addClientContactInfo(@Body Map<String, Object> params);

    /**
     * 提交通讯录认证
     */
    @POST("clientAddressList/addClientAddressListAuth")
    Observable<BaseResult<AttentionSourrssBO>> addContactListInfo(@Body Map<String, Object> params);

    /**
     * 短信记录认证
     */
    @POST("clientSmsRecord/addClientSmsRecordAuth")
    Observable<BaseResult<AttentionSourrssBO>> addClientSmsRecordAuth(@Body Map<String, Object> params);

    /**
     * 提交活体检测认证
     */
    @POST("clientActive/addClientActiveAuth")
    Observable<BaseResult<AttentionSourrssBO>> addClientActiveAuth(@Body Map<String, Object> params);

    /**
     * 去认证的接口
     */
    @GET("clientAuthStatus/getFirstAuth")
    Observable<BaseResult<AttentionSourrssBO>> getFirstAuth();

    /**
     * 获取返回的文案信息
     */
    @GET("operateApplicationAuthProcess/getCopyWriter")
    Observable<BaseResult<String>> getCopyWriter(@Query("code") String code);

    /**
     * 跳过
     */
    @GET("clientAuthStatus/getJumpAuth")
    Observable<BaseResult<AttentionSourrssBO>> JumpAuth(@Query("code") String code);

    /**
     * 获取枚举数据
     */
    @GET("tenantLabel/getTenantLabelsByParentId")
    Observable<BaseResult<List<LablesBO>>> getSysLabels(@Query("parentId") String parentId);

    /**
     * 获取所有银行
     */
    @GET("sysBank/getSysBanks")
    Observable<BaseResult<List<BankBO>>> getSysBanks();

    /**
     * 获取全部客服
     */
    @GET("tenantCustomerService/getTenantCustomerServices")
    Observable<BaseResult<List<KeFuBO>>> getCustomerServicesByApplicationId();

    /**
     * 上传文件
     */
    @Multipart
    @POST("upload/uploadFile")
    Observable<BaseResult<String>> updateFile(@Part MultipartBody.Part file);

    /**
     * 获取首页banner
     */
    @GET("operateApplicationBanner/getOperateApplicationBanner")
    Observable<BaseResult<BannerBO>> getHomeBanner();

    /**
     * 查询推荐页面是否显示
     */
    @GET("operateApplicationSetting/getAppRecommend")
    Observable<BaseResult<String>> getAppRecommend();

    /**
     * 查询产品
     */
    @GET("orderProduct/getOrderProduct")
    Observable<BaseResult<OrderDetailsBO>> getOrderProduct();

    /**
     * 确认借款
     */
    @POST("orderLoan/confirm")
    Observable<BaseResult<String>> confirm(@Body Map<String, Object> params);

    /**
     * 获取全部轮播图
     */
    @GET("operateApplicationCarousel/getCarouselsByApplicationId")
    Observable<BaseResult<List<BannerBO>>> getHomeCarouse();

    /**
     * 获取推荐广告图片
     */
    @GET("recommend/getRecommend")
    Observable<BaseResult<RecommendBO>> getRecommendImg();


    /**
     * 获取公告列表
     */
    @GET("operateApplicationNotice/getNoticeListByApplicationId")
    Observable<BaseResult<List<GongGaoBO>>> getNoticeList();

    /**
     * 获取公告不翻页
     */
    @GET("operateApplicationNotice/getNotices")
    Observable<BaseResult<List<GongGaoBO>>> getNotices();

    /**
     * 获取金额和期限列表
     */
    @GET("operateApplicationSetting/getNoticeListByApplicationId")
    Observable<BaseResult<List<String>>> getPayNumOrDays(@Query("code") String code);

    /**
     * 查询当天提交的订单
     */
    @GET("orderApply/getMyApply")
    Observable<BaseResult<StatusBO>> getMyApply();

    /**
     * 提交客户申请
     */
    @POST("orderApply/addOrderApply")
    Observable<BaseResult<Object>> addOrderApply(@Body Map<String, Object> params);

    /**
     * 查询待确认订单
     */
    @GET("orderLoan/getMyConfirmLoan")
    Observable<BaseResult<OrderBO>> getMyConfirmLoan(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 查询已结束订单
     */
    @GET("orderLoan/getMyEndLoan")
    Observable<BaseResult<OrderBO>> getMyEndLoan(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 查询待还款订单
     */
    @GET("orderLoan/getMyRepayLoan")
    Observable<BaseResult<OrderBO>> getMyRepayLoan(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 查询订单详情
     */
    @GET("orderLoan/getMyLoan")
    Observable<BaseResult<OrderDetailsBO>> getMyLoan(@Query("id") String id);

    /**
     * 查询还款流水
     */
    @GET("orderLoanRepaySerial/getMyRepaySerial")
    Observable<BaseResult<HuanKuanBO>> getMyRepaySerial(@Query("orderId") String orderId, @Query("pageNum") String pageNum,
                                                        @Query("pageSize") String pageSize);

    /**
     * 查询租户收款账户
     */
    @GET("tenantGatheringAccount/getTenantGatheringAccounts")
    Observable<BaseResult<List<AccountBO>>> getUserPayNums(@Query("tenantId") String tenantId);

    /**
     * 查询租户客服
     */
    @GET("tenantCustomerService/getTenantCustomerServices")
    Observable<BaseResult<List<KeFuBO>>> getUserContact(@Query("tenantId") String tenantId);


    /**
     * 再借一次
     */
    @POST("orderLoan/loanAgain")
    Observable<BaseResult<String>> loanAgain(@Body Map<String, Object> params);


    /**
     * 获取Oss配置
     */
    @GET("ossInfo/getOssInfo")
    Observable<BaseResult<StsTokenBean>> getOssInfo(@Query("type") String type, @Query("key") String key);

    /**
     * 确认提现
     */
    @POST("orderLoan/withdraw")
    Observable<BaseResult<String>> withDraw(@Body Map<String, Object> params);

    /**
     * 查询最后订单
     */
    @GET("orderLoan/getMyLastLoan")
    Observable<BaseResult<OrderDetailsBO>> getMyLastLoan();

    /**
     * 检查更新
     */
    @GET("operateApplication/getForceUpdate")
    Observable<BaseResult<VersionBO>> checkUpdate();

    /**
     * 获取活体密钥
     */
    @GET("tenantInfo/getTenantActiveKey")
    Observable<BaseResult<LiveKeyBO>> getSaaSActiveKey();

    /**
     * 获取用户信息
     */
    @GET("clientUser/getClientInfo")
    Observable<BaseResult<LoginSuressBO>> getUserInfo();


    /**
     * 获取身份证信息
     */
    @GET("clientUserIdcard/getIdCardInfo")
    Observable<BaseResult<IdCardInfoBO>> getIdCardInfo(@Query("idCardBackOssUrl") String idCardBackOssUrl,
                                                       @Query("idCardFrontOssUrl") String idCardFrontOssUrl);


    /**
     * 获取用户姓名
     */
    @GET("clientUserInfo/getRealName")
    Observable<BaseResult<String>> getRealName();

    /**
     * 上传GPS定位数据
     */
    @POST("clientUser/updateLocation")
    Observable<BaseResult<String>> updateLocation(@Body Map<String,Object> params);

    /**
     * 获取协议地址
     */
    @GET("sysSetting/getContract")
    Observable<BaseResult<ContaceBO>> getContract();

}
