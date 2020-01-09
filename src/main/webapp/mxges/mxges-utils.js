
/**
 * Reemplaza los elementos option de un input select
 * @param jsonArray - Arreglo en nomenclatura JSON
 * [
   {
      "clave":"CLAVE1",
      "valor":"TEXTO CLAVE 1"
   },
   {
      "clave":"CLAVE2",
      "valor":"TEXTO CLAVE 2"
   }
   ]
 * @param elSel - input select
 * @param text1st - valor para el primer elemento, donde el text sera una cadena vacia
 * @return
 */
function clearAndFillSelect(jsonArray, elSel, text1st) {
  try {
    if (Y.lang.isArray(jsonArray) == false) {
      alert('Result is not an array!');
    }
    if (elSel == undefined) {
      alert('elSel is undefined!');
    }

    var elOptNew;
    for (i = elSel.length - 1; i >= 0; i--) {
      elSel.remove(i);
    }

    elSel.options.add(new Option(text1st, ''));

    for ( var j = 0; j < jsonArray.length; ++j) {
      elOptNew = new Option(jsonArray[j].valor, jsonArray[j].clave);
      elSel.options.add(elOptNew);
    }
    if (jsonArray.length == 1) {
      elSel.options[1].selected = true;
    }
    // elSel.disabled = false;
  } catch (e) {
    alert("clearAndFillSelect:" + e.description);
  }
}


Y.clearAndFillSelect = clearAndFillSelect;

function clearAndFillSelectWS(jsonArray, elSel) {
	  try {
	    if (Y.lang.isArray(jsonArray) == false) {
	      alert('Result is not an array!');
	    }
	    if (elSel == undefined) {
	      alert('elSel is undefined!');
	    }

	    var elOptNew;
	    for (i = elSel.length - 1; i >= 0; i--) {
	      elSel.remove(i);
	    }
	    for ( var j = 0; j < jsonArray.length; ++j) {
	      elOptNew = new Option(jsonArray[j].valor, jsonArray[j].clave);
	      elSel.options.add(elOptNew);
	    }
	    
	    // elSel.disabled = false;
	  } catch (e) {
	    alert("clearAndFillSelectWS:" + e.description);
	  }
	}


	Y.clearAndFillSelectWS = clearAndFillSelectWS;
	

function clearSelect(elSel) {
  try {
    for (i = elSel.length - 1; i >= 0; i--) {
      elSel.remove(i);
    }
    //elSel.disabled = true;
  } catch (e) {
    alert("clearSelect:" + e.description);
  }
}


Y.clearSelect = clearSelect;

/**
 * Regresa el text del input select, que esta seleccionado
 * 
 * @param form - objecto form
 * @param name - nombre del input select
 * @return
 */

function getTextFromSelect(form, name) {
  try {
    var select = form.elements[name];
    if (select == undefined) {
      alert("No se encontro el select:" + name);
      return "";
    }
    if (select.selectedIndex < 0) {
      return "";
    } else {
      return select.options[select.selectedIndex].text;
    }
  } catch (x) {
    alert("error en getTextFromSelect:" + x.description + " name: " + name);
  }
}


Y.getTextFromSelect = getTextFromSelect;

/**
 * Regresa el texto de la opcion seleccionada de un input select
 * @param select - objecto input select
 */
Y.getSelectedText = function (select) {
  try {
    if (select == undefined) {
      alert("Objecto select undefined");
      return "";
    }
    if (select.selectedIndex < 0) {
      return "";
    } else {
      return select.options[select.selectedIndex].text;
    }
  } catch (x) {
    alert("Y.getSelectedText:" + x.description );
  }
};



/**
 * Regresa el objecto form
 * @param formName - nombre del formulario a buscar en el documento
 * @return
 */
function getForm(formName) {
  try {
    var form = document.getElementById(formName);
    if (form == undefined) {
      alert("No se encontro el formulario:" + formName);
    }
    return form;
  } catch (x) {
    alert("error en getForm:" + x.description);
  }
}

/**
 * Copia los elementos de un formulario source a un form destino
 * @param source - objecto form source
 * @param target - objecto form destino
 * @param fields - array de nombres de los elementos a copias
 * @param sourceprefield - prefijo para los elementos del form source
 * @param targetprefield - prefijo para los elementos del form destino
 * @return
 */
function copyFields(source, target, fields, sourceprefield, targetprefield) {
  try {
    for ( var i = 0; i < fields.length; i++) {
      target.elements[targetprefield + fields[i]].value = source.elements[sourceprefield + fields[i]].value;
    }
  } catch (e) {
    alert("copyFields:" + e.description);
  }
}

Y.copyFields = copyFields;

/**
 * Compara los elementos del form f1 versus form f2
 * @param f1 - objecto form 1
 * @param f2 - objecto form 2
 * @param fields - array de nombres de los elementos a copias
 * @param pre1 - prefijo para los elementos del form 1
 * @param pre2 - prefijo para los elementos del form 2 
 * @return false si existe al menos una diferencia en los valores
 */
Y.allFieldsEquals = function(f1, f2, fields, pre1, pre2) {
  try {
    var allEquals = true;
    for ( var i = 0; i < fields.length; i++) {
      if (f1.elements[pre1 + fields[i]].value == f2.elements[pre2 + fields[i]].value) {
      } else {
        allEquals = false;
      }
    }
    return allEquals;
  } catch (e) {
    alert("Y.allFieldsEquals: " + e.description);
  }
};


/**
 * Reemplaza el innerHTML de un elemento
 * @param divId - id del elemento dentro del document
 * @param message - texto que se publicara en el document
 * @return
 */
function publishMsg(divId, message) {
  try {
    if (message != undefined) {
      var div = document.getElementById(divId);
      if (div != undefined) {
        if (message == '') {
          div.style.visibility = "hidden";
          if (div.style.lineHeight != undefined) {
            div.style.lineHeight = "0%";
          }
        } else {
          div.style.visibility = "visible";
          div.innerHTML = message;
          if (div.style.lineHeight != undefined) {
            div.style.lineHeight = "100%";
          }
        }
      } else {
        alert(divId + " no existe en el documento.");
      }
    }
  } catch (e) {
    txt = " message = [" + message + "] \n";
    txt += " divId = [" + divId + "] \n";
    txt += " e.description = [" + e.description + "]\n";
    alert("publishMsg:" + txt);
  }
}

Y.publishMsg = publishMsg;

/**
 * Se traduce el error, si esta identificado
 * @param error
 * @return
 */
function traducirError(error) {
  try {
    if (error == undefined) {
      return "Error desconocido";
    }
    if (error.indexOf('CannotGetJdbcConnectionException') != -1) {
      return "Base de Datos no disponible"
    }
    return error;
  } catch (e) {
    alert("traducirError: " + e.description);
  }
}

/**
 * Parsea el resultado de una peticion asincrona, traducira el error si esta identificado
 * @param oResponse
 * @return
 */
function parseErrorMsg(oResponse) {

  try {
    var debug = false;
    if (debug) {
      alert("oResponse.error:" + oResponse.error);
      alert("oResponse.tId:" + oResponse.tId);
      alert("oResponse.status:" + oResponse.status);
      alert("oResponse.statusText:" + oResponse.statusText);
      alert("oResponse.getResponseHeader:" + oResponse.getResponseHeader);
      alert("oResponse.getAllResponseHeaders:" + oResponse.getAllResponseHeaders);
      alert("oResponse.responseText:" + oResponse.responseText);
      alert("oResponse.responseXML:" + oResponse.responseXML);
    }

    if (Y.lang.isArray(oResponse)) {
      var dump = "<br/>[";
      dump += Y.lang.dump(oResponse);
      dump += "]";
      if (oResponse.statusText) {
        if (oResponse.statusText.indexOf('communication failure') != -1) {
          return "Servicio no disponible";
        } else {
          return oResponse.statusText + dump;
        }
      }
    } else {
      var msg = "";
      if (oResponse.status) {
        msg += "<br/> Estatus: " + oResponse.status;
        if (oResponse.status == '404') {
          msg += " La pagina solicitada no existe"
        }
      }
      if (oResponse.statusText) {
        if (oResponse.statusText.indexOf('communication failure') != -1) {
          return "Servicio no disponible";
        } else {
          msg += "<br/> [ " + oResponse.statusText + " ]";
        }

      }
      return msg;
    }
  } catch (e) {
    alert("parseErrorMsg: " + e.description);
  }
}

/**
 * Reemplaza el contenido de exceptiondiv
 * 
 * @param html
 * @return
 */
function publishExceptionDiv(html) {
  try {
    var container1 = document.getElementById('exceptiondiv');
    container1.innerHTML = html;
  } catch (e) {
    alert("publishExceptionDiv: " + e.description);
  }
}

/**
 * Limpia el contenido de exceptiondiv
 * 
 * @return
 */
function clearExceptionDiv() {
  try {
    var container1 = document.getElementById('exceptiondiv');
    container1.innerHTML = '';
  } catch (e) {
    alert("clearExceptionDiv: " + e.description);
  }

}

/**
 * http://bytes.com/topic/javascript/answers/145532-replace-french-characters-form-input
 */
function stripVowelAccent(inputElement) {
  try {
    var s = inputElement.value;

    var rExps = [ /[\xC0-\xC2]/g, /[\xE0-\xE2]/g, /[\xC8-\xCA]/g, /[\xE8-\xEB]/g, /[\xCC-\xCE]/g, /[\xEC-\xEE]/g, /[\xD2-\xD4]/g,
        /[\xF2-\xF4]/g, /[\xD9-\xDB]/g, /[\xF9-\xFB]/g ];

    var repChar = [ 'A', 'a', 'E', 'e', 'I', 'i', 'O', 'o', 'U', 'u', 'N', 'n' ];

    for ( var i = 0; i < rExps.length; i++) {
      s = s.replace(rExps[i], repChar[i]);
    }

    inputElement.value = s;

  } catch (x) {
    alert("error en stripVowelAccent:" + x.description);
  }
}

/////////////////////////////////////////////////////////////////////////////
//
// PLAD CALENDAR
//
/////////////////////////////////////////////////////////////////////////////
Y.PladCalendar = function() {

};

Y.PladCalendar.prototype = {
  BUTTON_ID : null,
  SHORT_DATE_ID : null,
  LARGE_DATE_ID : null,
  DIALOG_ID : null,
  CALENDAR_ID : 'pladcalendarid',
  DIALOG_TITTLE : null,
  DIALOG : null,
  CALENDAR : null,
  MINDATE : '1/1/2010',
  CLOSE_EVENT : null,
  NAV_CONFIG : false,

  setButtonId : function(elementId) {
    this.BUTTON_ID = elementId;
  },
  setShortDateId : function(elementId) {
    this.SHORT_DATE_ID = elementId;
  },
  setLargeDateId : function(elementId) {
    this.LARGE_DATE_ID = elementId;
  },
  setDialogId : function(elementId) {
    this.DIALOG_ID = elementId;
  },
  setCalendarId : function(elementId) {
    this.CALENDAR_ID = elementId;
  },
  setDialogTitle : function(text) {
    this.DIALOG_TITTLE = text;
  },
  setMinDate : function(text) {
    this.MINDATE = text;
  },
  setCloseEvent : function(fn) {
    this.CLOSE_EVENT = fn;
  },  
  setNavConfig : function(text) {
    this.NAV_CONFIG = text;
  }, 
  construct : function() {
    var Event = Y.util.Event, Dom = Y.util.Dom;

    if (!this.BUTTON_ID) {
      alert("BUTTON_ID is undefined");
      return;
    }
    if (!this.SHORT_DATE_ID) {
      alert("SHORT_DATE_ID is undefined");
      return;
    }
    if (!this.LARGE_DATE_ID) {
      alert("LARGE_DATE_ID is undefined");
      return;
    }
    if (!this.DIALOG_ID) {
      alert("DIALOG_ID is undefined");
      return;
    }
    if (Dom.get(this.BUTTON_ID) == null) {
      alert("BUTTON_ID: El elemento " + this.BUTTON_ID + " no existe en el documento");
      return;
    }
    if (Dom.get(this.SHORT_DATE_ID) == null) {
      alert("SHORT_DATE_ID: El elemento " + this.SHORT_DATE_ID + " no existe en el documento");
      return;
    }
    if (Dom.get(this.DIALOG_ID) == null) {
      alert("DIALOG_ID: El elemento " + this.DIALOG_ID + " no existe en el documento");
      return;
    }
    if (Dom.get(this.LARGE_DATE_ID) == null) {
      alert("LARGE_DATE_ID: El elemento " + this.LARGE_DATE_ID + " no existe en el documento");
      return;
    }

    var _button_id = this.BUTTON_ID;
    var _short_date_id = this.SHORT_DATE_ID;
    var _large_date_id = this.LARGE_DATE_ID;
    var _dialog_id = this.DIALOG_ID;
    var _calendar_id = this.CALENDAR_ID;
    var _dialog_title = this.DIALOG_TITTLE;
    var _dialog = this.DIALOG;
    var _calendar = this.CALENDAR;
    var _mindate = this.MINDATE;
    var _close_event = this.CLOSE_EVENT;
    var showBtn = Dom.get(_button_id);
	var _navConfig = this.NAV_CONFIG;
	
    Event.on(showBtn, "click", function() {
      
      if (!_dialog) {
        Event.on(document, "click", function(e) {
          var el = Event.getTarget(e);
          var dialogEl = _dialog.element;
          if (el != dialogEl && !Dom.isAncestor(dialogEl, el) && el != showBtn && !Dom.isAncestor(showBtn, el)) {
            _dialog.hide();
          }
        });
        function resetHandler() {
          Dom.get(_large_date_id).value = "";
          Dom.get(_short_date_id).value = "";
          _dialog.hide();
        }
        function closeHandler() {
          _dialog.hide();
        }
        _dialog = new Y.widget.Dialog(_dialog_id, {
          visible : false,
          context : [ _button_id, "tl", "bl" ],
          buttons : [ {
            text : "Limpiar",
            handler : resetHandler
          }, {
            text : "Cerrar",
            handler : closeHandler,
            isDefault : true
          } ],
          draggable : false,
          close : true,
          constraintoviewport : true,
          zIndex : 9000
        });
        _dialog.setHeader(_dialog_title);
        _dialog.setBody('<div id="' + _calendar_id + '" style="border:none;padding:1em;"></div>');
        _dialog.render(document.body);
        _dialog.showEvent.subscribe(function() {
          if (Y.env.ua.ie) {
            _dialog.fireEvent("changeContent");
          }
        });
        if (_close_event == null) {
        } else {
          if (Y.lang.isFunction(_close_event)) {
            _dialog.hideEvent.subscribe( _close_event );
          } else {
            alert("_close_event no es una funcion.");
          }
        }        
      }
      if (!_calendar) {
	  	if (_navConfig) {
			var navConfig = {
                    strings : {
                        month: "Elija un mes",
                        year: "Elija un a&ntilde;o",
                        submit: "Aceptar",
                        cancel: "Cancelar",
                        invalidYear: "Por favor elija un a&ntilde;o valido"
                    },
                    monthFormat: Y.PladCalendar.SHORT_DATE_ID,
                    initialFocus: "year"
              };
			_calendar = new Y.widget.Calendar(_calendar_id, {
				iframe: false,
				hide_blank_weeks: true,
				mindate: _mindate,
				navigator: navConfig
			});
		} else {
			_calendar = new Y.widget.Calendar(_calendar_id, {
				iframe: false,
				hide_blank_weeks: true,
				mindate: _mindate
			});
		}

        _calendar.cfg.setProperty("MONTHS_SHORT", [ "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" ]);
        _calendar.cfg.setProperty("MONTHS_LONG", [ "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
            "Octubre", "Noviembre", "Diciembre" ]);
        _calendar.cfg.setProperty("WEEKDAYS_SHORT", [ "Do", "Lu", "Ma", "Mi", "Ju", "Vi", "Sa" ]);
        _calendar.cfg.setProperty("WEEKDAYS_MEDIUM", [ "Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab" ]);
        _calendar.cfg.setProperty("WEEKDAYS_LONG", [ "Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado" ]);
        _calendar.render();

        _calendar.selectEvent.subscribe(function() {
          if (_calendar.getSelectedDates().length > 0) {
            var selDate = _calendar.getSelectedDates()[0];
            var wStr = _calendar.cfg.getProperty("WEEKDAYS_LONG")[selDate.getDay()];
            var dStr = selDate.getDate();
            var mStr = _calendar.cfg.getProperty("MONTHS_LONG")[selDate.getMonth()];
            var yStr = selDate.getFullYear();
            var mesStr = _calendar.cfg.getProperty("MONTHS_SHORT")[selDate.getMonth()];
            try {
              Dom.get(_large_date_id).value = wStr + ", " + dStr + " " + mStr + " " + yStr;
              Dom.get(_short_date_id).value = selDate.getDate() + "/" + mesStr + "/" + selDate.getFullYear();
            } catch (e) {
              alert(e.description);
            }
          } else {
            Dom.get(_large_date_id).value = "";
          }
          _dialog.hide();
        });

        _calendar.renderEvent.subscribe(function() {
          _dialog.fireEvent("changeContent");
        });
      }

      var seldate = _calendar.getSelectedDates();

      if (seldate.length > 0) {
        _calendar.cfg.setProperty("pagedate", seldate[0]);
        _calendar.render();
      }

      _dialog.show();

    });

  }

};
