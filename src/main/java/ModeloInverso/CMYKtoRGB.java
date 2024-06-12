package ModeloInverso;

import java.awt.image.BufferedImage;
import modelo.ProcesarImagen;

public class CMYKtoRGB implements  ProcesarImagen{
    private int [][]data = new int [3][256];
    
    
    @Override
    public BufferedImage[] convertir(BufferedImage imagen)  {
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
                int c = (pixel & 0x00ff0000) >> 16;
                int m = (pixel & 0x0000ff00) >> 8;
                int y = pixel & 0x000000ff;

                // Conversión de RGB a CMYK
                int r = 255 - c;
                int g = 255 - m ;
                int b = 255 - y ;
                
                int pixelR = (r << 16);
                int pixelG = (g << 8);
                int pixelB =  b;
                pixel = ((int)r << 16) | ((int)g << 8) | b;
                
                data[0][r]++;
                data[1][g]++;
                data[2][b]++;
                imgTemp[0].setRGB(xImg, yImg, pixelR);
                imgTemp[1].setRGB(xImg, yImg, pixelG);
                imgTemp[2].setRGB(xImg, yImg, pixelB);
                imgTemp[3].setRGB(xImg, yImg, pixel);
            }
        }

        return imgTemp;
    }

    @Override
    public int [][] extraerData(){
        return this.data;
    }

    @Override
    public BufferedImage[] convertir(BufferedImage imagen, int[] umbral) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
