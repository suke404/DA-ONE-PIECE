package group2.Menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinMenu extends JFrame {
    /**
     * The width of the window.
     */
    final Integer WINDOW_X = 800;
    /**
     * The height of the window.
     */
    final Integer WINDOW_Y = 800;

    final Integer BUTTON_SIZE = 30;

    public WinMenu() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("That's all folks!");

        // Load the background image
        ImageIcon backgroundImageIcon = new ImageIcon("game/resources/menu/win.jpeg");
        Image backgroundImage = backgroundImageIcon.getImage().getScaledInstance(WINDOW_X, WINDOW_Y,
                Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundIcon = new ImageIcon(backgroundImage);

        // Create a JLabel with the background image
        JLabel backgroundLabel = new JLabel(scaledBackgroundIcon);
        setContentPane(backgroundLabel);

        // JButton restartButton = new JButton("Restart");
        // restartButton.addActionListener(new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // System.out.println("restart");
        // }
        // });

        JButton quitButton = new JButton("Quit");
        // Create a larger font for buttons
        Font buttonFont = new Font(quitButton.getFont().getName(), Font.PLAIN, BUTTON_SIZE);
        quitButton.setFont(buttonFont);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Create a vertical box layout
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalGlue());
        // verticalBox.add(restartButton);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(quitButton);
        verticalBox.add(Box.createVerticalGlue());

        // Set the layout of the JFrame
        backgroundLabel.setLayout(new BoxLayout(backgroundLabel, BoxLayout.Y_AXIS));
        backgroundLabel.add(Box.createRigidArea(new Dimension(0, 50))); // Adjust the space at the top
        backgroundLabel.add(verticalBox);

        // Set the preferred size of the JFrame
        setSize(new Dimension(WINDOW_X, WINDOW_Y));

        setLocationRelativeTo(null);
        setVisible(true);
    }
}