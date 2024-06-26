package Ecualizacion;

import java.awt.image.BufferedImage;

import modelo.EcualizarImagen;

public class Logaritmica implements EcualizarImagen {
    @Override
    public BufferedImage Ecualizar(BufferedImage imagen, double[] datos){
        System.out.println("Logaritmica");
        return null;
    }

    @Override
    public void setDatos(int f1, int f2, int a, int pot) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDatos'");
    }
}
