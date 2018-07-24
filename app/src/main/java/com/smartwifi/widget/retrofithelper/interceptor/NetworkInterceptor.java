package com.smartwifi.widget.retrofithelper.interceptor;

import android.util.Log;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @Author zhangshurong
 * @CreateTime 2018/7/15
 * @Describe
 */

public class NetworkInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public enum Level {
        /**
         * No logs.
         */
        NONE,
        /**
         * Logs request and response lines.
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }


    public NetworkInterceptor() {

    }

    private volatile Level level = Level.NONE;

    /**
     * Change the level at which this interceptor logs.
     */
    public NetworkInterceptor setLevel(Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        this.level = level;
        return this;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        StringBuffer bfLog = new StringBuffer();
        bfLog.append("接口访问开始=======================================接口访问开始========================================");
        Request request = chain.request();
        Response response;
        try {
            response = chain.proceed(request);

        } catch (Exception e) {
            //response = chain.proceed(request);
            bfLog.append("<-- HTTP FAILED: \n" + e.getMessage() + "\n");
            //  HttpLogManager.getInstance().recordLog(bfLog.toString());
            //  L.d(bfLog.toString());
            Log.d("----", bfLog.toString());

            throw e;
        }
        if (response.code() == 200) {
            //这里是网络拦截器，可以做错误处理
            MediaType mediaType = response.body().contentType();
            //当调用此方法java.lang.IllegalStateException: closed，原因为OkHttp请求回调中response.body().string()只能有效调用一次
            byte[] data = response.body().bytes();
          /*  byte[] bytes = Base64.decode(data, Base64.DEFAULT);
            data = GZIPUtils.uncompress(bytes);*/
            /*if (GZIPUtils.isGzip(response.headers())) {
                //请求头显示有gzip，需要解压

            }*/
            //创建一个新的responseBody，返回进行处理
            response = response.newBuilder()
                    .body(ResponseBody.create(mediaType, data))
                    .build();
        }

        //   bfLog.append("操作时间：" + TimeUtils.getSystemDateToSecond() + "\n");
        Level level = this.level;
        if (level == Level.NONE) {
            return chain.proceed(request);
        }

        boolean logBody = level == Level.BODY;
        boolean logHeaders = logBody || level == Level.HEADERS;

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        String requestStartMessage = "-->\n请求类型 " + request.method() + "\n接口地址：" + request.url() + "\n请求协议：" + protocol;
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody.contentLength() + "-byte body)\n";
        }
        bfLog.append(requestStartMessage + "\n");

        //
        // bfLog.append(requestStartMessage);

        if (logHeaders) {
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {
                    bfLog.append("Content-Type: " + requestBody.contentType() + "\n");
                }
                if (requestBody.contentLength() != -1) {
                    bfLog.append("Content-Length: " + requestBody.contentLength() + "\n");
                }
            }

            Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
//                    bfLog.append(name + ": " + headers.value(i));
                    bfLog.append(name + ": " + headers.value(i));
                }
            }

            if (!logBody || !hasRequestBody) {
                bfLog.append("--> END " + request.method() + "\n");
            } else if (bodyEncoded(request.headers())) {
                bfLog.append("--> END " + request.method() + " (encoded body omitted)" + "\n");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (isPlaintext(buffer)) {
                    bfLog.append("请求参数:+\n" + buffer.readString(charset) + "\n");
                    bfLog.append("--> END " + request.method()
                            + " (" + requestBody.contentLength() + "-byte body)" + "\n");
                } else {
                    bfLog.append("--> END " + request.method() + " (binary "
                            + requestBody.contentLength() + "-byte body omitted)" + "\n");
                }
            }
        }

        long startNs = System.nanoTime();

        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        bfLog.append(
                "code" + response.code() + "\n" +
                        "message" + response.message() + "\n" +
                        "request" + response.request().url() + "\n " +
                        "(" + tookMs + "ms" + (!logHeaders ? ", " + bodySize + " body" : "") + ')' + "\n");

        if (logHeaders) {
            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                bfLog.append(headers.name(i) + ": " + headers.value(i) + "\n");
            }
            if (!logBody || !HttpHeaders.hasBody(response)) {
                bfLog.append("<-- END HTTP" + "\n");
            } else if (bodyEncoded(response.headers())) {
                bfLog.append("<-- END HTTP (encoded body omitted)" + "\n");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (!isPlaintext(buffer)) {
                    bfLog.append("<-- END HTTP (binary " + buffer.size() + "-byte body omitted " + "\n");
                    return response;
                }

                if (contentLength != 0) {
                    String readString = buffer.clone().readString(charset);
                    bfLog.append("响应数据：" + "\n");
                    try {
                        bfLog.append((new JSONObject(readString)).toString(4));
                    } catch (JSONException _e) {
                        _e.printStackTrace();
                        bfLog.append(readString);
                    }
                }

                bfLog.append("END HTTP (" + buffer.size() + "-bit body)" + "\n");
                bfLog.append("接口访问结束==================================接口访问结束==================================");
            }
        }

        //  HttpLogManager.getInstance().recordLog(bfLog.toString());
        //   Log.d("----",bfLog.toString());
        Logger.d(bfLog.toString());
        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}
