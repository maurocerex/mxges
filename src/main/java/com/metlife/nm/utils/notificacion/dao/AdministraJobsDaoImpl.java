package com.metlife.nm.utils.notificacion.dao;

import static java.sql.Types.NUMERIC;
import static java.sql.Types.VARCHAR;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Service;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.JdbcDao;
import com.metlife.nm.utils.notificacion.vo.JobVO;



@Service (value = BeanNames.AdministraJobsDao)
public class AdministraJobsDaoImpl extends JdbcDao implements AdministraJobsDao {

private String qryJobs;
private String spCambiaEstatus;
private static final String P_Id_Job ="P_ID_JOB";
private static final String P_Estatus = "P_ESTATUS";
private static final String P_Hora = "P_HORA";


@SuppressWarnings("unchecked")
public List<JobVO> getJobs() {
	if (log.isDebugEnabled()) {
        log.debug("getJobs DAO...");
        log.debug(qryJobs);
    }
	
	//List<Map<String,Object>> result2 = new ArrayList<Map<String,Object>>();
    List<JobVO> result1 = new ArrayList<JobVO>();
    result1 = (List<JobVO>)jdbcTemplate.query(qryJobs,MAPPER_JOBS);
//    result2 = (List<Map<String,Object>>)jdbcTemplate.queryForList(qryJobs);
    return result1;
}

@SuppressWarnings("unchecked")
public void actualizaJobs(List<JobVO> jobs){
	
Boolean broken =false;
for (JobVO job : jobs) {
	 broken= job.getBroken().equalsIgnoreCase("Y");
	 spCambiaEstatusJob sp = new spCambiaEstatusJob(this.jdbcTemplate.getDataSource(), spCambiaEstatus);
	 sp.execute(String.valueOf(job.getIdJob()),String.valueOf(broken), job.getHorario());
}
	
}

private static ParameterizedRowMapper<JobVO> MAPPER_JOBS = new ParameterizedRowMapper<JobVO>() {
	public JobVO mapRow(ResultSet rs, int rowNum) throws SQLException {
		JobVO job = new JobVO();
		job.setIdJob(rs.getInt("JOB"));
		job.setWhat(rs.getString("DES_PARAMETRO"));
		job.setBroken(rs.getString("BROKEN"));
		job.setHorario(rs.getString("HORARIO"));
		return job;
	}
};
	
public void setQryJobs(String qryJobs) {
	this.qryJobs = qryJobs;
}
public void setSpCambiaEstatus(String spCambiaEstatus) {
	this.spCambiaEstatus = spCambiaEstatus;
}

private class spCambiaEstatusJob extends StoredProcedure{
	public spCambiaEstatusJob(DataSource ds, String store){
		super(ds, store);
		declareParameter(new SqlParameter(P_Id_Job, NUMERIC));
		declareParameter(new SqlParameter(P_Estatus, VARCHAR));
		declareParameter(new SqlParameter(P_Hora, VARCHAR));
	}
	
@SuppressWarnings("unchecked")
public void execute(String idJob, String estatus, String hora){
	Map<String, String> param = new HashMap<String, String>();
	param.put(P_Id_Job, idJob);
	param.put(P_Estatus,estatus);
	param.put(P_Hora, hora);
	
	super.execute(param);
	
	
}

	
}


}
