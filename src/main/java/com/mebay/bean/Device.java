package com.mebay.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;


@ApiModel(value = "设备")
public class Device {
    @ApiModelProperty(hidden = true)
    private Long id;                        //ID
    @ApiModelProperty(value = "DTU编号", required = true)
    private String DTUId;                   //DTU编号
    @ApiModelProperty(value = "DTU型号")
    private String DTUModel;                //DTU型号
    @ApiModelProperty(value = "站点名称", required = true)
    private String siteName;                //站点名称
    @ApiModelProperty(value = "控制器型号")
    private String controllerModel;         //控制器型号
    @ApiModelProperty(value = "通信号码", required = true)
    private String communicationNumber;     //通信号码
    @ApiModelProperty(value = "注册日期", required = true)
    //@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date registrationDate;          //注册日期
    @ApiModelProperty(value = "许可到期日", required = true)
    //@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date licenseExpirationDate;     //许可到期日
    @ApiModelProperty(value = "设备图片")
    private String uploadPhoto;             //照片上传
    @ApiModelProperty(value = "控制器品牌")
    private String controllerBrand;         //控制器品牌
    @ApiModelProperty(value = "控制器类型", required = true)
    private String controllerType;          //控制器类型
    @ApiModelProperty(value = "操作员", required = true)
    private Long userId;                    //操作员
    @ApiModelProperty(value = "许可周期", required = true)
    private String licensePeriod;           //许可周期
    @ApiModelProperty(value = "设备状态", required = true)
    private String deviceStatus;            //设备状态
    @ApiModelProperty(value = "其他")
    private String other;                   //其他
    @ApiModelProperty(value = "机组型号", required = true)
    private String unitModel;               //机组型号
    @ApiModelProperty(value = "发动机型号", required = true)
    private String engineModel;             //发动机型号
    @ApiModelProperty(value = "调速类型")
    private String speedControlType;        //调速类型
    @ApiModelProperty(value = "调速板型号")
    private String speedControlBoardModel;  //调速板型号
    @ApiModelProperty(value = "调压器类型")
    private String pressureRegulatorType;   //调压器类型
    @ApiModelProperty(value = "生产厂家")
    private String manufacturer;            //生产厂家
    @ApiModelProperty(value = "发电机型号", required = true)
    private String electricGeneratorModel;  //发电机型号
    @ApiModelProperty(value = "执行器类型")
    private String actuatorType;            //执行器类型
    @ApiModelProperty(value = "油箱容量")
    private Float fuelTankCapacity;         //油箱容量
    @ApiModelProperty(value = "备注")
    private String reference;               //备注
    @ApiModelProperty(value = "附件")
    private String accessory;               //附件
    @ApiModelProperty(value = "电子围栏状态")
    private Boolean electronicFenceStatus;  //电子围栏状态
    @ApiModelProperty(value = "围栏范围设置")
    private String electronicFenceScope;    //围栏范围设置
    @ApiModelProperty(hidden = true)
    //@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date createTime;                //添加时间
    @ApiModelProperty(hidden = true)
    private Long depId;                     //单位id
    @ApiModelProperty(value = "当前档位")
    private String gearRange;               //当前档位

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

    public String getDTUModel() {
        return DTUModel;
    }

    public void setDTUModel(String DTUModel) {
        this.DTUModel = DTUModel;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getControllerModel() {
        return controllerModel;
    }

    public void setControllerModel(String controllerModel) {
        this.controllerModel = controllerModel;
    }

    public String getCommunicationNumber() {
        return communicationNumber;
    }

    public void setCommunicationNumber(String communicationNumber) {
        this.communicationNumber = communicationNumber;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getLicenseExpirationDate() {
        return licenseExpirationDate;
    }

    public void setLicenseExpirationDate(Date licenseExpirationDate) {
        this.licenseExpirationDate = licenseExpirationDate;
    }

    public String getUploadPhoto() {
        return uploadPhoto;
    }

    public void setUploadPhoto(String uploadPhoto) {
        this.uploadPhoto = uploadPhoto;
    }

    public String getControllerBrand() {
        return controllerBrand;
    }

    public void setControllerBrand(String controllerBrand) {
        this.controllerBrand = controllerBrand;
    }

    public String getControllerType() {
        return controllerType;
    }

    public void setControllerType(String controllerType) {
        this.controllerType = controllerType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLicensePeriod() {
        return licensePeriod;
    }

    public void setLicensePeriod(String licensePeriod) {
        this.licensePeriod = licensePeriod;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getUnitModel() {
        return unitModel;
    }

    public void setUnitModel(String unitModel) {
        this.unitModel = unitModel;
    }

    public String getEngineModel() {
        return engineModel;
    }

    public void setEngineModel(String engineModel) {
        this.engineModel = engineModel;
    }

    public String getSpeedControlType() {
        return speedControlType;
    }

    public void setSpeedControlType(String speedControlType) {
        this.speedControlType = speedControlType;
    }

    public String getSpeedControlBoardModel() {
        return speedControlBoardModel;
    }

    public void setSpeedControlBoardModel(String speedControlBoardModel) {
        this.speedControlBoardModel = speedControlBoardModel;
    }

    public String getPressureRegulatorType() {
        return pressureRegulatorType;
    }

    public void setPressureRegulatorType(String pressureRegulatorType) {
        this.pressureRegulatorType = pressureRegulatorType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getElectricGeneratorModel() {
        return electricGeneratorModel;
    }

    public void setElectricGeneratorModel(String electricGeneratorModel) {
        this.electricGeneratorModel = electricGeneratorModel;
    }

    public String getActuatorType() {
        return actuatorType;
    }

    public void setActuatorType(String actuatorType) {
        this.actuatorType = actuatorType;
    }

    public Float getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    public void setFuelTankCapacity(Float fuelTankCapacity) {
        this.fuelTankCapacity = fuelTankCapacity;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory;
    }

    public Boolean getElectronicFenceStatus() {
        return electronicFenceStatus;
    }

    public void setElectronicFenceStatus(Boolean electronicFenceStatus) {
        this.electronicFenceStatus = electronicFenceStatus;
    }

    public String getElectronicFenceScope() {
        return electronicFenceScope;
    }

    public void setElectronicFenceScope(String electronicFenceScope) {
        this.electronicFenceScope = electronicFenceScope;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public String getGearRange() {
        return gearRange;
    }

    public void setGearRange(String gearRange) {
        this.gearRange = gearRange;
    }
}
