
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

//KeyListener -> klavye işlemlerinin anlaşılması için
//ActionListener -> hareketler için
class Ates {

    private int x, y;

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}

public class Oyun extends JPanel implements KeyListener, ActionListener {

    Timer timer = new Timer(5, this);

    private int gecenSure = 0, harcananAtes = 0;

    private BufferedImage image;

    private ArrayList<Ates> atesler = new ArrayList<Ates>();

    private int atesdirY = 1; //ateşin ilerlemesii için
    private int topX = 0; //topun başlangıç noktası ve hareketi için

    private int topdirX = 2;//topun hareket hızı

    private int uzayGemisiX = 0;//uzay gemisi başlangıç noktası

    private int dirUzayX = 20; //her harekette geminin gideceği mesafe

    public Oyun() {
        try {
            image = ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")));
        } catch (IOException ex) {
            System.out.println("Image okunurken hata oluştu !");
        }
        setBackground(Color.BLACK);
        timer.start();
    }

    public boolean kontrolEt(){
        //çarpışma kontrolü
        for (Ates ates : atesler) {
            if (new Rectangle(ates.getX(),ates.getY(),10,10).intersects(new Rectangle(topX,0,20,20))) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        gecenSure+=5;
        //topun çizimi
        g.setColor(Color.BLUE);
        g.fillOval(topX, 0, 20, 20);

        //uzay gemisi eklenmesi
        g.drawImage(image, uzayGemisiX, 490, image.getWidth() / 22, image.getHeight() / 22, this);//resim boyutu çok büyük eklerken küçülttük
        for (Ates ates : atesler) {
            if (ates.getY()<0) {
                atesler.remove(ates);
            }
        }
        g.setColor(Color.RED);
        
        for (Ates ates : atesler) {
            g.fillOval(ates.getX(), ates.getY(), 10, 10);
        }
        if (kontrolEt()) {
            timer.stop();
            String message="Kazandınız...\n"+
                            "Harcanan Ateş : "+harcananAtes+
                            "\nGeçen Süre : "+gecenSure/1000.0+" saniye";
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c=e.getKeyCode();
        
        if (c ==KeyEvent.VK_LEFT) {
            if (uzayGemisiX<=0) {
                uzayGemisiX=0;
            }else{
                uzayGemisiX-=dirUzayX;
            }
        }else if (c ==KeyEvent.VK_RIGHT) {
            if (uzayGemisiX>=750) {
                uzayGemisiX=750;
            }else{
                uzayGemisiX+=dirUzayX;
            }
        }else if (c==KeyEvent.VK_CONTROL) {
            atesler.add(new Ates(uzayGemisiX+15,490));
            harcananAtes++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Ates ates : atesler) {
            ates.setY(ates.getY()-atesdirY);
        }
        topX += topdirX;
        if (topX >= 750) {
            topdirX = -topdirX;
        }
        if (topX<=0) {
            topdirX= -topdirX;
        }
        repaint();
    }

}
