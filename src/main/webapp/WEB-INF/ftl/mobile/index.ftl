<!DOCTYPE html>
<html lang="zh-cmn-Hans">

<head>
    <meta charset="UTF-8">
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no, email=no">
    <title>建造大赛</title>
    <script>function supportWebp(t){t=t||function(){};var i=new Image;function e(e){e&&"load"===e.type&&1==i.width&&(window.isSupportWebp=!0,t())}i.onerror=e,i.onload=e,i.src="data:image/webp;base64,UklGRiQAAABXRUJQVlA4IBgAAAAwAQCdASoBAAEAAwA0JaQAA3AA/vuUAAA="}window.isSupportWebp=!1,supportWebp(function(){document.documentElement.classList.add("webp")}),function(t,e){function i(){var e=a.getBoundingClientRect().width/10;a.style.fontSize=e+"px",d.rem=t.rem=e}var r,n=t.document,a=n.documentElement,o=n.querySelector('meta[name="viewport"]'),s=n.querySelector('meta[name="flexible"]'),m=n.querySelector('meta[name="flexible-in-x5"]'),c=!0,l=0,p=0,d=e.flexible||(e.flexible={});if(o){console.warn("将根据已有的meta标签来设置缩放比例");var u=o.getAttribute("content").match(/initial\-scale=([\d\.]+)/);u&&(p=parseFloat(u[1]),l=parseInt(1/p))}else if(s){var f=s.getAttribute("content");if(f){var w=f.match(/initial\-dpr=([\d\.]+)/),A=f.match(/maximum\-dpr=([\d\.]+)/);w&&(l=parseFloat(w[1]),p=parseFloat((1/l).toFixed(2))),A&&(l=parseFloat(A[1]),p=parseFloat((1/l).toFixed(2)))}}if(m&&(c="false"!==m.getAttribute("content")),!l&&!p){var b=(t.navigator.appVersion.match(/android/gi),t.chrome),h=t.navigator.appVersion.match(/iphone/gi),v=t.devicePixelRatio,g=/TBS\/\d+/.test(t.navigator.userAgent),x=!1;try{x="true"===localStorage.getItem("IN_FLEXIBLE_WHITE_LIST")}catch(t){x=!1}p=1/(l=h||b||g&&c&&x?3<=v&&(!l||3<=l)?3:2<=v&&(!l||2<=l)?2:1:1)}if(a.setAttribute("data-dpr",l),!o)if((o=n.createElement("meta")).setAttribute("name","viewport"),o.setAttribute("content","initial-scale="+p+", maximum-scale="+p+", minimum-scale="+p+", user-scalable=no, viewport-fit=cover"),a.firstElementChild)a.firstElementChild.appendChild(o);else{var y=n.createElement("div");y.appendChild(o),n.write(y.innerHTML)}t.addEventListener("resize",function(){clearTimeout(r),r=setTimeout(i,300)},!1),t.addEventListener("pageshow",function(e){e.persisted&&(clearTimeout(r),r=setTimeout(i,300))},!1),"complete"===n.readyState?n.body.style.fontSize=12*l+"px":n.addEventListener("DOMContentLoaded",function(e){n.body.style.fontSize=12*l+"px"},!1),i(),d.dpr=t.dpr=l,d.refreshRem=i,d.rem2px=function(e){var t=parseFloat(e)*this.rem;return"string"==typeof e&&e.match(/rem$/)&&(t+="px"),t},d.px2rem=function(e){var t=parseFloat(e)/this.rem;return"string"==typeof e&&e.match(/px$/)&&(t+="rem"),t}}(window,window.lib||(window.lib={})),window.Promise||document.write('<script src="//lib.baomitu.com/es6-promise/4.1.1/es6-promise.auto.min.js"><\/script>'),window.fetch||document.write('<script src="//lib.baomitu.com/fetch/2.0.3/fetch.min.js"><\/script>')</script>
    <style>*,::after,::before{-webkit-box-sizing:inherit;box-sizing:inherit;-webkit-tap-highlight-color:transparent}html{-webkit-box-sizing:border-box;box-sizing:border-box;color:#333;font-family:'Helvetica Neue',Tahoma,Arial,PingFangSC-Regular,'Hiragino Sans GB','Microsoft Yahei',sans-serif;line-height:1.2;-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none;-webkit-font-smoothing:antialiased;-ms-touch-action:manipulation;touch-action:manipulation;-webkit-text-size-adjust:none;-moz-text-size-adjust:none;-ms-text-size-adjust:none;text-size-adjust:none}body,button,dd,dl,ol,ul{margin:0;padding:0}ol,ul{list-style:none}a{outline:0;color:inherit;text-decoration:none}a,img{-webkit-touch-callout:none}button,input,select,textarea{outline:0;border:none;font-size:inherit;font-family:inherit}h1,h2,h3,h4,h5,h6,p{margin:0;font-weight:400}img{max-width:100%}textarea{resize:none}select{background-color:transparent;-webkit-appearance:none;-moz-appearance:none;appearance:none}input[type=button],input[type=reset],input[type=submit]{-webkit-appearance:button;-moz-appearance:button;appearance:button}input:-webkit-autofill{-webkit-box-shadow:0 0 0 100px #fff inset;box-shadow:0 0 0 100px #fff inset}.shellAnimation{-webkit-animation:shellPulse 1s infinite;animation:shellPulse 1s infinite}@-webkit-keyframes shellPulse{0%{opacity:1}50%{opacity:.6}100%{opacity:1}}@keyframes shellPulse{0%{opacity:1}50%{opacity:.6}100%{opacity:1}}@-webkit-keyframes spinner{from{-webkit-transform:rotate(0);transform:rotate(0)}to{-webkit-transform:rotate(360deg);transform:rotate(360deg)}}@keyframes spinner{from{-webkit-transform:rotate(0);transform:rotate(0)}to{-webkit-transform:rotate(360deg);transform:rotate(360deg)}}.spinner{height:1.333333rem;display:-webkit-box;display:-webkit-flex;display:-ms-flexbox;display:flex;-webkit-box-align:end;-webkit-align-items:flex-end;-ms-flex-align:end;align-items:flex-end;-webkit-box-pack:center;-webkit-justify-content:center;-ms-flex-pack:center;justify-content:center}.spinner:after{display:block;content:'';width:10.24px;width:.64rem;height:10.24px;height:.64rem;-webkit-animation:spinner .5s infinite linear;animation:spinner .5s infinite linear;border-left:.853px solid transparent;border-left:.053333rem solid transparent;border-right:.853px solid transparent;border-right:.053333rem solid transparent;border-bottom:.853px solid transparent;border-bottom:.053333rem solid transparent;border-top:.853px solid #2395ff;border-top:.053333rem solid #2395ff;border-radius:100%}</style>
    <link href="//10.0.0.95/jzds/mobile/css/home.a61f5783f338c26484d2.css" rel="stylesheet"></head>

<body ontouchstart>
<header class="site-header">
    <div class="wrap">

        <nav class="site-nav fl">
            <ul>
                <li class="active">
                    <a href="./">官方首页</a>
                </li>
                <li>
                    <a href="./join.html">报名投稿</a>
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
            <div id="unlogin" class="hide">
                <a href="javascript:;" class="login">登录</a>
            </div>
            <div id="logined" class="hide">
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
    <section class="g-theme g-box lazy" data-original="${match.mobileThemeImage}">
        <h2 class="hide">建造主题</h2>
    </section>
    <section class="g-time g-box lazy" data-original="${match.mobileTimeImage}">
        <h2 class="hide">赛事时间</h2>
    </section>
    <section class="g-rewards g-box lazy" data-original="${match.mobileRewardImage}">
        <h2 class="hide">赛事奖励</h2>
    </section>
    <section class="g-rules g-box lazy" data-original="${match.mobileRuleImage}">
        <h2 class="hide">赛事规则</h2>
        <article class="con">
            <div class="rules">${match.mobileRule}</div>
        </article>
    </section>
    <section class="g-review g-box lazy" data-original="${match.mobileReviewImage}">
        <h2 class="hide">评审机制</h2>
        <article class="con">
            <div class="review">${match.mobileReview}</div>
        </article>
    </section>
    <section class="g-judge g-box lazy" data-original="${match.mobileJudgeGuest}">
        <h2 class="hide">评委嘉宾</h2>
    </section>
    <section class="g-introduced g-box lazy" data-original="${match.mobileGameIntroduceImage}">
        <h2 class="hide">赛事介绍</h2>
        <article class="con">
            <div class="introduced">${match.mobileGameIntroduce}</div>
        </article>
    </section>
    <section class="g-partner g-box lazy" data-original="${match.mobilePartner}">
        <h2 class="hide">合作伙伴</h2>
    </section>
</div>


<script type="text/javascript" src="//10.0.0.95/jzds/mobile/js/common.44edeb4f8c675a927474.js"></script><script type="text/javascript" src="//10.0.0.95/jzds/mobile/js/home.72eeb4dfd43899fe6649.js"></script></body>

</html>


