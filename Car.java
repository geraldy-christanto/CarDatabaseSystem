import java.util.*;
/**
 * Menyimpan informasi keseluruhan mobil. Menghitung usia mobil hingga tanggal saat ini.
 * 
 * @version 1.0, 22 Maret 2020
 * @author Geraldy Christanto
 */
public class Car implements java.io.Serializable{
    private String model;
    private String manufacturer;
    private String information;
    private int year;
    private long price;
    private double distance;

    public Car(){
    }

    /**
     * @param man Parameter Nama manufacture
     * @param mod Parameter model mobil
     * @param info Parameter untuk informasi tambahan dari mobil
     */
    public Car(String man, String mod, String info){
        model = mod;
        manufacturer = man.toUpperCase();
        information = info;
    }

    /**
     * Menghitung usia mobil
     */
    public int getAge(){
        GregorianCalendar calendar = new GregorianCalendar();
        return(calendar.get(Calendar.YEAR) - year);
    }

    public String getInformation(){
        return information;
    }

    public double getDistance(){
        return distance;
    }

    public String getManufacturer(){
        return manufacturer;
    }

    public String getModel(){
        return model;
    }

    public long getPrice(){
        return price;
    }

    public int getYear(){
        return year;
    }

    public void setInformation(String info){
        information = info;
    }

    public void setDistance(double km){
        distance = km;
    }

    public void setManufacturer(String man){
        manufacturer = man.toUpperCase();
    }

    public void setModel(String mod){
        model = mod;
    }

    public void setPrice(long prc){
        price = prc;
    }

    public void setYear(int yr){
        year = yr;
    }
}