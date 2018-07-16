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
  <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/luluui/peak/css/common/ui.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/app/css/app.css">
</head>

<body>
  <header class="site-header">
    <div class="wrap">
      <h1 class="logo fl">
        <a href="<%=request.getContextPath()%>">迷你世界</a>
      </h1>
      <nav class="site-nav fl">
        <ul>
          <li>
            <a href="<%=request.getContextPath()%>">官方首页</a>
          </li>
          <li class="active">
            <a href="<%=request.getContextPath()%>/contribute.html">报名投稿</a>
          </li>
          <li>
            <a href="<%=request.getContextPath()%>/works.html">本届作品</a>
          </li>
          <li>
            <a href="<%=request.getContextPath()%>/list.html">荣誉榜单</a>
          </li>
        </ul>
      </nav>
      <div class="login-wrap fr" id="uLogin">
        <div id="unlogin" class="hide">小伙伴们，请
          <a href="javascript:;" class="login">登录</a>
        </div>
        <div id="logined" class="hide">筑梦师 -
          <span class="uname"></span>
          <a href="javascript:;" class="logout">[ 退出 ]</a>
        </div>
      </div>
    </div>
  </header>

  <div class="main">
    <section class="g-banner g-box lazy" data-original="${match.mobileBannerImage}">
      <h2 class="hide">赛事宣传</h2>
    </section>
    <section class="g-join-method g-box lazy" data-original="${match.mobileSubmissionIntroduceImage}">
      <h2 class="hide">报名方式</h2>
      <article class="con">
        <div class="join-method">
          ${match.mobileSubmissionIntroduce}
      </article>
    </section>
    <div class="join-form">
      <form novalidate="novalidate" id="joinForm">
        <section class="join-box">
          <header class="hd">
            <h3 class="tit">个人/团队信息</h3>
            <p class="tit-en">SUBMISSION INSTRUCTIONS</p>
          </header>
          <article class="bd">
            <div class="form-item">
              <div class="form-label">迷你号</div>
              <div class="form-input__wrap">
                <b id="joinUin"></b>
              </div>
            </div>
            <div class="form-item">
              <div class="form-label">姓名</div>
              <div class="form-input__wrap">
                <input type="text" class="ui-input" id="uname" required placeholder="请填写个人或团队队长真实姓名" autocomplete="off">
                <span>*</span>
              </div>
            </div>
            <div class="form-item">
              <div class="form-label">QQ号码</div>
              <div class="form-input__wrap">
                <input type="number" class="ui-input" id="qqNumber" required placeholder="请填写QQ号" autocomplete="off">
                <span>*</span>
              </div>
            </div>
            <div class="form-item">
              <div class="form-label">邮箱</div>
              <div class="form-input__wrap">
                <input type="email" class="ui-input" placeholder="请填写邮箱" autocomplete="off">
              </div>
            </div>
            <div class="form-item">
              <div class="form-label">联系方式</div>
              <div class="form-input__wrap">
                <input type="phoneortel" class="ui-input" id="phoneOrTel" placeholder="请填写电话或手机号码" autocomplete="off">
              </div>
            </div>
            <div class="form-item">
              <div class="form-label">团队成员</div>
              <div class="form-input__wrap">
                <input type="uingroup" class="ui-input" id="uingroup" placeholder="请填写创造团队的迷你号，以英文逗号间隔" autocomplete="off">
              </div>
            </div>
            <div class="notice">请填写真实信息，如获得现金奖励我们将根据上述信息进行联系，信息错误导致无法发奖，玩家需自行承担。</div>
          </article>
        </section>

        <section class="join-box second">
          <header class="hd">
            <h3 class="tit">作品信息</h3>
            <p class="tit-en">WORK INFORMATION</p>
          </header>
          <article class="bd">
            <div class="form-item">
              <div class="form-label">作品选择</div>
              <div class="form-input__wrap">
                <select name="map" id="mapList" required>
                  <option value="">请选择参赛作品</option>
                </select>
                <span>*</span>
              </div>
            </div>
            <div class="form-item">
              <div class="form-label align-top">作品介绍</div>
              <div class="form-input__wrap">
                <div class="ui-textarea-x">
                  <textarea id="workDesc" required maxlength="150" rows="5" placeholder="请填写作品介绍，包括作品创意、亮点（150字内）"></textarea>
                  <div class="ui-textarea"></div>
                  <label for="workDesc" class="ui-textarea-count">
                    <span>0</span>/
                    <span>150</span>
                  </label>
                </div>
              </div>
              <span>*</span>
            </div>
            <div class="form-item">
              <div class="form-label align-top">作品截图</div>
              <div class="form-input__wrap">
                <div class="upload-notice">（作品截图说明，主委通过作品截图初步审查玩家作品，乱截图或截图效果不佳可能会影响初审）</div>
                <div class="upload-wrap" id="uploadWrap">
                  <div class="upload-item">
                    <div class="place" id="fullViewSpace">
                      <label for="fullView"></label>
                      <input type="hidden" name="fullViewField" id="fullViewField" required>
                      <input type="hidden" name="fullViewThumbField">
                      <div class="work-img-thumb hide">
                        <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC" class="thumb" id="fullViewThumbImg" width="180" height="110">
                        <a href="javascript:;" class="delete-thumb-btn" data-target="fullView"></a>
                      </div>
                    </div>
                    <p>全景</p>
                  </div>
                  <div class="upload-item">
                    <div class="place" id="nearbyView1Space">
                      <label for="nearbyView1"></label>
                      <input type="hidden" name="nearbyView1Field" id="nearbyView1Field" required>
                      <input type="hidden" name="nearbyView1ThumbField">
                      <div class="work-img-thumb hide">
                        <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC" class="thumb" id="nearbyView1ThumbImg" width="180" height="110">
                        <a href="javascript:;" class="delete-thumb-btn" data-target="nearbyView1"></a>
                      </div>
                    </div>
                    <p>近景1</p>
                  </div>
                  <div class="upload-item">
                    <div class="place" id="nearbyView2Space">
                      <label for="nearbyView2"></label>
                      <input type="hidden" name="nearbyView2Field">
                      <input type="hidden" name="nearbyView2ThumbField">
                      <div class="work-img-thumb hide">
                        <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC" class="thumb" id="nearbyView2ThumbImg" width="180" height="110">
                        <a href="javascript:;" class="delete-thumb-btn" data-target="nearbyView2"></a>
                      </div>
                    </div>
                    <p>近景2</p>
                  </div>
                  <div class="upload-item">
                    <div class="place" id="nearbyView3Space">
                      <label for="nearbyView3"></label>
                      <input type="hidden" name="nearbyView3Field">
                      <input type="hidden" name="nearbyView3ThumbField">
                      <div class="work-img-thumb hide">
                        <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC" class="thumb" id="nearbyView3ThumbImg" width="180" height="110">
                        <a href="javascript:;" class="delete-thumb-btn" data-target="nearbyView3"></a>
                      </div>
                    </div>
                    <p>近景3</p>
                  </div>
                </div>
              </div>
            </div>
            <div>
              <button type="submit" class="join-submit-btn" id="joinSubmitBtn">确定投稿</button>
            </div>
          </article>
        </section>
      </form>
      <form class="hide" method="post" enctype="multipart/form-data">
        <input type="file" name="workImg" id="fullView" accept="image/png, image/jpeg, image/jpg">
      </form>
      <form class="hide" method="post" enctype="multipart/form-data">
        <input type="file" name="workImg" id="nearbyView1" accept="image/png, image/jpeg, image/jpg">
      </form>
      <form class="hide" method="post" enctype="multipart/form-data">
        <input type="file" name="workImg" id="nearbyView2" accept="image/png, image/jpeg, image/jpg">
      </form>
      <form class="hide" method="post" enctype="multipart/form-data">
        <input type="file" name="workImg" id="nearbyView3" accept="image/png, image/jpeg, image/jpg">
      </form>
    </div>
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
  <div id="result"></div>
  <script src="//cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
  <script src="//cdn.jsdelivr.net/npm/jquery.cookie@1.4.1/jquery.cookie.min.js"></script>
  <script src="//cdn.jsdelivr.net/npm/jquery-lazyload@1.9.7/jquery.lazyload.min.js"></script>
  <script src="//cdn.jsdelivr.net/npm/blueimp-file-upload@9.22.0/js/vendor/jquery.ui.widget.js"></script>
  <script src="//cdn.jsdelivr.net/npm/blueimp-file-upload@9.22.0/js/jquery.iframe-transport.js"></script>
  <script src="//cdn.jsdelivr.net/npm/blueimp-file-upload@9.22.0/js/jquery.fileupload.min.js"></script>
  <!--[if (gte IE 8)&(lt IE 10)]>
    <script src="//cdn.jsdelivr.net/npm/blueimp-file-upload@9.22.0/js/cors/jquery.xdr-transport.js"></script>
  <![endif]-->
  <script src="<%=request.getContextPath()%>/resources/luluui/peak/js/common/ui/Select.js"></script>
  <script src="<%=request.getContextPath()%>/resources/luluui/peak/js/common/ui/Placeholder.js"></script>
  <script src="<%=request.getContextPath()%>/resources/luluui/peak/js/common/ui/Loading.js"></script>
  <script src="<%=request.getContextPath()%>/resources/luluui/peak/js/common/ui/Dialog.js"></script>
  <script src="<%=request.getContextPath()%>/resources/luluui/peak/js/common/ui/Follow.js"></script>
  <script src="<%=request.getContextPath()%>/resources/luluui/peak/js/common/ui/LightTip.js"></script>
  <script src="<%=request.getContextPath()%>/resources/luluui/peak/js/common/ui/ErrorTip.js"></script>
  <script src="<%=request.getContextPath()%>/resources/luluui/peak/js/common/ui/Validate.js"></script>
  <script src="<%=request.getContextPath()%>/resources/app/js/common.js"></script>
  <script src="<%=request.getContextPath()%>/resources/app/js/app.join.js"></script>
</body>

</html>