package group2.ui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import group2.GamePanel;

/**
 * The StartMenu class represents the main menu of the Maze Game.
 * It provides options to start the game, view credits, read game rules, and
 * exit the application.
 */
public class StartMenu extends JFrame {
    /**
     * The width of the window.
     */
    final Integer WINDOW_X = 500;
    /**
     * The height of the window.
     */
    final Integer WINDOW_Y = 500;

    /**
     * Constructs a new StartMenu.
     * Initializes the main menu with buttons and background image.
     */
    public StartMenu() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Start Menu");
        // Load and scale the background image
        ImageIcon backgroundImageIcon = new ImageIcon("game/resources/menu/menu.jpg");
        Image backgroundImage = backgroundImageIcon.getImage().getScaledInstance(WINDOW_X, WINDOW_Y,
                Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundIcon = new ImageIcon(backgroundImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundIcon);
        setContentPane(backgroundLabel);

        // Initialize and add action listeners to buttons
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
                dispose();
            }
        });

        JButton creditsButton = new JButton("Credits");
        creditsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCredits();
            }
        });

        JButton rulesButton = new JButton("Rules");
        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRules();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        // Create a vertical box layout for buttons
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalGlue());
        verticalBox.add(startButton);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(creditsButton);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(rulesButton);
        verticalBox.add(Box.createVerticalStrut(10));
        verticalBox.add(exitButton);
        verticalBox.add(Box.createVerticalGlue());
        // Set the layout of the background label
        backgroundLabel.setLayout(new BoxLayout(backgroundLabel, BoxLayout.Y_AXIS));
        backgroundLabel.add(Box.createRigidArea(new Dimension(0, 50))); // Adjust the space at the top
        backgroundLabel.add(verticalBox);
        // Set the size and visibility of the JFrame
        setSize(new Dimension(WINDOW_X, WINDOW_Y));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Starts the game by creating a new game window.
     */
    private void startGame() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Maze Game");
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.startGameThread();
    }

    /**
     * Displays a dialog box showing the credits for the game.
     */
    private void showCredits() {
        JOptionPane.showMessageDialog(this, "Developed by:\nMichael Chan\nNathan Chan\nSri Srisathanantham\nShawn Xie",
                "Credits", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays a dialog box showing the rules of the game.
     */
    private void showRules() {
        JOptionPane.showMessageDialog(this,
                "The objective of the game is to escape the maze!\nCollect all the coins while avoiding enemies and traps to unlock the door.\n Collect the optional beer on the field to boost your score higher!",
                "Rules", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * The main method to launch the Maze Game's start menu.
     *
     * @param args UNUSED
     */
    public static void main(String[] args) {
        new StartMenu();
    }
}
