package modelo;

import java.awt.image.BufferedImage;

public interface EcualizarImagen{

    BufferedImage Ecualizar(BufferedImage imagen, double[] datos);
    
    void setDatos(int f1, int f2, int a, int pot);

    int[] getData();
}
