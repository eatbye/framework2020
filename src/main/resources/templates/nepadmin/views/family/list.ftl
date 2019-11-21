<div class="layui-fluid" id="VIEW-list-table" lay-title="家庭信息管理">
    <div class="layui-row layui-col-space12">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body nepadmin-table-full">
                    <div class="layui-card-header"><b>家庭信息管理</b></div>

                    <div class="test-table-reload-btn" style="margin:10px">
                        姓名：
                        <div class="layui-inline">
                            <input class="layui-input" name="id" autocomplete="off" style="height: 30px;">
                        </div>
                        地址：
                        <div class="layui-inline">
                            <input class="layui-input" name="id" autocomplete="off" style="height: 30px;">
                        </div>
                        <button class="layui-btn layui-btn-sm">搜索</button>
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
</script>

<script>
    layui.use(['admin', 'table', 'form', 'dropdown', 'jquery'], function (admin, table, form, dropdown, $) {
        var view = $('#VIEW-list-table');

        var tableFilter = 'list-table';
        form.render();
        table.render({
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
                {title: '操作', align: 'center', fixed: 'right',  width: '10%', toolbar: '#table-operate-bar'}
                // { title: '商品参数', templet: '#TPL-list-table-params', minWidth: 240 },
                // { title: '商品单价', templet: '<p><b class="nepadmin-c-red">￥{{d.price}}</b></p>', align: 'center', width: 90 },
                // { title: '购买数量', templet: '<p><b>{{d.buycount}}</b> <span class="nepadmin-c-gray">件</span></p>', align: 'center', width: 90 },
                // { field: 'time', title: '操作时间', templet: '<p><span title="{{d.time}}" class="nepadmin-c-gray">{{ layui.util.timeAgo(d.time)}}</span></p>', align: 'center', width: 170 }
            ]]
        });


        dropdown.render({
            elem: view.find('.action-more'),
            click: function (name, elem, event) {
                console.log('点击了' + name);
            },
            options: [{
                name: 'action1',
                title: 'Menu 1'
            }, {
                name: 'action2',
                title: 'Menu 2',
                options: [{
                    name: 'action3',
                    title: 'Menu 3',
                }, {
                    name: 'action4',
                    title: 'Menu 4',
                }]
            }]
        });

        view.find('.table-action').click(function () {
            var type = $(this).attr('data-type')
            if (type == 'refresh') {
                //刷新当前页
                layui.view.tab.refresh()

                //刷新一个指定页，不用传入页面的参数
                //layui.view.tab.refresh('/index')

                return false;
            }
            var checkedCount = table.checkStatus(tableFilter).data.length;
            if (checkedCount == 0) {
                layer.msg('请先选择某行');
            } else {
                layer.msg($(this).html() + checkedCount + '项');
            }
        })


    })
</script>