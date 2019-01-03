package cn.newcapec.city.smart.core.entity;

import cn.newcapec.city.smart.core.entity.base.BaseVerEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * APP版本控制表
 * Created by es on 2018/10/11.
 */
@Getter
@Setter
@Table(name = "BASE_APP_VERSION")
@Entity
public class AppVersionEntity extends BaseVerEntity {

    //应用类型: Android、IOS
    @Column(name = "APP_TYPE")
    private String appType;
    //应用名称
    @Column(name = "APP_NAME")
    private String appName;
    //应用描述
    @Column(name = "APP_DESC")
    private String appDesc;
    //渠道号
    @Column(name = "CHANNEL_CODE")
    private String channelCode;
    //应用版本号(内部版本号)
    @Column(name = "VERSION_CODE")
    private Integer versionCode;
    //应用版本号名称(用户可看到的)
    @Column(name = "VERSION_NAME")
    private String versionName;
    //应用下载地址
    @Column(name = "DOWNLOAD_URL")
    private String downloadUrl;
    //要下载的文件名
    @Column(name = "FILE_NAME")
    private String fileName;
    //版本日志(描述当前版本的功能)
    @Column(name = "VERSION_LOG")
    private String versionLog;
    //版本日志的网页版(保存描述版本日志的网页地址)
    @Column(name = "VERSION_LOG_HTML")
    private String versionLogHtml;
    //创建人
    @Column(name = "CJR")
    private String cjr;
    //创建时间
    @Column(name = "CJSJ")
    private Date cjsj;
}
