package CS3_SY1314;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.Random;

public class Display extends Panel {
//all variables named for convenience
    int ground = 280;
    int x = 15, y = ground, numb = 3;
    int aix = 560, aiy = ground;
    int bulletsp[][] = new int[numb][2];
    int pcount[] = new int[numb];
    int bulletsai[][] = new int[numb][2];
    int aicount[] = new int[numb];
    boolean bstatep[] = {false, false, false, false, false, false, false, false, false, false};
    boolean bstatep2[] = {false, false, false, false, false, false, false, false, false, false};
    boolean bstateai[] = {false, false, false, false, false, false, false, false, false, false};
    boolean bstateai2[] = {false, false, false, false, false, false, false, false, false, false};
    int plife = 5, ailife = 5;
    boolean pstate = true, aistate = true;
    boolean pjump = false, aijump = false;
    int pyheight = 0, aiyheight = 0;
    int pjcount = 0, aijcount = 0;
    boolean sstate = false;
    int shields = 5;
    int scount = 0;
    int sdecay = 0;
    int diff = 1;

    public static void main(String[] args) {
        Display d = new Display();//display
        d.setSize(600, 400);
    }

    @Override
    public void paint(Graphics mom) {//buffered image loads the image
        BufferedImage robot = null;
        try {
            robot = ImageIO.read(new File("src/OOP/robot.jpg"));
        } catch (IOException e) {
        }
        BufferedImage life = null;
        try {
            life = ImageIO.read(new File("src/OOP/life.png"));
        } catch (IOException e) {
        }
        BufferedImage shield = null;
        try {
            shield = ImageIO.read(new File("src/OOP/shield.png"));
        } catch (IOException e) {
        }
        if (pstate == true) {
            mom.drawImage(robot, x, y, 34, 68, this);//draw robot
        }
        BufferedImage aibot = null;
        try {
            aibot = ImageIO.read(new File("src/OOP/robot.jpg"));
        } catch (IOException e) {
        }
        if (aistate == true) {
            mom.drawImage(aibot, aix, aiy, -34, 68, this);//draw ai
        }
        if (sstate == true) {
            mom.drawImage(shield, aix - 44, aiy, 2, 68, this);//shield, if needed
        }
        for (int x = 1; x <= ailife; x++) {
            mom.drawImage(life, 570 + x * -5, 10, -3, 20, this);//life bar
        }
        if (diff == 3) {
            for (int x = 1; x <= shields; x++) {
                mom.drawImage(shield, 540 + x * -5, 10, -3, 20, this);//shield bar
            }
        }
        for (int x = 1; x <= plife; x++) {
            mom.drawImage(life, 20 + x * 5, 10, 3, 20, this);//player life bar
        }
        BufferedImage bulletn = null;
        try {
            bulletn = ImageIO.read(new File("src/OOP/Bulletn.jpg"));//bulletpic
        } catch (IOException e) {
        }
        for (int x = 0; x < numb; x++) {
            if (bstatep[x] == true) {
                mom.drawImage(bulletn, bulletsp[x][0], bulletsp[x][1], 34, 34, this);//bullets
            }
        }
        for (int x = 0; x < numb; x++) {
            if (bstateai[x] == true) {
                mom.drawImage(bulletn, bulletsai[x][0], bulletsai[x][1], -34, 34, this);//bullets
            }
        }
    }
    int direction = 1;
    int rate = 400 / 2;
    int bcount = 0;

    public void AIFire() {
        Random rand = new Random();//fires randomly
        if (rand.nextInt(100) < 1) {
            loop:
            for (int x = 0; x < numb; x++) {//checks for used bullets
                if ((bstateai[x] == false) && (bcount == 0)) {
                    bstateai[x] = true;
                    bstateai2[x] = true;
                    bulletsai[x][0] = aix - 34;
                    bulletsai[x][1] = aiy;
                    bcount = 1;
                    break loop;
                }
            }
        }
    }

    public void AIFireEX() {//more chances of firing
        Random rand = new Random();
        if (rand.nextInt(100) < 20) {
            loop:
            for (int x = 0; x < numb; x++) {
                if ((bstateai[x] == false) && (bcount == 0)) {
                    bstateai[x] = true;
                    bstateai2[x] = true;
                    bulletsai[x][0] = aix - 34;
                    bulletsai[x][1] = aiy;
                    bcount = 1;
                    break loop;
                }
            }
        }
    }

    private void AICombo() {
    }

    public void AICode(int level, int xpt) {//calls the dodge script
        switch (level) {
            case 1:
                Easy();
                break;
            case 2:
                Easy();
                break;
            case 3:
                Easy();
                break;
        }
    }

    private int Check() {
        for (int x = 0; x < numb; x++) {
            if ((bulletsp[x][0] > 400) && (bulletsp[x][1] > 280 - 34)) {//checks if the bullet is near
                return bulletsp[x][1];
            }
        }
        return -1;
    }

    private void Easy() {
        int dodgethis = Check();
        if (dodgethis > 280 - 34) {//dodges the near bullet
            if (aijump != true) {
                aijump = true;
                aijcount = 1;
            }
        }
    }
}
