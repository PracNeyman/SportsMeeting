package Action;

import DB.DBConnect;
import UI.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectAction {

    //只返回一个字段，形式为String
    public static String selectOne(String sql){
        Connection c;
        try {
            c = DBConnect.connect();
            ResultSet rs = c.createStatement().executeQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static TableView selectInfoToData(String sql){
        System.out.println(sql);
        ObservableList<ObservableList> data;
        TableView tableview = new TableView();

        Connection c ;
        data = FXCollections.observableArrayList();
        try {
            c  =DBConnect.connect();
//            String sql = "select * from player";
            ResultSet rs = c.createStatement().executeQuery(sql);
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableview.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
            }

            while(rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            tableview.setItems(data);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }

        class TableRowControl extends TableRow{
            public TableRowControl(){
                super();
                this.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(event.getButton().equals(MouseButton.PRIMARY)&&event.getClickCount()==2&&TableRowControl.this.getIndex()<tableview.getItems().size()){
                            ObservableList<String> row = (ObservableList<String>)(tableview.getItems().get(TableRowControl.this.getIndex()));
                            Controller.selectedID = row.get(0);
                            System.out.println("当前选择的ID是:"+Controller.selectedID);

                        }
                    }
                });
            }
        }

        tableview.setRowFactory(new Callback<TableView, TableRow>() {
            @Override
            public TableRow call(TableView param) {
                return new TableRowControl();
            }
        });

        return tableview;
    }
}

