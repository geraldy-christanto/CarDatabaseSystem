/**
 * Class untuk memperbarui terjadinya event
 * 
 * @version 1.0, 23 Maret 2020
 * @author Geraldy Christanto
 */
public class CarUpdateEvent extends java.util.EventObject{
    /**
     * @param source Sebagai source even dari EventObject
     */
    public CarUpdateEvent(Object source){
        super(source);
    }
}
