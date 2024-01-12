package com.example.fitness.fellow;

import com.example.fitness.JDBCDAO;

import java.sql.*;
import java.util.List;

public class fellowDao implements JDBCDAO<fellow,String> {


    public static fellow select(String primaryKey) throws SQLException {
        fellow fellow1 = null;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fitness", "root", "13034870875");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM member WHERE membertel = ?");) {
            preparedStatement.setString(1, primaryKey);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    fellow1 = new fellow();
                    fellow1.setMemcoursecno(resultSet.getString("memcoursecno"));
                    fellow1.setMembername(resultSet.getString("membername"));
                    fellow1.setMembertel(resultSet.getString("membertel"));
                    fellow1.setMembercno(resultSet.getString("membercno"));
                    fellow1.setCoursetimes(resultSet.getString("coursetimes"));
                    fellow1.setMembersex(resultSet.getString("membersex"));
                    fellow1.setPurchasetime(resultSet.getString("purchasetime"));


                }
            }
        }

        return fellow1;
    }
    @Override
    public void insert(fellow entity) throws SQLException {

    }

    @Override
    public void insertBatch(List<fellow> entities) throws SQLException {

    }

    @Override
    public void updateByid(fellow entity, String key) throws SQLException {

    }

    @Override
    public void updateByCondition(String condition, Object[] params, fellow entity) throws SQLException {

    }

    @Override
    public void delete(String primaryKey) throws SQLException {

    }

    @Override
    public void deleteBatch(List<String> primaryKeys) throws SQLException {

    }

    @Override
    public void deleteByCondition(String condition, Object[] params) throws SQLException {

    }



    @Override
    public List<fellow> selectAll() throws SQLException {
        return null;
    }

    @Override
    public List<fellow> selectByCondition(String conditions) throws SQLException {
        return null;
    }

    @Override
    public List<fellow> selectPaged(int page, int pagesize) throws SQLException {
        return null;
    }

    @Override
    public int countStudentsByBuilding(String building) throws SQLException {
        return 0;
    }

    @Override
    public int gettotalRecords() throws Exception {
        return 0;
    }
}
