package Ecualizacion;

import java.awt.image.BufferedImage;

import modelo.EcualizarImagen;

public class Raices implements EcualizarImagen {
    @Override
    public BufferedImage Ecualizar(BufferedImage imagen, double[] datos){
        System.out.println("Raices");
        return null;
    }

    @Override
    public void setDatos(int f1, int f2, int a, int pot) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDatos'");
    }
}
