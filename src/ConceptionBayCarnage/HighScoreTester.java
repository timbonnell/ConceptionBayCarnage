package ConceptionBayCarnage;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * HighScoreTester is used to test the fundamental functions of the HighScore object
 * and to generate the default serialized score object
 * 
 * @author David Budgell
 */
public class HighScoreTester {

    /**
     * Creates a HighScoreHandler and uses it in a number of generic tests. 
     * 1.Creating a generic HighScoreHandler with autoGeneration active.
     * 2.Using the toString of this HighScoreHandler
     * 3. Testing whether it can detect whether a value is a high score.
     * 3. a. Testing 'too low' - 1000
     * 3. b. Testing 'high' 20000
     * 4. Prints the serialized object to an output stream
     * 5. Reads that serialized object back in and print its toString method to ensure
     * that it retrieves the same object
     * 
     * @param args - String array args 
     */
    public static void main(String[] args){
        HighScoreHandler testHandler;
        System.out.println("Generating default highscore file");
        testHandler = new HighScoreHandler(true); // Runs the autogenerating constructor
        System.out.println(testHandler);
        System.out.println(testHandler.isHighScore(1000)); // Should return false
        System.out.println(testHandler.isHighScore(20000)); // Should return true;
        System.out.println("Tester - Attempting to write HighScore object");
        ObjectOutputStream output;
        try // Open file for output
        {
             output = new ObjectOutputStream(new FileOutputStream("HighScoreHandler.ser"));
             output.writeObject(testHandler);
             output.flush();
             output.close();
        }
        catch (IOException ioException){
            System.err.println("Error opening file.");
        }        
        
        System.out.println("Tester - Attempting to read in HighScore object from file");
        ObjectInputStream input;
        try// Open file for input
        {
            input = new ObjectInputStream(new FileInputStream("HighScoreHandler.ser"));
            HighScoreHandler inputElement = (HighScoreHandler)input.readObject();
            System.out.println("Tester - This object was retrieved:");
            System.out.println(inputElement);
        }
        catch (EOFException endOfFileException){
            System.out.println("End of File reached");
        }
        catch (ClassNotFoundException classNotFoundException){
            System.err.println("Unable to create object");
        }
        catch (IOException ioException){
            System.err.println("Error during read from file");
        }
    }
}
