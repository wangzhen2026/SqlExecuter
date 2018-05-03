package model;

import java.util.Map;

public class PreparedSql {
    private String sqlName = null ;
    private String lackParamSql = null ;
    private Map<String,String> paraMap = null ;
	public String getSqlName() {
		return sqlName;
	}
	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}
	public String getLackParamSql() {
		return lackParamSql;
	}
	public void setLackParamSql(String lackParamSql) {
		this.lackParamSql = lackParamSql;
	}
	public Map<String, String> getParaMap() {
		return paraMap;
	}
	public void setParaMap(Map<String, String> paraMap) {
		this.paraMap = paraMap;
	}
    
	/**
	 * @param paramName
	 * @return
	 * 获取指定的参数值
	 */
	public String obtainParamVal(String paramName){
		return this.paraMap.get(paramName);
	}
}
