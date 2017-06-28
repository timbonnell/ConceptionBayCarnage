package ConceptionBayCarnage;


/** The Movable interface provides scope over functions relating to movement
* @author David Budgell, Tim Bonnell, K. Craig Webber
*/
public interface Moveable {

    /**
     * Triggers the moveable object move action which updates its x and y coordinates
     * as a GameEntity
     */
    public void move();

    /**
     * Returns the x-coordinate of the Moveable object
     * @return int - The x-coordinate of this Moveable object
     */
    public int getX();
    
    
} //End Moveable