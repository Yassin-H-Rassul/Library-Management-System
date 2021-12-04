package Server;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Formatter {

    /*
     * Convert ResultSet to a common JSON Object array
     * Result is like: [{"ID":"1","NAME":"Tom","AGE":"24"}, {"ID":"2","NAME":"Bob","AGE":"26"}, ...]
     */

    public static List<JSONObject> getFormattedResult(ResultSet rs) {
        List<JSONObject> resList = new ArrayList<JSONObject>();
        try {
            // get column names
            ResultSetMetaData rsMeta = rs.getMetaData();
            int columnCnt = rsMeta.getColumnCount();
            List<String> columnNames = new ArrayList<String>();
            for(int i=1;i<=columnCnt;i++) {
                columnNames.add(rsMeta.getColumnName(i).toUpperCase());
            }


            while(rs.next()) { // convert each object to an human readable JSON object
                JSONObject obj = new JSONObject();
                try {
                    Field changeMap = obj.getClass().getDeclaredField("map");
                    changeMap.setAccessible(true);
                    changeMap.set(obj, new LinkedHashMap<>());
                    changeMap.setAccessible(false);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    System.out.println("Exception in Formatter Class");
                }
                for(int i=1;i<=columnCnt;i++) {
                    String key = columnNames.get(i - 1);
                    String value = rs.getString(i);
                    obj.put(key, value);
                }
                resList.add(obj);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resList;
    }
}
