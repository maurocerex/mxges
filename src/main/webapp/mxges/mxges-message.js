Y.MessagePnl = function() {
};

Y.MessagePnl.prototype = {
  _messagepnl : null,
  showmsg : function(oArgs) {
    if (this._messagepnl == null) {
      this.construct();
    }
    msg = oArgs.msg;
    fnSubmit = oArgs.fnSubmit;
    this._messagepnl.setBody("<p>" + msg + "</p>");
    this._messagepnl.show();
    if (Y.lang.isFunction(oArgs.fnSubmit)) {
      this._messagepnl.submitEvent.subscribe(oArgs.fnSubmit);
    } else {
      this._messagepnl.submitEvent.subscribe(function() {
      });
    }
  },
  construct : function(o) {
    var handleSubmit = function() {
      this.submit();
    };
    this._messagepnl = new Y.widget.SimpleDialog("messagepnl", {
      width : "40em",
      fixedcenter : true,
      close : true,
      draggable : false,
      zindex : 6,
      modal : true,
      constraintoviewport : true,
      monitorresize : true,
      visible : false,
      buttons : [ {
        text : "Aceptar",
        handler : handleSubmit,
        isDefault : true
      } ]

    });
    var kw = new Y.util.KeyListener(document, {
      keys : 27
    }, {
      fn : this._messagepnl.hide,
      scope : this._messagepnl,
      correctScope : true
    });
    kw.enable();
    this._messagepnl.setHeader("Aviso");
    this._messagepnl.render(document.body);
  }
};

Y.MessageErrorPnl = function() {
};

Y.MessageErrorPnl.prototype = {
  _messagepnl : null,
  showmsg : function(oArgs) {
    if (this._messagepnl == null) {
      this.construct();
    }
    msg = oArgs.msg;
    fnSubmit = oArgs.fnSubmit;
    this._messagepnl.setBody("<p>" + msg + "</p>");
    this._messagepnl.show();
    if (Y.lang.isFunction(oArgs.fnSubmit)) {
      this._messagepnl.submitEvent.subscribe(oArgs.fnSubmit);
    } else {
      this._messagepnl.submitEvent.subscribe(function() {
      });
    }
  },
  construct : function(o) {
    var handleSubmit = function() {
      this.submit();
    };
    this._messagepnl = new Y.widget.SimpleDialog("messagepnl", {
      width : "40em",
      fixedcenter : true,
      close : true,
      draggable : false,
      zindex : 6,
      modal : true,
      constraintoviewport : true,
      monitorresize : true,
      visible : false,
      buttons : [ {
        text : "Cerrar",
        handler : handleSubmit,
        isDefault : true
      } ]

    });
    var kw = new Y.util.KeyListener(document, {
      keys : 27
    }, {
      fn : this._messagepnl.hide,
      scope : this._messagepnl,
      correctScope : true
    });
    kw.enable();
    this._messagepnl.setHeader('<font color="red">DETALLE ERROR</font>');
    this._messagepnl.render(document.body);
  }
};
