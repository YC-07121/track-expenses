package controller.user;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.MainUI;
import model.User;
import service.impl.UserServiceImpl;
import util.Tool;

public class LoginUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField name;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI frame = new LoginUI();
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
	public LoginUI() {
		setTitle("登入系統");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		setLocationRelativeTo(null); // Center on screen
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Title
		JLabel lblTitle = new JLabel("記帳系統登入");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("微軟正黑體", Font.BOLD, 24));
		lblTitle.setBounds(0, 30, 434, 40);
		contentPane.add(lblTitle);

		// Username
		JLabel lblName = new JLabel("帳號:");
		lblName.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		lblName.setBounds(80, 100, 60, 25);
		contentPane.add(lblName);

		name = new JTextField();
		name.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		name.setBounds(150, 100, 180, 25);
		contentPane.add(name);
		name.setColumns(10);

		// Password
		JLabel lblPwd = new JLabel("密碼:");
		lblPwd.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		lblPwd.setBounds(80, 150, 60, 25);
		contentPane.add(lblPwd);

		password = new JPasswordField();
		password.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		password.setBounds(150, 150, 180, 25);
		contentPane.add(password);

		// Service
		UserServiceImpl userServiceImpl = new UserServiceImpl();

		// Buttons
		JButton loginBtn = new JButton("登入");
		loginBtn.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		loginBtn.setBounds(100, 220, 100, 35);
		contentPane.add(loginBtn);

		JButton registerBtn = new JButton("註冊");
		registerBtn.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		registerBtn.setBounds(230, 220, 100, 35);
		contentPane.add(registerBtn);

		// Events
		loginBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String username = name.getText();
				String pwd = new String(password.getPassword());

				if (userServiceImpl.login(username, pwd)) {
					List<User> l = userServiceImpl.find_by_name(username);
					if (l.size() > 0) {
						Tool.saveUser(l.get(0));
					}
					MainUI mainUI = new MainUI();
					mainUI.setVisible(true);
					dispose();
				} else {
					LoginErrorUI loginErrorUI = new LoginErrorUI();
					loginErrorUI.setVisible(true);
					dispose();
				}
			}
		});

		registerBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RegisterUI registerUI = new RegisterUI();
				registerUI.setVisible(true);
				dispose();
			}
		});
	}
}
