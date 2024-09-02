package com.ei.desktop.theme;

/**
 * @author yitiansong
 * @since 2024/8/11
 */

public class PurpleTheme implements ColorTheme {
    @Override
    public String getPrimary() {
        return "#4A0E4E";
    }

    @Override
    public String getSecondary() {
        return "#7E4A87";
    }

    @Override
    public String getAccent() {
        return "#A87DB3";
    }

    @Override
    public String getLightBackground() {
        return "#C4A8CC";
    }

    @Override
    public String getLightestBackground() {
        return "#E9E3EB";
    }

    @Override
    public String getTextColor() {
        return "#333333";
    }
}
