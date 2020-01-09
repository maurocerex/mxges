/*
     Initialize and render the MenuBar when the page's DOM is ready 
     to be scripted.
 */

Y.util.Event.onDOMReady(function() {
  
  var onClicAyuda = function(p_sType, p_aArgs, p_oValue) {
    try {

      var callback = new Y.AsyncCallBack();
      callback.processResult = function(messages) {

        if (messages.flag == true) {
          var url = Y.ctx + '/ayuda/' + messages.page;
          var my_window = window.open(url, "pladayuda", "width=1100,height=500,menubar=no,scrollbars=yes,resizable=yes", "true");
        } else {
          var msg = '<div><center>';
          msg += 'Este elemento no tiene definido un archivo de ayuda.';
          msg += '</center></div>';
          new Y.MessagePnl().showmsg( {
            msg : msg,
            fnSubmit : null
          });
        }
      }

      var f = getForm("ayudaredirect");
      var url = Y.redirects['ayudaasync'] + "?ddd=" + new Date().getTime();
      Y.util.Connect.setForm(f);
      var request = Y.util.Connect.asyncRequest('POST', url, callback);

    } catch (e) {
      alert("onClicAyuda: " + e.description);
    }
  };  
  
    var onPladMenuItemClickIgnore = function() {
    };

    var onMenuItemClick = function() {
        redirect(this.cfg.getProperty("text"));
    };

    var onPladMenuItemClick = function(p_sType, p_aArgs, p_oValue) {
        try {
            var form = getMainRedirectForm();
            var actionUrl = Y.redirects[p_oValue];
            if (actionUrl == undefined || actionUrl == null) {
                alert("Action URL no definida para  " + p_oValue + " en onPladMenuItemClick");
            } else {
                form.action = actionUrl;
                form.submit();
            }
        } catch (e) {
            alert("onPladMenuItemClick " + e.description);
        }
    };

    var onPladMenuItemClickPnl = function(p_sType, p_aArgs, p_oValue) {
        if ("acerca" == p_oValue) {
            if (Y.panel301 == undefined) {
                Y.panel301 = new Y.widget.Panel("panel301", {
                    xy : [ 100, 100 ],
                    constraintoviewport : true,
                    fixedcenter : false,
                    width : "400px",
                    zIndex : 1
                });
                Y.panel301.setHeader("Acerca de PLAD");
                Y.panel301.setBody("PLAD Versi&oacute;n " + Y.version + " <br> Ultima actualizaci&oacute;n " + Y.ultimaact);
                Y.panel301.render(document.body);
            }
            Y.panel301.show();

        } else if ("team" == p_oValue) {
            if (Y.panel305 == undefined) {
                Y.panel305 = new Y.widget.Panel("panel305", {
                    xy : [ 200, 200 ],
                    constraintoviewport : true,
                    fixedcenter : false,
                    width : "400px",
                    zIndex : 1
                });
                Y.panel305.setHeader("Team Info");
                Y.panel305.setBody("MELTSAN SOLUTIONS");
                Y.panel305.render(document.body);
            }
            Y.panel305.show();

        } else {
            alert("Opcion " + p_oValue + " no definida en onPladMenuItemClickPnl.");
        }
    };

    /*
     * Define an array of object literals, each containing the data necessary to
     * create the items for a MenuBar.
     */

    var aItemData = null;

    try {
        var callback = new Y.AsyncCallBack();
        callback.processResult = function(messages) {
            var rows = messages.ResultSet;
            if (Y.lang.isArray(rows)) {
            	asignarFnMenu(rows);
            }
            
            var fr = getForm("ayudaredirect");
            if (fr){
              if (fr.elements["pladActionId"].value == ""){
                
              }/*else {
                var ayuda = {text:"<em id=\"ayuda\">Ayuda</em>",onclick:{fn:onClicAyuda, obj:"ayuda"},disabled:false};
                rows.push(ayuda);
              }*/
            }
            

            
            aItemData = rows;
            
            var oMenuBar = new Y.widget.MenuBar("mymenubar", {
                lazyload : true,
                itemdata : aItemData
            });

            /*
             * Since this MenuBar instance is built completely from script, call
             * the "render" method passing in a node reference for the DOM
             * element that its should be appended to.
             */

            oMenuBar.render(document.body);

            // Add a "show" event listener for each submenu.

            function onSubmenuShow() {
                var oIFrame, oElement, nOffsetWidth;

                // Keep the left-most submenu against the left edge of the
                // browser
                // viewport

                if (this.id == "yahoo") {

                    Y.util.Dom.setX(this.element, 0);
                    oIFrame = this.iframe;
                    if (oIFrame) {
                        Y.util.Dom.setX(oIFrame, 0);
                    }
                    this.cfg.setProperty("x", 0, true);
                }

                /*
                 * Need to set the width for submenus of submenus in IE to
                 * prevent the mouseout event from firing prematurely when the
                 * user mouses off of a MenuItem's text node.
                 */

                if ((this.id == "filemenu" || this.id == "editmenu") && Y.env.ua.ie) {
                    oElement = this.element;
                    nOffsetWidth = oElement.offsetWidth;

                    /*
                     * Measuring the difference of the offsetWidth before and
                     * after setting the "width" style attribute allows us to
                     * compute the about of padding and borders applied to the
                     * element, which in turn allows us to set the "width"
                     * property correctly.
                     */

                    oElement.style.width = nOffsetWidth + "px";
                    oElement.style.width = (nOffsetWidth - (oElement.offsetWidth - nOffsetWidth)) + "px";
                }
            }

            // Subscribe to the "show" event for each submenu

            oMenuBar.subscribe("show", onSubmenuShow);
            
        };

        var request = Y.util.Connect.asyncRequest('POST', Y.redirects['menuasync'], callback);
    } catch (e) {
        alert("menu: " + e.description);
    }
    
    function asignarFnMenu(menuArr){
        for(var j = 0; j < menuArr.length; j++) {
            var node = menuArr[j];
            if(node.submenu){
                asignarFnMenu(node.submenu.itemdata);
            }else{
                node.onclick.fn = onPladMenuItemClick;
            }
        }
    }

});

Y.util.Event.onDOMReady(function() {
  try {
    
    Y.AyudaPnl = new Y.widget.Panel("ayudadlg", {
      width : "95%",
      fixedcenter : true,
      close : true,
      draggable : true,
      zindex : 5,
      modal : false,
      visible : false
    });
    var kl = new Y.util.KeyListener(document, {
      keys : 27
    }, {
      fn : Y.AyudaPnl.hide,
      scope : Y.AyudaPnl,
      correctScope : true
    });
    kl.enable();
    Y.AyudaPnl.render(document.body);
  } catch (e) {
    alert("AyudaPnl: " + e.description);
  }
});



