<div class="layui-fluid layui-anim febs-anim" id="febs-menu" lay-title="菜单管理">
    <div class="layui-row layui-col-space8 febs-container">
        <div class="layui-col-md6 layui-col-sm6 layui-col-xs12">
            <div class="layui-card">
                <div class="layui-card-body febs-table-full">
                    <form class="layui-form layui-table-form" lay-filter="menu-table-form" id="menu-table-form">
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
<#--                                <div class="layui-btn layui-btn-sm layui-btn-primary table-action action-more">-->
<#--                                    <i class="layui-icon">&#xe875;</i>-->
<#--                                </div>-->
<#--                            </div>-->
                        </div>
                    </form>
                    <div class="eleTree menuTree" lay-filter="menuTree" style="margin-left: 1rem"></div>
                </div>
            </div>
        </div>
        <div class="layui-col-md6 layui-col-sm6 layui-col-xs12">
            <div class="layui-card">
                <div class="layui-card-header" id="form-header">新增菜单</div>
                <div class="layui-card-body febs-table-full">
                    <form class="layui-form layui-table-form" action="" lay-filter="menu-form">
                        <div class="layui-form-item febs-hide" style="display: none">
                            <label class="layui-form-label febs-form-item-require">ID：</label>
                            <div class="layui-input-block">
                                <input type="text" name="menuId" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">上级ID：</label>
                            <div class="layui-input-block">
                                <input type="text" value="" name="parentId" readonly class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label febs-form-item-require">名称：</label>
                            <div class="layui-input-block">
                                <input type="text" name="menuName" autocomplete="off" class="layui-input"
                                       minlength="2" maxlength="10" lay-verify="range">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label febs-form-item-require">类型：</label>
                            <div class="layui-input-block">
                                <input type="radio" name="type" lay-filter="menu-type" value="1" title="菜单" checked="">
                                <input type="radio" name="type" lay-filter="menu-type" value="2" title="按钮">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">图标：</label>
                            <div class="layui-input-block">
                                <input type="text" name="icon" autocomplete="off" class="layui-input"
                                       maxlength="50" lay-verify="range">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">URL：</label>
                            <div class="layui-input-block">
                                <input type="text" name="url" autocomplete="off" class="layui-input"
                                       maxlength="50" lay-verify="range">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">权限：</label>
                            <div class="layui-input-block">
                                <input type="text" name="permission" autocomplete="off" class="layui-input"
                                       maxlength="50" lay-verify="range">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">排序：</label>
                            <div class="layui-input-block">
                                <input type="text" name="sort" autocomplete="off" class="layui-input" lay-verify="number">
                            </div>
                        </div>
                        <div style="display: none">
                        <button type="reset" class="layui-btn febs-hide" id="reset-form"></button>
                        <button class="layui-btn febs-hide" lay-submit="" lay-filter="menu-form-submit" id="submit-form"></button>
                        </div>
                    </form>
                </div>
                <div class="layui-card-footer">
                    <button class="layui-btn" id="submit">保存</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script data-th-inline="none" type="text/javascript">
    layui.use(['dropdown', 'jquery', 'laydate', 'admin', 'form', 'eleTree'], function () {
        var $ = layui.jquery,
            laydate = layui.laydate,
            admin = layui.admin,
            form = layui.form,
            eleTree = layui.eleTree,
            dropdown = layui.dropdown,
            $view = $('#febs-menu'),
            $query = $view.find('#query'),
            $reset = $view.find('#reset'),
            $submit = $view.find('#submit'),
            $searchForm = $view.find('#menu-table-form'),
            // $menuName = $searchForm.find('input[name="menuName"]'),
            $type = $view.find('input[type="radio"][name="type"]'),
            $icon = $view.find('input[name="icon"]'),
            $icon_parent = $icon.parents('.layui-form-item'),
            $url = $view.find('input[name="url"]'),
            $url_parent = $url.parents('.layui-form-item'),
            $order = $view.find('input[name="orderNum"]'),
            $order_parent = $order.parents('.layui-form-item'),
            $header = $view.find('#form-header'),
            add = $("#add"),
            del = $("#del"),
            _currentMenuData,
            _selectNode,
            _menuTree,
            tableIns;

        // form.verify(validate);
        form.render();

        //新增
        add.on('click', function () {
            reset();
            var selected = _menuTree.getChecked(false, true);
            if (selected.length > 1) {
                admin.alert.warn('只能选择一个节点作为父级！');
                return;
            }
            if (selected[0] && selected[0].type === 2) {
                admin.alert.warn('不能选择按钮作为父级！');
                return;
            }
            form.val("menu-form", {
                "parentId": selected[0] ? selected[0].id : ''
            });
        });

        //删除菜单
        del.on('click', function () {
            var checked = _menuTree.getChecked(false, true);
            if (checked.length < 1) {
                admin.alert.warn('请勾选需要删除的菜单或按钮');
                return;
            }
            var menuIds = [];
            layui.each(checked, function (key, item) {
                menuIds.push(item.id)
            });
            admin.modal.confirm('提示', '当您点击确定按钮后，这些记录将会被彻底删除，如果其包含子记录，也将一并删除！', function () {
                admin.get('${basePath}/system/menu/delete?menuId=' + menuIds.join(','), null, function () {
                    admin.alert.success('删除成功！');
                    reloadMenuTree();
                    reset();
                })
            });
        });



        _menuTree = renderMenuTree();

        //点击树形菜单进行修改
        eleTree.on("nodeClick(menuTree)", function (d) {
            var data = d.data.currentData.data;
            _currentMenuData = data;
            $type.attr("disabled", true);
            var type = data.type;
            // handleTypeChange(type);
            if (type === 1) { // 菜单
                $header.text('修改菜单');
            } else { // 按钮
                $header.text('修改按钮');
            }
            form.val("menu-form", {
                "icon": data.icon,
                "url": data.url,
                "sort": data.sort,
                "type": data.type,
                "menuName": data.menuName,
                "permission": data.permission,
                "parentId": data.parentId,
                "menuId": data.id
            });
        });

        form.on("radio(menu-type)", function (data) {
            handleTypeChange(data.value);
        });

        $reset.on('click', function () {
            reloadMenuTree();
            reset();
        });

        $query.on('click', function () {
            reloadMenuTree();
            reset();
        });

        $submit.on('click', function () {
            $view.find('#submit-form').trigger('click');
        });

        $icon.focus(function () {
            admin.modal.open('图标选择', 'system/menu/menuIcon', {
                btn: ['确定'],
                yes: function () {
                    var icon = $('#febs-icon').find('.icon-active .icon-name').text();
                    if (icon) {
                        form.val("menu-form", {
                            "icon": 'layui-icon-' + icon
                        });
                    } else {
                        form.val("menu-form", {
                            "icon": ''
                        });
                    }
                    layer.closeAll();
                }
            });
        });

        function reset() {
            $view.find('#reset-form').trigger('click');
            handleTypeChange(1);
            $type.removeAttr("disabled");
        }

        function renderMenuTree() {
            _menuTree = eleTree.render({
                elem: '.menuTree',
                url: '${basePath}system/menu/tree',
                where: {
                    "invalidate_ie_cache": new Date().getTime()
                },
                accordion: true,
                highlightCurrent: true,
                showCheckbox: true,
                checkStrictly: true,
                renderAfterExpand: true,
                defaultExpandAll: true,
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
                }
            });
            return _menuTree;
        }

        function reloadMenuTree() {
            _menuTree = renderMenuTree();
        }

        var handleTypeChange = function (type) {
            form.val("menu-form", {
                "icon": '',
                "url": '',
                "orderNum": ''
            });
            if (type === 1) {
                $header.text('新增菜单');
                $icon_parent.show();
                $url_parent.show();
                $order_parent.show();
            } else {
                $header.text('新增按钮');
                $icon_parent.hide();
                $url_parent.hide();
                $order_parent.hide();
            }
        };

        //保存按钮
        form.on('submit(menu-form-submit)', function (data) {
            admin.post('${basePath}system/menu/update', data.field, function () {
                admin.alert.success('操作成功');
                reloadMenuTree();
                reset();
            })
            return false;
        });
    });
</script>