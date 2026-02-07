//TODO CSV匯出的時間修改

package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFileChooser;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.record.EditUI;
import controller.record.RecordUI;
import controller.user.LoginUI;
import model.Item;
import model.User;
import service.impl.ItemServiceImpl;
import util.Tool;

public class MainUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User user;
	private ItemServiceImpl itemService = new ItemServiceImpl();

	private JDatePickerImpl datePicker;
	private JTable incomeTable;
	private JTable expenseTable;
	private JLabel incomeLabel;
	private JLabel expenseLabel;
	private JLabel balanceLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI frame = new MainUI();
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
	public MainUI() {
		user = Tool.readUser();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 939, 647); // Expanded size
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// ==================== Top Panel: Summary & Filter ========================
		JPanel topContainer = new JPanel(new BorderLayout(0, 5));
		topContainer.setBounds(5, 5, 914, 88);
		contentPane.add(topContainer);

		// 1. Summary Cards
		JPanel summaryPanel = new JPanel();
		summaryPanel.setLayout(new GridLayout(1, 3, 10, 0));
		topContainer.add(summaryPanel, BorderLayout.CENTER);

		incomeLabel = new JLabel("$ 0");
		expenseLabel = new JLabel("$ 0");
		balanceLabel = new JLabel("$ 0");

		summaryPanel.add(createSummaryCard("總收入", incomeLabel, new Color(0, 153, 76)));
		summaryPanel.add(createSummaryCard("總支出", expenseLabel, new Color(204, 0, 0)));
		summaryPanel.add(createSummaryCard("結餘", balanceLabel, Color.BLACK));

		// 2. Date Filter with JDatePicker
		JPanel filterPanel = new JPanel();
		topContainer.add(filterPanel, BorderLayout.SOUTH);

		// Add Date/Time Label
		JLabel lblNewLabel_Date = new JLabel("目前時間：");
		lblNewLabel_Date.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		lblNewLabel_Date.setBorder(new EmptyBorder(0, 0, 0, 20)); // Add some spacing
		filterPanel.add(lblNewLabel_Date);

		javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
				lblNewLabel_Date.setText("目前時間：" + time);
			}
		});
		timer.start();

		JLabel dateLbl = new JLabel("日期查詢: ");
		dateLbl.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
		filterPanel.add(dateLbl);

		// Setup JDatePicker
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

		filterPanel.add(datePicker);

		JButton searchBtn = new JButton("查詢");
		filterPanel.add(searchBtn);

		JButton clearBtn = new JButton("清除");
		filterPanel.add(clearBtn);

		// ==================== Center Panel: Split Tables ========================
		JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
		centerPanel.setBounds(5, 103, 914, 451);
		contentPane.add(centerPanel);

		// Left: Income
		JPanel incomePanel = new JPanel(new BorderLayout());
		JLabel incTitle = new JLabel("收入 (Income)");
		incTitle.setHorizontalAlignment(SwingConstants.CENTER);
		incTitle.setFont(new Font("微軟正黑體", Font.BOLD, 16));
		incTitle.setForeground(new Color(0, 153, 76));
		incomePanel.add(incTitle, BorderLayout.NORTH);

		incomeTable = new JTable();
		incomePanel.add(new JScrollPane(incomeTable), BorderLayout.CENTER);
		centerPanel.add(incomePanel);

		// Right: Expense
		JPanel expensePanel = new JPanel(new BorderLayout());
		JLabel expTitle = new JLabel("支出 (Expend)");
		expTitle.setHorizontalAlignment(SwingConstants.CENTER);
		expTitle.setFont(new Font("微軟正黑體", Font.BOLD, 16));
		expTitle.setForeground(new Color(204, 0, 0));
		expensePanel.add(expTitle, BorderLayout.NORTH);

		expenseTable = new JTable();
		expenseTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Select row on right click
				if (e.getButton() == MouseEvent.BUTTON3) {
					int row = expenseTable.rowAtPoint(e.getPoint());
					expenseTable.setRowSelectionInterval(row, row);
				}
			}
		});
		expensePanel.add(new JScrollPane(expenseTable), BorderLayout.CENTER);
		centerPanel.add(expensePanel);

		// Initialize Data
		loadData(null);

		// Context Menu
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem editItem = new JMenuItem("編輯 (Edit)");
		JMenuItem deleteItem = new JMenuItem("刪除 (Delete)");
		popupMenu.add(editItem);
		popupMenu.add(deleteItem);

		incomeTable.setComponentPopupMenu(popupMenu);
		expenseTable.setComponentPopupMenu(popupMenu);

		// Setup selection for right click on Income Table too
		incomeTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					int row = incomeTable.rowAtPoint(e.getPoint());
					incomeTable.setRowSelectionInterval(row, row);
				}
			}
		});

		// Menu Events
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTable targetTable = (JTable) popupMenu.getInvoker();
				int row = targetTable.getSelectedRow();
				if (row == -1)
					return;

				int id = (int) targetTable.getValueAt(row, 0); // Column 0 is ID

				if (e.getSource() == deleteItem) {
					int confirm = JOptionPane.showConfirmDialog(null, "確定要刪除此項目嗎?", "刪除確認", JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						itemService.delete_by_id(id);
						// Refresh
						java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
						String query = null;
						if (selectedDate != null) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							query = sdf.format(selectedDate);
						}
						loadData(query);
						JOptionPane.showMessageDialog(null, "刪除成功");
					}
				} else if (e.getSource() == editItem) {
					// Fetch full Item to edit
					List<Item> list = itemService.find_by_id(id); // Returns List
					if (list.size() > 0) {
						Item item = list.get(0);
						EditUI editUI = new EditUI(item);
						editUI.setVisible(true);
						dispose();
					}
				}
			}
		};

		editItem.addActionListener(menuListener);
		deleteItem.addActionListener(menuListener);

		// ==================== Bottom Panel: Actions ========================
		JPanel actionPanel = new JPanel();
		actionPanel.setBounds(5, 564, 914, 41);
		contentPane.add(actionPanel);

		JButton goRecordBtn = new JButton("記一筆帳");
		goRecordBtn.setFont(new Font("微軟正黑體", Font.BOLD, 16));
		actionPanel.add(goRecordBtn);

		JButton refreshBtn = new JButton("重新整理");
		actionPanel.add(refreshBtn);

		JButton accountBtn = new JButton("帳號管理");
		actionPanel.add(accountBtn);

		JButton printBtn = new JButton("列印 CSV");
		actionPanel.add(printBtn);

		JButton logoutBtn = new JButton("登出");
		actionPanel.add(logoutBtn);

		// ==================== Events ========================
		searchBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Get selected date
				java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
				String query = null;
				if (selectedDate != null) {
					// Format to yyyy-MM-dd
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					query = sdf.format(selectedDate);
				}
				loadData(query);
			}
		});

		clearBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				datePicker.getModel().setValue(null);
				loadData(null);
			}
		});

		goRecordBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RecordUI recordUI = new RecordUI();
				recordUI.setVisible(true);
				dispose();
			}
		});

		refreshBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
				String query = null;
				if (selectedDate != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					query = sdf.format(selectedDate);
				}
				loadData(query);
			}
		});

		accountBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.user.AccountUI accountUI = new controller.user.AccountUI();
				accountUI.setVisible(true);
				dispose();
			}
		});

		logoutBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LoginUI loginUI = new LoginUI();
				loginUI.setVisible(true);
				dispose();
			}
		});

		printBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("儲存 CSV 檔案");
				int userSelection = fileChooser.showSaveDialog(MainUI.this);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File fileToSave = fileChooser.getSelectedFile();
					// Ensure it has .csv extension
					if (!fileToSave.getAbsolutePath().endsWith(".csv")) {
						fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
					}

					try (FileWriter writer = new FileWriter(fileToSave)) {
						// Write Header
						// Income Columns: 收入編號,收入類型,收入項目,收入金額,收入時間
						// Separator
						// Expense Columns: 支出編號,支出類型,支出項目,支出金額,支出時間
						writer.write("收入編號,收入類型,收入項目,收入金額,收入時間,,支出編號,支出類型,支出項目,支出金額,支出時間\n");

						// 1. Get filtered data
						java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
						String dateFilter = null;
						if (selectedDate != null) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							dateFilter = sdf.format(selectedDate);
						}

						List<Item> allItems = itemService.find_by_recorderID(user.getUserID());

						// 2. Separate into Income and Expense lists
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

						// 3. Write rows side-by-side
						int maxRows = Math.max(incomeList.size(), expenseList.size());

						for (int i = 0; i < maxRows; i++) {
							StringBuilder sb = new StringBuilder();

							// Left: Income
							if (i < incomeList.size()) {
								Item in = incomeList.get(i);
								sb.append(in.getItemID()).append(",")
										.append("收入").append(",")
										.append(in.getItemName()).append(",")
										.append(in.getPrice()).append(",")
										.append(in.getTime());
							} else {
								sb.append(",,,,"); // Empty columns for Income
							}

							sb.append(","); // Separator column

							// Right: Expense
							if (i < expenseList.size()) {
								Item ex = expenseList.get(i);
								sb.append(" ").append(",")
										.append(ex.getItemID()).append(",")
										.append("支出").append(",")
										.append(ex.getItemName()).append(",")
										.append(ex.getPrice()).append(",")
										.append(ex.getTime());
							} else {
								sb.append(",,,,"); // Empty columns for Expense
							}

							sb.append("\n");
							writer.write(sb.toString());
						}

						JOptionPane.showMessageDialog(MainUI.this, "匯出成功！\n儲存於: " + fileToSave.getAbsolutePath());

					} catch (IOException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(MainUI.this, "匯出失敗: " + ex.getMessage(), "錯誤",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}

	private void loadData(String dateFilter) {
		if (user == null)
			return;

		List<Item> items = itemService.find_by_recorderID(user.getUserID());

		String[] columnNames = { "ID", "項目", "金額", "時間" };
		DefaultTableModel incomeModel = new DefaultTableModel(columnNames, 0);
		DefaultTableModel expenseModel = new DefaultTableModel(columnNames, 0);

		int totalIncome = 0;
		int totalExpense = 0;

		for (Item item : items) {
			// Filter Logic
			if (dateFilter != null && !dateFilter.isEmpty()) {
				// Checks if the timestamp string starts with the query (e.g. "2024-02-04")
				if (item.getTime() == null || !item.getTime().toString().startsWith(dateFilter)) {
					continue;
				}
			}

			// Add to Table & Calc
			if ("income".equals(item.getType())) {
				totalIncome += item.getPrice();
				incomeModel.addRow(new Object[] {
						item.getItemID(),
						item.getItemName(),
						item.getPrice(),
						item.getTime()
				});
			} else if ("expend".equals(item.getType())) {
				totalExpense += item.getPrice();
				expenseModel.addRow(new Object[] {
						item.getItemID(),
						item.getItemName(),
						item.getPrice(),
						item.getTime()
				});
			}
		}

		incomeTable.setModel(incomeModel);
		incomeTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		incomeTable.getColumnModel().getColumn(0).setMaxWidth(50);

		expenseTable.setModel(expenseModel);
		expenseTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		expenseTable.getColumnModel().getColumn(0).setMaxWidth(50);

		incomeLabel.setText("$ " + totalIncome);
		expenseLabel.setText("$ " + totalExpense);
		balanceLabel.setText("$ " + (totalIncome - totalExpense));
	}

	private JPanel createSummaryCard(String title, JLabel valueLbl, Color valueColor) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		panel.setBorder(new javax.swing.border.LineBorder(new Color(200, 200, 200), 1, true));
		panel.setBackground(Color.WHITE);

		JLabel titleLabel = new JLabel(title);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 14));

		valueLbl.setHorizontalAlignment(SwingConstants.CENTER);
		valueLbl.setFont(new Font("Arial", Font.BOLD, 20));
		valueLbl.setForeground(valueColor);

		panel.add(titleLabel);
		panel.add(valueLbl);
		return panel;
	}

	// Inner class for Date Picker Formatting
	public class DateLabelFormatter extends AbstractFormatter {
		private static final long serialVersionUID = 1L;
		private String datePattern = "yyyy-MM-dd";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}
			return "";
		}
	}
}
