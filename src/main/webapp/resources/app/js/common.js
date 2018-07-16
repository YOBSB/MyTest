!(function () {
  // 获取url参数
  function queryURLParam(key) {
    var val = location.search.match(
      new RegExp('[?&]' + key + '=([^&]*)(&?)', 'i')
    );
    return val ? val[1] : val;
  }

  // 登录逻辑
  function ULogin() {
    this.unLoginEle = $('#unlogin');
    this.loginedEle = $('#logined');

    this.dialogEle = null;

    this.init();
  }

  ULogin.prototype = {
    init: function () {
      var loginedUserInfo = this.loginedInfo();
      if (loginedUserInfo) {
        if (loginedUserInfo.auth) {
          this.logined(loginedUserInfo.nickname + '(' + loginedUserInfo.Uin + ')');
        } else {
          this.logined(loginedUserInfo.Uin);
        }
      } else {
        this.unLoginEle.removeClass('hide');
        this.showDialog();
      }
      this.bindLogin();
      this.bindLogout();
    },
    /**
     * 检测登录状态，并返回登录信息
     */
    loginedInfo: function () {
      if (this.isFromClient()) {
        var _info = {
          auth: queryURLParam('auth'),
          s2t: queryURLParam('s2t'),
          Uin: queryURLParam('uin'),
          nickname: decodeURIComponent(queryURLParam('nickname'))
        }

        if (_info.auth) {
          sessionStorage.setItem('loginedUserInfo', JSON.stringify(_info))
          return _info;
        } else {
          return false;
        }
      } else {
        return JSON.parse(sessionStorage.getItem('loginedUserInfo'));
      }
    },
    /**
     * 判断是否来自游戏客户端
     */
    isFromClient: function () {
      return !!queryURLParam('fromClient');
    },
    /**
     * 已登录的逻辑
     */
    logined: function (text) {
      this.unLoginEle.addClass('hide');
      this.loginedEle
        .removeClass('hide')
        .find('.uname')
        .text(text);
    },
    /**
     * 隐藏登录层
     */
    hideDialog: function () {
      this.dialogEle.addClass('hide');
      $('html,body').removeClass('hide-scroll');
    },
    /**
     * 创建登录层dom结构
     */
    createDialog: function () {
      var dialogHtml =
        '<div class="login-dialog" id="loginDialog">' +
        '<div class="mask"></div>' +
        '<div class="con">' +
        '<div class="hd">用户登录<a href="javascript:;" class="close"></a></div>' +
        '<div class="bd">' +
        '<div class="login-types">' +
        '<a href="javascript:;" data-type="pwd" class="active">账号登录</a>' +
        '<a href="javascript:;" data-type="code">验证码登录</a>' +
        '</div>' +
        '<div class="login-pwd-box login-box">' +
        '<form class="login-form" novalidate="novalidate" id="loginPwdForm">' +
        '<div class="login-form-item"><label>迷你号：</label><div class="login-form-item-con"><input type="number" id="uin" name="uin" required autocomplete="off"></div></div>' +
        '<div class="login-form-item"><label>密码：</label><div class="login-form-item-con"><input type="password" id="upwd" name="passwd" required autocomplete="off"></div></div>' +
        '<div class="login-form-item"><div class="login-form-item__button"><input type="submit" value="登录" id="loginPwdSubmitBtn"></div></div>' +
        '</form>' +
        '</div>' +
        '<div class="login-code-box login-box hide">' +
        '<form class="login-form" novalidate="novalidate" id="loginCodeForm">' +
        '<div class="login-form-item"><label>迷你号：</label><div class="login-form-item-con"><input type="number" id="cuin" name="uin" required autocomplete="off"></div></div>' +
        '<div class="login-form-item"><label>验证码：</label><div class="login-form-item-con login-form-item-con__short"><input type="number" id="code" name="code" required autocomplete="off"></div><div class="login-form-item-con__button"><button type="button" id="sendCodeBtn">发送验证码</button></div></div>' +
        '<div class="login-form-item"><div class="login-form-item__button"><input type="submit" value="登录" id="loginCodeSubmitBtn"></div></div>' +
        '</form>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>';

      this.dialogEle = $(dialogHtml);

      this.dialogEle.appendTo('body');
    },
    /**
     * 显示登录层
     */
    showDialog: function () {
      $('html,body').addClass('hide-scroll');
      if (this.dialogEle) {
        this.dialogEle.removeClass('hide');
      } else {
        this.createDialog();
        this.bindClose();
        this.bindTab();
        this.validate();
        this.sendCode();
      }
    },
    /**
     * 退出登录
     */
    bindLogout: function () {
      var _this = this;
      this.loginedEle.on('click', 'a.logout', function(){
        _this.unLoginEle.removeClass('hide');
        _this.loginedEle.addClass('hide');
        sessionStorage.removeItem('loginedUserInfo');

        // location.href = '/';
      });
    },
    /**
     * 隐藏弹层
     */
    bindClose: function () {
      var _this = this;
      this.dialogEle.on('click', '.close', function () {
        _this.hideDialog();
      });
    },
    /**
     * 页面元素绑定事件
     */
    bindLogin: function () {
      var _this = this;
      this.unLoginEle.on('click', 'a.login', function () {
        _this.showDialog();
      });
    },
    /**
     * tab切换绑定
     */
    bindTab: function () {
      var _this = this;
      var loginBox = this.dialogEle.find('.login-box');
      this.dialogEle.find('.login-types').on('click', 'a', function () {
        $(this)
          .addClass('active')
          .siblings('a')
          .removeClass('active');
        loginBox
          .eq($(this).index())
          .removeClass('hide')
          .siblings('.login-box')
          .addClass('hide');
      });
    },
    /**
     * 发送验证码倒计时
     */
    countDown: function (count) {
      var timer = null;
      var sendCodeBtn = $('#sendCodeBtn');
      count = count || 60;

      sendCodeBtn.prop('disabled', true).text(count);

      timer = setInterval(function () {
        count--;

        if (!count) {
          clearTimeout(timer);
          sendCodeBtn.prop('disabled', false).text('发送验证码');
          timer = null;
        } else {
          sendCodeBtn.text(count);
        }
      }, 1000);
    },
    /**
     * 发送验证码
     */
    sendCode: function () {
      var _this = this;
      $('#sendCodeBtn').on('click', function () {
        var cuinVal = $('#cuin').val();
        if (cuinVal) {
          $.ajax({
              type: 'GET',
              url: 'http://10.0.0.95/open/auth/getCode',
              data: {
                uin: cuinVal,
                title: '筑梦建造大赛登录验证码'
              },
              dataType: 'json'
            })
            .done(function (res) {
              switch (res.code) {
                case 'SUCCESS':
                  $.lightTip.success('验证码已发送到你的游戏邮箱，请查收');
                  break;
                case '401':
                  $.lightTip.error('迷你号不存在或格式错误');
                  break;
                case 'FAIL':
                  $.lightTip.error('发送验证码失败，请稍候再试');
                  break;
              }
            })
            .fail(function (err) {
              $.lightTip.error('发送验证码服务出错');
            })
            .always(function (res) {
              _this.countDown(60)
            });
        } else {
          $.lightTip.error('请输入迷你号', 2000);
        }
      });
    },
    /**
     * 表单验证与提交
     */
    validate: function () {
      var _this = this;
      var loginPwdForm = $('#loginPwdForm');
      var loginPwdSubmitBtn = $('#loginPwdSubmitBtn');
      new Validate(
        loginPwdForm,
        function () {
          // 提交表单
          loginPwdSubmitBtn.prop('disabled', true).val('正在登录...');
          $.ajax({
              type: 'GET',
              url: 'http://10.0.0.95/login/auth',
              data: {
                apiid: 999 || 110,
                passwd: $.trim(loginPwdForm.find('[name="passwd"]').val()),
                uin: $.trim(loginPwdForm.find('[name="uin"]').val())
              },
              dataType: 'json'
            })
            .done(function (res) {
              if (res.code === 'OK') {
                _this.hideDialog();
                loginPwdForm[0].reset();
                _this.logined(res.authinfo.Uin);

                // 存储sign
                sessionStorage.setItem('loginedUserInfo', JSON.stringify(res.authinfo));
              } else {
                $.lightTip.error('迷你号或密码错误', 2000);
              }
            })
            .fail(function (err) {
              $.lightTip.error('登录服务出错', 2000);
            })
            .always(function (res) {
              loginPwdSubmitBtn.prop('disabled', false).val('登录');
            });
        }, {
          validate: [{
              id: 'uin',
              prompt: {
                ignore: '请输入迷你号'
              }
            },
            {
              id: 'upwd',
              prompt: {
                ignore: '请输入密码'
              }
            }
          ]
        }
      );

      var loginCodeForm = $('#loginCodeForm');
      var loginCodeSubmitBtn = $('#loginCodeSubmitBtn');
      new Validate(
        loginCodeForm,
        function () {
          // 提交表单
          loginCodeSubmitBtn.prop('disabled', true).val('正在登录...');
          $.ajax({
              type: 'GET',
              url: 'http://10.0.0.95/open/auth/authCode',
              data: {
                uin: $.trim(loginCodeForm.find('[name="uin"]').val()),
                code: $.trim(loginCodeForm.find('[name="code"]').val())
              },
              dataType: 'json'
            })
            .done(function (res) {
              switch (res.code) {
                case 'SUCCESS':
                  _this.hideDialog();
                  loginCodeForm[0].reset();
                  _this.logined(res.authinfo.Uin);

                  // 存储sign
                  sessionStorage.setItem('loginedUserInfo', JSON.stringify(res.authinfo));
                  break;
                case '401':
                  $.lightTip.error('迷你号或验证码格式错误');
                  break;
                case 'FAIL':
                  $.lightTip.error(res.msg);
                  break;
              }
            })
            .fail(function (err) {
              $.lightTip.error('登录服务出错', 2000);
            })
            .always(function (res) {
              loginCodeSubmitBtn.prop('disabled', false).val('登录');
            });
        }, {
          validate: [{
              id: 'cuin',
              prompt: {
                ignore: '请输入迷你号'
              }
            },
            {
              id: 'code',
              prompt: {
                ignore: '请输入验证码'
              }
            }
          ]
        }
      );
    }
  };

  var uLogin = new ULogin();
})();