package com.mebay.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;

/**
 * Created by gyh on 2018/12/17.
 */
@ApiModel(value = "设备保养")
public class DeviceUpkeep {
    @ApiModelProperty(value = "id", hidden = true)
    private Long id;
    @ApiModelProperty(value = "DTU编号", required = true)
    private String DTUId;
    @ApiModelProperty(value = "控制器类型")
    private String controllerType;
    @ApiModelProperty(value = "保养人员", required = true)
    private String upkeepUser;
    @ApiModelProperty(value = "保养费用", required = true)
    private String upkeepCost;
    @ApiModelProperty(value = "保养时间", required = true)
    private Date upkeepTime;
    @ApiModelProperty(value = "下次保养日期", required = true)
    private Date nextUpkeepTime;
    @ApiModelProperty(value = "保养内容", required = true)
    private String upkeepContent;
    @ApiModelProperty(value = "保养机构", required = true)
    private String upkeepMechanism;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDTUId() {
        return DTUId;
    }

    public void setDTUId(String DTUId) {
        this.DTUId = DTUId;
    }

    public String getControllerType() {
        return controllerType;
    }

    public void setControllerType(String controllerType) {
        this.controllerType = controllerType;
    }

    public String getUpkeepUser() {
        return upkeepUser;
    }

    public void setUpkeepUser(String upkeepUser) {
        this.upkeepUser = upkeepUser;
    }

    public String getUpkeepCost() {
        return upkeepCost;
    }

    public void setUpkeepCost(String upkeepCost) {
        this.upkeepCost = upkeepCost;
    }

    public Date getUpkeepTime() {
        return upkeepTime;
    }

    public void setUpkeepTime(Date upkeepTime) {
        this.upkeepTime = upkeepTime;
    }

    public Date getNextUpkeepTime() {
        return nextUpkeepTime;
    }

    public void setNextUpkeepTime(Date nextUpkeepTime) {
        this.nextUpkeepTime = nextUpkeepTime;
    }

    public String getUpkeepContent() {
        return upkeepContent;
    }

    public void setUpkeepContent(String upkeepContent) {
        this.upkeepContent = upkeepContent;
    }

    public String getUpkeepMechanism() {
        return upkeepMechanism;
    }

    public void setUpkeepMechanism(String upkeepMechanism) {
        this.upkeepMechanism = upkeepMechanism;
    }
}
