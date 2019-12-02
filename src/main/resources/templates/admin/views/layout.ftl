<div class="layui-layout layui-layout-admin">
    <div class="layui-header" id="app-header">
        <script
                type="text/html"
                template
                lay-done="layui.element.render('nav','admin-header')"
        >
            <ul class="layui-nav layui-layout-left" lay-filter="admin-header">
                <li class="layui-nav-item" lay-unselect>
                    <a admin-event="flexible" title="侧边伸缩">
                        <i class="layui-icon layui-icon-shrink-right"></i>
                    </a>
                </li>
                <!--
                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <a admin-event="prev" title="返回上一页">
                        <i class="layui-icon layui-icon-prev"></i>
                    </a>
                </li>
                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <a admin-event="refresh" title="刷新">
                        <i class="layui-icon layui-icon-refresh-3"></i>
                    </a>
                </li>
                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <a href="https://www.layui.com/" target="_blank" title="Layui">
                        <i class="layui-icon layui-icon-website"></i>
                    </a>
                </li>
                -->
                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <input type="text" placeholder="Search / 搜索 ..." autocomplete="off" class="layui-input layui-search-input" admin-event="serach">
                </li>
            </ul>
            <ul class="layui-nav layui-layout-right" lay-filter="admin-header">

                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <a admin-event="fullscreen" title="切换全屏">
                        <i class="layui-icon layui-icon-screen-full"></i>
                    </a>
                </li>
                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <a title="APP 下载" lay-popup="{url:'popup/phone','offset':'rt',area:['400px','100%'],anim:5}">
                        <i class="layui-icon layui-icon-cellphone"></i>
                    </a>
                </li>
                <li class="layui-nav-item" lay-unselect>
                    <a lay-href="app/message/" admin-event="message" title="消息提醒">
                        <i class="layui-icon layui-icon-notice"></i>
                        <span class="layui-badge-dot message-dot"></span>
                    </a>
                </li>
                <li class="layui-nav-item" lay-unselect>
                    <a class="layui-hide-xs">
                        欢迎使用，${user.realName}
                        <i class="layui-icon layui-icon-triangle-d"></i>
                    </a>
                    <a class="layui-hide-sm layui-show-xs-inline-block">
                        <i class="layui-icon layui-icon-username"></i>
                    </a>
                    <dl class="layui-nav-child">
                        <dd><a>个人信息</a></dd>
                        <dd><a>认证信息</a></dd>
                        <hr/>
                        <dd><a href="/logout" admin-event="logout">退出登录</a></dd>
                    </dl>
                </li>
            </ul>
        </script>
    </div>

    <div class="admin-tabs-hidden">
        <script type="text/html" id="TPL-app-tabsmenu" template>
            {{# if(layui.conf.viewTabs == true){ }}
            <div class="admin-tabs-wrap">
          <span class="admin-tabs-btn admin-tabs-action admin-tabs-prev" data-type="prev">
              <i class="layui-icon layui-icon-left"></i>
          </span>
                <span class="admin-tabs-btn admin-tabs-action admin-tabs-next" data-type="next">
              <i class="layui-icon layui-icon-right"></i>
          </span>
                <span class="admin-tabs-btn admin-tabs-action admin-tabs-down" data-type="down">
              <i class="layui-icon layui-icon-close"></i>
          </span>
                <ul class="admin-tabs-menu">
                    {{# layui.each(layui.view.tab.data,function(i,item){ }}<li data-type="page" class="admin-tabs-btn" lay-url="{{item.fileurl}}"><i class="admin-tabs-ball"></i>{{ item.title }}<b class="layui-icon layui-icon-close admin-tabs-close"></b></li>{{# }) }}
                </ul>
            </div>
            {{# } }}
        </script>
    </div>

    <!-- 侧边菜单 -->
    <div class="layui-side" id="app-sidebar">
        <div class="layui-side-scroll">
            <div class="layui-logo">管理系统</div>
            <script
                    type="text/html"
                    template
                    lay-api="getMenu"
                    lay-done="layui.element.render('nav','admin-sidebar');layui.admin.sidebarFocus()"
            >
                <ul class="layui-nav layui-nav-tree" lay-filter="admin-sidebar" >
                    {{#
                    function __createSlidebar(data,index){
                    if(!data || data.length == 0) return '';
                    var html = '<dl class="layui-nav-child">';
                        layui.each(data,function(i,child){
                        html += '<dd><a style="padding-left:'+(index == 0 ? 50 : 50 + index * 20)+'px" target="'+(child.target||'')+'" lay-href="'+ (child.href||'') +'">' + child.title + '</a>';
                            if(child.childs) html += __createSlidebar(child.childs,index+1);
                            html += '</dd>';
                        });
                        html = html +'</dl>';
                    return html;
                    }
                    layui.each(d,function(i,child){
                    }}
                    <li class="layui-nav-item">
                        <a lay-href="{{child.href||''}}" target="{{child.target||''}}" title="{{child.title}}"><i class="layui-icon {{child.icon}}"></i>{{child.title}} {{# if(child.notice){ }}<span class="layui-badge">{{ child.notice }}</span>{{# } }}</a>
                        {{ __createSlidebar(child.childs,0) }}
                    </li>
                    {{# }) }}
                </ul>
            </script>
        </div>
    </div>

    <!-- 主体内容 -->
    <div class="layui-body" id="app-body"></div>
</div>
