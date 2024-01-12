package com.example.fitness;

import com.example.fitness.fellow.fellowview;
import com.sun.javafx.charts.Legend;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.awt.*;
import java.io.IOException;
import java.sql.*;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static javafx.fxml.FXMLLoader.load;

/**
 * 工具类，用于各种界面直接的转换
 */
public class Dao {
    public static String username;
    @FXML
    private TextField usernamefield;
    @FXML
    private TextField passwordfield;

    /*
     * 通过界面输入账号密码 进入主界面
     */
    @FXML
    public void login(ActionEvent event) {
        String username = usernamefield.getText();
        String password = passwordfield.getText();

        // 假设用户名为 "admin"，密码为 "password"
        if (username.equals("1") && password.equals("1")) {
            // 登录成功
            // 跳转到其他界面
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/home.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            // 登录失败
            System.out.println("登录失败，请检查用户名和密码！");
        }
    }

    @FXML
    private void jumpmember(ActionEvent event) throws IOException {
        // 加载新的界面
        Parent root = FXMLLoader.load(getClass().getResource("/member.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void jumpcoach(ActionEvent event) throws IOException {
        // 加载新的界面
        Parent root = FXMLLoader.load(getClass().getResource("/coach.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void jumpcourse(ActionEvent event) throws IOException {
        // 加载新的界面
        Parent root = FXMLLoader.load(getClass().getResource("/course.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void jumpequipment(ActionEvent event) throws IOException {
        // 加载新的界面
        Parent root = FXMLLoader.load(getClass().getResource("/equipment.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    private void jumpadmemberenroll(ActionEvent event) throws IOException {
        // 加载新的界面
        Parent root = FXMLLoader.load(getClass().getResource("/memberenroll.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}




