package ModeloColor;

import java.awt.image.BufferedImage;
import modelo.ProcesarImagen;

public class RGBtoLAB implements ProcesarImagen {

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

                // Conversión de RGB a XYZ
                double r = rojo / 255.0;
                double g = verde / 255.0;
                double b = azul / 255.0;

                double[] xyz = rgbToXyz(r, g, b);
                double[] lab = xyzToLab(xyz[0], xyz[1], xyz[2]);

                double l = lab[0];
                double a = lab[1];
                double bVal = lab[2];

                // Almacenamiento de los valores LAB
                int pixelL = (255 << 24) | ((int) (l *255 /100) << 16) | ((int) (l *255 /100) << 8) | (int) (l *255 /100);
                int pixelA = (255 << 24) | ((int) ((a + 128)) << 16) | ((int) ((a + 128)) << 8) | (int) ((a + 128));
                int pixelB = (255 << 24) | ((int) ((bVal + 128)) << 16) | ((int) ((bVal + 128)) << 8) | (int) ((bVal + 128));
                pixel = (255 << 24) | ((int) (l *255 /100) << 16) | ((int) ((bVal + 128)) << 8) | (int) ((bVal + 128));
                
                // Almacenamiento de los valores en los arreglos de datos y en las imágenes temporales
                data[0][(int) (l / 100.0 * 255)]++;
                data[1][(int) ((a + 128) / 2)]++;
                data[2][(int) ((bVal + 128) / 2)]++;
                imgTemp[0].setRGB(xImg, yImg, pixelL);
                imgTemp[1].setRGB(xImg, yImg, pixelA);
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
    public BufferedImage[] convertir(BufferedImage imagen, int[] umbral) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static double[] rgbToXyz(double r, double g, double b) {
        r = (r > 0.04045) ? Math.pow((r + 0.055) / 1.055, 2.4) : r / 12.92;
        g = (g > 0.04045) ? Math.pow((g + 0.055) / 1.055, 2.4) : g / 12.92;
        b = (b > 0.04045) ? Math.pow((b + 0.055) / 1.055, 2.4) : b / 12.92;

        r *= 100;
        g *= 100;
        b *= 100;

        double x = r * 0.4124 + g * 0.3576 + b * 0.1805;
        double y = r * 0.2126 + g * 0.7152 + b * 0.0722;
        double z = r * 0.0193 + g * 0.1192 + b * 0.9505;

        return new double[]{x, y, z};
    }

    private static double[] xyzToLab(double x, double y, double z) {
        x /= 95.047;
        y /= 100.000;
        z /= 108.883;

        x = (x > 0.008856) ? Math.pow(x, 1/3.0) : (7.787 * x) + (16 / 116.0);
        y = (y > 0.008856) ? Math.pow(y, 1/3.0) : (7.787 * y) + (16 / 116.0);
        z = (z > 0.008856) ? Math.pow(z, 1/3.0) : (7.787 * z) + (16 / 116.0);

        double L = (116 * y) - 16;
        double a = 500 * (x - y);
        double b = 200 * (y - z);

        return new double[]{L, a, b};
    }
}
