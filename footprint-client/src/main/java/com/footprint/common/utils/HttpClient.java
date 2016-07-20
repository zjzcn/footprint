package com.footprint.common.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * httpClient 工具类<br>
 * 发送http请求,验证http响应结果
 */
public class HttpClient {

    /**
     * 日志定义
     */
	private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);

    /**
     * json格式数据
     */
    public static final String CONTENT_TYPE_JSON = "application/json";

    /**
     * urlencoded格式数据
     */
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

    /**
     * 提交方式post
     */
    private static final String HTTP_METHOD_POST = "POST";

    /**
     * 数据类型
     */
    private static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    /**
     * 数据长度
     */

    private static final String HTTP_HEADER_CONTENT_LENGTH = "Content-Length";
    /**
     * 编码
     */

    private static final String HTTP_HEADER_CONTENT_ENCODING = "Content-Encoding";

    /**
     * gzip编码
     */
    private static final String ENCODING_GZIP = "gzip";

    /**
     * redirect
     */
    private static final boolean FOLLOW_REDIRECTS = true;
    
    //30s
    private static final int DEFAULT_TIMEOUT = 30000;

    /**
     * post方式提交
     * @param url
     * @param dataBody
     * @return
     */
    public static String sendByPost(String url, String dataBody) {
    	return sendByPost(url, dataBody, DEFAULT_TIMEOUT);
    }
    /**
     * post方式提交
     *
     * @param url      url
     * @param dataBody     数据
     * @param timeout  超时时间
     * @return 返回结果
     * @throws Exception
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String sendByPost(String url, String dataBody, int timeout) {
    	try{
    		// Not use proxy to prevent: Software caused connection abort: socket write error
    		URLConnection connection = new URL(url).openConnection(Proxy.NO_PROXY);
    		// must be http url connection
    		if (!(connection instanceof HttpURLConnection)) {
    			throw new Exception("Service URL [" + url + "] is not an HTTP URL");
    		}
    		HttpURLConnection con = (HttpURLConnection) connection;
    		// prepareConnection
    		prepareConnection(con, dataBody.length(), timeout);
    		// write request
    		writeRequestBody(con, dataBody);
    		// validate response
    		validateResponse(con);
    		// read response
    		InputStream responseBody = readResponseBody(con);
    		// return response
    		return readResult(responseBody);
    	} catch(Exception e) {
    		throw new HttpClientException(e);
    	}
    }

    /**
     * 功能描述: <br>
     * prepareConnection
     *
     * @param connection    http连接
     * @param contentLength 消息体长度
     * @param timeout       超时时间
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private static void prepareConnection(HttpURLConnection connection, int contentLength, int timeout)
            throws IOException {
        // set connect timeout
        if (timeout >= 0) {
            connection.setConnectTimeout(timeout);
        }
        // set read timeout
        if (timeout >= 0) {
            connection.setReadTimeout(timeout);
        }
        // set redirect
        connection.setInstanceFollowRedirects(FOLLOW_REDIRECTS);
        // do output
        connection.setDoOutput(true);
        // do post
        connection.setRequestMethod(HTTP_METHOD_POST);
        // do input
        connection.setDoInput(true);
        // need cache
        connection.setUseCaches(false);
        // connection.setRequestProperty(HTTP_HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
        connection.setRequestProperty(HTTP_HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
        connection.setRequestProperty(HTTP_HEADER_CONTENT_LENGTH, Integer.toString(contentLength));
    }

    /**
     * 功能描述: <br>
     * 写入http请求内容
     *
     * @param con  http连接
     * @param data 待写数据
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private static void writeRequestBody(HttpURLConnection con, String data) throws IOException {
        final DataOutputStream printout = new DataOutputStream(con.getOutputStream());
        printout.write(data.getBytes("UTF-8"));
        // flush
        printout.flush();
        // 关闭
        printout.close();
    }

    /**
     * 功能描述: <br>
     * 检查请求结果
     *
     * @param con http连接
     * @throws Exception
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private static void validateResponse(HttpURLConnection con) throws Exception {
        // response 503
        if (con.getResponseCode() == 503) {
            String errorMsg = con.getHeaderField("errorCode");
            // have error message log warn
            if (errorMsg != null && errorMsg.length() > 0) {
            	logger.warn("Server handle meet error! Error Message is:" + errorMsg);
                throw new RuntimeException(errorMsg);
            } else {
                // no error message log info and throw server handle meet error!
            	logger.info("Server handle meet error!");
                throw new RuntimeException("Server handle meet error!");
            }
        }
        // response 300
        if (con.getResponseCode() >= 300) {
        	logger.info("Did not receive successful HTTP response: status code = " + con.getResponseCode()
                    + ", status message = [" + con.getResponseMessage() + "]");
            throw new IOException("Did not receive successful HTTP response: status code = " + con.getResponseCode()
                    + ", status message = [" + con.getResponseMessage() + "]");
        }
    }

    /**
     * 功能描述: <br>
     * 读取响应内容
     *
     * @param con http连接
     * @return 返回结果
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private static InputStream readResponseBody(HttpURLConnection con) throws IOException {
        if (isGzipResponse(con)) {
            // GZIP response found - need to unzip.
            return new GZIPInputStream(con.getInputStream());
        } else {
            // Plain response found.
            return con.getInputStream();
        }
    }

    /**
     * 功能描述: <br>
     * 判断是否Gzip类型响应
     *
     * @param con http连接
     * @return 是否gzip response
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private static boolean isGzipResponse(HttpURLConnection con) {
        String encodingHeader = con.getHeaderField(HTTP_HEADER_CONTENT_ENCODING);
        return encodingHeader != null && encodingHeader.toLowerCase().contains(ENCODING_GZIP);
    }

    /**
     * 功能描述: <br>
     * 读取响应结果
     *
     * @param is 数据流
     * @return 返回结果
     * @throws IOException
     * @throws ClassNotFoundException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private static String readResult(InputStream is) throws IOException, ClassNotFoundException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        try {
            // temp string builder
            StringBuilder temp = new StringBuilder();
            String line = reader.readLine();
            // read line
            while (line != null) {
                temp.append(line);
                line = reader.readLine();
            }
            return temp.toString();
        } finally {
            reader.close();
        }
    }
    
    public static class HttpClientException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		
		public HttpClientException() {
			super();
		}

		public HttpClientException(String message, Throwable cause) {
			super(message, cause);
		}

		public HttpClientException(String message) {
			super(message);
		}

		public HttpClientException(Throwable cause) {
			super(cause);
		}
    }
}
