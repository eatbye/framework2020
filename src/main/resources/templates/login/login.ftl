
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/layui/css/layui.css?t=2" media="all">
    <link rel="stylesheet" href="/nepadmin/css/admin.css?t=2" media="all">
    <link rel="stylesheet" href="/nepadmin/css/login.css?t=2" media="all">
    <link rel="icon" href="/nepadmin/images/favicon.ico" type="image/x-icon"/>
</head>
<body>
<div id="febs-login" lay-title="登录">
    <div class="login-wrap">
        <div class="layui-container">
            <div class="layui-row">
                <div class="layui-col-xs12 layui-col-lg4 layui-col-lg-offset4 febs-tc">
                    <div class="layui-logo" style="text-align: center"><span>管理系统</span></div>
                </div>
                <div class="layui-col-xs12 layui-col-lg4 layui-col-lg-offset4" id="login-div">
                    <div class="layui-form" lay-filter="login-form">
                        <div class="layui-anim layui-anim-upbit">
                            <ul class="login-type-tab">
                                <li class="active">请输入用户名、密码登录系统</li>
                            </ul>
                            <div class="normal-login-form">
                                <div class="layui-form-item">
                                    <label class="layui-icon label-icon layui-icon-username"></label>
                                    <input type="text" name="username" lay-verify="required"
                                           placeholder="用户名" autocomplete="off" class="layui-input">
                                </div>
                                <div class="layui-form-item password-block">
                                    <label class="layui-icon label-icon layui-icon-password"></label>
                                    <input type="password" name="password" lay-verify="required"
                                           placeholder="密码" autocomplete="off" class="layui-input">
                                </div>
                                <div class="layui-form-item">
                                    <div class="layui-row">
                                        <div class="layui-col-xs7">
                                            <label class="layui-icon label-icon layui-icon-vercode"></label>
                                            <input type="text" maxlength="4" name="verifyCode" lay-verify="required"
                                                   placeholder="验证码" class="layui-input" autocomplete="off">
                                        </div>
                                        <div class="layui-col-xs5">
                                            <img class="codeimg" id="codeimg"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="layui-form-item" style="margin-top: -10px;">
                                    <input type="checkbox" name="rememberMe" value="1" title="记住我" lay-skin="primary">
                                </div>
                                <div class="layui-form-item">
                                    <button class="layui-btn layui-btn-normal layui-btn-fluid" lay-submit
                                            lay-filter="login-submit" id="login">
                                        <i style="display: none"
                                           class="layui-icon layui-icon-loading layui-icon layui-anim layui-anim-rotate layui-anim-loop"></i>
                                        立即登录
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<script src="/layui/layui.js"></script>
<script>
    var ctx = "\/";

    layui.extend({
        validate: './febs/lay/modules/validate'
    }).use(['form', 'layer'], function (form, layer) {
        var $ = layui.jquery,
            $view = $('#febs-login'),
            type = 'login',
            $loginDiv = $view.find('#login-div'),
            $registDiv = $view.find('#regist-div');

        form.render();
        initCode();

        //提交登录表单
        form.on('submit(login-submit)', function (data) {
            var loading = $(this).find('.layui-icon');
            if (loading.is(":visible")) return;
            loading.show();
            $.post('/login/checkLogin', data.field, function (r) {
                if (r.code === 0) {
                    location.href = '/index';
                } else {
                    layer.msg(r.message);
                    loading.hide();
                    initCode();
                }
            });
            return false;
        });

        function initCode() {
            $view.find('#codeimg').attr("src", "/images/captcha?data=" + new Date().getTime());
        }

        $view.find('#codeimg').on('click', function () {
            initCode();
        });



        $view.find('#login-href').on('click', function () {
            resetForm();
            type = 'login';
            $loginDiv.show();
            $registDiv.hide();
        });

        $(document).on('keydown', function (e) {
            if (e.keyCode === 13) {
                $view.find('#login').trigger("click");
            }
        });

    });
</script>
</body>
</html>