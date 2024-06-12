package ModeloColor;

import java.awt.image.BufferedImage;
import modelo.ProcesarImagen;

public class RGBtoCMYK implements  ProcesarImagen{
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
                int rojo = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >> 8;
                int azul = pixel & 0x000000ff;

                // Conversión de RGB a CMYK
                int c = 255 - rojo;
                int m = 255 - verde ;
                int y = 255 - azul ;
                
                int pixelC = (0 << 16) | ((int)m << 8) | (int)y;
                int pixelM = ((int)c << 16) | (0 << 8) | (int)y;
                int pixelY = ((int)c << 16) | ((int)m << 8) | 0;
                pixel = (255 << 24) | ((int) (c) << 16) | ((int) (m) << 8) | (int) (y);
                
                data[0][c]++;
                data[1][m]++;
                data[2][y]++;
                imgTemp[0].setRGB(xImg, yImg, pixelC);
                imgTemp[1].setRGB(xImg, yImg, pixelM);
                imgTemp[2].setRGB(xImg, yImg, pixelY);
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
