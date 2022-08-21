package com.persoff68.fatodo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfo extends AbstractModel {

    private String email;

    private String username;

    private String firstname;

    private String lastname;

    private String language;

}
