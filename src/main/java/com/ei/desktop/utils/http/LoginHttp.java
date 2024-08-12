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
            HttpUtils.token = token;
        }
    }

    public static boolean login(String username, String password) {
        // prepare json params
        Account account = new Account(username, password);
        ObjectMapper objectMapper = new ObjectMapper();
        String loginParams;
        try {
            loginParams = objectMapper.writeValueAsString(account);
        } catch (JsonProcessingException e) {
            logger.error("将Account对象转为json字符串出现错误，Account：{}", account);
            throw new RuntimeException(e);
        }
        // send post and handle login result
        ResponseDto loginResult = HttpUtils.post("/api/auth/login", loginParams);
        if (loginResult == null) {
            return false;
        }
        if (Objects.equals(loginResult.getResultType(), ResponseType.SUCCESS)) {
            try {
                JsonNode data = loginResult.getData();
                String token = data.get("token").toString();
                if (token == null || token.isEmpty()) {
                    throw new TokenNotFoundException();
                }
                HttpUtils.token = token;
                // stores token to preferences
                PreferenceUtils.put("token", token);
                return true;
            } catch (TokenNotFoundException e2) {
                EILog.logger.error(e2.getMessage());
            }
        }
        return false;
    }

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
