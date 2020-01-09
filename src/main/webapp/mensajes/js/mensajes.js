function getVariables(proceso){
	try {
		var callback = new Y.AsyncCallBack();
		callback.processResult = function(messages) {

			Y.Mensajes.AltaDtbl.clear();
			Y.Mensajes.AltaDtbl.insertar(messages.rows);

		}
		var sUrl = Y.Mensajes.DetalleDtblURL;
        var postData = "proceso=" + proceso.value;
        var request = Y.util.Connect.asyncRequest('POST', sUrl, callback, postData);
	} catch (e) {
		alert("buttonBusquedaClick: " + e.description);
	}
}
function initDtblMensajes() {

	Y.Mensajes.StatusFmt = function(cell, row, col, data) {
		var combo = Y.Mensajes.VigenciaCatalog;
		for ( var i = 0; i < combo.length; i++) {
			if (data == combo[i].value) {
				cell.innerHTML = combo[i].label;
				return;
			}
		}
	};

	Y.Mensajes.MedioFmt = function(cell, row, col, data) {
		var combo = Y.Mensajes.MedioCatalog;
		for ( var i = 0; i < combo.length; i++) {
			if (data == combo[i].value) {
				cell.innerHTML = combo[i].label;
				return;
			}
		}
	};
	
	Y.Mensajes.StatusEditor = new YAHOO.widget.DropdownCellEditor({
        dropdownOptions: Y.Mensajes.VigenciaCatalog,
        disableBtns: false,
        LABEL_SAVE: "Aceptar",
        LABEL_CANCEL: "Cancelar"
    });
    
	
    
	Y.Mensajes.RegistrosDtbl = new Y.Dtbl();
	Y.Mensajes.RegistrosDtbl.setRowMouse(true);
	Y.Mensajes.RegistrosDtbl.setRowSelect(true);
	Y.Mensajes.RegistrosDtbl.setUrl(Y.Mensajes.RegistrosDtblURL);
	Y.Mensajes.RegistrosDtbl.setDtblContainer('dtbl.Cont.Registros');
	Y.Mensajes.RegistrosDtbl.setPgntContainer('dtbl.Pgnt.Registros');
	Y.Mensajes.RegistrosDtbl.setPgntTotalContainer('dtbl.Ttal.Registros');
	Y.Mensajes.RegistrosDtbl.setPgntRecords(10);
	Y.Mensajes.RegistrosDtbl.setFields([ "idMensaje", "descripcion", "asunto", "mensaje", "idlob", "lob", "medio", "idProceso", "proceso", "status", "nuevo", "modificado" ]);
	var columns = [ {
		key : "idMensaje",
		label : "ID MENSAJE"
	}, {
		key : "lob",
		label : "LOB"
	}, {
		key : "medio",
		label : "MEDIO ENVIO",
		formatter: Y.Mensajes.MedioFmt
	},   {
		key : "proceso",
		label : "PROCESO"
	}, {
		key : "descripcion",
		label : "DESCRIPCION"
	},{
		key : "status",
		label : "STATUS",
		formatter : Y.Mensajes.StatusFmt,
        editor: Y.Mensajes.StatusEditor
	}, {
		key : "detalle",
		label : "DETALLE",
		formatter : Y.Dtbl.detalleFmt
	} ];

	Y.Mensajes.RegistrosDtbl.setColumnsDefs(columns);

	// Indicamos que las columnas del datatable tienen un editor relacionado
	Y.Mensajes.RegistrosDtbl.setEditor(true);
	/**
	 * Se construye el objecto datatable interno con los parametros asignados
	 * previamente
	 */
	Y.Mensajes.RegistrosDtbl.construct();

	Y.Mensajes.RegistrosDtbl.myDataTable.subscribe("buttonClickEvent", function(oArgs) {
		var target = oArgs.target;
		var record = this.getRecord(target);
		if (target.value == "Detalle") {

			DetalleDlgEditar(record);

		} else {
			alert("buttonClickEvent: " + target.value);
		}

	});

	Y.Mensajes.RegistrosDtbl.actualizarRegistro = function(oData) {
		Y.logplad("actualizarRegistro...");

		try {
			var dtbl = this.myDataTable;
			var nRecordsLength = dtbl.getRecordSet().getLength();
		      var record;

		      for (i = 0; i < nRecordsLength; i++) {
		        record = dtbl.getRecordSet().getRecord(i);
		        if (record.getId() == oData['recordId']) {
					record.setData("idMensaje", oData['idMensaje']);
					record.setData("descripcion", oData['descripcion']);
					record.setData("asunto", oData['asunto']);
					record.setData("mensaje", oData['mensaje']);
					record.setData("idlob", oData['idlob']);
					record.setData("lob", oData['lob']);
					record.setData("medio", oData['medio']);
					record.setData("idProceso", oData['idProceso']);
					record.setData("proceso", oData['proceso']);
					record.setData("status", oData['status']);
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

	Y.Mensajes.RegistrosDtbl.insertarRegistro = function(oData) {
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

	Y.Mensajes.RegistrosDtbl.insertar = function(rows) {
		try {
			var dtbl = this.myDataTable;
			if (Y.lang.isArray(rows)) {
				for ( var j = 0; j < rows.length; j++) {
					var m = rows[j];
					try {
						var oData = {};

						oData['idMensaje'] = m.idMensaje;
						oData['descripcion'] = m.descripcion;
						oData['asunto'] = m.asunto;
						oData['mensaje'] = m.mensaje;
						oData['idlob'] = m.idlob;
						oData['lob'] = m.lob;
						oData['medio'] = m.medio;
						oData['idProceso'] = m.idProceso;
						oData['proceso'] = m.proceso;
						oData['status'] = m.status;
						oData['nuevo'] = m.nuevo;
						oData['modificado'] = m.modificado;

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

	Y.Mensajes.RegistrosDtbl.myDataTable.setAttributeConfig("generateRequest", {
		value : function(oState, oSelf) {
			// Set defaults
			oState = oState || {
				pagination : null,
				sortedBy : null
			};
			Y.logplad('generateRequest...');
			var sort = (oState.sortedBy) ? oState.sortedBy.key : oSelf.getColumnSet().keys[0].getKey();
			var dir = (oState.sortedBy && oState.sortedBy.dir === Y.widget.DataTable.CLASS_DESC) ? "desc" : "asc";
			var startIndex = (oState.pagination) ? oState.pagination.recordOffset : 0;
			var results = (oState.pagination) ? oState.pagination.rowsPerPage : null;

			return "ddd=" + new Date().getTime() + "&sort=" + sort + "&dir=" + dir + "&startIndex=" + startIndex + ((results !== null) ? "&results=" + results : "");
		},
		validator : Y.lang.isFunction
	});
	
	Y.Mensajes.RegistrosDtbl.myDataTable.subscribe("editorSaveEvent", function(oArgs){
            try {
                var oRecord = oArgs.editor.getRecord();
                if (oArgs.newData != oArgs.oldData) {
                    if (oArgs.oldData == 'A' ) {
                        var callback = new Y.AsyncCallBack();
                        callback.processResult = function(messages){
							oRecord.setData("modificado", true);
							Y.Mensajes.RegistrosDtbl.myDataTable.render();                    
                        };
                        callback.processResultUnsuccess = function(messages){
							oRecord.setData("status", oArgs.oldData);
							oRecord.setData("modificado", false);
							Y.Mensajes.RegistrosDtbl.myDataTable.render();
							var msg = '<div><center>';
							msg += 'El mensaje esta asociado a una notificicaci&oacute;n.';
							msg += '</center></div>';
							new Y.MessagePnl().showmsg({
								msg : msg,
								fnSubmit : function() {
								}
							});         
						};
                        var sUrl = Y.Mensajes.AsociadoURL;
                        var postData = "idNotificacion=" + oRecord.getData("idNotificacion") +"&idMensaje="+oRecord.getData("idMensaje");
                        var request = Y.util.Connect.asyncRequest('POST', sUrl, callback, postData);
						
                    }
                }
                
            } catch (e) {
                alert("editorSaveEvent:" + e + " " + e.description)
            }
        });
		
}

function initDtblAlta() {

	Y.Mensajes.AltaDtbl = new Y.Dtbl();
	Y.Mensajes.AltaDtbl.setUrl(Y.Mensajes.DetalleDtblURL);
	Y.Mensajes.AltaDtbl.setRowMouse(true);
	
	Y.Mensajes.AltaDtbl.setDtblContainer('dtbl.Cont.RegistrosAlta');
	Y.Mensajes.AltaDtbl.setPgntContainer('dtbl.Pgnt.RegistrosAlta');
	Y.Mensajes.AltaDtbl.setPgntTotalContainer('dtbl.Ttal.RegistrosAlta');
	Y.Mensajes.AltaDtbl.setPgntRecords(3);
	Y.Mensajes.AltaDtbl.setFields([ "variable", "descripcion", "longitud", "nuevo", "modificado" ]);
	var columns = [ {
		key : "variable",
		label : "VARIABLE"
	}, {
		key : "descripcion",
		label : "DESCRIPCION"
	}, {
		key : "longitud",
		label : "LONGITUD"
	} ];

	Y.Mensajes.AltaDtbl.setColumnsDefs(columns);

	// Indicamos que las columnas del datatable tienen un editor relacionado
	Y.Mensajes.AltaDtbl.setEditor(true);
	/**
	 * Se construye el objecto datatable interno con los parametros asignados
	 * previamente
	 */
	Y.Mensajes.AltaDtbl.construct();

	Y.Mensajes.AltaDtbl.myDataTable.subscribe("rowClickEvent", function(oArgs) {
		var target = oArgs.target;
		var record = this.getRecord(target);
		if (record != null) {
			
			var f = document.getElementById("AltaMensaje");
			f.elements['mensaje.mensaje'].value += "${"+record.getData("variable")+"}";
			cuenta();
			//suma(record.getData("longitud"));
		}
	});
	
	Y.Mensajes.AltaDtbl.insertar = function(rows) {
		try {
			var dtbl = this.myDataTable;
			if (Y.lang.isArray(rows)) {
				for ( var j = 0; j < rows.length; j++) {
					var m = rows[j];
					try {
						var oData = {};
						
						oData['variable'] = m.variable;
						oData['descripcion'] = m.descripcion;
						oData['longitud'] = m.longitud;

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

}

function cuenta(){
	var f = document.getElementById("AltaMensaje");
	document.getElementById("chars").innerHTML = f.elements['mensaje.mensaje'].value.length;	
}



Y.util.Event.onDOMReady(function() {

	Y.btnBuscar = new Y.widget.Button("buscar", {
		onclick : {
			fn : function() {
				buttonBusquedaClick();
			}
		},
		disabled : true
	});
	Y.btnGuardar = new Y.widget.Button("guardar", {
		onclick : {
			fn : function() {
				GuardarBtnClick();
			}
		},
		disabled : false
	});
	Y.btnAgregar = new Y.widget.Button("agregar", {
		onclick : {
			fn : function() {
				DetalleDlgNuevo();
			}
		},
		disabled : false
	});

	Y.btnGuardarDetalle = new Y.widget.Button("guardardetalle", {
		onclick : {
			fn : function() {
				Y.DetalleDlg.hide();
			}
		},
		disabled : false
	});
	Y.btnGuardarDetalle = new Y.widget.Button("cancelardetalle", {
		onclick : {
			fn : function() {
				Y.DetalleDlg.hide();
			}
		},
		disabled : false
	});

	Y.DetalleDlg = new Y.widget.Panel("detalle", {
		width : "30em",
		height : "auto",
		autofillheight : "bd",
		fixedcenter : "contained",
		modal : true,
		visible : false,
		constraintoviewport : true,
		draggable : true
	});
	var kl = new Y.util.KeyListener(document, {
		keys : 27
	}, {
		fn : Y.DetalleDlg.hide,
		scope : Y.DetalleDlg,
		correctScope : true
	});
	kl.enable();

	Y.DetalleDlg.render();

	

	Y.DetalleAltaDlg = new Y.widget.Panel("alta", {
		width : "30em",
		height : "auto",
		autofillheight : "bd",
		fixedcenter : "contained",
		modal : true,
		visible : false,
		constraintoviewport : true,
		draggable : true
	});
	var kl = new Y.util.KeyListener(document, {
		keys : 27
	}, {
		fn : Y.DetalleAltaDlg.hide,
		scope : Y.DetalleAltaDlg,
		correctScope : true
	});
	kl.enable();

	Y.DetalleAltaDlg.render();

	Y.btnGuardarDlg = new Y.widget.Button("guardaralta", {
		onclick : {
			fn : function() {
				DetalleAceptarBtnClick();
				// Y.DetalleAltaDlg.hide();
			}
		},
		disabled : false
	});

	Y.btnCancelarDlg = new Y.widget.Button("cancelaralta", {
		onclick : {
			fn : function() {
				Y.DetalleAltaDlg.hide();
				Y.publishMsg("div.mensaje", "");
				Y.publishMsg("div.lob", "");
				Y.publishMsg("div.proceso", "");
				Y.publishMsg("div.asunto", "");
				Y.publishMsg("div.mensaje.cuerpo", "");
				Y.publishMsg("div.medio", "");
				Y.publishMsg("div.descripcion", "");
				
			}
		},
		disabled : false
	});
});

function DetalleDlgNuevo() {
	try {
		Y.publishMsg("div.mensaje", "");
		Y.publishMsg("div.lob", "");
		Y.publishMsg("div.proceso", "");
		Y.publishMsg("div.asunto", "");
		Y.publishMsg("div.mensaje.cuerpo", "");
		Y.publishMsg("div.medio", "");
		Y.publishMsg("div.descripcion", "");

		var f = document.getElementById("AltaMensaje");

		f.elements['mensaje.idLob'].style.display = 'block';
		f.elements['mensaje.idLobDet'].style.display = 'none';

		// f.elements['mensaje.idProceso'].value = record.getData('idProceso');
		f.elements['mensaje.idProceso'].style.display = 'block';
		f.elements['mensaje.idProcesoDet'].style.display = 'none';
		
		f.elements['record.id'].value = '';
		f.elements['mensaje.idMensaje'].value = '';
		f.elements['mensaje.idMensaje'].disabled = false;
		f.elements['mensaje.descripcion'].value = '';
		f.elements['mensaje.idLob'].value = '';
		f.elements['mensaje.idProceso'].value = '';
		//f.elements['mensaje.status'].value = '';
		f.elements['mensaje.medio'].value = '';
		f.elements['mensaje.medio'].disabled = false;
		f.elements['mensaje.asunto'].value = '';
		f.elements['mensaje.mensaje'].value = '';
		cuenta();
		Y.DetalleAltaDlg.setHeader("Nuevo Mensaje");
		//Y.DetalleAltaDlg.center();
		Y.DetalleAltaDlg.show();
		Y.DetalleAltaDlg.center();
	} catch (e) {
		alert("DetalleDlgNuevo:" + e.description);
	}
};

function BuscarProcesos(dlg) {
	try {
		var detalle = false;
		if (dlg == undefined || dlg == '') {
			var f = document.getElementById("formBusqueda");
			var elSel = f.elements['proceso'];
			var lob = f.elements['lob'].value;
		} else {
			detalle=true;
			var f = document.getElementById("AltaMensaje");
			var elSel = f.elements['mensaje.idProceso'];
			var lob = f.elements['mensaje.idLob'].value;
		}

		botonBusqueda();

		var callback = new Y.AsyncCallBack();
		callback.processResult = function(messages) {
			clearAndFillSelect(messages.select, elSel, '');
			var f = getForm("formBusqueda");
			if(detalle){
				getForm("AltaMensaje").elements['mensaje.idProceso'].disabled = false;;
			}else{
				f.elements['proceso'].disabled = false;
			}
			
			Y.btnBuscar.set("disabled", false);
		};

		var sUrl = Y.Mensajes.ProcesosURL;
		var postData = "lob=" + lob;
		var request = Y.util.Connect.asyncRequest('POST', sUrl, callback, postData);

	} catch (x) {
		alert("Y.Mensajes.BuscaProcesos :" + x.description);
	}
}

function buttonBusquedaClick() {
	try {
		var formMensaje = document.getElementById("MensajesForm");
		var formBusque = document.getElementById("formBusqueda");
		var callback = new Y.AsyncCallBack();
		callback.processResult = function(messages) {

			Y.Mensajes.RegistrosDtbl.clear();
			Y.Mensajes.RegistrosDtbl.insertar(messages.rows);

		}
		formMensaje.elements['lob'].value = formBusque.elements['lob'].value;
		formMensaje.elements['status'].value = formBusque.elements['status'].value;
		formMensaje.elements['proceso'].value = formBusque.elements['proceso'].value;
		formMensaje.elements['medio'].value = formBusque.elements['medio'].value;
		Y.util.Connect.setForm(formMensaje);
		Y.showWait();
		var request = Y.util.Connect.asyncRequest('POST', Y.Mensajes.FiltrarURL, callback);
	} catch (e) {
		alert("buttonBusquedaClick: " + e.description);
	}
}

function updTablaVariables(proceso) {
	try {
		

		var callback = new Y.AsyncCallBack();
		callback.processResult = function(messages) {
			Y.Mensajes.AltaDtbl.clear();
			Y.Mensajes.AltaDtbl.insertar(messages.rows);
						
		}
		
		
		var sUrl = Y.Mensajes.DetalleDtblURL;
        var postData = "proceso=" + proceso;
        var request = Y.util.Connect.asyncRequest('POST', sUrl, callback, postData);
	} catch (e) {
		alert("buttonBusquedaClick: " + e.description);
	}
}

function DetalleDlgEditar(record) {

	try {
		Y.publishMsg("div.mensaje", "");
		Y.publishMsg("div.lob", "");
		Y.publishMsg("div.proceso", "");
		Y.publishMsg("div.asunto", "");
		Y.publishMsg("div.mensaje.cuerpo", "");
		Y.publishMsg("div.medio", "");
		Y.publishMsg("div.descripcion", "");
		
		var f = document.getElementById("AltaMensaje");
		
		f.elements['record.id'].value = record.getId();

		f.elements['mensaje.idMensaje'].value = record.getData('idMensaje');
		f.elements['mensaje.idMensaje'].disabled = true;
		f.elements['mensaje.descripcion'].value = record.getData('descripcion');

		// f.elements['mensaje.idLob'].value = record.getData('idlob');

		f.elements['mensaje.idLob'].style.display = 'none';
		f.elements['mensaje.idLobDet'].style.display = 'block';
		f.elements['mensaje.idLobDet'].value = record.getData('lob');
		f.elements['lob'].value = record.getData('idlob');
		
		f.elements['mensaje.idProceso'].value = record.getData('idProceso');
		f.elements['mensaje.idProceso'].style.display = 'none';
		f.elements['mensaje.idProcesoDet'].style.display = 'block';
		f.elements['mensaje.idProcesoDet'].value = record.getData('proceso');
		f.elements['proceso'].value = record.getData('idProceso');

		//f.elements['mensaje.status'].value = record.getData('status');
		//f.elements['mensaje.status'].disabled = true;
		f.elements['mensaje.medio'].value = record.getData('medio');
		f.elements['medio'].value = record.getData('medio');
		f.elements['mensaje.medio'].disabled = true;
		if(record.getData('medio')=='SM'){
			f.elements['mensaje.asunto'].disabled=true;
		}else{
			f.elements['mensaje.asunto'].disabled=false;
		}
		f.elements['mensaje.asunto'].value = record.getData('asunto');
		f.elements['mensaje.mensaje'].value = record.getData('mensaje');

		cuenta();
		updTablaVariables(record.getData('idProceso'));
		
		
		Y.Mensajes.AltaDtbl.clear();
		//Y.Mensajes.AltaDtbl.insertar(messages.rows);
		
		Y.DetalleAltaDlg.setHeader("Detalle Mensaje");
		Y.DetalleAltaDlg.show();
		Y.DetalleAltaDlg.center();

	} catch (e) {
		alert("DetalleDlgEditar:" + e.description);
	}

}

function DetalleAceptarBtnClick() {
	try {
		Y.publishMsg("div.mensaje", "");
		Y.publishMsg("div.lob", "");
		Y.publishMsg("div.proceso", "");
		Y.publishMsg("div.asunto", "");
		Y.publishMsg("div.mensaje.cuerpo", "");
		Y.publishMsg("div.medio", "");
		Y.publishMsg("div.descripcion", "");
		
		var f = document.getElementById("AltaMensaje");
		if (f.elements['record.id'].value == '') {
			// valida un registro nuevo
			f.elements['mensaje.nuevo'].value = true;
		} else {
			f.elements['mensaje.nuevo'].value = false;
		}
		
		var callback = new Y.AsyncCallBack();
		callback.processResult = function(messages) {
			var index;
			var recordId = f.elements['record.id'].value;
			var oData = {};

			oData['recordId'] = recordId;
			oData['idMensaje'] = f.elements['mensaje.idMensaje'].value;
			oData['descripcion'] = f.elements['mensaje.descripcion'].value;

			index = f.elements['mensaje.idLob'].selectedIndex;
			if (recordId == '') {
				if (index == -1) {
					oData['lob'] = " ";
				} else {
					oData['lob'] = f.elements['mensaje.idLob'].options[index].text;
					oData['idlob'] = f.elements['mensaje.idLob'].value;
				}
			} else {
				if (index == -1) {
					oData['lob'] = " ";
				} else {
					oData['lob'] = f.elements['mensaje.idLobDet'].value;
					oData['idLob'] = f.elements['lob'].value;
				}
			}

			oData['medio'] = f.elements['mensaje.medio'].value;
				
			
			index = f.elements['mensaje.idProceso'].selectedIndex;
			if (recordId == '') {
				if (index == -1) {
					oData['proceso'] = " ";
				} else {
					oData['proceso'] = f.elements['mensaje.idProceso'].options[index].text;
					oData['idProceso'] = f.elements['mensaje.idProceso'].value;
				}
			} else {

					oData['proceso'] = f.elements['mensaje.idProcesoDet'].value;
					oData['idProceso'] = f.elements['proceso'].value;
			}
			//oData['status'] = f.elements['mensaje.status'].value;
			oData['asunto'] = f.elements['mensaje.asunto'].value;
			oData['mensaje'] = f.elements['mensaje.mensaje'].value;
			oData['status'] = 'A';
			
			if (recordId == '') {
				Y.Mensajes.RegistrosDtbl.insertarRegistro(oData);
			} else {
				Y.Mensajes.RegistrosDtbl.actualizarRegistro(oData);

			}
			Y.DetalleAltaDlg.hide();

		};

		Y.util.Connect.setForm(f);
		Y.showWait();
		var request = Y.util.Connect.asyncRequest('POST', Y.Mensajes.ValidarURL, callback);

	} catch (e) {
		alert("DetalleAceptarBtnClick: " + e.description);
	}
};

function GuardarBtnClick() {
	try {
		var f = document.getElementById("MensajesJsonForm");

		f.elements["jsonrecords"].value = Y.Mensajes.RegistrosDtbl.getJsonRecords();

		var callback = new Y.AsyncCallBack();
		callback.processResult = function(messages) {
			var msg = '<div><center>';
			msg += 'Los cambios se guardaron correctamente.';
			msg += '</center></div>';
			new Y.MessagePnl().showmsg({
				msg : msg,
				fnSubmit : function() {
					Y.Mensajes.RegistrosDtbl.update();
				}
			});
		};

		Y.util.Connect.setForm(f);
		Y.showWait();
		var request = Y.util.Connect.asyncRequest('POST', Y.Mensajes.GuardarCambiosURL, callback);

	} catch (e) {
		alert("guardargeneral: " + e.description);
	}
};

function validaEnvio(algo) {
	var f = document.getElementById("AltaMensaje");
	if (algo.value == 'SM') {
		f.elements['mensaje.asunto'].value = '';
		f.elements['mensaje.asunto'].disabled = true;
	} else {
		f.elements['mensaje.asunto'].disabled = false;
	}
}

function botonBusqueda(){
    var formBusque = document.getElementById("formBusqueda");
    
	 var lob = formBusque.elements['lob'].value;
     var status = formBusque.elements['status'].value;
     var proceso = formBusque.elements['proceso'].value;
     var medio = formBusque.elements['medio'].value;
    
	if(lob == '' && status == '' && proceso == '' && medio == ''){
		Y.btnBuscar.set("disabled", true);
	}else{
		Y.btnBuscar.set("disabled", false);
	}
}
