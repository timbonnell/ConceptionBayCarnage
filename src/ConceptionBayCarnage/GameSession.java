package ConceptionBayCarnage;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import javax.swing.ImageIcon;


/** The GameSession class is used to create a new game session and handle 
 * methods that deal with the current game session
 * @author David Budgell, Tim Bonnell, K. Craig Webber
 */
public class GameSession {
    private LinkedList<GameEntity> listedObstacles;
    private LinkedList<Moveable> moveableObjects;
    private int playerHealth, playerScore, playerLives;
    private HighScoreHandler theScoreboard;
    private Level theLevel;
    private int progressX;
    private int gameSpeed;
    private Vehicle moveCar;
    private RoadBackground scrollBackground2, scrollBackground;

    
    /**creates a new GameSession with a particular difficulty level.
     * 1. Easy 2. Normal 3. Hard
     * @param difficulty - Representing the difficulty setting 1,2 or 3
     */
    public GameSession(int difficulty) {
        if (difficulty == 3)
                this.gameSpeed = 9;
        else {
            this.gameSpeed = 6;
        }
        scrollBackground = new RoadBackground(0);
        scrollBackground2 = new RoadBackground(800);
        this.progressX = 0;
        this.playerHealth = 150;
        this.playerScore = 0;
        this.playerLives = 3;
        theScoreboard = new HighScoreHandler(true); // auto-generates values
        loadScoreBoard();// try to load the serialized GameHandler
        LevelManager game = new LevelManager(20000, 800, 300, difficulty);
        theLevel = game.getLevel();
        listedObstacles = theLevel.getAllEntities();
        moveableObjects = theLevel.getMoveable();
        moveCar = new Vehicle(10, 400, 20, new ImageIcon("car.png"), null);
    } //End GameSession

    
    /** creates player's vehicle and sets health, score, to the state at game start
     * Ie. health: 150 Score: 0 Lives:3
     */
    public void startGame() {
        this.setPlayerHealth(150);
        this.setPlayerScore(0);
        this.setPlayerLives(3);
        moveCar = new Vehicle(20, 400, 20, new ImageIcon("car.png"), null);
    }
    
    
    /** determines if the level has ended (when the progress of the level 
     * is at the length of the level)
     * @return boolean - true or false
     */
    public boolean atLevelEnd(){
        boolean atEnd = false;
        
        if (this.getProgressX() >  theLevel.getLevelLength()){
            atEnd = true;
        } //End if
        return atEnd;
    } //End atLevelEnd
    
    
    /** Gets a reference to the GameEntity representing the player's vehicle.
     * @return moveCar - player's vehicle (GameEntity)
     */
    public GameEntity getMoveCar(){
        return moveCar;
    }
        
    /** gets a reference to scrollBackground
     * @return scrollBackground - RoadBackground object
     */
    public RoadBackground getBackground1(){
        return scrollBackground;
    }
    
    
    /** gets a reference to scrollBackground2
     * @return scrollBackground2 - RoadBackground object
     */
    public RoadBackground getBackground2(){
        return scrollBackground2;
    }
    
    
    /** updates the background image all game objects upon a single game cycle
     */
    public void updateGame() {
        if (scrollBackground.getX() <= -800) {
            scrollBackground.setX(scrollBackground2.getX() + 800);
        } //End if

        if (scrollBackground2.getX() <= -800) {
            scrollBackground2.setX(scrollBackground.getX() + 800);
        } //End if
        
        // Update Progress
        this.progressX += this.gameSpeed;
        //Update Car Location
        moveCar.setX(moveCar.getX() + this.gameSpeed);
        scrollBackground.setX(scrollBackground.getX() - this.gameSpeed);
        scrollBackground2.setX(scrollBackground2.getX() -this.gameSpeed);
        
        for (Moveable movingObject : this.moveableObjects) {
            if((movingObject.getX() >= (this.progressX-100)) && (movingObject.getX() <= (this.progressX + 900))){
                movingObject.move();
            } //End if
        } //End for
    } //End updateGame
    
    /** 
     * Gets references to all the obstacles in the Level currently being 
     * used by the GameSession.
     * @return listedObstacles - list of generated obstacles
     */
    public LinkedList<GameEntity> getListedObstacles(){
        return listedObstacles;
    } //End getListedObstacles

    
    /** gets the value of playerHealth
     * @return playerHealth - value of player's health
     */
    public int getPlayerHealth() {
        return playerHealth;
    } //End getPlayerHealth

    
    /** sets the value of playerHealth
     * @param playerHealth value of player's health
     */
    public void setPlayerHealth(int playerHealth) {
        if (playerHealth <= 150){
            this.playerHealth = playerHealth;
        } else {
            this.playerHealth = 150;
        } //End if
    } //End setPlayerHealth

    
    /** gets the value of playerScore
     * @return playerScore - value of the player's score
     */
    public int getPlayerScore() {
        return playerScore;
    } //End getPlayerScore

    
    /** sets the value of playerScore
     * @param playerScore value of the player's score
     */
    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    } //End setPlayerScore

    
    /** gets the value of playerLives
     * @return playerLives - value of the player's lives
     */
    public int getPlayerLives() {
        return playerLives;
    } //End getPlayerLives

    
    /** sets the value of playerLives
     * @param playerLives value of the player's lives
     */
    public void setPlayerLives(int playerLives) {
        this.playerLives = playerLives;
    } //End setPlayerLives
    
    
    /** resets the value of moveCar (player's position)
     */
    public void resetPlayerPosition(){
        moveCar.setX(moveCar.getRadius() + this.getProgressX());
        moveCar.setY(400);
    } //End resetPlayerPostion

    
    /** gets the value of progressX
     * @return progressX - the value of the location for the x-axis
     */
    public int getProgressX() {
        return progressX;
    } //End getProgressX

            
    /** retrieves the values from theScoreBoard
     * @return theScoreboard - recorded high scores
     */    
    public HighScoreHandler getTheScoreboard() {
        return theScoreboard;
    } //End getTheScoreBoard
    
    
    /** returns a toString representation of the scoreboard object
     * @return String 
     */
    public String getTheScoreBoardToString() {
        return theScoreboard.toString();
    } //End getTheScoreBoardToString
    
    
    /** determines if the player's score is a new high score
     * @return boolean
     */
    public boolean hasNewHighScore(){ // Use this to test if the score of the current game is a high score
        return this.theScoreboard.isHighScore(this.getPlayerScore());
    } //End hasNewHighScore
    
    
    /** assigns a name to a new high score
     * @param playerName the player's name
     */
    public void assignNameToHighScore(String playerName){ // Use to assign a name to a new high score
        this.theScoreboard.insertHighScore(this.getPlayerScore(), playerName);
        this.saveScoreBoard();
    } //End assignNameToHighScore
    
    
    /** saves the values for the score board into an external file (HighScoreHandler.ser)
     */
    public void saveScoreBoard() {
        ObjectOutputStream output;
        try // Open file for output
        {
            output = new ObjectOutputStream(new FileOutputStream("HighScoreHandler.ser"));
            output.writeObject(this.theScoreboard);
            output.close();
        } catch (IOException ioException) {
            System.err.println("Error opening file.");
        } //End catch
    } //End saveScoreBoard

    
    /** loads the scoreboard / reads external file (HighScoreHandler.ser)
     */
    public void loadScoreBoard() {
        ObjectInputStream input;
        
        try// Open file for input
        {
            input = new ObjectInputStream(new FileInputStream("HighScoreHandler.ser"));
            HighScoreHandler inputElement = (HighScoreHandler) input.readObject();
            this.theScoreboard.setListedScores(inputElement.getListedScores());

        } catch (EOFException endOfFileException) {
            System.out.println("End of File reached");
        } catch (ClassNotFoundException classNotFoundException) {
            System.err.println("Unable to create object");
        } catch (IOException ioException) {
            System.err.println("Error during read from file");
            ioException.printStackTrace();
        } //End catch
    } //End loadScoreBoard
    
    
    /** 
     * Parses through the GameEntities of the GameSession and checks
     * whether the player vehicle object is in collision with any of the
     * GameEntities and if it is, apply the collisionResult of that GameEntity
     * to the game state
     */
    public void checkCollision() {
        for (GameEntity obstacle : listedObstacles) {
            if (GameEntity.overlap(moveCar, obstacle)){
                CollisionResult collided = obstacle.getColResult();
                //Set Health change
                this.setPlayerHealth(this.getPlayerHealth() + 
                        collided.getHealth());
                //Set Score change
                this.setPlayerScore(this.getPlayerScore() + collided.getScore());
                // Switch off the collision of this object
                if (!obstacle.getColResult().isPersist()) {
                    obstacle.setX(-100);
                } //End if
                if (this.getPlayerHealth() <= 0){
                    resetPlayerPosition();
                    this.setPlayerHealth(150);
                    this.setPlayerLives(this.getPlayerLives() - 1);
                } //End if
                obstacle.deactivateCollision();
            } else if (obstacle.getInactive()){
                obstacle.enableCollision();
            } //End if
        } //End for
    } //End checkCollision

    
} //End Class