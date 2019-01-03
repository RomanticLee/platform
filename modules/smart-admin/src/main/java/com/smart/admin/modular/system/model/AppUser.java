package com.smart.admin.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 测试
 * </p>
 *
 * @author sesame
 * @since 2018-11-15
 */
@TableName("app_user")
public class AppUser extends Model<AppUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 经纪人表主键
     */
    @TableId(value = "id", type = IdType.UUID)
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 公司地址
     */
    @TableField("company_address")
    private String companyAddress;
    /**
     * 区域管理员id
     */
    @TableField("area_administrator_id")
    private Integer areaAdministratorId;
    /**
     * 业务员姓名
     */
    @TableField("salesman_name")
    private String salesmanName;
    /**
     * 账号状态  0：已登录，1：未登录
     */
    private String state;
    /**
     * 注册时间
     */
    @TableField("registration_time")
    private Date registrationTime;
    /**
     * 账号到期时间
     */
    @TableField("expiry_time")
    private Date expiryTime;
    /**
     * 经纪人二维码地址
     */
    @TableField("qr_code")
    private String qrCode;
    /**
     * 删除标记 0：未删 1：已删
     */
    @TableField("del_flag")
    private String delFlag;


    
    
    public AppUser() {
		super();
	}

	public AppUser(String name, String password, String mobile, String companyAddress, Integer areaAdministratorId,
			String salesmanName) {
		super();
		this.name = name;
		this.password = password;
		this.mobile = mobile;
		this.companyAddress = companyAddress;
		this.areaAdministratorId = areaAdministratorId;
		this.salesmanName = salesmanName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public Integer getAreaAdministratorId() {
        return areaAdministratorId;
    }

    public void setAreaAdministratorId(Integer areaAdministratorId) {
        this.areaAdministratorId = areaAdministratorId;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AppUser{" +
        "id=" + id +
        ", name=" + name +
        ", password=" + password +
        ", mobile=" + mobile +
        ", companyAddress=" + companyAddress +
        ", areaAdministratorId=" + areaAdministratorId +
        ", salesmanName=" + salesmanName +
        ", state=" + state +
        ", registrationTime=" + registrationTime +
        ", expiryTime=" + expiryTime +
        ", qrCode=" + qrCode +
        ", delFlag=" + delFlag +
        "}";
    }
}
