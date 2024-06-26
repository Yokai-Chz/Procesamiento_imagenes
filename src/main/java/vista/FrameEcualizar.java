package vista;

import javax.swing.*;

import Ecualizacion.Exponencial;
import Ecualizacion.Logaritmica;
import Ecualizacion.Raices;
import Ecualizacion.Rayleigh;
import Ecualizacion.Uniforme;
import modelo.EcualizarImagen;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

public class FrameEcualizar extends JInternalFrame {
    private BufferedImage imagenOrg;
    private int[] dataOrg;
    
    private JComboBox<String> opcionesPrincipales;
    private JMenuBar menuBar;

    private PanelImagen panelImagen;
    private PanelHistograma panelHistograma;

    private EcualizarImagen estrategia;

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
        opcionesPrincipales.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    int opcionSeleccionada = opcionesPrincipales.getSelectedIndex();
                    actualizarImg(opcionSeleccionada);
                }
            }
        });
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

    private void actualizarImg(int opcion){
        switch (opcion) {
            case 1 -> estrategia = new Uniforme();

            case 2 -> estrategia = new Exponencial();
            
            case 3 -> estrategia = new Rayleigh();
            
            case 4 -> estrategia = new Raices();
            
            case 5 -> estrategia = new Logaritmica();
            
            default -> System.out.println("Original");
        }

        if (opcion !=0 ){estrategia.Ecualizar(imagenOrg);}
    }
}