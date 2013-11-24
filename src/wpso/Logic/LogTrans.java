/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

/**
 *
 * @author Cwiku
 */
public class LogTrans {

    public static BufferedImage transformuj(BufferedImage img, int con) {

        BufferedImage out = null;

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
        for(int i=0;i<=255;i++) {
            poziomy[i] = logTrans(i,con);
        //    System.out.println("Przeksztalcilem "+i+" na "+poziomy[i]);
        }

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
            dane[i*3+0] = poziomy[r];
            dane[i*3+1] = poziomy[g];
            dane[i*3+2] = poziomy[b];
        }

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

    private static int logTrans(int grayscale,int con) {
        int result=0;
        double gs = grayscale;

        result =((int)Math.round((con * Math.log(1+gs))));

        if (result > 255) {
            result=255;
        } else if (result < 0) {
            result=0;
        }


        return result;
    }



}
