function showException(exception) {
  try {
    Y.ExceptionPnl.setBody("<p>" + exception + "</p>");
    Y.ExceptionPnl.show();
  } catch (e) {
    alert("showException:" + e.description);
  }
}

function exceptionInit() {
  Y.ExceptionPnl = new Y.widget.Panel("exceptioncontainer", {
    width : "40em",
    fixedcenter : true,
    close : true,
    draggable : false,
    zindex : 6,
    modal : true,
    constraintoviewport : true,
    monitorresize : true,
    visible : false
  });
  var kw = new Y.util.KeyListener(document, {
    keys : 27
  }, {
    fn : Y.ExceptionPnl.hide,
    scope : Y.ExceptionPnl,
    correctScope : true
  });
  kw.enable();
  Y.ExceptionPnl.setHeader("Error");
  Y.ExceptionPnl.render(document.body);
}

Y.util.Event.onDOMReady(exceptionInit);