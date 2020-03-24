import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Menu untuk menampilkan semua informasi mobil
 * @version 1.0, 22 Maret 2020
 * @author Geraldy Christanto
 */
public class ShowAllCarsMenu extends JPanel implements ActionListener, ChangeListener{
    private CarDatabaseSystem carSystem;
    private Car[] carList;
    private int currentIndex = 0;
    private JLabel headingLabel = new JLabel("Tampilkan semua model mobil");
    private JButton previousButton = new JButton("Sebelumnya");
    private JButton nextButton = new JButton("Lanjut");
    private JPanel buttonPanel = new JPanel();
    private CarDetailsComponents carComponents = new CarDetailsComponents();
    private boolean carsUpdated = false;

    /**
     * @param carSys Objek CarDatabaseSystem
     */
    public ShowAllCarsMenu(CarDatabaseSystem carSys){
        carSystem = carSys;
        carList = carSystem.getAllCars();

        if (carList.length > 0){
            carComponents.displayDetails(carList[0]);
        }

        carSys.addCarUpdateListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        previousButton.addActionListener(this);
        nextButton.addActionListener(this);
        headingLabel.setAlignmentX(0.5f);

        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);

        add(Box.createVerticalStrut(10));
        add(headingLabel);
        add(Box.createVerticalStrut(15));
        carComponents.add(buttonPanel, "Center");
        add(carComponents);

        carList = carSystem.getAllCars();
    }

    /**
     * Menunggu event button ketika di klik
     *
     * @param ev Objek ActionEvent
     */
    public void actionPerformed(ActionEvent ev){
        if (ev.getSource() == previousButton){
            previousButtonClicked();
        }
        else if (ev.getSource() == nextButton){
            nextButtonClicked();
        }
    }

    /**
     * Method untuk menotice ketika Car Collection di update
     *
     * @param ev Objek CarUpdateEvent
     */
    public void carsUpdated(CarUpdateEvent ev){
        if (ev.getSource() == carSystem){
            carsUpdated = true;
        }
    }

    /**
     * Update data car collection ketika berpindah menu
     *
     * @param ev Objek ChangeEvent
     */
    public void stateChanged(ChangeEvent ev){
        if (ev.getSource() instanceof JTabbedPane){
            JTabbedPane tab = (JTabbedPane)ev.getSource();
            if (tab.getSelectedIndex() == 2){
                if (carsUpdated){
                    carList = carSystem.getAllCars();
                    if (!(carList == null)){
                        carComponents.displayDetails(carList[currentIndex]);
                    }
                    carsUpdated = false;
                }
            }
        }
    }

    /**
     * Tampilkan item selanjutnya berdasarkan index, jika ada
     */
    private void nextButtonClicked(){
        if (currentIndex < carList.length - 1){
            currentIndex++;
            carComponents.displayDetails(carList[currentIndex]);
        }
        else{
            JOptionPane.showMessageDialog(carSystem, "Anda tidak dapat lanjut ke halaman berikutnya", "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Tampilkan item sebelumnya berdasarkan index, jika ada
     * 
     */
    private void previousButtonClicked(){
        if (currentIndex > 0){
            currentIndex--;
            carComponents.displayDetails(carList[currentIndex]);
        }
        else{
            JOptionPane.showMessageDialog(carSystem, "Anda tidak dapat melihat halaman sebelumnya", "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }
}