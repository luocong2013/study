// **********************************************//
// home 客户端脚本
//
// @author luoc
// @date 2020/1/11 11:47
// **********************************************//
new Vue({
    el: '#homeApp',
    data: {
        requestParam: {
            prefix: null
        },
        responseData: []
    },
    methods: {
        init: function() {
            var _this = this;
            _this.loadData("");
        },
        loadData: function(prefix) {
            var _this = this;
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