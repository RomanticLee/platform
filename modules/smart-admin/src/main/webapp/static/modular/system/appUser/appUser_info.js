/**
 * 初始化appUser详情对话框
 */
var AppUserInfoDlg = {
    appUserInfoData : {},
    validateFields : {
    	name : {
    		validators : {
    			notEmpty : {
					message : '姓名不能为空'
				}
    		}
    	},
    	mobile : {
    		validators : {
    			numeric : {
					message : '手机号必须为数字'
				},
				stringLength: {
                    min: 11,
                    max: 11,
                    message: '手机号长度必须为11位'
                }
    		}
    	},
    	companyAddress : {
    		validators : {
    			notEmpty : {
					message : '公司地址不能为空'
				}
    		}
    	},
    	salesmanName : {
    		validators : {
    			notEmpty : {
					message : '业务员不能为空'
				}
    		}
    	},
    	expiryTime : {
    		validators : {
    			notEmpty : {
					message : '账号到期时间不能为空'
				}
    		}
    	}
    }
};

/**
 * 清除数据
 */
AppUserInfoDlg.clearData = function() {
    this.appUserInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AppUserInfoDlg.set = function(key, val) {
    this.appUserInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AppUserInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AppUserInfoDlg.close = function() {
    parent.layer.close(window.parent.AppUser.layerIndex);
}

/**
 * 收集数据
 */
AppUserInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('password')
    .set('mobile')
    .set('companyAddress')
    .set('areaAdministratorId')
    .set('salesmanName')
    .set('state')
    .set('registrationTime')
    .set('expiryTime')
    .set('qrCode')
    .set('delFlag');
}

/**
 * 验证数据
 */
AppUserInfoDlg.validate = function() {
	$('#appUserInfoForm').data("bootstrapValidator").resetForm();
    $('#appUserInfoForm').bootstrapValidator('validate');
    return $("#appUserInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 提交添加
 */
AppUserInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
		return;
	}

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/appUser/add", function(data){
        Feng.success("添加成功!");
        window.parent.AppUser.table.refresh();
        AppUserInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.appUserInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AppUserInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
		return;
	}

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/appUser/update", function(data){
        Feng.success("修改成功!");
        window.parent.AppUser.table.refresh();
        AppUserInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.appUserInfoData);
    ajax.start();
}

$(function() {
	Feng.initValidator("appUserInfoForm", AppUserInfoDlg.validateFields);
});
