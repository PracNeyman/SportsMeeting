package UI;

import DB.DBConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class RandomID {
    public static String getRandomID(String type){
        boolean foundFreeID = false;
        String testID = "";
        while(!foundFreeID) {
            testID = "";
            Random rand = new Random();
            for (int i = 0; i < 10; i++) {
                testID += String.valueOf(rand.nextInt(10));
            }
            Connection c;
            try {
                c = DBConnect.connect();
                String sql;
                if(type.equals("player"))
                    sql = "select * from player WHERE ID = " + testID;
                else
                    sql = "select * from team WHERE ID = " + testID;
                ResultSet rs = c.createStatement().executeQuery(sql);
                boolean haveSame = false;
                while (rs.next()) {
                    haveSame = true;
                }
                if (!haveSame) {
                    foundFreeID = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return testID;
    }
}
