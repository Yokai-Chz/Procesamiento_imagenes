package OpLogicas;

import java.awt.image.BufferedImage;
import modelo.OperarImagen;

public class And implements OperarImagen {

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
                
                int pxR = px1 & px2;
                //System.out.println(px1 +" "+ px2);
                //System.out.println(pxR);
                data[pxR]++;

                pixel1 = (255 << 24) | (pxR << 16) | (pxR << 8) | pxR;

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
