layui
  .extend({
    conf: 'config',
    api: 'lay/modules/api',
    view: 'lay/modules/view'
  })
  .define(['conf', 'view', 'api', 'jquery'], function(exports) {
    POPUP_DATA = {}
    var conf = layui.conf
    var view = layui.view
    var element = layui.element
    var $ = layui.jquery

    layui.extend(conf.extend)
    var self = {}
    var windowWidth = $(window).width()

    self.route = layui.router()
    self.api = layui.api
    self.closeOnceHashChange = false
    self.ie8 = view.ie8
    self.get = view.request
    self.appBody = null
    self.shrinkCls = 'admin-sidebar-shrink'
    self.isInit = false
    self.routeLeaveFunc = null
    self.loginToken = null
    self.routeLeave = function(callback) {
      this.routeLeaveFunc = callback
    }
    self.render = function(elem) {
      if (typeof elem == 'string') elem = $('#' + elem)
      var action = elem.get(0).tagName == 'SCRIPT' ? 'next' : 'find'
      elem[action]('[is-template]').remove()
      view.parse(elem)
    }
    self.getLoginToken = function() {
      if (self.loginToken == null) {
        self.loginToken =
          self.data()[conf.tokenName] ||
          layui.sessionData(conf.tableName, conf.tokenName)
      }
      return self.loginToken
    }
    self.logout = function() {
      self.data({ key: conf.tokenName, remove: true })
      self.data({ key: conf.tokenName, remove: true }, sessionStorage)
      self.loginToken = null

      self.navigate(conf.loginPage)
    }
    self.login = function(token, data, isSession) {
      self.data(
        {
          key: conf.tokenName,
          value: token
        },
        isSession && sessionStorage
      )

      if ($.isPlainObject(data)) {
        var disableData = []
        layui.each(data, function(key) {
          disableData.push({
            key: key,
            value: data[key]
          })
        })
        self.data(disableData)
      }
    }
    //初始化整个页面
    self.initPage = function(initCallback) {
      //加载样式文件
      layui.each(layui.conf.style, function(index, url) {
        layui.link(url + '?v=' + conf.v)
      })
      self.initView(self.route)
    }
    self.post = function(params) {
      view.request($.extend({ type: 'post' }, params))
    }

    /**
    self.router = function(url) {
      var route
      if (url) {
        url = '#' + url
        if (url == '#/') url += conf.entry
        console.log(url)
      } else {
        route = layui.router()
      }
      route.fileurl = '/' + route.path.join('/')
      return route
    }
     */
    //初始化视图区域
    self.initView = function(route) {
      if (!self.route.href || self.route.href == '/') {
        self.route = layui.router('#' + conf.entry)
        route = self.route
      }
      route.fileurl = '/' + route.path.join('/')
      //判断登录页面
      if (conf.loginCheck == true) {
        if (self.getLoginToken()) {
          if (route.fileurl == conf.loginPage) {
            self.navigate('/')
            return
          }
        } else {
          if (route.fileurl != conf.loginPage) {
            self.logout()
            return
          }
        }
      }

      if ($.inArray(route.fileurl, conf.indPage) === -1) {
        var loadRenderPage = function(params) {
          if (conf.viewTabs == true) {
            view.renderTabs(route)
          } else {
            view.render(route.fileurl)
          }
        }

        if (view.containerBody == null) {
          //加载layout文件
          view.renderLayout(function() {
            //重新渲染导航
            element.render('nav', 'admin-sidebar')
            //加载视图文件
            loadRenderPage()
          })
        } else {
          //layout文件已加载，加载视图文件
          loadRenderPage()
        }
      } else {
        //加载单页面
        view.renderIndPage(route.fileurl, function() {
          if (conf.viewTabs == true) view.tab.clear()
        })
      }
    }
    //根据当前加载的URL高亮左侧导航
    self.sidebarFocus = function(url) {
      url = url || self.route.href
      var elem = $('#app-sidebar')
        .find('[lay-href="' + url + '"]')
        .eq(0)
      if (elem.length > 0) {
        elem.parents('.layui-nav-item').addClass('layui-nav-itemed')
        elem.click()
      }
    }
    self.flexible = function(open) {
      if (open == true) {
        view.container.removeClass(self.shrinkCls)
      } else {
        view.container.addClass(self.shrinkCls)
      }
    }
    self.on = function(name, callback) {
      return layui.onevent(conf.eventName, 'system(' + name + ')', callback)
    }
    self.event = function(name, params) {
      layui.event(conf.eventName, 'system(' + name + ')', params)
    }
    self.csshref = function(name) {
      name = name == undefined ? self.route.path.join('/') : name
      return conf.css + 'views/' + name + '.css' + '?v=' + conf.v
    }
    self.prev = function(n) {
      if (n == undefined) n = -1
      window.history.go(n)
    }
    self.navigate = function(url) {
      if (url == conf.entry) url = '/'
      window.location.hash = url
    }
    self.data = function(settings, storage) {
      if (settings == undefined) return layui.data(conf.tableName)
      if ($.isArray(settings)) {
        layui.each(settings, function(i) {
          layui.data(conf.tableName, settings[i], storage)
        })
      } else {
        layui.data(conf.tableName, settings, storage)
      }
    }

    self.modal = {}

    self.modal.base = function (msg, params) {
      params = params || {};
      params.titleIcoColor = params.titleIcoColor || '#5a8bff';
      params.titleIco = params.titleIco || 'exclaimination';
      params.title = params.title || [
        '<i class="layui-icon layui-icon-' +
        params.titleIco +
        '" style="font-size:12px;background:' +
        params.titleIcoColor +
        ';display:inline-block;position:relative;top:-2px;height:21px;line-height:21px;text-align:center;width:21px;color:#fff;border-radius:50%;margin-right:12px;"></i>' +
        params.titleValue,
        'background:#fff;border:none;font-weight:bold;font-size:16px;color:#08132b;padding-top:10px;height:36px;line-height:46px;padding-bottom:0;'
      ];
      params = $.extend(
          {
            skin: 'layui-layer-admin-modal febs-alert',
            area: [windowWidth <= 750 ? '60%' : '400px'],
            closeBtn: 0,
            shadeClose: false
          },
          params
      );
      layer.alert(msg, params);
    };

    // ----------------- 弹窗类 --------------------- //

    self.alert = {};

    function alertParams(msg, params) {
      params.time = 3000;
      params.shade = 0;
      params.btn = null;
      params.title = [
        '<i class="layui-icon layui-icon-' +
        params.titleIco +
        '" style="font-size:12px;background:' +
        params.titleIcoColor +
        ';display:inline-block;font-weight:600;position:relative;top:-2px;height:21px;line-height:21px;text-align:center;width:21px;color:#fff;border-radius:50%;margin-right:12px;"></i>' +
        (msg || '请填写提示信息'),
        'background:#fff;border:none;font-weight:500;font-size:14px;color:#08132b;margin-bottom:-50px;padding:16px;height:45px;line-height:14px;padding-bottom:0;'
      ];
      params.offset = '40px';
      params.area = [windowWidth <= 750 ? '80%' : '400px'];
    }

    self.alert.success = function (msg, params) {
      params = params || {};
      params.titleIco = 'ok';
      params.titleIcoColor = '#30d180';
      alertParams(msg, params);
      self.modal.base('', params);
    };
    self.alert.warn = function (msg, params) {
      params = params || {};
      params.titleIco = 'exclaimination';
      params.titleIcoColor = '#ffc107';
      alertParams(msg, params);
      self.modal.base('', params);
    };
    self.alert.error = function (msg, params) {
      params = params || {};
      params.titleIco = 'close';
      params.titleIcoColor = '#ff5652';
      alertParams(msg, params);
      self.modal.base('', params);
    };
    self.alert.info = function (msg, params) {
      params = params || {};
      params.titleIco = 'infomation';
      params.titleIcoColor = '#2db7f5';
      alertParams(msg, params);
      self.modal.base('', params);
    };

    // ----------------- 模态框类 --------------------- //

    self.modal.confirm = function (title, msg, yes, no, params) {
      params = params || {};
      params.titleIco = 'exclaimination';
      params.titleIcoColor = '#ffc107';
      params.titleValue = title;
      params.shadeClose = false;
      params = $.extend({
        btn: ['确定', '取消']
        , yes: function (index, layero) {
          yes && (yes)();
          layer.close(index);
        }
        , btn2: function (index, layero) {
          no && (no)();
        }
      }, params);
      self.modal.base(msg, params);
    };

    self.modal.info = function (title, msg, yes, params) {
      params = params || {};
      params.titleIco = 'infomation';
      params.titleIcoColor = '#2db7f5';
      params.titleValue = title;
      params.shadeClose = false;
      params = $.extend({
        btn: ['确定']
        , yes: function (index, layero) {
          yes && (yes)();
          layer.close(index);
        }
      }, params);
      self.modal.base(msg, params);
    };

    self.modal.warn = function (title, msg, yes, params) {
      params = params || {};
      params.titleIco = 'exclaimination';
      params.titleIcoColor = '#ffc107';
      params.titleValue = title;
      params.shadeClose = false;
      params = $.extend({
        btn: ['确定']
        , yes: function (index, layero) {
          yes && (yes)();
          layer.close(index);
        }
      }, params);
      self.modal.base(msg, params);
    };

    self.modal.success = function (title, msg, yes, params) {
      params = params || {};
      params.titleIco = 'ok';
      params.titleIcoColor = '#30d180';
      params.titleValue = title;
      params.shadeClose = false;
      params = $.extend({
        btn: ['确定']
        , yes: function (index, layero) {
          yes && (yes)();
          layer.close(index);
        }
      }, params);
      self.modal.base(msg, params);
    };

    self.modal.error = function (title, msg, yes, params) {
      params = params || {};
      params.titleIco = 'close';
      params.titleIcoColor = '#ff5652';
      params.titleValue = title;
      params.shadeClose = false;
      params = $.extend({
        btn: ['确定']
        , yes: function (index, layero) {
          yes && (yes)();
          layer.close(index);
        }
      }, params);
      self.modal.base(msg, params);
    };

    self.modal.open = function (title, url, params) {
      params = $.extend({
        url: url,
        maxmin: false,
        shadeClose: false,
        title: [
          (title || '请填写头部信息'),
          'font-size:16px;color:#08132b;line-height:46px;padding-bottom:0;border-bottom:1px solid #fcfcfc;background-color:#fcfcfc'
        ]
      }, params);
      self.popup(params);
    };

    self.modal.view = function (title, url, params) {
      params = $.extend({
        url: url,
        maxmin: false,
        shadeClose: false,
        title: [
          title,
          'font-size:15px;color:#08132b;line-height:46px;height:48px;padding-bottom:0;background-color:#fff;border-bottom:none'
        ],
        area: $(window).width() <= 750 ? ['80%', '80%'] : ['50%', '60%']
      }, params);
      self.popup(params);
    };

    self.form2json = function(formId){
      var paramArray = $('#' + formId).serializeArray();
      var jsonObj={};
      $(paramArray).each(function(){
        jsonObj[this.name]=this.value;
      });
      return jsonObj;
    }

    self.isUrl = function(str) {
      return /^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/.test(
        str
      )
    }
    self.popup = function(params) {
      var url = params.url || ''
      var success = params.success || function() {}
      params.skin = 'layui-layer-admin-page'
      POPUP_DATA = params.data || {}
      var defaultParams = {
        type: 1,
        area: ['90%', '90%'], //宽高
        shadeClose: true
      }
      // alert(url);

      if (self.isUrl(url)) {
        params.type = 2
        params.content = url
        layer.open($.extend(defaultParams, params))
        return
      }



      view.tab.del(url)

      view.loadHtml(conf.views + url, function(res) {
        var htmlElem = $('<div>' + res.html + '</div>')

        if (params.title === undefined) {
          params.title = htmlElem.find('title').text() || '信息'
          if (params.title) htmlElem.find('title').remove()
        }

        params.content = htmlElem.html()
        params.success = function(layer, index) {
          success(layer, index)

          view.parse(layer)
          //重新对面包屑进行渲染
          element.render('breadcrumb', 'admin-breadcrumb')
        }

        params = $.extend(defaultParams, params)
        layer.open($.extend(defaultParams, params))
      })
    }


    // ajax get请求
    self.get = function (url, params, success) {
      if (params) {
        params.invalidate_ie_cache = new Date();
      }
      $.get(url, params, function (r) {
        resolveResponse(r, success);
      })
    };

    // ajax post请求
    self.post = function (url, params, success) {
      if (params) {
        params.invalidate_ie_cache = new Date().getTime();
      }
      $.post(url, params, function (r) {
        resolveResponse(r, success);
      })
    };

    function resolveResponse(r, f) {
      if (r.code === 0) {
        f(r) && (f)();
      } else if (r.code === 401) {
        self.modal.info('登录失效', '登录已失效，请重新登录', function () {
          window.location.href = ctx + 'login';
        });
      } else if (r.code === 403) {
        self.alert.warn('对不起，您暂无该操作权限');
      } else {
        self.alert.error(r.message ? r.message : '操作失败');
        console.error(r);
      }
    }

    //当小于这个尺寸的时候会进行手机端的适配
    var mobileWidth = 991
    var isMobileAdapter = false
    function mobileAdapter() {
      self.flexible(false)
      var device = layui.device()
      if (device.weixin || device.android || device.ios) {
        //点击空白处关闭侧边栏
        $(document).on('click', '#' + conf.containerBody, function() {
          if (
            $(window).width() < mobileWidth &&
            !view.container.hasClass(self.shrinkCls)
          ) {
            self.flexible(false)
          }
        })
      }
      isMobileAdapter = true
    }
    $(window).on('resize', function(e) {
      if ($(window).width() < mobileWidth) {
        if (isMobileAdapter == true) return
        mobileAdapter()
      } else {
        isMobileAdapter = false
      }
    })

    $(window).on('hashchange', function(e) {
      //移动端跳转链接先把导航关闭
      if ($(window).width() < mobileWidth) {
        self.flexible(false)
      }
      self.route = layui.router()
      layer.closeAll()
      self.initView(self.route)
    })

    //回车提交 form 表单
    $(document).on('keydown', function(e) {
      var ev = document.all ? window.event : e
      if (ev.keyCode == 13) {
        var form = $(':focus').parents('.layui-form')
        form.find('[lay-submit]').click()
      }
    })

    $(document).on('click', '[lay-href]', function(e) {
      var href = $(this).attr('lay-href')
      var target = $(this).attr('target')

      if (href == '') return
      if (self.isUrl(href)) {
        next()
      }

      function next() {
        target == '__blank' ? window.open(href) : self.navigate(href)
      }

      if ($.isFunction(self.routeLeaveFunc)) {
        self.routeLeaveFunc(self.route, href, next)
      } else {
        next()
      }

      return false
    })
    $(document).on('click', '[lay-popup]', function(e) {
      var params = $(this).attr('lay-popup')
      self.popup(
        params.indexOf('{') === 0
          ? new Function('return ' + $(this).attr('lay-popup'))()
          : { url: params }
      )
      return false
    })
    $(document).on('mouseenter', '[lay-tips]', function(e) {
      var title = $(this).attr('lay-tips')
      var dire = $(this).attr('lay-dire') || 3
      if (title) {
        layer.tips(title, $(this), {
          tips: [dire, '#263147']
        })
      }
    })
    $(document).on('mouseleave', '[lay-tips]', function(e) {
      layer.closeAll('tips')
    })

    $(document).on('click', '*[' + conf.eventName + ']', function(e) {
      self.event($(this).attr(conf.eventName), $(this))
    })

    var shrinkSidebarBtn =
      '.' + self.shrinkCls + ' #app-sidebar .layui-nav-item a'

    $(document).on('click', shrinkSidebarBtn, function(e) {
      if (isMobileAdapter == true) return
      var chileLength = $(this)
        .parent()
        .find('.layui-nav-child').length
      if (chileLength > 0) {
        self.flexible(true)
        layer.closeAll('tips')
      }
    })
    $(document).on('mouseenter', shrinkSidebarBtn, function(e) {
      var title = $(this).attr('title')
      if (title) {
        layer.tips(title, $(this).find('.layui-icon'), {
          tips: [2, '#263147']
        })
      }
    })
    $(document).on('mouseleave', shrinkSidebarBtn, function(e) {
      layer.closeAll('tips')
    })

    self.on('flexible', function(init) {
      var status = view.container.hasClass(self.shrinkCls)
      self.flexible(status)
      self.data({ key: 'flexible', value: status })
    })
    self.on('refresh', function(e) {
      var url = self.route.href
      if (conf.viewTabs == true) {
        //view.tab.refresh(url);
      } else {
        view.render(location.hash)
      }
    })
    self.on('prev', function(e) {
      self.prev()
    })

    self.on('logout', function(e) {
      self.logout()
    })

    self.on('fullscreen', function(e) {
      var normalCls = 'layui-icon-screen-full'
      var activeCls = 'layui-icon-screen-restore'
      var ico = e.find('.layui-icon')

      if (ico.hasClass(normalCls)) {
        var e = document.body
        e.webkitRequestFullScreen
          ? e.webkitRequestFullScreen()
          : e.mozRequestFullScreen
          ? e.mozRequestFullScreen()
          : e.requestFullScreen && e.requestFullscreen()
        ico.removeClass(normalCls).addClass(activeCls)
      } else {
        var e = document
        e.webkitCancelFullScreen
          ? e.webkitCancelFullScreen()
          : e.mozCancelFullScreen
          ? e.mozCancelFullScreen()
          : e.cancelFullScreen
          ? e.cancelFullScreen()
          : e.exitFullscreen && e.exitFullscreen()
        ico.removeClass(activeCls).addClass(normalCls)
      }
    })

    if ($(window).width() <= mobileWidth) {
      mobileAdapter()
    } else {
      var flexibleOpen = self.data().flexible
      self.flexible(flexibleOpen === undefined ? true : flexibleOpen)
    }

    exports('admin', self)
  })
