package ModeloInverso;

import java.awt.image.BufferedImage;
import modelo.ProcesarImagen;

public class LABtoRGB implements ProcesarImagen {

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
                int lP = (pixel & 0x00ff0000) >> 16;
                int aP = (pixel & 0x0000ff00) >> 8;
                int bValP = pixel & 0x000000ff;

                double l = lP *100 /255.0;
                double a = aP -128.0;
                double bVal = bValP -128.0;

                double[] xyz = labToXyz(l, a, bVal);
                double[] rgb = xyzToRgb(xyz[0], xyz[1], xyz[2]);

                int red =(int) (rgb[0]*255);
                int green = (int) (rgb[1]*255);
                int blue = (int) (rgb[2]*255);

                if (red < 0 ) {red=0;} else if(red>255){red=255;}
                if (green < 0 ) {green=0;} else if(green>255){green=255;}
                if (blue < 0 ) {blue=0;} else if(blue>255){blue=255;}

                int pixelQ = (255 << 24) | ((int) red << 16) | ((int) 0 << 8) | (int) 0;
                int pixelI = (255 << 24) | ((int) 0 << 16) | ((int) green << 8) | (int) 0;
                int pixelY = (255 << 24) | ((int) 0 << 16) | ((int) 0 << 8) | (int) blue;
                pixel = (255 << 24) | ((int) (red) << 16) | ((int) (green) << 8) | (int) (blue);
                
                data[0][(int) (red)]++;
                data[1][(int) green]++;
                data[2][(int) blue]++;
                imgTemp[0].setRGB(xImg, yImg, pixelY);
                imgTemp[1].setRGB(xImg, yImg, pixelI);
                imgTemp[2].setRGB(xImg, yImg, pixelQ);
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

    private static double[] labToXyz(double L, double a, double b) {
        double y = (L + 16) / 116;
        double x = a / 500 + y;
        double z = y - b / 200;

        x = 95.047 * ((x > 0.206897) ? x * x * x : (x - 16 / 116) / 7.787);
        y = 100.000 * ((y > 0.206897) ? y * y * y : (y - 16 / 116) / 7.787);
        z = 108.883 * ((z > 0.206897) ? z * z * z : (z - 16 / 116) / 7.787);

        return new double[]{x, y, z};
    }

    private static double[] xyzToRgb(double x, double y, double z) {
        x /= 100;
        y /= 100;
        z /= 100;

        double r = x *  3.2406 + y * -1.5372 + z * -0.4986;
        double g = x * -0.9689 + y *  1.8758 + z *  0.0415;
        double b = x *  0.0557 + y * -0.2040 + z *  1.0570;

        r = (r > 0.0031308) ? 1.055 * Math.pow(r, 1/2.4) - 0.055 : 12.92 * r;
        g = (g > 0.0031308) ? 1.055 * Math.pow(g, 1/2.4) - 0.055 : 12.92 * g;
        b = (b > 0.0031308) ? 1.055 * Math.pow(b, 1/2.4) - 0.055 : 12.92 * b;

        return new double[]{r, g, b};
    }

}
