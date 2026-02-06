package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ItemDao;
import model.Item;
import util.Tool;

public class ItemDaoImpl implements ItemDao {
    Connection conn = Tool.ConnectDB();

    @Override
    public void add_item(Item item) {
        String sql = "INSERT INTO item (type, item_name, price, recorder_id, time) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, item.getType());
            preparedStatement.setString(2, item.getItemName());
            preparedStatement.setInt(3, item.getPrice());
            preparedStatement.setInt(4, item.getRecorderID());
            preparedStatement.setTimestamp(5, item.getTime());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Item> select_by_id(int itemID) {
        String sql = "SELECT * FROM item WHERE item_id = ?";
        List<Item> items = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, itemID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Item item = new Item();
                item.setItemID(resultSet.getInt("item_id"));
                item.setType(resultSet.getString("type"));
                item.setItemName(resultSet.getString("item_name"));
                item.setPrice(resultSet.getInt("price"));
                item.setRecorderID(resultSet.getInt("recorder_id"));
                item.setTime(resultSet.getTimestamp("time"));
                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public List<Item> select_by_type(String type) {
        String sql = "SELECT * FROM item WHERE type = ?";
        List<Item> items = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, type);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Item item = new Item();
                item.setItemID(resultSet.getInt("item_id"));
                item.setType(resultSet.getString("type"));
                item.setItemName(resultSet.getString("item_name"));
                item.setPrice(resultSet.getInt("price"));
                item.setRecorderID(resultSet.getInt("recorder_id"));
                item.setTime(resultSet.getTimestamp("time"));
                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public List<Item> select_by_recorderID(int recorderID) {
        String sql = "SELECT * FROM item WHERE recorder_id = ?";
        List<Item> items = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, recorderID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Item item = new Item();
                item.setItemID(resultSet.getInt("item_id"));
                item.setType(resultSet.getString("type"));
                item.setItemName(resultSet.getString("item_name"));
                item.setPrice(resultSet.getInt("price"));
                item.setRecorderID(resultSet.getInt("recorder_id"));
                item.setTime(resultSet.getTimestamp("time"));
                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public void update(Item item) {
        String sql = "UPDATE item SET type = ?, item_name = ?, price = ?, recorder_id = ?, time = ? WHERE item_id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, item.getType());
            preparedStatement.setString(2, item.getItemName());
            preparedStatement.setInt(3, item.getPrice());
            preparedStatement.setInt(4, item.getRecorderID());
            preparedStatement.setTimestamp(5, item.getTime());
            preparedStatement.setInt(6, item.getItemID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete_by_id(int itemID) {
        String sql = "DELETE FROM item WHERE item_id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, itemID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
