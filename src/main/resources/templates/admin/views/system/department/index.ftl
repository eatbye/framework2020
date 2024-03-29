<div class="layui-fluid layui-anim febs-anim" id="febs-dept" lay-title="部门管理">
    <div class="layui-row layui-col-space8 febs-container">
        <div class="layui-col-md6 layui-col-sm6 layui-col-xs12">
            <div class="layui-card">
                <div class="layui-card-body febs-table-full">
                    <form class="layui-form layui-table-form" lay-filter="dept-table-form" id="dept-table-form">
                        <div class="layui-row">
                            <div class="layui-col-md8 layui-col-sm9 layui-col-xs9">
                                <div class="layui-form-item">
                                    <div class="layui-inline">
                                        <button class="layui-btn layui-btn-sm" id="add" type="button">新增</button>
                                        <button class="layui-btn layui-btn-sm" id="del" type="button">删除</button>
                                    </div>
                                </div>
                            </div>
<#--                            <div class="layui-col-md4 layui-col-sm12 layui-col-xs12 table-action-area">-->
<#--                                <div class="layui-btn layui-btn-sm layui-btn-primary table-action" id="query">-->
<#--                                    <i class="layui-icon">&#xe848;</i>-->
<#--                                </div>-->
<#--                                <div class="layui-btn layui-btn-sm layui-btn-primary table-action" id="reset">-->
<#--                                    <i class="layui-icon">&#xe79b;</i>-->
<#--                                </div>-->
<#--                                <div class="layui-btn layui-btn-sm layui-btn-primary table-action action-more"-->
<#--                                     shiro:hasAnyPermissions="dept:add,dept:delete,dept:export">-->
<#--                                    <i class="layui-icon">&#xe875;</i>-->
<#--                                </div>-->
<#--                            </div>-->
                        </div>
                    </form>
                    <div class="dept-tree" lay-filter="deptTree" style="margin-left: 1rem"></div>
                </div>
            </div>
        </div>
        <div class="layui-col-md6 layui-col-sm6 layui-col-xs12">
            <div class="layui-card">
                <div class="layui-card-header" id="form-header">新增部门</div>
                <div class="layui-card-body febs-table-full">
                    <form class="layui-form layui-table-form" action="" lay-filter="dept-form">
                        <div class="layui-form-item febs-hide" style="display: none">
                            <label class="layui-form-label febs-form-item-require">ID：</label>
                            <div class="layui-input-block">
                                <input type="text" name="departmentId" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">上级ID：</label>
                            <div class="layui-input-block">
                                <input type="text" value="" name="parentId" readonly autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label febs-form-item-require">部门编号：</label>
                            <div class="layui-input-block">
                                <input type="text" name="departmentCode" autocomplete="off" class="layui-input"
                                       minlength="2" maxlength="10" lay-verify="range">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label febs-form-item-require">部门名称：</label>
                            <div class="layui-input-block">
                                <input type="text" name="departmentName" autocomplete="off" class="layui-input"
                                       minlength="2" maxlength="10" lay-verify="range">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">部门排序：</label>
                            <div class="layui-input-block">
                                <input type="text" name="sort" autocomplete="off" class="layui-input" lay-verify="number">
                            </div>
                        </div>
                        <div style="display: none">
                        <button type="reset" class="layui-btn febs-hide" id="reset-form"></button>
                        <button class="layui-btn febs-hide" lay-submit="" lay-filter="dept-form-submit"
                                id="submit-form"></button>
                        </div>
                    </form>
                </div>
                <div class="layui-card-footer">
                    <button class="layui-btn layui-btn-normal" id="submit">保存</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script data-th-inline="javascript" type="text/javascript">
    layui.use(['dropdown', 'jquery',  'admin', 'form', 'eleTree'], function () {
        var $ = layui.jquery,
            admin = layui.admin,
            form = layui.form,
            validate = layui.validate,
            eleTree = layui.eleTree,
            dropdown = layui.dropdown,
            $view = $('#febs-dept'),
            $query = $view.find('#query'),
            $reset = $view.find('#reset'),
            $submit = $view.find('#submit'),
            $header = $view.find('#form-header'),
            $searchForm = $view.find('#dept-table-form'),
            $deptName = $searchForm.find('input[name="deptName"]'),
            add = $("#add"),
            del = $("#del"),
            _currentDeptData,
            _deptTree;

        form.render();

        renderDeptTree();

        //新增
        add.on('click', function () {
            reset();
            var selected = _deptTree.getChecked(false, true);
            if (selected.length > 1) {
                admin.alert.warn('只能选择一个节点作为父级！');
                return;
            }
            form.val("dept-form", {
                "parentId": selected[0] ? selected[0].id : ''
            });
        });

        //删除菜单
        del.on('click', function () {
            var checked = _deptTree.getChecked(false, true);
            if (checked.length < 1) {
                admin.alert.warn('请勾选需要删除的部门');
                return;
            }
            var deptIds = [];
            layui.each(checked, function (key, item) {
                deptIds.push(item.id)
            });
            admin.modal.confirm('提示', '当您点击确定按钮后，这些记录将会被彻底删除，如果其包含子记录，也将一并删除！', function () {
                admin.get('${basePath}/system/department/delete?departmentIds=' + deptIds.join(','), null, function () {
                    admin.alert.success('删除成功！');
                    reloadDeptTree();
                    reset();
                })
            });
        });

        //修改部门
        eleTree.on("nodeClick(deptTree)", function (d) {
            $header.text('修改部门');
            var data = d.data.currentData.data;
            _currentDeptData = data;
            form.val("dept-form", {
                "departmentCode": data.departmentCode,
                "departmentName": data.departmentName,
                "sort": data.sort,
                "parentId": data.parentId,
                "departmentId": data.id
            });
        });


        $view.on('click', '#submit', function () {
            $view.find('#submit-form').trigger('click');
        });

        $reset.on('click', function () {
            $deptName.val('');
            reloadDeptTree();
            reset();
        });

        $query.on('click', function () {
            reloadDeptTree();
        });

        function getQueryParams() {
            return {
                "deptName": $deptName.val().trim()
            }
        }

        function reset() {
            $header.text('新增部门');
            $view.find('#reset-form').trigger('click');
        }

        function renderDeptTree() {
            _deptTree = eleTree.render({
                elem: '.dept-tree',
                url: '${basePath}/system/department/tree',
                accordion: true,
                highlightCurrent: true,
                showCheckbox: true,
                checkStrictly: true,
                renderAfterExpand: false,
                defaultExpandAll: true,
                where: {
                    // "deptName": $deptName.val().trim(),
                    "invalidate_ie_cache": new Date().getTime()
                },
                request: {
                    name: 'name',
                    key: "id",
                    checked: "checked",
                    data: 'data'
                },
                response: {
                    statusName: "code",
                    statusCode: 0,
                    dataName: "data"
                }
            });
            return _deptTree;
        }

        function reloadDeptTree() {
            _deptTree = renderDeptTree();
        }

        form.on('submit(dept-form-submit)', function (data) {
            admin.post('${basePath}/system/department/update', data.field, function () {
                admin.alert.success('修改成功');
                reloadDeptTree();
                reset();
            })
            return false;
        });
    });
</script>