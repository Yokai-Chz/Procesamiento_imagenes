package vista;

import javax.swing.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.image.BufferedImage;

public class FrameEcualizar extends JInternalFrame {
    private BufferedImage imagenOrg;
    private int[] dataOrg;
    
    private JComboBox<String> opcionesPrincipales;
    private JMenuBar menuBar;

    private PanelImagen panelImagen;
    private PanelHistograma panelHistograma;


    public FrameEcualizar(BufferedImage image, int[] data) {
        super("Ecualizacion", true, true, true, true);
        this.imagenOrg = image;
        this.dataOrg = data;
        this.setSize(640, 330);
        setLocation(50, 50); // Establece la ubicación inicial del internal frame
        setVisible(true);
        initComponents();
    }

    private void initSelector(){
        String[] opcionesPrin = {"Imagen Original", "Ecualizacion Uniforme", "Ecualizacion Exponencial", "Ecualizacion Rayleigh", "Ecualizacion Hiperbolica Raices", "Ecualizacion Hiperbolica Logaritmica"};
        opcionesPrincipales = new JComboBox<>(opcionesPrin);
        opcionesPrincipales.setBounds(330, 200, 225, 30);
    }

    private void initImage(){
        panelImagen = new PanelImagen(imagenOrg);
        panelImagen.setBounds(20, 20, 250, 250);
    }

    private void initHistograma(){
        panelHistograma = new PanelHistograma(dataOrg, Color.BLUE);
        panelHistograma.setBounds(300, 20, 300, 150);
    }

    private void initComponents(){
        initImage();
        initHistograma();
        initSelector();
        Container contenedor = this.getContentPane();
        contenedor.setLayout(null);
        contenedor.add(panelImagen);
        contenedor.add(panelHistograma);
        contenedor.add(opcionesPrincipales);
        this.setJMenuBar(menuBar);
    } 

}