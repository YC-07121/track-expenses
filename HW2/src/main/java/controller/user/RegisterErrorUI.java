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

public class RegisterErrorUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterErrorUI frame = new RegisterErrorUI();
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
	public RegisterErrorUI() {
		setTitle("註冊失敗");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 250);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblMessage = new JLabel("註冊失敗");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		lblMessage.setBounds(0, 40, 334, 30);
		contentPane.add(lblMessage);

		JLabel lblDetail = new JLabel("使用者名稱已被使用");
		lblDetail.setHorizontalAlignment(SwingConstants.CENTER);
		lblDetail.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		lblDetail.setBounds(0, 80, 334, 20);
		contentPane.add(lblDetail);

		JButton backBtn = new JButton("返回註冊頁");
		backBtn.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		backBtn.setBounds(100, 140, 132, 30);
		contentPane.add(backBtn);

		// Events
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RegisterUI registerUI = new RegisterUI();
				registerUI.setVisible(true);
				dispose();
			}
		});
	}

}
