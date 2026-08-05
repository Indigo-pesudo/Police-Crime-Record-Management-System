package dashboard;

import login.LoginPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InspectorDashboard extends JFrame implements ActionListener {

    JButton btnDashboard;
    JButton btnRegisterFIR;
    JButton btnEvidence;
    JButton btnCase;
    JButton btnLogout;

    JPanel contentPanel;

    public InspectorDashboard(String username) {

        setTitle("PCRMS - Inspector Dashboard");
        setSize(1100,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // ================= HEADER =================

        JPanel header = new JPanel();
        header.setBounds(0,0,1100,70);
        header.setBackground(new Color(15,32,65));
        header.setLayout(null);

        JLabel title = new JLabel("POLICE CRIME RECORD MANAGEMENT SYSTEM");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI",Font.BOLD,22));
        title.setBounds(20,18,600,30);

        JLabel user = new JLabel("Welcome Inspector : " + username);
        user.setForeground(Color.WHITE);
        user.setFont(new Font("Segoe UI",Font.PLAIN,16));
        user.setBounds(780,20,300,25);

        header.add(title);
        header.add(user);

        add(header);

        // ================= SIDEBAR =================

        JPanel sidebar = new JPanel();
        sidebar.setBounds(0,70,230,580);
        sidebar.setBackground(new Color(25,45,85));
        sidebar.setLayout(null);

        btnDashboard = createButton("🏠 Dashboard",30);
        btnRegisterFIR = createButton("📝 Register FIR",90);
        btnEvidence = createButton("📦 Evidence",150);
        btnCase = createButton("📂 Case Management",210);
        btnLogout = createButton("🚪 Logout",500);

        sidebar.add(btnDashboard);
        sidebar.add(btnRegisterFIR);
        sidebar.add(btnEvidence);
        sidebar.add(btnCase);
        sidebar.add(btnLogout);

        add(sidebar);

        // ================= CONTENT =================

        contentPanel = new JPanel();
        contentPanel.setBounds(230,70,870,580);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(null);

        JLabel heading = new JLabel("Inspector Dashboard");
        heading.setFont(new Font("Segoe UI",Font.BOLD,28));
        heading.setBounds(30,20,350,40);

        JLabel msg = new JLabel(
                "<html>Welcome Inspector.<br><br>" +
                "Use the left menu to register FIRs, manage evidence " +
                "and update case information.</html>");

        msg.setFont(new Font("Segoe UI",Font.PLAIN,18));
        msg.setBounds(30,80,700,80);

        JPanel card1 = createCard("Registered FIRs","125",30,200);
        JPanel card2 = createCard("Evidence Items","86",280,200);
        JPanel card3 = createCard("Active Cases","39",530,200);

        contentPanel.add(heading);
        contentPanel.add(msg);
        contentPanel.add(card1);
        contentPanel.add(card2);
        contentPanel.add(card3);

        add(contentPanel);

        setVisible(true);
    }

    private JButton createButton(String text,int y){

        JButton button=new JButton(text);

        button.setBounds(15,y,200,42);

        button.setFocusPainted(false);

        button.setFont(new Font("Segoe UI",Font.BOLD,14));

        button.setBackground(new Color(41,128,185));

        button.setForeground(Color.WHITE);

        button.addActionListener(this);

        return button;
    }

    private JPanel createCard(String title,String value,int x,int y){

        JPanel panel=new JPanel();

        panel.setLayout(null);

        panel.setBounds(x,y,200,120);

        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel lblTitle=new JLabel(title,SwingConstants.CENTER);

        lblTitle.setBounds(10,15,180,25);

        lblTitle.setFont(new Font("Segoe UI",Font.BOLD,16));

        JLabel lblValue=new JLabel(value,SwingConstants.CENTER);

        lblValue.setBounds(10,50,180,40);

        lblValue.setFont(new Font("Segoe UI",Font.BOLD,32));

        panel.add(lblTitle);

        panel.add(lblValue);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==btnRegisterFIR){

            JOptionPane.showMessageDialog(this,
                    "Register FIR Module\n(Coming in Phase 2)");

        }

        else if(e.getSource()==btnEvidence){

            JOptionPane.showMessageDialog(this,
                    "Evidence Management Module\n(Coming in Phase 2)");

        }

        else if(e.getSource()==btnCase){

            JOptionPane.showMessageDialog(this,
                    "Case Management Module\n(Coming in Phase 2)");

        }

        else if(e.getSource()==btnLogout){

            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Do you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION);

            if(option==JOptionPane.YES_OPTION){

                dispose();

                new LoginPage();

            }

        }

    }

}