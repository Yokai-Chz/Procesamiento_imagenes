package modelo;

import java.awt.image.BufferedImage;

public interface ProcesarImagen {
    BufferedImage[] convertir(BufferedImage imagen);
    
    BufferedImage[] convertir(BufferedImage imagen, int[] umbral);
    
    int [][] extraerData();
}
