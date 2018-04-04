package com.mt.common.utils.qiniuyun;

import com.google.gson.Gson;
import com.mt.common.utils.SecurityUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.ResourceBundle;

/**
 * @Author: Alnwick
 * @Date: create in 2018-01-31   15:12
 * @Description:
 * @Modified By:
 */
public class QiniuUtil {
    private static final Logger log = Logger.getLogger(QiniuUtil.class);

    public static final ResourceBundle QINIU = ResourceBundle.getBundle("config/system");

    // 账号的ACCESS_KEY和SECRET_KEY
    private final static String ACCESS_KEY = QINIU.getString("ACCESS_KEY");
    private final static String SECRET_KEY = QINIU.getString("SECRET_KEY");


    private final static byte[] DEC_KEY = QINIU.getString("DES_KEY").getBytes();


    private static int LIMIT_SIZE = 1000;

    // 七牛云空间名称
    // motooling存储图片空间
    public final static String BUCKET_NAME_MOTOOLING = "motoolingimage";
    // mowork存储空间
    public final static String BUCKET_NAME_MOWORK = "mowork";

    // 密钥配置
    private final static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    /**
     * @param zone         华东    Zone.zone0()
     * 华北    Zone.zone1()
     * 华南    Zone.zone2()  广东
     * 北美    Zone.zoneNa0()
     */
    private final static Zone zone = Zone.zone2();

    private final static UploadManager uploadManager = new UploadManager(new Configuration(zone));

//    public static void main(String args[]) throws Exception {
//        fileUpload( "E:\\motooling1111.png", "motooling/201801/31/wudalang.png");
//        InputStream ima = ZXingUtils.getQRCodeInputSterm("你好世界");
//        String sdf = uploadFile(ima, BUCKET_NAME, "motoling1111111111.png");

//        System.out.println(sdf);
//    }


    /**
     * 获取上传凭证
     */
    public static String getUploadCredential() {
//        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
//        String upToken = auth.uploadToken(bucket);
//        System.out.println(upToken);
//        return upToken;
        return auth.uploadToken(BUCKET_NAME_MOTOOLING);
    }


    /**
     * 文件上传
     * 通过路径上传
     *
     * @param localFilePath 需要上传的文件本地路径
     * @return
     */
    public static DefaultPutRet fileUpload(String localFilePath, String key) {
        String upToken = getUploadCredential();
        // 构造一个带指定Zone对象的配置类
//        Configuration cfg = new Configuration(zone);
        // ...其他参数参考类注释
//        UploadManager uploadManager = new UploadManager(cfg);
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info(putRet.key + "       " + putRet.hash);
            return putRet;
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error(ex.getMessage());
            log.error(r.toString());
        }
        return null;
    }


    /**
     * @param @param  inputStream 待上传文件输入流
     * @param @param  bucketName 空间名称 目前分。邀请的链接  和  单号的链接   头像的链接
     * @param @param  param1    公司id  如果是头像传 picture
     * @param @param  param2   单号类型   如果是邀请 传 uid
     * @param @return
     * @param @throws IOException
     * @return String
     * @throws
     * @Author: Alnwick
     * @Title: uploadFile
     * @Description: 七牛图片上传
     */
    public static String uploadFile(InputStream inputStream, String bucketName, String param1, String param2) {
        String name = "";
        param1 = SecurityUtil.encryptDes(param1, DEC_KEY);
        param2 = SecurityUtil.encryptDes(param2, DEC_KEY);
        switch (bucketName) {
            case "motoolingimage":
                name = QINIU.getString("qiniu.url.path");
                break;
            case "mowork":
                name = QINIU.getString("qiniu.url2.path");
                break;
        }

        StringBuilder sb = new StringBuilder(bucketName);
        sb.append("/").append(param1).append("/").append(param2).append(".png");
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
//        String token = auth.uploadToken(bucketName);
        String token = auth.uploadToken(bucketName, null, 3600, new StringMap().put("insertOnly", 0));
        try {
            byte[] byteData = IOUtils.toByteArray(inputStream);
            uploadManager.put(byteData, sb.toString(), token, null, null, false);
            inputStream.close();
            return sb.insert(0, name).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }


    /**
     * @param @param  bucketName 空间名称
     * @param @param  prefix 文件key的前缀
     * @param @return
     * @param @throws QiniuException
     * @return FileInfo[]
     * @throws
     * @Author: Alnwick
     * @Title: findFiles
     * @Description: 返回指定空间下的所有文件信息
     */
    public static FileInfo[] findFiles(String bucketName, String prefix) throws QiniuException {
        BucketManager bucketManager = new BucketManager(auth, new Configuration(zone));
        FileListing listing = bucketManager.listFiles(bucketName, prefix, null,
                LIMIT_SIZE, null);
        if (listing == null || listing.items == null
                || listing.items.length <= 0) {
            return null;
        }
        return listing.items;
    }


//    /**
//     * 七牛云通过base64 上传
//     * @param imgPath
//     */
//    public static void uploadByBase64(String imgPath,int l) {
//        //自己修改一下空间名称 - bucketName 和 文件名称 - key
//        String key = "upload_by_base64_sy1.jpg";
//        try {
//            String fileStr = imgPath;
//            String url = "http://upload-z2.qiniup.com/putb64/" + l +"/key/"+ key;
//            RequestBody reqBody = RequestBody.create(null, fileStr);
//            Request req = new Request.Builder()
//                    .url(url)
//                    .addHeader("Content-Type", "application/octet-stream")
//                    .addHeader("Authorization", "UpToken " + getUploadCredential())
//                    .post(reqBody)
//                    .build();
//            OkHttpClient client = new OkHttpClient();
//            okhttp3.Response resp = client.newCall(req).execute();
//            System.out.println(resp.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static String GetImageStrFromPath(String imgPath) {
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

    // 如果希望只能上传指定key的文件，并且不允许修改，那么可以将下面的 insertOnly 属性值设为 1
    public static String getUpToken() {
        return auth.uploadToken(BUCKET_NAME_MOTOOLING, null, 3600, new StringMap().put("insertOnly", 0));
    }

    public static void put64image(String base64Param) throws Exception {
        String key = "upload1.jpg";
        String file = base64Param;
        FileInputStream fis = null;
        int l = (int) (new File(file).length());
        byte[] src = new byte[l];
        fis = new FileInputStream(new File(file));
        fis.read(src);
        String file64 = Base64.encodeToString(src, 0);
        String url = "http://upload-z2.qiniup.com/putb64/" + file64.length() + "/key/" + UrlSafeBase64.encodeToString(key);
        //非华东空间需要根据注意事项 1 修改上传域名
        RequestBody rb = RequestBody.create(null, file64);
        Request request = new Request.Builder().
                url(url).
                addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getUpToken())
                .post(rb).build();
        System.out.println(request.headers());
        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println(response);
    }


    public static void main(String[] args) throws Exception {
//        new QiniuUtil().put64image();
        String images ="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";
        String url = QiniuUtil.uploadFile(QiniuUtil.generateImage(images), QiniuUtil.BUCKET_NAME_MOTOOLING, "picture", "20");
System.out.println(url);

    }


    public static InputStream generateImage(String imgStr) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            InputStream sbs = new ByteArrayInputStream(b);
            return sbs;
        } catch (Exception e) {
            return null;
        }
    }

}


