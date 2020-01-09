package com.metlife.nm.exclusiones.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.metlife.nm.domain.JdbcDao;
import com.metlife.nm.domain.LabeValueBeanCascade;
import com.metlife.nm.domain.LabelValueBean;

public class RetenedorUnidadPagoDaoImpl extends JdbcDao implements RetenedorUnidadPagoDao{

	private String qryRetenedores;
	private String qryUnidadesDePago;
	private static List<LabeValueBeanCascade> listaRetedores = new ArrayList<LabeValueBeanCascade>();
	

	
	public List<LabelValueBean> getUndadesPago(String retenedor) {
		return this.jdbcTemplate.query(qryUnidadesDePago,new Object[]{retenedor}, LABEL_VALUE_MAPPER);
	}
	
	public static ParameterizedRowMapper<LabeValueBeanCascade> LABEL_VALUE_MAPPER_RET_UP = new ParameterizedRowMapper<LabeValueBeanCascade>() {

		public LabeValueBeanCascade mapRow(ResultSet rs, int arg1)
				throws SQLException {
			LabeValueBeanCascade result = new LabeValueBeanCascade();
			
			result.setId(rs.getString(1));
			result.setLabel(rs.getString(1)+"-"+rs.getString(2));
			
			return result;
		}
	};
	
	public static ParameterizedRowMapper<LabelValueBean> LABEL_VALUE_MAPPER = new ParameterizedRowMapper<LabelValueBean>() {

		public LabelValueBean mapRow(ResultSet rs, int arg1)
				throws SQLException {
			LabelValueBean result = new LabelValueBean();
			
			result.setValue(rs.getString(3)+"-"+rs.getString(1));
			result.setLabel(rs.getString(3)+"-"+rs.getString(1)+"-"+rs.getString(2));
			
			return result;
		}
	};
	
	public void setQryRetenedores(String qryRetenedores) {
		this.qryRetenedores = qryRetenedores;
	}
	public void setQryUnidadesDePago(String qryUnidadesDePago) {
		this.qryUnidadesDePago = qryUnidadesDePago;
	}
	
	public List<LabeValueBeanCascade> getRetenedores(boolean isNumeric, String filter) {
			String AuxQry = qryRetenedores;
		if(StringUtils.isNumeric(filter)){
			filter+="%";
			AuxQry += "WHERE ID_RETENEDOR LIKE ?";
		}else{
			
			filter ="%"+filter.toUpperCase()+"%";
			AuxQry += "WHERE DESC_RETENEDOR LIKE ?";
		}
		AuxQry+= "AND STAT_RET = 'A'";
		
			listaRetedores = this.jdbcTemplate.query(AuxQry, new Object[]{filter}, LABEL_VALUE_MAPPER_RET_UP);
		
		return listaRetedores; 
		
	}
}
