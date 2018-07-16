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
          <li class="active">
            <a href="./">官方首页</a>
          </li>
          <li>
            <a href="./contribute.html">报名投稿</a>
          </li>
          <li>
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
    <section class="g-theme g-box lazy" data-original="${match.themeImage}">
      <h2 class="hide">建造主题</h2>
    </section>
    <section class="g-time g-box lazy" data-original="${match.timeImage}">
      <h2 class="hide">赛事时间</h2>
    </section>
    <section class="g-rewards g-box lazy" data-original="${match.rewardImage}">
      <h2 class="hide">赛事奖励</h2>
    </section>
    <section class="g-rules g-box lazy" data-original="${match.ruleImage}">
      <h2 class="hide">赛事规则</h2>
      <article class="con">
        <div class="rules">${match.rule}</div>
      </article>
    </section>
    <section class="g-review g-box lazy" data-original="${match.reviewImage}">
      <h2 class="hide">评审机制</h2>
      <article class="con">
        <div class="review">${match.review}</div>
      </article>
    </section>
    <section class="g-judge g-box lazy" data-original="${match.judgeGuest}">
      <h2 class="hide">评委嘉宾</h2>
    </section>
    <section class="g-introduced g-box lazy" data-original="${match.gameIntroduceImage}">
      <h2 class="hide">赛事介绍</h2>
      <article class="con">
        <div class="introduced">${match.gameIntroduce}</div>
      </article>
    </section>
    <section class="g-partner g-box lazy" data-original="${match.partner}">
      <h2 class="hide">合作伙伴</h2>
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

  <script src="//cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
  <script src="//cdn.jsdelivr.net/npm/jquery.cookie@1.4.1/jquery.cookie.min.js"></script>
  <script src="//cdn.jsdelivr.net/npm/jquery-lazyload@1.9.7/jquery.lazyload.min.js"></script>
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
  </script>
</body>

</html>