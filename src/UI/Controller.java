package UI;

import Action.InsertAction;
import Action.SelectAction;
import DB.DBConnect;
import Module.Player;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static Action.SelectAction.selectInfoToData;
import static Action.SelectAction.selectOne;

public class Controller {
    private String playerIDToTakePart = "";
    private String teamIDToTakePart = "";
    private TableView tableview;
    private String myID = "0123456789";
    private static String selectedTeamID = "";
    private static String selectedTeamEventName = "";
    private static String selectedSoloEventName = "";
    static PropertyChangeSupport propertyChangeSupportTeamID = new PropertyChangeSupport(selectedTeamID);
    static PropertyChangeSupport propertyChangeSupportTeamEventName = new PropertyChangeSupport(selectedTeamID);
    static PropertyChangeSupport propertyChangeSupportSoloEventName = new PropertyChangeSupport(selectedTeamID);

    public static String getSelectedTeamEventName(){
        return selectedTeamEventName;
    }
    public static void setSelectedTeamID(String newID){
        String oldID = selectedTeamID;
        selectedTeamID = newID;
        propertyChangeSupportTeamID.firePropertyChange("YouSelectOneTeam",oldID,selectedTeamID);
    }
    public static void setSelectedTeamEventName(String newName){
        String oldName = selectedTeamEventName;
        selectedTeamEventName = newName;
        propertyChangeSupportTeamEventName.firePropertyChange("YouSelectOneTeamEvent",oldName,selectedTeamEventName);
    }
    public static void setSelectedSoloEventName(String newName){
        String oldName = selectedSoloEventName;
        selectedSoloEventName = newName;
        propertyChangeSupportSoloEventName.firePropertyChange("YouSelectOneTeamEvent",oldName,selectedSoloEventName);

    }

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
        String sql = "select * from player where ID = '"+myID+"';";
        Player player = new Player();
        player.setInfo(sql);
        Label IDLabel = new Label("ID");
        Label nameLabel = new Label("姓名");
        Label sexLabel= new Label("性别");
        Label heightLabel = new Label("身高");
        Label weightLabel = new Label("体重");

        TextField ID = new TextField(player.getID());
        ID.setEditable(false);
        TextField name = new TextField(player.getName());
        TextField sex = new TextField(player.getSex());
        TextField height = new TextField(String.valueOf(player.getHeight()));
        TextField weight = new TextField(String.valueOf(player.getWeight()));

        Button exitBtn = new Button("注销");
        Button modifyBtn = new Button("修改");

        exitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            }
        });

        modifyBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.setHeight(Integer.valueOf(height.getText()));
                player.setID(ID.getText());
                player.setName(name.getText());
                player.setWeight(Integer.valueOf(weight.getText()));
                player.setSex(sex.getText());
                player.updateToTable();
            }
        });

        center_pane.getChildren().clear();
        center_pane.setPadding(new Insets(20,20,20,20));
        center_pane.add(IDLabel, 0,0);
        center_pane.add(ID,1,0);
        center_pane.add(nameLabel,0,1);
        center_pane.add(name,1,1);
        center_pane.add(sexLabel,0,2);
        center_pane.add(sex,1,2);
        center_pane.add(heightLabel, 0,3);
        center_pane.add(height,1,3);
        center_pane.add(weightLabel,0,4);
        center_pane.add(weight,1,4);
        center_pane.add(exitBtn,0,6);
        center_pane.add(modifyBtn,1,6);
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
                String sql = "insert into player values('"+ID.getText()+"','"+userName.getText()+"','"+sex.getText()+"',"+height.getText()+","+weight.getText()+");";
                boolean insertRs = InsertAction.insert(sql);
                if(!insertRs)
                    System.out.println("注册失败");
                myID = ID.getText();
                person_info_btn.fire();
                regiStage.close();

//                Connection c;
//                try {
//                    c = DBConnect.connect();
//                    String sql = "insert into player values('"+ID.getText()+"','"+userName.getText()+"','"+sex.getText()+"',"+height.getText()+","+weight.getText()+");";
//                    System.out.println(sql);
//                    c.createStatement().executeUpdate(sql);
//                    myID = ID.getText();
//                    person_info_btn.fire();
//                    regiStage.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    System.out.println("注册失败！");
//                }
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
//        tableview.getColumns().clear();
//        tableview.getItems().clear();
        String sql = "select team.ID as TeamID,team.name as TeamName, player.name as CaptainName from team, player "
                +"where team.id in (select team.id from play_for where playerID = '"+myID+"') and player.id=team.captionID;";
        tableview = selectInfoToData(sql);

        center_pane.getChildren().clear();
        center_pane.add(tableview,0,0,4,10);

        Label tip = new Label("选中队伍ID为");
        center_pane.add(tip,0,10,1,2);
        TextField selectTeam = new TextField();
        selectTeam.setEditable(false);
        center_pane.add(selectTeam,1,10,2,2);
        Button seeDetail = new Button("点击查看详情");
        seeDetail.setDisable(true);
        seeDetail.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                seeTeamDetail(selectTeam.getText());
            }
        });
        center_pane.add(seeDetail,3,10,2,2);

        //监听被选择的行是否发生变化
        propertyChangeSupportTeamID.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                selectTeam.setText(selectedTeamID);
                seeDetail.setDisable(false);
            }
        });
    }

    private void seeTeamDetail(String ID){
        if(ID.equals("")){
            System.out.println("没有选择团队ID，非法操作");
            return;
        }
        center_pane.getChildren().clear();
        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();
        vBox1.setAlignment(Pos.CENTER);
        vBox2.setAlignment(Pos.CENTER);
        vBox1.setSpacing(10);
        vBox2.setSpacing(10);
        Label teammatesLabel = new Label("队伍成员");
        vBox1.getChildren().add(teammatesLabel);
        String sql = "select name,sex,height,weight from player where player.ID in (select playerID from play_for where teamID = '"+ID+"');";
        tableview = selectInfoToData(sql);

        center_pane.add(vBox1,0,0);
        center_pane.add(tableview,0,1,1,3);
        vBox2.getChildren().add(new Label("参赛情况"));
        String sql2 = "select * from team_take_part_in where teamID='"+ID+"';";
        center_pane.add(vBox2,0,4);
        center_pane.add(selectInfoToData(sql2),0,5,1,3);
        Button cansaiBtn = new Button("使用当前队伍报名参赛");
        center_pane.add(new HBox(cansaiBtn),0,8);
        cansaiBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                teamIDToTakePart = ID;
                take_team_event_btn.fire();
            }
        });
        //如果当前用户不是队长
        if(!SelectAction.selectOne("select captionid from team where id = '"+ID+"';").equals(myID)){
            cansaiBtn.setDisable(true);
            cansaiBtn.setText("您不是队长，无法报名");
        }
    }

    @FXML
    public void takeTeamEvent(ActionEvent actionEvent) {
        String sql = "select * from event where type = 'team';";
        tableview = selectInfoToData(sql);
        center_pane.getChildren().clear();
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(new Label("团体赛一览"));
        center_pane.add(tableview,0,1,4,8);
        Label tip = new Label("选中的比赛项目为");
        center_pane.add(tip,0,9,1,2);
        TextField selectEvent = new TextField();
        selectEvent.setEditable(false);
        center_pane.add(selectEvent,1,9,2,2);
        Button rollBtn = new Button("点击报名");
        if(teamIDToTakePart.equals(""))
            rollBtn.setText("请先到队伍界面选择队伍");
        rollBtn.setDisable(true);
        rollBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"确认报名么？",new ButtonType("取消", ButtonBar.ButtonData.NO),
                        new ButtonType("确认", ButtonBar.ButtonData.YES));
                alert.setTitle("报名团体赛");
                alert.setHeaderText(null);
//                alert.setContentText();
                Optional<ButtonType> buttonType = alert.showAndWait();
                if(buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)){
                    System.out.println("确认了报名团体赛");
                    String sql = "insert into TEAM_TAKE_PART_IN values ('"+selectedTeamID+"','"+selectedTeamEventName+"',-1);";
                    boolean rollSuccess = InsertAction.insert(sql);
                    if(!rollSuccess){
                        System.out.println("报名失败");
                    }
                }else{
                    System.out.println("您取消了报名");
                }
            }
        });
        center_pane.add(rollBtn,3,8,2,2);
        //监听被选择的行是否发生变化
        propertyChangeSupportTeamEventName.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                selectEvent.setText(selectedTeamEventName);
                if(!teamIDToTakePart.equals(""))
                    rollBtn.setDisable(false);
            }
        });
    }





}
