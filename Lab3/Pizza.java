
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

class Pizza extends JFrame implements ActionListener{
    
    JTextField txtName;
    JTextField txtAddress;
    JTextField txtPhone;
    JTextField txtEmail;
    JTextField txtCCNum;
    JTextArea txtOrder;
    
    ButtonGroup btnGroupCCType;
    ButtonGroup btnGroupRadio;
    ArrayList<JCheckBox> btnCheck;
    
    Pizza(){
        JPanel panelMain = new JPanel();
        GroupLayout layout = new GroupLayout(panelMain);
	JPanel pnlCCType = new JPanel();
        JPanel pnlButtons = new JPanel();
        JPanel pnlRadios = new JPanel();
        
        txtName = new JTextField(20);
        txtAddress = new JTextField(50);
        txtPhone = new JTextField(10);
        txtEmail = new JTextField(30);
	txtCCNum = new JTextField(16);
        txtOrder = new JTextArea(15,50);
        txtOrder.setEditable(false);
        
        JLabel lName = new JLabel("Name");
        JLabel lAddress = new JLabel("Address");
        JLabel lPhone = new JLabel("Phone");
        JLabel lEmail = new JLabel("Email");
	JLabel lCCNum = new JLabel("Credit Card Number");

	ImageIcon AmexImg = new ImageIcon(((new ImageIcon("images/Amex.png")).getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
	ImageIcon VisaImg = new ImageIcon(((new ImageIcon("images/Visa.png")).getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
	ImageIcon MasterCardImg = new ImageIcon(((new ImageIcon("images/MasterCard.jpg")).getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
	ImageIcon DiscoverImg = new ImageIcon(((new ImageIcon("images/Discover.png")).getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));

        JRadioButton Amex = new JRadioButton("Amex", AmexImg);
        JRadioButton Visa = new JRadioButton("Visa", VisaImg);
        JRadioButton Mastercard = new JRadioButton("Mastercard", MasterCardImg);
        JRadioButton Discover = new JRadioButton("Discover", DiscoverImg);

 	JLabel lblCCType = new JLabel("Credit Card Type:  ");
        lblCCType.setFont(new Font(lblCCType.getFont().getName(), Font.PLAIN, 15));

	//Setting initial Fonts
        Amex.setFont(new Font("ROMAN_BASELINE", Font.PLAIN, 12));
	Visa.setFont(new Font(lblCCType.getFont().getName(), Font.PLAIN, 12));
	Mastercard.setFont(new Font(lblCCType.getFont().getName(), Font.PLAIN, 12));
	Discover.setFont(new Font(lblCCType.getFont().getName(), Font.PLAIN, 12));

	Amex.addActionListener(new ActionListener() {
       	 	@Override
        	public void actionPerformed(ActionEvent e) {
            		Amex.setFont(new Font("ROMAN_BASELINE", Font.BOLD + Font.ITALIC, 12));
			Visa.setFont(new Font(lblCCType.getFont().getName(), Font.PLAIN, 12));
			Mastercard.setFont(new Font(lblCCType.getFont().getName(), Font.PLAIN, 12));
			Discover.setFont(new Font(lblCCType.getFont().getName(), Font.PLAIN, 12));

        	}
    	});

	Visa.addActionListener(new ActionListener() {
       	 	@Override
        	public void actionPerformed(ActionEvent e) {
            		Visa.setFont(new Font("ROMAN_BASELINE", Font.BOLD + Font.ITALIC, 12));
			Amex.setFont(new Font(lblCCType.getFont().getName(), Font.PLAIN, 12));
			Mastercard.setFont(new Font(lblCCType.getFont().getName(), Font.PLAIN, 12));
			Discover.setFont(new Font(lblCCType.getFont().getName(), Font.PLAIN, 12));

        	}
    	});

	Mastercard.addActionListener(new ActionListener() {
       	 	@Override
        	public void actionPerformed(ActionEvent e) {
            		Mastercard.setFont(new Font("ROMAN_BASELINE", Font.BOLD + Font.ITALIC, 12));
			Visa.setFont(new Font(lblCCType.getFont().getName(), Font.PLAIN, 12));
			Amex.setFont(new Font(lblCCType.getFont().getName(), Font.PLAIN, 12));
			Discover.setFont(new Font(lblCCType.getFont().getName(), Font.PLAIN, 12));

        	}
    	});

	Discover.addActionListener(new ActionListener() {
       	 	@Override
        	public void actionPerformed(ActionEvent e) {
            		Discover.setFont(new Font("ROMAN_BASELINE", Font.BOLD + Font.ITALIC, 12));
			Visa.setFont(new Font(lblCCType.getFont().getName(), Font.PLAIN, 12));
			Mastercard.setFont(new Font(lblCCType.getFont().getName(), Font.PLAIN, 12));
			Amex.setFont(new Font(lblCCType.getFont().getName(), Font.PLAIN, 12));

        	}
    	});



	btnGroupCCType = new ButtonGroup();
        btnGroupCCType.add(Amex);
        btnGroupCCType.add(Visa);
        btnGroupCCType.add(Mastercard);
        btnGroupCCType.add(Discover);

        pnlCCType.add(lblCCType);
        pnlCCType.add(Amex);
        pnlCCType.add(Visa);
        pnlCCType.add(Mastercard);
        pnlCCType.add(Discover);

        JRadioButton rad12 = new JRadioButton("12 inch");
        rad12.setActionCommand("12inch");
        JRadioButton rad10 = new JRadioButton("10 inch");
        rad10.setActionCommand("10inch");
        rad10.setSelected(true);
        JRadioButton rad16 = new JRadioButton("16 inch");
        rad16.setActionCommand("16inch");
        
        btnGroupRadio = new ButtonGroup();
        btnGroupRadio.add(rad12);
        btnGroupRadio.add(rad10);
        btnGroupRadio.add(rad16);

        JLabel lblSize = new JLabel("Size:  ");
        lblSize.setFont(new Font(lblSize.getFont().getName(), Font.PLAIN, 20));
        pnlRadios.add(lblSize);
        pnlRadios.add(rad10);
        pnlRadios.add(rad12);
        pnlRadios.add(rad16);
        
        JLabel lblToppings = new JLabel("Toppings:  ");
        lblToppings.setFont(new Font(lblToppings.getFont().getName(), Font.PLAIN, 20));
        JCheckBox chkPepperoni = new JCheckBox("Pepperoni");
        chkPepperoni.setActionCommand("Pepperoni");
        JCheckBox chkPeppers = new JCheckBox("Peppers");
        chkPeppers.setActionCommand("Peppers");
        JCheckBox chkSausage = new JCheckBox("Sausage");
        chkSausage.setActionCommand("Sausage");
        
        btnCheck = new ArrayList<JCheckBox>();
        btnCheck.add(chkPepperoni);
        btnCheck.add(chkPeppers);
        btnCheck.add(chkSausage);
        
        pnlButtons.add(lblToppings);
        pnlButtons.add(chkPepperoni);
        pnlButtons.add(chkPeppers);
        pnlButtons.add(chkSausage);
        
        JButton btnOrder = new JButton("Place Order");
        btnOrder.setActionCommand("order");
        btnOrder.addActionListener(this);
        
        panelMain.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
           layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lName)
                    .addComponent(txtName)
                    .addComponent(lAddress)
                    .addComponent(txtAddress)
                    .addComponent(lPhone)
                    .addComponent(txtPhone)
                    .addComponent(lEmail)
                    .addComponent(txtEmail)
                    .addComponent(lCCNum)
                    .addComponent(txtCCNum)
                    .addComponent(pnlCCType)
                    .addComponent(pnlRadios)
                    .addComponent(pnlButtons)
                    .addComponent(btnOrder))
                .addComponent(txtOrder)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lName)
                    .addComponent(txtName)
                    .addComponent(lAddress)
                    .addComponent(txtAddress)
                    .addComponent(lPhone)
                    .addComponent(txtPhone)
                    .addComponent(lEmail)
                    .addComponent(txtEmail)
                    .addComponent(lCCNum)
                    .addComponent(txtCCNum)
                    .addComponent(pnlCCType)
                    .addComponent(pnlRadios)
                    .addComponent(pnlButtons)
                    .addComponent(btnOrder))
                .addComponent(txtOrder)
        );
        
        setSize(800,500);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pizza Order");

        panelMain.setBackground(Color.gray);
        getContentPane().add(panelMain);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        setLocation(x, y);
        
        setVisible(true);          
    }
    
    public void actionPerformed(ActionEvent e){
           if(e.getActionCommand().equals("order")){
               StringBuffer strOrder = new StringBuffer("Customer Information \n--------------------------------\n");
               strOrder.append(txtName.getText() + "\n");
               strOrder.append(txtAddress.getText() + "\n");
               strOrder.append(txtPhone.getText() + "\n");
               strOrder.append(txtEmail.getText() + "\n");

	       strOrder.append("\nPayment Information \n--------------------------------\n");
               strOrder.append(txtCCNum.getText() + "\n");
               strOrder.append(btnGroupCCType.getSelection().getActionCommand() + "\n");

               strOrder.append("\nOrder Information \n--------------------------------\n");
               strOrder.append(btnGroupRadio.getSelection().getActionCommand() + "\n");
               Iterator<JCheckBox> iterCheckButtons = btnCheck.iterator();
               while(iterCheckButtons.hasNext()){
                   JCheckBox nextBox = iterCheckButtons.next();
                   if(nextBox.isSelected()){
                       strOrder.append("+ " + nextBox.getActionCommand() + "\n");
                       
                   }
                   
               }
               
               txtOrder.setText(strOrder.toString());
           }
    } 
    
    public static void main(String args[]){
        new Pizza();
    }
}
