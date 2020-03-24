import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Menu untuk mencari mobil berdasarkan harga
 *
 * @version 1.0, 23 Maret 2020
 * @author Geraldy Christanto
 */
public class SearchByOtherMenu extends JPanel implements ActionListener{
    private final String[] price = {
        "0 - 1000000000", 
        "1000000001 - 3000000000", 
        "3000000001 - 6000000000", 
        "6000000001+"
    };
    private Car[] carList;
    private CarDatabaseSystem carSystem;
    private int currentIndex = 0;
    private JLabel headingLabel = new JLabel("Cari berdasarkan harga");
    private JLabel priceLabel = new JLabel("Harga (Rp)");
    private JButton searchButton = new JButton("Cari");
    private JButton resetButton = new JButton("Reset");
    private JButton previousButton = new JButton("Sebelumnya");
    private JButton nextButton = new JButton("Lanjut");
    private JComboBox priceCombo = new JComboBox(price);
    private JPanel topPanel = new JPanel();
    private JPanel pricePanel = new JPanel();
    private JPanel priceDistancePanel = new JPanel();
    private JPanel searchButtonsPanel = new JPanel();
    private JPanel navigateButtonsPanel = new JPanel();
    private CarDetailsComponents carComponents = new CarDetailsComponents();

    /**
     * @param carSys Object dari class CarDatabaseSystem
     * @param dest Menu yang akan ditampilkan
     */
    public SearchByOtherMenu(CarDatabaseSystem carSys){
        carSystem = carSys;
        setLayout(new BorderLayout());
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        previousButton.addActionListener(this);
        nextButton.addActionListener(this);
        resetButton.addActionListener(this);
        searchButton.addActionListener(this);

        pricePanel.add(priceLabel);
        pricePanel.add(priceCombo);
        priceDistancePanel.add(pricePanel);

        searchButtonsPanel.add(searchButton);
        searchButtonsPanel.add(resetButton);
        navigateButtonsPanel.add(previousButton);
        navigateButtonsPanel.add(nextButton);

        headingLabel.setAlignmentX(0.5f);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(headingLabel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(priceDistancePanel);
        topPanel.add(searchButtonsPanel);
        topPanel.add(Box.createVerticalStrut(15));
        carComponents.add(navigateButtonsPanel, "Center");
        carComponents.setVisible(false);

        add(topPanel, "North");
        add(carComponents, "Center");
    }

    /**
     * Cek Input user pada Button
     *
     * @param ev Object ActionEvent
     */
    public void actionPerformed(ActionEvent ev){
        if (ev.getSource() == searchButton){
            searchButtonClicked();
        }
        else if (ev.getSource() == resetButton){
            resetButtonClicked();
        }
        else if (ev.getSource() == previousButton){
            previousButtonClicked();
        }
        else if (ev.getSource() == nextButton){
            nextButtonClicked();
        }
    }

    /**
     * Menuju index berikutnya, jika ada dan akan menampilkan secara visual detail Mobil pada index tersebut
     */
    private void nextButtonClicked(){
        if (currentIndex < carList.length - 1){
            currentIndex++;
            carComponents.displayDetails(carList[currentIndex]);
        }
        else{
            JOptionPane.showMessageDialog(carSystem, "Anda tidak dapat lanjut ke halaman berikutnya.", "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Menuju index berikutnya jika ada dan akan menampilkan secara detail informasi mobil pada index tersebut
     */
    private void previousButtonClicked(){
        if (currentIndex > 0){
            currentIndex--;
            carComponents.displayDetails(carList[currentIndex]);
        }
        else{
            JOptionPane.showMessageDialog(carSystem, "Anda tidak dapat lanjut ke halaman sebelumnya", "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Menghapus hasil pencarian
     */
    private void resetButtonClicked(){
        currentIndex = 0;
        carList = null;
        carComponents.setVisible(false);
        priceCombo.setSelectedIndex(0);
    }

    /**
     * Mencari mobil berdasarkan harga
     */
    private void searchButtonClicked(){
        double[] priceRange = CarDatabaseSystem.convertToRange((String)priceCombo.getSelectedItem());

        if (priceRange[0] >= 0){
            carList = carSystem.search((int)priceRange[0], (int)priceRange[1]);
        }

        if (carList.length > 0){
            currentIndex = 0;
            carComponents.setVisible(true);
            carComponents.displayDetails(carList[0]);

            if (carList.length == 1){
                nextButton.setEnabled(false);
                previousButton.setEnabled(false);
            }
            else{
                nextButton.setEnabled(true);
                previousButton.setEnabled(true);
            }
            carSystem.repaint();
        }
        else{
            JOptionPane.showMessageDialog(carSystem, "Mohon maaf, pencarian tidak ditemukan", "Search failed", JOptionPane.WARNING_MESSAGE);
        }
    }
}