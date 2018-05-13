package Action;

import DB.DBConnect;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class InsertAction {

    public static boolean insert(String sql) {
        Connection c;
        try {
            c = DBConnect.connect();
            System.out.println(sql);
            c.createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
