import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
/**
 * Welcome dialog yang ditampilkan pada menu utama
 * @version 1.0, 23 Maret 2020
 * @author Geraldy Christanto
 */
public class WelcomePanel extends JPanel implements ChangeListener{
    private CarDatabaseSystem carSystem;
    private JLabel headingLabel = new JLabel("Selamat datang!\n Sistem Database Mobil", JLabel.CENTER);
    private JLabel carsLabel = new JLabel();
    private JLabel manufacturersLabel = new JLabel();
    private JLabel avgPriceLabel = new JLabel();
    private JLabel avgDistanceLabel = new JLabel();
    private JLabel avgAgeLabel = new JLabel();
    private JLabel versionLabel = new JLabel();
    private JLabel dataSizeLabel = new JLabel();
    private JPanel statsPanel = new JPanel();
    private JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
    private boolean carsUpdated = false;
    private String file;

    /**
     * Menu utama
     * @param carSys Objek sistem Database mobil
     * @param data Nama file
     */
    public WelcomePanel(CarDatabaseSystem carSys, String data){
        carSystem = carSys;
        file = data;
        setLayout(new BorderLayout(0, 10));
        carSys.addCarUpdateListener(this);

        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        centerPanel.add(statsPanel);
        headingLabel.setBorder(new EmptyBorder(new Insets(10, 0, 0, 0)));

        updateStats();

        statsPanel.add(carsLabel);
        statsPanel.add(manufacturersLabel);
        statsPanel.add(avgPriceLabel);
        statsPanel.add(avgDistanceLabel);
        statsPanel.add(avgAgeLabel);
        statsPanel.add(Box.createVerticalStrut(20));
        statsPanel.add(versionLabel);
        statsPanel.add(dataSizeLabel);

        add(headingLabel, "North");
        add(centerPanel, "Center");
    }

    /**
     * Metode ketika Mobil berhasil ditambahkan ke dalam sistem
     *
     * @param ev Objek untuk memperbarui data Car
     */
    public void carsUpdated(CarUpdateEvent ev){
        if (ev.getSource() == carSystem){
            carsUpdated = true;
        }
    }

    /**
     * Ketika berpindah menu memastikan file CarCollection diperbarui
     *
     * @param ev Objek ChangeEvent
     */
    public void stateChanged(ChangeEvent ev){
        if (ev.getSource() instanceof JTabbedPane){
            JTabbedPane tab = (JTabbedPane)ev.getSource();
            if (tab.getSelectedIndex() == 0){
                if (carsUpdated){
                    updateStats();
                    carsUpdated = false;
                }
            }
        }
    }

    /**
     * Perbarui statistik yang ada di menu utama
     */
    private void updateStats(){
        int cars = (int)carSystem.getStatistics(CarDatabaseSystem.CARS_COUNT);
        int manufacturers = (int)carSystem.getStatistics(CarDatabaseSystem.MANUFACTURERS_COUNT);
        double avgPrice = Math.floor(carSystem.getStatistics(CarDatabaseSystem.AVERAGE_PRICE) * 10 + 0.5) / 10;
        double avgDistance = Math.floor(carSystem.getStatistics(CarDatabaseSystem.AVERAGE_DISTANCE) * 10 + 0.5) / 10;
        double avgAge = Math.floor(carSystem.getStatistics(CarDatabaseSystem.AVERAGE_AGE) * 10 + 0.5) / 10;
        java.io.File f = new java.io.File(file);
        long size = f.length();

        carsLabel.setText("Jumlah mobil: " + String.valueOf(cars));
        manufacturersLabel.setText("Jumlah manufaktur: " + String.valueOf(manufacturers));
        avgPriceLabel.setText("Rata-rata harga mobil: Rp." + String.valueOf(avgPrice));
        avgDistanceLabel.setText("Rata-rata jarak tempuh: " + String.valueOf(avgDistance) + " KM") ;
        avgAgeLabel.setText("Rata-rata usia mobil: " + String.valueOf(avgAge) + " Tahun");
        versionLabel.setText("Sistem Database Mobil, v" + CarDatabaseSystem.APP_VERSION);
        dataSizeLabel.setText("Ukuran file data: " + size + " bytes");
    }
}