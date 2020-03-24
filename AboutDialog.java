import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Informasi aplikasi melalui about dialog
 *
 * @version 1.0, 22 Maret 2020
 * @author Geraldy Christanto
 */
public class AboutDialog extends JDialog implements ActionListener{
    private JButton okButton = new JButton("OK");
    private JPanel buttonPanel = new JPanel();
    private WindowCloser closer = new WindowCloser();

    /**
     * @param parent Parent JFrame untuk menampilkan tulisan di atas menu
     * @param title Judul dialog
     * @param modal Modal window untuk indikator
     */
    public AboutDialog(JFrame parent, String title, boolean modal){
        super(parent, title, modal);
        Container c = getContentPane();

        setSize(520, 180);
        setLocationRelativeTo(parent);
        addWindowListener(closer);
        c.setLayout(new GridLayout(4, 1));
        setTitle(title);
        buttonPanel.add(okButton);
        c.add(new JLabel("Sistem Database Mobil oleh Geraldy Christanto", JLabel.CENTER));
        c.add(new JLabel("OOP1, 1706043001", JLabel.CENTER));
        c.add(new JLabel("(C) 2020", JLabel.CENTER));
        c.add(buttonPanel);
        okButton.addActionListener(this);
    }

    /**
     * Action untuk menutup menu
     *
     * @param ev Objek ActionEvent
     */
    public void actionPerformed(ActionEvent ev){
        if (ev.getSource() == okButton){
            closing();
        }
    }

    /**
     * Menyembunyikan menu tanpa perlu membuat object menu baru
     * dengan menggunakan setVisible
     */
    public void closing(){
        setVisible(false);
    }

    /**
     * Method untuk menampilkan About dialog
     */
    public void showAbout(){
        setLocationRelativeTo(getParent());
        setVisible(true);
    }

    class WindowCloser extends WindowAdapter{
        /**
         * Memanggil Menu utama setelah menutup about dialog
         *
         * @param ev Object WindowEvent
         */
        public void windowClosing(WindowEvent ev){
            closing();
        }
    }
}