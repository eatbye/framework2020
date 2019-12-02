<div class="layui-fluid" id="familyList" lay-title="在线用户">
    <div class="layui-row layui-col-space12">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body admin-table-full">
                    <div class="layui-card-header"><b>在线用户</b></div>

                    <div class="test-table-reload-btn" style="margin:10px">
                        <form id="queryForm">
                            姓名：
                            <div class="layui-inline">
                                <input class="layui-input" name="name" id="name" autocomplete="off" style="height: 30px;">
                            </div>
                            地址：
                            <div class="layui-inline">
                                <input class="layui-input" name="address" id="address" autocomplete="off" style="height: 30px;">
                            </div>
                            <button class="layui-btn layui-btn-sm" id="query" type="button">搜索</button>
                            <@shiro.hasPermission name="family:edit">
                                <button class="layui-btn layui-btn-sm" id="add" type="button">登记</button>
                            </@shiro.hasPermission>
                        </form>
                    </div>
                    <table class="layui-table" id="list-table" lay-filter="list-table"></table>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/html" id="online-username">
    <div>
        {{d.username}}
        {{#
        if (d.current) {
        }}
        <span class="layui-badge febs-tag-red">current</span>
        {{#
        }
        }}
    </div>
</script>
<script type="text/html" id="online-status">
    {{#
    var status = {
    1: {title: '在线', color: 'green'},
    0: {title: '离线', color: 'volcano'}
    }[d.status];
    }}
    <span class="layui-badge febs-tag-{{status.color}}">{{ status.title }}</span>
</script>
<script type="text/html" id="online-option">
    <a lay-event="del"><i class="layui-icon febs-edit-area febs-red">&#xe7a1;</i></a>
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
            url: '${basePath}system/monitor/onlineList',
            height: 'full-165',
            page: false,
            cols: [[
                {type:'numbers', title:'序号', align:'center', width:60},
                {title: '用户名', templet: '#online-username', minWidth: 180},
                {field: 'startTimestamp', title: '登录时间', minWidth: 180},
                {field: 'lastAccessTime', title: '最后访问时间', minWidth: 180},
                {field: 'host', title: 'IP地址', minWidth: 165},
                {field: 'location', title: '登录地点', minWidth: 180},
                {title: '状态', templet: '#online-status'},
                {title: '操作', toolbar: '#online-option', minWidth: 80}
            ]]
        });

        table.on('tool(list-table)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;

            if (layEvent === 'del') {
                admin.modal.confirm('踢出用户', '确定将该用户踢出？', function () {
                    admin.get('${basePath}system/monitor/deleteSession?id='+data.id, null, function () {
                        admin.alert.success('踢出用户成功');
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
            admin.modal.open('家庭管理', 'family/form', {
                area: $(window).width() <= 750 ? '90%' : '50%',
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