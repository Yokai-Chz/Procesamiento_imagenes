package ModeloColor;

import java.awt.image.BufferedImage;
import modelo.ProcesarImagen;

public class RGBtoHSV implements ProcesarImagen {

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
                int rojo = (pixel >> 16) & 0xff;
                int verde = (pixel >> 8) & 0xff;
                int azul = pixel & 0xff;

                double r = rojo / 255.0;
                double g = verde / 255.0;
                double b = azul / 255.0;
                
                double max = Math.max(r, Math.max(g, b));
                double min = Math.min(r, Math.min(g, b));
                double H, S, V;
                V = max;

                double diff = max - min;
                if (max == 0) {
                    S = 0;
                } else {
                    S = diff / max;
                }

                if (max == min) {
                    H = 0;
                } else {
                    if (max == r) {
                        H = (60 * ((g - b) / diff) + 360) % 360;
                    } else if (max == g) {
                        H = (60 * ((b - r) / diff) + 120) % 360;
                    } else {
                        H = (60 * ((r - g) / diff) + 240) % 360;
                    }
                }


                int pixelH = (255 << 24) | ((int) (H * 255 / 360) << 16) | ((int) (H * 255 / 360) << 8) | (int) (H * 255 / 360);
                int pixelS = (255 << 24) | ((int) (S * 255) << 16) | ((int) (S * 255) << 8) | (int) (S * 255);
                int pixelL = (255 << 24) | ((int) (V * 255) << 16) | ((int) (V * 255) << 8) | (int) (V * 255);
                pixel = (255 << 24) | ((int) (H * 255/360) << 16) | ((int) (S * 255) << 8) | (int) (V * 255);

                data[0][(int) (H * 255 / 360)]++;
                data[1][(int) (S * 255)]++;
                data[2][(int) (V * 255)]++;
                imgTemp[0].setRGB(xImg, yImg, pixelH);
                imgTemp[1].setRGB(xImg, yImg, pixelS);
                imgTemp[2].setRGB(xImg, yImg, pixelL);
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
