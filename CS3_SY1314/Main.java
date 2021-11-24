package CS3_SY1314;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.concurrent.TimeUnit;

public class Main {

    JFrame mainframe = new JFrame("The Game");
    Container space = new Container();
    Display d = new Display();
    int count = 0;
    int rate = 10;
    boolean win = true;

    public static void main(String[] args) {
        Main app = new Main();//app launcher
        app.Choose();
        app.Launch();
    }

    private void Choose() {//choose diff
        JFrame frame = new JFrame("Choose difficulty:");
        frame.setSize(200, 70);
        Panel p = new Panel();
        p.setSize(300, 200);
        frame.add(p);
        Button d1 = new Button("Easy");
        Button d2 = new Button("Hard");
        Button d3 = new Button("Impossible");
        p.add(d1);
        p.add(d2);
        p.add(d3);
        ButtonHandler handler = new ButtonHandler();//add handler
        d1.addActionListener(handler);
        d2.addActionListener(handler);
        d3.addActionListener(handler);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        try {
            TimeUnit.MILLISECONDS.sleep(5000);//wait for 5 seconds before initializing the game
        } catch (InterruptedException e) {
        }
        frame.setVisible(false);
    }

    private void Launch() {
        mainframe.setSize(600, 400);
        d.setSize(600, 400);
        d.setBackground(Color.BLACK);
        space = mainframe.getContentPane();
        space.add(d);
        d.addKeyListener(new Main.KeyPress());//for space and enter buttons
        mainframe.setLocationRelativeTo(null);
        mainframe.setResizable(false);
        mainframe.setVisible(true);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ballz:
        while (true) {//infinite loop until life becomes 0
            try {
                TimeUnit.MILLISECONDS.sleep(40);
                if (d.scount < 15000) {
                    d.scount++;//shield replenish every 15 seconds
                } else {
                    d.scount = 0;
                    if (d.shields < 5) {
                        d.shields++;
                    }
                }
                if (d.sdecay > 0) {
                    d.sdecay--;
                } else if (d.sdecay == 0) {//if shield wears off
                    d.sstate = false;//shield state becomes no shield
                }
                switch (d.diff) {//difficulty firing rate
                    case 1:
                        d.AIFire();
                        break;
                    case 2:
                        d.AIFireEX();
                        break;
                    case 3:
                        d.AIFireEX();
                        break;
                }
                if (d.pjump == true) {//if player jumps
                    if (d.pjcount <= 15) {//jump calculator based on physics laws
                        d.pyheight = 16 - d.pjcount;
                        d.y -= d.pyheight;
                    } else {
                        d.pyheight = 1 + 30 - d.pjcount;
                        d.y += d.pyheight;
                    }
                    if (d.pjcount >= 30) {
                        d.pjcount = 0;
                        d.pyheight = 0;
                        d.pjump = false;
                    } else {
                        d.pjcount++;
                    }
                }
                if (d.aijump == true) {//for ai
                    if (d.aijcount <= 15) {
                        d.aiyheight = 16 - d.aijcount;
                        d.aiy -= d.aiyheight;
                    } else {
                        d.aiyheight = 1 + 30 - d.aijcount;
                        d.aiy += d.aiyheight;
                    }
                    if (d.aijcount >= 30) {
                        d.aijcount = 0;
                        d.aiyheight = 0;
                        d.aijump = false;
                    } else {
                        d.aijcount++;
                    }
                }
                for (int x = 0; x < d.numb; x++) {
                    if (d.bstatep[x] == true) {
                        d.AICode(d.diff, d.bulletsp[x][1]);//ai dodger
                    }
                }
                for (int x = 0; x < d.numb; x++) {
                    if (d.bstatep[x] == true) {//if bullet exists
                        d.pcount[x]++;
                        if (d.pcount[x] == 455 / rate) {//if rate reaches max
                            d.pcount[x] = 0;
                            d.bstatep[x] = false;
                            if ((d.bulletsp[x][1] > d.aiy - 17) && (d.bulletsp[x][1] < d.aiy + 51)) {//if bullet collides
                                if (d.shields != 0 && d.diff == 3) {//difficulty 3 shields trigger
                                    d.sstate = true;
                                    d.sdecay = 50;
                                    d.shields -= 1;
                                } else {//or -1 life
                                    d.ailife -= 1;
                                    if (d.ailife == 0) {
                                        d.aistate = false;
                                        win = true;
                                        break ballz;
                                    }
                                }
                            }
                        }
                        d.bulletsp[x][0] += rate;//move
                    } else {
                        d.bulletsp[x][1] = 0;//bullet state off
                    }
                    if (count != 0) {
                        count++;
                    }
                    if (count == 4 * d.numb) {
                        count = 0;
                    }
                }
                for (int x = 0; x < d.numb; x++) {//same script but for ai
                    if (d.bstateai[x] == true) {
                        d.aicount[x]++;
                        if (d.aicount[x] == 455 / rate) {
                            d.aicount[x] = 0;
                            d.bstateai[x] = false;
                            if ((d.bulletsai[x][1] > d.y - 17) && (d.bulletsai[x][1] < d.y + 51)) {
                                d.plife -= 1;
                                if (d.plife == 0) {
                                    d.pstate = false;
                                    win = false;
                                    break ballz;
                                }
                            }
                        }
                        d.bulletsai[x][0] -= rate;
                    } else {
                        d.bulletsai[x][1] = 0;
                    }
                    if (d.bcount != 0) {
                        d.bcount++;
                    }
                    if (d.bcount == 4 * d.numb) {
                        d.bcount = 0;
                    }
                }
                d.repaint();
            } catch (InterruptedException ex) {
            }
        }
        if (win) {
            JOptionPane.showMessageDialog(mainframe, "You win!");
        } else {
            JOptionPane.showMessageDialog(mainframe, "You lose.");
        }
        JOptionPane.showMessageDialog(mainframe, "Game over.");
        System.exit(0);
    }

    class KeyPress extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {//on space press jump
            if (e.getKeyCode() == 32) {
                if (d.pjump == false) {//if not yet jumping it allows you to jump
                    d.pjump = true;
                    d.pjcount = 1;
                }
            }
            switch (e.getKeyCode()) {
                case 10:
                    loop:
                    for (int x = 0; x < d.numb; x++) {//fires bullets on enter press
                        if ((d.bstatep[x] == false) && (count == 0)) {
                            d.bstatep[x] = true;
                            d.bstatep2[x] = true;
                            d.bulletsp[x][0] = d.x + 34;
                            d.bulletsp[x][1] = d.y;
                            count = 1;
                            break loop;
                        }
                    }
                    break;
            }
        }
    }

    private class ButtonHandler implements ActionListener {//difficulty level actionlistener

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Easy")) {
                d.diff = 1;
            } else if (e.getActionCommand().equals("Hard")) {
                d.diff = 2;
            } else if (e.getActionCommand().equals("Impossible")) {
                d.diff = 3;
            }
        }
    }
}
