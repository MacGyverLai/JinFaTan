package lai.macgyver;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import tw.lai.macgyver.jft.module.HsqlCustomerManageDAO;
import tw.lai.macgyver.tools.PowerVoDisplay;

public class SqlCommand {
	
	private PowerVoDisplay pvd = new PowerVoDisplay();
	
	private HsqlCustomerManageDAO hsqlDao = null;
	
	private static final String DATA_BASE_NAME = "JinFaTan";

	protected void setUp() throws Exception {
//		String filePath = "C:/JinFaTan/JinFaTan";
		String filePath = new File(".").getAbsolutePath() + "./";
		System.out.println("DB path: " + filePath);
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:file:" + filePath + DATA_BASE_NAME + ";shutdown=true");
		dataSource.setUsername("SA");
		dataSource.setPassword("");
		
		this.hsqlDao = new HsqlCustomerManageDAO();
		this.hsqlDao.setDataSource(dataSource);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Input SQL command to perform or \"Q\" to exit");
			
			BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("\nPlease input the SQL command: ");
			String sql = buf.readLine();
//			String sql = "select * from \"Family\"";
			
			SqlCommand sqlCommand = null;
			while (!"Q".equalsIgnoreCase(sql)) {
				System.out.println("Command is " + sql);
				
				sqlCommand = new SqlCommand();
				sqlCommand.setUp();
				List queryList = null;
				if ((sql.startsWith("Select")) || (sql.startsWith("select"))) {
					queryList = sqlCommand.hsqlDao.jdbcTemplate.queryForList(sql);
					System.out.println("Return size = " + queryList.size());
					for (int i = 0; i < queryList.size(); i++)
						System.out.println(sqlCommand.pvd.diplayToString(queryList.get(i)));
				} else {
					int updateCount = sqlCommand.hsqlDao.jdbcTemplate.update(sql);
					System.out.println("Update count = " + updateCount);
				}
				System.out.print("\nPlease input the SQL command: ");
				sql = buf.readLine();
//				sql = "Q";
			}
			
			System.out.println("\nbye bye!");
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}

}
