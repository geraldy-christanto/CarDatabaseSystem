import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Menu untuk menambahkan mobil
 * 
 * @version 1.0, 23 Maret 2020
 * @author Geraldy Christanto
 */
public class AddCarMenu extends JPanel implements ActionListener{
    private CarDatabaseSystem carSystem;
    private JLabel headingLabel = new JLabel("Tambah mobil");
    private JButton resetButton = new JButton("Reset");
    private JButton saveButton = new JButton("Simpan");
    private JPanel buttonPanel = new JPanel();
    private CarDetailsComponents carComponents = new CarDetailsComponents();

    /**
     * Menu yang ditampilkan dari class AddCarMenu
     * 
     * @param carSys Parameter objek CarDatabaseSystem
     */
    public AddCarMenu(CarDatabaseSystem carSys){
        carSystem = carSys;
        resetButton.addActionListener(this);
        saveButton.addActionListener(this);
        headingLabel.setAlignmentX(0.5f);

        buttonPanel.add(resetButton);
        buttonPanel.add(saveButton);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(10));
        add(headingLabel);
        add(Box.createVerticalStrut(15));
        carComponents.add(buttonPanel, "Center");
        add(carComponents);
    }

    /**
     * Mengecek action yang terjadi ketika button ditekan
     *
     * @param ev Object ActionEvent
     */
    public void actionPerformed(ActionEvent ev){
        if (ev.getSource() == resetButton){
            resetButtonClicked();
        }
        else if (ev.getSource() == saveButton){
            saveButtonClicked();
        }
    }

    private void resetButtonClicked(){
        carComponents.clearTextFields();
    }

    private void saveButtonClicked(){
        String manufacturer = "";
        String model = "";
        String info = "";
        double distance = 0;
        long price = 0;
        int year = 0;
        boolean valid = false;
        try{
            manufacturer = carComponents.getManufacturerText().trim();
            model = carComponents.getModelText().trim();
            info = carComponents.getInfoText().trim();
            distance = Double.parseDouble(carComponents.getDistanceText().trim());
            price = Long.parseLong(carComponents.getPriceText().trim());
            year = Integer.parseInt(carComponents.getYearText().trim());

            if (validateString(manufacturer)){
                if (year >= 1000 && year <= 9999){
                    if (validateString(model)){
                        if (validateDistance(carComponents.getDistanceText().trim())){
                            valid = true;
                        }
                        else{
                            JOptionPane.showMessageDialog(carSystem, "Terjadi kesalahan pengisian data \"Jarak tempuh (Km)\".\nJarak tempuh harus diisi dengan angka desimal.", "Invalid field", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(carSystem, "Terjadi kesalahan pengisian data \"Model\".\nModel harus terdiri dari minimal tiga character.", "Invalid field", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(carSystem, "Terjadi kesalahan pengisian data \"Tahun\".\nData tahun harus diisi dengan format, YYYY. contoh, 2020.", "Invalid field", JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(carSystem, "Terjadi kesalahan pengisian\"Manufaktur\".\nManufaktur harus terdiri dari minimal tiga character.", "Invalid field", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (NumberFormatException exp){
            JOptionPane.showMessageDialog(carSystem, "Terjadi sebuah kesalahan. Pastikan pengisian data memenuhi persyaratan:\n" +
            "\"Tahun\" hanya terdiri dari empat digit dengan format YYYY\n\"Harga(Rp)\" terdiri dari integer tanpa desimal\n\"Jarak tempuh(Km) \" terdiri dari angka desimal dengan maksimum satu desimal", "Invalid field", JOptionPane.ERROR_MESSAGE);
        }

        if (valid){
            //Membuat object Mobil setelah validasi
            Car myCar = new Car(manufacturer, model, info);
            myCar.setDistance(distance);
            myCar.setPrice(price);
            myCar.setYear(year);

            int result = carSystem.addNewCar(myCar);

            if (result == CarsCollection.NO_ERROR){
                carSystem.setCarsUpdated();
                JOptionPane.showMessageDialog(carSystem, "Data telah ditambahkan.", "Konfirmasi", JOptionPane.INFORMATION_MESSAGE);
                resetButtonClicked();
                carComponents.setFocusManufacturerTextField();
            }
            else if (result == CarsCollection.CARS_MAXIMUM_REACHED){
                JOptionPane.showMessageDialog(carSystem, "Jumlah maksimum mobil dalam satu manufaktur telah mencapai batas.\nAnda tidak dapat menambahkan mobil lagi dari manufaktur tersebut", "Problem adding car", JOptionPane.WARNING_MESSAGE);
            }
            else if (result == CarsCollection.MANUFACTURERS_MAXIMUM_REACHED){
                JOptionPane.showMessageDialog(carSystem, "Jumlah manufaktur dalam sistem database telah mencapai batas.\nAnda tidak dapat menambahkan manufaktur ke dalam sistem database mobil kembali", "Problem adding manufacture", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Mengecek argumen jika input yang dimasukkan kurang dari 3 character maka tidak valid
     *
     * @param arg String untuk divalidasi
     * @return true Jika falid
     */
    private boolean validateString(String arg){
        boolean valid = false;
        String[] splitted = arg.split(" ");

        for (int i = 0; i < splitted.length; i++){
            valid = (splitted[i].length() > 2);
            if (valid){
                break;
            }
        }
        return valid;
    }

    /**
     * Mengecek argumen distance. Jika berbentuk decimal
     *
     * @param distance Nilai yang akan dijadikan tipe double dari String
     * @return true jika valid
     */
    private boolean validateDistance(String distance){
        boolean valid = false;
        String rem;
        StringTokenizer tokens = new StringTokenizer(distance, ".");

        tokens.nextToken();
        //Jika terdapat decimal
        if (tokens.hasMoreTokens()){
            rem = tokens.nextToken();
            if (rem.length() == 1){
                valid = true;
            }
            else{
                if ((Integer.parseInt(rem)) % (Math.pow(10, rem.length() - 1)) == 0){
                    valid = true;
                }
                else{
                    valid=false;
                }
            }
        }
        else{ 
            valid = true;
        }
        return valid;
    }
}