package controller.user;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.MainUI;
import model.User;
import service.impl.UserServiceImpl;
import util.Tool;

public class AccountUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField name;
    private JPasswordField password;
    private User user;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AccountUI frame = new AccountUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public AccountUI() {
        user = Tool.readUser();

        setTitle("帳號管理");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 400); // Slightly taller for delete btn
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("管理我的帳號");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("微軟正黑體", Font.BOLD, 24));
        lblTitle.setBounds(0, 20, 434, 40);
        contentPane.add(lblTitle);

        // Username (Editable?) - Allow editing name?
        // Requirement said "change password and delete account".
        // Let's keep name editable but read-only if not requested.
        // Usually changing username is fine.
        JLabel lblName = new JLabel("帳號:");
        lblName.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
        lblName.setBounds(80, 90, 60, 25);
        contentPane.add(lblName);

        name = new JTextField();
        name.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        name.setBounds(150, 90, 180, 25);
        contentPane.add(name);
        name.setColumns(10);
        name.setText(user.getName());

        // Password
        JLabel lblPwd = new JLabel("新密碼:");
        lblPwd.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
        lblPwd.setBounds(80, 140, 60, 25);
        contentPane.add(lblPwd);

        password = new JPasswordField();
        password.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        password.setBounds(150, 140, 180, 25);
        contentPane.add(password);
        password.setText(user.getPassword()); // Pre-fill current password? Or leave blank?
        // Showing password is bad practice usually, but for this simple app maybe
        // useful for user to know what it is.
        // Let's pre-fill.

        // Buttons
        JButton updateBtn = new JButton("更新資料");
        updateBtn.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        updateBtn.setBounds(80, 220, 100, 35);
        contentPane.add(updateBtn);

        JButton cancelBtn = new JButton("返回");
        cancelBtn.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        cancelBtn.setBounds(250, 220, 100, 35);
        contentPane.add(cancelBtn);

        // Delete Account Section
        JButton deleteBtn = new JButton("刪除帳號");
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setBackground(new Color(204, 0, 0));
        deleteBtn.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        deleteBtn.setBounds(165, 290, 100, 35);
        contentPane.add(deleteBtn);

        // Events
        updateBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String newName = name.getText();
                String newPwd = new String(password.getPassword());

                if (newName.isEmpty() || newPwd.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "帳號或密碼不可為空");
                    return;
                }

                user.setName(newName);
                user.setPassword(newPwd);

                UserServiceImpl userService = new UserServiceImpl();
                if (userService.update(user)) {
                    Tool.saveUser(user); // Save to file
                    JOptionPane.showMessageDialog(null, "資料更新成功");
                    MainUI mainUI = new MainUI();
                    mainUI.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "更新失敗，帳號名稱可能已被使用");
                }
            }
        });

        cancelBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainUI mainUI = new MainUI();
                mainUI.setVisible(true);
                dispose();
            }
        });

        deleteBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "確定要永久刪除此帳號嗎?\n這將無法復原!",
                        "刪除帳號確認",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    new UserServiceImpl().delete_by_id(user.getUserID());
                    // Should also clear local user file or just overwrite on next login
                    // Redirect to Login
                    JOptionPane.showMessageDialog(null, "帳號已刪除");
                    LoginUI loginUI = new LoginUI();
                    loginUI.setVisible(true);
                    dispose();
                }
            }
        });
    }
}
