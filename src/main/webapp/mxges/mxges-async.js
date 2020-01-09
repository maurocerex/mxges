function asyncProcessUnsuccess(messages) {
  try {
    if (messages.redirect != undefined) {
      window.location = Y.ctx + messages.redirect;
    }
  } catch (e) {
    alert("asyncProcessUnsuccess: " + e.description);
  }
}

function asyncProcessDivs(messages) {
  if (Y.lang.isArray(messages.divs)) {
    for ( var j = 0; j < messages.divs.length; j++) {
      var m;
      try {
        m = messages.divs[j];
        publishMsg(m.div, m.msg)
      } catch (e) {
        var txt = " m = [" + m + "]\n";
        txt += " m.div = [" + m.div + "] \n";
        txt += " m.msg = [" + m.msg + "] \n";
        txt += " e.description = [" + e.description + "]\n";
        alert("asyncProcessDivs:" + txt);
      }
    }
  }
}

function asyncProcessAlerts(messages) {
  if (Y.lang.isArray(messages.alerts)) {
    for ( var j = 0; j < messages.alerts.length; j++) {
      try {
        var m = messages.alerts[j];
        if (m.msg != undefined) {
          alert(m.msg);
        }
      } catch (e) {
        alert("asyncProcessAlerts:" + e.description);
      }
    }
  }
}

function asyncProcessException(messages) {
  try {
    if (Y.lang.isString(messages.exception)) {
      showException(messages.exception);
    }
  } catch (e) {
    alert("asyncProcessException:" + e.description);
  }
}

function asyncShowError(error) {
  try {
    if (Y.lang.isString(error)) {
      showException(error);
    }
  } catch (e) {
    alert("asyncShowError:" + e.description);
  }
}

function asyncProcessTabs(tabView, messages) {
  try {
    if (tabView == undefined) {
      alert("tabView undefined!");
      return false;
    }
    var tabs = tabView.get('tabs');
    for ( var i = 0, len = tabs.length; i < len; i++) {
      tab = tabs[i];
      var colorate = asyncIterateTabs(messages.tabs, tab);
      if (colorate == true) {
        Y.util.Dom.addClass(tab, "taberror");
      } else {
        if (Y.util.Dom.hasClass(tab, 'taberror')) {
          Y.util.Dom.removeClass(tab, "taberror");
        }
      }
    }
  } catch (e) {
    alert("asyncProcessTabs:" + e.description);
  }
}

function asyncIterateTabs(messagesTabs, tabEl) {
  var found = false;
  if (Y.lang.isArray(messagesTabs) == false) {
    return false; // Se elimina el color rojo del tab.
  }
  for ( var j = 0; j < messagesTabs.length; j++) {
    try {
      var m = messagesTabs[j];
      if (m.tab != undefined) {
        conEl = tabEl.get('contentEl');
        if (Y.util.Dom.hasClass(conEl, m.tab)) {
          found = true;
        }
      }
    } catch (e) {
      alert("asyncIterateTabs:" + e.description);
    }
  }
  return found;
}

function asyncProcessFormValues(formName, messages) {
  try {
    if (formName == undefined) {
      alert("formName undefined!");
      return false;
    }
    var targetForm = getForm(formName);
    if (targetForm == undefined) {
      return false;
    }
    if (Y.lang.isArray(messages.values)) {
      for ( var j = 0; j < messages.values.length; j++) {
        var msg = messages.values[j];
        var elementName = msg.name;
        var newValue = msg.value;
        var targetElement = targetForm.elements[elementName];
        if (targetElement.length == undefined) {
          targetElement.value = newValue;
        } else {
          for (i = 0; i < targetElement.length; i++) {
            targetElement[i].value = newValue;
          }
        }
      }
    }
  } catch (e) {
    alert("asyncProcessFormValues:" + e.description);
  }
}

/**
 * AsyncCallBack para llamadas a metodos que regresan resultados en formato
 * JSON.
 */
Y.AsyncCallBack = function() {
}
Y.AsyncCallBack.prototype = {
  success : function(o) {
    try {

      if (Y.WaitPnl.cfg.getProperty("visible")) {
        hideWait();
      }

      messages = Y.lang.JSON.parse(o.responseText);

      if (Y.lang.isBoolean(messages.success)) {

        asyncProcessDivs(messages);
        if (this._tabView != null) {
          asyncProcessTabs(this._tabView, messages);
        }
        if (this._formName != null) {
          asyncProcessFormValues(this._formName, messages);
        }

        asyncProcessAlerts(messages);

        asyncProcessException(messages);
        if (messages.success == true) {
          this.processResult(messages);
        } else {
          this.processResultUnsuccess(messages);
          asyncProcessUnsuccess(messages);
        }
      } else {
        alert('messages.success no valido o no es boolean: ' + messages.success);
      }
    } catch (x) {
      alert("AsyncCallBack (success): " + x.description);
    }
  },
  failure : function(o) {
    if (!Y.util.Connect.isCallInProgress(o)) {
      if (Y.WaitPnl.cfg.getProperty("visible")) {
        hideWait();
      }
      asyncShowError("Error (f): " + parseErrorMsg(o));
    }
  },
  upload : function(o) {
    try {

      if (Y.WaitPnl.cfg.getProperty("visible")) {
        hideWait();
      }

      messages = Y.lang.JSON.parse(o.responseText);

      if (Y.lang.isBoolean(messages.success)) {

        asyncProcessDivs(messages);
        if (this._tabView != null) {
          asyncProcessTabs(this._tabView, messages);
        }
        if (this._formName != null) {
          asyncProcessFormValues(this._formName, messages);
        }

        asyncProcessAlerts(messages);

        asyncProcessException(messages);
        if (messages.success == true) {
          this.processResult(messages);
        } else {
          this.processResultUnsuccess(messages);
          asyncProcessUnsuccess(messages);
        }
      } else {
        alert('messages.success no valido o no es boolean: ' + messages.success);
      }
    } catch (x) {
      alert("AsyncCallBack (success): " + x.description);
    }
  },
  argument : null,
  cache : false,
  processResult : function(messages) {
    alert('overrideme!...');
  },
  processResultUnsuccess : function(messages) {
  },
  _tabView : null,
  setTabView : function(tabView) {
    this._tabView = tabView;
  },
  _formName : null,
  setFormName : function(formName) {
    this._formName = formName;
  }
}