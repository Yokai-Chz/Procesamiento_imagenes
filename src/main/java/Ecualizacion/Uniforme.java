package Ecualizacion;

import java.awt.image.BufferedImage;

import modelo.EcualizarImagen;

public class Uniforme implements EcualizarImagen {
    @Override
    public BufferedImage Ecualizar(BufferedImage imagen){
        System.out.println("Uniforme");
        return null;
    }
}
