import java.util.*;
/**
 * Menyimpan dan mengambil objek manufacturer mobil
 *
 * @version 1.0, 23 Maret 2020
 * @author Geraldy Christanto
 */
public class Manufacturer implements java.io.Serializable{
    private String manufacturer;
    private Car[] car = new Car[0];

    /**
     * @param name Nama dari manufacturer
     * @param car Objek car yang ditambahkan manufacturer
     */
    public Manufacturer(String name, Car c){
        manufacturer = name.toUpperCase();
        addCar(c);
    }

    /**
     * Tambahkan mobil baru dalam manufacturer
     *
     * @param car Mobil/car yang ditambahkan dalam manufacturer
     */
    public void addCar(Car c){
        car = resizeArray(car, 1);
        car[car.length - 1] = c;
    }

    /**
     * Menghitung mobil berdasarkan manufacturer
     * @return Jumlah mobil berdasarkan manufacturer
     */
    public int carCount(){
        return car.length;
    }

    /**
     * Mendapatkan semua mobil dalam koleksi mobil berdasarkan manufacturer
     * @return Array dari mobil berdasarkan manufacturer
     */
    public Car[] getAllCars(){
        return car;
    }

    /**
     * Method untuk mendapatkan nama manufacturer
     * @return manufacturer Nama manufacturer
     */
    public String getManufacturerName(){
        return manufacturer;
    }

    /**
     * Mengatur ulang ukuran array dari Car
     *
     * @param c array Car yang akan diresize
     * @param extendBy Jumlah ukuran baru yang akan di extend
     * @return result Hasil array yang telah di resize
     */
    private Car[] resizeArray(Car[] c, int extendBy){
        Car[] result = new Car[c.length + extendBy];

        for (int i = 0; i < c.length; i++){
            result[i] = c[i];
        }
        return result;
    }

    /**
     * Method untuk mengatur nama dari manufaktur 
     * 
     */
    public void setManufacturersName(String name){
        manufacturer = name.toUpperCase();
    }
}