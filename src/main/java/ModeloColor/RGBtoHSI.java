package ModeloColor;

import java.awt.image.BufferedImage;
import modelo.ProcesarImagen;

public class RGBtoHSI implements ProcesarImagen {

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

                double min = Math.min(Math.min(r, g), b);
                double I = (r + g + b) / 3.0;
                double S = 1 - 3 * min / (r + g + b);
                if (r == g && g == b) {
                    S = 0;
                }
                double sqrt = (double) Math.sqrt((r - g) * (r - g) + (r - b) * (g - b));
                double acos = 0;
                if (sqrt != 0) {
                    acos = (double) Math.acos(0.5 * ((r - g) + (r - b)) / sqrt);
                }
                double H = b <= g ? acos : (2.0 * (double) Math.PI - acos);
                H = (double) Math.toDegrees(H);
                if (r == g && g == b) {
                    H = 0;
                }

                int pixelH = (255 << 24) | ((int) (H * 255 / 360) << 16) | ((int) (H * 255 / 360) << 8) | (int) (H * 255 / 360);
                int pixelS = (255 << 24) | ((int) (S * 255) << 16) | ((int) (S * 255) << 8) | (int) (S * 255);
                int pixelI = (255 << 24) | ((int) (I * 255) << 16) | ((int) (I * 255) << 8) | (int) (I * 255);
                pixel = (255 << 24) | ((int) (H * 255 / 360) << 16) | ((int) (S * 255) << 8) | (int) (I * 255 );

                data[0][(int) (H * 255 / 360)]++;
                data[1][(int) (S * 255)]++;
                data[2][(int) (I * 255)]++;
                imgTemp[0].setRGB(xImg, yImg, pixelH);
                imgTemp[1].setRGB(xImg, yImg, pixelS);
                imgTemp[2].setRGB(xImg, yImg, pixelI);
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
