package com.xinan.cn.p2p.litagation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinan.cn.p2p.litagation.bean.nifa.*;
import com.xinan.cn.p2p.litagation.constant.LawConstant;
import com.xinan.cn.p2p.litagation.util.BeanConvertUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 查询中互金系统获取诉讼信息
 */
public class LawQueryNifaHelper implements LawConstant {
    private static final Logger logger = LoggerFactory.getLogger(LawQueryNifaHelper.class);

    public static void natural() {
        NaturalQueryRequest natural = new NaturalQueryRequest();
        natural.setAppKey(LawRequestConst.APP_KEY);
        natural.setIndustrType(LawRequestConst.INDUSTR_TYPE);
        natural.setProductType(LawRequestConst.PRODUCT_TYPE);
        natural.setTransactionNo(UUID.randomUUID().toString().replace("-", ""));
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmm");
        String clientTime = simpleDateFormat.format(date);
        natural.setClientTime(clientTime);

        // 失信人员：罗勇（522423196807156733）   王建华（520201195507100038）
        // 正常：姚桂华（340202198006082827）
        natural.setName("王建华");
        natural.setCardNo("522423196807156733");

        String reponseStr = sendPost(LawRequestConst.NATRUAL_PATH, natural);
        System.out.println(reponseStr);

        LawResultResponse nifaLawMsgResponse = JSONObject.parseObject(reponseStr, LawResultResponse.class);
        System.out.println(JSON.toJSONString(nifaLawMsgResponse));

        if ("0".equals(nifaLawMsgResponse.getCode()) && "0".equals(nifaLawMsgResponse.getResult())) {
            Map dataMap = (Map) (JSON.parse(nifaLawMsgResponse.getData()));
            LawDetailResponse lawDetail = JSONObject.parseObject(MapUtils.getString(dataMap, "count"), LawDetailResponse.class);
            System.out.println(JSON.toJSONString(lawDetail));
        } else if ("0".equals(nifaLawMsgResponse.getCode()) && "1".equals(nifaLawMsgResponse.getResult())) {
            // 未查询到
        } else {
            // 异常
        }


    }

    public static void org() {
        OrgQueryRequest org = new OrgQueryRequest();
        org.setAppKey(LawRequestConst.APP_KEY);
        org.setIndustrType(LawRequestConst.INDUSTR_TYPE);
        org.setProductType(LawRequestConst.PRODUCT_TYPE);
        org.setTransactionNo(UUID.randomUUID().toString().replace("-", ""));
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmm");
        String clientTime = simpleDateFormat.format(date);
        org.setClientTime(clientTime);

        // 失信企业：南京中脉科技发展有限公司    马鞍山市华夏玻璃有限公司
        // 正常：安徽新安左右贷金融服务有限公司
        org.setName("南京中脉科技发展有限公司");

        String reponseStr = sendPost(LawRequestConst.ORG_PATH, org);
        System.out.println(reponseStr);

        LawResultResponse nifaLawMsgResponse = JSONObject.parseObject(reponseStr, LawResultResponse.class);
        System.out.println(JSON.toJSONString(nifaLawMsgResponse));
        Map dataMap = (Map) (JSON.parse(nifaLawMsgResponse.getData()));
        LawDetailResponse lawDetail = JSONObject.parseObject(MapUtils.getString(dataMap, "count"), LawDetailResponse.class);
        System.out.println(JSON.toJSONString(lawDetail));
    }

    public static void main(String[] args) {
        try {
            natural(); // 个人
            //  org(); // 企业

//            if (msg == null || msg.trim().length() == 0) {
//            }
//            JSONObject jsonObject = JSONObject.parseObject(msg);
//            if (jsonObject.containsKey("code")
//                    && "0".equals(jsonObject.getString("code"))
//                    && jsonObject.containsKey("result")
//                    && "0".equals(jsonObject.getString("result"))) {
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static String sendMsg(){
//
//    }


    /**
     * 发送报文
     */
    public static String sendPost(String url, LawQueryBasic requestObj) {
        try {
            logger.info("LawQueryNifaHelper.sendPost,开始,requestObj:{}", JSON.toJSONString(requestObj));

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(30000)//设置连接超时时间，单位毫秒。
                    .setConnectionRequestTimeout(30000)//设置从connect Manager(连接池)获取Connection 超时时间，单位毫秒
                    .setSocketTimeout(30000)//请求获取数据的超时时间(即响应时间)，单位毫秒
                    .build();
            httpPost.setConfig(requestConfig);
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-Type", "application/json");

            StringEntity entity = new StringEntity(signParam(requestObj), Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json;charset=UTF-8");
            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();
            if (code == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String result = EntityUtils.toString(responseEntity, "UTF-8");
                EntityUtils.consume(responseEntity);
                httpClient.close();
                logger.info("LawQueryNifaHelper.sendPost,结束,result:{}", result);
                return result;
            }
            logger.error("LawQueryNifaHelper.sendPost,失败,状态码：{}", code);
            httpClient.close();
            throw new RuntimeException();
        } catch (Exception e) {
            logger.error("LawQueryNifaHelper.sendPost,异常,e：{}", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 数据加密签名处理
     */
    private static String signParam(LawQueryBasic requestObj) throws Exception {
        Map<String, Object> signParameters = BeanConvertUtil.beanToMap(requestObj);
        String name = "";
        if (requestObj instanceof NaturalQueryRequest) { //个人查询
            name = ((NaturalQueryRequest) requestObj).getName();
            String cardNo = ((NaturalQueryRequest) requestObj).getCardNo();
            cardNo = encrypt3DESAndEncodeBase64(cardNo.getBytes("UTF-8"));
            signParameters.put(LawRequestConst.CARD_NO, cardNo);
        } else if (requestObj instanceof OrgQueryRequest) {
            name = ((OrgQueryRequest) requestObj).getName();
        }
        name = encrypt3DESAndEncodeBase64(name.getBytes("UTF-8"));
        signParameters.put(LawRequestConst.NAME, name);

        String signValue = sign(signParameters);
        signParameters.put(LawRequestConst.SIGN, signValue);
        logger.info("LawQueryNifaHelper.signParam，准备发送的数据：" + JSON.toJSONString(signParameters));
        return JSON.toJSONString(signParameters);
    }

    /**
     * 签名
     */
    private static String sign(Map<String, Object> paramValues) {
        StringBuilder sb = new StringBuilder();
        List<String> paramNames = new ArrayList<>(paramValues.size());
        paramNames.addAll(paramValues.keySet());
        Collections.sort(paramNames);
        sb.append(LawRequestConst.APP_SECRET);
        for (String paramName : paramNames) {
            sb.append(String.valueOf(paramValues.get(paramName)));
        }
        sb.append(LawRequestConst.APP_SECRET);
        return getMD5Digest(sb.toString());
    }

    private static String getMD5Digest(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes("UTF-8"));
            byte[] bytes = md.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : bytes) {
                int v = b & 0xFF;
                String hv = Integer.toHexString(v);
                if (hv.length() < 2) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(hv);
            }
            return stringBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        } catch (GeneralSecurityException gse) {
            throw new RuntimeException(gse.getMessage());
        }
    }

    /**
     * 3Des加密，返回base64字符串
     */
    private static String encrypt3DESAndEncodeBase64(byte[] datasource) {
        byte[] encryptedData = encrypt3DES(datasource);
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    /**
     * 加密
     *
     * @param datasource byte[]
     * @return byte[]
     */
    private static byte[] encrypt3DES(byte[] datasource) {
        try {
            String algorithm = "DESede";
            byte[] bytePassword = LawRequestConst.APP_SECRET.getBytes();
            //生成密钥
            byte[] tripleDESKey = new byte[24];
            int k = 0;
            int i;
            //初始化Key
            for (i = 0; i < 24; i++) {
                if (k >= bytePassword.length) {
                    tripleDESKey[i] = 0;
                } else
                    tripleDESKey[i] = bytePassword[k];
                k++;
            }
            SecretKey deskey = new SecretKeySpec(tripleDESKey, algorithm);
            //加密
            Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(datasource);
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 解密
     *
     * @param datasource byte[]
     * @param password   String
     * @return byte[]
     * @throws Exception
     */
    public static byte[] decrypt3DES(byte[] datasource, String password) {
        try {
            String algorithm = "DESede";
            byte[] bytePassword = password.getBytes();
            //生成密钥
            byte[] tripleDESKey = new byte[24];
            int k = 0;
            int i = 0;
            //初始化Key
            for (i = 0; i < 24; i++) {
                if (k >= bytePassword.length) {
                    tripleDESKey[i] = 0;
                    ;
                } else
                    tripleDESKey[i] = bytePassword[k];
                k++;
            }
            SecretKey deskey = new SecretKeySpec(tripleDESKey, algorithm);
            //加密
            Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(datasource);
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }
}
