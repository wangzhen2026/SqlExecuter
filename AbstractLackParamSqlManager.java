package sqlFileAnalysis;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import model.PreparedSql;

public abstract class AbstractLackParamSqlManager extends Observable implements Observer  {
	protected Map<String,String> _sqlMap = null ;
    protected Queue<PreparedSql> preparedSqlQueue = new LinkedBlockingQueue<PreparedSql>();
   
    public Map<String, String> get_sqlMap() {
		return _sqlMap;
	}

	public void set_sqlMap(Map<String, String> _sqlMap) {
		this._sqlMap = _sqlMap;
	}
}
