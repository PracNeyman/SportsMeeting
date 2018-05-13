package Action;

import DB.DBConnect;

import java.sql.Connection;
import java.sql.SQLException;

public class UpdateAction {
    public static void update(String sql){
        System.out.println(sql);
        Connection c;
        try {
            c = DBConnect.connect();
            c.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("更新失败！");
        }
    }

}
