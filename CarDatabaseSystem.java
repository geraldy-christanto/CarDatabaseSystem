import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
/**
 * Class ini merupakan fungsi utama dari aplikasi ini
 * Class ini akan membuat instance dari panel lain seperti tambah mobil, lihat mobil, dan lainnya.
 * ke dalam frame utama. Secara hierarki class ini merupakan fungsi teratas
 *
 * @version 1.0, 22 Maret 2020
 * @author Geraldy Christanto
 */
public class CarDatabaseSystem extends JFrame implements ActionListener, ComponentListener, ChangeListener{
    public static final double APP_VERSION = 1.0;
    public static final int CARS_COUNT = 0;
    public static final int MANUFACTURERS_COUNT = 1;
    public static final int AVERAGE_PRICE = 2;
    public static final int AVERAGE_DISTANCE = 3;
    public static final int AVERAGE_AGE = 4;

    private String file;
    private AboutDialog aboutDlg;
    private boolean carsUpdated = false;
    private Vector registeredListeners = new Vector();
    private CarsCollection carCollection;
    private JPanel topPanel = new JPanel(new BorderLayout());
    private JPanel titlePanel = new JPanel(new GridLayout(2, 1));
    private JLabel statusLabel = new JLabel();
    private JLabel pictureLabel = new JLabel();
    private JLabel carCoLabel = new JLabel("PT. jCar", JLabel.CENTER);
    private JLabel sysLabel = new JLabel("Sistem Database Mobil", JLabel.CENTER);
    private JTabbedPane theTab = new JTabbedPane(JTabbedPane.LEFT);
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenuItem aboutItem = new JMenuItem("Tentang");
    private JMenuItem exitItem = new JMenuItem("Keluar");
    private WindowCloser closer = new WindowCloser();

    /**
     * @param f Parameter untuk file binary yang berfungsi untuk menyimpan informasi mobil
     */
    public CarDatabaseSystem(String f){
        super("Sistem Database Mobil");

        addWindowListener(closer);
        addComponentListener(this);
        theTab.addChangeListener(this);

        setSize(780, 560);

        Container c = getContentPane();
        carCollection = new CarsCollection();

        file = f;

        try{
            carCollection.loadCars(file);
        }
        catch(java.io.FileNotFoundException exp){
            System.out.println("File 'cars.dat' tidak ditemukan.");
            System.exit(0);
        }
        
        catch(java.io.EOFException exp){
        }
        catch(java.io.IOException exp){
            System.out.println("File 'cars.dat' kemungkinan corrupt, coba hapus dan buat file 'cars.dat' baru");
            System.exit(0);
        }
        catch(Exception exp){
            System.out.println("Terdapat kesalahan dalam pengambilan data pada file 'cars.dat'. coba hapus dan buat file 'cars.dat' baru");
            System.exit(0);
        }

        String currentFont = carCoLabel.getFont().getName();
        carCoLabel.setFont(new Font(currentFont, Font.BOLD, 26));
        sysLabel.setFont(new Font(currentFont, Font.PLAIN, 16));

        //Menu
        menuBar.add(fileMenu);
        fileMenu.add(aboutItem);
        fileMenu.add(exitItem);
        aboutItem.addActionListener(this);
        exitItem.addActionListener(this);

        //Menu Bar
        setJMenuBar(menuBar);

        statusLabel.setBorder(new javax.swing.border.EtchedBorder());
        
        titlePanel.add(carCoLabel);
        titlePanel.add(sysLabel);
        topPanel.add(pictureLabel, "West");
        topPanel.add(titlePanel, "Center");

        WelcomePanel welcomePanel = new WelcomePanel(this, f);
        AddCarMenu addCarMenu = new AddCarMenu(this);
        ShowAllCarsMenu showAllCarsMenu = new ShowAllCarsMenu(this);
        SearchByOtherMenu searchByOtherMenu = new SearchByOtherMenu(this);

        theTab.add("Selamat datang", welcomePanel);
        theTab.add("Tambahkan Informasi Mobil Baru", addCarMenu);
        theTab.add("Tampilkan manufaktur dan model", showAllCarsMenu);
        theTab.add("Cari berdasarkan harga", searchByOtherMenu);

        theTab.addChangeListener(showAllCarsMenu);
        theTab.addChangeListener(welcomePanel);

        theTab.setSelectedIndex(0);

        c.setLayout(new BorderLayout());
        c.add(topPanel, "North");
        c.add(theTab, "Center");
        c.add(statusLabel, "South");
    }

    /**
     * Menampilkan about dialog
     */
    public void aboutMenuItemClicked(){
        if(aboutDlg == null){
            aboutDlg = new AboutDialog(this, "About", true);
        }
        aboutDlg.showAbout();
    }

    /**
     * Menerima dan mengendalikan menu
     *
     * @param ev Objek untuk event
     */
    public void actionPerformed(ActionEvent ev){
        if(ev.getSource() == aboutItem){
            aboutMenuItemClicked();
        }
        else if(ev.getSource() == exitItem){
            closing();
        }
    }

    /**
     * Menambahkan sebuah objek notifikasi ketika objek mobil berhasil ditabahkan
     *
     * @param listener a listener object
     */
    public void addCarUpdateListener(Object listener){
        if (!(listener == null)){
            registeredListeners.add(listener);
        }
    }

    /**
     * Menambahkan mobil dalam class CarCollection
     *
     * @param c Objek mobil/car yang ditambahkan
     * @return indikasi berhasil atau tidaknya mobil ditambahkan
     */
    public int addNewCar(Car c){
        return carCollection.addCar(c);
    }

    /**
     * Event untuk menutup aplikasi dan menyimpan pembaruan data pada file binary
     * ketika ditutup
     */
    public void closing(){
        boolean ok;
        if (carsUpdated){
            do{
                try{
                    carCollection.saveCars(file);
                    ok = true;
                }
                catch (java.io.IOException exp){
                    int result = JOptionPane.showConfirmDialog(this, "File tidak dapat diperbarui, mungkin anda tidak memiliki akses pada lokasi penyimpanan file.\n\nApakah anda ingin menyimpan data file kembali?", "Problem saving data", JOptionPane.YES_NO_OPTION);

                    if (result == JOptionPane.YES_OPTION){
                        ok = false;
                    }
                    else{
                        ok = true;
                    }
                }
            }
            while (!ok);
        }
        System.exit(0);
    }

    public void componentHidden(ComponentEvent ev){
    }

    public void componentMoved(ComponentEvent ev){
    }

    /**
     * Menerima event ketika Frame di atur ulang dan memastikan ukuran aplikasi tidak berubah
     *
     * @param ev ComponentEvent object
     */
    public void componentResized(ComponentEvent ev){
        if (this == ev.getComponent()){
            Dimension size = getSize();

            if (size.height < 530){
                size.height = 530;
            }
            
            if (size.width < 675){
                size.width = 675;
            }

            setSize(size);
        }
    }

    public void componentShown(ComponentEvent ev){
    }

    /**
     * Konversi string range menjadi batas maksimum dan batas Minimum
     *
     * @param s String yang berisi range nilai
     * @return array dari 2 element. Element pertama mengindikasikan batas minimum, 
     * Element kedua mengindikasikan batas maksimum. 
     * 
     */
    public static double[] convertToRange(String s){
        String[] parts = s.trim().split("-");
        double[] bounds = new double[2];

        try{
            if (parts.length == 1){
                String c = s.substring(s.length() - 1);

                //Jika range price berbentuk "m+"
                if (c.equals("+")){
                    //Mendapatkan batas bawah dari String
                    bounds[0] = Double.parseDouble(s.substring(0, s.length() - 1));
                    //Tidak ada batas maksimum, sehingga infinite
                    bounds[1] = -1;
                }
                //Jika range price berbentuk "m"
                else{
                    //Batas atas == batas bawah. Range ini berbentuk angka
                    bounds[0] = Double.parseDouble(s);
                    bounds[1] = bounds[0];
                }
            }
            //Range Price dengan bentuk "m - n"
            else if(parts.length == 2){
                bounds[0] = Double.parseDouble(parts[0]);
                bounds[1] = Double.parseDouble(parts[1]);
                if (bounds[0] > bounds[1]){
                    //Jika range harga dalam bentuk yang salah, contoh "15 - 10"
                    bounds[0] = -1;
                    bounds[1] = -1;
                }
            }
        }
        catch (NumberFormatException exp){
            bounds[0] = -1;
            bounds[1] = -1;
        }
        return bounds;
    }

    /**
     * Mendapatkan keseluruhan daftar mobil dari CarsCollection
     *
     * @return array dari mobil yang berisi detail mobil
     */
    public Car[] getAllCars(){
        return carCollection.getAllCars();
    }


    /**
     * Method indicator jika terjadi pembaruan mobil
     *
     * @return boolean mengindikasikan jika terjadi pembaruan mobil dalam CarCollection
     */
    public boolean getCarsUpdated(){
        return carsUpdated;
    }

    /**
     * Mendapatkan statistic dari CarCollection
     *
     * @param type antara variabel CARS_COUNT, MANUFACTURERS_COUNT, AVERAGE_PRICE, AVERAGE_DISTANCE atau
     * AVERAGE_AGE
     * @return sebuah nilai berdasarkan type tertentu
     */
    public double getStatistics(int type){
        double result = 0;

        if (type == CARS_COUNT){
            result  = carCollection.carsCount();
        }
        else if (type == MANUFACTURERS_COUNT){
            result = carCollection.manufacturerCount();
        }
        else if (type == AVERAGE_PRICE){
            result = carCollection.getAveragePrice();
        }
        else if (type == AVERAGE_DISTANCE){
            result = carCollection.getAverageDistance();
        }
        else if (type == AVERAGE_AGE){
            result = carCollection.getAverageAge();
        }
        return result;
    }

    /**
     * Inisialisasi CarDatabaseSystem
     *
     * @param args Nama dari file binary yang digunakan
     */
    public static void main(String[] args){
        CarDatabaseSystem carSys = new CarDatabaseSystem("cars.dat");
        carSys.setVisible(true);
    }

    /**
     * Cari mobil berdasarkan parameter tertentu dalam CarsCollection
     *
     * @param minBound batas minimum
     * @param maxBound batas maksimum
     * @return array dari mobil yang memenuhi kriteria
     */
    public Car[] search(int minBound, int maxBound){
        return carCollection.search(minBound, maxBound);
    }

    /**
     * Pencarian berdasarkan kriteria Price dan Jarak pada CarsCollection
     *
     * @param minPrice Harga minimum mobil
     * @param maxPrice Harga maksimum mobil
     * @param minDistance Jarak tempuh minimum
     * @param maxDistance Jarak tempuh maksimum
     * @return array dari mobil yang sesuai dengan kriteria
     */
    public Car[] search(int minPrice, int maxPrice, double minDistance, double maxDistance){
        return carCollection.search(minPrice, maxPrice,  minDistance, maxDistance);
    }

    /**
     * Memanggil method ini untuk mengindikasikan adanya penambahan mobil 
     * dan meinformasikan adanya perubahan
     */
    public void setCarsUpdated(){
        carsUpdated = true;

        for(int i = 0; i < registeredListeners.size(); i++){
            Class[] paramType = {CarUpdateEvent.class};
            Object[] param = {new CarUpdateEvent(this)};

            try{
                java.lang.reflect.Method callingMethod = registeredListeners.get(i).getClass().getMethod("carsUpdated", paramType);
                callingMethod.invoke(registeredListeners.get(i), param);
            }
            catch(NoSuchMethodException exp){
                System.out.println("Perhatian, method 'public carsUpdated(CarEvent)' tidak ada dalam " + registeredListeners.get(i).getClass().getName() + ". Anda tidak dapat melakukan pembaruan data Mobil");
            }
            catch(IllegalAccessException exp){
                System.out.println("Perhatian, method 'public carUpdated(CarEvent)' tidak dapat dipanggil dengan alasan tertentu, Anda tidak dapat melakukan pembaruan data Mobil");
            }
            catch(Exception exp){
            }
        }
    }

    /**
     * Mengubah judul Menu ketika object berubah
     *
     * @param ev Objek ChangeEvent
     */
    public void stateChanged(ChangeEvent ev){
        if (ev.getSource() == theTab)
            statusLabel.setText("Menu saat ini: " + theTab.getTitleAt(theTab.getSelectedIndex()));
    }

    /**
     * Mengubah vector menjadi array
     *
     * @param v Vector yang berisi array dari object Car
     * @return ret Array berisi objek Car dari Vector
     */
    public static Car[] vectorToCar(Vector v){
        Car[] ret = new Car[v.size()];

        for (int i = 0; i < v.size(); i++){
            ret[i] = (Car)v.elementAt(i);
        }
        return ret;
    }

    class WindowCloser extends WindowAdapter{
        /**
         * Memanggil menu utama ketika menu ditutup
         *
         * @param ev Objek WindowEvent
         */
        public void windowClosing(WindowEvent ev){
            closing();
        }
    }
}