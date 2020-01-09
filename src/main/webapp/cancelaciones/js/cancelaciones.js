function limpiaRet(){
    var r1 = document.getElementById("retenedor");
	var unidadesPago = document.getElementById("unidadPago");
	var f = document.getElementById("cancelaNotif");
	f.elements["idRetenedor"].value = "";
    r1.value = '';
    unidadesPago.length = 0;
}
function limpiaRetCT(){
    var r1 = document.getElementById("retenedorCT");
    var unidadesPagoCT = document.getElementById('unidadPagoCt');
    var f = document.getElementById("cancelaNotif");
    f.elements["idRetenedorCT"].value = "";
    r1.value = '';
    unidadesPagoCT.length = 0;
}

function validateFormSearch(fechaInicial, fechaFin){
	var valida = true;
	Y.publishMsg("div.Canceladas","");
	
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
    	var f = document.getElementById("cancelaNotif");
    	var retenedor = document.getElementById("retenedor").value;
    	var unidadesPago = document.getElementById("unidadPago");
    	Y.publishMsg("div.Canceladas","");
    	var noNumber = isNaN(retenedor);
    	if(retenedor=="" || retenedor=="No se obtuvieron resultados" || noNumber == true){
    		var r1 = document.getElementById("retenedor");
    	    r1.value = '';
    		Y.publishMsg("div.retenedor.empty","Debe indicar el Retenedor correcto a consultar");
    		limpiaRet();
    	}else{
    		Y.publishMsg("div.retenedor.empty","");
    		var callback = new Y.AsyncCallBack();
        	callback.processResult = function(messages){
        		document.getElementById("retenedor").value = messages.retainerDescripcion;
        		clearAndFillSelectWS(messages.selectUnidadesPago, unidadesPago);
        		f.elements["idRetenedor"].value = messages.retainerkey;
        		
            };
            showWait();
            var sUrl = Y.Cancelaciones.RetenedoresURL;
            var postData = "retenedor=" + retenedor;
            var request = Y.util.Connect.asyncRequest('POST', sUrl, callback, postData);
    	}
        
    } catch (e) {
        alert("buttonBusquedaClick: " + e.description);
    }
}

function buttonBusquedaCtClick(){
    try {
    	var f = document.getElementById("cancelaNotif");
    	var retenedorCT = document.getElementById("retenedorCT").value;
    	var unidadesPagoCT = document.getElementById("unidadPagoCt");
    	Y.publishMsg("div.Canceladas","");
    	var noNumber = isNaN(retenedorCT);
    	if(retenedorCT=="" || retenedorCT=="No se obtuvieron resultados" || noNumber == true){
    		var r1 = document.getElementById("retenedorCT");
    		r1.value = '';
    		Y.publishMsg("div.retenedorCT.empty","Debe indicar el Retenedor CT correcto a consultar");
    		limpiaRetCT();
    	}else{
    		Y.publishMsg("div.retenedorCT.empty","");
    		var callback = new Y.AsyncCallBack();
        	callback.processResult = function(messages){
        		document.getElementById("retenedorCT").value = messages.retainerDescripcion;
        		clearAndFillSelectWS(messages.selectUnidadesPago, unidadesPagoCT);
        		f.elements["idRetenedorCT"].value = messages.retainerkey;
        		
            };
            showWait();
            var sUrl = Y.Cancelaciones.RetenedoresURL;
            var postData = "retenedor=" + retenedorCT;
            var request = Y.util.Connect.asyncRequest('POST', sUrl, callback, postData);
    	}
        
    } catch (e) {
        alert("buttonBusquedaCtClick: " + e.description);
    }
}
function guardarClick(){
    try {
    	var f = document.getElementById("cancelaNotif");
    	var fechaIni = document.getElementById('fecInicio').value;
    	var fechaFin = document.getElementById('fecFin').value;
    	var retenedorId = f.elements["idRetenedor"].value;
    	var retenedorCtId =  f.elements["idRetenedorCT"].value;
    	var inidadPagoId = "";
    	var inidadPagoCTId = "";
    	if(retenedorId != ""){
    		var up = document.getElementById('unidadPago').options[document.getElementById('unidadPago').selectedIndex].value;
    		if(up != "0"){
    			inidadPagoId = up;
    		}    		
    	}
    	if(retenedorCtId != ""){
    		var upCt = document.getElementById('unidadPagoCt').options[document.getElementById('unidadPagoCt').selectedIndex].value;
    		if(upCt != "0"){
    			inidadPagoCTId = upCt;
    		}
    	}
    	var valida = validateFormSearch(fechaIni, fechaFin);
    	
    	if(valida == true){
    		var answer = confirm("¿DESEA ENVIAR LA CANCELACIÓN?");
        	if(answer){
        		var callback = new Y.AsyncCallBack();
            	callback.processResult = function(messages){
            		Y.publishMsg("div.Canceladas",messages.canceladas);
                };
                showWait();
                var sUrl = Y.Cancelaciones.CancelaNotURL;
                var postData = "retenedor=" + retenedorId 
                				+ "&retenedorCT=" + retenedorCtId 
                				+ "&unidadPago=" + inidadPagoId 
                				+ "&unidadPagoCT=" + inidadPagoCTId
                				+ "&fechaInicio=" + fechaIni
                				+ "&fechaFin=" + fechaFin;
                var request = Y.util.Connect.asyncRequest('POST', sUrl, callback, postData);
        	}else{
        		alert("Se ha detenido el proceso");
        	}
    	}
    } catch (e) {
        alert("guardarClick: " + e.description);
    }
}


Y.util.Event.onDOMReady(function(){
	var TCCalendar = new Y.PladCalendar();
    TCCalendar.setButtonId('show1up'); //id del button que activara el calendario de fecha inicio
    TCCalendar.setShortDateId('fecInicio'); //id del input donde se guardara la fecha en formato dd/mm/yyyy
    TCCalendar.setLargeDateId('fecInicio');//id del input donde se guardara la fecha en formato dia mes anio
    TCCalendar.setDialogId('fechaWCCDlg');//id del elemento donde se construira el dialogo para el calendario
    TCCalendar.setDialogTitle('Fecha de Inicio');//titulo de la ventana del dialogo
    TCCalendar.setMinDate('01/01/1900'); //los dias anteriores a esta fecha no estaran disponibles, la fecha por default es 1/1/1900
    TCCalendar.setNavConfig(true);
    
    TCCalendar.construct();
    
    var enteredCalendar = new Y.PladCalendar();
    enteredCalendar.setButtonId('show');//id del button que activara el calendario de fecha fin
    enteredCalendar.setShortDateId('fecFin');//id del input donde se guardara la fecha en formato dd/mm/yyyy
    enteredCalendar.setLargeDateId('fecFin');//id del input donde se guardara la fecha en formato dia mes anio
    enteredCalendar.setDialogId('fechaEnteredWCDlg');//id del elemento donde se construira el dialogo para el calendario
    enteredCalendar.setDialogTitle('Fecha de Fin');//titulo de la ventana del dialogo de fecha fin
    enteredCalendar.setMinDate('01/01/1900');//los dias anteriores a esta fecha no estaran disponibles, la fecha por default es 1/1/1900
    enteredCalendar.setNavConfig(true);
    
    enteredCalendar.construct();
    
    Y.btnBuscarRet = new Y.widget.Button("buscar", {
        onclick: {
            fn: function(){
            	buttonBusquedaClick();
            }
        },
        disabled: false
    });
    
    Y.btnBuscarRetCT = new Y.widget.Button("buscarCt", {
        onclick: {
            fn: function(){
            	buttonBusquedaCtClick();
            }
        },
        disabled: false
    });

    Y.btnGuardar = new Y.widget.Button("guardar", {
        onclick: {
            fn: function(){
            	guardarClick();
            }
        },
        disabled: false
    });


});