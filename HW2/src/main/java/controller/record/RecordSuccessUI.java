package controller.record;

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

import controller.MainUI;

public class RecordSuccessUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecordSuccessUI frame = new RecordSuccessUI();
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
	public RecordSuccessUI() {
		setTitle("記帳成功");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 250);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblMessage = new JLabel("記帳完成!");
		lblMessage.setBounds(0, 50, 334, 30);
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		contentPane.add(lblMessage);

		JButton backBtn = new JButton("回首頁");
		backBtn.setBounds(50, 130, 100, 30);
		backBtn.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		contentPane.add(backBtn);

		JButton nextBtn = new JButton("再記一筆");
		nextBtn.setBounds(180, 130, 100, 30);
		nextBtn.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		contentPane.add(nextBtn);

		// Events
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainUI mainUI = new MainUI();
				mainUI.setVisible(true);
				dispose();
			}
		});

		nextBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RecordUI recordUI = new RecordUI();
				recordUI.setVisible(true);
				dispose();
			}
		});
	}
}
