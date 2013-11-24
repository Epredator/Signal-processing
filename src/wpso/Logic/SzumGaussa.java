/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

// http://www.dspguru.com/dsp/howtos/how-to-generate-white-gaussian-noise


package Logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

/**
 *
 * @author Pawel
 */
public class SzumGaussa {

    public static BufferedImage transformuj(BufferedImage img, int srednia, int wariancja) {
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
            poziomy[i] = Trans(i,srednia,wariancja);
        //    System.out.println("Przeksztalcilem "+i+" na "+poziomy[i]);
        }

        int mapaPixeli[] = new int[width*height];

        PixelGrabber pg = new PixelGrabber(img, 0,0,width,height, mapaPixeli, 0, width);
        try {
            pg.grabPixels();
        } catch (InterruptedException e){return null;}

        int dane[] = new int[mapaPixeli.length*3];

        double pierWar = Math.sqrt(wariancja);

        for (int i=0;i<mapaPixeli.length;i++) {
            int pixel = mapaPixeli[i];
            Color c = new Color(pixel);
            int r = c.getRed();
            int g = c.getGreen();
            int b = c.getBlue();
            
            int genR = (Math.random() < 0.5)? -1 : 1;
            int genG = (Math.random() < 0.5)? -1 : 1;
            int genB = (Math.random() < 0.5)? -1 : 1;

            int szumR = (int) (0.5 + boxMuller(Math.random(), Math.random(), srednia, pierWar));
            int szumG = (int) (0.5 + boxMuller(Math.random(), Math.random(), srednia, pierWar));
            int szumB = (int) (0.5 + boxMuller(Math.random(), Math.random(), srednia, pierWar));


            dane[i*3+0] = Math.max(0, Math.min(255, r + genR * szumR));
            dane[i*3+1] = Math.max(0, Math.min(255, g + genG * szumG));
            dane[i*3+2] = Math.max(0, Math.min(255, b + genB * szumB));
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

    private static double boxMuller(double u1, double u2, double sr, double pw) {

        return ((Math.sqrt(-2 * Math.log(u2)) * Math.cos(2 * Math.PI * u1) * pw) + sr);
    }

    private static int Trans(int grayscale,int sr, int war) {

        if(grayscale<0)grayscale+=255;
        double pix2=grayscale;
        pix2/=255;
        double pixV = (1/Math.pow(2*3.14*Math.pow(war,0.5), 0.5))*
                Math.exp((-Math.pow(pix2-sr, 2))/(2*war));
        pixV*=255;
        return (int)Math.round(pixV);
    }

}
