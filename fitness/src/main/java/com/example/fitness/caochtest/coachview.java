package com.example.fitness.caochtest;

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
public class coachview
{
        @FXML
        private TableView<coach> coachTableView;
        @FXML
        private TextField coachcnoField;

        @FXML
        private TextField coachnameField;

        @FXML
        private TextField coachsexField;

        @FXML
        private TextField telField;

        @FXML
        private TextField coursenameField;

        @FXML
        private TextField searchField;

        @FXML
        private TableColumn<coach, String> coachcno;

        @FXML
        private TableColumn<coach, String> coachname;

        @FXML
        private TableColumn<coach, String> coachsex;

        @FXML
        private TableColumn<coach, String> coachtel;
        @FXML
        private TableColumn<coach, String> coursename;
        @FXML
        private  Button addbutton;

        @FXML
        private Pagination pagination;
        private int itemsPerPage = 20; // 每页显示的记录数
        private ArrayList<coach> allMembers;
        @FXML
        //连接数据库将各个属性连接起来
        private ArrayList<coach> getDataFromDatabase() {

        ArrayList<coach> coaches = new ArrayList<>();
        try {
                // 获取数据库连接
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fitness", "root", "13034870875");

                // 执行查询语句
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM coach");

                // 遍历结果集，将数据存储在ArrayList中
                while (resultSet.next()) {
                        String cno = resultSet.getString("coachcno");
                        String name = resultSet.getString("coachname");
                        String sex = resultSet.getString("coachsex");
                        String tel = resultSet.getString("coachtel");
                        String coursename = resultSet.getString("coursename");
                        coach coach1 = new coach(cno, name, sex, tel, coursename );
                        System.out.println(name);
                        coaches.add(coach1);
                }

                // 关闭数据库连接
                resultSet.close();
                statement.close();
                connection.close();

        } catch (SQLException e) {
                e.printStackTrace();
        }

        return  coaches;
}
        @FXML
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
                List<coach> subList = allMembers.subList(startIndex, endIndex);
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
        @FXML
        private void initializeTableView() {
                // 将ArrayList中的数据加载到TableView中
                List<coach> subList = allMembers.subList(0, Math.min(itemsPerPage, allMembers.size()));
                coachTableView.getItems().setAll(subList);

                // 将每个TableColumn与coach对象的相应属性绑定起来
                coachcno.setCellValueFactory(new PropertyValueFactory<>("coachcno"));
                coachname.setCellValueFactory(new PropertyValueFactory<>("coachname"));
                coachsex.setCellValueFactory(new PropertyValueFactory<>("coachsex"));
                coachtel.setCellValueFactory(new PropertyValueFactory<>("coachtel"));
                coursename.setCellValueFactory(new PropertyValueFactory<>("coursename"));

        }
        @FXML
        private void addcoach(ActionEvent event) {
                // 获取表单中的数据
                String cno = coachcnoField.getText();
                String name = coachnameField.getText();
                String sex = coachsexField.getText();
                String tel = telField.getText();
                String coursename = coursenameField.getText();
                coachcnoField.clear();
                coachsexField.clear();
                coursenameField.clear();
                coachnameField.clear();
                telField.clear();

                // 在数据库中添加新的会员记录
                try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fitness", "root", "13034870875");
                        PreparedStatement statement = connection.prepareStatement("INSERT INTO coach (coachcno,coachsex,coachtel,coachname,coursename) VALUES (?, ?, ?, ?,?)");
                        statement.setString(1, cno);
                        statement.setString(2, sex);
                        statement.setString(3, tel);
                        statement.setString(4, name);
                        statement.setString(5, coursename);
                        List<coach> currentPageData = getCurrentPageData();
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
                coach selectedMember = coachTableView.getSelectionModel().getSelectedItem();

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
                                String sql = "DELETE FROM coach WHERE coachcno = ?";
                                statement = connection.prepareStatement(sql);
                                statement.setString(1, selectedMember.getCoachcno());
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

                        List<coach> currentPageData = getCurrentPageData();
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
                ArrayList<coach> coaches = searchFromDatabase(keyword);
                if (coaches.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("提示");
                        alert.setHeaderText(null);
                        alert.setContentText("查无此人");
                        alert.showAndWait();
                } else {
                        // 更新TableView
                        updateTableView(coaches);
                }
        }

        @FXML
        private void btnEdit(ActionEvent event) {
                // 获取选中的学生对象
                coach selectedStudent = coachTableView.getSelectionModel().getSelectedItem();

                if (selectedStudent != null) {
                        // 将选中的学生信息显示在文本框中
                        coachcnoField.setText(selectedStudent.getCoachcno());
                        coachnameField.setText(selectedStudent.getCoachname());
                        telField.setText(selectedStudent.getCoachtel());
                        coachsexField.setText(selectedStudent.getCoachsex());
                        coursenameField.setText(selectedStudent.getCoursename());
                }
        }

        @FXML
        private void savecoach(ActionEvent event) throws Exception{
                // 获取选中的会员对象
                coach selectedMember = coachTableView.getSelectionModel().getSelectedItem();

                if (selectedMember != null) {
                        // 更新会员对象
                        selectedMember.setCoachname(coursenameField.getText());
                        selectedMember.setCoachtel(telField.getText());
                        selectedMember.setCoachsex(coachsexField.getText());
                        selectedMember.setCoachcno(coachcnoField.getText());
                        selectedMember.setCoursename(coursenameField.getText());
                        coachcnoField.clear();
                        coachsexField.clear();
                        coursenameField.clear();
                        coachnameField.clear();
                        telField.clear();

                        // 将更新后的会员对象保存到数据库
                        updateToDatabase(selectedMember);

                        List<coach> currentPageData = getCurrentPageData();
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
        private void updateTableView(List<coach> coaches) throws Exception {
// 传入List集合，更新TableView
                coachTableView.getItems().setAll(coaches);
// 为每列设置单元格值工厂，以从对象中获取属性值显示。
                coachcno.setCellValueFactory(new PropertyValueFactory<>
                        ("coachcno"));
                coachname.setCellValueFactory(new PropertyValueFactory<>
                        ("coachname"));
                coachsex.setCellValueFactory(new PropertyValueFactory<>
                        ("coachsex"));
                coachtel.setCellValueFactory(new PropertyValueFactory<>
                        ("coachtel"));
                coursename.setCellValueFactory(new PropertyValueFactory<>
                        ("coursename"));

        }
        private List<coach> getCurrentPageData() {
                int startIndex = pagination.getCurrentPageIndex() * itemsPerPage;
                int endIndex = Math.min(startIndex + itemsPerPage, allMembers.size());
                return allMembers.subList(startIndex, endIndex);
        }
        private void updateToDatabase(coach selectedMember) {
                try {
                        // 创建数据库连接对象
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fitness", "root", "13034870875");

                        // 构造更新语句
                        String sql = "UPDATE coach SET coachsex=? ,coachtel=?,coachname=?,coursename=?WHERE coachcno = ?";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, selectedMember.getCoachsex());
                        pstmt.setString(2, selectedMember.getCoachtel());
                        pstmt.setString(3, selectedMember.getCoachname());
                        pstmt.setString(4, selectedMember.getCoursename());
                        pstmt.setString(5, selectedMember.getCoachcno());


                        // 执行更新操作
                        pstmt.executeUpdate();

                        // 关闭预编译语句和连接对象
                        pstmt.close();
                        conn.close();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }

        private ArrayList<coach> searchFromDatabase(String keyword) {
                ArrayList<coach> coaches = new ArrayList<>();

                try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fitness", "root", "13034870875");
                        PreparedStatement statement = connection.prepareStatement("SELECT * FROM coach WHERE coachname LIKE ? OR coachtel LIKE ?");
                        statement.setString(1, "%" + keyword + "%");
                        statement.setString(2, "%" + keyword + "%");
                        ResultSet resultSet = statement.executeQuery();

                        while (resultSet.next()) {
                                String cno = resultSet.getString("coachcno");
                                String name = resultSet.getString("coachname");
                                String sex = resultSet.getString("coachsex");
                                String tel = resultSet.getString("coachtel");
                                String coursename = resultSet.getString("coursename");


                                coach coach1 = new coach(cno, name, sex, tel, coursename);
                                coaches.add(coach1);
                        }

                        resultSet.close();
                        statement.close();
                        connection.close();

                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return coaches;
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
        private void jumpmember(ActionEvent event) throws IOException {
                // 加载新的界面
                Parent root = FXMLLoader.load(getClass().getResource("/member.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
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


