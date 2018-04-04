package com.mt.common.core.interceptor;


import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * @Author: Alnwick
 * @Date: create in 2018-01-25   14:16
 * @Description: 对request 进行包装。  解决 getReader 读一次后 为-1 问题。
 * @Modified By:
 */
public class TokenHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private byte[] requestBody;
    final static  int BODY_SIZE = 1024 * 8;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public TokenHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);

        //缓存请求body
        try {
            requestBody  =  inputStreamTOByte(request.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

    /**
     *  将InputStream转换成byte数组
     */
    public static byte[] inputStreamTOByte(InputStream in) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BODY_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BODY_SIZE)) != -1){
            outStream.write(data, 0, count);
        }
        data = null;
        return outStream.toByteArray();
    }
}
