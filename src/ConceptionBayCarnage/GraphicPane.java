package ConceptionBayCarnage;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * The GraphicPane class is used to create the panel and manage draw methods
 *
 * @author David Budgell, Tim Bonnell, K. Craig Webber
 */
public class GraphicPane extends JPanel {

    private Image road, road2, logo;
    private ArrayList<Integer> buttonsPressed;
    private GameSession newGame;
    private Timer timer;
    private boolean pausedState;
    private JLabel scoreLabel, healthLabel, livesLabel;
    private Boolean gameStarted = false;
    private JPanel subPanelDifficulty;
    private JButton startButton1, startButton2, startButton3;
    private JButton returnToMain;
    private int persistentDifficulty;

    private JTextField newNameScore;
    JPanel newHighScorePanel;
    JButton retryButton;

    /**
     * creates a new GraphicPane object
     */
    public GraphicPane() {
        this.setLayout(new BorderLayout());
        this.newGame = new GameSession(2);
        this.pausedState = false;
        this.buttonsPressed = new ArrayList<Integer>();
        this.addKeyListener(new gameKeys());

        ImageIcon roadImage = new ImageIcon("road.png");
        road = roadImage.getImage();
        ImageIcon roadImage2 = new ImageIcon("road.png");
        road2 = roadImage2.getImage();
        ImageIcon logoImage = new ImageIcon("logo.png");
        logo = logoImage.getImage();
        this.subPanelDifficulty = new JPanel();
        this.startButton1 = new JButton("Start-Easy");
        startButton1.addMouseListener(new ActionStart());
        subPanelDifficulty.add(startButton1);
        this.startButton2 = new JButton("Start-Normal");
        startButton2.addMouseListener(new ActionStart());
        subPanelDifficulty.add(startButton2);
        this.startButton3 = new JButton("Start-Hard");
        startButton3.addMouseListener(new ActionStart());
        subPanelDifficulty.add(startButton3);
        this.add(subPanelDifficulty, BorderLayout.NORTH);

    } //End GraphicPane

    /**
     * P
     */
    private void returnToMainMenu() {
        System.out.println("Return to main");
        this.removeAll();
        this.revalidate();
        this.subPanelDifficulty = new JPanel();
        this.startButton1 = new JButton("Start-Easy");
        startButton1.addMouseListener(new ActionStart());
        subPanelDifficulty.add(startButton1);
        this.startButton2 = new JButton("Start-Normal");
        startButton2.addMouseListener(new ActionStart());
        subPanelDifficulty.add(startButton2);
        this.startButton3 = new JButton("Start-Hard");
        startButton3.addMouseListener(new ActionStart());
        subPanelDifficulty.add(startButton3);
        this.add(subPanelDifficulty, BorderLayout.NORTH);
        ImageIcon logoImage = new ImageIcon("logo.png");
        logo = logoImage.getImage();
        this.repaint();
    }

    /**
     * starts a new game session
     *
     * @param difficulty
     */
    private void start(int difficulty) {
        System.out.println("Clearing GraphicPane on start");
        this.removeAll();
        if (timer != null) {
            timer.stop();
        }
        System.out.println("Regenerating level on start");
        newGame = new GameSession(difficulty); // Todo
        ImageIcon logoImage = new ImageIcon("");
        this.gameStarted = true;
        logo = logoImage.getImage();

        newGame.startGame();

        this.timer = new Timer(18, ae -> update());
        timer.start();

        JPanel topPanel = new JPanel();
        retryButton = new JButton("Retry");
        retryButton.addMouseListener(new ActionStart());
        returnToMain = new JButton("Return");
        returnToMain.addMouseListener(new ActionStart());

        this.scoreLabel = new JLabel("Score:");
        this.healthLabel = new JLabel("Health:");
        this.livesLabel = new JLabel("Lives:");
        topPanel.add(scoreLabel);
        topPanel.add(healthLabel);
        topPanel.add(livesLabel);
        topPanel.add(retryButton);
        topPanel.add(returnToMain);
        retryButton.setVisible(false);
        returnToMain.setVisible(false);
        add(topPanel, BorderLayout.NORTH);

        this.repaint();
    } //End switchView

    /**
     * ends a game session
     */
    private void endGame() {
        // Stop the timer
        timer.stop();
        retryButton.setVisible(true);
        returnToMain.setVisible(true);
        for (ActionListener al : this.timer.getActionListeners()) {
            this.timer.removeActionListener(al);
        } //End for

        if (newGame.hasNewHighScore()) {
            newHighScorePanel = new JPanel();
            JLabel setScore = new JLabel("Please Enter Name for New High Score");
            newNameScore = new JTextField("John Doe");
            JButton saveScore = new JButton("Save");
            saveScore.addMouseListener(new ActionSaveScore());
            //newNameScoreTXT = newNameScore.getText();
            newHighScorePanel.add(setScore);
            newHighScorePanel.add(newNameScore);
            newHighScorePanel.add(saveScore);
            add(newHighScorePanel, BorderLayout.SOUTH);
        } else {
            JPanel highScorePanel = new JPanel();
            JTextArea highScores = new JTextArea();
            highScores.setText("Conception Bay Carnage High Scores\n" + ("Player Score is: " + newGame.getPlayerScore() + "\n\n"));
            //highScores.append("Is this a new high score? : " + newGame.hasNewHighScore()+ "\n\n");
            highScores.append(newGame.getTheScoreBoardToString());
            highScores.setEditable(false);
            highScorePanel.add(highScores);
            add(highScorePanel, BorderLayout.SOUTH);
        } //End else

        this.revalidate();
        this.repaint();

    }

    /**
     * pauses the current game session
     */
    private void pauseGame() {
        // Stop the timer
        this.buttonsPressed.clear();
        this.pausedState = true;
        //ImageIcon logoImage = new ImageIcon("logo.png");
        //logo = logoImage.getImage();
        this.repaint();
    }

    /**
     * resumes the current game session
     */
    private void resumeGame() {
        // Restart the timer, resume the game
        this.buttonsPressed.clear();
        this.pausedState = false;
        //ImageIcon logoImage = new ImageIcon("");
        //logo = logoImage.getImage();
        this.repaint();
    }

    /**
     * updates the current game session if an event occurs (atLevelEnd, 0 lives,
     * paused, etc)
     */
    private void update() {

        if (newGame.atLevelEnd()) { // End of Level has been reached
            System.out.println("Level end has been reached");
            System.out.println("Player Score is: " + newGame.getPlayerScore());
            System.out.println("Is this a new high score? : " + newGame.hasNewHighScore());
            /*
             Useful function to assign highscores with names:
             newGame.assignNameToHighScore("INSERT NAME HERE");
             */
            endGame();
        } else if (newGame.getPlayerLives() == 0) {  // Player is out of lives
            System.out.println("Player is out of lives");
            System.out.println("Player Score is: " + newGame.getPlayerScore());
            System.out.println("Is this a new high score? : " + newGame.hasNewHighScore());
            /*
             Useful function to assign highscores with names:
             newGame.assignNameToHighScore("INSERT NAME HERE");
             */
            endGame();
        } else if (buttonsPressed.indexOf(KeyEvent.VK_SPACE) != -1) {
            if (this.pausedState) {
                System.out.println("resuming game");
                resumeGame();
            } else {
                System.out.println("pausing game");
                pauseGame();
            }
        } else if (!this.pausedState) {
            interpretKeys();
            newGame.updateGame();
            newGame.checkCollision();
            this.repaint();
        }
    }

    private void interpretKeys() {
        int moveSpeed = 3;
        if (persistentDifficulty == 1){
            moveSpeed = 5;
        }
        if (!buttonsPressed.isEmpty()) {
            //Interpret pressed keycode and update
            for (int keyVal : buttonsPressed) {
                switch (keyVal) {
                    case KeyEvent.VK_LEFT:
                        if (newGame.getMoveCar().getX() <= newGame.getProgressX() + newGame.getMoveCar().getRadius()) {
                            break;
                        } //End if
                        newGame.getMoveCar().setX(newGame.getMoveCar().getX() - moveSpeed);
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (newGame.getMoveCar().getX() >= newGame.getProgressX() + 720 + newGame.getMoveCar().getRadius()) {
                            break;
                        } //End if
                        newGame.getMoveCar().setX(newGame.getMoveCar().getX() + moveSpeed);
                        break;
                    case KeyEvent.VK_UP:
                        if (newGame.getMoveCar().getY() <= (0 + newGame.getMoveCar().getRadius())) {
                            break;
                        } //End if
                        newGame.getMoveCar().setY(newGame.getMoveCar().getY() - moveSpeed);
                        break;
                    case KeyEvent.VK_DOWN:
                        if (newGame.getMoveCar().getY() >= (800 - (2 * newGame.getMoveCar().getRadius()))) {
                            break;
                        } //End if
                        newGame.getMoveCar().setY(newGame.getMoveCar().getY() + moveSpeed);
                        break;
                } //End switch
            } //End for
        } //End if
    }

    /**
     * draws the required game entities
     *
     * @param g graphic
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Background
        g.drawImage(road, newGame.getBackground1().getX(), 0, null);
        g.drawImage(road, newGame.getBackground2().getX(), 0, null);
        //Draws Game Entities
        for (GameEntity obstacle : newGame.getListedObstacles()) {
            g.drawImage(obstacle.getImageLocation().getImage(),
                    obstacle.getX() - obstacle.getRadius() - newGame.getProgressX(), obstacle.getY() - obstacle.getRadius(), null);
        } //End for

        g.drawImage(newGame.getMoveCar().getImageLocation().getImage(),
                newGame.getMoveCar().getX() - newGame.getMoveCar().getRadius() - newGame.getProgressX(),
                newGame.getMoveCar().getY() - newGame.getMoveCar().getRadius(), null);
        if (this.gameStarted) {
            healthLabel.setText("Health: " + newGame.getPlayerHealth());
            scoreLabel.setText("Score: " + newGame.getPlayerScore());
            livesLabel.setText("Lives: " + newGame.getPlayerLives());
        }
        g.drawImage(logo, 250, 200, null);

    } // End paintComponent

    private class gameKeys implements KeyListener {

        /**
         * checks for keys that are typed
         *
         * @param e key code of key that was typed
         */
        @Override
        public void keyTyped(KeyEvent e) {
            // No Event
        } //End keyTyped

        /**
         * checks for keys that are pressed
         *
         * @param e key code of key that was pressed
         */
        @Override
        public void keyPressed(KeyEvent e) {

            if (!buttonsPressed.contains(e.getKeyCode())) {
                buttonsPressed.add(e.getKeyCode());
            } //End if
        } //End keyPressed

        /**
         * checks for keys that are released
         *
         * @param e key code of key that was released
         */
        @Override
        public void keyReleased(KeyEvent e) {

            buttonsPressed.remove((Integer) e.getKeyCode());
        } //End keyReleased

    }

    private class ActionSaveScore implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            newGame.assignNameToHighScore(newNameScore.getText());
            JPanel highScorePanel = new JPanel();
            JTextArea highScores = new JTextArea();
            highScores.setText("Conception Bay Carnage High Scores\n" + ("Player Score is: " + newGame.getPlayerScore() + "\n\n"));
            highScores.append(newGame.getTheScoreBoardToString());
            highScores.setEditable(false);
            highScorePanel.add(highScores);
            add(highScorePanel, BorderLayout.SOUTH);
            newHighScorePanel.setVisible(false);
            revalidate();
            repaint();

        } //End mouseClicked

        @Override
        public void mousePressed(MouseEvent e) {
            // No Event
        } //End mousePressed

        @Override
        public void mouseReleased(MouseEvent e) {
            // No Event
        } //End mouseReleased

        @Override
        public void mouseEntered(MouseEvent e) {
            // No Event
        } //End mouseEntered

        @Override
        public void mouseExited(MouseEvent e) {
            // No Event
        } //End mouseExited

    } //End ActionSaveScore

    private class ActionStart implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            JButton button = (JButton) e.getSource();
            String buttonName = button.getText();
            if (!buttonName.equalsIgnoreCase("Return")) {
                if (buttonName.equalsIgnoreCase("Start-Easy")) {
                    persistentDifficulty = 1;
                    start(persistentDifficulty);
                } else if (buttonName.equalsIgnoreCase("Start-Normal")) {
                    persistentDifficulty = 2;
                    start(persistentDifficulty);
                } else if (buttonName.equalsIgnoreCase("Start-Hard")) {
                    persistentDifficulty = 3;
                    start(persistentDifficulty);
                }
                start(persistentDifficulty);
                subPanelDifficulty.setVisible(false);
            } else {
                System.out.println("Trying to return");
                returnToMainMenu();
            }

        } //End mouseClicked

        @Override
        public void mousePressed(MouseEvent e) {
            // No Event
        } //End mousePressed

        @Override
        public void mouseReleased(MouseEvent e) {
            // No Event
        } //End mouseReleased

        @Override
        public void mouseEntered(MouseEvent e) {
            // No Event
        } //End mouseEntered

        @Override
        public void mouseExited(MouseEvent e) {
            // No Event
        } //End mouseExited

    } //End ActionStart

} //End Class
