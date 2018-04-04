package com.mt.common.utils;


import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author:Wujie
 * @Date: Create in 2018-01-26 16:41
 * @Description:
 * @Modified By:
 */
public class PdfUtil {

    //模板文件路径
    private static String templateFile = "F://pdfTest/sqTest.pdf";
    //生成的文件路径
    private static String outFile = "F://pdfTest/Test.pdf";
    //二维码图片路径
    private static String qcImagePath = "F://pdfTest/qcImage.jpg";
    //logo图片路径
    private static String logoImagePath = "F://pdfTest/logoImage.png";

    /**
     * 导入PDF
     */
    public static void inputPDF() {
        try {

            // 读取模板文件
            PdfReader pdfReader = new PdfReader(templateFile);
            // 生成的输出流
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(outFile));
            //提取pdf中的表单
            AcroFields acroFields =  pdfStamper.getAcroFields();
            //通过书签名获取所在页和坐标，左下角为起点
            int qcPageNo = acroFields.getFieldPositions("qc_code").get(0).page;
            int logoPageNo = acroFields.getFieldPositions("logo").get(0).page;
            Rectangle qcRectangle= acroFields.getFieldPositions("qc_code").get(0).position;
            Rectangle logoRectangle = acroFields.getFieldPositions("logo").get(0).position;
            float x = qcRectangle.getLeft();
            float y = qcRectangle.getBottom();
            float xx = logoRectangle.getLeft();
            float yy = logoRectangle.getBottom();

            //读图片
            Image qcImage = Image.getInstance(qcImagePath);
            Image logoImage = Image.getInstance(logoImagePath);
            //获取操作的页面
            PdfContentByte qcUnder = pdfStamper.getOverContent(qcPageNo);
            PdfContentByte logoUnder = pdfStamper.getOverContent(logoPageNo);
            //根据域的大小缩放图片
            qcImage.scaleToFit(qcRectangle.getWidth(),qcRectangle.getHeight());
            logoImage.scaleToFit(logoRectangle.getWidth(),logoRectangle.getHeight());
            //添加图片
            qcImage.setAbsolutePosition(x,y);
            logoImage.setAbsolutePosition(xx,yy);
            qcUnder.addImage(qcImage);
            logoUnder.addImage(logoImage);

            //编辑文本域表单的内容
            acroFields.setField("image_no", "QT150001");
            acroFields.setField("company_full_name", "深圳市伟博思技术有限公司");
            acroFields.setField("company_elglish_name", "WEBS TECHNOLOOY CO.,LTD");
            acroFields.setField("address", "深圳市南山区粤兴三道华中科技大学深圳产学研基地A栋11楼");
            acroFields.setField("tel", "+86-755-26718823/26718826");
            acroFields.setField("Fax", "+86-755-26544726");
            acroFields.setField("company_name", "深圳金池汽配有限公司");
            acroFields.setField("phone", "0755-38800921");
            acroFields.setField("quote_date", "2018-1-30");
            acroFields.setField("contact","黄宝华");
            acroFields.setField("email", "18979307713@189.com");
            acroFields.setField("fax", "0755-388009210");
            acroFields.setField("prod_name", "丰田S7系列SUV");
            acroFields.setField("coin", "人民币");
            acroFields.setField("version","V1");
            acroFields.setField("item", "1");
            acroFields.setField("description","FORD-T550 车头灯");
            acroFields.setField("material", "PMMA");
            acroFields.setField("runner", "");
            acroFields.setField("out_way", "");
            acroFields.setField("water_type", "");
            acroFields.setField("task_time", "65");
            acroFields.setField("count", "1");
            acroFields.setField("price", "167800.00");
            acroFields.setField("count_price","167800.00");
            acroFields.setField("remark","1. 此报价方案仅针对深圳金池公司有效");
            acroFields.setField("payment_rules","1. 20% 定金");
            acroFields.setField("handler_name", "刘全福");
            //如果为false生成的PDF文件还能编译
            pdfStamper.setFormFlattening(true);
            pdfStamper.close();
            pdfReader.close();
        } catch (IOException io) {
            io.printStackTrace();
        }catch (DocumentException de) {
            de.printStackTrace();
        }
    }

    /**
     * PDF文件转换为图片
     */
    public static void pdfToImage() {
        String filePath = "F://pdfTest/Test.pdf";
        PDDocument pdDocument;
        try {
            pdDocument = PDDocument.load(new File(filePath));
            PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
            int pageCounter = pdDocument.getNumberOfPages();
            for (int i = 0; i < pageCounter ; i++){
                BufferedImage image = pdfRenderer.renderImage(i,2.5f);
                ImageIO.write(image,"JPG",new File("F://pdfTest/"+i+".jpg"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * java链接打印机
     * @param fileName
     * @param count
     */
    public static void drawImage(String fileName, int count){
        try {
            DocFlavor docFlavor = null;
            if (fileName.endsWith(".gif")){
                docFlavor = DocFlavor.INPUT_STREAM.GIF;
            } else if (fileName.endsWith(".jpg")){
                docFlavor = DocFlavor.INPUT_STREAM.JPEG;
            }else if (fileName.endsWith(".png")){
                docFlavor = DocFlavor.INPUT_STREAM.PNG;
            }
            PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            printRequestAttributeSet.add(OrientationRequested.PORTRAIT);
            printRequestAttributeSet.add(new Copies(count));
            printRequestAttributeSet.add(PrintQuality.HIGH);
            DocAttributeSet docAttributeSet = new HashDocAttributeSet();
            docAttributeSet.add(new MediaPrintableArea(0,0,210,296,MediaPrintableArea.MM));
            FileInputStream fileInputStream = new FileInputStream(fileName);
            Doc doc = new SimpleDoc(fileInputStream,docFlavor,docAttributeSet);
            DocPrintJob docPrintJob = printService.createPrintJob();
            docPrintJob.print(doc,printRequestAttributeSet);
            fileInputStream.close();
        } catch (IOException ie){
            ie.printStackTrace();
        } catch (PrintException pe){
            pe.printStackTrace();
        }
    }

    public static void spin(int degree) throws Exception {
        // 旋转后的宽度
        int swidth = 0;
        // 旋转后的高度
        int sheight = 0;
        // 原点横坐标
        int x;
        // 原点纵坐标
        int y;

        File file = new File("F://pdfTest/0.jpg");
        if (!file.isFile()) {
            throw new Exception("ImageDeal>>>" + file + " 不是一个图片文件!");
        }
        // 读取该图片
        BufferedImage bi = ImageIO.read(file);
        // 处理角度--确定旋转弧度
        degree = degree % 360;
        if (degree < 0){
            // 将角度转换到0-360度之间
            degree = 360 + degree;
        }
        // 将角度转为弧度
        double theta = Math.toRadians(degree);

        // 确定旋转后的宽和高
        if (degree == 180 || degree == 0 || degree == 360) {
            swidth = bi.getWidth();
            sheight = bi.getHeight();
        } else if (degree == 90 || degree == 270) {
            sheight = bi.getWidth();
            swidth = bi.getHeight();
        } else {
            swidth = (int) (Math.sqrt(bi.getWidth() * bi.getWidth()
                    + bi.getHeight() * bi.getHeight()));
            sheight = (int) (Math.sqrt(bi.getWidth() * bi.getWidth()
                    + bi.getHeight() * bi.getHeight()));
        }
        // 确定原点坐标
        x = (swidth / 2) - (bi.getWidth() / 2);
        y = (sheight / 2) - (bi.getHeight() / 2);

        BufferedImage spinImage = new BufferedImage(swidth, sheight,
                bi.getType());
        // 设置图片背景颜色
        Graphics2D gs = (Graphics2D) spinImage.getGraphics();
        gs.setColor(Color.white);
        // 以给定颜色绘制旋转后图片的背景
        gs.fillRect(0, 0, swidth, sheight);

        AffineTransform at = new AffineTransform();
        // 旋转图象
        at.rotate(theta, swidth / 2, sheight / 2);// 旋转图象
        at.translate(x, y);
        AffineTransformOp op = new AffineTransformOp(at,
                AffineTransformOp.TYPE_BICUBIC);
        spinImage = op.filter(bi, spinImage);
        File sf = new File("F://pdfTest", "image" + "." + "jpg");
        // 保存图片
        ImageIO.write(spinImage, "jpg", sf);

    }

    public static void main(String[] args) throws Exception{
        //spin(270);
        //drawImage("F://pdfTest/image.jpg",1);
        //pdfToImage();
        inputPDF();
        //imagePath();
    }
}
