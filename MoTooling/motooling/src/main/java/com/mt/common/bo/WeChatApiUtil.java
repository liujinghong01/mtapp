package com.mt.common.bo;


import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;



class JEEWeiXinX509TrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}

/**
* @Author: Fhk
* @Description:
* @create    2017/11/25 13:42
* @Modified By:
*/
public class WeChatApiUtil {
    /** token 接口(GET) */
    private static final String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    /** 素材上传(POST) */
    private static final String UPLOAD_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/upload";
    //private static final String UPLOAD_MEDIA = "http://file.api.weixin.qq.com/cgi-bin/media/upload";
    /** 素材下载:不支持视频文件的下载(GET) */
    private static final String DOWNLOAD_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
    //private static final String DOWNLOAD_MEDIA = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";

    protected static final Logger logger = LogManager.getLogger(WeChatApiUtil.class);

    public static String getTokenUrl(String appId, String appSecret) {
        return String.format(ACCESS_TOKEN, appId, appSecret);
    }

    public static String getDownloadUrl(String token, String mediaId) {
        return String.format(DOWNLOAD_MEDIA, token, mediaId);
    }


    /**
     * 发送请求以https方式发送请求并将请求响应内容以String方式返回
     *
     * @param path   请求路径
     * @param method 请求方法
     * @param body   请求数据体
     * @return 请求响应内容转换成字符串信息
     */
    public static String httpsRequestToString(String path, String method, String body) {
        if (path == null || method == null) {
            return null;
        }

        String response = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        HttpsURLConnection conn = null;
        try {
            TrustManager[] tm = {new JEEWeiXinX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            System.out.println(path);
            URL url = new URL(path);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(method);
            if (null != body) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(body.getBytes("UTF-8"));
                outputStream.close();
            }

            inputStream = conn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            response = buffer.toString();
        } catch (Exception e) {
            logger.info(e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            try {
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
            } catch (IOException execption) {
                logger.info(execption.getMessage());
            }
        }
        return response;
    }


    /**
     * 通用接口获取Token凭证
     *
     * @param appId
     * @param appSecret
     * @return
     */
    public static String getToken(String appId, String appSecret) {
        if (appId == null || appSecret == null) {
            return null;
        }

        String token = null;
        String tockenUrl = WeChatApiUtil.getTokenUrl(appId, appSecret);
        String response = httpsRequestToString(tockenUrl, "GET", null);
        JSONObject jsonObject = JSONObject.fromObject(response);
        if (null != jsonObject) {
            try {
                token = jsonObject.getString("access_token");
            } catch (JSONException e) {
                // 获取token失败
                token = null;
                logger.info(e.getMessage());
                System.out.println(response);
            }
        }
        return token;
    }

    /**
     * 微信服务器素材上传
     *
     * @param file  表单名称media
     * @param token access_token
     * @param type  type只支持四种类型素材(video/image/voice/thumb)
     */
    public static JSONObject uploadMedia(File file, String token, String type) {
        if (file == null || token == null || type == null) {
            return null;
        }

        if (!file.exists()) {
            logger.info("上传文件不存在,请检查!");
            return null;
        }

        String url = UPLOAD_MEDIA;
        JSONObject jsonObject = null;
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Connection", "Keep-Alive");
        post.setRequestHeader("Cache-Control", "no-cache");
        FilePart media = null;
        HttpClient httpClient = new HttpClient();
        //信任任何类型的证书
        Protocol myhttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", myhttps);


        try {
            media = new FilePart("media", file);
            Part[] parts = new Part[]{new StringPart("access_token", token),
                    new StringPart("type", type), media};
            MultipartRequestEntity entity = new MultipartRequestEntity(parts,
                    post.getParams());
            post.setRequestEntity(entity);
            int status = httpClient.executeMethod(post);
            if (status == HttpStatus.SC_OK) {
                String text = post.getResponseBodyAsString();
                jsonObject = JSONObject.fromObject(text);
            } else {
                logger.info("upload Media failure status is:" + status);
            }
        } catch (FileNotFoundException execption) {
            logger.info(execption.getMessage());
        } catch (HttpException execption) {
            logger.info(execption.getMessage());
        } catch (IOException execption) {
            logger.info(execption.getMessage());
        }
        return jsonObject;
    }

    /**
     * 多媒体下载接口
     *
     * @param fileName 素材存储文件路径
     * @param token    认证token
     * @param mediaId  素材ID（对应上传后获取到的ID）
     * @return 素材文件
     * @comment 不支持视频文件的下载
     */
    public static File downloadMedia(String fileName, String token,
                                     String mediaId) {
        String url = getDownloadUrl(token, mediaId);
        return httpRequestToFile(fileName, url, "GET", null);
    }

    /**
     * 以http方式发送请求,并将请求响应内容输出到文件
     *
     * @param path   请求路径
     * @param method 请求方法
     * @param body   请求数据
     * @return 返回响应的存储到文件
     */
    public static File httpRequestToFile(String fileName, String path, String method, String body) {
        if (fileName == null || path == null || method == null) {
            return null;
        }

        File file = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        FileOutputStream fileOut = null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(method);
            if (null != body) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(body.getBytes("UTF-8"));
                outputStream.close();
            }

            inputStream = conn.getInputStream();
            if (inputStream != null) {
                file = new File(fileName);
            } else {
                return file;
            }

            //写入到文件
            fileOut = new FileOutputStream(file);
            if (fileOut != null) {
                int c = inputStream.read();
                while (c != -1) {
                    fileOut.write(c);
                    c = inputStream.read();
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

        /*
         * 必须关闭文件流
         * 否则JDK运行时，文件被占用其他进程无法访问
         */
            try {
                inputStream.close();
                fileOut.close();
            } catch (IOException execption) {
                logger.info(execption.getMessage());
            }
        }
        return file;
    }

   /* public static void main(String[] args) {
        File f = new File("D:/test.png");
        String appId = "";
        String appSecret = "";
        String token = WeChatApiUtil.getToken(appId, appSecret);
        JSONObject o = WeChatApiUtil.uploadMedia(f, token.getAccessToken(), "image");
        System.out.println(o.toString());

        //下载刚刚上传的图片以id命名
        String media_id = o.getString("media_id");
        File t = WeChatApiUtil.downloadMedia("D:/"+media_id+".png", token.getAccessToken(), media_id);

    }*/
}