// **********************************************//
// login 客户端脚本
//
// @author luoc
// @date 2019/6/23 16:25
// **********************************************//
new Vue({
    el: '#app',
    data: {
        user: {
            username: null,
            password: null
        },
        visible: false
    },
    methods: {
        check: function () {
            // 获取值
            var _this = this;
            var username = _this.user.username;
            var password = _this.user.password;
            if (_this.isEmpty(username) || _this.isEmpty(password)) {
                alert('账号或密码为空！');
                return;
            }
            $.ajax({
                type: 'POST',
                url: 'login',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                data: JSON.stringify(_this.user),
                success: function (data) {
                    var success = data.success;
                    if (success) {
                        alert("登录成功");
                    } else {
                        alert("登录失败");
                    }
                },
                error: function (data) {
                    alert(data.responseText);
                }
            });
        },
        isEmpty: function (obj) {
            return typeof obj == 'undefined' || obj == null || obj === '';
        }
    }
});