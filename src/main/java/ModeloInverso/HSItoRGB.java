package ModeloInverso;

import java.awt.image.BufferedImage;
import modelo.ProcesarImagen;

public class HSItoRGB implements ProcesarImagen {

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
                int h = (pixel & 0x00ff0000) >> 16;
                int s = (pixel & 0x0000ff00) >> 8;
                int i = pixel & 0x000000ff;

                double r, g, b;

                double H = h *360 / 255.0;
                double S = s/255.0;
                double I = i/255.0;

                H = (double) Math.toRadians(H);
                if (H < 2 * Math.PI / 3) {
                    b = I * (1 - S);
                    r = I * (1 + ((S * (double) Math.cos(H)) / (double) Math.cos(Math.PI / 3 - H)));
                    g = 3 * I - (r + b);
                } else if (H < 4 * Math.PI / 3) {
                    H = H - 2 * (double) Math.PI / 3;
                    r = I * (1 - S);
                    g = I * (1 + ((S * (double) Math.cos(H)) / (double) Math.cos(Math.PI / 3 - H)));
                    b = 3 * I - (r + g);
                } else {
                    H = H - 4 * (double) Math.PI / 3;
                    g = I * (1 - S);
                    b = I * (1 + ((S * (double) Math.cos(H)) / (double) Math.cos(Math.PI / 3 - H)));
                    r = 3 * I - (g + b);
                }

                

                int red = (int) (r * 255);
                int green = (int) (g * 255);
                int blue = (int) (b * 255);

                if (red < 0 ) {red=0;} else if(red>255){red=255;}
                if (green < 0 ) {green=0;} else if(green>255){green=255;}
                if (blue < 0 ) {blue=0;} else if(blue>255){blue=255;}

                int pixelQ = (255 << 24) | ((int) red << 16) | ((int) 0 << 8) | (int) 0;
                int pixelI = (255 << 24) | ((int) 0 << 16) | ((int) green << 8) | (int) 0;
                int pixelY = (255 << 24) | ((int) 0 << 16) | ((int) 0 << 8) | (int) blue;
                pixel = (255 << 24) | ((int) (red) << 16) | ((int) (green) << 8) | (int) (blue);

                data[0][(int) red]++;
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

}
