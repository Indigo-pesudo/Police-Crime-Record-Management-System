package login;

import dashboard.AdminDashboard;
import dashboard.InspectorDashboard;
import dashboard.ClerkDashboard;
import util.Constants;
import util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JFrame implements ActionListener {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private JComboBox<String> cmbRole;

    private RoundedButton btnLogin;
    private RoundedButton btnClear;

    private JCheckBox chkShow;

    public LoginPage() {

        setTitle("Police Crime Record Management System");
        setSize(900,550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        getContentPane().setBackground(Constants.PRIMARY);

        // ---------------- LEFT PANEL ----------------

        ImageIcon icon = new ImageIcon(getClass().getResource("/images/police_logo.png"));
        Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(img));
        logo.setBounds(70,60,120,120);
        add(logo);

        JLabel heading = new JLabel ( "<html><center>"
        + "<span style='font-size:20px'>Police</span><br>"
        + "<span style='font-size:20px'>Crime Record</span><br>"
        + "<span style='font-size:20px'>Management System</span>"
        + "</center></html>"
        );

        heading.setForeground(Color.WHITE);
        heading.setBounds(20,170,260,130);
        add(heading);

       JLabel tagline = new JLabel("Log  Track  Protect");
        tagline.setForeground(new Color(220,220,220));
        tagline.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tagline.setHorizontalAlignment(SwingConstants.CENTER);
        tagline.setBounds(20,460,260,25);
        add(tagline);

        // ---------------- LOGIN PANEL ----------------

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Constants.PANEL);
        panel.setBounds(300,40,550,450);
        add(panel);

        JLabel title = new JLabel("LOGIN");
        title.setFont(Constants.TITLE_FONT);
        title.setBounds(220,20,150,40);
        panel.add(title);

        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(Constants.LABEL_FONT);
        lblUser.setBounds(70,90,120,25);
        panel.add(lblUser);

        txtUsername = new JTextField();
        txtUsername.setBounds(70,120,400,38);
        txtUsername.setFont(Constants.TEXT_FONT);
        panel.add(txtUsername);

        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(Constants.LABEL_FONT);
        lblPass.setBounds(70,175,120,25);
        panel.add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(70,205,400,38);
        txtPassword.setFont(Constants.TEXT_FONT);
        panel.add(txtPassword);

        JLabel lblRole = new JLabel("Role");
        lblRole.setFont(Constants.LABEL_FONT);
        lblRole.setBounds(70,260,120,25);
        panel.add(lblRole);

        cmbRole = new JComboBox<>();

        cmbRole.addItem("Administrator");
        cmbRole.addItem("Inspector");
        cmbRole.addItem("Record Clerk");

        cmbRole.setBounds(70,290,400,38);

        panel.add(cmbRole);

        chkShow = new JCheckBox("Show Password");
        chkShow.setBounds(70,340,150,25);
        chkShow.setBackground(Constants.PANEL);

        chkShow.addActionListener(e->{

            if(chkShow.isSelected())
                txtPassword.setEchoChar((char)0);

            else
                txtPassword.setEchoChar('\u2022');

        });

        panel.add(chkShow);

        btnLogin = new RoundedButton("LOGIN");
        btnLogin.setBounds(70,385,160,42);
        btnLogin.addActionListener(this);
        panel.add(btnLogin);

        btnClear = new RoundedButton("CLEAR");
        btnClear.setBounds(310,385,160,42);
        btnClear.addActionListener(this);
        panel.add(btnClear);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==btnClear){

            txtUsername.setText("");
            txtPassword.setText("");
            cmbRole.setSelectedIndex(0);

            return;

        }

        String username = txtUsername.getText().trim();

        String password = String.valueOf(txtPassword.getPassword());

        String role = cmbRole.getSelectedItem().toString();

        // ---------------- ADMIN ----------------

        if(role.equals("Administrator")
                && username.equals("admin")
                && password.equals("admin123")){

            JOptionPane.showMessageDialog(this,
                    "Welcome Administrator");

            dispose();

            new AdminDashboard(username);

            return;

        }

        // ---------------- INSPECTOR ----------------

        if(role.equals("Inspector")
                && username.equals("inspector")
                && password.equals("insp123")){

            JOptionPane.showMessageDialog(this,
                    "Welcome Inspector");

            dispose();

            new InspectorDashboard(username);

            return;

        }

        // ---------------- RECORD CLERK ----------------

        if(role.equals("Record Clerk")
                && username.equals("clerk")
                && password.equals("clerk123")){

            JOptionPane.showMessageDialog(this,
                    "Welcome Record Clerk");

            dispose();

            new ClerkDashboard(username);

            return;

        }

        JOptionPane.showMessageDialog(
                this,
                "Invalid Username, Password or Role.",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);

    }

}