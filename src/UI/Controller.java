package UI;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Controller {
    private ObservableList<ObservableList> data;
    private TableView tableview = new TableView();
    private String myID = "0123456789";

    @FXML
    public Button found_team_btn;
    @FXML
    public Button see_team_btn;
    @FXML
    public Button take_solo_event_btn;
    @FXML
    public Button see_score;
    @FXML
    public Button take_team_event_btn;
    @FXML
    public GridPane center_pane;
    @FXML
    public GridPane func_pane;
    @FXML
    public Button person_info_btn;
    @FXML
    public void personInfoFunc(ActionEvent actionEvent) {
        tableview.getColumns().clear();
        tableview.getItems().clear();
        selectInfoToData("select * from player where ID = '"+myID+"';");
        center_pane.getChildren().clear();
        center_pane.getChildren().add(tableview);
    }


    public void selectInfoToData(String sql){
        System.out.println(sql);
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
    }

    public void init(){

        center_pane.setPadding (new Insets(20, 20, 20, 20));
        center_pane.setHgap (25);
        center_pane.setVgap (25);
        center_pane.setAlignment (Pos.CENTER);

        Button loginBtn = new Button("登录");
        Button regiBtn = new Button("注册");
        loginBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                login();
            }
        });
        regiBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                regi();
            }
        });
        center_pane.setAlignment(Pos.CENTER);
        center_pane.add(loginBtn,0,0);
        center_pane.add(regiBtn,1,0);
    }

    public void regi(){
        Stage regiStage = new Stage();
        regiStage.setAlwaysOnTop(true);
        GridPane regiPane = new GridPane ();
        regiPane.setPadding (new Insets(20, 20, 20, 20));
        regiPane.setHgap (25);
        regiPane.setVgap (25);
        regiPane.setAlignment (Pos.CENTER);

        Label higherCloud = new Label("运动会");
        higherCloud.setId ("Sports Meeting");
        regiPane.add (higherCloud, 0, 0, 3, 1);

        Label IDLabel = new Label("ID");
        Label userNameLabel = new Label("姓名");
        Label sexLabel = new Label("性别");
        Label heightLabel =  new Label("身高");
        Label weightLabel = new Label("体重");

        final TextField ID = new TextField();
        ID.setPrefWidth (200);
        final TextField userName = new TextField();
        userName.setPrefWidth (200);
        final TextField sex = new TextField();
        sex.setPrefWidth (200);
        final TextField height = new TextField();
        height.setPrefWidth (200);
        final TextField weight = new TextField();
        weight.setPrefWidth (200);

        regiPane.add (IDLabel, 0, 2);
        regiPane.add (userNameLabel, 0, 3);
        regiPane.add (sexLabel, 0, 4);
        regiPane.add (heightLabel, 0, 5);
        regiPane.add (weightLabel, 0, 6);
        regiPane.add (ID, 1, 2, 3, 1);
        regiPane.add (userName, 1, 3, 3, 1);
        regiPane.add (sex, 1, 4, 3, 1);
        regiPane.add (height, 1, 5, 3, 1);
        regiPane.add (weight, 1, 6, 3, 1);

        ID.setEditable(false);
        boolean foundFreeID = false;
        String testID = "";
        while(!foundFreeID) {
            testID = "";
            Random rand = new Random();
            for (int i = 0; i < 10; i++) {
                testID += String.valueOf(rand.nextInt(10));
            }
            System.out.println(testID);
            Connection c;
            try {
                c = DBConnect.connect();
                String sql = "select * from player WHERE ID = " + testID;
                System.out.println(sql);
                ResultSet rs = c.createStatement().executeQuery(sql);
                boolean haveSame = false;
                while (rs.next()) {
                    if (rs.getString("ID").equals(userName.getText())) {
                        haveSame = true;
                        break;
                    }
                }
                if (!haveSame) {
                    foundFreeID = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        ID.setText(testID);

        JFXButton regiBtn = new JFXButton("注册");
        regiBtn.setButtonType(JFXButton.ButtonType.RAISED);
        regiBtn.setStyle("-fx-background-color: e45652");
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        hBox.setSpacing(150);
        hBox.getChildren().addAll(regiBtn);
        regiPane.add (hBox, 1, 8, 3, 1);

        regiBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Connection c;
                try {
                    c = DBConnect.connect();
                    String sql = "insert into player values('"+ID.getText()+"','"+userName.getText()+"','"+sex.getText()+"',"+height.getText()+","+weight.getText()+");";
                    System.out.println(sql);
                    c.createStatement().executeUpdate(sql);
                    myID = ID.getText();
                    person_info_btn.fire();
                    regiStage.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("注册失败！");
                    
                }
            }
        });

        Scene scene = new Scene(regiPane, 600, 800);
        scene.getStylesheets().add(Main.class.getResource("base.css").toExternalForm());
        regiStage.setScene(scene);
        regiStage.setResizable(false);
//        loginStage.initOwner(pmstage);
        regiStage.show();

    }
    public void login(){
        Stage loginStage = new Stage();
        loginStage.setAlwaysOnTop(true);
        GridPane loginPane = new GridPane ();
        loginPane.setPadding (new Insets(20, 20, 20, 20));
        loginPane.setHgap (25);
        loginPane.setVgap (25);
        loginPane.setAlignment (Pos.CENTER);

        Label higherCloud = new Label("运动会");
        higherCloud.setId ("Sports Meeting");
        loginPane.add (higherCloud, 0, 0, 3, 1);

        Label userNameLabel = new Label("姓名");
        Label IDLabel = new Label ("ID");
        final TextField userName = new TextField();
        userName.setPrefWidth (200);
        final PasswordField psw = new PasswordField();
        psw.setPrefWidth (200);
        loginPane.add (userNameLabel, 0, 2);
        loginPane.add (IDLabel, 0, 3);
        loginPane.add (userName, 1, 2, 3, 1);
        loginPane.add (psw, 1, 3, 3, 1);

        JFXButton loginBtn = new JFXButton("登陆");
        loginBtn.setButtonType(JFXButton.ButtonType.RAISED);
        loginBtn.setStyle("-fx-background-color: e45652");
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        hBox.setSpacing(150);
        hBox.getChildren().addAll(loginBtn);
        loginPane.add (hBox, 1, 4, 3, 1);
        final Label loginInfo = new Label("");
        loginInfo.setVisible(false);
        loginPane.add(loginInfo, 2, 5, 3, 1);
        final boolean[] isSuccess = {false};

        loginBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                myID = psw.getText();
                Connection c;
                try {
                    c = DBConnect.connect();
                    String sql = "select name from player WHERE ID = "+psw.getText();
                    System.out.println(sql);
                    ResultSet rs = c.createStatement().executeQuery(sql);
                    while(rs.next()){
                        System.out.println(rs.getString("name"));
                        if (rs.getString("name").equals(userName.getText())){
                            isSuccess[0] = true;
                            System.out.println("登录成功");
                            person_info_btn.fire();
                            loginStage.close();
                        }
                    }
                    if(!isSuccess[0]){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("登录失败");
                        alert.setHeaderText(null);
                        alert.setContentText("请检查信息是否正确");
                        alert.initOwner(loginStage);
                        alert.showAndWait();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        Scene scene = new Scene(loginPane, 600, 400);
        scene.getStylesheets().add(Main.class.getResource("base.css").toExternalForm());
        loginStage.setScene(scene);
        loginStage.setResizable(false);
//        loginStage.initOwner(pmstage);
        loginStage.show();

    }

    @FXML
    public void foundTeam(ActionEvent actionEvent) {

        center_pane.getChildren().clear();
        Label higherCloud = new Label("创建队伍");
        higherCloud.setId ("Sports Meeting");
        center_pane.add (higherCloud, 0, 0, 3, 1);

        Label peopleNumLabel = new Label("队伍人数");
        TextField peopleNum = new TextField();

        Button ensureNumBtn = new Button("确认");
        center_pane.add(peopleNumLabel, 0,2,2,2);
        center_pane.add(peopleNum, 2,2,2,2);
        center_pane.add(ensureNumBtn, 3,4,2,2);
        ensureNumBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                center_pane.getChildren().clear();

                Label teamNameLabel = new Label("队伍名称");
                TextField teamName = new TextField();
                center_pane.add(teamNameLabel, 0,0,4,2);
                center_pane.add(teamName, 1,0,4,2);

                Label captionLabel = new Label("队长");
                Label captionIDLabel =  new Label("ID");
                Label captionNameLabel = new Label("姓名");
                TextField captionID = new TextField();
                captionID.setText(myID);
                captionID.setEditable(false);
                TextField captionName = new TextField();
                captionName.setText(selectOne("select name from player where ID = '"+myID+"';"));
                captionName.setEditable(false);

                center_pane.add(captionLabel, 0,3,2,1);
                center_pane.add(captionIDLabel, 0,4,1,1);
                center_pane.add(captionID, 1,4,4,1);
                center_pane.add(captionNameLabel, 5,4,1,1);
                center_pane.add(captionName, 6,4,2,1);

                //内部类
                class PersonField{
                    private TextField ID;
                    private TextField name;
                }
                List<PersonField> personFieldList = new ArrayList<>();
                for(int i = 0;i<Integer.valueOf(peopleNum.getText())-1; i++){
                    Label mateLabel = new Label("队员"+(i+1));
                    Label mateIDLabel =  new Label("ID");
                    Label mateNameLabel = new Label("姓名");
                    TextField mateID = new TextField();
                    TextField mateName = new TextField();
                    PersonField personField = new PersonField();
                    personField.ID = mateID;
                    personField.name  = mateName;
                    personFieldList.add(personField);
                    center_pane.add(mateLabel, 0,5+2*i,2,1);
                    center_pane.add(mateIDLabel, 0,6+2*i,1,1);
                    center_pane.add(mateID, 1,6+2*i,4,1);
                    center_pane.add(mateNameLabel, 5,6+2*i,1,1);
                    center_pane.add(mateName, 6,6+2*i,2,1);
                }

                Button ensureFoundBtn = new Button("确认");
                center_pane.add(ensureFoundBtn,0,5+2*Integer.valueOf(peopleNum.getText()));
                final boolean[] infoRight = {true};
                ensureFoundBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        for (PersonField personField : personFieldList) {
                            if(!selectOne("select name from player where ID = '"+personField.ID.getText()+"';").equals(personField.name.getText())){
                                infoRight[0] = false;
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("队伍建立失败");
                                alert.setHeaderText(null);
                                alert.setContentText("请检查信息是否正确");
                                alert.showAndWait();
                                break;
                            }
                        }
                        if(infoRight[0]){
                            Connection c;
                            try {
                                c = DBConnect.connect();
                                String teamID = RandomID.getRandomID("team");
                                String sql = "insert into team values('"+teamID+"','"+teamName.getText()+"','"+myID+"');";
                                c.createStatement().executeUpdate(sql);
                                c.createStatement().executeUpdate("insert into play_for values('"+myID+"','"+teamID+"')");
                                for(PersonField personField:personFieldList) {
                                    sql = "insert into play_for values('"+personField.ID.getText()+"','"+teamID+"')";
                                    System.out.println(sql);
                                    c.createStatement().executeUpdate(sql);
                                }
                                center_pane.getChildren().clear();
                                Label infoLabel = new Label("队伍建立成功，您的队伍ID为"+teamID);
                                infoLabel.setPrefSize(420,70);
                                center_pane.getChildren().add(infoLabel);
                            } catch (SQLException e) {
                                e.printStackTrace();
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("队伍建立失败");
                                alert.setHeaderText(null);
                                alert.setContentText("请检查信息是否正确");
                                alert.showAndWait();
                            }
                        }
                    }
                });
            }
        });
        System.out.println("here");
    }
    @FXML
    public void takeSoloEvent(ActionEvent actionEvent) {
    }
    @FXML
    public void seeTeam(ActionEvent actionEvent) {
        tableview.getColumns().clear();
        tableview.getItems().clear();
        String sql = "select team.ID as TeamID,team.name as TeamName, player.name as CaptainName from team, player "
                +"where team.id in (select team.id from play_for where player.id = '"+myID+"');";
        selectInfoToData(sql);
        //tableview = new TableView();
        center_pane.getChildren().clear();
        center_pane.getChildren().add(tableview);
    }
    @FXML
    public void takeTeamEvent(ActionEvent actionEvent) {
    }

    //只返回一个字段，形式为String
    private String selectOne(String sql){
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



}
