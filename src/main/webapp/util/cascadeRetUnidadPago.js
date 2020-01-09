var busquedaExito = false;
var confirmar = function(fn, fn2) {
	try {
		
			confirmardlg = new Y.widget.SimpleDialog(
					"confirmarSimpleDialog",
					{
						width : "400px",
						fixedcenter : true,
						visible : false,
						draggable : false,
						close : true,
						modal : true,
						text : "Se Limpiar&aacute; Retenedor y la unidad de Pago. <br>&iquest;Desea continuar?",
						icon : Y.widget.SimpleDialog.ICON_HELP,
						constraintoviewport : true,
						buttons : [ {
							text : "Aceptar",
							handler : function() {
								try {
									confirmardlg.hide();
									fn();
									if(fn2){
										fn2();
									}
								} catch (e) {
									alert("siContinuar: " + e);
								}
							},
							isDefault : true
						}, {
							text : "Cancelar",
							handler : function() {
								try {
									confirmardlg.hide();
								} catch (e) {
									alert("noContinuar: " + e);
								}
							}
						} ]
					});
			confirmardlg.setHeader("Confirmaci&oacute;n");
			confirmardlg.render(document.body);
		
			confirmardlg.show();

	} catch (e) {
		alert("confirmar: " + e);
	}
}

function initComboRetenedor() {
	var retenedorCombo = document.getElementById('retenedor');
	var callback = new Y.AsyncCallBack();
	callback.processResult = function(messages){
		try {
			var result = messages.ResultSet.Result;
			insertaComboValues(retenedorCombo, result);
		} catch (e) {
			alert(e);
		}
	};
	callback.processResultUnsuccess = function(messages) {
	};
	showWait();
	var sUrl = Y.Busqueda.Retenedores;
    var request = Y.util.Connect.asyncRequest('POST', sUrl, callback);
	retenedorCombo.onchange = function() {
		setValuesUnidadPago();
	}
}

function setValuesUnidadPago(unidadDePago, retenedor) {
	cleanCombo(unidadDePago)
	var unidadPago = document.getElementById(unidadDePago);
	var retenedorCombo = document.getElementById(retenedor);
	if (!retenedorCombo.value == 'undefined' || !retenedorCombo.value == '') {

		var callback = new Y.AsyncCallBack();
		callback.processResult = function(messages){
			try {
				var result = messages.ResultSet.Result;

				insertaComboValues(unidadPago, result);

			} catch (e) {
				alert(e);
			}
		};
		callback.fallo = function(messages) {

		};
		//Z.ShowWait();
		try{
		if (!isNaN(val)) {
	    	var sUrl = Y.Busqueda.UnidadesPago;
	        var postData = "retenedor=" + retenedorCombo.value;
	        var request = Y.util.Connect.asyncRequest('POST', sUrl, callback, postData);
		}
		}catch(e){}
		retenedorCombo.onchange = function() {
			setValuesUnidadPago(unidadDePago, retenedor);
		}
	}
}

function cleanCombo(id, btnClean) {
	var selectObj = document.getElementById(id);
	var selectParentNode = selectObj.parentNode;
	var newSelectObj = selectObj.cloneNode(false); // Make a shallow copy
	selectParentNode.replaceChild(newSelectObj, selectObj);
	document.getElementById(id).disabled = true;

	insertaComboValues(document.getElementById(id), {});

	/*var combo = document.getElementById(id)
	var newOption = document.createElement('option');
	newOption.value = '';
	newOption.text = 'Seleccione';
	try {
		combo.options.add(newOption);
	} catch (ex) {
		combo.options.add(newOption);
	}*/

	if (btnClean) {
		document.getElementById(btnClean).disabled = true;
	}
}

function insertaComboValues(combo, listaValores) {

	for ( var i in listaValores) {
		var newOption = document.createElement('option');
		newOption.value = listaValores[i].value;
		newOption.text = listaValores[i].label;
		try {
			combo.options.add(newOption);
		} catch (ex) {
			combo.options.add(newOption);
		}
	}

}

function cleanRetUp(idRetenedor, idUnidadDePagp, idRetenedorHidden, btnClean,fn2) {
	
	document.getElementById(idRetenedor).onclick = function() {
		if (document.getElementById(idRetenedor).value != "") {
			confirmar(function() {
				document.getElementById(idRetenedor).value = "";
				document.getElementById(idRetenedorHidden).value = "";
				cleanCombo(idUnidadDePagp, btnClean);
			},fn2)
		}
	}
	if (btnClean != undefined) {
		try {
				document.getElementById(btnClean).onclick = function() {
				if (document.getElementById(idRetenedor).value != "") {
					confirmar(function() {
						document.getElementById(idRetenedor).value = "";
						document.getElementById(idRetenedorHidden).value = "";
						cleanCombo(idUnidadDePagp, btnClean);
					},fn2)
				}
			}
		} catch (e) {
			alert('Ocurrio un Error en la asignacio del bton Clean: ' + e);
		}
	}

}

var autocomPletaRetUP = function(idRetenedor, idUnidadDePagp,
		idRetenedorHidden, btnClean, fn2) {
	
setTimeout(function(){
	
	cleanRetUp(idRetenedor, idUnidadDePagp, idRetenedorHidden, btnClean, fn2);
	cleanCombo(idUnidadDePagp, btnClean);

	$(function() {
		function change(val) {
			cleanCombo(idUnidadDePagp, btnClean);
			document.getElementById(idUnidadDePagp).disabled = false;
			document.getElementById(btnClean).disabled = false;
			var unidadPago = document.getElementById(idUnidadDePagp);
			var retenedorCombo = document.getElementById(idRetenedor);

			var callback = new Y.AsyncCallBack();
			callback.processResult = function(messages){
				try {
					var result = messages.ResultSet.Result;

					insertaComboValues(unidadPago, result);

					if (idRetenedorHidden) {
						if (document.getElementById(idRetenedorHidden)) {
							document.getElementById(idRetenedorHidden).value = val;
						}
					}

				} catch (e) {
					alert("autocomPletaRetUP: " + e);
				}
			};
			callback.processResultUnsuccess = function(messages){
				if (idRetenedorHidden) {
					if (document.getElementById(idRetenedorHidden)) {
						document.getElementById(idRetenedorHidden).value = '';
					}
				}
			};
			//Z.ShowWait();

			if (!isNaN(val)) {

				var sUrl = Y.Busqueda.UnidadesPago;
		         var postData = "retenedor=" + val;
		         var request = Y.util.Connect.asyncRequest('POST', sUrl, callback, postData);
				retenedorCombo.onchange = function() {
					setValuesUnidadPago(idUnidadDePagp, idRetenedor);
				}
			}
		}
		$("#" + idRetenedor).autocomplete({
			source : Y.Busqueda.Retenedores,
			select : function(event, ui) {
				change(ui.item.id);
			}
		});
	});
},0);
}