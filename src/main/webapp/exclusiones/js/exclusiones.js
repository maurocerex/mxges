function banderaCambia(select){
    var f = getForm("camposform");
	if(select.selectedIndex != -1){
		f.elements['bandera'].value = true;
	}
}


function banderaCtCambia(select){
	if(select.selectedIndex != -1){
		document.getElementById("banderaCt").value = true;
	}
}

function buscarProcesos(){
    try {
    

        var elSel = document.getElementById("proceso");
        var lob = document.getElementById("lob").value;
        
        
        var callback = new Y.AsyncCallBack();
        callback.processResult = function(messages){
            clearAndFillSelect(messages.select, elSel, '');
            elSel.disabled = false;
            Y.btnBuscar.set("disabled", false);
        };
        
        var sUrl = Y.Exclusiones.ProcesosURL;
        var postData = "lob=" + lob;
        var request = Y.util.Connect.asyncRequest('POST', sUrl, callback, postData);
        
    } catch (x) {
        alert("Y.Exclusiones.BuscaProcesos :" + x.description);
    }
}

function habilitaReten(proceso){

    var r1 = document.getElementById("retenedor");

    if (proceso.value == '') {
        r1.disabled = true;
    } else {
        r1.disabled = false;
    }
}

function limpia(){
    var r1 = document.getElementById("retenedor");
    r1.value = '';
}


function consultar(){
    try {
    
        
        var selDisponibles = document.getElementById("disponibles");
        var selSeleccionados = document.getElementById("seleccionados");
        var selDisponiblesCt = document.getElementById("disponiblesct");
        var selSeleccionadosCt = document.getElementById("seleccionadosct");
        
        
        var lob = document.getElementById("lob").value;
        var proceso = document.getElementById("proceso").value;
        var retenedor = document.getElementById("retenedor").value;
        var mEn=document.getElementById("tipoEnvio").value
        var callback = new Y.AsyncCallBack();
        callback.processResult = function(messages){
            clearAndFillSelectWS(messages.selectDisponibles, selDisponibles);
            clearAndFillSelectWS(messages.selectSeleccionados, selSeleccionados);
            clearAndFillSelectWS(messages.selectDisponiblesCt, selDisponiblesCt);
            clearAndFillSelectWS(messages.selectSeleccionadosCt, selSeleccionadosCt);
            document.getElementById("retenedor").value = messages.descripcion;
        };
        showWait();
        var sUrl = Y.Exclusiones.BusquedaURL;
        var postData = "lob=" + lob + "&proceso=" + proceso + "&retenedor=" + retenedor + "&tipoEnvio=" + mEn;
        var request = Y.util.Connect.asyncRequest('POST', sUrl, callback, postData);
        
        
        
    } catch (x) {
        alert("Y.Exclusiones.BuscaProcesos :" + x.description);
    }
}

function guardar(){
    try {
    
        var f = document.getElementById("camposform");
        var bus = document.getElementById("formBusqueda");
        f.elements['lob'].value = bus.elements['lob'].value;
        f.elements['proceso'].value = bus.elements['proceso'].value;
        document.getElementById("medioEnvio").value=document.getElementById("tipoEnvio").value
        seleccionadosInput = document.getElementById("seleccionados");
        seleccionadosCtInput =  document.getElementById("seleccionadosct");
        FM.selectAllOptions(seleccionadosInput);
        FM.selectAllOptions(seleccionadosCtInput);
        var callback = new Y.AsyncCallBack();
        callback.processResult = function(messages){
            var msg = '<div><center>';
            msg += 'Los cambios se guardaron correctamente.';
            msg += '</center></div>';
            new Y.MessagePnl().showmsg({
                msg: msg,
                fnSubmit: function(){
                    var form = getMainRedirectForm();
                    var actionUrl = Y.redirects['exclusiones'];
                    form.action = actionUrl;
                    form.submit();
                }
            });
        };
        showWait();
        Y.util.Connect.setForm(f);
        var request = Y.util.Connect.asyncRequest('POST', Y.Exclusiones.Guardar, callback);
        
    } catch (x) {
        alert("Y.Exclusiones.guardar :" + x.description);
    }
}

Y.util.Event.onDOMReady(function(){

    Y.btnBuscar = new Y.widget.Button("buscar", {
        onclick: {
            fn: function(){
                consultar();
            }
        },
        disabled: false
    });
    
    Y.btnGuardar = new Y.widget.Button("guardar", {
        onclick: {
            fn: function(){
                //buttonBusquedaClick();
                guardar();
            }
        },
        disabled: false
    });
    
    Y.btnAdd = new Y.widget.Button("addbutton", {
        onclick: {
            fn: function(){
                var f = getForm("camposform");
                if (f.elements['bandera'].value == "true" ||f.elements['bandera'].value == true) {
                    add();
					f.elements['bandera'].value = false;
                } else {
					var msg = '<div><center>';
                    msg += 'Debe seleccionar al menos un registro.';
                    msg += '</center></div>';
                    new Y.MessagePnl().showmsg({
                        msg: msg,
                        fnSubmit: function(){
                        }
                    });
                }
            }
        },
        disabled: false
    });
    
    Y.btnRem = new Y.widget.Button("removebutton", {
        onclick: {
            fn: function(){
				var f = getForm("camposform");
                if (f.elements['bandera'].value == "true" ||f.elements['bandera'].value == true) {
                    remove();
					f.elements['bandera'].value = false;
                } else {
					var msg = '<div><center>';
                    msg += 'Debe seleccionar al menos un registro.';
                    msg += '</center></div>';
                    new Y.MessagePnl().showmsg({
                        msg: msg,
                        fnSubmit: function(){
                        }
                    });
                }
            }
        },
        disabled: false
    });
    
    Y.btnAddAll = new Y.widget.Button("addAllbutton", {
        onclick: {
            fn: function(){
                    addall();
            }
        },
        disabled: false
    });
    
    Y.btnRemAll = new Y.widget.Button("remAllbutton", {
        onclick: {
            fn: function(){
                    removeall();
            }
        },
        disabled: false
    });
    
    
    
    Y.btnAddCt = new Y.widget.Button("addbuttonct", {
        onclick: {
            fn: function(){
                
                if (document.getElementById("banderaCt").value == "true" ||document.getElementById("banderaCt").value == true) {
                    addCt();
                    document.getElementById("banderaCt").value = false;
                } else {
					var msg = '<div><center>';
                    msg += 'Debe seleccionar al menos una opci&oacute;n.';
                    msg += '</center></div>';
                    new YPLAD.MessagePnl().showmsg({
                        msg: msg,
                        fnSubmit: function(){
                        }
                    });
                }
            }
        },
        disabled: false
    });
    
    Y.btnRemCt = new Y.widget.Button("removebuttonct", {
        onclick: {
            fn: function(){
                if (document.getElementById("banderaCt").value == "true" ||document.getElementById("banderaCt").value == true) {
                    removeCt();
                    document.getElementById("banderaCt").value = false;
                } else {
					var msg = '<div><center>';
                    msg += 'Debe seleccionar al menos una opci&oacute;n.';
                    msg += '</center></div>';
                    new YPLAD.MessagePnl().showmsg({
                        msg: msg,
                        fnSubmit: function(){
                        }
                    });
                }
            }
        },
        disabled: false
    });
    
    Y.btnAddAllCt = new Y.widget.Button("addAllbuttonct", {
        onclick: {
            fn: function(){
                    addallCt();
            }
        },
        disabled: false
    });
    
    Y.btnRemAllCt = new Y.widget.Button("remAllbuttonct", {
        onclick: {
            fn: function(){
                    removeallCt();
            }
        },
        disabled: false
    });
    
    
    
    
});
