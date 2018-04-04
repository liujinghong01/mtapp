package com.mt.common.utils.imagesUtil;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mt.common.core.support.security.BASE64Decoder;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: Alnwick
 * @Date: create in 2018-01-30   9:50
 * @Description:
 * @Modified By:
 */
public class ZXingUtils {
    private final static Logger log = Logger.getLogger(ZXingUtils.class);
    // 默认是黑色
    private static final int QRCOLOR = 0xFF000000;
    // 背景颜色
    private static final int BGWHITE = 0xFFFFFFFF;
    // 二维码宽
    private static  int WIDTH = 190;
    // 二维码高
    private static  int HEIGHT = 190;

    // 用于设置QR二维码参数
    private static Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>() {
        private static final long serialVersionUID = 1L;

        {
            // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
            put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 设置编码方式
            put(EncodeHintType.CHARACTER_SET, "utf-8");
            put(EncodeHintType.MARGIN, 0);
        }
    };

    /**
     * 删除白边
     * @param matrix
     * @return
     */
    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }

    /**
     * bufferedImage 对象 转成 流对象
     *
     * @param bufferedImage
     * @return
     * @throws IOException
     */
    public static InputStream bufferedImageToInputStream(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
        ImageIO.write(bufferedImage, "png", imOut);
        return new ByteArrayInputStream(bs.toByteArray());
    }

    /**
     * 生成带logo的二维码图片
     * 文件地址
     *
     * @param path
     * @param param
     * @param note
     */
    public static void drawLogoQRCode(String path, String param, String note) {
        try {
            File codeFile = new File(path);
            BufferedImage image = getBufferedImage(param,0,0);
            log.info(codeFile);
            ImageIO.write(image, "png", codeFile); // TODO
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * param 二维码的内容
     * 返回二维码流对象
     */
    public static InputStream getQRCodeInputSterm(String param,int width,int height) throws IOException, WriterException {
        return bufferedImageToInputStream(getBufferedImage(param,width,height));
    }

    public static InputStream base64ToInpustStream(String param) throws IOException {
        byte[] bytes = new BASE64Decoder().decodeBuffer(param);
        return new ByteArrayInputStream(bytes);
    }



    /**
     * @param param 二维码中的类容
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage getBufferedImage(String param,int width1 ,int height1) throws WriterException, IOException {
        if(width1>0){
            WIDTH=width1;
        }
        if(height1>0){
            HEIGHT=height1;
        }
        String filePath = "src/main/resources/image/logo.png";
        File logoFile = new File(filePath);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
        BitMatrix bm = multiFormatWriter.encode(param, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
        bm = deleteWhite(bm);
        BufferedImage image = new BufferedImage(bm.getWidth(), bm.getHeight(), BufferedImage.TYPE_INT_RGB);
        // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
        for (int x = 0; x < bm.getWidth(); x++) {
            for (int y = 0; y < bm.getHeight(); y++) {
                image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
            }
        }
        int width = image.getWidth();
        int height = image.getHeight();
        if (Objects.nonNull(logoFile) && logoFile.exists()) {
            // 构建绘图对象
            Graphics2D g = image.createGraphics();
            // 读取Logo图片
            BufferedImage logo = ImageIO.read(logoFile);
            // 开始绘制logo图片
            g.drawImage(logo, width * 2 / 5, height * 2 / 5, width * 2 / 8, height * 2 / 8, null);
            g.dispose();
            logo.flush();
        }

        // 自定义文本描述
//            if (StringUtils.isNotEmpty(note)) {
//                // 新的图片，把带logo的二维码下面加上文字
//                BufferedImage outImage = new BufferedImage(400, 445, BufferedImage.TYPE_4BYTE_ABGR);
//                Graphics2D outg = outImage.createGraphics();
//                // 画二维码到新的面板
//                outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
//                // 画文字到新的面板
//                outg.setColor(Color.BLACK);
//                outg.setFont(new Font("楷体", Font.BOLD, 30)); // 字体、字型、字号
//                int strWidth = outg.getFontMetrics().stringWidth(note);
//                if (strWidth > 399) {
//                    // //长度过长就截取前面部分
//                    // 长度过长就换行
//                    String note1 = note.substring(0, note.length() / 2);
//                    String note2 = note.substring(note.length() / 2, note.length());
//                    int strWidth1 = outg.getFontMetrics().stringWidth(note1);
//                    int strWidth2 = outg.getFontMetrics().stringWidth(note2);
//                    outg.drawString(note1, 200 - strWidth1 / 2, height + (outImage.getHeight() - height) / 2 + 12);
//                    BufferedImage outImage2 = new BufferedImage(400, 485, BufferedImage.TYPE_4BYTE_ABGR);
//                    Graphics2D outg2 = outImage2.createGraphics();
//                    outg2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
//                    outg2.setColor(Color.BLACK);
//                    outg2.setFont(new Font("宋体", Font.BOLD, 30)); // 字体、字型、字号
//                    outg2.drawString(note2, 200 - strWidth2 / 2, outImage.getHeight() + (outImage2.getHeight() - outImage.getHeight()) / 2 + 5);
//                    outg2.dispose();
//                    outImage2.flush();
//                    outImage = outImage2;
//                } else {
//                    outg.drawString(note, 200 - strWidth / 2, height + (outImage.getHeight() - height) / 2 + 12); // 画文字
//                }
//                outg.dispose();
//                outImage.flush();
//                image = outImage;
//            }
        image.flush();
        return image;
    }


    public static void main(String[] args) throws WriterException {

        String param = "武大郎武";
        String note = "";
        drawLogoQRCode("E://motooling1111.png", param, note);
    }
}
