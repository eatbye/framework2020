layui.define(function(exports) {
  exports('conf', {
    //容器ID
    container: 'app',
    //容器内容ID
    containerBody: 'app-body',
    //版本号
    v: layui.cache.version,
    //记录admin文件夹所在路径
    base: layui.cache.base,
    css: layui.cache.base + 'css/',
    //视图所在目录
    views: layui.cache.base + 'views/',
    //是否开启选项卡
    viewTabs: false,
    //显示页面加载条
    viewLoadBar: false,
    //公用加载的样式
    style: [
      //layui.cache.base + "css/admin.css"
    ],
    //是否开启调试模式，开启的话接口异常会抛出异常 URL信息
    debug: layui.cache.debug,
    //网站名称
    name: 'admin',
    //默认视图文件名
    entry: '/index',
    //视图文件后缀名
    engine: '',
    eventName: 'admin-event',
    //本地存储表名
    tableName: 'admin',
    //全局设置 headers 信息
    // requestHeaders: {
    //   'Test-User-Agent': 'os=pc;ver=0.0.1;imei=asdasdas'
    // },
    //request 基础URL
    requestUrl: './',
    //独立页面路由，可随意添加（无需写参数）

    //全局自定义响应字段
    response: {
      //数据状态的字段名称
      statusName: 'code',
      statusCode: {
        //数据状态一切正常的状态码
        ok: 0,
        //通过接口返回的登录过期状态码
        logout: 401
      },
      msgName: 'msg', //状态信息的字段名称
      dataName: 'data', //数据详情的字段名称
      countName: 'count' //数据条数的字段名称，用于 table
    },
    //全局 table 配置
    //参数请参照 https://www.layui.com/doc/modules/table.html
    table: {
      page: true,
      size: 'lg',
      skin: 'line',
      //每页显示的条数
      limit: 20,
      //是否显示加载条
      loading: true,
      //用于对分页请求的参数：page、limit重新设定名称
      request: {
        pageName: 'page', //页码的参数名称，默认：page
        limitName: 'size' //每页数据量的参数名，默认：limit
      }
    },
    //第三方扩展

    extend: {
      //后台根据业务需求扩展的方法
      helper: 'lay/extends/helper',
      //生成二维码
      qrcode: 'lay/extends/qrcode',
      //生成 MD5 加密
      md5: 'lay/extends/md5',
      //生成图表
      echarts: 'lay/extends/echarts',
      echartsTheme: 'lay/extends/echartsTheme',
      //复制内容到剪贴板
      clipboard: 'lay/extends/clipboard',
      eleTree: 'lay/extends/eleTree',
      treeSelect: 'lay/extends/treeSelect',
      formSelects: 'lay/extends/formSelects-v4.min',
    }
  })
})
