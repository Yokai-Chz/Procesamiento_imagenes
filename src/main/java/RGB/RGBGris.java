package RGB;

import java.awt.image.BufferedImage;
import modelo.ProcesarImagen;

public class RGBGris implements ProcesarImagen {
    private int [][]data = new int [4][256];
    @Override
    public BufferedImage[] convertir(BufferedImage imagen) {
        clearArray();
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
                int prom = (r+g+b)/3;
                int pixelR = (r << 16) | (r << 8) | r;
                int pixelG = (g << 16) | (g << 8) | g;
                int pixelB = (b << 16) | (b << 8) | b;
                int pixelP = ((int)prom << 16) | ((int)prom << 8) | (int)prom;
                
                data[0][r]++;
                data[1][g]++;
                data[2][b]++;
                data[3][prom]++;
                imgTemp[0].setRGB(x, y, pixelR);
                imgTemp[1].setRGB(x, y, pixelG);
                imgTemp[2].setRGB(x, y, pixelB);
                imgTemp[3].setRGB(x, y, pixelP);
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
    
    private void clearArray(){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = 0;
            }
        }
    }
}
