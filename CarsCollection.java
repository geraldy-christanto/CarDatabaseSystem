import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
/**
 * Menyimpan objek manufacturer dan menampilkan pencarian
 *
 * @version 1.0, 23 Maret 2020
 * @author Geraldy Christanto
 */
public class CarsCollection{
    public static final int NO_ERROR = 0;
    public static final int CARS_MAXIMUM_REACHED = 1;
    public static final int MANUFACTURERS_MAXIMUM_REACHED = 2;
    private final int maxManufacturers = 20;
    private final int maxCars = 20;
    private Manufacturer[] manufacturer = new Manufacturer[0];

    public CarsCollection(){
    }
    
    /**
     * @param man Objek manufacturer untuk ditambahkan ke dalam daftar koleksi
     * 
     */
    public CarsCollection(Manufacturer man){
        addManufacturer(man);
    }

    /**
     * Method untuk menambahkan mobil ke dalam CarCollection berdasarkan manufaktur
     * jika tidak ada manufaktur buat manufaktur baru
     *
     * @param c car Untuk ditambahkan ke dalam koleksi
     * @return Mengembalikan Salah satu nilai NO_ERROR, CARS_MAXIMUM_REACHED, atau MANUFACTURERS_MAXIMUM_REACHED
     */
    public int addCar(Car c){
        Manufacturer man;
        String name = c.getManufacturer();
        int index = -1;
        int result = NO_ERROR;

        for (int i = 0; i < manufacturer.length; i++){
            if (manufacturer[i].getManufacturerName().equalsIgnoreCase(name))
                index = i;
        }
        
        if (index == -1){
            if (manufacturer.length < maxManufacturers){
                man = new Manufacturer(name, c);
                addManufacturer(man);
            }
            else{
                result = MANUFACTURERS_MAXIMUM_REACHED;
            }
        }
        else{
            if (manufacturer[index].carCount() < maxCars){
                manufacturer[index].addCar(c);
            }
            else{
                result = CARS_MAXIMUM_REACHED;
            }
        }
        return result;
    }

    /**
     * Tambahkan objek manufacturer
     *
     * @param man Objek manufacturer yang akan ditambahkan
     */
    private void addManufacturer(Manufacturer man){
        manufacturer = resizeArray(manufacturer, 1);
        manufacturer[manufacturer.length - 1] = man;
    }

    /**
     * Mendapatkan jumlah mobil dalam satu manufaktur
     *
     * @return count Mengembalikan integer jumlah mobil dalam satu manufaktur
     */
    public int carsCount(){
        int count = 0;
        for (int i = 0; i < manufacturer.length; i++){
            count += manufacturer[i].carCount();
        }
        return count;
    }

    /**
     * Mengembalikan jumlah manufacturer yang telah dibuat
     *
     * @return jumlah manufacture
     */
    public int manufacturerCount(){
        return manufacturer.length;
    }

    /**
     * Mengembalikan keseluruh daftar CarCollction dari semua manufacturer
     *
     * @return semua koleksi mobil dari semua manufacturer
     */
    public Car[] getAllCars(){
        Vector result = new Vector();
        Car[] car;
        for (int i = 0; i < manufacturer.length; i++){
            car = manufacturer[i].getAllCars();
            for (int j = 0; j < car.length; j++){
                result.addElement(car[j]);
            }
        }
        return CarDatabaseSystem.vectorToCar(result);
    }

    /**
     * Method untuk mendapatkan semua manufacturer yang terdaftar
     *
     * @return manufacturer SEmua manufacturer yang tersimpan
     */
    public Manufacturer[] getAllManufacturers(){
        return manufacturer;
    }

    /**
     * Menghitung rata-rata usia mobil dalam daftar car collection
     *
     * @return Nilai rata-rata usia mobil
     */
    public double getAverageAge(){
        Car[] car;
        double result = 0;
        int count = 0;

        for (int i = 0; i < manufacturer.length; i++){
            car = manufacturer[i].getAllCars();
            for (int j = 0; j < car.length; j++){
                result += car[j].getAge();
                count++;
            }
        }
        
        if (count == 0){
            return 0;
        }
        else{
            return (result / count);
        }
    }

    /**
     * Menghitung rata-rata jarak tempuh mobil dalam kilometer
     *
     * @return result Nilai yang mengindikasikan rata-rata jarak tempul mobil dalam kilometer
     */
    public double getAverageDistance(){
        Car[] car;
        double result = 0;
        int count = 0;

        for (int i = 0; i < manufacturer.length; i++){
            car = manufacturer[i].getAllCars();
            for (int j = 0; j < car.length; j++){
                result += car[j].getDistance();
                count++;
            }
        }
        if (count == 0){
            return 0;
        }
        else{
            return (result / count);
        }
    }

    /**
     * Menghitung rata-rata harga mobil dalam daftar car collection
     *
     * @return Nilai yang mengindikasikan rata-rata harga dari koleksi mobil
     */
    public double getAveragePrice(){
        Car[] car;
        double result = 0;
        int count = 0;

        for (int i = 0; i < manufacturer.length; i++){
            car = manufacturer[i].getAllCars();
            for (int j = 0; j < car.length; j++){
                result += car[j].getPrice();
                count++;
            }
        }
        if (count == 0){
            return 0;
        }
        else{
            return (result / count);
        }
    }

    /**
     * Mengambil daftar car collection dari file 
     *
     * @param file Nama file binary untuk menyimpan mobil
     */
    public void loadCars(String file) throws IOException, ClassNotFoundException{
        ObjectInputStream inp = new ObjectInputStream(new FileInputStream(file));
        manufacturer = (Manufacturer[])inp.readObject();
        inp.close();
    }

    /**
     * Method untuk mengatur ukuran array kembali setelah terjadi perubahan data
     *
     * @param inArray Array yang akan diatur kembali
     * @param extendBy Mengindikasikan julmah elemen yang harus ditambhakan
     * @return hasil resize array manufacturer
     */
    private Manufacturer[] resizeArray(Manufacturer[] inArray, int extendBy){
        Manufacturer[] result = new Manufacturer[inArray.length + extendBy];
        for (int i = 0; i < inArray.length; i++){
            result[i] = inArray[i];
        }
        return result;
    }

    /**
     * Menyimpan car collection ke dalam file binary
     *
     * @param file Nama file binary
     */
    public void saveCars(String file) throws IOException{
        int flag = 0;
        int items = manufacturer.length;
        Manufacturer temp;

        if(manufacturer.length > 0){
            do{
                flag = 0;
                for (int i = 0; i < items; i++){
                    if (i + 1 < items){
                        if (manufacturer[i].getManufacturerName().compareTo(manufacturer[i + 1].getManufacturerName()) > 0){
                            temp = manufacturer[i];
                            manufacturer[i] = manufacturer[i + 1];
                            manufacturer[i + 1] = temp;
                            flag++;
                        }
                    }
                }
            }
            while (flag > 0);

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(manufacturer);
            out.close();
        }
    }

    /**
     * Cari mobil berdasarkan harga dan jarak tempuh
     *
     * @param minPrice Harga terendah dari koleksi mobil
     * @param maxPrice Harga tertinggi dari koleksi mobil 
     * @param minDistance Jarak tempuh terendah dari koleksi mobil 
     * @param maxDistance Jarak tempuh tertinggi dari koleksi mobil
     * @return array dari objek mobil yang memenuhi kriteria
     */
    public Car[] search(long minPrice, long maxPrice, double minDistance, double maxDistance){
        Vector result = new Vector();
        long price;
        double distance;
        Car[] car;
        car = getAllCars();

        for (int i = 0; i < car.length; i++){
            price = car[i].getPrice();
            distance = car[i].getDistance();

            if (price >= minPrice && price <= maxPrice){
                if (distance >= minDistance && distance <= maxDistance){
                    result.add(car[i]);
                }
            }
        }
        return CarDatabaseSystem.vectorToCar(result);
    }

    /**
     * Method untuk mencari mobil berdasarkan harga
     *
     * @param minPrice Harga terendah dari mobil
     * @param maxPrice Harga tertinggi dari mobil
     * @return array dari objek mobil yang memenuhi kriteria
     */
    public Car[] search(long minPrice, long maxPrice){
        Car[] car;
        car = getAllCars();
        Vector result = new Vector();

        if (maxPrice == -1){
            for (int i = 0; i < car.length; i++){
                if (car[i].getAge() >= minPrice){
                    result.addElement(car[i]);
                }
            }
        }
        else{
            for (int i = 0; i < car.length; i++){
                if (car[i].getAge() >= minPrice && car[i].getAge() <= maxPrice){
                    result.addElement(car[i]);
                }
            }
        }

        return CarDatabaseSystem.vectorToCar(result);
    }
}