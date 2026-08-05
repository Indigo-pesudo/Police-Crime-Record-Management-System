package util;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {

    private Color backgroundColor = new Color(41, 128, 185);
    private Color hoverColor = new Color(52, 152, 219);
    private Color pressedColor = new Color(21, 67, 96);

    public RoundedButton(String text) {
        super(text);

        setFont(new Font("Segoe UI", Font.BOLD, 15));
        setForeground(Color.WHITE);

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);

        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isPressed()) {
            g2.setColor(pressedColor);
        }
        else if (getModel().isRollover()) {
            g2.setColor(hoverColor);
        }
        else {
            g2.setColor(backgroundColor);
        }

        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                25,
                25);

        FontMetrics fm = g2.getFontMetrics(getFont());

        int x = (getWidth() - fm.stringWidth(getText())) / 2;

        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();

        g2.setFont(getFont());

        g2.setColor(getForeground());

        g2.drawString(getText(), x, y);

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // No border
    }
}