package sqlFileAnalysis;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.PreparedSql;

/**
 * ִ��SQL����ʵ���࣬��Ҫ��չ���Լ������ݿ������
 * @author Administrator
 *
 */
public class SqlExecuter extends AbstractSqlExecuter {

	
	@Override
	protected void dealPreParedSqlObj(PreparedSql preSql) {
		// TODO Auto-generated method stub
	}

	@Override
	protected int exeDMLBesideSel(String sql, Map<String, String> paraMap) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected List<Map<String, Object>> exeSelect(String sql, Map<String, String> paraMap) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	

}
