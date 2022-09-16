
package com.mygame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

public class MyGame extends JFrame implements ActionListener {
    
    JLabel heading,clock;
    Font font=new Font("",Font.BOLD,40);
    JPanel mainPanel;
    
    JButton[] btns=new JButton[9]; 
    
    // game instance variable
    int[] gameChances={2,2,2,2,2,2,2,2,2};
    int activePlayer=0;
    
    int[][] wps={
        {0,1,2},
        {3,4,5},
        {6,7,8},        // pattern for winning logic
        {0,3,6},
        {1,4,7},
        {2,5,8},
        {0,4,8},
        {2,4,6},
    };
    int winner=2;
    boolean gameOver=false;
      
    MyGame(){
        System.out.println("Creating instance");
        setTitle("My TicTacToe Game.."); 
        setSize(700,700);
        ImageIcon icon=new ImageIcon("src/img/tic.png");
        setIconImage(icon.getImage());
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        setVisible(true);
        
    }
    private void createGUI()
    {
        this.getContentPane().setBackground(Color.decode("#FFA500"));
        this.setLayout(new BorderLayout());
        
        // north portion of our display
        heading=new JLabel("Tic Tac Toe");
        heading.setFont(font);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(heading,BorderLayout.NORTH);
        heading.setForeground(Color.white);
        
        clock=new JLabel("Clock");
        clock.setFont(font);
        clock.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(clock,BorderLayout.SOUTH);
        clock.setForeground(Color.white);
        
        Thread t=new Thread(){
            @Override
            public void run()
            {
                try{
                    while(true)
                     {
                         String dt=new Date().toLocaleString();
                         clock.setText(dt);
                         Thread.sleep(1000);
                       }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }        
        };      
        t.start();
        
        // Panel Section
        mainPanel=new JPanel();
        mainPanel.setLayout(new GridLayout(3,3));
        
        for(int i=1; i<=9; i++){
            JButton btn=new JButton();
            
            
//            btn.setIcon(new ImageIcon("src/img/0.png"));
            
            
            btn.setBackground(Color.decode("#FFFFFF"));
            btn.setFont(font);
            btns[i-1]=btn;
            btn.addActionListener(this); 
            btn.setName(String.valueOf(i-1));
            mainPanel.add(btn);
        }
        this.add(mainPanel,BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         JButton currentButton =(JButton)e.getSource(); 
         
         String nameStr=currentButton.getName();
         
         
         // name will store the index of button
         int name=Integer.parseInt(nameStr.trim());
         
         if(gameOver)
         {
             JOptionPane.showMessageDialog(null,"Match is over");
             return;
         }
         
         
         
        // program to check button is empty or not(if empty then put img 0 else put img 1)  
         
         if(gameChances[name]==2)
         {
             if(activePlayer==1)
             {
                 currentButton.setIcon(new ImageIcon("src/img/1.png"));
                 
                 gameChances[name]=activePlayer;
                 activePlayer=0;    // now time for player 0
             }else{
                 currentButton.setIcon(new ImageIcon("src/img/0.png"));
                 gameChances[name]=activePlayer;
                 activePlayer=1;
             }
             
             // find the winner
             
             for(int[] temp : wps)
             {
                 if((gameChances[temp[0]]==gameChances[temp[1]])
                     && gameChances[temp[1]]==gameChances[temp[2]]
                      && gameChances[temp[2]] != 2)
                 {
                     gameOver=true;
                     winner=gameChances[temp[0]];
                 JOptionPane.showMessageDialog(null,"Player "+winner+" has won!");
                 
                int i=JOptionPane.showConfirmDialog(this,"Do you want to play again?");
                if(i==0)
                {
                    this.setVisible(false);
                    new MyGame();
                }else if(i==1)
                {
                    System.exit(0);
                }else{
                    
                }
                 System.out.println(i);
                 break;
                 }
             }
             
                             // Draw Logic
             int c=0;       // to count the any value 2 in gameChances array
             for(int x : gameChances)
             {
                  if(x==2)
                  {
                      c++;
                      break;
                  }
             }
             
             if(c==0 && gameOver==false)
             {
                 JOptionPane.showMessageDialog(null,"Match is drawn:");
                 int i=JOptionPane.showConfirmDialog(this, "Again Play??");
            
            if(i==0)
            {
                this.setVisible(false);
                  new MyGame();
                  
            }else if(i==1)
            {
                System.exit(12);
            }else{
                
            }
                gameOver=true;
             }
             
             
         }else{
             JOptionPane.showMessageDialog(this,"already occupied");
         }
             
    }
}