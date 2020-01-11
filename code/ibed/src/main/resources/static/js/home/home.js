// **********************************************//
// home 客户端脚本
//
// @author luoc
// @date 2020/1/11 11:47
// **********************************************//
new Vue({
    el: '#homeApp',
    data: {
        // 请求参数
        requestParam: {
            prefix: null
        },
        // 返回结果
        responseData: [],
        // 记录栈
        recordStack: []
    },
    methods: {
        // 初始化方法
        init: function() {
            var _this = this;
            _this.loadData();
        },
        // 加载数据
        loadData: function(prefix) {
            var _this = this;
            _this.requestParam.prefix = prefix;
            $.ajax({
                type: 'GET',
                url: 'home/loadData',
                dataType: 'json',
                data: JSON.parse(JSON.stringify(_this.requestParam)),
                success: function(response) {
                    if (response.success) {
                        _this.responseData = response.data;
                    } else {
                        console.log(response.message);
                    }
                },
                error: function(data) {
                    console.log(data);
                }
            });
        },
        // 查看
        lookOver: function(row) {
            var _this = this;
            if (row.dir) {
                _this.recordStack.push(row.objectName);
                _this.loadData(row.objectName);
            } else {
                // 打开图片
            }
        },
        // 返回上一页
        backTrack: function() {
            var _this = this;
            _this.loadData(_this.recordStack.pop());
        }
    },
    // vm实例初始化后、创建之前执行
    beforeCreate: function() {

    },
    // vm实例创建后执行
    created: function() {
        var _this = this;
        _this.init();
    },
    // dom挂载开始之前执行
    beforeMounted: function() {

    },
    // dom插入后调用该函数
    mounted: function() {

    },
    // 数据更新之前调用
    beforeUpdate: function() {

    },
    // 更新数据后调用该函数
    update: function() {

    }
});