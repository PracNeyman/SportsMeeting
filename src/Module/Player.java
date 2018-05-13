package Module;

import Action.UpdateAction;
import DB.DBConnect;

import java.sql.Connection;
import java.sql.ResultSet;

public class Player {
    private String ID;
    private String name;
    private String sex;
    private int height;
    private int weight;

    public Player(){

    }
    public void setInfo(String sql){
        Connection c ;
        try {
            c  = DBConnect.connect();
            ResultSet rs = c.createStatement().executeQuery(sql);
            while(rs.next()) {
                this.ID = rs.getString(1);
                this.name = rs.getString(2);
                this.sex = rs.getString(3);
                this.height = Integer.parseInt(rs.getString(4));
                this.weight = Integer.parseInt(rs.getString(5));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weigth) {
        this.weight = weigth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void updateToTable(){
        String sql = "update player set name='"+name+"',"+"sex='"+sex+"',height="+height+",weight="+weight+" where ID='"+ID+"';";
        UpdateAction.update(sql);
    }
}

