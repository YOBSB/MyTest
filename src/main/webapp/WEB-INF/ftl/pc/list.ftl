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
  <title>筑梦师建造大赛2018夏季赛 - 迷你世界</title>
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
          <li>
            <a href="./works.html">本届作品</a>
          </li>
          <li class="active">
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
    <section class="reward-list-box">
      <div class="wrap">
        <header class="hide">
          <h2>获奖名单</h2>
        </header>
          <article class="reward-list-con" id="listContent"></article>
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

  <script type="text/html" id="tplSeasonTab">
      <div class="hd">
          <div class="tabs season-tab">
              {{each historySeason}}
              <a href="javascript:;" class="season-tab-item{{if $value.season.seasonLife}} active{{/if}}" data-seasonid="{{$value.season.id}}">{{$value.season.seasonName}}</a>
              {{/each}}
          </div>
      </div>
  </script>
  <script type="text/html" id="tplList">
      <div class="season-content-box">
          {{if !seasonInfo.season.seasonLife}}
          <div class="top-list-box">
              <div class="con">
                  <div class="hd">
                      <ul>
                          {{each seasonInfo.works}}
                          <li>{{$index}}</li>
                          {{/each}}
                      </ul>
                  </div>
                  <div class="bd">
                      <ul>
                          {{each seasonInfo.works}}
                          <li><img src="{{$value.worksMainImage}}"></li>
                          {{/each}}
                      </ul>
                  </div>
              </div>
          </div>
          <div class="other-list-box">
              <div class="other-list-box-tit">4-{{seasonInfo.works.length}}名作品</div>
              <div class="other-list-box-con">
                  <a href="javascript:;" class="prev">prev</a>
                  <a href="javascript:;" class="next">next</a>
                  <ul class="works-list clearfix">
                      {{each seasonInfo.works.slice(3)}}
                      <li>
                          <div class="cover">
                              <img src="{{$value.mainSmallImage}}" alt="{{$value.worksName}}">
                          </div>
                          <div class="info">
                              <div class="uin">迷你号：{{$value.miniId}}</div>
                              <div class="title">作品名称：{{$value.worksName}}</div>
                          </div>
                      </li>
                      {{/each}}
                  </ul>
              </div>
          </div>
          {{else}}
          <div class="empty-season-list">本赛季还未结束，荣誉榜单需要在赛季结束后才能评出结果</div>
          {{/if}}
      </div>
  </script>

  <script src="//cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
  <script src="//cdn.jsdelivr.net/npm/jquery.cookie@1.4.1/jquery.cookie.min.js"></script>
  <script src="//cdn.jsdelivr.net/npm/jquery-lazyload@1.9.7/jquery.lazyload.min.js"></script>
  <script src="//cdn.jsdelivr.net/npm/art-template@4.12.2/lib/template-web.js"></script>
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

      // honor/getHonorList
      var currentSeasonId = 0;
      var currentSeason = {};
      var historySeason = [];
      var topSlide = null;
      var listSlide = null;

      function getDeaultList(){
          $.ajax({
              type: "get",
              url: "http://10.0.0.95/eventsys/honor/getThisYearHonor",
              dataType: "json",
              success: function (response) {
                  if(!response.code){
                      if(response.data.seasonInfoList.length){
                          var tplHtml = $('#tplList').html();
                          var tplSeasonTab = $('#tplSeasonTab').html();

                          historySeason = response.data.seasonInfoList;

                          for (var i = 0, l = historySeason.length; i < l; i++) {
                              var ele = historySeason[i];
                              if(ele.season.seasonLife){
                                  currentSeason = ele;
                                  currentSeasonId = ele.season.id;
                              }
                          }

                          $('#listContent').append(template.render(tplSeasonTab, {
                              historySeason: historySeason
                          })).append(template.render(tplHtml, {
                              seasonInfo: currentSeason,
                              historySeason: historySeason
                          }));

                          if(!currentSeason.season.seasonLife){
                              topSlide = $('.top-list-box .con').slide({
                                  mainCell: ".bd ul",
                                  effect: "leftLoop"
                              });

                              listSlide = $('.other-list-box-con').slide({
                                  mainCell: '.works-list',
                                  effect: "leftLoop",
                                  prevCell: '.other-list-box-con .prev',
                                  nextCell: '.other-list-box-con .next',
                                  vis: 8,
                                  scroll: 8
                              });
                          }
                      } else {
                          $.lightTip.error('还没有榜单。');
                      }
                  } else {
                      $.lightTip.error(response.msg);
                  }
              }
          });
      }

      $('#listContent').on('click', 'a.season-tab-item', function(){
          var seasonId = $(this).data('seasonid');
          topSlide = null;
          listSlide = null;

          if(!$(this).hasClass('active')){
              $(this).addClass('active').siblings().removeClass('active');

              for (var i = 0, l = historySeason.length; i < l; i++) {
                  var ele = historySeason[i];
                  if(ele.season.id == seasonId){
                      currentSeason = ele;
                  }
              }

              console.log(currentSeason);
              var tplHtml = $('#tplList').html();

              $('#listContent').find('.season-content-box').replaceWith(template.render(tplHtml, {
                  seasonInfo: currentSeason
              }));

              if(!currentSeason.season.seasonLife){
                  topSlide = $('.top-list-box .con').slide({
                      mainCell: ".bd ul",
                      effect: "leftLoop"
                  });

                  console.log('aaa');
                  listSlide = $('.other-list-box-con').slide({
                      mainCell: '.works-list',
                      effect: "leftLoop",
                      prevCell: '.other-list-box-con .prev',
                      nextCell: '.other-list-box-con .next',
                      /* vis: 8,
                      scroll: 8 */
                  });
              }
          }
      });


      getDeaultList();

  </script>
</body>

</html>