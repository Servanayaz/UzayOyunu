
import java.awt.HeadlessException;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class OyunEkrani extends JFrame {

    public OyunEkrani(String title) throws HeadlessException {
        super(title);
    }

    public static void main(String[] args) {

        OyunEkrani ekran = new OyunEkrani("Uzay Oyunu");

        ekran.setResizable(false);
        ekran.setFocusable(false); //odak burası olmayacak
        ekran.setSize(800,600);
        ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Oyun oyun=new Oyun(); //kodların sırası önemli
        oyun.requestFocus(); //klavye işlemleri için odağı istedik
        oyun.addKeyListener(oyun); //klavye işlemlerini anlaması için
        oyun.setFocusable(true); //klavye işlemlerinin odağını burası yaptık
        oyun.setFocusTraversalKeysEnabled(false);//klavye işlemlerinin gerçekleşebilmesi için
        
        ekran.add(oyun);
        
        ekran.setVisible(true);
    }

}
