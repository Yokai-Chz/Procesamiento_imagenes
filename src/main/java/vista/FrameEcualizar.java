package vista;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Ecualizacion.Exponencial;
import Ecualizacion.Logaritmica;
import Ecualizacion.Raices;
import Ecualizacion.Rayleigh;
import Ecualizacion.Uniforme;
import modelo.EcualizarImagen;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

public class FrameEcualizar extends JInternalFrame {
    private BufferedImage imagenOrg;
    private int[] dataOrg;

    private JComboBox<String> opcionesPrincipales;
    private JSlider slider1, slider2;//, slider3;
    private JTextField valSlider1, valSlider2;
    private JLabel labelF1, labelF2;
    private JButton btnActualizar;

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

    private void initSelector() {
        String[] opcionesPrin = { "Imagen Original", "Ecualizacion Uniforme", "Ecualizacion Exponencial",
                "Ecualizacion Rayleigh", "Ecualizacion Hiperbolica Raices", "Ecualizacion Hiperbolica Logaritmica" };
        opcionesPrincipales = new JComboBox<>(opcionesPrin);
        opcionesPrincipales.setBounds(330, 20, 225, 30);
        opcionesPrincipales.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    int opcionSeleccionada = opcionesPrincipales.getSelectedIndex();
                    actualizarImg(opcionSeleccionada);
                }
            }
        });
    }

    private void initImage() {
        panelImagen = new PanelImagen(imagenOrg);
        panelImagen.setBounds(20, 20, 250, 250);
    }

    private void initHistograma() {
        panelHistograma = new PanelHistograma(dataOrg, Color.BLUE);
        panelHistograma.setBounds(300, 70, 300, 150);
    }

    private void initSliders() {
        slider1 = new JSlider(JSlider.HORIZONTAL, 0, 255, 127);
        slider1.setPaintTicks(true);
        slider1.setPaintLabels(true);
        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Obtenemos el valor del JSlider y lo establecemos en el JTextField
                valSlider1.setText(String.valueOf(slider1.getValue()));
            }
        });
        slider1.setBounds(350, 230, 200, 40);

        slider2 = new JSlider(JSlider.HORIZONTAL, 0, 255, 127);
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(true);
        slider2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Obtenemos el valor del JSlider y lo establecemos en el JTextField
                valSlider2.setText(String.valueOf(slider2.getValue()));
            }
        });
        slider2.setBounds(350, 270, 200, 40);

        valSlider1 = new JTextField("127");
        valSlider1.setBounds(550, 230, 30, 25);
        valSlider1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtenemos el valor del texto del JTextField
                    int value = Integer.parseInt(valSlider1.getText());
                    // Establecemos el valor del JSlider
                    if (!(value < 0 || value > 255)) {
                        slider1.setValue(value);
                    }

                } catch (NumberFormatException ex) {
                    // Manejo de error si el texto no es un número válido
                    JOptionPane.showMessageDialog(FrameEcualizar.this, "Por favor, introduce un número válido", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        valSlider2 = new JTextField("127");
        valSlider2.setBounds(550, 270, 30, 25);
        valSlider2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtenemos el valor del texto del JTextField
                    int value = Integer.parseInt(valSlider2.getText());
                    // Establecemos el valor del JSlider
                    if (!(value < 0 || value > 255)) {
                        slider2.setValue(value);
                    }

                } catch (NumberFormatException ex) {
                    // Manejo de error si el texto no es un número válido
                    JOptionPane.showMessageDialog(FrameEcualizar.this, "Por favor, introduce un número válido", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    private void initLabels() {
        labelF1 = new JLabel("F1:");
        labelF1.setBounds(330, 230, 50, 30);
        labelF1.setFont(new Font("Tahoma", Font.BOLD, 14));

        labelF2 = new JLabel("F2:");
        labelF2.setBounds(330, 270, 50, 30);
        labelF2.setFont(new Font("Tahoma", Font.BOLD, 14));
    }

    private void initBoton(){
        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(20, 290, 100, 30);
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                actualizarImg(opcionesPrincipales.getSelectedIndex());
            }
        });
    }

    private void initComponents() {
        initImage();
        initHistograma();
        initSelector();
        initSliders();
        initBoton();
        initLabels();
        Container contenedor = this.getContentPane();
        contenedor.setLayout(null);
        contenedor.add(panelImagen);
        contenedor.add(panelHistograma);
        contenedor.add(opcionesPrincipales);
        contenedor.add(slider1);
        contenedor.add(slider2);
        contenedor.add(valSlider1);
        contenedor.add(valSlider2);
        contenedor.add(labelF1);
        contenedor.add(labelF2);
        contenedor.add(btnActualizar);
    }

    private void actualizarImg(int opcion) {
        switch (opcion) {
            case 1 -> estrategia = new Uniforme();

            case 2 -> estrategia = new Exponencial();

            case 3 -> estrategia = new Rayleigh();

            case 4 -> estrategia = new Raices();

            case 5 -> estrategia = new Logaritmica();

            default -> System.out.println("Original");
        }

        int f1 = slider1.getValue();
        int f2 = slider2.getValue();
        estrategia.setDatos(f1, f2, 0, 0);
        panelHistograma.setData(dataOrg);
        if (opcion == 1) {
            BufferedImage impTemp = estrategia.Ecualizar(imagenOrg, panelHistograma.getAcumulado());
            int[] data = estrategia.getData();
            panelImagen.addImagen(impTemp);
            panelHistograma.setData(data);
        }
    }
}