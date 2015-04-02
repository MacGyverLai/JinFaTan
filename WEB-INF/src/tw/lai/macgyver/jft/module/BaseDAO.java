package tw.lai.macgyver.jft.module;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import tw.lai.macgyver.tools.PowerVoDisplay;

public class BaseDAO {

	public JdbcTemplate jdbcTemplate = null;
	
	protected IJinFaTanCenter jftCenter = null;
	
	protected PowerVoDisplay pvd = new PowerVoDisplay();

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Resource(name = "jftCenter")
	public void setJftCenter(IJinFaTanCenter jftCenter) {
		this.jftCenter = jftCenter;
	}
}
