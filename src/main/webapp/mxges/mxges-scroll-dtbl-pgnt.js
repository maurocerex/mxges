/**
 * *****************************************************************************
 * 
 */

Y.ScrollingYDtbl = function() {
};

Y.ScrollingYDtbl.prototype = {
  myDataTable : null,
  myDataSource : null,
  paginator : null,
  responseSchema : null,
  _EDITOR : false,
  _ROW_MOUSE : false,
  _ROW_SELECT : false,
  _URL : null,
  _ERROR : '<font color="red">Error de Sistema.</font>',
  _EMPTY : 'No existen registros.',
  _LOADING : '<div style="color: blue;vertical-align:middle;"><img src="' + Y.ctx + '/images/wait_icon.gif" align="middle"/>&nbsp;Cargando...</div>',
  _DBTL_CONTAINER : null,
  _PGNT_CONTAINTER : null,
  _PGTN_RECORDS : 5,
  _PGNT_TOTAL_CONTAINTER : null,
  _PGNT_ID_SEARCH : null,
  _PGNT_FN_SEARCH : null,
  _DYNAMIC : false,
  _HEIGHT : null,
  _WIDTH : null,
  _FIELDS : [ "id", "nuevo", "modificado", "eliminado", "error" ],
  _COLUMNS_DEFS : [ {
    key : "id",
    label : "DEFAULT"
  } ],
  _SORTEDBY : null,
  setDtblContainer : function(container) {
    this._DBTL_CONTAINER = container;
  },
  setPgntContainer : function(container) {
    this._PGNT_CONTAINTER = container;
  },
  setPgntTotalContainer : function(container) {
    this._PGNT_TOTAL_CONTAINTER = container;
  },
  setPgntIdSearch : function(container) {
    this._PGNT_ID_SEARCH = container;
  },   
  setPgntRecords : function(records) {
    this._PGTN_RECORDS = records;
  },
  setUrl : function(url) {
    this._URL = url;
  },
  setColumnsDefs : function(columnsDefs) {
    this._COLUMNS_DEFS = columnsDefs;
  },
  setFields : function(fields) {
    this._FIELDS = fields;
  },
  setEditor : function(flag) {
    this._EDITOR = flag;
  },
  setRowMouse : function(flag) {
    this._ROW_MOUSE = flag;
  },
  setRowSelect : function(flag) {
    this._ROW_SELECT = flag;
  },
  setEmpty : function(text) {
    this._EMPTY = text;
  },
  setDynamic : function(flag) {
    this._DYNAMIC = flag;
  },
  setHeight : function(param) {
    this._HEIGHT = param;
  },
  setWidth : function(param) {
    this._WIDTH = param;
  },
  setSortedBy : function(param) {
    this._SORTEDBY = param;
  },  
  construct : function() {
    if (!this._URL) {
      alert("Url is undefined");
      return;
    }
    if (!this._DBTL_CONTAINER) {
      alert("DtblContainer is undefined");
      return;
    }
    if (!this._PGNT_CONTAINTER) {
      alert("PgntContainer is undefined");
      return;
    }
    if (!this._PGNT_TOTAL_CONTAINTER) {
      alert("PgntTotalContainer is undefined");
      return;
    }

    this.responseSchema = {
      resultsList : "ResultSet.Result",
      fields : this._FIELDS
    };

    if (this._DYNAMIC == true) {
      this.responseSchema["metaFields"] = {
        totalRecords : "totalRecords"
      };
    }

    this.myDataSource = new Y.util.DataSource(this._URL);
    this.myDataSource.connXhrMode = "queueRequests";
    this.myDataSource.connMethodPost = true;
    this.myDataSource.responseType = Y.util.DataSource.TYPE_JSON;
    this.myDataSource.responseSchema = this.responseSchema;
    this.myDataSource.maxCacheEntries = 1;

    this.myDataSource.doBeforeParseData = function(oRequest, oFullResponse, oCallback) {
      try {
        if (hasAsyncDtblErrors(oFullResponse) == true) {
          manageAsyncDtblErrors(oFullResponse);
          return {};
        }
      } catch (e) {
        alert("doBeforeParseData: " + e.description);
      }
      return oFullResponse;
    };
    if (this._PGNT_ID_SEARCH == null) {
      this.paginator = getPladPaginator(this._PGNT_CONTAINTER, this._PGTN_RECORDS);
    } else {
      this.paginator = getPladPaginatorWithSearch(this._PGNT_CONTAINTER, this._PGTN_RECORDS, this._PGNT_ID_SEARCH);
    }

    this.paginator.subscribe('totalRecordsChange', function(e) {
      this.setTotalRecords(e.newValue);
    }, this, true);

  var myRowFormatter = function(elTr, oRecord) {
    if (oRecord.getData('modificado') == true) {
      Y.util.Dom.addClass(elTr, "modificado");
    }
    if (oRecord.getData('eliminado') == true) {
      Y.util.Dom.addClass(elTr, "eliminado");
    }
    if (oRecord.getData('nuevo') == true) {
      Y.util.Dom.addClass(elTr, "nuevo");
    }
    if (oRecord.getData('error') == true) {
      Y.util.Dom.addClass(elTr, "fontrojo");
    } else {
      Y.util.Dom.removeClass(elTr, "fontrojo");
    }

    return true;
  };

  this.oConfigs = {
    paginator : this.paginator,
    MSG_EMPTY : this._EMPTY,
    MSG_LOADING : this._LOADING,
    MSG_ERROR : this._ERROR,
    initialLoad : false,
    formatRow : myRowFormatter,
    sortedBy : this._SORTEDBY,
    MSG_SORTASC : "Ordenar Ascendente",
    MSG_SORTDESC : "Ordenar Descendente"
  };

  if (this._DYNAMIC == true) {
    this.oConfigs["dynamicData"] = true;
  }

  if (this._HEIGHT != null) {
    this.oConfigs["height"] = this._HEIGHT;
  }
  if (this._WIDTH != null) {
    this.oConfigs["width"] = this._WIDTH;
  }
  try {
    this.myDataTable = new Y.widget.ScrollingDataTable(this._DBTL_CONTAINER, this._COLUMNS_DEFS, this.myDataSource, this.oConfigs);

    if (this._EDITOR) {
      var highlightEditableCell = function(oArgs) {
        var elCell = oArgs.target;
        if (Y.util.Dom.hasClass(elCell, "yui-dt-editable")) {
          this.highlightCell(elCell);
        }
      };

      this.myDataTable.subscribe("cellMouseoverEvent", highlightEditableCell);
      this.myDataTable.subscribe("cellMouseoutEvent", this.myDataTable.onEventUnhighlightCell);
      this.myDataTable.subscribe("cellClickEvent", this.myDataTable.onEventShowCellEditor);

      /**
       * Asignara el field modificado a true, en el evento de guardar con un
       * editor de columna
       */
      this.myDataTable.subscribe("editorSaveEvent", function(oArgs) {
        var oRecord = oArgs.editor.getRecord();
        if (oArgs.newData != oArgs.oldData) {
          Y.logplad('field modificado ahora es true..');
          oRecord.setData("modificado", true);
        }
        this.render();
      });
    }

    if (this._DYNAMIC) {
      this.myDataTable.setAttributeConfig("generateRequest", {
        value : function(oState, oSelf) {
          try {
            oState = oState || {
              pagination : null,
              sortedBy : null
            };
            Y.logplad(' on generateRequest...');
            Y.logplad(' oSelf.configs.plad-postdata : ' + oSelf.configs["plad-postdata"]);
            var sort = (oState.sortedBy) ? oState.sortedBy.key : oSelf.getColumnSet().keys[0].getKey();
            var dir = (oState.sortedBy && oState.sortedBy.dir === Y.widget.DataTable.CLASS_DESC) ? "desc" : "asc";
            var startIndex = (oState.pagination) ? oState.pagination.recordOffset : 0;
            var results = (oState.pagination) ? oState.pagination.rowsPerPage : null;

            var params = "ddd=" + new Date().getTime();
            if (oSelf.configs["plad-postdata"] && oSelf.configs["plad-postdata"] !== null) {
              params += oSelf.configs["plad-postdata"];
            }

            return params + "&sort=" + sort + "&dir=" + dir + "&startIndex=" + startIndex + ((results !== null) ? "&results=" + results : "");
          } catch (e) {
            alert("generateRequest: " + e.description);
          }
        },
        validator : Y.lang.isFunction
      });
    }

    /**
     * Activa el evento de mouse over sobre cada renglon del datatable
     */
    if (this._ROW_MOUSE) {
      this.myDataTable.subscribe("rowMouseoverEvent", this.myDataTable.onEventHighlightRow);
      this.myDataTable.subscribe("rowMouseoutEvent", this.myDataTable.onEventUnhighlightRow);
    }

    /**
     * Activa el evento al seleccionar el renglon del datatable
     */
    if (this._ROW_SELECT) {
      this.myDataTable.subscribe("rowClickEvent", this.myDataTable.onEventSelectRow);
    }

    /**
     * Eventos para el scroll
     */

    this.myDataTable.subscribe("tableScrollEvent", function(oArgs) {
      try {
        // Y.logplad("tableScrollEvent...");
        // Y.logplad("oArgs.event: " + (oArgs.event));
        // Y.logplad("oArgs.target: " + (oArgs.target));
      } catch (e) {
        alert("tableScrollEvent: " + e.description);
      }
    });

  } catch (e) {
    alert("construct: " + e.description);
  }
},
setTotalRecords : function(total) {
  try {
    var container = document.getElementById(this._PGNT_TOTAL_CONTAINTER);
    if (container) {
      container.innerHTML = total;
    }
  } catch (e) {
    alert("ScrollingYDtbl on setTotalRecords: " + e.description);
  }
},
success : function(sRequest, oResponse, oPayload) {
  try {
    var dtbl = this.myDataTable;
    dtbl.onDataReturnInitializeTable(sRequest, oResponse, oPayload);
    if (this._SORTEDBY !== null){
      dtbl.set("sortedBy", this._SORTEDBY , false ); 
    }
    var pag = dtbl.get('paginator');
    if (pag) {
      if (this._DYNAMIC == true) {
        pag.set('totalRecords', oResponse.meta.totalRecords);
      } else {
        pag.set('totalRecords', oResponse.results.length);
      }
    }
  } catch (e) {
    alert("ScrollingYDtbl on success: " + e.description);
  }
},
failure : function(sRequest, oResponse, oPayload) {
  try {
    var dtbl = this.myDataTable;
    if (dtbl.getRecordSet().getLength() > 0) {
      dtbl.deleteRows(dtbl.getRecordSet().getLength() - 1, -1 * dtbl.getRecordSet().getLength());
    }
    dtbl.showTableMessage(this._ERROR + " <br/> " + parseErrorMsg(oResponse), "errorMessage");
    var pag = dtbl.get('paginator');
    if (pag) {
      pag.set('totalRecords', 0);
    }
  } catch (e) {
    alert("ScrollingYDtbl on failure: " + e.description);
  }
},
update : function() {
  try {
    var dtbl = this.myDataTable;
    if (dtbl.getRecordSet().getLength() > 0) {
      dtbl.deleteRows(dtbl.getRecordSet().getLength() - 1, -1 * dtbl.getRecordSet().getLength());
    }
    dtbl.showTableMessage(this._LOADING);
    var pag = dtbl.get('paginator');
    if (pag) {
      pag.set('totalRecords', 0);
    }
    var postdata = "";
    postdata += "ddd=" + new Date().getTime();
    if (pag) {
      postdata += "&results=" + pag.get('rowsPerPage');
    }
    postdata += this.getPostData();

    var callback2 = {
      success : this.success,
      failure : this.failure,
      scope : this
    };
    this.myDataSource.sendRequest(postdata, callback2);
  } catch (x) {
    alert("update:" + x.description);
  }
},
updateCifras : function(algo) {
  try {
    var dtbl = this.myDataTable;
    if (dtbl.getRecordSet().getLength() > 0) {
      dtbl.deleteRows(dtbl.getRecordSet().getLength() - 1, -1 * dtbl.getRecordSet().getLength());
    }
    dtbl.showTableMessage(this._LOADING);
    var pag = dtbl.get('paginator');
    if (pag) {
      pag.set('totalRecords', 0);
    }
    var postdata = "";
    postdata += "ddd=" + new Date().getTime();
    if (pag) {
      postdata += "&results=" + pag.get('rowsPerPage');
    }
	postdata +=algo;
    postdata += this.getPostData();

    var callback2 = {
      success : this.success,
      failure : this.failure,
      scope : this
    };
    this.myDataSource.sendRequest(postdata, callback2);
  } catch (x) {
    alert("update:" + x.description);
  }
},
getPostData : function() {
  return "";
},
isChanged : function() {
  try {
    var dtbl = this.myDataTable;
    var nRecordsLength = dtbl.getRecordSet().getLength();
    var record;
    for (i = 0; i < nRecordsLength; i++) {
      record = dtbl.getRecordSet().getRecord(i);
      if (record) {
        if (record.getData("nuevo") == true) {
          return true;
        }
        if (record.getData("modificado") == true) {
          return true;
        }
        if (record.getData("eliminado") == true) {
          return true;
        }
      }
    }
    return false;
  } catch (x) {
    alert("isChanged: " + x.description);
  }
},
clear : function() {
  try {
    var dtbl = this.myDataTable;
    dtbl.unselectAllRows();
    if (dtbl.getRecordSet().getLength() > 0) {
      dtbl.deleteRows(dtbl.getRecordSet().getLength() - 1, -1 * dtbl.getRecordSet().getLength());
    }
    var pag = dtbl.get('paginator');
    if (pag) {
      pag.set('totalRecords', 0);
    }
  } catch (x) {
    alert("clear: " + x.description);
  }
},
getJsonRecords : function() {
  try {
    var dtbl = this.myDataTable;
    var recordSet = dtbl.getRecordSet();
    var arrayTemp = [];
    for (i = 0; i < recordSet.getLength(); i++) {
      var record = recordSet.getRecord(i);
      if (record) {
        arrayTemp.push(record.getData());
      }
    }
    return Y.lang.JSON.stringify(arrayTemp);
  } catch (x) {
    alert("getJsonRecords: " + x.description);
  }
},
size : function() {
  try {
    if (this._DYNAMIC == true) {
      alert("En un tabla dynamic el size puede no ser preciso.");
    }
    var dtbl = this.myDataTable;
    return dtbl.getRecordSet().getLength();
  } catch (x) {
    alert("size: " + x.description);
  }
}

}

/**
 * *****************************************************************************
 * 
 */
