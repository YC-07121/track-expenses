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

public class RegisterSuccessUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterSuccessUI frame = new RegisterSuccessUI();
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
	public RegisterSuccessUI() {
		setTitle("註冊成功");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 250);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblMessage = new JLabel("註冊成功");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		lblMessage.setBounds(0, 50, 334, 30);
		contentPane.add(lblMessage);

		JButton backBtn = new JButton("前往登入");
		backBtn.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		backBtn.setBounds(110, 130, 110, 30);
		contentPane.add(backBtn);

		// Events
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
