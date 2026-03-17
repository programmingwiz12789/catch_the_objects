/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catchtheobjects;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Bryan AW
 */
public class JPanel extends javax.swing.JPanel {

    /**
     * Creates new form JPanel
     */
    
    Rectangle catcher, box;
    
    String text;
    
    Drops[][] drops;
    
    int[] cnt;
    
    int mid, h, score, b;
    
    boolean gameover;
    
    public JPanel() {
        
        initComponents();
        
        mid = (int) getPreferredSize().getWidth() / 2;
        h = (int) getPreferredSize().getHeight();
        int x = mid - 50;
        int y = h - 20;
        
        catcher = new Rectangle(x, y, 100, 20);
        
        box = new Rectangle(mid - 40, (h / 2) - 10, 90, 20);
        
        text = "Press S to start";
        
        drops = new Drops[4][1000];
        
        cnt = new int[4];
        
        for (int i = 0; i < 4; i++){
            
            for (int j = 0; j < 1000; j++){
                
                drops[i][j] = null;
                
            }
            
        }
        
        score = 0;
        
        gameover = false;
        
        b = 0;
        
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (b == 0){
                    if (e.getKeyCode() == KeyEvent.VK_S){
                        box = null;
                        text = "";
                        repaint();
                        b = 1;
                        t.start();
                    }
                }
                else if (b == 1){
                    if (e.getKeyCode() == KeyEvent.VK_P){
                        box = new Rectangle(mid - 40, (h / 2) - 10, 110, 20);
                        text = "Press R to resume";
                        repaint();
                        b = -1;
                        t.stop();
                    }
                }
                else {
                    if (e.getKeyCode() == KeyEvent.VK_R){
                        box = null;
                        text = "";
                        repaint();
                        b = 1;
                        t.start();
                    }
                }
            }
        });
        
        this.setFocusable(true);
        
    }
    
    int timerB = -1, timerW = -1, timerM = -1, ctr = 1;
    
    private final Timer t = new Timer(5, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ctr == 35){
                ctr = 1;
                Random rnd = new Random();
                int col = rnd.nextInt(4);
                int w = (int) getPreferredSize().getWidth();
                int x = w * (col + 1) / 4;
                int prob = rnd.nextInt(100);
                if (prob >= 0 && prob <= 89){
                    drops[col][cnt[col]] = new Drops(x, 0, 30, 30, Color.red);
                }
                else if (prob == 90){
                    drops[col][cnt[col]] = new Drops(x, 0, 30, 30, Color.blue);
                }
                else if (prob == 91){
                    drops[col][cnt[col]] = new Drops(x, 0, 30, 30, Color.white);
                }
                else if (prob >= 92 && prob <= 93){
                    drops[col][cnt[col]] = new Drops(x, 0, 30, 30, Color.magenta);
                }
                else {
                    if (col > 0 && col < 3){
                        if (drops[col - 1][0] != null && drops[col + 1][0] != null){
                            if (drops[col - 1][0].getColor() == Color.black
                                || drops[col + 1][0].getColor() == Color.black){
                                drops[col][cnt[col]] = new Drops(x, 0, 30, 30, Color.red);
                            }
                            else {
                                drops[col][cnt[col]] = new Drops(x, 0, 30, 30, Color.black);
                            }
                        }
                        else {
                            drops[col][cnt[col]] = new Drops(x, 0, 30, 30, Color.black);
                        }
                    }
                    else if (col > 0){
                        if (drops[col - 1][0] != null){
                            if (drops[col - 1][0].getColor() == Color.black){
                                drops[col][cnt[col]] = new Drops(x, 0, 30, 30, Color.red);
                            }
                            else {
                                drops[col][cnt[col]] = new Drops(x, 0, 30, 30, Color.black);
                            }
                        }
                        else {
                            drops[col][cnt[col]] = new Drops(x, 0, 30, 30, Color.black);
                        }
                    }
                    else if (col < 3){
                        if (drops[col + 1][0] != null){
                            if (drops[col + 1][0].getColor() == Color.black){
                                drops[col][cnt[col]] = new Drops(x, 0, 30, 30, Color.red);
                            }
                            else {
                                drops[col][cnt[col]] = new Drops(x, 0, 30, 30, Color.black);
                            }
                        }
                        else {
                            drops[col][cnt[col]] = new Drops(x, 0, 30, 30, Color.black);
                        }
                    }
                }
                cnt[col]++;
            }
            int i = 0;
            while (gameover == false && i < 4){
                int j = 0;
                while (gameover == false && j < cnt[i]){
                    if (drops[i][j] != null){
                        drops[i][j].y++;
                        if (drops[i][j].intersects(catcher)){
                            if (drops[i][j].getColor() == Color.red){
                                score += 30;
                                jLabel1.setText("Score = " + score);
                                for (int k = 0; k < cnt[i] - 1; k++){
                                    drops[i][k] = drops[i][k + 1];
                                }
                                drops[i][cnt[i] - 1] = null;
                                cnt[i]--;
                            }
                            else if (drops[i][j].getColor() == Color.blue){
                                timerB = 0;
                                for (int k = 0; k < cnt[i] - 1; k++){
                                    drops[i][k] = drops[i][k + 1];
                                }
                                drops[i][cnt[i] - 1] = null;
                                cnt[i]--;
                            }
                            else if (drops[i][j].getColor() == Color.white){
                                timerW = 0;
                                for (int k = 0; k < cnt[i] - 1; k++){
                                    drops[i][k] = drops[i][k + 1];
                                }
                                drops[i][cnt[i] - 1] = null;
                                cnt[i]--;
                            }
                            else if (drops[i][j].getColor() == Color.magenta){
                                if (timerW == -1){
                                    timerM = 0;
                                }
                                for (int k = 0; k < cnt[i] - 1; k++){
                                    drops[i][k] = drops[i][k + 1];
                                }
                                drops[i][cnt[i] - 1] = null;
                                cnt[i]--;
                            }
                            else {
                                if (timerW == -1){
                                    gameover = true;
                                }
                                else {
                                    for (int k = 0; k < cnt[i] - 1; k++){
                                        drops[i][k] = drops[i][k + 1];
                                    }
                                    drops[i][cnt[i] - 1] = null;
                                    cnt[i]--;
                                }
                            }
                        }
                        else if (drops[i][j].y > getPreferredSize().getHeight()){
                            if (drops[i][j].getColor() == Color.red){
                                score = Math.max(0, score - 1);
                                jLabel1.setText("Score = " + score);
                            }
                            for (int k = 0; k < cnt[i] - 1; k++){
                                drops[i][k] = drops[i][k + 1];
                            }
                            drops[i][cnt[i] - 1] = null;
                            cnt[i]--;
                        }
                    }
                    repaint();
                    j++;
                }
                i++;
            }
            
            if (gameover == false){
                
                if (timerB == 0){
                    t.setDelay(15);
                }
                if (timerB != -1){
                    timerB++;
                }
                if (timerB == 300){
                    t.setDelay(5);
                    timerB = -1;
                }
                
                if (timerW == 0){
                    setBackground(new Color(102, 255, 102));
                    timerM = -1;
                    int w = (int) getPreferredSize().getWidth();
                    catcher.width = w;
                }
                if (timerW != -1){
                    timerW++;
                }
                if (timerW == 500){
                    catcher.width = 100;
                    timerW = -1;
                }
                
                if (timerM == 0){
                    setBackground(Color.black);
                }
                if (timerM != -1){
                    timerM++;
                }
                if (timerM == 1000){
                    setBackground(new Color(102, 255, 102));
                    timerM = -1;
                }
                
            }
            
            if (gameover){
                t.stop();
                int option = JOptionPane.showConfirmDialog(null, "Game Over !\nPlay again ?");
                if (option == JOptionPane.YES_NO_OPTION){
                    Restart();
                }
                else {
                    Frame.getFrames()[0].dispose();
                }
            }
            
            ctr++;
            
        }
    });
    
    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        
        g.setColor(Color.YELLOW);
        g.fillRect(catcher.x, catcher.y, catcher.width, catcher.height);
        
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < cnt[i]; j++){
                if (drops[i][j] != null){
                    g.setColor(drops[i][j].getColor());
                    g.fillOval(drops[i][j].x, drops[i][j].y, drops[i][j].width, drops[i][j].height);
                }
            }
        }
        
        if (box != null && text.equals("") == false){
        
            if (text.equals("Press S to start")){
                
                g.setColor(Color.GRAY);
                g.fillRect(box.x - 5, box.y, box.width, box.height);
                g.setColor(Color.BLACK);
                
                int Lx = box.x, Rx = box.x + box.width - 93;
                int Uy = box.y, Dy = box.y + box.height + 8;
                int Cx = (Lx + Rx) / 2, Cy = (Uy + Dy) / 2;
                g.drawString(text, Cx, Cy);
                
            }
            else {
                
                g.setColor(Color.GRAY);
                g.fillRect(box.x - 15, box.y, box.width, box.height);
                g.setColor(Color.BLACK);
                
                int Lx = box.x, Rx = box.x + box.width - 133;
                int Uy = box.y, Dy = box.y + box.height + 8;
                int Cx = (Lx + Rx) / 2, Cy = (Uy + Dy) / 2;
                g.drawString(text, Cx, Cy);
                
            }
            
        }
        
    }
    
    public void Restart(){
        
        setBackground(new Color(102, 255, 102));
        
        jLabel1.setText("Score = 0");
        
        mid = (int) getPreferredSize().getWidth() / 2;
        h = (int) getPreferredSize().getHeight();
        int x = mid - 50;
        int y = h - 20;
        
        catcher = new Rectangle(x, y, 100, 20);
        
        box = new Rectangle(mid - 40, (h / 2) - 10, 90, 20);
        
        text = "Press S to start";
        
        drops = new Drops[4][1000];
        
        cnt = new int[4];
        
        for (int i = 0; i < 4; i++){
            
            for (int j = 0; j < 1000; j++){
                
                drops[i][j] = null;
                
            }
            
        }
        
        score = 0;
        
        gameover = false;
        
        b = 0;
        
        timerB = -1;
        
        timerW = -1;
        
        timerM = -1;
        
        ctr = 1;
        
        t.setDelay(5);
        
        repaint();
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(102, 255, 102));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setText("Score = 0");
        jLabel1.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(277, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(260, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        
        if (text.equals("Press R to resume") == false){
        
            int maxX = (int) getPreferredSize().getWidth() - catcher.width;

            catcher.x = Math.max(0, Math.min(evt.getX() - catcher.width / 2, maxX));

            repaint();
            
        }
        
    }//GEN-LAST:event_formMouseDragged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
