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
 * �������ִ��SQL���ĳ�����
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
     * ִ��AbstractParamMapManager.preparedSqlQueue�洢��sql���
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
     * �ж��Ƿ���SELECT ���
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
     * �����Լ�����Ҫ����PreparedSql����ʹ֮����Ӧ�Լ��Ļ�������
     * @param preSql
     */
    protected abstract void dealPreParedSqlObj(PreparedSql preSql);
    /**
     * ִ�г���select ֮���DML��䣬���ﲻ��Ҫ
     * @param sql
     * @param paraMap
     * @return
     * @throws Exception
     */
    protected abstract int exeDMLBesideSel(String sql ,Map<String,String>paraMap) throws Exception;
    /**
     * ִ��select���
     * @param sql
     * @param paraMap
     * @throws Exception
     */
    protected abstract List<Map<String,Object>> exeSelect(String sql,Map<String,String> paraMap) throws Exception ;
}
