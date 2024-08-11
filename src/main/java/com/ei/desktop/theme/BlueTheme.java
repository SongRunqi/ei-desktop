package com.ei.desktop.theme;

/**
 * @author yitiansong
 * @since 2024/8/11
 */

public class BlueTheme implements ColorTheme {
    @Override
    public String getPrimary() {
        return "#1A237E";
    }

    @Override
    public String getSecondary() {
        return "#3F51B5";
    }

    @Override
    public String getAccent() {
        return "#7986CB";
    }

    @Override
    public String getLightBackground() {
        return "#C5CAE9";
    }

    @Override
    public String getLightestBackground() {
        return "#E8EAF6";
    }

    @Override
    public String getTextColor() {
        return "#000000";
    }
}
