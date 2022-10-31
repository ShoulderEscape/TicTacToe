import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import static javax.swing.JOptionPane.showMessageDialog;

public class MyWindow extends JFrame implements ComponentListener, ActionListener {
    public Label label;
    public JButton button;
    char player;
    char noone=' ';
    int size=250;
    float PlayerXWins=0;
    float PlayerOWins=0;
    boolean running;
    ImageIcon logo;
    private final ArrayList<Character> Placements;
    private final ArrayList<JButton> bg;
    private final JPanel jp;
    public MyWindow(){
        this.setBackground(Color.BLACK);
        label = new Label();
        label.setForeground(Color.BLACK);
        logo= new ImageIcon("src/Logo.png");
        this.setLayout(new BorderLayout());
        jp = new JPanel();
        this.add(jp);
        Placements= new ArrayList<Character>();
        labelFont();
        this.add((label),BorderLayout.NORTH);
        jp.setLayout(new GridLayout(3,3,5,5));

        for (int i = 0; i < 9; i++) {
            Placements.add(noone);
        }

        bg= new ArrayList<>();
        Makebuttons();
        this.setVisible(true);
        this.setSize(1000,1000);
        this.addComponentListener(this);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.black);
        Start();
    }
    public void Start(){

        running=true;
        player='X';
        int n=0;
        for (int i = 0; i < 9; i++) {
            if(i!=4){
                bg.get(i).setIcon(null);
            }
            Placements.set(i,noone);
            System.out.println("Restart");
            write();
            bg.get(i).setText(String.valueOf(noone));
            bg.get(i).setBackground(Color.gray);
        }
        label.setBackground(Color.blue);
    }
    public void Makebuttons(){
        for (int i = 0; i < 9; i++) {
            System.out.print(Placements.get(i));
            button = new JButton();
            bg.add(button);
            jp.add(button);
            button.setForeground(Color.BLACK);
            button.addActionListener(this);
            String N=Integer.toString(i);
            button.setActionCommand(N);
            button.setBackground(Color.gray);

        }
        System.out.println();
    }
    @Override
    public void componentResized(ComponentEvent e) {
        System.out.println("Resized");
        if(this.getHeight()>this.getWidth()){
            size=this.getWidth()/4;
        } else{
            size=this.getHeight()/4;
        }
        for (int i = 0; i < 9; i++) {
            setFont(i);
        }
        labelFont();
        if(!running){
            restartFont();
        }

    }
    private void labelFont(){
        label.setFont(new Font("Serif", Font.BOLD, size/5));
    }
    private void restartFont(){
        bg.get(4).setFont(new Font("Arial",Font.BOLD,size/5));
    }
    private void setFont(int i){ bg.get(i).setFont(new Font("Arial", Font.BOLD,size)); }

    @Override public void componentMoved(ComponentEvent e)  {      }
    @Override public void componentShown(ComponentEvent e)  {      }
    @Override public void componentHidden(ComponentEvent e) {      }




    @Override
    public void actionPerformed(ActionEvent e) {
        //Inspiration från https://examples.javacodegeeks.com/desktop-java/swing/jbutton/set-action-command-for-jbutton/
        String action = e.getActionCommand();
        if (running) {
            for (int i = 0; i < 10; i++) {
                String N=Integer.toString(i);
                if(action.equals(N) && Placements.get(i)==noone){
                    Placements.set(i, player);
                    //Testing information
                    write();

                    bg.get(i).setText(String.valueOf(player));
                    setFont(i);

                    if(player=='X'){
                        bg.get(i).setBackground(Color.blue);
                        label.setBackground(Color.red);
                        player='O';
                    } else {
                        bg.get(i).setBackground(Color.red);
                        label.setBackground(Color.blue);
                        player='X';
                    }
                }
            }
            results();
        } else{
            if(action.equals("4")){
                System.out.println("Hello");
                Start();
            } else{
                showMessageDialog(null, "The Game is over (Click the center square to restart)");
            }
        }

    }
    public void results(){
        boolean Xwon= checkstatus('X');
        boolean Owon= checkstatus('O');
        boolean Draw=true;
        for (int i = 0; i < 9; i++) {
            if(Placements.get(i)==noone){
                Draw=false;
            }
        }
        if(Xwon){
            showMessageDialog(null, "X Won!");
            Ended("Xwon");

        } else if(Owon){
            showMessageDialog(null, "O Won!");
            Ended("Owon");

        } else if(Draw){
            showMessageDialog(null, "Draw!");
            Ended("Draw");

        }
    }
    public void Ended(String result) {
        running=false;
        bg.get(4).setText("restart");
        for (int i = 0; i < 9; i++) {
            if(result.equals("Owon")){
                bg.get(i).setBackground(Color.red);
            }
            if(result.equals("Xwon")){
                bg.get(i).setBackground(Color.blue);
            }
            if(result.equals("Draw")){
                bg.get(i).setBackground(new Color(87,45,94));
            }
            if(i!=4){
                bg.get(i).setIcon(logo);
                bg.get(i).setText("");
            }
        }
        restartFont();

        if(result.equals("Owon")){
            PlayerOWins++;
            label.setBackground(Color.red);
        }
        if(result.equals("Xwon")){
            PlayerXWins++;
            label.setBackground(Color.blue);
        }
        if(result.equals("Draw")){
            PlayerXWins+=0.5;
            PlayerOWins+=0.5;
            label.setBackground(new Color(87,45,94));
        }

        label.setText("Player X score: "+PlayerXWins+" | Player O score: "+ PlayerOWins);
    }
    public boolean checkstatus(char P){
        boolean horizontal= (Placements.get(0) == P && Placements.get(1) == P && Placements.get(2) == P) ||
                (Placements.get(3) == P && Placements.get(4) == P && Placements.get(5) == P) ||
                (Placements.get(6) == P && Placements.get(7) == P && Placements.get(8) == P);
        boolean vertical= (Placements.get(0) == P && Placements.get(3) == P && Placements.get(6) == P) ||
                (Placements.get(1) == P && Placements.get(4) == P && Placements.get(7) == P) ||
                (Placements.get(2) == P && Placements.get(5) == P && Placements.get(8) == P);
        boolean diagonal= (Placements.get(0) == P && Placements.get(4) == P && Placements.get(8) == P) ||
                (Placements.get(2) == P && Placements.get(4) == P && Placements.get(6) == P);
        return horizontal || vertical || diagonal;
    }
    public void write(){
        System.out.println("---------");
        System.out.println("|"+Placements.get(0)+"  "+Placements.get(1)+"  "+Placements.get(2)+"|");
        System.out.println("|"+Placements.get(3)+"  "+Placements.get(4)+"  "+Placements.get(5)+"|");
        System.out.println("|"+Placements.get(6)+"  "+Placements.get(7)+"  "+Placements.get(8)+"|");
        System.out.println("---------");
    }
}
