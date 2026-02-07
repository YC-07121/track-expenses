package controller.record;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import service.impl.ItemServiceImpl;

public class EditUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField name;
    private JTextField price;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private Item currentItem;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // For testing, would need a dummy item
                    EditUI frame = new EditUI(null);
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
    public EditUI(Item item) {
        this.currentItem = item;

        setTitle("編輯項目");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 350);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Title
        JLabel lblTitle = new JLabel("編輯記帳項目");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("微軟正黑體", Font.BOLD, 24));
        lblTitle.setBounds(0, 20, 434, 40);
        contentPane.add(lblTitle);

        // Type Selection
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

        // Pre-fill Data
        if (item != null) {
            name.setText(item.getItemName());
            price.setText(String.valueOf(item.getPrice()));
            if ("income".equals(item.getType())) {
                income.setSelected(true);
            } else {
                expend.setSelected(true);
            }
        }

        // Buttons
        JButton updateBtn = new JButton("確認修改");
        updateBtn.setFont(new Font("微軟正黑體", Font.BOLD, 14));
        updateBtn.setBounds(100, 240, 100, 35);
        contentPane.add(updateBtn);

        JButton cancelBtn = new JButton("取消返回");
        cancelBtn.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        cancelBtn.setBounds(230, 240, 100, 35);
        contentPane.add(cancelBtn);

        // Events
        updateBtn.addMouseListener(new MouseAdapter() {
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

                    // Update Item Object
                    currentItem.setType(type);
                    currentItem.setItemName(name.getText());
                    currentItem.setPrice(amount);
                    // currentItem.setTime() - Keep original time

                    new ItemServiceImpl().update(currentItem);

                    // Using RecordSuccessUI for simplicity or create EditSuccessUI?
                    // Let's create a generic message and return to MainUI for now layout polish
                    // requested
                    // Reusing RecordSuccessUI is a bit weird text-wise ("記帳完成").
                    // I'll show a dialog and go to MainUI.

                    JOptionPane.showMessageDialog(null, "修改成功");
                    MainUI mainUI = new MainUI();
                    mainUI.setVisible(true);
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
