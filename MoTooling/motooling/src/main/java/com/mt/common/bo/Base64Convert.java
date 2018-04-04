package com.mt.common.bo;

import com.mt.common.core.support.security.BASE64Decoder;
import com.mt.common.core.support.security.BASE64Encoder;

import java.io.*;
/**
* @Author: Fhk
* @Description:
* @create    2017/11/25 11:25
* @Modified By:
*/
public class Base64Convert {

    /**
     * @Description: 将base64编码字符串转换为图片
     * @Author:
     * @CreateTime:
     * @param imgStr base64编码字符串
     * @param path 图片路径-具体到文件
     * @return
     */

    public static boolean generateImageFile(String imgStr, String path, StringBuilder fileSuffix) {
        if (imgStr == null) { return false; }
        ///////////////////////////////////
        //自动生成文件后缀
        String fileType = "";
        String fileTypeHead = "data:image/";
        String base64Head = ";base64,";
        Integer index1 = imgStr.indexOf(fileTypeHead);
        Integer index2 = imgStr.indexOf(base64Head);

        if(index1 >= 0 && index2 > index1) {
            fileType = imgStr.substring(index1 + fileTypeHead.length(), index2);
            imgStr = imgStr.substring(index2 + base64Head.length());
        }
        path += "." + fileType;
        fileSuffix.append(fileType);
        /////////////////////////
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                        b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(path);

            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     * @return
     */
    public static String getImageBase64(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);

            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
}
