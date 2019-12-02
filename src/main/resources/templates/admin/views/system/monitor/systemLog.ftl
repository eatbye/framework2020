<div class="layui-fluid" id="familyList" lay-title="系统日志">
    <div class="layui-row layui-col-space12">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body nepadmin-table-full">
                    <div class="layui-card-header"><b>系统日志</b></div>

                    <div class="test-table-reload-btn" style="margin:10px">
                        <form id="queryForm">
                            登录用户：
                            <div class="layui-inline">
                                <input class="layui-input" name="username" id="username" autocomplete="off" style="height: 30px;">
                            </div>
                            <button class="layui-btn layui-btn-sm" id="query" type="button">搜索</button>
                        </form>
                    </div>
                    <table class="layui-table" id="list-table" lay-filter="list-table"></table>
                </div>
            </div>
        </div>
    </div>
    <script type="text/html" id="TPL-list-table-params">
        {{# layui.each(d.params,function(i,item){ }}
        <span class="layui-badge-rim">{{ item.val }}</span>
        {{# }) }}
    </script>
    <script type="text/html" id="TPL-list-table-status">
        {{#
        var status = {
        1:{title:'有效',color:'blue'},
        2:{title:'无效',color:'orange'},
        }[d.valid];
        }}
        <span class="layui-badge layui-bg-{{status.color}}">{{ status.title }}</span>
    </script>
</div>

<script type="text/html" id="table-operate-bar">
    <a lay-event="del"><i class="layui-icon febs-edit-area febs-red">&#xe7f9;</i></a>
</script>
<script type="text/html" id="log-time">
    <div>
        <span class="layui-badge">{{d.time}} ms</span>
    </div>
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
            url: '${basePath}/system/monitor/systemLogListData',
            height: 'full-165',
            cols: [[
                // {title:'', type:'checkbox', width:'6%'},
                {type:'numbers', title:'序号', align:'center', width:'6%'},
                {field: 'username', title: '用户', width:'6%'},
                {field: 'ip', title: 'IP地址', width:'10%'},
                {field: 'createTime', title: '操作时间', minWidth: 180, sort: true},
                {field: 'operation', title: '操作内容', width:'10%'},
                {field: 'method', title: '操作方法'},
                {field: 'params', title: '操作参数'},
                {field: 'time', title: '耗时', width:'6%', templet: "#log-time"},
                {title: '操作', toolbar: '#table-operate-bar', width:'6%'}
            ]]
        });

        table.on('tool(list-table)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;

            if (layEvent === 'del') {
                admin.modal.confirm('删除日志', '确定删除该条系统日志？', function () {
                    admin.get('${basePath}system/monitor/deleteSystemLog?id='+data.id, null, function () {
                        admin.alert.success('删除日志成功');
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



    })
</script>