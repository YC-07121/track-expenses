package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Item;
import model.User;
import service.impl.ItemServiceImpl;

public class Tool {

    public static void main(String[] args) {
        Connection conn = Tool.ConnectDB();
        System.out.println(conn);
    }

    public static Connection ConnectDB() {

        String url = "jdbc:mysql://localhost:3306/hw2";
        String user = "root";
        String password = "1234";
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void saveUser(User user) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("User.txt"))) {
            oos.writeObject(user);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static User readUser() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("User.txt"))) {
            return (User) ois.readObject();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static void saveCSV(Date date, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write Header
            writer.write("收入編號,收入類型,收入項目,收入金額,收入時間,,支出編號,支出類型,支出項目,支出金額,支出時間\n");
            ItemServiceImpl itemService = new ItemServiceImpl();
            User user = Tool.readUser();
            List<Item> allItems = itemService.find_by_recorderID(user.getUserID());

            // 1. Get filtered data
            Date selectedDate = date;
            String dateFilter = null;
            if (selectedDate != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                dateFilter = sdf.format(selectedDate);
            }

            List<Item> incomeList = new ArrayList<>();
            List<Item> expenseList = new ArrayList<>();

            for (Item item : allItems) {
                // Apply Filter
                if (dateFilter != null && !dateFilter.isEmpty()) {
                    if (item.getTime() == null || !item.getTime().toString().startsWith(dateFilter)) {
                        continue;
                    }
                }

                if ("income".equals(item.getType())) {
                    incomeList.add(item);
                } else if ("expend".equals(item.getType())) {
                    expenseList.add(item);
                }
            }

            // Get max rows
            int maxRows = Math.max(incomeList.size(), expenseList.size());
            SimpleDateFormat outputSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (int i = 0; i < maxRows; i++) {
                // Left: Income
                if (i < incomeList.size()) {
                    Item in = incomeList.get(i);
                    writer.append(String.valueOf(in.getItemID())).append(",")
                            .append("收入").append(",")
                            .append(in.getItemName()).append(",")
                            .append(String.valueOf(in.getPrice())).append(",")
                            .append(outputSdf.format(in.getTime())).append(",");
                } else {
                    writer.append(",,,,,"); // Empty cells for alignment
                }

                // Right: Expense
                if (i < expenseList.size()) {
                    Item ex = expenseList.get(i);
                    writer.append(",").append(String.valueOf(ex.getItemID())).append(",")
                            .append("支出").append(",")
                            .append(ex.getItemName()).append(",")
                            .append(String.valueOf(ex.getPrice())).append(",")
                            .append(outputSdf.format(ex.getTime())).append("\n");
                } else {
                    writer.append("\n"); // New line for the last income row
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
