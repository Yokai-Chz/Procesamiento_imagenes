package Binarizacion;

import java.awt.image.BufferedImage;
import modelo.ProcesarImagen;

public class DosUmbral implements ProcesarImagen {

    private int[][] data = new int[3][256];

    @Override
    public BufferedImage[] convertir(BufferedImage imagen, int[] umbral) {
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

                revisarUmbral(umbral);

                int r, g, b, gris;
                r = rojo;
                g = verde;
                b = azul;
                gris = (rojo + verde + azul) / 3;
                if (rojo < umbral[0]) {
                    r = 0;
                }
                if (verde < umbral[0]) {
                    g = 0;
                }
                if (azul < umbral[0]) {
                    b = 0;
                }
                if (gris < umbral[0]) {
                    gris = 0;
                }

                if (rojo >= umbral[0] && rojo <= umbral[1]) {
                    r = 127;
                }
                if (verde >= umbral[0] && verde <= umbral[1]) {
                    g = 127;
                }
                if (azul >= umbral[0] && azul <= umbral[1]) {
                    b = 127;
                }
                if (gris >= umbral[0] && azul <= umbral[1]) {
                    gris = 127;
                }

                if (rojo > umbral[1]) {
                    r = 255;
                }
                if (verde > umbral[1]) {
                    g = 255;
                }
                if (azul > umbral[1]) {
                    b = 255;
                }
                if (gris > umbral[1]) {
                    gris = 255;
                }

                int pixelR = (255 << 24) | (r << 16) | (r << 8) | r;
                int pixelG = (255 << 24) | (g << 16) | (g << 8) | g;
                int pixelB = (255 << 24) | (b << 16) | (b << 8) | b;
                pixel = (255 << 24) | (gris << 16) | (gris << 8) | gris;

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
    public int[][] extraerData() {
        return this.data;
    }

    @Override
    public BufferedImage[] convertir(BufferedImage imagen) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int[] revisarUmbral(int[] umbral) {
        int temporal = 0;
        if (umbral[0] > umbral[1]) {
            temporal = umbral[0];
            umbral[0] = umbral[1];
            umbral[1] = temporal;
        }
        return umbral;
    }

}
