package RGB;

import java.awt.image.BufferedImage;
import modelo.ProcesarImagen;

public class ExtraerCanal implements ProcesarImagen {
    private int [][]data = new int [3][256];
    @Override
    public BufferedImage[] convertir(BufferedImage imagen) {
        int ancho = imagen.getWidth();
        int alto = imagen.getHeight();
        BufferedImage[] imgTemp = new BufferedImage[4];
        imgTemp[0] = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        imgTemp[1] = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        imgTemp[2] = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        imgTemp[3] = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int pixel = imagen.getRGB(x, y);
                int r = (pixel & 0x00ff0000) >> 16;
                int g = (pixel & 0x0000ff00) >> 8;
                int b = pixel & 0x000000ff;
                int pixelR = (r << 16) | (0 << 8) | 0;
                int pixelG = (0 << 16) | (g << 8) | 0;
                int pixelB = (0 << 16) | (0 << 8) | b;
                
                
                data[0][r]++;
                data[1][g]++;
                data[2][b]++;
                imgTemp[0].setRGB(x, y, pixelR);
                imgTemp[1].setRGB(x, y, pixelG);
                imgTemp[2].setRGB(x, y, pixelB);
                imgTemp[3].setRGB(x, y, pixel);
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
