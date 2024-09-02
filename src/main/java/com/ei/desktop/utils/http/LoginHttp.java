package com.ei.desktop.utils.http;

import com.ei.desktop.domain.Account;
import com.ei.desktop.dto.ResponseDto;
import com.ei.desktop.dto.ResponseType;
import com.ei.desktop.exception.TokenNotFoundException;
import com.ei.desktop.utils.EILog;
import com.ei.desktop.utils.PreferenceUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.prefs.Preferences;

/**
 * @author yitiansong
 * 2024/8/4
 */

public class LoginHttp {

    private static final Logger logger = LoggerFactory.getLogger(LoginHttp.class);

    static {
        String token = PreferenceUtils.get("token");
        if (token != null && !token.isEmpty()) {
            logger.info("从Preferences中获取到token:{}", token);
            HttpUtils.token = token;
        }
    }

    /**
     * 登录
     * 1.Create an Account object with username and password.
     * 2.Convert the Account object to a JSON string.
     * 3.Send a POST request with the JSON string.
     * 4.Handle the response:
     * If the response is null, log an error.
     * If the response indicates success:
     *      Extract the token from the response.
     *      If the token is valid, store it and return true.
     *      If the token is invalid, log an error.
     * If the response indicates failure, log an error and clear the stored token.
     * @param username 用户名
     * @param password 密码
     * @return 是否登录成功
     */
    public static boolean login(String username, String password) {
        Account account = new Account(username, password);
        ObjectMapper objectMapper = new ObjectMapper();
        String loginParams;

        try {
            loginParams = objectMapper.writeValueAsString(account);
        } catch (JsonProcessingException e) {
            logger.error("将Account对象转为json字符串出现错误，Account：{}", account, e);
            throw new RuntimeException(e);
        }

        // Send POST request and handle login result
        ResponseDto loginResult = HttpUtils.post("/api/auth/login", loginParams);

        if (loginResult == null) {
            EILog.logger.error("登录失败，返回结果为空");
            clearToken();
            return false;
        }

        if (ResponseType.SUCCESS.equals(loginResult.getResultType())) {
            return handleSuccessfulLogin(loginResult);
        } else {
            EILog.logger.error("登录失败，返回结果：{}", loginResult);
            clearToken();
            return false;
        }
    }

    /**
     * 处理登录成功的结果
     * @param loginResult 登录结果
     * @return 是否登录成功
     */
    private static boolean handleSuccessfulLogin(ResponseDto loginResult) {
        try {
            JsonNode data = loginResult.getData();
            String token = data.get("token").asText();
            if (token == null || token.isEmpty()) {
                throw new TokenNotFoundException();
            }
            HttpUtils.token = token;
            PreferenceUtils.put("token", token);
            return true;
        } catch (TokenNotFoundException e) {
            EILog.logger.error(e.getMessage());
            return false;
        }
    }
    /**
     * 清除Preferences中的token
     */
    private static void clearToken() {
        logger.error("登录失败，清除Preferences中的token");
        PreferenceUtils.remove("token");
    }

    /**
     * 检查登录状态
     * @param username 用户名
     * @return 是否登录
     */
    public static boolean checkLoginStatus(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        Preferences loginPreference = Preferences.userNodeForPackage(LoginHttp.class);
        String token = loginPreference.get("token", null);
        if (token == null || token.isEmpty()) {
            return false;
        }
        ResponseDto post = HttpUtils.post("/api/auth/token/validate", "username", username, "token", token);
        String resultType = post.getResultType();
        if (resultType.equals(ResponseType.FAIL)) {
            loginPreference.remove("token");
            return false;
        }
        return Objects.equals(post.getResultType(), ResponseType.SUCCESS);
    }
}
