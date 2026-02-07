package controller.record;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.MainUI;
import model.Item;
import model.User;
import service.impl.ItemServiceImpl;
import util.Tool;

public class RecordUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField name;
	private JTextField price;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecordUI frame = new RecordUI();
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
	public RecordUI() {
		setTitle("記帳");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		User user = Tool.readUser();

		// Title
		JLabel lblTitle = new JLabel("新增記帳項目");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("微軟正黑體", Font.BOLD, 24));
		lblTitle.setBounds(0, 20, 434, 40);
		contentPane.add(lblTitle);

		// Type Selection (Radio Buttons)
		JLabel lblType = new JLabel("類別:");
		lblType.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		lblType.setBounds(80, 80, 60, 25);
		contentPane.add(lblType);

		JRadioButton income = new JRadioButton("收入 (Income)");
		income.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		buttonGroup.add(income);
		income.setBounds(150, 80, 120, 25);
		contentPane.add(income);

		JRadioButton expend = new JRadioButton("支出 (Expense)");
		expend.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		buttonGroup.add(expend);
		expend.setBounds(270, 80, 130, 25);
		expend.setSelected(true);
		contentPane.add(expend);

		// Name
		JLabel lblName = new JLabel("項目:");
		lblName.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		lblName.setBounds(80, 130, 60, 25);
		contentPane.add(lblName);

		name = new JTextField();
		name.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		name.setBounds(150, 130, 180, 25);
		contentPane.add(name);
		name.setColumns(10);

		// Price
		JLabel lblPrice = new JLabel("金額:");
		lblPrice.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		lblPrice.setBounds(80, 180, 60, 25);
		contentPane.add(lblPrice);

		price = new JTextField();
		price.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		price.setBounds(150, 180, 180, 25);
		contentPane.add(price);
		price.setColumns(10);

		// Buttons
		JButton recordBtn = new JButton("確認記帳");
		recordBtn.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		recordBtn.setBounds(100, 240, 100, 35);
		contentPane.add(recordBtn);

		JButton cancelBtn = new JButton("返回");
		cancelBtn.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		cancelBtn.setBounds(230, 240, 100, 35);
		contentPane.add(cancelBtn);

		// Events
		recordBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (name.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "名稱不可為空");
						return;
					}
					if (price.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "金額不可為空");
						return;
					}

					int amount = Integer.parseInt(price.getText());
					if (amount < 0) {
						JOptionPane.showMessageDialog(null, "金額必須大於0");
						return;
					}

					String type = expend.isSelected() ? "expend" : "income";

					new ItemServiceImpl().record(new Item(
							type,
							name.getText(),
							amount,
							user.getUserID(),
							new Timestamp(System.currentTimeMillis())));

					RecordSuccessUI recordSuccessUI = new RecordSuccessUI();
					recordSuccessUI.setVisible(true);
					dispose();

				} catch (NumberFormatException exception) {
					JOptionPane.showMessageDialog(null, "金額必須為數字");
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
	}
}
