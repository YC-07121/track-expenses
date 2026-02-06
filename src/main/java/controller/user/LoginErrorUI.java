package controller.user;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class LoginErrorUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginErrorUI frame = new LoginErrorUI();
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
	public LoginErrorUI() {
		setTitle("登入失敗");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 250);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblMessage = new JLabel("登入失敗");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		lblMessage.setBounds(0, 40, 334, 30);
		contentPane.add(lblMessage);

		JLabel lblDetail = new JLabel("帳號或密碼錯誤，請重試");
		lblDetail.setHorizontalAlignment(SwingConstants.CENTER);
		lblDetail.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		lblDetail.setBounds(0, 80, 334, 20);
		contentPane.add(lblDetail);

		JButton backBtn = new JButton("返回登入");
		backBtn.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		backBtn.setBounds(40, 140, 110, 30);
		contentPane.add(backBtn);

		JButton registerBtn = new JButton("註冊帳號");
		registerBtn.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		registerBtn.setBounds(180, 140, 110, 30);
		contentPane.add(registerBtn);

		// Events
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LoginUI loginUI = new LoginUI();
				loginUI.setVisible(true);
				dispose();
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
