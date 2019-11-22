<div class="layui-fluid" id="familyList" lay-title="家庭信息管理">
    <div class="layui-row layui-col-space12">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body nepadmin-table-full">
                    <div class="layui-card-header"><b>家庭信息管理</b></div>

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
                        <button class="layui-btn layui-btn-sm" id="add" type="button">登记</button>
                        </form>
                    </div>
                    <table class="layui-table" id="list-table" lay-filter="list-table"></table>
<#--                    <table class="layui-table" lay-even lay-skin="line" id="list-table" lay-filter="list-table"></table>-->
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
        WAIT_PAY:{title:'待付款',color:'blue'},
        WAIT_DELIVER:{title:'待发货',color:'orange'},
        WAIT_REFUND:{title:'待退款',color:'red'},
        }[d.status];
        }}
        <span class="layui-badge layui-bg-{{status.color}}">{{ status.title }}</span>
    </script>
</div>

<script type="text/html" id="table-operate-bar">
    <a class="layui-btn layui-btn-normal layui-btn-xs"  data-type="test8" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-normal layui-btn-xs"  data-type="test8" lay-event="view">查看</a>
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
            url: '/nepadmin/views/family/listData',
            height: 'full-165',
            cols: [[
                {title:'', type:'checkbox', width:'6%'},
                {type:'numbers', title:'序号', align:'center', width:'6%'},
                // { title: '状态', templet: '#TPL-list-table-status', width: 80 },
                {title: '姓名', field: 'name', align:'center', width: '20%'},
                {title: '地址', field: 'address'},
                {title: '面积', field: 'area',align:'center', width: '10%'},
                {title: '操作', align: 'center', fixed: 'right',  width: 170, toolbar: '#table-operate-bar'}
            ]]
        });

        table.on('tool(list-table)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;

            if (layEvent === 'edit') {
                admin.modal.open('修改家庭信息', 'family/form?id='+data.id, {
                    area: $(window).width() <= 750 ? '90%' : '50%',
                    btn: ['提交', '取消'],
                    yes: function (index, layero) {
                        $('#form').find('#submit').trigger('click');
                    },
                    btn2: function () {
                        layer.closeAll();
                    }
                });
            }
            if (layEvent === 'view') {
                admin.modal.open('查看家庭信息', 'family/view?id='+data.id, {
                    area: $(window).width() <= 750 ? '90%' : '50%',
                    btn: ['关闭'],
                    yes: function (index, layero) {
                        layer.closeAll();
                    }
                });
            }
            if (layEvent === 'del') {
                admin.modal.confirm('删除家庭', '确定删除该家庭吗？', function () {
                    admin.get('/nepadmin/views/family/delete?id='+data.id, null, function () {
                        admin.alert.success('删除家庭成功');
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