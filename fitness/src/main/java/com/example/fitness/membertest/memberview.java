package com.example.fitness.membertest;

import com.example.fitness.fellow.fellow;
import com.example.fitness.membertest.member;
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

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 工具，控制器类，用于管理健身俱乐部会员信息的数据处理
 */
public class memberview {

    @FXML
    private TableView<member> memberTableView;
    @FXML
    private TableColumn<member, String> membercno;

    @FXML
    private TableColumn<member, String> membername;

    @FXML
    private TableColumn<member, String> membersex;

    @FXML
    private TableColumn<member, String> membertel;

    @FXML
    private TableColumn<member, String> memcoursecno;

    @FXML
    private TableColumn<member, String> coursetimes;

    @FXML
    private TableColumn<member, String> purchasetime;

    @FXML
    private TextField cnoField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField telField;

    @FXML
    private TextField courseField;

    @FXML
    private TextField timesField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField sexField;
    @FXML
    private Button add;
    @FXML
    private TextField searchField;

    @FXML
    private Pagination pagination;
    private int itemsPerPage = 20; // 每页显示的记录数
    private List<member> allMembers; // 所有会员的列表
    // 学生表初始更新的时间，设置为当前系统时间
    private Timestamp initialUpdateTime =
            Timestamp.valueOf(LocalDateTime.now());
    // 学生表最新更新时间，初始值与初始更新时间相同
    private Timestamp lastUpdateTime = initialUpdateTime;




    @FXML
    private void addNewMember(ActionEvent event) {
        // 获取表单中的数据
        String cno = cnoField.getText();
        String name = nameField.getText();
        String sex = sexField.getText();
        String tel = telField.getText();
        String coursecno = courseField.getText();
        String coursetimes = timesField.getText();
        String date = dateField.getText();

        // 在数据库中添加新的会员记录
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fitness", "root", "13034870875");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO member (membercno,membername, membersex, membertel, memcoursecno, coursetimes,purchasetime) VALUES (?, ?, ?, ?, ?, ? ,?)");
            statement.setString(1, cno);
            statement.setString(2, name);
            statement.setString(3, sex);
            statement.setString(4, tel);
            statement.setString(5, coursecno);
            statement.setString(6, coursetimes);
            statement.setString(7, date);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new member was inserted successfully!");
            }
            List<member> currentPageData = getCurrentPageData();
            // 如果当前页数据为空
            if (currentPageData.isEmpty()) {
                // 将当前页索引减1
                int currentPageIndex = pagination.getCurrentPageIndex();
                pagination.setCurrentPageIndex(currentPageIndex - 1);
                // 更新当前页数据
                currentPageData = getCurrentPageData();
            }
            updateTableView(currentPageData);
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SneakyThrows
    @FXML
    private void btnDel(ActionEvent actionEvent) {
        // 获取选定行数据
        member selectedMember = memberTableView.getSelectionModel().getSelectedItem();

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
                String sql = "DELETE FROM member WHERE membercno = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, selectedMember.getMembercno());
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
            allMembers.remove(selectedMember);
            // 更新数据表格，并停留在原来这一页

            List<member> currentPageData = getCurrentPageData();
            // 如果当前页数据为空
            if (currentPageData.isEmpty()) {
                // 将当前页索引减1
                int currentPageIndex = pagination.getCurrentPageIndex();
                pagination.setCurrentPageIndex(currentPageIndex - 1);
                // 更新当前页数据
                currentPageData = getCurrentPageData();
            }
            updateTableView(currentPageData);

        }
    }

    @FXML
    private void searchMember(ActionEvent event)throws  Exception {
        // 获取搜索关键字
        String keyword = searchField.getText();

        // 在数据库中进行模糊查询
        ArrayList<member> members = searchFromDatabase(keyword);
        if (members.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("查无此人");
            alert.showAndWait();
        } else {
            // 更新TableView
            updateTableView(members);
        }
    }

    @FXML
    private void btnEdit(ActionEvent event) {
        // 获取选中的学生对象
        member selectedStudent = memberTableView.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            // 将选中的学生信息显示在文本框中
            cnoField.setText(selectedStudent.getMembercno());
            nameField.setText(selectedStudent.getMembername());
            telField.setText(selectedStudent.getMembertel());
            sexField.setText(selectedStudent.getMembersex());
            courseField.setText(selectedStudent.getMemcoursecno());
            timesField.setText(selectedStudent.getCoursetimes());
            dateField.setText(selectedStudent.getPurchasetime());
            add.setDisable(true);

        }
    }

    @FXML
    private void saveMember(ActionEvent event) throws Exception{
        // 获取选中的会员对象
        member selectedMember = memberTableView.getSelectionModel().getSelectedItem();

        if (selectedMember != null) {
            // 更新会员对象
            selectedMember.setMembername(nameField.getText());
            selectedMember.setMembertel(telField.getText());
            selectedMember.setMembersex(sexField.getText());
            selectedMember.setMemcoursecno(courseField.getText());
            selectedMember.setCoursetimes(timesField.getText());
            selectedMember.setPurchasetime(dateField.getText());
            add.setDisable(false);
            cnoField.clear();
            nameField.clear();
            telField.clear();
            sexField.clear();
            courseField.clear();
            timesField.clear();
            dateField.clear();
            // 将更新后的会员对象保存到数据库
            updateToDatabase(selectedMember);

            List<member> currentPageData = getCurrentPageData();
            // 如果当前页数据为空
            if (currentPageData.isEmpty()) {
                // 将当前页索引减1
                int currentPageIndex = pagination.getCurrentPageIndex();
                pagination.setCurrentPageIndex(currentPageIndex - 1);
                // 更新当前页数据
                currentPageData = getCurrentPageData();
            }
            updateTableView(currentPageData);

        }
    }

    public void initialize() throws Exception{
        // 获取所有会员的列表
        allMembers = getDataFromDatabase();

        // 设置Pagination的总页数
        int pageCount = (int) Math.ceil((double) allMembers.size() / itemsPerPage);
        pagination.setPageCount(pageCount);

        // 当前页码改变时更新TableView
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            int startIndex = newValue.intValue() * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, allMembers.size());
            List<member> subList = allMembers.subList(startIndex, endIndex);
            try {
                updateTableView(subList);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // 第一页默认选中
        pagination.setCurrentPageIndex(0);

        // 初始化TableView
        initializeTableView();




    }

    private void initializeTableView() {
        // 将ArrayList中的数据加载到TableView中
        List<member> subList = allMembers.subList(0, Math.min(itemsPerPage, allMembers.size()));
        memberTableView.getItems().setAll(subList);

        // 将每个TableColumn与member对象的相应属性绑定起来
        membercno.setCellValueFactory(new PropertyValueFactory<>("membercno"));
        membername.setCellValueFactory(new PropertyValueFactory<>("membername"));
        membersex.setCellValueFactory(new PropertyValueFactory<>("membersex"));
        membertel.setCellValueFactory(new PropertyValueFactory<>("membertel"));
        memcoursecno.setCellValueFactory(new PropertyValueFactory<>("memcoursecno"));
        coursetimes.setCellValueFactory(new PropertyValueFactory<>("coursetimes"));
        purchasetime.setCellValueFactory(new PropertyValueFactory<>("purchasetime"));
    }
    //连接数据库将各个属性连接起来
    private ArrayList<member> getDataFromDatabase() {
        ArrayList<member> members = new ArrayList<>();

        try {
            // 获取数据库连接
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fitness", "root", "13034870875");

            // 执行查询语句
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM member");

            // 遍历结果集，将数据存储在ArrayList中
            while (resultSet.next()) {
                String cno = resultSet.getString("membercno");
                String name = resultSet.getString("membername");
                String sex = resultSet.getString("membersex");
                String tel = resultSet.getString("membertel");
                String coursecno = resultSet.getString("memcoursecno");
                String times = resultSet.getString("coursetimes");
                String purchasetime = resultSet.getString("purchasetime");

                member member1 = new member(cno, name, sex, tel, coursecno, times, purchasetime);
                members.add(member1);
            }

            // 关闭数据库连接
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
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

    @FXML
    private void updateTableView(List<member> members) throws Exception {
// 传入List集合，更新TableView
        memberTableView.getItems().setAll(members);
// 为每列设置单元格值工厂，以从对象中获取属性值显示。
        membercno.setCellValueFactory(new PropertyValueFactory<>
                ("membercno"));
        membername.setCellValueFactory(new PropertyValueFactory<>
                ("membername"));
        memcoursecno.setCellValueFactory(new PropertyValueFactory<>
                ("memcoursecno"));
        membersex.setCellValueFactory(new PropertyValueFactory<>
                ("membersex"));
        membertel.setCellValueFactory(new PropertyValueFactory<>
                ("membertel"));
        coursetimes.setCellValueFactory(new PropertyValueFactory<>
                ("coursetimes"));
        purchasetime.setCellValueFactory(new PropertyValueFactory<>
                ("purchasetime"));

    }

    private List<member> getCurrentPageData() {
        int startIndex = pagination.getCurrentPageIndex() * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, allMembers.size());
        return allMembers.subList(startIndex, endIndex);
    }

    private void updateToDatabase(member selectedMember) {
        try {
            // 创建数据库连接对象
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fitness", "root", "13034870875");

            // 构造更新语句
            String sql = "UPDATE member SET membername = ?, membertel = ?, membersex = ?, memcoursecno = ?, coursetimes = ?, purchasetime = ? WHERE membercno = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, selectedMember.getMembername());
            pstmt.setString(2, selectedMember.getMembertel());
            pstmt.setString(3, selectedMember.getMembersex());
            pstmt.setString(4, selectedMember.getMemcoursecno());
            pstmt.setString(5, selectedMember.getCoursetimes());
            pstmt.setString(6, selectedMember.getPurchasetime());
            pstmt.setString(7, selectedMember.getMembercno());

            // 执行更新操作
            pstmt.executeUpdate();

            // 关闭预编译语句和连接对象
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<member> searchFromDatabase(String keyword) {
        ArrayList<member> members = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fitness", "root", "13034870875");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM member WHERE membercno LIKE ? OR membername LIKE ? OR membertel like ?");
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");
            statement.setString(3, "%" + keyword + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String cno = resultSet.getString("membercno");
                String name = resultSet.getString("membername");
                String sex = resultSet.getString("membersex");
                String tel = resultSet.getString("membertel");
                String coursecno = resultSet.getString("memcoursecno");
                String times = resultSet.getString("coursetimes");
                String purchasetime = resultSet.getString("purchasetime");

                member member1 = new member(cno, name, sex, tel, coursecno, times, purchasetime);
                members.add(member1);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }








}
