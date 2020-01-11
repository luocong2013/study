// **********************************************//
// index 客户端脚本
//
// @author luoc
// @date 2019/6/22 17:16
// **********************************************//

var vm = new Vue({
    el: '#app',
    data: {
        yes: true,
        no: false,
        age: 28,
        name: 'keepfool'
    },
    methods: {
        login: function () {
            window.location.href = '/ibed/toLogin';
        }
    }
});