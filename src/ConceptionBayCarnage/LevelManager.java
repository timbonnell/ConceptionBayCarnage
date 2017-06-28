package ConceptionBayCarnage;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.ImageIcon;


/** The LevelManager class is used to generate correct level objects
 * based upon their height, length and height of their margins
 * @author David Budgell, Tim Bonnell, K. Craig Webber
 */
public class LevelManager {
    private int levelLength, levelHeight, marginHeight;
    private int mooseTemplateRadius = 25, 
            treeTemplateRadius = 50, 
            coinTemplateRadius = 18,
            potholeTemplateRadius = 10,
            pylonTemplateRadius = 18, 
            wrenchTemplateRadius = 18;
    private final ImageIcon mooseImage = new ImageIcon("moose.png");
    private final ImageIcon coinImage = new ImageIcon("coin.png");
    private final ImageIcon potholeImage = new ImageIcon("pothole.png");
    private final ImageIcon treeImage = new ImageIcon("bush.png");
    private final ImageIcon pylonImage = new ImageIcon("pylon.png");
    private final ImageIcon wrenchImage = new ImageIcon("wrench.png");
    private CollisionResult mooseCol = new CollisionResult(0,-150,false);
    private CollisionResult coinCol = new CollisionResult(1000,0,false);
    private CollisionResult potholeCol = new CollisionResult(0,-50,true);
    private CollisionResult treeCol = new CollisionResult(0,-150,true);
    private CollisionResult pylonCol = new CollisionResult(0,-25,false);
    private CollisionResult wrenchCol = new CollisionResult(250,25,false);
    private int difficulty;
    private final double generationChance;
    private Random randomGen;
    private LinkedList<Moveable> moveableObjects;

    
    /** creates a new LvelManager object
     * @param levelLength the level length in pixels
     * @param levelHeight the level height in pixels
     * @param marginHeight the margin height in pixels
     * @param difficulty the difficulty level 1-3 representing easy, medium and hard
     */
    public LevelManager(int levelLength, int levelHeight, int marginHeight, int difficulty) {
        this.randomGen = new Random();
        this.levelLength = levelLength;
        this.levelHeight = levelHeight;
        this.marginHeight = marginHeight;
        this.moveableObjects = new LinkedList<Moveable>();
        if(difficulty == 2){ // normal
            this.generationChance = 0.80;
        } else if (difficulty == 1){ // easy
            coinCol.setScore(coinCol.getScore() / 2);
            wrenchCol.setScore(wrenchCol.getScore() / 2);
            this.generationChance = 0.70;
        } else { // hard
            coinCol.setScore(coinCol.getScore() * 2);
            wrenchCol.setScore(wrenchCol.getScore() * 2);
            this.generationChance = 0.95;
        }
        
    }
    
    
    /** generates a populated Level object 
     * @return theLevel - level object
     */
    public Level getLevel(){
        Level theLevel = new Level(this.levelLength, 
                this.levelHeight, this.marginHeight);
        LinkedList<GameEntity> obstacles = generateObstacles();
        for (GameEntity obstacle : obstacles) {
            theLevel.insertEntity(obstacle);
        } //End for
        for (Moveable moveable: this.moveableObjects){
            theLevel.insertMoveable(moveable);
        } //End for
        return theLevel;
    } //End getLevel
    
    
    /** generates and returns a list of obstacles as a Linkedlist of GameEntities
     * @return allObstacles - list of all obstacles for level
     */
    public LinkedList<GameEntity> generateObstacles(){
        LinkedList<GameEntity> allObstacles;
        LinkedList<GameEntity> topTreeLine = 
                buildMarginTrees((treeTemplateRadius + 10), 80);
        LinkedList<GameEntity> bottomTreeLine = 
                buildMarginTrees(this.levelHeight - (treeTemplateRadius + 10), -80);
        LinkedList<GameEntity> streetObstacles = 
                buildStreetObstacles();
        LinkedList<GameEntity> mooseObstacles = 
                generateMooseObstacles();
        allObstacles = new LinkedList<GameEntity>();
        allObstacles.addAll(topTreeLine);
        allObstacles.addAll(bottomTreeLine);
        allObstacles.addAll(streetObstacles);
        allObstacles.addAll(mooseObstacles);
        Collections.sort(allObstacles);
        return allObstacles;
    } //End generateObstacles
    
    /*
    * @param yLineCoordinate - The 
    * @return treeList 
    */
    private LinkedList<GameEntity> buildMarginTrees(int yLineCoordinate,int randomDeviation){
        LinkedList<GameEntity> treeList = new LinkedList<GameEntity>();
        for (int x = 20; x < this.levelLength; x = x + 
                (this.getTreeTemplateRadius()*2) + 10 ){
            if(randomDeviation >= 0){
                int yDeviation = this.randomGen.nextInt(randomDeviation);
                GameEntity anEntity = new GameEntity(x,yLineCoordinate + yDeviation,
                    this.treeTemplateRadius,this.treeImage,this.treeCol);
                treeList.push(anEntity);
            } //End if
            else {
                int yDeviation = this.randomGen.nextInt(-randomDeviation);
                GameEntity anEntity = new GameEntity(x,yLineCoordinate - yDeviation,
                    this.treeTemplateRadius,this.treeImage,this.treeCol);
                treeList.push(anEntity);
            } //End else
        } //End for
        return treeList;
    } //End buildMarginTrees
    
    
    private LinkedList<GameEntity> buildStreetObstacles(){
        LinkedList<GameEntity> streetObstacles = new LinkedList<GameEntity>();
        int gapSpace = 200;
        if (difficulty == 3){
            gapSpace = 100;
        }
        int streetRange = this.levelHeight- (2 * this.marginHeight);
        for (int x = 800; x < this.levelLength; x = x + gapSpace) {
            Double generationChanceComp = this.randomGen.nextDouble();
            if (generationChanceComp <= this.generationChance){ //Generate an object here
                int objectType = this.randomGen.nextInt(6);
                //System.out.println(objectType); // Remove later
                int yLoc = (this.randomGen.nextInt(streetRange) + this.marginHeight);
                switch (objectType){
                    case 0: //pothole
                    case 1: 
                        //System.out.println("Pothole");
                        streetObstacles.push(new GameEntity(x, yLoc, 
                                this.potholeTemplateRadius,this.potholeImage,this.potholeCol));
                        break;
                        //pylon
                    case 2: //pylon
                    case 3:
                        streetObstacles.push(new GameEntity(x, yLoc, this.pylonTemplateRadius,
                                this.pylonImage,this.pylonCol));
                        //System.out.println("Pylon");
                        break;
                    case 4: //coin
                        streetObstacles.push(new GameEntity(x, yLoc, this.coinTemplateRadius,
                                this.coinImage, this.coinCol));
                        //System.out.println("Coin");
                        break;
                    case 5: //wrench
                        streetObstacles.push(new GameEntity(x, yLoc, this.wrenchTemplateRadius, 
                                this.wrenchImage, this.wrenchCol));
                        //System.out.println("Wrench");
                        break;
                } //End switch
            } //End if
        } //End for
        return streetObstacles;
    } //End buildStreetObstacles
    
    
    /** generates a list of mooseObstacles within the bounds of the Level
     * 
     * @return mooseObstacles - list of moose obstacles
     */
    private LinkedList<GameEntity> generateMooseObstacles(){
        LinkedList<GameEntity> mooseObstacles = new LinkedList<GameEntity>();
        
        int spawnRange = this.levelHeight - 100;
        int gapSpace = 400;
        if (difficulty == 3){
            gapSpace = 300;
        }
        for (int x = 800; x < this.levelLength; x = x + 400) {
            Double generationChanceComp = this.randomGen.nextDouble();
            if (generationChanceComp <= this.generationChance){ //Generate an object here
                int yLoc = (this.randomGen.nextInt(spawnRange) + 50);
                int xMoveSpeed = this.randomGen.nextInt(3);
                int yMoveSpeed = this.randomGen.nextInt(3);
                if (yMoveSpeed >= 0 && xMoveSpeed == 0){
                    xMoveSpeed = 1;
                } //End if
                //System.out.println("Moose Generated with moveX:" + xMoveSpeed + " moveY:" + yMoveSpeed);
                Moose aMoose;
                if(yLoc < this.getLevelHeight() / 2 ){
                    aMoose = new Moose(x,yLoc, this.mooseTemplateRadius, 
                        this.mooseImage,this.mooseCol, -xMoveSpeed, yMoveSpeed);
                } else { // greater than halfway
                    aMoose = new Moose(x,yLoc, this.mooseTemplateRadius, 
                        this.mooseImage,this.mooseCol, -xMoveSpeed, -yMoveSpeed);
                } //End else
               
                this.moveableObjects.push(aMoose);
                mooseObstacles.push(aMoose);
            } //End if
        } //End for
        return mooseObstacles;
    } //End generateMooseObstacles

    
    /** gets the value of levelLength
     * @return levelLength - level length in pixels
     */
    public int getLevelLength() {
        return levelLength;
    } //End getLevelLength

    
    /** sets the value of levelLength
     * @param levelLength level length in pixels
     */
    public void setLevelLength(int levelLength) {
        this.levelLength = levelLength;
    } //End setLevelLength

    
    /** gets the value of levelHeight
     * @return levelHeight - level height in pixels
     */
    public int getLevelHeight() {
        return levelHeight;
    } //End getLevelHeight

    
    /** sets the value of levelWidth
     * @param levelHeight height of level in pixels
     */
    public void setLevelWidth(int levelHeight) {
        this.levelHeight = levelHeight;
    } //End setLevelWidth

    
    /** gets the value of marginHeight
     * @return marginHeight - height of margin in pixels
     */
    public int getMarginHeight() {
        return marginHeight;
    } //End getMarginHeight

    
    /** sets the value of marginHeight
     * @param marginHeight height of margin in pixels
     */
    public void setMarginHeight(int marginHeight) {
        this.marginHeight = marginHeight;
    } //End setMarginHeight

    
    /** gets value of mooseTemplateRadius
     * @return mooseTemplateRadius - radius of moose template
     */
    public int getMooseTemplateRadius() {
        return mooseTemplateRadius;
    } //End getMooseTemplateRadius

    
    /** sets the value of mooseTemplateRadius
     * @param mooseTemplateRadius radius of moose template
     */
    public void setMooseTemplateRadius(int mooseTemplateRadius) {
        this.mooseTemplateRadius = mooseTemplateRadius;
    } //End setMooseTemplateRadius

    
    /** gets the value of treeTemplateRadius
     * @return treeTemplateRadius - radius of tree template
     */
    public int getTreeTemplateRadius() {
        return treeTemplateRadius;
    } //End getTreeTemplateRadius

    
    /** sets the value of bushTemplayeRadius
     * @param bushTemplateRadius radius of bush template
     */
    public void setTreeTemplateRadius(int bushTemplateRadius) {
        this.treeTemplateRadius = bushTemplateRadius;
    } //End bushTemplateRadius

    
    /** gets the value of coinTemplateRadius
     * @return coinTemplateRadius - radius of coin template
     */
    public int getCoinTemplateRadius() {
        return coinTemplateRadius;
    } //End getCoinTemplateRadius

    
    /** sets the value of coinTemplateRadius
     * @param coinTemplateRadius radius of coin template
     */
    public void setCoinTemplateRadius(int coinTemplateRadius) {
        this.coinTemplateRadius = coinTemplateRadius;
    } //End setCoinRadiusTemplate

    
    /** gets the value of potholeTemplateRadius
     * @return potholeTemplateRadius - radius of pothole template
     */
    public int getPotholeTemplateRadius() {
        return potholeTemplateRadius;
    } //End getPotholeTemplateRadius

    
    /** sets the value of potholeTemplateRadius
     * @param potholeTemplateRadius radius of pothole template
     */
    public void setPotholeTemplateRadius(int potholeTemplateRadius) {
        this.potholeTemplateRadius = potholeTemplateRadius;
    } //End setPotholeTemplateRadius

    
    /** gets the value of pylonTemplateRadius
     * @return pylonTemplateRadius - radius of pylon template
     */
    public int getPylonTemplateRadius() {
        return pylonTemplateRadius;
    } //End getPylonTemplateRadius

    
    /** sets the value of pylonTemplateRadius
     * @param pylonTemplateRadius radius of pylon template
     */
    public void setPylonTemplateRadius(int pylonTemplateRadius) {
        this.pylonTemplateRadius = pylonTemplateRadius;
    } //End setPylonTemplateRadius

    
    /** gets the value of wrenchTemplateRadius
     * @return wrenchTemplateRadius - radius of wrench template
     */
    public int getWrenchTemplateRadius() {
        return wrenchTemplateRadius;
    } //End getWrenchTemplateRadius

    
    /** sets the value of wrenchTemplateRadius
     * @param wrenchTemplateRadius radius of wrench template
     */
    public void setWrenchTemplateRadius(int wrenchTemplateRadius) {
        this.wrenchTemplateRadius = wrenchTemplateRadius;
    } //End setWrenchTemplateRadius
    
    
} //End Class