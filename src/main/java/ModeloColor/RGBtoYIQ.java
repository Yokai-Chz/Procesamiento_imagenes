package ModeloColor;

import java.awt.image.BufferedImage;
import modelo.ProcesarImagen;

public class RGBtoYIQ implements  ProcesarImagen{
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

                double r = rojo / 255.0;
                double g = verde / 255.0;
                double b = azul / 255.0;

                double y = 0.299 * r + 0.587 * g + 0.114 * b;
                double i = (0.596 * r) - (0.274 * g) - (0.322 * b);
                double q = 0.211 * r - 0.523 * g + 0.312 * b;

                double q_norm = ((q + 0.5226)*255) / (2*0.5226);
                double i_norm = ((i + 0.5957)*255) / (2*0.5957);
                
                int pixelQ = (255 << 24) | ((int) q_norm << 16) | ((int) q_norm << 8) | (int) q_norm;
                int pixelI = (255 << 24) | ((int) i_norm << 16) | ((int) i_norm << 8) | (int) i_norm;
                int pixelY = (255 << 24) | ((int) (y*255) << 16) | ((int) (y*255) << 8) | (int) (y*255);
                pixel = (255 << 24) | ((int) (y*255) << 16) | ((int) (i_norm) << 8) | (int) (q_norm);

                data[0][(int)(y*255)]++;
                data[1][(int)q_norm]++;
                data[2][(int)(i_norm)]++;
                imgTemp[0].setRGB(xImg, yImg, pixelY);
                imgTemp[1].setRGB(xImg, yImg, pixelI);
                imgTemp[2].setRGB(xImg, yImg, pixelQ);
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
