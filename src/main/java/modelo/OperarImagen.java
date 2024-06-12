package modelo;

import java.awt.image.BufferedImage;

public interface OperarImagen {
    BufferedImage operar(BufferedImage imagen, BufferedImage imagen2);
    
    //BufferedImage[] convertir(BufferedImage imagen, int[] umbral);
    
    int []extraerData();
}
