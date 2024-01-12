package com.example.fitness.coursetest;


import com.example.fitness.caochtest.coach;
import com.example.fitness.membertest.member;
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

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 工具，控制器类，用于管理健身俱乐部设备信息的数据处理
 */
public class courseview {

    @FXML
    private TableView<course> courseTableView;
    @FXML
    private TextField cnoField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriField;

    @FXML
    private TextField coachcnoField;
    @FXML
    private  TextField SearchField;

    @FXML
    private TableColumn<course, String> coursecno;

    @FXML
    private TableColumn<course, String> coursename;

    @FXML
    private TableColumn<course,String> describe;

    @FXML
    private TableColumn<course, String> coachcno;
    @FXML
    private TableColumn<course, String> coachtel;
    @FXML
    private Pagination pagination;
    private int itemsPerPage = 10; // 每页显示的记录数
    private List<member> allMembers; // 所有会员的列表
    @FXML
    private TextField searchField;


    private ArrayList<course> getDataFromDatabase() {
        ArrayList<course> courses = new ArrayList<>();

        try {
            // 获取数据库连接
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fitness", "root", "13034870875");

            // 执行查询语句
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM course");

            // 遍历结果集，将数据存储在ArrayList中
            while (resultSet.next()) {
                String cno = resultSet.getString("coursecno");
                String name = resultSet.getString("coursename");
                String describe = resultSet.getString("coursedescribe");
                String coachcno = resultSet.getString("coachcno");


                course course1 = new course(cno, name, describe,coachcno);
                courses.add(course1);
            }

            // 关闭数据库连接
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }
    public void initialize() {
        // 从数据库中获取数据并存储在ArrayList中
        ArrayList<course> courses = getDataFromDatabase();
        // 将ArrayList中的数据加载到TableView中
        try {
            updateTableView(courses);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void updateTableView(List<course> courses) throws Exception {
// 传入List集合，更新TableView
        courseTableView.getItems().setAll(courses);
// 为每列设置单元格值工厂，以从学生对象中获取属性值显示。
        coursecno.setCellValueFactory(new PropertyValueFactory<>
                ("coursecno"));
        coursename.setCellValueFactory(new PropertyValueFactory<>
                ("coursename"));
        describe.setCellValueFactory(new PropertyValueFactory<>
                ("coursedescribe"));
        coachcno.setCellValueFactory(new PropertyValueFactory<>
                ("coachcno"));


    }
    @FXML
    private void addNewMember(ActionEvent event) {
        // 获取表单中的数据
        String cno = cnoField.getText();
        String name = nameField.getText();
        String describe = descriField.getText();
        String coachcno = coachcnoField.getText();

        // 在数据库中添加新的会员记录
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fitness", "root", "13034870875");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO member (coursecno,coursename,coursedescribe,coachcno) VALUES (?, ?, ?, ?)");
            statement.setString(1, cno);
            statement.setString(2, name);
            statement.setString(3, describe);
            statement.setString(4, coachcno);
            List<course> currentPageData = getDataFromDatabase();
            updateTableView(currentPageData);
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @SneakyThrows
    @FXML
    private void btnDelcourse(ActionEvent actionEvent) {
        // 获取选定行数据
        course selectedMember = courseTableView.getSelectionModel().getSelectedItem();

        // 使用系统确认框询问
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("您确定要删除选中的学生信息吗？");

        // 显示对话框，并等待按钮返回
        Optional<ButtonType> result = alert.showAndWait();

        // 判断返回的按钮类型是确定还是取消，再据此分别进一步处理
        if (result.isPresent() && result.get() == ButtonType.OK) { // 单击确定按钮OK
            // 连接数据库并执行删除操作
            Connection connection = null;
            PreparedStatement statement = null;
            try {
                Class.forName("com.mysql.jdbc.Driver"); // 加载MySQL驱动程序
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fitness", "root", "13034870875");
                String sql = "DELETE FROM course WHERE courcno = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, selectedMember.getCoursecno());
                statement.executeUpdate();
                System.out.println(statement);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                // 处理异常
            } finally {
                // 关闭数据库连接和语句对象
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    @FXML
    private void btnEdit(ActionEvent event) {
        // 获取选中的学生对象
        course selectedStudent = courseTableView.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            // 将选中的学生信息显示在文本框中
            cnoField.setText(selectedStudent.getCoursecno());
            nameField.setText(selectedStudent.getCoursename());
            descriField.setText(selectedStudent.getCoursedescribe());
            coachcnoField.setText(selectedStudent.getCoachcno());
        }
    }
    @FXML
    private void saveMember(ActionEvent event) throws Exception {
        // 获取选中的会员对象
        course selectedMember = courseTableView.getSelectionModel().getSelectedItem();

        if (selectedMember != null) {
            // 更新会员对象
            selectedMember.setCoachcno(cnoField.getText());
            selectedMember.setCoursename(nameField.getText());
            selectedMember.setCoursedescribe(descriField.getText());
            selectedMember.setCoursecno(coachcnoField.getText());
            cnoField.clear();
            nameField.clear();
            descriField.clear();
            coachcnoField.clear();
            updateToDatabase(selectedMember);
            List<course> currentPageData = getDataFromDatabase();
            updateTableView(currentPageData);

        }
    }
    @FXML
    private void searchMember(ActionEvent event)throws  Exception {
        // 获取搜索关键字
        String keyword = searchField.getText();

        // 在数据库中进行模糊查询
        ArrayList<course> courses = searchFromDatabase(keyword);
        if (courses.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("查无此人");
            alert.showAndWait();
        } else {
            // 更新TableView
            updateTableView(courses);
            searchField.clear();
        }
    }
    private ArrayList<course> searchFromDatabase(String keyword) {
        ArrayList<course> courses = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fitness", "root", "13034870875");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM course WHERE coursecno LIKE ? OR coursename LIKE ? ");
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String cno = resultSet.getString("coursecno");
                String name = resultSet.getString("coursename");
                String describe = resultSet.getString("coursedescribe");
                String coacoursecno = resultSet.getString("coachcno");
                course course1 = new course(cno, name,describe,coacoursecno);
                courses.add(course1);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }
    private void updateToDatabase(course selectedMember) {
        try {
            // 创建数据库连接对象
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fitness", "root", "13034870875");

            // 构造更新语句
            String sql = "UPDATE course SET  coursename = ?, coursedescribe = ?, coachcno = ? WHERE coursecno = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, selectedMember.getCoursecno());
            pstmt.setString(2, selectedMember.getCoursename());
            pstmt.setString(3, selectedMember.getCoursedescribe());
            pstmt.setString(4, selectedMember.getCoachcno());
            // 执行更新操作
            pstmt.executeUpdate();
            // 关闭预编译语句和连接对象
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void jumphome(ActionEvent event) throws IOException {
        // 加载新的界面
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/home.fxml")));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}

