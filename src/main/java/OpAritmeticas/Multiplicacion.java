package OpAritmeticas;

import java.awt.image.BufferedImage;
import modelo.OperarImagen;

public class Multiplicacion implements OperarImagen {

    private int[] data = new int[256];

    @Override
    public BufferedImage operar(BufferedImage imagen, BufferedImage imagen2) {
        int ancho = imagen.getWidth();
        int alto = imagen.getHeight();
        BufferedImage imgTemp = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        int pixel1 = 0;
        int pixel2 = 0;
        int max = max(imagen, imagen2);
        int min = min(imagen, imagen2);
        for (int yImg = 0; yImg < alto; yImg++) {
            for (int xImg = 0; xImg < ancho; xImg++) {
                pixel1 = imagen.getRGB(xImg, yImg);
                int px1 = pixel1 & 0x000000ff;

                pixel2 = imagen2.getRGB(xImg, yImg);
                int px2 = pixel2 & 0x000000ff;
                
                int px3 = (px1 * px2) ;
                double pxR = ( px3 - min ) * (255.0/(max-min));
                //System.out.println(px1 +" "+ px2);
                //System.out.println(pxR);
                data[(int)pxR]++;

                pixel1 = (255 << 24) | ((int)pxR << 16) | ((int)pxR << 8) | (int)pxR;

                imgTemp.setRGB(xImg, yImg, pixel1);
            }
        }
        return imgTemp;
    }

    @Override
    public int[] extraerData() {
        return this.data;
    }
    
    private int max(BufferedImage imagen, BufferedImage imagen2){
        int ancho = imagen.getWidth();
        int alto = imagen.getHeight();
        int pixel1=0;
        int pixel2=0;
        int max = 0;
        for (int yImg = 0; yImg < alto; yImg++) {
            for (int xImg = 0; xImg < ancho; xImg++) {
                pixel1 = imagen.getRGB(xImg, yImg);
                int px = pixel1 & 0x000000ff;
                pixel2 = imagen2.getRGB(xImg, yImg);
                int px2 = pixel2 & 0x000000ff;
                
                if((px*px2)>max){
                    max=(px*px2);
                }
            }
        }
        return max;
    }
    
    private int min(BufferedImage imagen, BufferedImage imagen2){
        int ancho = imagen.getWidth();
        int alto = imagen.getHeight();
        int pixel1=0;
        int pixel2=0;
        int min = 256;
        for (int yImg = 0; yImg < alto; yImg++) {
            for (int xImg = 0; xImg < ancho; xImg++) {
                pixel1 = imagen.getRGB(xImg, yImg);
                int px = pixel1 & 0x000000ff;
                pixel2 = imagen2.getRGB(xImg, yImg);
                int px2 = pixel2 & 0x000000ff;
                if((px*px2)<min){
                    min=(px*px2);
                }
            }
        }
        return min;
    }
}
