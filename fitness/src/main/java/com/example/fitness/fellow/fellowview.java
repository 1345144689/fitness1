package com.example.fitness.fellow;


import com.example.fitness.Dao;
import com.example.fitness.MainApp;
import com.example.fitness.fellow.fellow;
import com.example.fitness.membertest.member;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.apache.poi.util.SystemOutLogger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import static javafx.fxml.FXMLLoader.load;

/**
 * 工具，控制器类，用于管理健身俱乐部会员的数据处理
 */
public class fellowview {

    private static String username;
    @FXML
    private TableView<fellow> fellowTableView = new TableView<>();
    @FXML
    private TableColumn<fellow, String> membercno = new TableColumn<>();

    @FXML
    private TableColumn<fellow, String> membername = new TableColumn<>();

    @FXML
    private TableColumn<fellow, String> membersex = new TableColumn<>();

    @FXML
    private TableColumn<fellow, String> membertel = new TableColumn<>();

    @FXML
    private TableColumn<fellow, String> memcoursecno = new TableColumn<>();

    @FXML
    private TableColumn<fellow, String> coursetimes = new TableColumn<>();

    @FXML
    private TableColumn<fellow, String> purchasetime = new TableColumn<>();

    @FXML
    private TextField cnoField = new TextField();


    @FXML
    private TextField nameField = new TextField();

    @FXML
    private TextField telField = new TextField();

    @FXML
    private TextField coursecnoField = new TextField();

    @FXML
    private TextField timesField = new TextField();
    @FXML
    private TextField sexField = new TextField();
    @FXML
    private TextField passwordField = new TextField();

    @FXML
    private TextField usernamefield = new TextField();

    @FXML
    private TextField passwordfield = new TextField();

//通过获取数据库中的电话号码和密码来判断是否登录成功
    public void loginmember() throws Exception {
        String username = usernamefield.getText();
        String password = passwordfield.getText();
        Dao.username = username;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // 连接数据库
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fitness", "root", "13034870875");
            // 查询用户（假设登录时只需要匹配一个用户）
            String query = "SELECT * FROM member WHERE membertel=? AND purchasetime=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            if (rs.next()) {
                // 获取查询结果并设置到文本框
                cnoField.setText(rs.getString("membercno"));
                nameField.setText(rs.getString("membername"));
                telField.setText(rs.getString("membertel"));
                sexField.setText(rs.getString("membersex"));
                coursecnoField.setText(rs.getString("memcoursecno"));
                timesField.setText(rs.getString("coursetimes"));

                // 由于您没有提供purchasetime对应哪个文本框，这里假设它应该展示在passwordfield
                passwordField.setText(rs.getString("purchasetime")); // 根据实际业务逻辑判断是否合适


            } else {
                System.out.println("登录失败，请检查用户名和密码！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    //跳转到管理员登录界面
    @FXML
    private void jumpadminnistraatorsenroll(ActionEvent event) throws IOException {
        // 加载新的界面
        Parent root = FXMLLoader.load(getClass().getResource("/adminnistratorsenroll.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //跳转到教练界面
    @FXML
    private void jumpcoach(ActionEvent event) throws IOException {
        // 加载新的界面
        Parent root = FXMLLoader.load(getClass().getResource("/fellowcoach.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    //跳转到器材界面
    @FXML
    private void jumpequipment(ActionEvent event) throws IOException {
        // 加载新的界面
        Parent root = FXMLLoader.load(getClass().getResource("/fellowequipment.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
