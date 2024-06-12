package ModeloInverso;

import java.awt.image.BufferedImage;
import modelo.ProcesarImagen;

public class YIQtoRGB implements ProcesarImagen {

    private int[][] data = new int[3][256];

    @Override
    public BufferedImage[] convertir(BufferedImage imagen) {
        int ancho = imagen.getWidth();
        int alto = imagen.getHeight();
        BufferedImage[] imgTemp = new BufferedImage[4];
        imgTemp[0] = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        imgTemp[1] = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        imgTemp[2] = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        imgTemp[3] = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        int pixel = 0;
        for (int yImg = 0; yImg < alto; yImg++) {
            for (int xImg = 0; xImg < ancho; xImg++) {
                pixel = imagen.getRGB(xImg, yImg);
                int yP = (pixel & 0x00ff0000) >> 16;
                int iP = (pixel & 0x0000ff00) >> 8;
                int qP = pixel & 0x000000ff;
                
                double y= yP/255.0;
                double i= ((iP*2*0.5957)/255.0)-0.5957;
                double q= ((qP*2*0.5226)/255.0)-0.5226;

                double r = (y + ( 0.956 * i) + ( 0.621 * q)) * 255;
                double g = (y + (-0.272 * i) + (-0.647 * q)) * 255;
                double b = (y + (-1.105 * i) + ( 1.702 * q)) * 255;

                if (r < 0 ) {r=0;} else if(r>255){r=255;}
                if (g < 0 ) {g=0;} else if(g>255){g=255;}
                if (b < 0 ) {b=0;} else if(b>255){b=255;}

                int pixelQ = (255 << 24) | ((int) r << 16) | ((int) 0 << 8) | (int) 0;
                int pixelI = (255 << 24) | ((int) 0 << 16) | ((int) g << 8) | (int) 0;
                int pixelY = (255 << 24) | ((int) 0 << 16) | ((int) 0 << 8) | (int) b;
                pixel = (255 << 24) | ((int) (r) << 16) | ((int) (g) << 8) | (int) (b);

                data[0][(int) (r)]++;
                data[1][(int) g]++;
                data[2][(int) b]++;
                imgTemp[0].setRGB(xImg, yImg, pixelY);
                imgTemp[1].setRGB(xImg, yImg, pixelI);
                imgTemp[2].setRGB(xImg, yImg, pixelQ);
                imgTemp[3].setRGB(xImg, yImg, pixel);
            }
        }
        return imgTemp;
    }

    @Override
    public int[][] extraerData() {
        return this.data;
    }

    @Override
    public BufferedImage[] convertir(BufferedImage imagen, int[] umbral) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
