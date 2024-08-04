package com.ei.desktop.utils.http;

import com.ei.desktop.domain.Account;
import com.ei.desktop.dto.ResponseDto;
import com.ei.desktop.dto.ResponseType;
import com.ei.desktop.exception.TokenNotFoundException;
import com.ei.desktop.utils.EILog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.prefs.Preferences;

/**
 * @author yitiansong
 * 2024/8/4
 */

public class LoginHttp {
    static {
        Preferences preferences = Preferences.userNodeForPackage(LoginHttp.class);
        String token = preferences.get("token", null);
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
            EILog.logger.error("将Account对象转为json字符串出现错误，Account：{}", account);
            throw new RuntimeException(e);
        }
        // send post and handle login result
        ResponseDto loginResult = HttpUtils.post("/api/login", loginParams);
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
                Preferences loginPreference = Preferences.userNodeForPackage(LoginHttp.class);
                loginPreference.put("token", token);
                return true;
            } catch (TokenNotFoundException e2) {
                EILog.logger.error(e2.getMessage());
            }
        }
        return false;
    }

    public static boolean checkLoginStatus() {
        Preferences loginPreference = Preferences.userNodeForPackage(LoginHttp.class);
        String token = loginPreference.get("token", null);
        return token != null && !token.isEmpty();
    }
}
