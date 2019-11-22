<div class="layui-fluid" id="form" style="margin-top:10px">
    <form class="layui-form" action="" lay-filter="job-add-form">
        <input type="hidden" name="id" value="${family.id}"/>
        <div class="layui-form-item">
            <label class="layui-form-label febs-form-item-require">姓名：</label>
            <div class="layui-input-block">
                <input type="text" name="name" lay-verify="required" autocomplete="off" class="layui-input" value="${family.name}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label febs-form-item-require">地址：</label>
            <div class="layui-input-block">
                <input type="text" name="address" maxlength="50" lay-verify="required" autocomplete="off" class="layui-input" value="${family.address}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">生日：</label>
            <div class="layui-input-block">
                <input type="text" name="birthday" autocomplete="off" class="layui-input" value="${family.birthday}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">面积：</label>
            <div class="layui-input-block">
                <input type="text" name="area" autocomplete="off" class="layui-input" value="${family.area}">
            </div>
        </div>
        <div class="layui-form-item febs-hide" style="display: none">
            <button class="layui-btn" lay-submit="" lay-filter="job-add-form-submit" id="submit"></button>
            <button type="reset" class="layui-btn" id="reset"></button>
        </div>
    </form>
</div>

<script>
    layui.use(['jquery', 'admin', 'form'], function () {
        var $ = layui.jquery,
            admin = layui.admin,
            form = layui.form,
            $view = $('#job-add');
            // validate = layui.validate;

        // form.verify(validate);
        form.render();

        // alert(0);
        // alert(form);

        form.on('submit', function (data) {
            admin.post('/nepadmin/views/family/save', data.field, function () {
                layer.closeAll();
                admin.alert.success('家庭信息保存完成');
                $('#familyList').find('#query').click();
            });
            return false;
        });

    });
</script>