package sqlFileAnalysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.PreparedSql;

/**
 * 这个类是执行SQL语句的抽象类
 * @author Administrator
 *
 */
public abstract class AbstractSqlExecuter extends Observable implements Observer{
	private Queue<PreparedSql> sqlQue ;
	
    private  IDBManager dbMng = null ;
	
	public IDBManager getDbMng() {
		return dbMng;
	}

	public void setDbMng(IDBManager dbMng) {
		this.dbMng = dbMng;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if(o instanceof AbstractLackParamSqlManager){
			this.sqlQue = (Queue<PreparedSql>) arg ;
			try {
				this.executeSql();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

    /**
     * 执行AbstractParamMapManager.preparedSqlQueue存储的sql语句
     */
    public  void executeSql() throws Exception{
    	PreparedSql sql = null ;
    	String sqlContext = null ,sqlName = null ;
    	Map<String,String> paraMap = null ;
    	Map<String,List> resListMap = new HashMap<String,List>() ;
    	List<Map<String,Object>> selResList = null ;
        while(!this.sqlQue.isEmpty()){
    		sql = this.sqlQue.remove();
    		this.dealPreParedSqlObj(sql);
    		sqlContext = sql.getLackParamSql();
    		sqlName = sql.getSqlName();
    		paraMap = sql.getParaMap() ;
    		if(this.isSelectSql(sqlContext)){
    			selResList = this.exeSelect(sqlContext, paraMap);
    			if(null != selResList && !selResList.isEmpty()){
    				resListMap.put(sqlName, selResList);
    				super.setChanged();
        			super.notifyObservers(resListMap);
    			}
    		}else{
    			this.exeDMLBesideSel(sqlContext, paraMap);
    		}
    	}	
    }
    
    /**
     * 判定是否是SELECT 语句
     * @param sql
     * @return
     */
    private boolean isSelectSql(String sql){
    	if(null == sql){
    		return false ;
    	}
    	String _sql = sql.toUpperCase();
    	String regex = "^\\s*SELECT\\s+[\\s|\\S]*" ;
    	Pattern p = Pattern.compile(regex);
    	Matcher m = p.matcher(_sql);
    	return m.matches();
    }
    /**
     * 根据自己的需要整理PreparedSql对象，使之能适应自己的环境运行
     * @param preSql
     */
    protected abstract void dealPreParedSqlObj(PreparedSql preSql);
    /**
     * 执行除了select 之外的DML语句，这里不需要
     * @param sql
     * @param paraMap
     * @return
     * @throws Exception
     */
    protected abstract int exeDMLBesideSel(String sql ,Map<String,String>paraMap) throws Exception;
    /**
     * 执行select语句
     * @param sql
     * @param paraMap
     * @throws Exception
     */
    protected abstract List<Map<String,Object>> exeSelect(String sql,Map<String,String> paraMap) throws Exception ;
}
