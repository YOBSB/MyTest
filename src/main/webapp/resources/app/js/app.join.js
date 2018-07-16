!(function () {
  // 扩展验证类型
  $.validate.reg = $.extend({}, $.validate.reg || {}, {
    code: '^\\d{6}$',
    phone: '^\\d+(?:\\-\\d+)*$',
    id: '^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|[Xx])$',
    phoneortel: '(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^1\\d{10}$)',
    uingroup: '^(\\d+\\,)*(\\d+)$'
  });

  $.validate.name = $.extend({}, $.validate.name || {}, {
    code: '短信验证码',
    phone: '电话',
    id: '身份证号码',
    phoneortel: '电话或手机号码',
    uingroup: '团队成员迷你号'
  });

  // 判断ie
  var isIE = function (ver) {
    var b = document.createElement('b')
    b.innerHTML = '<!--[if IE ' + ver + ']><i></i><![endif]-->'
    return b.getElementsByTagName('i').length === 1
  }

  // 图片延时加载初始化
  $(".lazy").lazyload({
    effect: "fadeIn"
  });

  // UI插件初始化
  new Placeholder();
  $('#mapList').selectMatch();

  // 用户登录信息
  var loginedUserInfo = JSON.parse(sessionStorage.getItem('loginedUserInfo'));

  function JoinForm() {
    this.uploadUrl = 'http://image-test.mini1.cn/api/image/uploadCommon'; //'http://image-test.mini1.cn/api/image/upload';

    this.workImages = {
      fullView: null,
      nearbyView1: null,
      nearbyView2: null,
      nearbyView3: null,
      fullViewThumb: null,
      nearbyView1Thumb: null,
      nearbyView2Thumb: null,
      nearbyView3Thumb: null,
    };

    this.init();
  }

  JoinForm.prototype = {
    init: function () {
      if (loginedUserInfo) {
        $('#joinUin').text(loginedUserInfo.Uin);
      }
      this.validate();
      this.initUpload();
      this.getMapList();

      this.deleteUploadImgs();
    },
    /**
     * 获取可参赛地图列表
     */
    getMapList: function () {
      var defaultData = {
        uin: loginedUserInfo.Uin,
        time: parseInt(new Date().getTime() / 1000)
      };

      var _data = null;

      if (loginedUserInfo.auth) {
        _data = $.extend({}, defaultData, {
          auth: loginedUserInfo.auth,
          s2t: loginedUserInfo.s2t
        });
      } else {
        _data = $.extend({}, defaultData, {
          sign: loginedUserInfo.sign
        });
      }

      $.ajax({
        type: 'GET',
        url: 'http://10.0.0.95/eventsys/submit/getMapList',
        data: _data,
        dataType: 'json'
      }).done(function (res) {
        if (!res.code) {
          if (res.data.length) {
            var _optionsHtml = '';

            for (var i = 0; i < res.data.length; i++) {
              var item = res.data[i];
              _optionsHtml += '<option value="' + item.mapId + '">' + item.mapName + '</option>'
            }

            // 插入页面
            $('#mapList').append(_optionsHtml);

            // 刷新select UI组件
            $('#mapList').selectMatch();
          } else {
            $.lightTip.error('你还没有可参赛的地图，赶紧进入迷你世界创建地图吧。');
          }
        } else {
          $.lightTip.error('获取可参赛地图列表失败，请刷新页面重试。');
        }
      }).fail(function (err) {
        $.lightTip.error('获取参赛地图列表服务出错');
      }).always(function (res) {
        //
      });
    },
    /**
     * 初始化上传事件
     */
    initUpload: function () {
      var _this = this;
      // 最终配置
      var uploadOptions = null;

      // 默认配置
      var defaultOptions = {
        url: this.uploadUrl,
        type: 'post',
        dataType: 'json',
        autoUpload: true,
        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
        formData: {
          platform: 'javasite',
          token: '891A6E81A88DA3C38D316956159E777C',
          thumb_width: 180,
          thumb_height: 120
        }
      };

      // 跨域上传图片配置
      var crossOptions = {
        forceIframeTransport: true,
        redirectParamName: "cb",
        redirect: 'http://' + window.location.host + '/pc/callupload.html', //回调页面
      };

      // 判断ie，如果是ie老版本则全部使用iframe模式上传图片，微博直接用这种方式
      if (isIE()) {
        uploadOptions = $.extend({}, defaultOptions, crossOptions);
      }

      // 给上传按钮绑定上传事件
      var uploadFields = ['fullView', 'nearbyView1', 'nearbyView2', 'nearbyView3'];

      $.each(uploadFields, function (idx, item) {
        $('input[id="' + item + '"]')
          .fileupload(uploadOptions || defaultOptions)
          .on('fileuploadadd', function (e, data) {
            console.log(data);
            // 判断大小
          })
          .on('fileuploaddone', function (e, data) {
            console.log(data.result);
            // 赋值图片地址
            // _this.workImages[item] = data.result.data.workImg.cdn_url;
            // _this.workImages[item + 'Thumb'] = data.result.data.workImg.cdn_thumb_url;
            $('input[name="'+item+'Field"]').val(data.result.data.workImg.cdn_url).trigger('change');
            $('input[name="'+item+'ThumbField"]').val(data.result.data.workImg.cdn_thumb_url);

            // console.log(_this.workImages);

            $('label[for="'+item+'"]')
              .addClass('hide')
              .siblings('.work-img-thumb')
              .removeClass('hide');
            $('#' + item + 'ThumbImg').attr('src', data.result.data.workImg.cdn_thumb_url);
          })
          .on('fileuploadfail', function (e, data) {
            console.log(data);
          })
      });
    },
    /**
     * 删除预览图片事件绑定
     */
    deleteUploadImgs: function () {
      var _this = this;
      $('#uploadWrap').on('click', 'a.delete-thumb-btn', function(){
        var target = $(this).data('target');
        // _this.workImages[target] = null;
        // _this.workImages[target+'Thumb'] = null;
        $('input[name="'+target+'Field"]').val('').trigger('change');
        $('input[name="'+target+'ThumbField"]').val('');

        $(this).parent('.work-img-thumb').addClass('hide').siblings('label').removeClass('hide');

        // console.log(_this.workImages);
      });
    },
    /**
     * 提交表单
     */
    submitForm: function () {
      var joinSubmitBtn = $('#joinSubmitBtn');
      var joinForm = $('#joinForm');

      joinSubmitBtn.prop('disabled', true).text('正在投稿...');

      $.ajax({
        type: 'POST',
        url: 'http://10.0.0.95/eventsys/submit/submitWorks',
        data: {
          mini_id: loginedUserInfo.Uin,
          name: $('#uname').val(),
          qq: '',
          mail: '',
          phone: '',
          team_mates: '',
          works_map_id: '',
          works_name: '',
          works_introduce: ''
        },
        dataType: 'json'
      }).done(function(res){
        if(!res.code){
          $.lightTip.success('投稿成功，作品将进入审核');
          setTimeout(function() {
            // location.href = '/';
          }, 3000);
        } else if(res.code == 2){
          $.lightTip.error('你已投稿过，每名筑梦师只能投稿一个作品');
        } else {
          $.lightTip.error(res.msg);
        }
      }).fail(function(err){
        $.lightTip.error('投稿失败，请稍候再试');
      }).always(function(res){
        joinSubmitBtn.prop('disabled', false).text('确定投稿');
      });
    },
    /**
     * 验证表单
     */
    validate: function () {
      var _this = this;

      $('input[name="fullViewField"]').data('target', $('#fullViewSpace'));
      $('input[name="nearbyView1Field"]').data('target', $('#nearbyView1Space'));

      new Validate($('#joinForm'), function () {
        _this.submitForm();
      }, {
        onSuccess: function () {},
        onError: function (err) {
          console.log(err);
        },
        validate: [{
            id: 'uname',
            prompt: {
              ignore: '请填写真实姓名'
            }
          },
          {
            id: 'qqNumber',
            prompt: {
              ignore: '请填写QQ号'
            }
          },
          {
            id: 'phoneOrTel',
            prompt: {
              unmatch: '电话或手机号码格式错误'
            }
          },
          {
            id: 'uingroup',
            prompt: {
              unmatch: '团队成员格式错误'
            }
          },
          {
            id: 'workDesc',
            prompt: {
              ignore: '请填写作品介绍'
            }
          },
          {
            id: 'fullViewField',
            prompt: {
              ignore: '请上传全景图'
            }
          },
          {
            id: 'nearbyView1Field',
            prompt: {
              ignore: '请上传近景图'
            }
          },
        ]
      })
    }
  }

  new JoinForm();


})();