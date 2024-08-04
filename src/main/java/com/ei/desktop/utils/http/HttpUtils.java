package com.ei.desktop.utils.http;

import com.ei.desktop.dto.ResponseDto;
import com.ei.desktop.utils.EILog;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

/**
 * @author yitiansong
 * 2024/8/4
 */

public class HttpUtils {

    private static final String EI_BACKEND_URI = "http://localhost:8081";

    static String token = "";

    public static ResponseDto post(String url, String paramBody) {
        return post(url, paramBody, null, null);
    }

    public static ResponseDto post(String url, String paramBody, Map<String, String> headers, String auth) {
        EILog.logger.info("发送post请求，请求url：{}", url);
        EILog.logger.info("请求参数：{}" , paramBody);
        EILog.logger.info("额外请求头信息：{}", headers);
        EILog.logger.debug("身份信息：{}", auth);
        // url替换
        url = replace(url);
        // 创建HttpClient
        try(HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(5))
                .build()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .POST(HttpRequest.BodyPublishers.ofString(paramBody))
                    .build();
            HttpResponse<String> commonResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            EILog.logger.info("response body{}", commonResponse.body());
            HttpResponse<String> send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseDto responseDto = objectMapper.readValue(send.body(), ResponseDto.class);
            EILog.logger.info("响应数据：{}", responseDto);
            return responseDto;
        }catch (IOException | InterruptedException e) {
            EILog.logger.error("Http请求错误:{}", e.getMessage());
            int count = 0;
            Throwable ex = e;
            EILog.logger.error("调用栈:\n{}", (Object[]) ex.getStackTrace());
            while(count++ < 10 && ex.getCause() != null) {
                EILog.logger.error(ex.getMessage());
                ex = ex.getCause();
            }
            return null;
        }

    }

    public static String replace(String url) {
        if (url.contains("/api")) {
            return url.replace("/api", EI_BACKEND_URI);
        }
        return url;
    }


}
