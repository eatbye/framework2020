<div class="layui-fluid" id="form" style="margin-top:10px">
    <form class="layui-form" action="" lay-filter="job-add-form">
        <input type="hidden" name="id" value="${user.id}"/>
        <div class="layui-form-item">
            <label class="layui-form-label febs-form-item-require">姓名：</label>
            <div class="layui-input-block">
                <input type="text" name="realName" lay-verify="required" autocomplete="off" class="layui-input" value="${user.realName}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label febs-form-item-require">用户名：</label>
            <div class="layui-input-block">
                <input type="text" name="username" maxlength="50" lay-verify="required" autocomplete="off" class="layui-input" value="${user.username}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label febs-form-item-require">密码：</label>
            <div class="layui-input-block">
                <input type="text" name="password" maxlength="50" lay-verify="required" autocomplete="off" class="layui-input" value="${user.password}">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label febs-form-item-require">部门：</label>
            <div class="layui-input-block">
                <input type="text" name="departmentId" id="user-update-dept" lay-filter="user-update-dept">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label febs-form-item-require">角色：</label>
            <div class="layui-input-block">
                <select name="roleId"
                        lay-verify="required"
                        xm-select-direction="down"
                        xm-select="user-update-role"
                        xm-select-skin="default">
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label febs-form-item-require">是否有效：</label>
            <div class="layui-input-block">
                <input type="radio" name="valid" value="1" title="有效" <#if user.valid==1> checked="checked"</#if>>
                <input type="radio" name="valid" value="2" title="禁用" <#if user.valid==2> checked="checked"</#if>>
            </div>
        </div>


        <div class="layui-form-item febs-hide" style="display: none">
            <button class="layui-btn" lay-submit="" lay-filter="job-add-form-submit" id="submit"></button>
            <button type="reset" class="layui-btn" id="reset"></button>
        </div>
    </form>
</div>

<script>
    layui.use(['jquery', 'admin', 'form', 'treeSelect','formSelects'], function () {
        var $ = layui.jquery,
            admin = layui.admin,
            form = layui.form,
            treeSelect = layui.treeSelect,
            formSelects = layui.formSelects,
            $view = $('#job-add');

        form.render();

        treeSelect.render({
            elem: $('#user-update-dept'),
            type: 'get',
            data: '${basePath}system/department/selectTree',
            placeholder: '请选择',
            search: false,
            success: function () {
                treeSelect.checkNode('user-update-dept', '${user.departmentId}');
            }
        });

        formSelects.config('user-update-role', {
            searchUrl: '${basePath}system/role/roleAllList',
            response: {
                statusCode: 0
            },
            beforeSuccess: function (id, url, searchVal, result) {
                var data = result.data;
                var tranData = [];
                for (var i = 0; i < data.length; i++) {
                    tranData.push({
                        name: data[i].roleName,
                        value: data[i].id
                    })
                }
                result.data = tranData;
                return result;
            },
            success: function () {
                formSelects.value('user-update-role', '${roleId}'.split(','));
            },
            error: function (id, url, searchVal, err) {
                console.error(err);
                febs.alert.error('获取角色列表失败');
            }
        });

        form.on('submit', function (data) {
            admin.post('${basePath}system/user/save', data.field, function () {
                layer.closeAll();
                admin.alert.success('用户信息保存完成');
                $('#familyList').find('#query').click();
            });
            return false;
        });

    });
</script>