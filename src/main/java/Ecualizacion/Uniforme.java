package Ecualizacion;

import java.awt.image.BufferedImage;
import modelo.EcualizarImagen;

public class Uniforme implements EcualizarImagen {
    private int fmax, fmin;
    private int[] dataResultante = new int[256];

    @Override
    public BufferedImage Ecualizar(BufferedImage imagen, double[] datos) {
        int ancho = imagen.getWidth();
        int alto = imagen.getHeight();
        BufferedImage imgTemp = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        
        double[] dataNorm = datos;

        // Normalizar la dataNorm a [0, 255]
        for (int i = 0; i < dataNorm.length; i++) {
            dataNorm[i] = (fmax - fmin) * dataNorm[i] + fmin;
            if (dataNorm[i] > fmax) {
                dataNorm[i] = fmax;
            }
            if (dataNorm[i] < 0.0) {
                dataNorm[i] = 0.0;
            }
        }

        for (int yImg = 0; yImg < alto; yImg++) {
            for (int xImg = 0; xImg < ancho; xImg++) {
                int pixel = imagen.getRGB(xImg, yImg) & 0x000000ff;

                // Mapear el valor del pixel utilizando la data
                int pixelEcualizado = (int) Math.floor(dataNorm[pixel]);
                dataResultante[pixelEcualizado]++;

                int nuevoPixel = (255 << 24) | (pixelEcualizado << 16) | (pixelEcualizado << 8) | pixelEcualizado;
                imgTemp.setRGB(xImg, yImg, nuevoPixel);
            }
        }
        return imgTemp;
    }

    @Override
    public void setDatos(int f1, int f2, double a, double pot) {
        this.fmax = Math.max(f1, f2);
        this.fmin = Math.min(f1, f2);
    }

    @Override
    public int[] getData() {
        return dataResultante;
    }
}
