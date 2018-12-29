package com.mebay.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;

/**
 * Created by gyh on 2018/12/27.
 */
@ApiModel(value = "设备维修")
public class DeviceRepair {
    @ApiModelProperty(value = "id", hidden = true)
    private Long id;
    @ApiModelProperty(value = "DTU编号", required = true)
    private String DTUId;
    @ApiModelProperty(value = "控制器类型")
    private String controllerType;
    @ApiModelProperty(value = "维修机构")
    private String repairMechanism;
    @ApiModelProperty(value = "维修人员")
    private String repairUser;
    @ApiModelProperty(value = "维修费用")
    private String repairCost;
    @ApiModelProperty(value = "维修时间")
    private Date repairTime;
    @ApiModelProperty(value = "维修内容")
    private String repairContent;

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

    public String getRepairMechanism() {
        return repairMechanism;
    }

    public void setRepairMechanism(String repairMechanism) {
        this.repairMechanism = repairMechanism;
    }

    public String getRepairUser() {
        return repairUser;
    }

    public void setRepairUser(String repairUser) {
        this.repairUser = repairUser;
    }

    public String getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(String repairCost) {
        this.repairCost = repairCost;
    }

    public Date getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(Date repairTime) {
        this.repairTime = repairTime;
    }

    public String getRepairContent() {
        return repairContent;
    }

    public void setRepairContent(String repairContent) {
        this.repairContent = repairContent;
    }
}
