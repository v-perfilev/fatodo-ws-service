package com.persoff68.fatodo.model;

import com.persoff68.fatodo.config.constant.AppConstants;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public abstract class AbstractModel implements Serializable {
    protected static final long serialVersionUID = AppConstants.SERIAL_VERSION_UID;

    protected UUID id;

}
