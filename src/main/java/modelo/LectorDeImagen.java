package modelo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class LectorDeImagen {
    private Image imagen;
    private String path;
    private int tipo;
    private BufferedImage bufferedImagen;
    
    public LectorDeImagen(String path) {
        imagen = null;
        this.path = path;
        leerBufferedImagen();
    }
    
    public void leerImagen() {
        String acceso = this.path;
        System.out.println(acceso);
        imagen = new ImageIcon(
        	getClass().getResource( acceso ) ).getImage();
    }
    
    public void leerBufferedImagen() {
        bufferedImagen = null;
        try {
            String acceso = this.path;
            //DEBUG
            System.out.println("Acceso: " + acceso);
            FileInputStream input = 
                        new FileInputStream(
                            new File(acceso));
            bufferedImagen = ImageIO.read(input);
            tipo = bufferedImagen.getType();
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
        int ancho = bufferedImagen.getWidth();
        int alto = bufferedImagen.getHeight();
        imagen = bufferedImagen.getScaledInstance(ancho, alto, 
                BufferedImage.SCALE_DEFAULT);
    }
    

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public BufferedImage getBufferedImagen() {
        return bufferedImagen;
    }

    public BufferedImage getGrayBuffer(){
        int ancho = bufferedImagen.getWidth();
        int alto = bufferedImagen.getHeight();
        BufferedImage imgTemp = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int pixel = bufferedImagen.getRGB(x, y);
                int r = (pixel & 0x00ff0000) >> 16;
                int g = (pixel & 0x0000ff00) >> 8;
                int b = pixel & 0x000000ff;
                int prom = (r+g+b)/3;
                int pixelP = ((int)prom << 16) | ((int)prom << 8) | (int)prom;
                
                imgTemp.setRGB(x, y, pixelP);
            }
        }
        return imgTemp;
    }

    public void setBufferedImagen(BufferedImage bufferedImagen) {
        this.bufferedImagen = bufferedImagen;
    }
    public int getAncho() {
        return imagen.getWidth(null);
    }
    public int getAlto() {
        return imagen.getHeight(null);
    }
    public int getAnchoBufferedImage() {
        return bufferedImagen.getWidth();
    }
    public int getAltoBufferedImage() {
        return bufferedImagen.getHeight();
    }
}
