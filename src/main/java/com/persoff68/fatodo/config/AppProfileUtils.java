package com.persoff68.fatodo.config;

import com.persoff68.fatodo.config.constant.Profiles;

import java.util.Properties;

public class AppProfileUtils {
    private static final String SPRING_PROFILES_DEFAULT = "spring.profiles.default";

    public static Properties getDefaultProfile() {
        String[] profiles = {Profiles.DEVELOPMENT};
        Properties properties = new Properties();
        properties.put(SPRING_PROFILES_DEFAULT, profiles);
        return properties;
    }

}
