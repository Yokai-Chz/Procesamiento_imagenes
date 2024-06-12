package OpAritmeticas;

import java.awt.image.BufferedImage;
import modelo.OperarImagen;

public class Division implements OperarImagen {

    private int[] data = new int[256];

    @Override
    public BufferedImage operar(BufferedImage imagen, BufferedImage imagen2) {
        int ancho = imagen.getWidth();
        int alto = imagen.getHeight();
        BufferedImage imgTemp = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        int pixel1 = 0;
        int pixel2 = 0;
        for (int yImg = 0; yImg < alto; yImg++) {
            for (int xImg = 0; xImg < ancho; xImg++) {
                pixel1 = imagen.getRGB(xImg, yImg);
                int px1 = pixel1 & 0x000000ff;

                pixel2 = imagen2.getRGB(xImg, yImg);
                int px2 = pixel2 & 0x000000ff;
                
                double b = px2==0 ? 0.001 : (double)px2;
                
                double pxR = (double)px1 / (double)b;
                
                if(pxR>255) pxR =255;
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

}
