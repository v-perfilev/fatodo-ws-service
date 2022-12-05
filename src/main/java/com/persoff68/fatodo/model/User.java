package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractModel {

    private String email;

    private String username;

    private Set<String> authorities;

    private String password;

    private String provider;

    private String providerId;

    private boolean activated;

    private Info info;

    private Settings settings;

    @Data
    public static class Info {

        private String firstname;

        private String lastname;

        private String imageFilename;

        private String gender;

    }

    @Data
    public static class Settings {

        private String language;

        private String timezone;

        private String timeFormat;

        private String dateFormat;

        private boolean emailReminders;

    }

}
