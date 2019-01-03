/**
 * 初始化小区详情对话框
 */
var VillageInfoDlg = {
    villageInfoData : {},
    validateFields : {
    	name : {
    		validators : {
				notEmpty : {
					message : '小区名字不能为空'
				}
    		}
    	},
    	address : {
    		validators : {
				notEmpty : {
					message : '小区地址不能为空'
				}
    		}
    	}
    }
};

/**
 * 清除数据
 */
VillageInfoDlg.clearData = function() {
    this.villageInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
VillageInfoDlg.set = function(key, val) {
    this.villageInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
VillageInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
VillageInfoDlg.close = function() {
    parent.layer.close(window.parent.Village.layerIndex);
}

/**
 * 收集数据
 */
VillageInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('address')
    .set('deptId')
    .set('longitude')
    .set('latitude');
}

/**
 * 验证数据
 */
VillageInfoDlg.validate = function() {
	$('#villageInfoForm').data("bootstrapValidator").resetForm();
    $('#villageInfoForm').bootstrapValidator('validate');
    return $("#villageInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 提交添加
 */
VillageInfoDlg.addSubmit = function() {
	/*var isAddress = getLL();
	if(isAddress == 0){
		return;
	}*/
	
    this.clearData();
    this.collectData();
    
    if (!this.validate()) {
		return;
	}
    if($("#longitude").val() == "" || $("#longitude").val() == null){
    	alert("请获取经纬度");
    	return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/village/add", function(data){
        Feng.success("添加成功!");
        window.parent.Village.table.refresh();
        VillageInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.villageInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
VillageInfoDlg.editSubmit = function() {
	/*var isAddress = getLL();
	if(isAddress == 0){
		return;
	}*/
	
    this.clearData();
    this.collectData();
    
    if (!this.validate()) {
		return;
	}

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/village/update", function(data){
        Feng.success("修改成功!");
        window.parent.Village.table.refresh();
        VillageInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.villageInfoData);
    ajax.start();
}

$(function() {
	Feng.initValidator("villageInfoForm", VillageInfoDlg.validateFields);
	$("#getLL").click(function() {
		getLL();
	});
	
});
function getLL(){
	var address = $("#address").val();//"郑州国际陆港开发建设有限公司 " 
	if(address == ""){
		alert("请输入小区地址");
		return;
	}
	$("#longitude").val("");
	$("#latitude").val("");
	//var flag = 0;
    var url = "http://api.map.baidu.com/geocoder/v2/?address=" + address + "&output=json&ak=FG7wxr1VUj0k2NwoO3yXzymd&callback=?";
    $.getJSON(url, function (data) {
        //alert(JSON.stringify(data));
        //请求成功
        if(data.status == "0"){
        	//经度
        	var longitude = data.result.location.lng;
        	$("#longitude").val(longitude);
        	//纬度
            var latitude = data.result.location.lat;
            $("#latitude").val(latitude);
            //flag = 1;
        	//console.log("longtitude:"+longtitude+",latitude:"+latitude);
        } else {
        	alert("小区地址错误请重新输入。");
        	//flag = 0;
        }
    });
    //return flag;
}