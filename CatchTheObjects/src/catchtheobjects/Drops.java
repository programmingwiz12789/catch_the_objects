/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catchtheobjects;

import java.awt.Rectangle;
import java.awt.Color;

/**
 *
 * @author Bryan AW
 */
public class Drops extends Rectangle {
    
    private Color color;
    
    public Drops(int x, int y, int width, int height, Color color){
        
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        
    }

    public Color getColor(){
        
        return color;
        
    }

    public void setColor(Color color){
        
        this.color = color;
        
    }
    
}
