function ReenviarBtnClick() {
	try {
		var f = document.getElementById("reporteCifras");
		var b = document.getElementById("formBusqueda");
		f.elements["fecInicio"].value = b.elements["fecInicio"].value;
		f.elements["fecFin"].value = b.elements["fecFin"].value;
		f.elements["jsonrecords"].value = Y.Cifras.RegistrosDtbl.getJsonRecords();

		var callback = new Y.AsyncCallBack();
		callback.processResult = function(messages) {
			var msg = '<div><center>';
			msg += 'Se ha iniciado el reenvio de Notificaciones.';
			msg += '</center></div>';
			new Y.MessagePnl().showmsg({
				msg : msg,
				fnSubmit : function() {
					Y.Cifras.RegistrosDtbl.update();
					Y.btnReenviar.set("disabled", true);
				}
				
			});
		};

		Y.util.Connect.setForm(f);
		Y.showWait();
		var request = Y.util.Connect.asyncRequest('POST', Y.Cifras.GuardarCambiosURL, callback);

	} catch (e) {
		alert("reenviar: " + e.description);
	}
};

function detalleRegistro(proceso, medio, fecInicio, fecFin){
	try {
		Y.showWait();
		//AQUI DEBO DE GUARDAR LOS DATOS DE LA BUSQUEDA DEL DETALLE
		var f = getForm("reporteCifras");
		f.elements['proceso'].value = proceso;
		f.elements['medio'].value = medio;
		f.elements['fecInicio'].value = fecInicio;
		f.elements['fecFin'].value = fecFin;
		
        var callback = new Y.AsyncCallBack();
        callback.processResult = function(messages){
        	Y.DetalleDlg.show();
			Y.DetalleDlg.center();
        	Y.Cifras.DetalleDtbl.clear();
        	Y.Cifras.DetalleDtbl.insertar(messages.rows);
        	Y.hideWait();
        }
        var sUrl = Y.Cifras.DetalleURL;
        var postData = "proceso=" + proceso+"&fecInicio="+fecInicio+"&medio="+medio+"&fecFin="+fecFin;
        var request = Y.util.Connect.asyncRequest('POST', sUrl, callback, postData);
    } catch (e) {
        alert("detalleRegistro: " + e.description);
    }
}
//YA NO NECESITO EL RECORD PUESTO QUE GUARDARE LOS DATOS DEL DETALLE A LA HORA DE ABRIR EL MODAL
function descargar(tipoReporte, proceso) {
	  try {
		  var f1 = getForm("formBusqueda");
	    	var procesoKey = f1.elements['proceso'].value;
		  
		  var f = getForm("reporteCifras");
		  //var proceso = f.elements['proceso'].value;
		  var medio = f.elements['medio'].value;
		  var fecInicio = f.elements['fecInicio'].value;
		  var fecFin = f.elements['fecFin'].value;
		
	    var url = "cifras-reporte!ventanita.action";
	    url += "?proceso=" + procesoKey;
	    url += "&medio=" + medio;
	    url += "&fecInicio=" + fecInicio;
	    url += "&fecFin=" + fecFin;
	    url += "&tipoReport=" + tipoReporte;
	    	    
	    var my_window = window.open("", "mywindow", "width=900,height=200,menubar=no,scrollbars=yes,resizable=yes", "true");
	    if (!my_window.closed) {
	      my_window.close();
	    }
	    
	    var my_window = window.open(url, "mywindow", "width=400,height=200,menubar=no,scrollbars=yes,resizable=yes", "true");
	 
	  } catch (e) {
	    alert("descargar: " + e.description);
	  }  
	};

	
function initDtblRegistros(){
	
	Y.Cifras.MedioFmt = function(cell, row, col, data){
        var combo = Y.Cifras.MedioCatalog;
        for (var i = 0; i < combo.length; i++) {
            if (data == combo[i].value) {
                cell.innerHTML = combo[i].label;
                return;
            }
        }
    };
    Y.Cifras.StatusFmt = function(cell, row, col, data){
        var combo = Y.Cifras.StatusCatalog;
        for (var i = 0; i < combo.length; i++) {
            if (data == combo[i].value) {
                cell.innerHTML = combo[i].label;
                return;
            }
        }
    };
    Y.Cifras.RegistrosDtbl = new Y.Dtbl();
    Y.Cifras.RegistrosDtbl.setUrl(Y.Cifras.RegistrosDtblURL);
	Y.Cifras.RegistrosDtbl.setRowMouse(true);
	Y.Cifras.RegistrosDtbl.setRowSelect(true);
    Y.Cifras.RegistrosDtbl.setDtblContainer('dtbl.Cont.Registros');
    Y.Cifras.RegistrosDtbl.setPgntContainer('dtbl.Pgnt.Registros');
    Y.Cifras.RegistrosDtbl.setPgntTotalContainer('dtbl.Ttal.Registros');
    Y.Cifras.RegistrosDtbl.setPgntRecords(10);
    Y.Cifras.RegistrosDtbl.checkFmt = function(cell, row, col, data){
        var checked = row.getData("reenvio");
        if (checked == 'true' || checked == true) {
            cell.innerHTML = '<input type="checkbox" name="ignorame" ></input>';
        }
    };
    Y.Cifras.RegistrosDtbl.downFmt = function(cell, row, col, data) {
    	  cell.innerHTML = '<button type="button" id="exportar">Exportar</button>';
    };
        
    Y.Cifras.RegistrosDtbl.setFields(["lob","idLob", "proceso", "idProceso", "medioEnvio", "status", "noRegistros","totalEnviados", "porcentaje", "reenvio", "nuevo", "modificado"]);
    var columns = [{
        key: "lob",
        label: "LOB"
    }, {
        key: "proceso",
        label: "PROCESO",
		sortable:true
    }, {
        key: "medioEnvio",
        label: "MEDIO ENVIO",
        formatter: Y.Cifras.MedioFmt
    }, {
        key: "status",
        label: "STATUS"
    }, {
        key: "noRegistros",
        label: "TOTAL REGISTROS"
    }, {
        key: "totalEnviados",
        label: "TOTAL NOTIFICACIONES ENVIADAS"
    }, {
        key: "porcentaje",
        label: "PORCENTAJE &#201;XITO(%)"
    }, {
        key: "reenvio",
        label: "REENVIO",
        formatter: Y.Cifras.RegistrosDtbl.checkFmt
    }, {
        key: "detalle",
        label: "DETALLE",
        formatter: Y.Cifras.RegistrosDtbl.downFmt //Y.Dtbl.detalleFmt
    }];
    
    Y.Cifras.RegistrosDtbl.setColumnsDefs(columns);
    
    // Indicamos que las columnas del datatable tienen un editor relacionado
    Y.Cifras.RegistrosDtbl.setEditor(true);
    /**
     * Se construye el objecto datatable interno con los parametros asignados
     * previamente
     */
    Y.Cifras.RegistrosDtbl.construct();
    
    Y.Cifras.RegistrosDtbl.myDataTable.subscribe("buttonClickEvent", function(oArgs){
        var target = oArgs.target;
        var record = this.getRecord(target);
        var f = document.getElementById("formBusqueda");
        var proceso = record.getData("idProceso");
    	var medio = record.getData("medioEnvio");		
		var fecInicio = f.elements['fecInicio'].value;
		var fecFin = f.elements['fecFin'].value;
		var tipoReporte = f.elements['report'].value;
		
    	var f = getForm("reporteCifras");
		f.elements['proceso'].value = proceso;
		f.elements['medio'].value = medio;
		f.elements['fecInicio'].value = fecInicio;
		f.elements['fecFin'].value = fecFin;

		descargar(tipoReporte, proceso);
    });
    
    Y.Cifras.RegistrosDtbl.insertar = function(rows){
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
                        oData['medioEnvio'] = m.medioEnvio;
                        oData['status'] = m.status;
                        oData['noRegistros'] = m.noRegistros;
                        oData['porcentaje'] = m.porcentaje;
                        oData['reenvio'] = m.reenvio;
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
    Y.Cifras.RegistrosDtbl.myDataTable.subscribe("checkboxClickEvent", function(oArgs){
        var target = oArgs.target;
        var record = this.getRecord(target);
        var x = target.checked;
		if(x){
        	record.setData("modificado", true);
		}else{
			record.setData("modificado", false);
		}
        var rows = Y.lang.JSON.parse(Y.Cifras.RegistrosDtbl.getJsonRecords());
		Y.btnReenviar.set("disabled", true);
        try {
            for (var j = 0; j < rows.length; j++) {
                var m = rows[j];
                if (m.modificado == true || m.modificado == "true") {
					Y.btnReenviar.set("disabled", false);
                }
            }
        } catch (x) {
            alert("check: " + x.description);
        }
    });
}

function initDtblDetalle(){

    Y.Cifras.DetalleDtbl = new Y.ScrollingYDtbl();
    //Y.Cifras.DetalleDtbl.setUrl(Y.Cifras.RegistrosDtblURL);
    Y.Cifras.DetalleDtbl.setUrl(Y.Cifras.DetalleURL);
	Y.Cifras.DetalleDtbl.setRowMouse(true);
	Y.Cifras.DetalleDtbl.setRowSelect(true);
    Y.Cifras.DetalleDtbl.setDtblContainer('dtbl.Cont.RegistrosDet');
    Y.Cifras.DetalleDtbl.setPgntContainer('dtbl.Pgnt.Registrosdet');
    Y.Cifras.DetalleDtbl.setPgntTotalContainer('dtbl.Ttal.Registrosdet');
    Y.Cifras.DetalleDtbl.setPgntRecords(5);
    
    Y.Cifras.StatusFmt = function(cell, row, col, data){
        var combo = Y.Cifras.StatusCatalog;
        for (var i = 0; i < combo.length; i++) {
            if (data == combo[i].value) {
            	cell.innerHTML = combo[i].label;
            	if(data == 'ER'){
            		cell.innerHTML = row.getData("detalleStatus");
            	}else{
            		cell.innerHTML = combo[i].label;
            	}
                return;
            }
        }
    };
    Y.Cifras.MedioFmt = function(cell, row, col, data){
        var combo = Y.Cifras.MedioCatalog;
        for (var i = 0; i < combo.length; i++) {
            if (data == combo[i].value) {
                cell.innerHTML = combo[i].label;
                return;
            }
        }
    };
    Y.Cifras.DetalleDtbl.setFields(["lob","nmcliente", "tpMensaje", "proceso", "tel", "email", "nopoliza", "refbancaria", "montorecibido", "recibosPend", "cvepromotoria", "cveagente", "retenedor", "unpago", "cntrotrabjo", "status", "nuevo", "modificado", "detalleStatus", "detalleNotificacion"]);
    var columns = [{
        key: "lob",
        label: "LOB",
		 sortable: true, width:150
    }, {
        key: "nmcliente",
        label: "NOMBRE CLIENTE",
        sortable: true, width:150
        
    }, {
        key: "tpMensaje",
        label: "TIPO MENSAJE",
        sortable: true, width:150,
        formatter: Y.Cifras.MedioFmt
        
    }, {
        key: "proceso",
        label: "PROCESO",
        sortable: true, width:150
        
    }, {
        key: "detalleNotificacion",
        label: "DESCRIPCION PROCESO",
        sortable: true, width:150
        
    }, {
        key: "tel",
        label: "TELEFONO CLIENTE",
        sortable: true, width:150
    }, {
        key: "email",
        label: "EMAIL CLIENTE",
        sortable: true, width:150
    }, {
        key: "nopoliza",
        label: "NO. POLIZA",
        sortable: true, width:150
    }, {
        key: "refbancaria",
        label: "REF. BANCARIA",
        sortable: true, width:150
    }, {
        key: "montorecibido",
        label: "MONTO RECIBIDO",
        sortable: true, width:150
    }, {
        key: "recibosPend",
        label: "RECIBOS PENDIENTES",
        sortable: true, width:150
    }, {
        key: "cvepromotoria",
        label: "CVE. PROMOTORIA",
        sortable: true, width:150
    }, {
        key: "cveagente",
        label: "CVE. AGENTE",
        sortable: true, width:150
    }, {
        key: "retenedor",
        label: "RETENEDOR",
        sortable: true, width:150
    }, {
        key: "unpago",
        label: "UNIDAD DE PAGO",
        sortable: true, width:150
    }, {
        key: "cntrotrabjo",
        label: "CENTRO DE TRABAJO",
        sortable: true, width:150
    }, {
        key: "status",
        label: "STATUS ENVIO",
        sortable: true, width:150
    }, {
        key: "status",
        label: "DETALLE",
        sortable: true, width:130,
        formatter: Y.Cifras.StatusFmt
    }];
    
    Y.Cifras.DetalleDtbl.setColumnsDefs(columns);
    Y.Cifras.DetalleDtbl.setWidth("75em");
	Y.Cifras.DetalleDtbl.setHeight("15em");
    // Indicamos que las columnas del datatable tienen un editor relacionado
    
    /**
     * Se construye el objecto datatable interno con los parametros asignados
     * previamente
     */
    Y.Cifras.DetalleDtbl.construct();
    
    Y.Cifras.DetalleDtbl.myDataTable.subscribe("buttonClickEvent", function(oArgs){
        var target = oArgs.target;
        var record = this.getRecord(target);
        //Y.DetalleDlg.show();
        alert(record.getData("idProceso"));
        
    });
    
    Y.Cifras.DetalleDtbl.insertar = function(rows){
        try {
            var dtbl = this.myDataTable;
            if (Y.lang.isArray(rows)) {
                for (var j = 0; j < rows.length; j++) {
                    var m = rows[j];
                    try {
                        var oData = {};
                        
                        oData['nmcliente'] = m.nmcliente;
                        oData['tpMensaje'] = m.tpMensaje;
                        oData['proceso'] = m.proceso;
                        oData['tel'] = m.tel;
                        oData['email'] = m.email;
                        oData['nopoliza'] = m.nopoliza;
                        oData['refbancaria'] = m.refbancaria;
                        oData['montorecibido'] = m.montorecibido;
                        oData['recibosPend'] = m.recibosPend;
                        oData['cvepromotoria'] = m.cvepromotoria;
                        oData['cveagente'] = m.cveagente;
                        oData['retenedor'] = m.retenedor;
						oData['cntrotrabjo'] = m.cntrotrabjo;
                        oData['unpago'] = m.unpago;
                        oData['status'] = m.status;
                        oData['detalleStatus'] = m.detalleStatus;
                        oData['detalleNotificacion'] = m.detalleNotificacion;
						oData['lob'] = m.lob;
						
						
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

function validateFormSearch(fechaInicial, fechaFin){
	var valida = true;
	
	if(fechaInicial == '' || fechaInicial == ' ' || fechaInicial == null){
    	Y.publishMsg("div.fecha.inicio","Seleccione Fecha Inicio");
    	valida = false;
    }else{
    	Y.publishMsg("div.fecha.inicio","");
    }        
    if(fechaFin == '' || fechaFin == ' ' || fechaFin == null){
    	Y.publishMsg("div.fecha.fin","Seleccione Fecha Fin");
    	valida = false;
    }else{
    	Y.publishMsg("div.fecha.fin","");
    }
    
    if(valida == true){
    	Y.publishMsg("div.fecha.inicio.mayor","");
    	Y.publishMsg("div.fecha.dias","");
    	var fecIni = new Date(fechaInicial);        
        var fecFin = new Date(fechaFin); 
        
        var fechaActual = new Date();
        var dd = fechaActual.getDate();
        var mm = fechaActual.getMonth()+1;
        var yy = fechaActual.getFullYear();
        
        var today = dd +"/"+ mm +"/"+ yy;
        var fActual = new Date(today);
        
    	var fInicial = fechaInicial.split("/");
        var fFinal = fechaFin.split("/");
        var fActual = today.split("/");
        
        if(fInicial[0].length == 1){
        	var dia = fInicial[0];
        	fInicial[0] = "0" + dia;
        }
        if(fFinal[0].length == 1){
        	var dia = fFinal[0];
        	fFinal[0] = "0" + dia;
        }
        if(fActual[0].length == 1){
        	var diaAct = fActual[0];
        	fActual[0] = "0" + diaAct;
        }
        if(fActual[1].length == 1){
        	var mesAct = fActual[1];
        	fActual[1] = "0" + fActual[1];
        }
        
        var fDiagIni = fInicial[2] +"/"+ fInicial[1] +"/"+ fInicial[0];
        var fDiagfin = fFinal[2] +"/"+ fFinal[1] +"/"+ fFinal[0];
        var fDiagAct = fActual[2] +"/"+ fActual[1] +"/"+ fActual[0];
        
        var fechDiagonalIncial = Date.parse(fDiagIni);
        var fechDiagonalfinal = Date.parse(fDiagfin);
        var fechDiagonalActual = Date.parse(fDiagAct);
        
        var fechDiasIni = (((fechDiagonalIncial/60)/60)/24)/1000;
        var fechDiasFin = (((fechDiagonalfinal/60)/60)/24)/1000;
        var fechDiasActual = (((fechDiagonalActual/60)/60)/24)/1000;
        
        var fechaDias = fechDiagonalfinal - fechDiagonalIncial;
        var fechaDias = (((fechaDias/60)/60)/24)/1000;
        
        if(fechDiasIni > fechDiasActual){
        	Y.publishMsg("div.fecha.inicio.mayor","la fecha inicial es mayor que la fecha actual");
        	valida = false;
        } else if(fechDiasFin > fechDiasActual){
        	Y.publishMsg("div.fecha.inicio.mayor","la fecha fin es mayor que la fecha actual");
        	valida = false;
        } else if(fechDiasIni > fechDiasFin){
        	Y.publishMsg("div.fecha.inicio.mayor","la fecha inicial es mayor que la fecha final");
        	valida = false;
        }else if(fechaDias > 30 || fechaDias < -30){
        	Y.publishMsg("div.fecha.dias","Las fechas deben tener 30 dias maximo de diferencia");
        	valida = false;
        }else{
        	Y.publishMsg("div.fecha.inicio.mayor","");
        	Y.publishMsg("div.fecha.dias","");
        }
    }
        
    return valida;
}

function buttonBusquedaClick(){
    try {
    	
    	var f = getForm("formBusqueda");
    	var procesoKey = f.elements['proceso'].value;
    	var medioKey = f.elements['medio'].value;
    	var fechaIni = f.elements['fecInicio'].value;
    	var fechaFin = f.elements['fecFin'].value;
    	var tipoReporte = f.elements['report'].value;
    	    	
    	var valida = validateFormSearch(f.elements['fecInicio'].value, f.elements['fecFin'].value);
                
        if(valida == true){
        	if(tipoReporte!=""){
        		if(tipoReporte =="01"){
        			medioKey=" ";
        		}
	        	Y.Cifras.RegistrosDtbl.getPostData = function() {
	    		    try {
	    		      var postdata  = "&proceso=" + procesoKey 
	    		      				 + "&medio=" + medioKey 
	    		      				 + "&fecInicio=" + fechaIni 
	    		      				 + "&fecFin="+fechaFin
	    		      				 + "&tipoReporte="+tipoReporte;
	    		      return postdata;
	    		    } catch (e) {
	    		      alert("getPostData:" + e.description);
	    		    }
	    		};
	        	Y.Cifras.RegistrosDtbl.update();
        	}else{
        		alert("FAVOR DE SELECCIONAR UN TIPO DE REPORTE A CONSULTAR");
        	}
        }
        
 
    } catch (e) {
        alert("buttonBusquedaClick: " + e.description);
    }
}

function habilitaMedioEnvio(){
	var f = document.getElementById("formBusqueda");
	var proceso = f.elements['proceso'].value;
	var medio = document.getElementById("idMedio");
    if(proceso=== 'PROC_REPORTE_CA' || proceso == 'PROC_REPORTE_NR') {
    	medio.disabled = true;
    } else {
    	medio.disabled = false;
    }
}

Y.util.Event.onDOMReady(function(){

    var TCCalendar = new Y.PladCalendar();
    TCCalendar.setButtonId('show1up'); //id del button que activara el calendario
    TCCalendar.setShortDateId('fecInicio'); //id del input donde se guardara la fecha en formato dd/mm/yyyy
    TCCalendar.setLargeDateId('fecInicio');//id del input donde se guardara la fecha en formato dia mes a�o
    TCCalendar.setDialogId('fechaWCCDlg');//id del elemento donde se construira el dialogo para el calendario
    TCCalendar.setDialogTitle('Fecha de Inicio');//titulo de la ventana del dialogo
    TCCalendar.setMinDate('01/01/1900'); //los dias anteriores a esta fecha no estaran disponibles, la fecha por default es 1/1/2010
    TCCalendar.setNavConfig(true);
    
    TCCalendar.construct();
    
    var enteredCalendar = new Y.PladCalendar();
    enteredCalendar.setButtonId('show');
    enteredCalendar.setShortDateId('fecFin');
    enteredCalendar.setLargeDateId('fecFin');
    enteredCalendar.setDialogId('fechaEnteredWCDlg');
    enteredCalendar.setDialogTitle('Fecha de Inicio');
    enteredCalendar.setMinDate('01/01/1900');
    enteredCalendar.setNavConfig(true);
    
    enteredCalendar.construct();
    
    Y.DetalleDlg = new Y.widget.Panel("detalle", {
        width:"71em",
		autofillheight :"bd",
		underlay: "none",
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
    
    Y.btnGuardarTwo = new Y.widget.Button("buscar", {
        onclick: {
            fn: function(){
            	buttonBusquedaClick();
            }
        },
        disabled: false
    });
    
    Y.btnReenviar = new Y.widget.Button("reenviar", {
        onclick: {
            fn: function(){
            	ReenviarBtnClick();
            }
        },
        disabled: true
    });
    
    Y.btnExportar = new Y.widget.Button("exportar", {
        onclick: {
            fn: function(){
            	descargar();
            }
        },
        disabled: false
    });
    Y.btnExportar = new Y.widget.Button("cancelar", {
        onclick: {
            fn: function(){
                Y.DetalleDlg.hide();
            }
        },
        disabled: false
    });
});


