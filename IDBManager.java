package sqlFileAnalysis;

import java.util.List;
import java.util.Map;

public interface IDBManager {
    public void connection() throws Exception;
    public List<Map<String,Object>> executeSEL(String sql) throws Exception ;
    public int executeU(String sql) throws Exception;
    public void closeConnection() throws Exception;
}
