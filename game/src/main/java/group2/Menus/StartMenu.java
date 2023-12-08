package group2.Menus;

import javax.swing.*;

import group2.GamePanel;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The `StartMenu` class represents the main menu of the Maze Game.
 * It extends JFrame and provides options to start the game, view credits, read
 * game rules, and
 * exit the application.
 */
public class StartMenu extends JFrame {
    /**
     * The width of the window.
     */
    final Integer WINDOW_X = 800;
    /**
     * The height of the window.
     */
    final Integer WINDOW_Y = 800;
    /**
     * The size of the buttons.
     */
    final Integer BUTTON_SIZE = 30;

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

        // Create a vertical box layout for buttons
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalGlue());

        addButton("Start Game", e -> {
            startGame();
            dispose();
        }, verticalBox);

        addButton("Credits", e -> showCredits(), verticalBox);
        addButton("Rules", e -> showRules(), verticalBox);
        addButton("Exit", e -> System.exit(0), verticalBox);

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
     * Adds a button to the menu with the specified text and action listener.
     *
     * @param buttonText     The text to be displayed on the button.
     * @param actionListener The action listener to be associated with the button.
     * @param verticalBox    The vertical box layout to which the button is added.
     */
    private void addButton(String buttonText, ActionListener actionListener, Box verticalBox) {
        JButton button = new JButton(buttonText);
        Font buttonFont = new Font(button.getFont().getName(), Font.PLAIN, BUTTON_SIZE);
        button.setFont(buttonFont);
        button.addActionListener(actionListener);
        verticalBox.add(button);
        verticalBox.add(Box.createVerticalStrut(10));
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