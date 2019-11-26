<div class="layui-fluid" id="form" style="margin-top:10px">
    <form class="layui-form" action="" lay-filter="job-add-form">
        <input type="hidden" name="id" value="${role.id}"/>
        <div class="layui-form-item">
            <label class="layui-form-label febs-form-item-require">角色名称：</label>
            <div class="layui-input-block">
                <input type="text" name="roleName" lay-verify="required" autocomplete="off" class="layui-input" value="${role.roleName}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label febs-form-item-require">角色描述：</label>
            <div class="layui-input-block">
                <input type="text" name="roleDescription" maxlength="50" lay-verify="required" autocomplete="off" class="layui-input" value="${role.roleDescription}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">角色排序：</label>
            <div class="layui-input-block">
                <input type="text" name="sort" autocomplete="off" class="layui-input" value="${role.sort}">
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
        form.render();

        form.on('submit', function (data) {
            admin.post('${basePath}system/role/save', data.field, function () {
                layer.closeAll();
                admin.alert.success('角色保存完成');
                $('#familyList').find('#query').click();
            });
            return false;
        });
    });
</script>