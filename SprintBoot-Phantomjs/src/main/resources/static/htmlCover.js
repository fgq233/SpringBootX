var page = require('webpage').create();

// 系统变量集合,按照角标,从1开始
var system = require('system');
var htmlUrl = system.args[1];      // 网页地址
var savePath = system.args[2];     // 转换后的文件全路径
var fileType = system.args[3];     // 转换文件类型，jpg，pdf
var cookies = system.args[4];      // cookie
var domain = system.args[5];      // cookie的domain
var domSelector = system.args[6]; // 页面dom裁剪
var singlePage = system.args[7];  // 用于没有滚动条的页面


if (singlePage) {
    if (fileType === "jpg") {
        // 浏览器大小
        page.viewportSize = {width: 1920, height: 1080};
        // 图片大小，可以是固定宽高，也可以是 A4 纸
        page.paperSize = {
            width: "1920px",
            height: "1080px",
            orientation: 'portrait'
        };
    } else if (fileType === "pdf") {
        page.viewportSize = {width: 1920, height: 1080};
        page.paperSize = {
            format: "A4",
            orientation: 'portrait'
        };
    }
}

// 设置cookies
if (cookies !== undefined && cookies !== null && cookies.length > 0 && cookies !== 'null') {
    var cookiesArr = cookies.split(';');
    if (cookiesArr && cookiesArr.length > 0) {
        for (var i = 0; i < cookiesArr.length; i++) {
            var c = cookiesArr[i].split("=");
            phantom.addCookie({
                'name': c[0],
                'value': c[1],
                'domain': domain ? domain : "localhost",
                'path': '/'
            });
        }
    }
}

/**
 * 裁剪
 */
function doClip() {
    if (domSelector !== undefined && domSelector !== null && domSelector.length > 0 && domSelector !== 'null') {
        console.log("doClip");
        // 找到指定dom位置裁剪
        var dd = page.evaluate(function (ds) {
            var dom = document.querySelector(ds);
            if (dom) {
                return {
                    top: dom.offsetTop,
                    left: dom.offsetLeft,
                    width: dom.offsetWidth,
                    height: dom.offsetHeight
                };
            } else {
                return null;
            }
        }, domSelector);
        if (dd) {
            page.clipRect = {
                top: dd.top,
                left: dd.left,
                width: dd.width,
                height: dd.height
            };
        }
        console.log("doClip ok");
    }
}

/**
 * 转换渲染，可以是 PDF、PNG、JPEG、BMP、GIF等，quality(图片质量)：0-100
 */
function doRender() {
    doClip();
    if (fileType === "jpg") {
        page.render(savePath, {format: 'jpeg', quality: '60'});
    } else if (fileType === "pdf") {
        page.render(savePath, {format: 'pdf'});
    } else {
        page.render(savePath);
    }
    console.error("doRender ok");
}

page.open(htmlUrl, function (status) {
    if (status === "success") {
        setTimeout(function () {
            doRender();
            closePhantom();
        }, 500);
    } else {
        console.error(status);
        closePhantom();
    }
});


/**
 * 退出
 */
function closePhantom() {
    phantom.exit();
}
