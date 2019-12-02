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
                <input type="text" name="roleDescription" maxlength="50" autocomplete="off" class="layui-input" value="${role.roleDescription}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">角色排序：</label>
            <div class="layui-input-block">
                <input type="text" name="sort" autocomplete="off" class="layui-input" value="${role.sort}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">角色权限：</label>
        </div>
        <div class="layui-form-item">
                <div class="layui-input-block menu-tree"></div>
        </div>
        <div class="layui-form-item febs-hide" style="display: none">
            <button class="layui-btn" lay-submit="" lay-filter="job-add-form-submit" id="submit"></button>
            <button type="reset" class="layui-btn" id="reset"></button>
        </div>
    </form>
</div>

<script>
    layui.use(['jquery', 'admin', 'form','eleTree'], function () {
        var $ = layui.jquery,
            admin = layui.admin,
            form = layui.form,
            eleTree = layui.eleTree,
            $view = $('#job-add');
        form.render();

        var menuTree = eleTree.render({
            elem: '.menu-tree',
            url: '${basePath}system/menu/tree',
            showCheckbox: true,
            defaultExpandAll: true,
            where: {
                "invalidate_ie_cache": new Date().getTime()
            },
            accordion: true,
            checkStrictly: true,
            renderAfterExpand: false,
            request: {
                name: "title",
                key: "id",
                children: "childs",
                checked: "checked",
                data: "data"
            },
            response: {
                statusName: "code",
                statusCode: 0,
                dataName: "data"
            },
            done: function(){
                //菜单选中
                admin.get('${basePath}/system/role/menu?roleId=${role.id}', null, function (data) {
                    menuTree.setChecked(data.data.split(','), true);
                })
            }
        });



        form.on('submit', function (data) {
            var selected = menuTree.getChecked(false, true);
            var menuIds = [];
            layui.each(selected, function (key, item) {
                menuIds.push(item.id)
            });
            data.field.menuIds = menuIds.join(',');
            admin.post('${basePath}system/role/save', data.field, function () {
                layer.closeAll();
                admin.alert.success('角色保存完成');
                $('#familyList').find('#query').click();
            });
            return false;
        });
    });
</script>