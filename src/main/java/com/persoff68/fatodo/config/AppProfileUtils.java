package com.persoff68.fatodo.config;

import com.persoff68.fatodo.config.constant.Profile;

import java.util.Properties;

public class AppProfileUtils {
    private static final String SPRING_PROFILES_DEFAULT = "spring.profiles.default";

    private AppProfileUtils() {
    }

    public static Properties getDefaultProfile() {
        String[] profiles = {Profile.Constants.DEVELOPMENT_VALUE};
        Properties properties = new Properties();
        properties.put(SPRING_PROFILES_DEFAULT, profiles);
        return properties;
    }

}
