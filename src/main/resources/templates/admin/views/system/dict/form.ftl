<div class="layui-fluid" id="form" style="margin-top:10px">
    <form class="layui-form" action="" lay-filter="add-form">
    数据字典名称：
    <div class="layui-inline">
        <input type="hidden" name="id" value="${dict.id}"/>
        <input type="hidden" name="size" value="${dictList?size}"/>
        <input class="layui-input" name="name" value="${dict.name}" autocomplete="off" style="height: 30px;width: 300px;">
    </div>
    <table class="layui-table">
        <thead>
        <tr>
            <td style="text-align: center">名称</td>
            <td style="text-align: center">颜色</td>
            <td style="text-align: center">数值</td>
            <td style="text-align: center">有效</td>
            <td style="text-align: center">排序</td>
        </tr>
        </thead>
        <tbody>
        <#list dictList as sondict>
        <tr>
            <td><input type="hidden" name="sonId_${sondict_index}" value="${sondict.id}"/>
                <input class="layui-input" name="sonName_${sondict_index}" value="${sondict.name}" autocomplete="off" style="height: 30px;"></td>
            <td><input class="layui-input" name="color_${sondict_index}" value="${sondict.color}" autocomplete="off" style="height: 30px;"></td>
            <td><input class="layui-input" name="value_${sondict_index}" value="${sondict.value}" autocomplete="off" style="height: 30px;"></td>
            <td style="text-align: center"><input type="checkbox" name="valid_${sondict_index}" value="1" <#if sondict.valid==1> checked</#if>></td>
            <td><input class="layui-input" name="sort_${sondict_index}" value="${sondict.sort}" autocomplete="off" style="height: 30px;;width: 60px;"></td>
        </tr>
        </#list>
        </tbody>
    </table>

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
            admin.post('${basePath}/system/dict/save', data.field, function () {
                layer.closeAll();
                admin.alert.success('数据字典保存完成');
                $('#dictList').find('#query').click();
            });
            return false;
        });

    });
</script>
