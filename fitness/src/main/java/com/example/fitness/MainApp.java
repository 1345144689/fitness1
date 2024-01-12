package com.example.fitness;

import com.sun.javafx.stage.EmbeddedWindow;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;
import java.lang.reflect.Member;
import java.sql.*;

/**
 * 主函数
 */
public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        Stage stage1=new Stage();
        Parent parent1 = new FXMLLoader(MainApp.class.getResource("/adminnistratorsenroll.fxml")).load();
        Scene scene1 = new Scene(parent1);
        stage1.setScene(scene1);
        stage1.setTitle("登录");
        stage1.show();
        /*Stage stage2=new Stage();
        Parent parent2 = new FXMLLoader(MainApp.class.getResource("member.fxml")).load();
        Scene scene2 = new Scene(parent2);
        stage2.setScene(scene2);
        stage2.setTitle("member");
        stage2.show();*/
        }



    public static void main(String[] args) {
        launch();
    }}
