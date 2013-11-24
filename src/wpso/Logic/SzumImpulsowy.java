/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.util.Random;

/**
 *
 * @author Pawel
 */
public class SzumImpulsowy {

    public static BufferedImage transformuj(BufferedImage img, double pieprz, double sol) {
        BufferedImage out = null;

        double pstwo;
        int width;
        int height;


        if (img==null) {
            width=300;
            height=300;
        } else {
            width=img.getWidth();
            height=img.getHeight();
        }
        int poziomy[] = new int[256];


        int mapaPixeli[] = new int[width*height];

        PixelGrabber pg = new PixelGrabber(img, 0,0,width,height, mapaPixeli, 0, width);
        try {
            pg.grabPixels();
        } catch (InterruptedException e){return null;}

        int dane[] = new int[mapaPixeli.length*3];


        for (int i=0;i<mapaPixeli.length;i++) {
            int pixel = mapaPixeli[i];
            Color c = new Color(pixel);
            int r = c.getRed();
            int g = c.getGreen();
            int b = c.getBlue();


            dane[i*3+0] = r;
            dane[i*3+1] = g;
            dane[i*3+2] = b;

            pstwo = Math.random();
            if(pstwo<=pieprz) {
                dane[i*3+0] = 0;
                dane[i*3+1] = 0;
                dane[i*3+2] = 0;
            }

            pstwo = Math.random();
            if(pstwo<=sol) {
                dane[i*3+0] = 255;
                dane[i*3+1] = 255;
                dane[i*3+2] = 255;
            }
        }

        //// Tworzenie nowego obrazka

        Color nowePixele[] = new Color[mapaPixeli.length];

        for(int i=0; i<nowePixele.length; i++) {
            nowePixele[i] = new Color(dane[i*3+0],dane[i*3+1],dane[i*3+2]);
        }

        out = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        int x=0,y=-1;
        for(int i=0; i<nowePixele.length; i++){

            if((i%width)==0){
                x=0;
                y++;
            }

            out.setRGB(x++, y, nowePixele[i].getRGB());

        }


        return out;

    }

}
