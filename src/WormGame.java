import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;


public class WormGame {
    int boardWidth = 700;
    int boardHeight = 400;
    JFrame frame = new JFrame("hi this is worm game");

    //background
    ImageIcon sky1Img = new ImageIcon(getClass().getResource("/images/sky 1.png"));
    ImageIcon sky2Img = new ImageIcon(getClass().getResource("/images/sky 2.png"));
    ImageIcon evilSkyImg = new ImageIcon(getClass().getResource("/images/evil background.png"));
    JLabel sky1 = new JLabel(sky1Img);
    JLabel sky2 = new JLabel(sky2Img);
    int skySizeWidth = sky1Img.getIconWidth();
    int skySizeHeight = sky1Img.getIconHeight();

    //worm
    ImageIcon wormFlatImg = new ImageIcon(getClass().getResource("/images/worm flat.png"));
    ImageIcon wormSquirmImg = new ImageIcon(getClass().getResource("/images/worm squirm.png"));
    ImageIcon wormFlatEvilImg = new ImageIcon(getClass().getResource("/images/worm flat EVIL.png"));
    ImageIcon wormSquirmEvilImg = new ImageIcon(getClass().getResource("/images/worm squirm EVIL.png"));
    JLabel worm = new JLabel(wormFlatImg);

    //panel all graphics will be on
    Panel container = new Panel();

    //text
    JLabel instructions = new JLabel("<html>hi ur a worm<br/>click the mouse to move<br/>press space to change intensity</html>");

    //music
    AudioInputStream intenseStream;
    AudioInputStream happyStream;

    Clip backgroundClip;

    //intensity mode
    boolean intenseMode = false;

    WormGame(){
        frame.setSize(boardWidth, boardHeight);
		frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //x button closes program
        frame.setLayout(new BorderLayout());

        container.setLayout(null);
        frame.add(container);

        //adds sky
        container.add(sky1);
        container.add(sky2);
       
        sky1.setBounds(0, 0, skySizeWidth, skySizeHeight);
        sky2.setBounds(700, 0, skySizeWidth, skySizeHeight);
        
        //adds worms
        worm.setBounds(150, 160, wormFlatImg.getIconWidth(), wormFlatImg.getIconHeight());
        container.add(worm);
        container.setComponentZOrder(worm, 0);
       
        //adds text
        instructions.setBounds(10, 0, 200, 100);
        container.add(instructions);
        container.setComponentZOrder(instructions, 0);

        //sounds
        playBackgroundMusic(false);

        onMouseClick();
        onKeyClick();
        

        frame.setVisible(true);

        // Make container focusable and request focus
        container.setFocusable(true);
        container.requestFocusInWindow();
    }

    void onMouseClick(){
        //moves background and worm on mouse click
        container.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //moves sky
                sky1.setLocation(sky1.getX() - 10, sky1.getY());
                sky2.setLocation(sky2.getX() - 10, sky2.getY());

                if((sky1.getX() == 0)){
                    sky2.setLocation(700, sky2.getY());
                }

                else if(sky2.getX() == 0){
                    sky1.setLocation(700, sky1.getY());
                }

                //changes images to be evil or happy
                changeMode();
                
                container.repaint();
                container.revalidate();
            }
        });
    }

    void onKeyClick(){
        container.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e){
                if(e.getKeyChar() == ' '){
                    //if intense mode is already on, turn it off and play happy music
                    if(intenseMode) intenseMode = false;
                    //if intense mode is off, turn it on and play intense music
                    else intenseMode = true;

                    changeMode();
                    stopAudio();
                    playBackgroundMusic(intenseMode);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

    }

    void changeMode(){
        if(intenseMode){
                sky1.setIcon(evilSkyImg);
                sky2.setIcon(evilSkyImg);

                instructions.setForeground(Color.WHITE);

                if(worm.getIcon() == wormFlatEvilImg) worm.setIcon(wormSquirmEvilImg);
                else worm.setIcon(wormFlatEvilImg);
            }
            else{
                sky1.setIcon(sky1Img);
                sky2.setIcon(sky2Img);

                instructions.setForeground(Color.BLACK);

                if(worm.getIcon() == wormFlatImg) worm.setIcon(wormSquirmImg);
                else worm.setIcon(wormFlatImg);
            }
    }

    void playBackgroundMusic(boolean intense){
        try{

            intenseStream = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/172027757680011.wav"));
            happyStream = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/Brick and Mortar.wav"));
            
            backgroundClip = AudioSystem.getClip();

            //if intense mode is on
            if(intense) backgroundClip.open(intenseStream);
            //if it is off
            else backgroundClip.open(happyStream);

            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop music 

        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    //stops current audio playing so it can be switched
    public void stopAudio() {
        if ((backgroundClip != null) && (backgroundClip.isRunning())) {
            backgroundClip.stop(); // Stop the currently playing clip
            backgroundClip.setFramePosition(0);
        }
    }

    public static void main(String[] args) {
        new WormGame();
    }
}

    

