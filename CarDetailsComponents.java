import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Class ini berisi informasi visual yang akan ditampilkan ke dalam berbagai menu
 * 
 * @version 1.0, 23 Maret 2020
 * @author Geraldy Christanto
 */
public class CarDetailsComponents extends JPanel implements ComponentListener{
    private JLabel manufacturerLabel = new JLabel("Manufaktur");
    private JLabel yearLabel = new JLabel("Tahun");
    private JLabel modelLabel = new JLabel("Model");
    private JLabel priceLabel = new JLabel("Harga (Rp)");
    private JLabel distanceLabel = new JLabel("Jarak Tempuh(KM)");
    private JLabel infoLabel = new JLabel("Informasi Tambahan");
    private JTextField manufacturerTextField = new JTextField();
    private JTextField yearTextField = new JTextField();
    private JTextField modelTextField = new JTextField();
    private JTextField priceTextField = new JTextField();
    private JTextField distanceTextField = new JTextField();
    private JTextArea infoTextArea = new JTextArea(4, 0);

    private final int divFactor = 27;

    public CarDetailsComponents(){
        Insets currentInsets;
        GridBagConstraints gridBagConstraints;
        setLayout(new BorderLayout(0, 20));
        JPanel compPanel = new JPanel(new GridBagLayout());
        String currentFont = manufacturerLabel.getFont().getName();
        currentInsets =  new Insets(0, 10, 0, 30);

        manufacturerLabel.setFont(new Font(currentFont, Font.BOLD, 12));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = currentInsets;
        compPanel.add(manufacturerLabel, gridBagConstraints);

        yearLabel.setFont(new Font(currentFont, Font.BOLD, 12));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = currentInsets;
        compPanel.add(yearLabel, gridBagConstraints);

        modelLabel.setFont(new Font(currentFont, Font.BOLD, 12));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = currentInsets;
        compPanel.add(modelLabel, gridBagConstraints);

        priceLabel.setFont(new Font(currentFont, Font.BOLD, 12));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = currentInsets;
        compPanel.add(priceLabel, gridBagConstraints);

        distanceLabel.setFont(new Font(currentFont, Font.BOLD, 12));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = currentInsets;
        compPanel.add(distanceLabel, gridBagConstraints);

        infoLabel.setFont(new Font(currentFont, Font.BOLD, 12));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = currentInsets;
        compPanel.add(infoLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        compPanel.add(manufacturerTextField, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        compPanel.add(yearTextField, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        compPanel.add(modelTextField, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        compPanel.add(priceTextField, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = gridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        compPanel.add(distanceTextField, gridBagConstraints);

        infoTextArea.setLineWrap(true);
        currentInsets = new Insets(2, 20, 0, 20);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = gridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        compPanel.add(new JScrollPane(infoTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), gridBagConstraints);

        addComponentListener(this);
        add(compPanel, "North");
    }

    /**
     * clear all the text fields
     */
    public void clearTextFields(){
        manufacturerTextField.setText("");
        yearTextField.setText("");
        modelTextField.setText("");
        priceTextField.setText("");
        distanceTextField.setText("");
        infoTextArea.setText("");
    }

    public void componentHidden(ComponentEvent ev){
    }

    public void componentMoved(ComponentEvent ev){
    }

    /**
     * Mengatur ulang ukuran setiap menu
     *
     * @return ev Objek ComponentEvent
     */
    public void componentResized(ComponentEvent ev){
        if (ev.getSource() == this){
            int width = getWidth();

            if (width >= 0){
                manufacturerTextField.setColumns(width / divFactor);
                yearTextField.setColumns(width / divFactor);
                modelTextField.setColumns(width / divFactor);
                priceTextField.setColumns(width / divFactor);
                distanceTextField.setColumns(width / divFactor);
                infoTextArea.setColumns((width / divFactor) + 3);
            }
        }
    }

    public void componentShown(ComponentEvent ev){
    }

    /**
     * MEnampilkan detail informasi mobil
     *
     * @param car Car yang akan ditampilkan
     */
    public void displayDetails(Car c){
        manufacturerTextField.setText(c.getManufacturer());
        yearTextField.setText(Integer.toString(c.getYear()));
        modelTextField.setText(c.getModel());
        priceTextField.setText(Long.toString(c.getPrice()));
        distanceTextField.setText(Double.toString(c.getDistance()));
        infoTextArea.setText(c.getInformation());
    }

    public String getInfoText(){
        return infoTextArea.getText();
    }

    public String getDistanceText(){
        return distanceTextField.getText();
    }

    public String getManufacturerText(){
        return manufacturerTextField.getText();
    }

    public String getModelText(){
        return modelTextField.getText();
    }

    public String getPriceText(){
        return priceTextField.getText();
    }

    public String getYearText(){
        return yearTextField.getText();
    }

    /**
     * Mengatur fokus ke detail Manufacturer
     * 
     */
    public void setFocusManufacturerTextField(){
        manufacturerTextField.grabFocus();
    }
}
