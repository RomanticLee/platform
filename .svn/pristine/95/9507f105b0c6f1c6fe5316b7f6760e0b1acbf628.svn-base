/**
 * appUser管理初始化
 */
var AppUser = {
    id: "AppUserTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AppUser.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            /*{title: '经纪人表主键', field: 'id', visible: true, align: 'center', valign: 'middle'},*/
            {title: '姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
            /*{title: '密码', field: 'password', visible: true, align: 'center', valign: 'middle'},*/
            {title: '手机号', field: 'mobile', visible: true, align: 'center', valign: 'middle'},
            {title: '公司地址', field: 'companyAddress', visible: true, align: 'center', valign: 'middle'},
            /*{title: '区域管理员id', field: 'areaAdministratorId', visible: true, align: 'center', valign: 'middle'},*/
            {title: '业务员姓名', field: 'salesmanName', visible: true, align: 'center', valign: 'middle'},
            {title: '账号状态', field: 'state', visible: true, align: 'center', valign: 'middle'},
            {title: '注册时间', field: 'registrationTime', visible: true, align: 'center', valign: 'middle'},
            {title: '账号到期时间', field: 'expiryTime', visible: true, align: 'center', valign: 'middle'}
            /*{title: '经纪人二维码地址', field: 'qrCode', visible: true, align: 'center', valign: 'middle'},
            {title: '删除标记 0：未删 1：已删', field: 'delFlag', visible: true, align: 'center', valign: 'middle'}*/
    ];
};

/**
 * 检查是否选中
 */
AppUser.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AppUser.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加appUser
 */
AppUser.openAddAppUser = function () {
    var index = layer.open({
        type: 2,
        title: '添加appUser',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/appUser/appUser_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看appUser详情
 */
AppUser.openAppUserDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'appUser详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/appUser/appUser_update/' + AppUser.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除appUser
 */
AppUser.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/appUser/delete", function (data) {
            Feng.success("删除成功!");
            AppUser.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("appUserId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询appUser列表
 */
AppUser.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    AppUser.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AppUser.initColumn();
    var table = new BSTable(AppUser.id, "/appUser/list", defaultColunms);
    table.setPaginationType("client");
    AppUser.table = table.init();
});