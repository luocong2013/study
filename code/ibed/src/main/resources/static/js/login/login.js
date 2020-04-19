// **********************************************//
// login 客户端脚本
//
// @author luoc
// @date 2019/6/23 16:25
// **********************************************//
new Vue({
    el: '#loginApp',
    data: {
        user: {
            username: null,
            password: null
        },
        userRules: {
            username: [
                {required: true, message: '请输入用户名', trigger: 'blur'},
                {min: 3, max: 10, message: '用户名长度在 3 到 10 个字符之间', trigger: 'blur'}
            ],
            password: [
                {required: true, message: '请输入密码', trigger: 'change'}
            ]
        }
    },
    methods: {
        // 校验提交数据
        submitForm: function(formName) {
            var _this = this;
            _this.$refs[formName].validate(function(valid) {
                if (valid) {
                    _this.doSubmit();
                } else {
                    console.log('error submit!');
                    return false;
                }
            })
        },
        // 提交请求
        doSubmit: function () {
            var _this = this;
            $.ajax({
                type: 'POST',
                url: 'login',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                data: JSON.stringify(_this.user),
                success: function (data) {
                    var success = data.success;
                    if (success) {
                        window.location.href = '/ibed/toHome';
                    } else {
                        console.log("登录失败");
                    }
                },
                error: function (data) {
                    console.log(data.responseText);
                }
            });
        },
        // 清空表单
        resetForm: function(formName) {
            var _this = this;
            _this.$refs[formName].resetFields();
        },
        isEmpty: function (obj) {
            return typeof obj == 'undefined' || obj == null || obj === '';
        }
    }
});