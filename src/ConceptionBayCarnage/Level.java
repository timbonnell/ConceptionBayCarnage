package ConceptionBayCarnage;
import java.util.LinkedList;

/** The Level class is used to represent a Level object
 * containing a list of all gameEntities populating that level. Along with a
 * linked collection to represent all objects that exist in the level that can
 * be seen as moveable.
 * @author David Budgell, Tim Bonnell, K. Craig Webber
 */
public class Level {
    private LinkedList<GameEntity> gameEntities;
    private LinkedList<Moveable> moveableObjects;
    private int levelLength, levelHeight, marginHeight;

    /** creates a new Level object based upon its length, its total height, and 
     * the height of its margins
     * @param levelLength the level length in pixels
     * @param levelHeight the level height in pixels
     * @param marginHeight the margin height in pixels
     */
    public Level(int levelLength, int levelHeight, int marginHeight) {
        this.levelLength = levelLength;
        this.levelHeight = levelHeight;
        this.marginHeight = marginHeight;
        this.gameEntities = new LinkedList<GameEntity>();
        this.moveableObjects = new LinkedList<Moveable>();
    } //End Level
    
    
    /** inserts a new GameEntity into the LinkedList of GameEntity objects in this
     * Level
     * @param parEntity a GameEntity object
     */
    public void insertEntity(GameEntity parEntity){
        gameEntities.push(parEntity);
    } //End insertEntity
    
    
    /** gets the list of all GameEntities within this level
     * @return LinkedList - representing all GameEntities within this Level
     */
    public LinkedList<GameEntity> getAllEntities(){
        return this.gameEntities;
    } //End getAllEntities
    
    
    /** inserts a new Moveable object into the LinkedList of Moveable objects in this
     * Level
     * @param movingObs Moveable object
     */
    public void insertMoveable(Moveable movingObs){
        moveableObjects.push(movingObs);
    } //End insertMovable
    
    
    /** gets the LinkedList of all Moveable objects within this level
     * @return LinkedList - LinkedList representing all Moveable objects in this Level
     */
    public LinkedList<Moveable> getMoveable(){
        return this.moveableObjects;
    } //End getMovable
    

    /** Returns the length of the level in pixels
     * @return levelLength - the length of the level in pixels
     */
    public int getLevelLength() {
        return levelLength;
    } //End getLevelLength

    
    /** sets the value of levelLength
     * @param levelLength the length of the level in pixels
     */
    public void setLevelLength(int levelLength) {
        this.levelLength = levelLength;
    } //End setLevelLength

    
    /** gets the value of levelHeight
     * @return levelHeight - the value of the levels height in pixels
     */
    public int getLevelHeight() {
        return levelHeight;
    } //End getLevelHeight

    
    /** sets the value of levelHeight
     * @param levelHeight the value of the levels height in pixels
     */
    public void setLevelHeight(int levelHeight) {
        this.levelHeight = levelHeight;
    } //End setLevelHeight

    
    /** gets the value of marginHeight
     * @return marginHeight - the value of the margin height in pixels
     */
    public int getMarginHeight() {
        return marginHeight;
    } //End getMarginHeight

    
    /** sets the value of marginHeight
     * @param marginHeight the value of the margin height in pixels
     */
    public void setMarginHeight(int marginHeight) {
        this.marginHeight = marginHeight;
    } //End setMarginHeight
    
    
} //End Class