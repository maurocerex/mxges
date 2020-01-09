function habilitaDia(dia){
	var f = getForm("FrecuenciaForm");
	var hh = dia.name+'hh';
	var mm = dia.name+'mm';
	if(dia.checked){
		f.elements[hh].disabled=false;
		f.elements[mm].disabled=false;
	}else{
		f.elements[hh].disabled=true;
		f.elements[mm].disabled=true;
	}
	
}


function getIdHUBChannel(valLabel){
	
	var combo = Y.Notificaciones.CanalCatalog;
    for (var i = 0; i < combo.length; i++) {
        if (valLabel == combo[i].label) {
            return combo[i].value;
        }
    }
}


function getLabelHUBChannel(valID){
	
	var combo = Y.Notificaciones.CanalCatalog;
    for (var i = 0; i < combo.length; i++) {
        if (valID == combo[i].value) {
            return combo[i].label;
        }
    }
}


function initDtblRegistros(){

    Y.Notificaciones.StatusEditor = new YAHOO.widget.DropdownCellEditor({
        dropdownOptions: Y.Notificaciones.VigenciaCatalog,
        disableBtns: false,
        LABEL_SAVE: "Aceptar",
        LABEL_CANCEL: "Cancelar"
    });
    
    Y.Notificaciones.MedioFmt = function(cell, row, col, data){
        var combo = Y.Notificaciones.MedioCatalog;
        for (var i = 0; i < combo.length; i++) {
            if (data == combo[i].value) {
                cell.innerHTML = combo[i].label;
                return;
            }
        }
    };
    Y.Notificaciones.CanalFmt = function(cell, row, col, data){
        var combo = Y.Notificaciones.CanalCatalog;
        for (var i = 0; i < combo.length; i++) {
            if (data == combo[i].value) {
                cell.innerHTML = combo[i].label;
                return;
            }
        }
    };
    Y.Notificaciones.StatusFmt = function(cell, row, col, data){
        var combo = Y.Notificaciones.VigenciaCatalog;
        for (var i = 0; i < combo.length; i++) {
            if (data == combo[i].value) {
                cell.innerHTML = combo[i].label;
                return;
            }
        }
    };
    Y.Notificaciones.DestFmt = function(cell, row, col, data){
        var combo = Y.Notificaciones.DestinatarioCatalog;
        for (var i = 0; i < combo.length; i++) {
            if (data == combo[i].value) {
                cell.innerHTML = combo[i].label;
                return;
            }
        }
    };
    
    Y.Notificaciones.RegistrosDtbl = new Y.Dtbl();
    Y.Notificaciones.RegistrosDtbl.setRowMouse(true);
    Y.Notificaciones.RegistrosDtbl.setRowSelect(true);
    Y.Notificaciones.RegistrosDtbl.setUrl(Y.Notificaciones.RegistrosDtblURL);
    Y.Notificaciones.RegistrosDtbl.setDtblContainer('dtbl.Cont.Registros');
    Y.Notificaciones.RegistrosDtbl.setPgntContainer('dtbl.Pgnt.Registros');
    Y.Notificaciones.RegistrosDtbl.setPgntTotalContainer('dtbl.Ttal.Registros');
    Y.Notificaciones.RegistrosDtbl.setPgntRecords(10);
    
    Y.Notificaciones.RegistrosDtbl.setFields(["lob", "idLob", "idProceso", "proceso", "medio", /*"canalEnvio",*/"destinatario", "status", "idNotificacion", "notificacionDesc", "idMensajeAnt", "idMensaje", "asunto", "mensaje", "envioCliente", "idRespBancaria", "reintentable", "dxn", "cobraBanca", "pagoDirecto", "regLabBase", "regLabEventual", "envioAgente", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday", "nuevo", "modificado", "mondayhh", "mondaymm", "tuesday", "tuesdayhh", "tuesdaymm", "wednesday", "wednesdayhh", "wednesdaymm", "thursday", "thursdayhh", "thursdaymm", "friday", "fridayhh", "fridaymm", "saturday", "saturdayhh", "saturdaymm", "sunday", "sundayhh", "sundaymm"]);
    var columns = [{
        key: "lob",
        label: "LOB"
    }, {
        key: "proceso",
        label: "PROCESO"
    }, {
        key: "notificacionDesc",
        label: "DESCRIPCION"
    },/* {
        key: "canalEnvio",
        label: "CANAL",
        formatter: Y.Notificaciones.CanalFmt
    },*/ {
        key: "medio",
        label: "MEDIO ENVIO",
        formatter: Y.Notificaciones.MedioFmt
    }, {
        key: "destinatario",
        label: "DESTINATARIO",
        formatter: Y.Notificaciones.DestFmt
    }, {
        key: "status",
        label: "* STATUS",
        formatter: Y.Notificaciones.StatusFmt,
        editor: Y.Notificaciones.StatusEditor
    }, {
        key: "detalle",
        label: "DETALLE",
        formatter: Y.Dtbl.detalleFmt
    }];
    
    Y.Notificaciones.RegistrosDtbl.setColumnsDefs(columns);
    
    // Indicamos que las columnas del datatable tienen un editor relacionado
    Y.Notificaciones.RegistrosDtbl.setEditor(true);
    /**
     * Se construye el objecto datatable interno con los parametros asignados
     * previamente
     */
    Y.Notificaciones.RegistrosDtbl.construct();
    
    Y.Notificaciones.RegistrosDtbl.myDataTable.subscribe("buttonClickEvent", function(oArgs){
        var target = oArgs.target;
        var record = this.getRecord(target);
        if (target.value == "Detalle") {
        
            DetalleDlgEditar(record);
            
        } else {
            alert("buttonClickEvent: " + target.value);
        }
    });
    
    Y.Notificaciones.RegistrosDtbl.insertarRegistro = function(oData){
        Y.logplad("insertarRegistro...");
        
        try {
            var dtbl = this.myDataTable;
            oData['nuevo'] = true;
            oData['modificado'] = false;
            
            dtbl.addRow(oData, 0);
        } catch (x) {
            alert("insertarRegistro: " + x.description);
            return false;
        }
    };
    
    Y.Notificaciones.RegistrosDtbl.insertar = function(rows){
        try {
            var dtbl = this.myDataTable;
            if (Y.lang.isArray(rows)) {
                for (var j = 0; j < rows.length; j++) {
                    var m = rows[j];
                    try {
                        var oData = {};
                        
                        oData['lob'] = m.lob;
                        oData['idLob'] = m.idLob;
                        oData['idProceso'] = m.idProceso;
                        oData['proceso'] = m.proceso;
                        oData['medio'] = m.medio;
                        /*oData['canalEnvio'] = m.canalEnvio;*/
                        oData['destinatario'] = m.destinatario;
                        oData['status'] = m.status;
                        oData['idNotificacion'] = m.idNotificacion;
                        oData['notificacionDesc'] = m.notificacionDesc;
                        oData['idMensaje'] = m.idMensaje;
                        oData['asunto'] = m.asunto;
                        oData['mensaje'] = m.mensaje;
                        oData['envioCliente'] = m.envioCliente;
                        oData['idRespBancaria'] = m.idRespBancaria;
                        oData['reintentable'] = m.reintentable;
                        oData['dxn'] = m.dxn;
                        oData['cobraBanca'] = m.cobraBanca;
                        oData['pagoDirecto'] = m.pagoDirecto;
                        oData['regLabBase'] = m.regLabBase;
                        oData['regLabEventual'] = m.regLabEventual;
                        oData['envioAgente'] = m.envioAgente;
                        oData['nuevo'] = m.nuevo;
                        oData['modificado'] = m.modificado;
                        oData['monday'] = m.monday;
                        oData['mondayhh'] = m.mondayhh;
                        oData['mondaymm'] = m.mondaymm;
                        oData['tuesday'] = m.tuesday;
                        oData['tuesdayhh'] = m.tuesdayhh;
                        oData['tuesdaymm'] = m.tuesdaymm;
                        oData['wednesday'] = m.wednesday;
                        oData['wednesdayhh'] = m.wednesdayhh;
                        oData['wednesdaymm'] = m.wednesdaymm;
                        oData['thursday'] = m.thursday;
                        oData['thursdayhh'] = m.thursdayhh;
                        oData['thursdaymm'] = m.thursdaymm;
                        oData['friday'] = m.friday;
                        oData['fridayhh'] = m.fridayhh;
                        oData['fridaymm'] = m.fridaymm;
                        oData['saturday'] = m.saturday;
                        oData['saturdayhh'] = m.saturdayhh;
                        oData['saturdaymm'] = m.saturdaymm;
                        oData['sunday'] = m.sunday;
                        oData['sundayhh'] = m.sundayhh;
                        oData['sundaymm'] = m.sundaymm;
                        
                        this.myDataTable.addRow(oData, dtbl.getRecordSet().getLength());
                        activar = true;
                        
                    } catch (e) {
                        alert("insertar:" + e.description);
                    }
                }
                
            }
        } catch (x) {
            alert("insertar: " + x.description);
            return false;
        }
        
       
    };
	
	 
    
    Y.Notificaciones.RegistrosDtbl.actualizarRegistro = function(oData){
        Y.logplad("actualizarRegistro...");
        
        try {
            var dtbl = this.myDataTable;
            var nRecordsLength = dtbl.getRecordSet().getLength();
            var record;
            
            for (i = 0; i < nRecordsLength; i++) {
                record = dtbl.getRecordSet().getRecord(i);
                if (record.getId() == oData['recordId']) {
                
                    record.setData("lob", oData['lob']);
                    record.setData("idLob", oData['idLob']);
                    record.setData("idProceso", oData['idProceso']);
                    record.setData("proceso", oData['proceso']);
                    record.setData("medio", oData['medio']);
                    /*record.setData("canalEnvio", oData['canalEnvio']);*/
                    record.setData("destinatario", oData['destinatario']);
                    record.setData("status", oData['status']);
                    record.setData("idNotificacion", oData['idNotificacion']);
                    record.setData("notificacionDesc", oData['notificacionDesc']);
                    record.setData("idMensaje", oData['idMensaje']);
                    record.setData("asunto", oData['asunto']);
                    record.setData("mensaje", oData['mensaje']);
                    record.setData("envioCliente", oData['envioCliente']);
                    record.setData("idRespBancaria", oData['idRespBancaria']);
                    record.setData("reintentable", oData['reintentable']);
                    record.setData("dxn", oData['dxn']);
                    record.setData("cobraBanca", oData['cobraBanca']);
                    record.setData("pagoDirecto", oData['pagoDirecto']);
                    record.setData("regLabBase", oData['regLabBase']);
                    record.setData("regLabEventual", oData['regLabEventual']);
                    record.setData("envioAgente", oData['envioAgente']);
                    record.setData("monday", oData['monday']);
                    record.setData("mondayhh", oData['mondayhh']);
                    record.setData("mondaymm", oData['mondaymm']);
                    record.setData("tuesday", oData['tuesday']);
                    record.setData("tuesdayhh", oData['tuesdayhh']);
                    record.setData("tuesdaymm", oData['tuesdaymm']);
                    record.setData("wednesday", oData['wednesday']);
                    record.setData("wednesdayhh", oData['wednesdayhh'])
                    record.setData("wednesdaymm", oData['wednesdaymm'])
                    record.setData("thursday", oData['thursday']);
                    record.setData("thursdayhh", oData['thursdayhh']);
                    record.setData("thursdaymm", oData['thursdaymm']);
                    record.setData("friday", oData['friday']);
                    record.setData("fridayhh", oData['fridayhh']);
                    record.setData("fridaymm", oData['fridaymm']);
                    record.setData("saturday", oData['saturday']);
                    record.setData("saturdayhh", oData['saturdayhh']);
                    record.setData("saturdaymm", oData['saturdaymm']);
                    record.setData("sunday", oData['sunday']);
                    record.setData("sundayhh", oData['sundayhh']);
                    record.setData("sundaymm", oData['sundaymm']);
                    
                    record.setData("modificado", true);
                    record.setData("nuevo", false);
                    
                    break;
                }
            }
            dtbl.render();
        } catch (x) {
            alert("actualizarRegistro: " + x.description);
        }
    };
    
}

Y.util.Event.onDOMReady(function(){

    Y.btnBuscar = new Y.widget.Button("buscar", {
        onclick: {
            fn: function(){
                buttonBusquedaClick();
            }
        },
        disabled: true
    });
    
    Y.btnAgregar = new Y.widget.Button("agregar", {
        onclick: {
            fn: function(){
                DetalleDlgNuevo();
            }
        },
        disabled: false
    });
    Y.btnAceptar = new Y.widget.Button("aceptar", {
        onclick: {
            fn: function(){
                // Y.AltaDlg.hide();
                DetalleAceptarBtnClick();
            }
        },
        disabled: false
    });
    
    Y.btnCancelar = new Y.widget.Button("cancelar", {
        onclick: {
            fn: function(){
                Y.AltaDlg.hide();
                Y.publishMsg("div.notificacion", "");
                Y.publishMsg("div.lob", "");
                Y.publishMsg("div.proceso", "");
                Y.publishMsg("div.mensaje", "");
                Y.publishMsg("div.descripcion", "");
                Y.publishMsg("div.dia", "");
                Y.publishMsg("div.envio", "");
                Y.publishMsg("div.respuesta", "");
                Y.publishMsg("div.reintentable", "");
				Y.publishMsg("div.medio", "");
				/*Y.publishMsg("div.canalEnvio", "");*/
            }
        },
        disabled: false
    });
    
    Y.btnGuardar = new Y.widget.Button("Guardar", {
        onclick: {
            fn: function(){
                GuardarBtnClick();
            }
        },
        disabled: false
    });
    
    var tabView = new YAHOO.widget.TabView('container');
    var tabView = new YAHOO.widget.TabView('containerDetalle');
    
    Y.AltaDlg = new Y.widget.Panel("AltaDlg", {
        width: "35em",
        height: "auto",
        autofillheight: "bd",
        fixedcenter: "contained",
        modal: true,
        underlay: "none",
        visible: false,
        constraintoviewport: true,
        draggable: true
    });
    var kl = new Y.util.KeyListener(document, {
        keys: 27
    }, {
        fn: Y.AltaDlg.hide,
        scope: Y.AltaDlg,
        correctScope: true
    });
    kl.enable();
    
    Y.AltaDlg.render();
    
    Y.DetalleDlg = new Y.widget.Panel("Detalle", {
        width: "35em",
        height: "auto",
        autofillheight: "bd",
        fixedcenter: "contained",
        modal: true,
        visible: false,
        constraintoviewport: true,
        draggable: true
    });
    var kl = new Y.util.KeyListener(document, {
        keys: 27
    }, {
        fn: Y.DetalleDlg.hide,
        scope: Y.DetalleDlg,
        correctScope: true
    });
    kl.enable();
    
    Y.DetalleDlg.render();
});
function valida(){
    var f = document.getElementById("formBusqueda");
    var lob = f.elements['lob'].value;
    var stat = f.elements['status'].value;
    var proc = f.elements['proceso'].value;
    if (lob == '' && stat == '' && proc == '') {
        // Ningun valor seleccionado
        Y.btnBuscar.set("disabled", true);
        return false;
    } else {
        Y.btnBuscar.set("disabled", false);
        return true;
    }
}

function BuscarProcesos(dlg){
    try {
        if (dlg == undefined || dlg == '') {
            var f = document.getElementById("formBusqueda");
            var elSel = f.elements['proceso'];
            var lob = f.elements['lob'].value;
        } else {
            var f = document.getElementById("GeneralesForm");
            var elSel = f.elements['notificacion.idProceso'];
            
            var lob = f.elements['notificacion.idLob'].value;
        }
        
        valida();
        
        var callback = new Y.AsyncCallBack();
        callback.processResult = function(messages){
            clearAndFillSelect(messages.select, elSel, '');
            f.elements['proceso'].disabled = false;
            getForm("GeneralesForm").elements['notificacion.idProceso'].disabled = false;
            getForm("GeneralesForm").elements['notificacion.medio'].disabled = false;
            /*getForm("GeneralesForm").elements['notificacion.canalEnvio'].disabled = false;*/
            
        };
        
        var sUrl = Y.Notificaciones.ProcesosURL;
        var postData = "lob=" + lob;
        var request = Y.util.Connect.asyncRequest('POST', sUrl, callback, postData);
        
    } catch (x) {
        alert("Y.Mensajes.BuscaProcesos :" + x.description);
    }
}

function buscarMensajes(){
    try {
    
        var f = document.getElementById("GeneralesForm");
        var proceso = f.elements['notificacion.idProceso'].value;
        var medio = f.elements['notificacion.medio'].value;
        /*var canalEnvio = f.elements['notificacion.canalEnvio'].value;*/
        var lob = f.elements['notificacion.idLob'].value;
        var elSel = f.elements['notificacion.mensaje.idMensaje'];
        
        if (lob == '') {
            // Ningun valor seleccionado
            clearSelect(elSel);
            Y.btnBuscar.set("disabled", true);
            return;
        }
        if(medio == 'SM'){
			f.elements['notificacion.envioAgente'].checked = false;
			f.elements['notificacion.envioAgente'].disabled = true;
		}else{
			f.elements['notificacion.envioAgente'].disabled = false;
		}
        var callback = new Y.AsyncCallBack();
        callback.processResult = function(messages){
            clearAndFillSelect(messages.select, elSel, '');
            elSel.options[0].selected = true;
            Y.btnBuscar.set("disabled", false);
        };
        
        var f = document.getElementById("CobranzaForm");
        if (proceso == 'PROC_COBRBANC') {
            f.elements['notificacion.idRespBancaria'].disabled = false;
			f.elements['notificacion.reintentable'].disabled = false;
        } else {
            f.elements['notificacion.idRespBancaria'].disabled = true;
			f.elements['notificacion.reintentable'].disabled = true;
			f.elements['notificacion.idRespBancaria'].selectedIndex = -1;
			f.elements['notificacion.reintentable'].selectedIndex = -1;
        }
        
        var sUrl = Y.Notificaciones.MensajesURL;
        var postData = "lob=" + lob + "&proceso=" + proceso + "&medio=" + medio;
        var request = Y.util.Connect.asyncRequest('POST', sUrl, callback, postData);
        
    } catch (x) {
        alert("Y.Mensajes.BuscaProcesos :" + x.description);
    }
}

function SeleccionMensaje(dlg){
    try {
        if (dlg == undefined || dlg == '') {
            var f = document.getElementById("formBusqueda");
            var elSel = f.elements['proceso'];
            var lob = f.elements['lob'].value;
        } else {
            var f = document.getElementById("GeneralesForm");
            f.elements['notificacion.mensaje.asunto'].value = '';
            f.elements['notificacion.mensaje.mensaje'].value = '';
            var idMensaje = f.elements['notificacion.mensaje.idMensaje'].value;
        }
        
        var callback = new Y.AsyncCallBack();
        callback.processResult = function(messages){
            if (messages.asunto != '' && messages.asunto != undefined) {
                f.elements['notificacion.mensaje.asunto'].value = messages.asunto;
            }
            if (messages.mensaje != '' && messages.mensaje != undefined) {
                f.elements['notificacion.mensaje.mensaje'].value = messages.mensaje;
            }
            
            Y.btnBuscar.set("disabled", false);
        };
        
        var sUrl = Y.Notificaciones.MensajeURL;
        var postData = "notificacion.mensaje.idMensaje=" + idMensaje;
        var request = Y.util.Connect.asyncRequest('POST', sUrl, callback, postData);
        
    } catch (x) {
        alert("Y.Mensajes.BuscaProcesos :" + x.description);
    }
}

function activaDesactivaOtros(){
    var f = document.getElementById("OtrosForm");
    var activo = f.elements['activar'].checked;
    if (activo) {
        f.elements['notificacion.dxn'].disabled = false;
        f.elements['notificacion.cobraBanca'].disabled = false;
        f.elements['notificacion.pagoDirecto'].disabled = false;
        f.elements['notificacion.regLabBase'].disabled = false;
        f.elements['notificacion.regLabEventual'].disabled = false;
    } else {
        f.elements['notificacion.dxn'].disabled = true;
        f.elements['notificacion.cobraBanca'].disabled = true;
        f.elements['notificacion.pagoDirecto'].disabled = true;
        f.elements['notificacion.regLabBase'].disabled = true;
        f.elements['notificacion.regLabEventual'].disabled = true;
        
    }
}

function DetalleDlgNuevo(){
    try {
        var generales = document.getElementById("GeneralesForm");
        var cobranza = document.getElementById("CobranzaForm");
        var otros = document.getElementById("OtrosForm");
        var frecuencia = document.getElementById("FrecuenciaForm");
        
        
        generales.elements['notificacion.idProceso'].style.display = 'block';
        generales.elements['notificacion.idProcesoDet'].style.display = 'none';
        
        
        // generales.elements['notificacion.idLob'].value =
        // record.getData('idLob');
        generales.elements['notificacion.idLob'].style.display = 'block';
        generales.elements['notificacion.idLobDet'].style.display = 'none';
        
        otros.elements['activar'].disabled = false;
        
        generales.elements['record.id'].value = '';
        generales.elements['status'].value = '';
        generales.elements['notificacion.idNotificacion'].disabled = false;
        generales.elements['notificacion.idNotificacion'].value = '';
        generales.elements['notificacion.notificacionDesc'].value = '';
        generales.elements['notificacion.mensaje.idMensaje'].value = '';
        generales.elements['notificacion.mensaje.idMensaje'].disabled = false;
        generales.elements['notificacion.medio'].value = '';
        //generales.elements['notificacion.canalEnvio'].style.display = 'block';
        /*generales.elements['notificacion.canalEnvio'].value = '';*/
        //generales.elements['notificacion.medio'].disabled = false;
        generales.elements['notificacion.mensaje.asunto'].value = '';
        generales.elements['notificacion.mensaje.mensaje'].value = '';
        generales.elements['notificacion.envioCliente'].checked = false;
        generales.elements['notificacion.envioAgente'].checked = false;
        
        generales.elements['notificacion.idProceso'].value = '';
        generales.elements['notificacion.idLob'].value = '';
        
        cobranza.elements['notificacion.idRespBancaria'].value = '';
        cobranza.elements['notificacion.idRespBancaria'].disabled = true;
        cobranza.elements['notificacion.reintentable'].value = '';
        cobranza.elements['notificacion.reintentable'].disabled = true;
        
        
        otros.elements['notificacion.dxn'].checked = false;
        otros.elements['notificacion.cobraBanca'].checked = false;
        otros.elements['notificacion.pagoDirecto'].checked = false;
        otros.elements['notificacion.regLabBase'].checked = false;
        otros.elements['notificacion.regLabEventual'].checked = false;
        
        frecuencia.elements['MON'].checked = false;
		frecuencia.elements['MONhh'].disabled = true;
		frecuencia.elements['MONmm'].disabled = true;
        frecuencia.elements['MONhh'].selectedIndex = 10;
        frecuencia.elements['MONmm'].selectedIndex = 0;
        frecuencia.elements['TUE'].checked = false;
		frecuencia.elements['TUEhh'].disabled = true;
		frecuencia.elements['TUEmm'].disabled = true;
        frecuencia.elements['TUEhh'].selectedIndex = 10;
        frecuencia.elements['TUEmm'].selectedIndex = 0;
        frecuencia.elements['WED'].checked = false;
		frecuencia.elements['WEDhh'].disabled = true;
		frecuencia.elements['WEDmm'].disabled = true;
        frecuencia.elements['WEDhh'].selectedIndex = 10;
        frecuencia.elements['WEDmm'].selectedIndex = 0;
        frecuencia.elements['THU'].checked = false;
		frecuencia.elements['THUhh'].disabled = true;
		frecuencia.elements['THUmm'].disabled = true;
        frecuencia.elements['THUhh'].selectedIndex = 10;
        frecuencia.elements['THUmm'].selectedIndex = 0;
        frecuencia.elements['FRI'].checked = false;
		frecuencia.elements['FRIhh'].disabled = true;
		frecuencia.elements['FRImm'].disabled = true;
        frecuencia.elements['FRIhh'].selectedIndex = 10;
        frecuencia.elements['FRImm'].selectedIndex = 0;
        frecuencia.elements['SAT'].checked = false;
		frecuencia.elements['SAThh'].disabled = true;
		frecuencia.elements['SATmm'].disabled = true;
        frecuencia.elements['SAThh'].selectedIndex = 10;
        frecuencia.elements['SATmm'].selectedIndex = 0;
        frecuencia.elements['SUN'].checked = false;
		frecuencia.elements['SUNhh'].disabled = true;
		frecuencia.elements['SUNmm'].disabled = true;
        frecuencia.elements['SUNhh'].selectedIndex = 10;
        frecuencia.elements['SUNmm'].selectedIndex = 0;
        
        Y.publishMsg("div.notificacion", "");
        Y.publishMsg("div.lob", "");
        Y.publishMsg("div.proceso", "");
        Y.publishMsg("div.mensaje", "");
        Y.publishMsg("div.descripcion", "");
        Y.publishMsg("div.dia", "");
        Y.publishMsg("div.respuesta", "");
        Y.publishMsg("div.reintentable", "");
        Y.publishMsg("div.envio", "");
        
        Y.AltaDlg.setHeader("Nueva Notificaci&oacute;n");
        Y.AltaDlg.center();
        Y.AltaDlg.show();
        Y.AltaDlg.center();
    } catch (e) {
        alert("DetalleDlgNuevo:" + e.description);
    }
};

function DetalleAceptarBtnClick(){
    try {
    
        var generales = document.getElementById("GeneralesForm");
        var cobranza = document.getElementById("CobranzaForm");
        var otros = document.getElementById("OtrosForm");
        var frecuencia = document.getElementById("FrecuenciaForm");
        
        if (generales.elements['record.id'].value == '') {
            // valida un registro nuevo
            generales.elements['notificacion.nuevo'].value = true;
        } else {
            generales.elements['notificacion.nuevo'].value = false;
        }
        
        var callback = new Y.AsyncCallBack();
        callback.processResult = function(messages){
            var index;
            var recordId = generales.elements['record.id'].value;
            var oData = {};
            
            oData['recordId'] = recordId;
            if (generales.elements['record.id'].value == '') {
                oData['status'] = 'A';
            } else {
                oData['status'] = generales.elements['status'].value;
            }
            oData['idNotificacion'] = generales.elements['notificacion.idNotificacion'].value;
            oData['notificacionDesc'] = generales.elements['notificacion.notificacionDesc'].value;
            oData['idMensaje'] = generales.elements['notificacion.mensaje.idMensaje'].value;
            oData['idMensajeAnt'] = generales.elements['idMensajeAnt'].value;
            //alert("Anterior "+ oData['idMensajeAnt']);
            //alert("Actual "+oData['idMensaje']);
            oData['medio'] = generales.elements['notificacion.medio'].value;
            /*oData['canalEnvio'] = getIdHUBChannel(generales.elements['notificacion.canalEnvio'].value);*/
            //alert(getIdHUBChannel(generales.elements['notificacion.canalEnvio'].value));
            oData['asunto'] = generales.elements['notificacion.mensaje.asunto'].value;
            oData['mensaje'] = generales.elements['notificacion.mensaje.mensaje'].value;
            
            var cliente = generales.elements['notificacion.envioCliente'].checked
            var agente = generales.elements['notificacion.envioAgente'].checked
            if (cliente) {
                oData['destinatario'] = "CL"
            }
            if (agente) {
                oData['destinatario'] = "AG"
            }
            if (cliente && agente) {
                oData['destinatario'] = "AM"
            }
            oData['envioCliente'] = oData['destinatario']
            index = generales.elements['notificacion.idProceso'].selectedIndex;
            if (generales.elements['record.id'].value == '') {
                if (index == -1) {
                    oData['proceso'] = " ";
                } else {
                    oData['proceso'] = generales.elements['notificacion.idProceso'].options[index].text;
                    oData['idProceso'] = generales.elements['notificacion.idProceso'].value;
                }
            } else {
                if (index == -1) {
                    oData['proceso'] = " ";
                } else {
                    oData['proceso'] = generales.elements['notificacion.idProcesoDet'].value;
                    oData['idProceso'] = generales.elements['proceso'].value;
                }
            }
            
            index = generales.elements['notificacion.idLob'].selectedIndex;
            if (generales.elements['record.id'].value == '') {
                if (index == -1) {
                    oData['lob'] = " ";
                } else {
                    oData['lob'] = generales.elements['notificacion.idLob'].options[index].text;
                    oData['idLob'] = generales.elements['notificacion.idLob'].value;
                }
            } else {
                if (index == -1) {
                    oData['lob'] = " ";
                } else {
                    oData['lob'] = generales.elements['notificacion.idLobDet'].value;
                    oData['idLob'] = generales.elements['lob'].value;
                }
            }
            
            oData['idRespBancaria'] = cobranza.elements['notificacion.idRespBancaria'].value;
            oData['reintentable'] = cobranza.elements['notificacion.reintentable'].value;
            
            var activo = otros.elements['activar'].value;
            
            if (activo) {
                oData['dxn'] = otros.elements['notificacion.dxn'].checked;
                oData['cobraBanca'] = otros.elements['notificacion.cobraBanca'].checked;
                oData['pagoDirecto'] = otros.elements['notificacion.pagoDirecto'].checked;
                oData['regLabBase'] = otros.elements['notificacion.regLabBase'].checked;
                oData['regLabEventual'] = otros.elements['notificacion.regLabEventual'].checked;
            } else {
                oData['dxn'] = false;
                oData['cobraBanca'] = false;
                oData['pagoDirecto'] = false;
                oData['regLabBase'] = false;
                oData['regLabEventual'] = false;
            }
            
            oData['monday'] = frecuencia.elements['MON'].checked;
            oData['mondayhh'] = frecuencia.elements['MONhh'].value;
            oData['mondaymm'] = frecuencia.elements['MONmm'].value;
            oData['tuesday'] = frecuencia.elements['TUE'].checked;
            oData['tuesdayhh'] = frecuencia.elements['TUEhh'].value;
            oData['tuesdaymm'] = frecuencia.elements['TUEmm'].value;
            oData['wednesday'] = frecuencia.elements['WED'].checked;
            oData['wednesdayhh'] = frecuencia.elements['WEDhh'].value;
            oData['wednesdaymm'] = frecuencia.elements['WEDmm'].value;
            oData['thursday'] = frecuencia.elements['THU'].checked;
            oData['thursdayhh'] = frecuencia.elements['THUhh'].value;
            oData['thursdaymm'] = frecuencia.elements['THUmm'].value;
            oData['friday'] = frecuencia.elements['FRI'].checked;
            oData['fridayhh'] = frecuencia.elements['FRIhh'].value;
            oData['fridaymm'] = frecuencia.elements['FRImm'].value;
            oData['saturday'] = frecuencia.elements['SAT'].checked;
            oData['saturdayhh'] = frecuencia.elements['SAThh'].value;
            oData['saturdaymm'] = frecuencia.elements['SATmm'].value;
            oData['sunday'] = frecuencia.elements['SUN'].checked;
            oData['sundayhh'] = frecuencia.elements['SUNhh'].value;
            oData['sundaymm'] = frecuencia.elements['SUNmm'].value;
            
            if (recordId == '') {
                Y.Notificaciones.RegistrosDtbl.insertarRegistro(oData);
            } else {
                Y.Notificaciones.RegistrosDtbl.actualizarRegistro(oData);
            }
			generales.elements['notificacion.envioAgente'].disabled = false;
            // Y.DetalleAltaDlg.hide();
            Y.AltaDlg.hide()
            
        };
        frec = document.getElementById("FrecuenciaForm");
        generales.elements['dia'].value = false;
        var valido = false;
        
        if (!valido && (frec.elements['MON'].checked)) {
            valido = true;
            generales.elements['dia'].value = true;
        } else 
            if (!valido && (frec.elements['TUE'].checked)) {
                valido = true;
                generales.elements['dia'].value = true;
            } else 
                if (!valido && (frec.elements['WED'].checked)) {
                    valido = true;
                    generales.elements['dia'].value = true;
                } else 
                    if (!valido && (frec.elements['THU'].checked)) {
                        valido = true;
                        generales.elements['dia'].value = true;
                    } else 
                        if (!valido && (frec.elements['FRI'].checked)) {
                            valido = true;
                            generales.elements['dia'].value = true;
                        } else 
                            if (!valido && (frec.elements['SAT'].checked)) {
                                valido = true;
                                generales.elements['dia'].value = true;
                            } else 
                                if (!valido && (frec.elements['SUN'].checked)) {
                                    valido = true;
                                    generales.elements['dia'].value = true;
                                }
        generales.elements['reintentable'].value = cobranza.elements['notificacion.reintentable'].value;
        generales.elements['idRespBancaria'].value = cobranza.elements['notificacion.idRespBancaria'].value;
        
        Y.util.Connect.setForm(generales);
        Y.showWait();
        var request = Y.util.Connect.asyncRequest('POST', Y.Notificaciones.ValidarURL, callback);
        
    } catch (e) {
        alert("DetalleAceptarBtnClick: " + e.description);
    }
};

function GuardarBtnClick(){
    try {
        var f = document.getElementById("NotificacionesJsonForm");
        
        f.elements["jsonrecords"].value = Y.Notificaciones.RegistrosDtbl.getJsonRecords();
        //alert(f.elements["jsonrecords"].value);
        //alert(f.elements["jsonrecords"].value);
        var callback = new Y.AsyncCallBack();
        callback.processResult = function(messages){
            var msg = '<div><center>';
            msg += 'Los cambios se guardaron correctamente.';
            msg += '</center></div>';
            new Y.MessagePnl().showmsg({
                msg: msg,
                fnSubmit: function(){
                    Y.Notificaciones.RegistrosDtbl.clear();
                }
            });
        };
        /*
         * callback.processResultUnsuccess = function(messages){
         * f.elements['porcTotal'].value =
         * Y.lang.JSON.stringify(messages.porcTotal); if
         * (f.elements['porcTotal'].value != 0 &&
         * form.elements['idCierre'].value != '') { if (messages.proceso !=
         * undefined) { var msg = '<div><center>'; msg += messages.proceso;
         * msg += '</center></div>'; new Y.MessagePnl().showmsg({ msg: msg
         * }); } } Y.btnAsignar.set("disabled", false); };
         */
        Y.util.Connect.setForm(f);
        Y.showWait();
        var request = Y.util.Connect.asyncRequest('POST', Y.Notificaciones.GuardarCambiosURL, callback);
        
    } catch (e) {
        alert("guardargeneral: " + e.description);
    }
};

function buttonBusquedaClick(){
    try {
        var formNotificacion = document.getElementById("NotificacionesForm");
        var formBusque = document.getElementById("formBusqueda");
        var callback = new Y.AsyncCallBack();
        callback.processResult = function(messages){
        
            Y.Notificaciones.RegistrosDtbl.clear();
            Y.Notificaciones.RegistrosDtbl.insertar(messages.rows);
            
        }
        
        formNotificacion.elements['lob'].value = formBusque.elements['lob'].value;
        formNotificacion.elements['status'].value = formBusque.elements['status'].value;
        formNotificacion.elements['proceso'].value = formBusque.elements['proceso'].value;
        
        Y.util.Connect.setForm(formNotificacion);
        Y.showWait();
        var request = Y.util.Connect.asyncRequest('POST', Y.Notificaciones.FiltrarURL, callback);
    } catch (e) {
        alert("buttonBusquedaClick: " + e.description);
    }
}

function DetalleDlgEditar(record){

    try {
        var generales = document.getElementById("GeneralesForm");
        var cobranza = document.getElementById("CobranzaForm");
        var otros = document.getElementById("OtrosForm");
        var frecuencia = document.getElementById("FrecuenciaForm");
        
        Y.publishMsg("div.notificacion", "");
        Y.publishMsg("div.lob", "");
        Y.publishMsg("div.proceso", "");
        Y.publishMsg("div.mensaje", "");
        Y.publishMsg("div.descripcion", "");
        Y.publishMsg("div.dia", "");
        Y.publishMsg("div.respuesta", "");
        Y.publishMsg("div.reintentable", "");
        Y.publishMsg("div.envio", "");
        //Y.publishMsg("div.canalEnvio", record.getData('canalEnvio'));
        /*var canalEnvioVar= record.getData('canalEnvio');*/
        var comboCanal = Y.Notificaciones.CanalCatalog;
     
        getMensajes(record.getData('idLob'), record.getData('idProceso'), record.getData('medio'), record.getData('idMensaje')/*, record.getData('canalEnvio')*/);
        
        generales.elements['record.id'].value = record.getId();
        generales.elements['status'].value = record.getData('status');
        generales.elements['notificacion.idNotificacion'].value = record.getData('idNotificacion');
        
        generales.elements['notificacion.idNotificacion'].disabled = true;
        
        generales.elements['notificacion.notificacionDesc'].value = record.getData('notificacionDesc');
        generales.elements['notificacion.mensaje.idMensaje'].value = record.getData('idMensaje');
        generales.elements['notificacion.mensaje.idMensaje'].disabled = true;
        generales.elements['idMensajeAnt'].value = record.getData('idMensaje');
        
        generales.elements['notificacion.medio'].value = record.getData('medio');
        /*generales.elements['notificacion.canalEnvio'].value = getLabelHUBChannel(record.getData('canalEnvio'));*/
		if(record.getData('medio')=='SM'){
			generales.elements['notificacion.envioAgente'].disabled = true;
		}else{
			generales.elements['notificacion.envioAgente'].disabled = false;
		}
        generales.elements['notificacion.medio'].disabled = true;
        /*generales.elements['notificacion.canalEnvio'].disabled = false;*/
        if (record.getData('asunto') != null && record.getData('asunto') != undefined) 
            generales.elements['notificacion.mensaje.asunto'].value = record.getData('asunto');
        if (record.getData('mensaje') != null && record.getData('mensaje') != undefined) 
            generales.elements['notificacion.mensaje.mensaje'].value = record.getData('mensaje');
        var destinatario = record.getData('destinatario');
        if (destinatario == "AM") {
            generales.elements['notificacion.envioCliente'].checked = true;
            generales.elements['notificacion.envioAgente'].checked = true
        } else 
            if (destinatario == "CL") {
                generales.elements['notificacion.envioCliente'].checked = true;
                generales.elements['notificacion.envioAgente'].checked = false
            } else 
                if (destinatario == "AG") {
                    generales.elements['notificacion.envioCliente'].checked = false;
                    generales.elements['notificacion.envioAgente'].checked = true
                }
        
        
        // generales.elements['notificacion.idProceso'].value =
        // record.getData('idProceso');
        generales.elements['notificacion.idProceso'].style.display = 'none';
        generales.elements['notificacion.idProcesoDet'].style.display = 'block';
        generales.elements['notificacion.idProcesoDet'].value = record.getData('proceso');
        generales.elements['proceso'].value = record.getData('idProceso');
        
        // generales.elements['notificacion.idLob'].value =
        // record.getData('idLob');
        generales.elements['notificacion.idLob'].style.display = 'none';
        generales.elements['notificacion.idLobDet'].style.display = 'block';
        generales.elements['notificacion.idLobDet'].value = record.getData('lob');
        generales.elements['lob'].value = record.getData('idLob');
        
        
        if (record.getData('idProceso') == "PROC_COBRBANC") {
            cobranza.elements['notificacion.idRespBancaria'].value = record.getData('idRespBancaria');
            cobranza.elements['notificacion.reintentable'].value = record.getData('reintentable');
        } else {
            cobranza.elements['notificacion.idRespBancaria'].selectedIndex = -1;
            cobranza.elements['notificacion.reintentable'].selectedIndex = -1;
        }
        
        cobranza.elements['notificacion.idRespBancaria'].disabled = true;
        cobranza.elements['notificacion.reintentable'].disabled = true;
        
        //        otros.elements['activar'].disabled = true;
        otros.elements['notificacion.dxn'].checked = record.getData('dxn');
        otros.elements['notificacion.cobraBanca'].checked = record.getData('cobraBanca');
        otros.elements['notificacion.pagoDirecto'].checked = record.getData('pagoDirecto');
        otros.elements['notificacion.regLabBase'].checked = record.getData('regLabBase');
        otros.elements['notificacion.regLabEventual'].checked = record.getData('regLabEventual');
        
        var check;
		frecuencia.elements['MON'].checked = record.getData('monday');
		check = frecuencia.elements['MON'].checked;
		habilitaDia(frecuencia.elements['MON']);
        if (record.getData('mondayhh') != undefined && check) {
			frecuencia.elements['MONhh'].value = record.getData('mondayhh');
		}else{
			frecuencia.elements['MONhh'].selectedIndex = 10;
		}
        if (record.getData('mondaymm') != undefined && check) {
			frecuencia.elements['MONmm'].value = record.getData('mondaymm');
		}else{
			frecuencia.elements['MONmm'].selectedIndex = 0;
		}
        frecuencia.elements['TUE'].checked = record.getData('tuesday');
		check = frecuencia.elements['TUE'].checked;
		habilitaDia(frecuencia.elements['TUE']);
        if (record.getData('tuesdayhh') != undefined && check) {
			frecuencia.elements['TUEhh'].value = record.getData('tuesdayhh');
		}else{
			frecuencia.elements['TUEhh'].selectedIndex = 10;
		}
        if (record.getData('tuesdaymm') != undefined && check) {
			frecuencia.elements['TUEmm'].value = record.getData('tuesdaymm');
		}else{
			frecuencia.elements['TUEmm'].selectedIndex = 0;
		}
        frecuencia.elements['WED'].checked = record.getData('wednesday');
		check = frecuencia.elements['WED'].checked;
		habilitaDia(frecuencia.elements['WED']);
        if (record.getData('wednesdayhh') != undefined && check) {
			frecuencia.elements['WEDhh'].value = record.getData('wednesdayhh');
		}else{
			frecuencia.elements['WEDhh'].selectedIndex = 10;
		}
        if (record.getData('wednesdaymm') != undefined) {
			frecuencia.elements['WEDmm'].value = record.getData('wednesdaymm');
		}else{
			frecuencia.elements['WEDmm'].selectedIndex = 0;
		}
        frecuencia.elements['THU'].checked = record.getData('thursday');
		check = frecuencia.elements['THU'].checked;
		habilitaDia(frecuencia.elements['THU']);
        if (record.getData('thursdayhh') != undefined && check) {
			frecuencia.elements['THUhh'].value = record.getData('thursdayhh');
		}else{
			frecuencia.elements['THUhh'].selectedIndex = 10;
		}
        if (record.getData('thursdaymm') != undefined) {
			frecuencia.elements['THUmm'].value = record.getData('thursdaymm');
		}else{
			frecuencia.elements['THUmm'].selectedIndex = 0;
		}
        frecuencia.elements['FRI'].checked = record.getData('friday');
		check = frecuencia.elements['FRI'].checked;
		habilitaDia(frecuencia.elements['FRI']);
        if (record.getData('fridayhh') != undefined && check) {
			frecuencia.elements['FRIhh'].value = record.getData('fridayhh');
		}else{
			frecuencia.elements['FRIhh'].selectedIndex = 10;
		}
        if (record.getData('fridaymm') != undefined) {
			frecuencia.elements['FRImm'].value = record.getData('fridaymm');
		}else{
			frecuencia.elements['FRImm'].selectedIndex = 0;
		}
        frecuencia.elements['SAT'].checked = record.getData('saturday');
		check = frecuencia.elements['SAT'].checked;
		habilitaDia(frecuencia.elements['SAT']);
        if (record.getData('saturdayhh') != undefined && check) {
			frecuencia.elements['SAThh'].value = record.getData('saturdayhh');
		}else{
			frecuencia.elements['SAThh'].selectedIndex = 10;
		}
        if (record.getData('saturdaymm') != undefined) {
			frecuencia.elements['SATmm'].value = record.getData('saturdaymm');
		}else{
			frecuencia.elements['SATmm'].selectedIndex = 0;
		}
        frecuencia.elements['SUN'].checked = record.getData('sunday');
		check = frecuencia.elements['SUN'].checked;
		habilitaDia(frecuencia.elements['SUN']);
        if (record.getData('sundayhh') != undefined && check) {
			frecuencia.elements['SUNhh'].value = record.getData('sundayhh');
		}else{
			frecuencia.elements['SUNhh'].selectedIndex = 10;
		}
        if (record.getData('sundaymm') != undefined) {
			frecuencia.elements['SUNmm'].value = record.getData('sundaymm');
		}else{
			frecuencia.elements['SUNmm'].selectedIndex = 0;
		}
        
        Y.AltaDlg.setHeader("Detalle Notificaci&oacute;n");
        Y.AltaDlg.show();
        Y.AltaDlg.center();
        
    } catch (e) {
		alert("DetalleDlgEditar:" + e + " " + e.description);
    }
}

function getMensajes(lob, proceso, medio, mensaje/*, canalEnvio*/){
    try {
    
        var f = document.getElementById("GeneralesForm");
        var elSel = f.elements['notificacion.mensaje.idMensaje'];
        
        var callback = new Y.AsyncCallBack();
        callback.processResult = function(messages){
            clearAndFillSelect(messages.select, elSel, '');
            //elSel.options[0].selected = true;
            f.elements['notificacion.mensaje.idMensaje'].value = mensaje;
            SeleccionMensaje('ds');
            
        };
        
        
        var sUrl = Y.Notificaciones.MensajesURL;
        var postData = "lob=" + lob + "&proceso=" + proceso + "&medio=" + medio;
        var request = Y.util.Connect.asyncRequest('POST', sUrl, callback, postData);
        
    } catch (x) {
        alert("Y.Notificaciones.getMensajes :" + x.description);
    }
}

function cobranza(){

}

function habRespuesta(valor){
    var f = getForm("CobranzaForm");
    if (f.elements["notificacion.idRespBancaria"].value == "R") {
        f.elements["notificacion.reintentable"].disabled = false;
    } else {
        f.elements["notificacion.reintentable"].disabled = true;
    }
}
