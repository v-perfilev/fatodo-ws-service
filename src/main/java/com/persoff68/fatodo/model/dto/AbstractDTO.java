package com.persoff68.fatodo.model.dto;

import com.persoff68.fatodo.config.constant.AppConstants;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class AbstractDTO implements Serializable {
    protected static final long serialVersionUID = AppConstants.SERIAL_VERSION_UID;

    protected String id;

}
