var page = require('webpage').create();

var system = require('system');
var htmlUrl = system.args[1];
var savePath = system.args[2];

function closePhantom() {
    phantom.exit();
}

function doRender() {
    page.render(savePath, {format: 'pdf'});
    console.log('render ok');
}

page.open(htmlUrl, function (status) {
    if (status === "success") {
        setTimeout(function () {
            doRender();
            closePhantom();
        }, 500);
    } else {
        console.log(status);
        closePhantom();
    }
});
