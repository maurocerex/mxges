function showWait() {
  Y.WaitPnl.show();
}
function hideWait() {
  Y.WaitPnl.hide();
}

function waitInit() {
  Y.WaitPnl = new Y.widget.Panel("waitpnl", {
    width : "120px",
    height : "120px",
    fixedcenter : true,
    close : false,
    draggable : false,
    zindex : 5,
    modal : true,
    visible : false
  });
  var kw = new Y.util.KeyListener(document, {
    keys : 27
  }, {
    fn : Y.WaitPnl.hide,
    scope : Y.WaitPnl,
    correctScope : true
  });
  kw.enable();
  Y.WaitPnl.setHeader("Cargando...");
  Y.WaitPnl.setBody('<img src="' + Y.ctx + '/images/loader.gif"  align="middle"  />');
  Y.WaitPnl.render(document.body);
}

Y.util.Event.onDOMReady(waitInit);

Y.showWait = showWait;

Y.hideWait = hideWait;