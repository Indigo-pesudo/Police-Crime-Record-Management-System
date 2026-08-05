package dashboard;

import login.LoginPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame implements ActionListener {

    JButton btnDashboard;
    JButton btnManageUsers;
    JButton btnPending;
    JButton btnAudit;
    JButton btnLogout;

    JPanel contentPanel;

    public AdminDashboard(String username) {

        setTitle("PCRMS - Administrator");
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

        JLabel user = new JLabel("Welcome, " + username);
        user.setForeground(Color.WHITE);
        user.setFont(new Font("Segoe UI",Font.PLAIN,16));
        user.setBounds(850,20,200,25);

        header.add(title);
        header.add(user);

        add(header);

        // ================= SIDEBAR =================

        JPanel sidebar = new JPanel();
        sidebar.setBounds(0,70,230,580);
        sidebar.setBackground(new Color(25,45,85));
        sidebar.setLayout(null);

        btnDashboard = createButton("🏠 Dashboard",30);
        btnManageUsers = createButton("👥 Manage Users",90);
        btnPending = createButton("📨 Pending Requests",150);
        btnAudit = createButton("📋 Audit Logs",210);
        btnLogout = createButton("🚪 Logout",500);

        sidebar.add(btnDashboard);
        sidebar.add(btnManageUsers);
        sidebar.add(btnPending);
        sidebar.add(btnAudit);
        sidebar.add(btnLogout);

        add(sidebar);

        // ================= CONTENT =================

        contentPanel = new JPanel();
        contentPanel.setBounds(230,70,870,580);
        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.WHITE);

        JLabel heading = new JLabel("Administrator Dashboard");
        heading.setFont(new Font("Segoe UI",Font.BOLD,28));
        heading.setBounds(30,20,400,40);

        JLabel msg = new JLabel(
                "<html>Welcome Administrator.<br><br>" +
                "Use the left menu to manage users, approve requests, " +
                "view audit logs and monitor the PCRMS.</html>");

        msg.setFont(new Font("Segoe UI",Font.PLAIN,18));
        msg.setBounds(30,80,700,100);

        // Cards

        JPanel card1 = createCard("Total Users","25",30,220);
        JPanel card2 = createCard("Pending Requests","4",270,220);
        JPanel card3 = createCard("Audit Entries","152",510,220);

        contentPanel.add(heading);
        contentPanel.add(msg);

        contentPanel.add(card1);
        contentPanel.add(card2);
        contentPanel.add(card3);

        add(contentPanel);

        setVisible(true);

    }

    // ================= BUTTON =================

    private JButton createButton(String text,int y){

        JButton button = new JButton(text);

        button.setBounds(15,y,200,42);

        button.setFocusPainted(false);

        button.setFont(new Font("Segoe UI",Font.BOLD,14));

        button.setBackground(new Color(41,128,185));

        button.setForeground(Color.WHITE);

        button.addActionListener(this);

        return button;

    }

    // ================= CARD =================

    private JPanel createCard(String title,String value,int x,int y){

        JPanel card = new JPanel();

        card.setLayout(null);

        card.setBounds(x,y,200,120);

        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel lblTitle = new JLabel(title,SwingConstants.CENTER);

        lblTitle.setBounds(10,15,180,25);

        lblTitle.setFont(new Font("Segoe UI",Font.BOLD,16));

        JLabel lblValue = new JLabel(value,SwingConstants.CENTER);

        lblValue.setBounds(10,50,180,40);

        lblValue.setFont(new Font("Segoe UI",Font.BOLD,32));

        card.add(lblTitle);

        card.add(lblValue);

        return card;

    }

    // ================= EVENTS =================

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==btnManageUsers){

            JOptionPane.showMessageDialog(this,
                    "Manage Users Module\n(Coming in Phase 2)");

        }

        else if(e.getSource()==btnPending){

            JOptionPane.showMessageDialog(this,
                    "Pending Requests Module\n(Coming in Phase 2)");

        }

        else if(e.getSource()==btnAudit){

            JOptionPane.showMessageDialog(this,
                    "Audit Logs Module\n(Coming in Phase 2)");

        }

        else if(e.getSource()==btnLogout){

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Do you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION);

            if(choice==JOptionPane.YES_OPTION){

                dispose();

                new LoginPage();

            }

        }

    }

}