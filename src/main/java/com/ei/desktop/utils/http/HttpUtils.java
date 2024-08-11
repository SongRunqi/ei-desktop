package com.ei.desktop.utils.http;

import com.ei.desktop.dto.ResponseDto;
import com.ei.desktop.utils.EILog;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class HttpUtils {

    private static final String EI_BACKEND_URI = "http://localhost:8081";
    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    private static final int CONNECT_TIMEOUT = 5;
    static String token = "";

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(CONNECT_TIMEOUT))
            .build();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ResponseDto post(String url, String paramBody) {
        return post(url, paramBody, null, null);
    }

    public static ResponseDto post(String url, String paramBody, Map<String, String> headers, String auth) {
        logRequestDetails(url, paramBody, headers, auth);
        url = replaceUrl(url);

        try {
            HttpRequest request = buildHttpRequest(url, paramBody, headers, auth);
            HttpResponse<String> response = sendRequest(request);
            return parseResponse(response);
        } catch (Exception e) {
            logError(e);
            return null;
        }
    }

    public static <T> ResponseDto post(String url, T paramBody) {
        return post(url, paramBody, null, null);
    }

    public static <T> ResponseDto post(String url, T paramBody, Map<String, String> headers, String auth) {
        try {
            String jsonBody = objectMapper.writeValueAsString(paramBody);
            return post(url, jsonBody, headers, auth);
        } catch (IOException e) {
            logError(e);
            return null;
        }
    }

    public static CompletableFuture<ResponseDto> postAsync(String url, String paramBody, Map<String, String> headers, String auth) {
        logRequestDetails(url, paramBody, headers, auth);
        url = replaceUrl(url);

        try {
            HttpRequest request = buildHttpRequest(url, paramBody, headers, auth);
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpUtils::parseResponse)
                    .exceptionally(e -> {
                        logError(e);
                        return null;
                    });
        } catch (Exception e) {
            logError(e);
            return CompletableFuture.completedFuture(null);
        }
    }

    public static <T> CompletableFuture<ResponseDto> postAsync(String url, T paramBody, Map<String, String> headers, String auth) {
        try {
            String jsonBody = objectMapper.writeValueAsString(paramBody);
            return postAsync(url, jsonBody, headers, auth);
        } catch (IOException e) {
            logError(e);
            return CompletableFuture.completedFuture(null);
        }
    }

    private static void logRequestDetails(String url, String paramBody, Map<String, String> headers, String auth) {
        logger.info("发送post请求，请求url：{}", url);
        logger.info("请求参数：{}", paramBody);
        logger.info("额外请求头信息：{}", headers);
        logger.debug("身份信息：{}", auth);
    }

    private static String replaceUrl(String url) {
        return url.contains("/api") ? url.replace("/api", EI_BACKEND_URI) : url;
    }

    private static HttpRequest buildHttpRequest(String url, String paramBody, Map<String, String> headers, String auth) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + (auth != null ? auth : token))
                .POST(HttpRequest.BodyPublishers.ofString(paramBody));

        if (headers != null) {
            headers.forEach(requestBuilder::header);
        }

        return requestBuilder.build();
    }

    private static HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static ResponseDto post(String url, Object ...params) {
        return post(url, null, null, params);
    }
    /**
     * Sends a POST request to the specified URL with the given headers and parameters.
     *
     * @param url the URL to send the POST request to
     * @param headers a map of headers to include in the request
     * @param auth the authorization token to include in the request
     * @param params the parameters to include in the request body, must be in key-value pairs
     * @return a ResponseDto object containing the response data, or null if an error occurs
     * @throws IllegalArgumentException if the params are not in key-value pairs
     */
    public static ResponseDto post(String url, Map<String, String> headers, String auth, Object... params) {
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("参数必须是键值对");
        }

        ObjectNode jsonNode = objectMapper.createObjectNode();
        for (int i = 0; i < params.length; i += 2) {
            String key = params[i].toString();
            Object value = params[i + 1];
            switch (value) {
                case String s -> jsonNode.put(key, s);
                case Integer integer -> jsonNode.put(key, integer);
                case Long l -> jsonNode.put(key, l);
                case Double v -> jsonNode.put(key, v);
                case Boolean b -> jsonNode.put(key, b);
                case null, default -> jsonNode.putPOJO(key, value);
            }
        }

        try {
            String jsonBody = objectMapper.writeValueAsString(jsonNode);
            return post(url, jsonBody, headers, auth);
        } catch (IOException e) {
            logError(e);
            return null;
        }
    }

    public static CompletableFuture<ResponseDto> postAsync(String url, Map<String, String> headers, String auth, Object... params) {
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("参数必须是键值对");
        }

        ObjectNode jsonNode = objectMapper.createObjectNode();
        for (int i = 0; i < params.length; i += 2) {
            String key = params[i].toString();
            Object value = params[i + 1];
            switch (value) {
                case String s -> jsonNode.put(key, s);
                case Integer integer -> jsonNode.put(key, integer);
                case Long l -> jsonNode.put(key, l);
                case Double v -> jsonNode.put(key, v);
                case Boolean b -> jsonNode.put(key, b);
                case null, default -> jsonNode.putPOJO(key, value);
            }
        }

        try {
            String jsonBody = objectMapper.writeValueAsString(jsonNode);
            return postAsync(url, jsonBody, headers, auth);
        } catch (IOException e) {
            logError(e);
            return CompletableFuture.completedFuture(null);
        }
    }

    private static ResponseDto parseResponse(HttpResponse<String> response) {
        try {
            logger.info("response body: {}", response.body());
            ResponseDto responseDto = objectMapper.readValue(response.body(), ResponseDto.class);
            logger.info("响应数据：{}", responseDto);
            return responseDto;
        } catch (IOException e) {
            logError(e);
            return null;
        }
    }

    private static void logError(Throwable e) {
        logger.error("Http请求错误: {}", e.getMessage());
        int count = 0;
        Throwable ex = e;
        logger.error("调用栈:\n{}", (Object[]) ex.getStackTrace());
        while (count++ < 10 && ex.getCause() != null) {
            logger.error(ex.getMessage());
            ex = ex.getCause();
        }
    }
}