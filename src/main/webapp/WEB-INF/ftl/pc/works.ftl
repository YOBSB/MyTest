<!DOCTYPE html>
<!--[if lte IE 6 ]> <html class="ie ie6 lte_ie7 lte_ie8 lte_ie9" lang="zh-cmn-Hans"> <![endif]-->
<!--[if IE 7 ]> <html class="ie ie7 lte_ie7 lte_ie8 lte_ie9" lang="zh-cmn-Hans"> <![endif]-->
<!--[if IE 8 ]> <html class="ie ie8 lte_ie8 lte_ie9" lang="zh-cmn-Hans"> <![endif]-->
<!--[if IE 9 ]> <html class="ie ie9 lte_ie9" lang="zh-cmn-Hans"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="zh-cmn-Hans">
<!--<![endif]-->

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=Edge">
  <meta name="renderer" content="webkit">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>筑梦师建造大赛2018夏季赛 - 报名投稿 - 迷你世界</title>
  <link rel="shortcut icon" href="//static-www.mini1.cn/favicon.ico" type="image/x-icon">
  <!--[if lte IE 8 ]>
    <script src="//lib.baomitu.com/html5shiv/r29/html5.min.js"></script>
    <script src="//lib.baomitu.com/es5-shim/4.5.10/es5-shim.min.js"></script>
    <script src="//lib.baomitu.com/es5-shim/4.5.10/es5-sham.min.js"></script>
  <![endif]-->
  <link rel="stylesheet" href="http://heby-test.mini1.cn/statics/jzds/luluui/peak/css/common/ui.css">
  <link rel="stylesheet" href="http://heby-test.mini1.cn/statics/jzds/app/pc/css/app.css">
</head>

<body>
  <header class="site-header">
    <div class="wrap">
      <h1 class="logo fl">
        <a href="./">迷你世界</a>
      </h1>
      <nav class="site-nav fl">
        <ul>
          <li>
            <a href="./">官方首页</a>
          </li>
          <li>
            <a href="./contribute.html">报名投稿</a>
          </li>
          <li class="active">
            <a href="./works.html">本届作品</a>
          </li>
          <li>
            <a href="./list.html">荣誉榜单</a>
          </li>
        </ul>
      </nav>
      <div class="login-wrap fr" id="uLogin">
        <div id="unlogin" class="hide">小伙伴们，请 <a href="javascript:;" class="login">登录</a></div>
        <div id="logined" class="hide">筑梦师 - <span class="uname"></span> <a href="javascript:;" class="logout">[ 退出 ]</a></div>
      </div>
    </div>
  </header>

  <div class="main">
    <section class="g-banner g-box lazy" data-original="${match.bannerImage}">
      <h2 class="hide">赛事宣传</h2>
    </section>
    <section class="g-description g-box lazy" data-original="${match.worksIntroduceImage}">
      <h2 class="hide">作品说明</h2>
      <article class="con">
        <div class="description">
          ${match.worksIntroduce}
        </div>
      </article>
    </section>
    <section class="works-box">
      <div class="wrap">
        <header class="hide">
          <h2>作品列表</h2>
        </header>
          <article class="works-con">
              <div class="hd clearfix">
                  <ul class="tabs fl" id="worksTab">
                      <!-- <li class="active">全部</li> -->
                      <li class="active" data-type="2">人气</li>
                      <li data-type="1">最新</li>
                      <li data-type="3">本周热门</li>
                  </ul>
                  <div class="fresh-box fr">
                      <!-- <a href="javascript:;" class="refresh">refresh</a> -->
                      <form class="search-box" id="searchWorkItemForm">
                          <input type="text" placeholder="搜索迷你号" name="uid" class="search-input" autocomplete="off">
                          <input type="submit" value="搜索" class="search-btn">
                      </form>
                  </div>
              </div>
              <div class="bd">
                  <ul class="works-list clearfix" id="worksList"></ul>
                  <div id="pagination" class="pagination"></div>
              </div>
          </article>
      </div>
    </section>
  </div>

  <footer id="footer" class="site-footer">
    <div class="con clearfix">
      <div class="company-logo s-bg"></div>
      <div class="info">
        <p>软著2016SR366060丨增值电信业务经营许可证：
          <a href="//www.mini1.cn/index.php/icp/" target="_blank">粤B2-20160622</a> |
          <a href="http://www.miitbeian.gov.cn" target="_blank">粤ICP备15114487号-1</a> | 文网游备字〔2017〕Ｃ-CSG 0005 号 |
          <a href="//www.mini1.cn/index.php/aboutus/" target="_blank">关于我们</a>
          <br>新广出审[2017]5896号 丨ISBN 978-7-7979-9367-8 | 《
          <a href="//www.mini1.cn/index.php/wangwen/" target="_blank">网络文化经营许可证</a>》粤网文[2016]1349-302号</p>
      </div>
      <div class="copyright">Copyright 2015-
        <script>
          document.write(new Date().getFullYear().toString());
        </script> 深圳市迷你玩科技有限公司 All Rights Reserved.</div>
      <div>
        <a href="http://58.62.173.137/mimpeach.php" target="_blank">
          <img src="//static-www.mini1.cn/assets/v3/images/blxxjb.png" alt="不良信息举报">
        </a>
      </div>
    </div>
  </footer>

  <script type="text/html" id="tplWorkItem">
      {{each list}}
      <li data-uid="{{$value.miniId}}" data-wid="{{$value.worksId}}">
          <div class="cover">
              <img src="{{$value.mainSmallImage}}" alt="{{$value.worksName}}">
          </div>
          <div class="info">
              <div class="uin">迷你号：{{$value.miniId}}</div>
              <div class="title">作品名称：{{$value.worksName}}</div>
          </div>
      </li>
      {{/each}}
  </script>

  <script src="//cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
  <script src="//cdn.jsdelivr.net/npm/jquery.cookie@1.4.1/jquery.cookie.min.js"></script>
  <script src="//cdn.jsdelivr.net/npm/jquery-lazyload@1.9.7/jquery.lazyload.min.js"></script>
  <script src="//cdn.jsdelivr.net/npm/art-template@4.12.2/lib/template-web.js"></script>
  <script src="http://heby-test.mini1.cn/statics/jzds/app/js/jquery.SuperSlide.2.1.1.js"></script>
  <script src="http://heby-test.mini1.cn/statics/jzds/luluui/peak/js/common/ui/Pagination.js"></script>
  <script src="http://heby-test.mini1.cn/statics/jzds/luluui/peak/js/common/ui/Loading.js"></script>
  <script src="http://heby-test.mini1.cn/statics/jzds/luluui/peak/js/common/ui/Dialog.js"></script>
  <script src="http://heby-test.mini1.cn/statics/jzds/luluui/peak/js/common/ui/Follow.js"></script>
  <script src="http://heby-test.mini1.cn/statics/jzds/luluui/peak/js/common/ui/LightTip.js"></script>
  <script src="http://heby-test.mini1.cn/statics/jzds/luluui/peak/js/common/ui/ErrorTip.js"></script>
  <script src="http://heby-test.mini1.cn/statics/jzds/luluui/peak/js/common/ui/Validate.js"></script>
  <script src="http://heby-test.mini1.cn/statics/jzds/app/pc/js/common.js"></script>
  <script>
      $(".lazy").lazyload({
          effect: "fadeIn"
      });

      new Login();

      var workList = [];

      // 获取默认列表
      /* function getDeaultList(){
        $.ajax({
          type: "get",
          url: "http://10.0.0.1:8080/eventsys/worksShow/getDef",
          data: {
            num: 8
          },
          dataType: "json",
          success: function (response) {
            if(!response.code){
              if(response.data.works.length){
                var tplWorkItem = $('#tplWorkItem').html();
                var listHtml = template.render(tplWorkItem, {list:response.data.works});

                $('#worksList').html(listHtml);

                $('#pagination').pagination({
                  length: response.data.numPages * 8,
                  every: 8,
                  onClick: function(el, cur){
                    console.log(cur);
                  }
                });
              } else {
                $.lightTip.error('还没有参赛作品。');
              }
            } else {
              $.lightTip.error(response.msg);
            }
          }
        });
      }

      getDeaultList(); */

      function getList(type, page){
          var lock = false;
          page = page || 1;

          if(!lock){
              lock = true;
              $.ajax({
                  type: "get",
                  url: "http://10.0.0.95/eventsys/worksShow/getList",
                  data: {
                      type: type,
                      num: 8,
                      page: page
                  },
                  dataType: "json",
                  success: function (response) {
                      if(!response.code){
                          if(response.data.works.length){
                              workList = response.data.works;

                              var tplWorkItem = $('#tplWorkItem').html();
                              var listHtml = template.render(tplWorkItem, {list: response.data.works});

                              $('#worksList').html(listHtml);

                              $('#pagination').pagination({
                                  length: response.data.worksNum,
                                  every: 8,
                                  current: page,
                                  onClick: function(el, cur){

                                      // 加载分页数据
                                      getList(type, cur);
                                  }
                              });
                          } else {
                              $.lightTip.error('没有展示作品');
                          }
                      } else {
                          $.lightTip.error(response.msg);
                      }
                  },
                  error: function(){
                      $.lightTip.error('获取列表出错');
                  },
                  complete: function(){
                      lock = false;
                  }
              })
          };
      }

      // tab绑定
      getList(2);
      $('#worksTab').on('click', 'li', function(){
          var _type = $(this).data('type');

          $(this).addClass('active').siblings().removeClass('active');

          // 执行加载数据逻辑
          getList(_type, 1);
      });

      // worksShow/getByUid
      $('#searchWorkItemForm').on('submit', function(e){
          e.preventDefault();

          var uid = $('[name="uid"]').val();
          if(uid){
              $.ajax({
                  type: "get",
                  url: "http://10.0.0.95/eventsys/worksShow/getByUid",
                  data: {
                      uid: $('[name="uid"]').val()
                  },
                  dataType: "json",
                  success: function (response) {
                      if(!response.code){
                          if(response.data.works.length){
                              workList = response.data.works;

                              var tplWorkItem = $('#tplWorkItem').html();
                              var listHtml = template.render(tplWorkItem, {list:response.data.works});

                              $('#worksList').html(listHtml);
                          } else {
                              $.lightTip.error('该玩家没有投稿作品');
                          }
                      } else {
                          $.lightTip.error(res.msg);
                      }
                  },
                  error: function(){
                      $.lightTip.error('获取作品失败');
                  },
                  complete: function(){
                      //
                  }
              });
          } else {
              $.lightTip.error('请输入迷你号');
          }

      });

      // report click
      function reportClick(wid,uid){
          var loginedUserInfo = JSON.parse(sessionStorage.getItem('loginedUserInfo'));
          var _data = null;
          var defaultData = {
              wid: wid,
              uid: uid
          };

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
              type: "get",
              url: "http://10.0.0.95/eventsys/worksShow/incrHeat",
              data: _data,
              dataType: "json",
              success: function (response) {
                  if(!response.code){
                      console.log('reported');
                  } else {
                      console.log('report fail');
                  }
              },
              error: function () {
                  console.log('report error');
              }
          });
      }


      // 弹出层
      var previewDialog = null;
      var previewSlide = null;
      $('#worksList').on('click', 'li', function () {

          var wid = $(this).data('wid');
          var uid = $(this).data('uid');
          var loginedUserInfo = JSON.parse(sessionStorage.getItem('loginedUserInfo'));

          // 只有登录了才记录
          if(loginedUserInfo && (loginedUserInfo.sign || loginedUserInfo.auth)){
              reportClick(wid,loginedUserInfo.Uin)
          }

          var result = {
              images: [],
              title: '',
              desc: ''
          };

          if(workList.length){
              for (var i = 0, l = workList.length; i < l; i++) {
                  var ele = workList[i];
                  if(wid == ele.worksId){
                      result.title = ele.worksName;
                      result.desc = ele.worksIntroduce;
                      result.images.push(ele.worksMainImage, ele.image1);

                      if(ele.image2.length){
                          result.images.push(ele.image2);
                      }

                      if(ele.image3.length){
                          result.images.push(ele.image3);
                      }
                      break;
                  }
              }
          }

          var createHtml = function(data){
              var html = '';
              var bdHtml = '';
              var hdHtml = '';
              $.each(data.images, function (index, obj) {
                  hdHtml = hdHtml + '<li>' + (index+1) + '</li>';
                  bdHtml = bdHtml + '<li><img src="' + obj + '"></li>';
              });

              var desc = '<div class="preview-desc"><h3>作品介绍</h3><div class="con">'+data.desc+'</div></div>';

              html = '<div class="preview-slide slideBox"><div class="hd"><ul>'+hdHtml+'</ul></div><div class="bd"><ul>'+bdHtml+'</ul></div></div>'+desc;

              return html;
          };

          previewDialog = new Dialog({
              width: 900,
              onShow: function(){
                  previewSlide = $(".slideBox").slide({
                      mainCell: ".bd ul",
                      effect: "leftLoop"
                  });
              },
              onHide: function(){
                  previewDialog = null;
                  previewSlide = null;
              },
              title: result.title,
              content: $(createHtml(result))
          });
      });
  </script>
</body>

</html>