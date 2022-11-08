import javax.swing.JFrame;

public class Main {
    static JFrame gameFrame;
    static GameField gp;

    public static void main(String[] args) {
        gameFrame = new JFrame();
        gp = new GameField();
        gameFrame.setBounds(10, 10, 700, 700); //создаем рамку
        gameFrame.setTitle("Ну Погоди!"); //имя рамки
        gameFrame.setResizable(false);
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //закрыть программу
        gameFrame.add(gp);
    }
}