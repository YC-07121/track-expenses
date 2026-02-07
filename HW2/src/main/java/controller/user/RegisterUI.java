package controller.user;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import service.impl.UserServiceImpl;

public class RegisterUI extends JFrame {

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
					RegisterUI frame = new RegisterUI();
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
	public RegisterUI() {
		setTitle("註冊帳號");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Title
		JLabel lblTitle = new JLabel("註冊新帳號");
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

		UserServiceImpl userServiceImpl = new UserServiceImpl();

		// Buttons
		JButton registerBtn = new JButton("確認註冊");
		registerBtn.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		registerBtn.setBounds(100, 220, 100, 35);
		contentPane.add(registerBtn);

		JButton backBtn = new JButton("返回登入");
		backBtn.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		backBtn.setBounds(230, 220, 100, 35);
		contentPane.add(backBtn);

		// Events
		registerBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (userServiceImpl.register(name.getText(), new String(password.getPassword()))) {
					RegisterSuccessUI registerSuccessUI = new RegisterSuccessUI();
					registerSuccessUI.setVisible(true);
					dispose();
				} else {
					RegisterErrorUI registerErrorUI = new RegisterErrorUI();
					registerErrorUI.setVisible(true);
					dispose();
				}
			}
		});

		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LoginUI loginUI = new LoginUI();
				loginUI.setVisible(true);
				dispose();
			}
		});
	}
}
