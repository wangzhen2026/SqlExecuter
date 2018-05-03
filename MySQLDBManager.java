package sqlFileAnalysis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.RegExMatcher;

/**
 * dburl=jdbc:mysql://127.0.0.1:3306/zpptdb?&useSSL=false&serverTimezone=GMT
 * dbDriver = com.mysql.jdbc.Driver;
 * @author Administrator
 *
 */
public class MySQLDBManager implements IDBManager {
    private String url= null ;
    private String user=null;
    private String password = null ;
    private String dbDriver = null ;
    
    private Connection  con = null ;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getDbDriver(){
		return this.dbDriver;
	}
	public void setDbDriver(String dbDriver){
		this.dbDriver = dbDriver;
		
	}
	/**
	 * 获得指定数据库的连接
	 * @return
	 * @throws Exception
	 */
	public void connection() throws Exception{
		if(null == this.url ){
			System.out.println("请设定url信息");
			
		}else if( null == this.user){
			System.out.println("请设定连接用户名");
		
		}else if( null==this.password){
			System.out.println("请设定连接密码");
			
		}
		
		Class.forName(this.dbDriver);
		this.con = DriverManager.getConnection(this.url, this.user,this.password);
	}
	
	/**
	 * 关闭数据库连接
	 * @throws Exception
	 */
	public void closeConnection() throws Exception{
		if(null != this.con){
			this.con.close();
		}else{
			this.con = null ;
		}
	}
	
	/**
	 * 执行select语句，返回执行结果
	 * @param selSql
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> executeSEL(String selSql) throws Exception {
		if (null == this.con) {
			System.out.println("请先建立数据库连接");
			return null;
		}
		String regEx = "^[\\s]*SELECT\\s+[\\s|\\S]*";
		if (!RegExMatcher.getMatheRes(selSql, regEx)) {
			return null;
		}

		Statement st = this.con.createStatement();
		ResultSet rt = st.executeQuery(selSql);

		if (null == rt) {
			if (null != st) {
				st.close();
			}
			return null;
		} else {
			List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
			ResultSetMetaData rm = rt.getMetaData();
			int colNum = rm.getColumnCount();
			Map<String, Object> colMap = new HashMap<String, Object>();
			for (int i = 1; i < colNum; i++) {
				colMap.put(rm.getColumnLabel(i), "");
			}

			Map<String, Object> resMap = null;
			while (rt.next()) {
				resMap = new HashMap<String, Object>();
				for (String k : colMap.keySet()) {
					resMap.put(k, rt.getString(k));
				}
				resList.add(resMap);
			}

			return resList;
		}

	}
	
	
	/**
	 * 执行DDL语句
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public int executeU(String src) throws Exception{
		if(null == this.con){
			System.out.println("请先建立数据库的连接");
			return -1;
		}
		
		if(!RegExMatcher.getMatheRes(src, "^[\\s]*[UPDATE|INSERT|DELETE][\\s|\\S]*")){
			System.out.println("sql语句不是update|insert|delete");
			return -1;
		}
		
		Statement st = this.con.createStatement();
		return st.executeUpdate(src);
	}
}
