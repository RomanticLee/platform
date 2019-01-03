

/**
 * 小区管理初始化
 */
var Village = {
    id: "VillageTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Village.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            /*{title: '小区表主键', field: 'id', visible: false, align: 'center', valign: 'middle'},*/
            {title: '小区名', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '小区地址', field: 'address', visible: true, align: 'center', valign: 'middle'},
            /*{title: '区域id', field: 'deptId', visible: false, align: 'center', valign: 'middle'},*/
            {title: '经度', field: 'longitude', visible: true, align: 'center', valign: 'middle'},
            {title: '纬度', field: 'latitude', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Village.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Village.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加小区
 */
Village.openAddVillage = function () {
    var index = layer.open({
        type: 2,
        title: '添加小区',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/village/village_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看小区详情
 */
Village.openVillageDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '小区详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/village/village_update/' + Village.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除小区
 */
Village.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/village/delete", function (data) {
            Feng.success("删除成功!");
            Village.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("villageId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询小区列表
 */
Village.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Village.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Village.initColumn();
    var table = new BSTable(Village.id, "/village/list", defaultColunms);
    table.setPaginationType("client");
    Village.table = table.init();
});
