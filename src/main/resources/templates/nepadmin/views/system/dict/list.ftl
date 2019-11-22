<div class="layui-fluid" id="dictList" lay-title="数据字典管理">
    <div class="layui-row layui-col-space12">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body nepadmin-table-full">
                    <div class="layui-card-header"><b>数据字典管理</b></div>

                    <div class="test-table-reload-btn" style="margin:10px">
                        <form id="queryForm">
                        字典名称：
                        <div class="layui-inline">
                            <input class="layui-input" name="name" id="name" autocomplete="off" style="height: 30px;">
                        </div>
                        <button class="layui-btn layui-btn-sm" id="query" type="button">搜索</button>
                        <button class="layui-btn layui-btn-sm" id="add" type="button">登记</button>
                        </form>
                    </div>
                    <table class="layui-table" id="list-table" lay-filter="list-table"></table>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/html" id="table-operate-bar">
    <a class="layui-btn layui-btn-normal layui-btn-xs"  data-type="test8" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs"  data-type="test8" lay-event="del">删除</a>
</script>

<script>
    layui.use(['admin', 'table', 'form', 'dropdown', 'jquery'], function (admin, table, form, dropdown, $) {
        var $ = layui.jquery,
            view = $('#familyList'),
            query = $("#query"),
            add = $("#add");

        var tableFilter = 'list-table';
        form.render();

        //表格
        var tableIns = table.render({
            elem: '[lay-filter="' + tableFilter + '"]',
            url: '${basePath}system/dict/listData',
            height: 'full-165',
            cols: [[
                {title:'', type:'checkbox', width:60},
                {type:'numbers', title:'序号', align:'center', width:60},
                {title: '名称', field: 'name', align:'left', width: '20%'},
                {title: '数据', field: 'value'},
                {title: '操作', align: 'center', fixed: 'right',  width: 170, toolbar: '#table-operate-bar'}
            ]]
        });

        table.on('tool(list-table)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;

            if (layEvent === 'edit') {
                admin.modal.open('修改数据字典', 'system/dict/form?id='+data.id, {
                    area: '70%',
                    btn: ['提交', '取消'],
                    yes: function (index, layero) {
                        $('#form').find('#submit').trigger('click');
                    },
                    btn2: function () {
                        layer.closeAll();
                    }
                });
            }

            if (layEvent === 'del') {
                admin.modal.confirm('删除数据字典', '确定删除该数据字典吗？', function () {
                    admin.get('${basePath}system/dict/delete?id='+data.id, null, function () {
                        admin.alert.success('删除数据字典成功');
                        query.click();
                    });
                });
            }
        });

        //查询功能
        query.on('click', function () {
            var params = $.extend(admin.form2json("queryForm"), {});
            tableIns.reload({where: params, page: {curr: 1}});
        });

        //新增
        add.on('click', function () {
            admin.modal.open('数据字典管理', 'system/dict/form', {
                area: '70%',
                btn: ['提交', '取消'],
                yes: function (index, layero) {
                    $('#form').find('#submit').trigger('click');
                },
                btn2: function () {
                    layer.closeAll();
                }
            });
        });

    })
</script>