package ConceptionBayCarnage;
import javax.swing.ImageIcon;


/** The GameEntity class is used as the basic structure for all obstacles that 
 * can exist in a game Level as well as the player vehicle
* @author David Budgell, Tim Bonnell, K. Craig Webber
*/
public class GameEntity implements Comparable<GameEntity>{
    private int x, y, radius;
    private boolean inactive;
    private final CollisionResult noCollide = new CollisionResult(0, 0, true);
    private ImageIcon imageLocation;
    private CollisionResult colResult;
    
    
    /**Creates a new GameEntity object
     * @param x coordinate for the x-axis
     * @param y coordinate for the y-axis
     * @param radius the value of the radius
     * @param picLocation ImageIcon of the required image
     * @param colResult the type of the colResult 
     */
    public GameEntity(int x, int y, int radius, ImageIcon picLocation, CollisionResult colResult){
        this.x = x;
        this.y = y;
        this.inactive = false;
        this.radius = radius;
        this.imageLocation = picLocation;
        this.setColResult(colResult);
    } //End GameEntity
    
    /** Compares x-position of this GameEntity to the x-position of another GameEntity and returns a -1, 0, or 1
     * @param y coordinate for the y-axis
     * @return -1 if the current Game Entity is greater than, 0 if it is equal, and 1 if it less than that 
     * which is being compared to
     */
    @Override
    public int compareTo(GameEntity y){
        int otherPosition = y.getX();
        if (this.getX() > otherPosition){
            return -1;
        } else if (this.getX() == otherPosition){
            return 0;
        } else {
            return 1;
        } //End if
    } //End compareTo
    
    
    /** gets the value of x
     * @return x - coordinate of x-axis
     */
    public int getX() {
        return x;
    } //End getX

    
    /** sets the value of x
     * @param x coordinate of x-axis
     */
    public void setX(int x) {
        this.x = x;
    } //End setX

    
    /** gets the value of y
     * @return y - coordinate of y-axis
     */
    public int getY() {
        return y;
    } //End getY

    
    /** sets the value of y
     * @param y coordinate of y-axis
     */
    public void setY(int y) {
        this.y = y;
    } //End setY

    
    /** gets the value of radius
     * @return radius - the value of the radius
     */
    public int getRadius() {
        return radius;
    } //End getRadius

    
    /** sets the value of the radius
     * @param radius the value of the radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
    } //End setRadius
    
    
    /** gets the location of the required image
     * @return imageLocation - the location of the required image
     */
    public ImageIcon getImageLocation() {
        return imageLocation;
    } //End getPictureLocation

    
    /** sets the value of the required image
     * @param imageLocation the location of the required image
     */
    public void setImageLocation(ImageIcon imageLocation) {
        this.imageLocation = imageLocation;
    } //End setPictureLocations

    
    /** gets a CollisionResult representing what happens when colliding with 
     * this object or a generic 'blank' collision object if this GameEntity has 
     * been temporarily set to be inactive
     * @return colResult - the CollisionResult of this object, or the blank collisionEvent
     */
    public CollisionResult getColResult() {
        if (!this.inactive){
            return colResult;
        } else {
            return this.noCollide;
        }
    } //End CollisionResult

    
    /** sets
     * @param colResult the type of colResult
     */
    public void setColResult(CollisionResult colResult) {
        if (colResult != null){
            this.colResult = new CollisionResult(colResult.getScore(), colResult.getHealth(),colResult.isPersist());
        } //End if
    } //End setColResult
    
    
    /** Returns whether this object is inactive for collisions or not
     * @return boolean - Representing whether the GameEntity is inactive (true) or not (false) for collisions
     */
    public boolean getInactive(){
        return this.inactive;
    } //End getInactive
    
    
    /** Sets the flag representing whether this GameEntity is to be seen as 
     * inactive for collisions to true
     */
    public void deactivateCollision(){
        this.inactive = true;
    } //End deactivateCollison
    
    
    /** Sets the flag representing whether this GameEntity is to be seen as 
     * inactive for collisions to false
     */
    public void enableCollision(){
        this.inactive = false;
    } //End enableCollision
    
    
    /** determines if the players vehicle object has collided with an obstacle object
     * @param vehicle the players vehicle object
     * @param obstacle a generated obstacle such as a moose or pothole
     * @return boolean - true or false
     */
    public static boolean overlap(GameEntity vehicle, GameEntity obstacle){
        double d;
        int xDiff = vehicle.getX() - obstacle.getX();
        int yDiff = vehicle.getY() - obstacle.getY();
        d = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
        if (d <= vehicle.getRadius() + obstacle.getRadius()){
            return true;
        } //End if
        else {
            return false;
        } //End else
    } //End overlap
      
    
} //End Class