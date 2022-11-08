import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.JPanel;


public class GameField extends JPanel implements KeyListener, ActionListener {
    private boolean start = false, gameOver = true;
    Timer timer;
    Random rand = new Random();//Рандом координат для яиц
    Random rand1 = new Random();//рандом для цвета
    int count = 0;
    int EggInterval = 100;
    int frameCounter = 0;
    static int basket_x = 50, basket_y = 500;//координаты корзины
    static int v = 10;//скорость корзины
    static int ev = 2; //скорость яиц
    Color[] c = {Color.red, Color.green, Color.blue, Color.white};
    int setcolor = rand1.nextInt(3);
    boolean s = true;
    int hcount = 5;

    ArrayList<Integer> eggX;
    ArrayList<Integer> eggY;

    public GameField() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(20, this);
        timer.start();
        refreshPresents();
    }


    void egg(Graphics g) {
        for (int j = 0; j < eggX.size(); j++) {
            g.setColor(c[3]);
            g.fillOval(eggX.get(j), eggY.get(j), 20, 30);
        }
    }

    public void paint(Graphics g) {
        //указать адрес корректно
        Image imgb = new ImageIcon("C:\\Users\\...\\Desktop\\nu_pogodi.jpg").getImage(); //фон
        Image imgf = new ImageIcon("C:\\Users\\...\\Desktop\\farm.jpg").getImage(); //задний фон
        Image img = new ImageIcon("C:\\Users\\...\\Desktop\\basket.png").getImage(); //корзина
        Image img1 = new ImageIcon("C:\\Users\\...\\Desktop\\wolf.png").getImage(); //волк
        Image imgh = new ImageIcon("C:\\Users\\...\\Desktop\\heart.png").getImage(); //сердце
        //задний фон
        g.clearRect(0, 0, 700, 700);
        g.fillRect(0, 0, 700, 700);
        //Фон
        Font infFont = new Font("Helvetica", Font.BOLD, 20);
        if (s) {
            g.clearRect(50, 50, 600, 600);
            g.drawRect(50, 50, 600, 600);
            g.drawImage(imgb, 50, 50, 600, 600, null);
            g.setColor(Color.white);
            g.setFont(infFont);
            g.setColor(Color.BLACK);
            g.drawString("Press", 300, 500);
            g.setColor(c[setcolor]);
            g.drawString("\tENTER\t", 400, 500);
            g.setColor(Color.BLACK);
            g.drawString("\tto Start the Game", 300, 550);
            if (setcolor >= 3)
                setcolor = 0;
            setcolor++;
        } else { //bg
            g.clearRect(50, 50, 600, 600);
            g.drawRect(50, 50, 600, 600);
            g.drawImage(imgf, 50, 50, 600, 600, null);
            //счет
            g.setFont(infFont);
            g.setColor(Color.WHITE);
            g.drawString("Score:" + count, 50, 40);
            //сердце
            for (int k = 0; k < hcount; k++)
                g.drawImage(imgh, 500 + 30 * k, 20, 30, 30, null);
            //волк
            g.drawImage(img1, basket_x - 15, basket_y - 100, 100, 200, null);
            //корзина
            g.drawImage(img, basket_x, basket_y, 100, 50, null);
            //яица
            egg(g);
            if (gameOver && !start) {
                g.clearRect(0, 0, 700, 700);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, 700, 700);
                g.drawRect(50, 50, 600, 600);
                g.drawImage(imgb, 50, 50, 600, 600, null);
                g.setFont(infFont);
                g.setColor(Color.WHITE);
                //Надписи игровых действий
                g.drawString("GAME OVER", 300, 500);
                g.drawString("Your score:" + count, 300, 520);
                g.setColor(Color.white);
                g.drawString("Press", 300, 540);
                g.setColor(c[setcolor]);
                g.drawString("\tENTER\t", 380, 540);
                g.setColor(Color.white);
                g.drawString("\tto Restart", 300, 560);
                setcolor++;
            }
        }
    }

    public void createEgg() { //создаем яица в рандомных местах
        int x = 70 + rand.nextInt(550), y = 50;
        eggX.add(x);
        eggY.add(y);
    }

    void play() { //игра
        if (start) {
            for (int j = 0; j < eggX.size(); j++) { //движение яиц
                eggY.set(j, eggY.get(j) + ev);

                if (eggX.get(j) > basket_x && eggX.get(j) < basket_x + 50 && //удаление яиц при касании с корзиной
                        eggY.get(j) + 20 >= basket_y && eggY.get(j) + 20 <=
                        basket_y + 5) {
                    eggX.remove(j);
                    eggY.remove(j);
                    count++;    //счетчик очков
                    break;
                }
                if (eggY.get(j) >= 600) { //удаление яиц после ухода из игровой зоны
                    eggX.remove(j);
                    eggY.remove(j);
                    hcount--;   // минус жизнь если пропустил яйцо, всего три жизни
                    if (hcount == 0) // а после игра заканчивается
                        start = false;
                    gameOver = true;
                    break;
                }
            }
        }
    }
//движание корзины вправо и влево
    void moveRight() {
        if (basket_x + 80 < 650)
            basket_x += v;
    }

    void moveLeft() {
        if (basket_x >= 50)
            basket_x -= v;
    }


    @Override
    public void actionPerformed(ActionEvent arg0) {
        play();
        frameCounter++;
        if (frameCounter % EggInterval == 0) {
            createEgg();
            frameCounter = 0;
        }
        setcolor++;
        if (setcolor >= 3)
            setcolor = 0;
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) { //движение привязано к клавишам
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (gameOver) {
                gameOver = false;
                s = false;
                start = true;
                hcount = 5;
                count = 0;
                refreshPresents();
            }
        }
    }

    private void refreshPresents() {
        eggX = new ArrayList<>();
        eggY = new ArrayList<>();
        createEgg();
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

}



