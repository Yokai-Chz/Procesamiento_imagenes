package vista;

import OpAritmeticas.*;
import OpLogicas.*;
import OpRelacionales.*;
import RGB.RGBGris;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.*;

public class FrameOperaciones extends JInternalFrame {

    private PanelImagen img1, img2, imgR;
    private PanelHistograma histo1, histo2, histoR;

    private JComboBox<String> operaciones, porcentaje1, porcentaje2;
    private JButton btnActualizar, btnReiniciar, btnSalir;
    private JLabel lbImgR, lbImg1, lbImg2;
    private JLabel lbHistR, lbHist1, lbHist2; 
    
    private LectorDeImagen lector1, lector2;
    private int[] dataHisto1, dataHisto2, dataHistoR; 
    private ProcesarImagen estrategia;
    private OperarImagen operar;

    public FrameOperaciones(String path) {
        super("Visor de imagen ", true, true, true, true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.lector1 = new LectorDeImagen(path);
        elegirImagen2();
        initComponents();
        this.setSize(740, 600);
    }

    private void initHistogramas(){
        Color c = Color.BLUE;
        histo1 = new PanelHistograma(dataHisto1, c);
        histo1.setBounds(20, 280, 300, 100);

        histo2 = new PanelHistograma(dataHisto2, c);
        histo2.setBounds(20, 440, 300, 100);

        histoR = new PanelHistograma(dataHistoR, c);
        histoR.setBounds(380, 280, 300, 100);
    }

    private void initImagenes() {
        estrategia = new RGBGris();
        int tam = 200;

        BufferedImage[] imgTemp = estrategia.convertir(lector1.getBufferedImagen());
        img1 = new PanelImagen(imgTemp[3]);
        img1.setBounds(30, 30, tam, tam);
        dataHisto1 = estrategia.extraerData()[3];
        
        estrategia = new RGBGris();
        imgTemp = estrategia.convertir(lector2.getBufferedImagen());
        img2 = new PanelImagen(imgTemp[3]);
        img2.setBounds(260, 30, tam, tam);
        dataHisto2 = estrategia.extraerData()[3];

        imgR = new PanelImagen(imgTemp[3]);
        imgR.setBounds(490, 30, tam, tam);
        dataHistoR = estrategia.extraerData()[3];
        
    }

    private void initLabels(){
        lbImg1 = new JLabel("Imagen 1");
        lbImg1.setBounds(30, 5, 100, 30);
        lbImg1.setFont(new Font("Tahoma", Font.BOLD, 12));

        lbImg2 = new JLabel("Imagen 2");
        lbImg2.setBounds(260, 5, 100, 30);
        lbImg2.setFont(new Font("Tahoma", Font.BOLD, 12));

        lbImgR = new JLabel("Imagen Resultante");
        lbImgR.setBounds(490, 5, 200, 30);
        lbImgR.setFont(new Font("Tahoma", Font.BOLD, 12));

        lbHist1 = new JLabel("Histograma Imagen 1");
        lbHist1.setBounds(20, 250, 200, 30);
        lbHist1.setFont(new Font("Tahoma", Font.BOLD, 12));

        lbHist2 = new JLabel("Histograma Imagen 2");
        lbHist2.setBounds(20, 410, 200, 30);
        lbHist2.setFont(new Font("Tahoma", Font.BOLD, 12));

        lbHistR = new JLabel("Histograma Imagen Resultante");
        lbHistR.setBounds(380, 250, 200, 30);
        lbHistR.setFont(new Font("Tahoma", Font.BOLD, 12));
        
    }

    private void initBotones() {
        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(400, 500, 100, 30);
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarImg();
            }
        });

        btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.setBounds(500, 500, 100, 30);
        btnReiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                   imgR.addImagen(lector2.getGrayBuffer());
                   histoR.setData(dataHistoR);
            }
        });

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(600, 500, 100, 30);
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void initSelectores() {
        //Inicializar selector para convertir 
        String[] opcOperaciones = { "Suma", "Resta", "Multiplicacion", "Division", "And", "Or", "Xor", "Not", "Igual", "Diferente", "Mayor", "Mayor o Igual", "Menor", "Menor o Igual"};
        operaciones = new JComboBox<>(opcOperaciones);
        operaciones.setBounds(400, 450, 225, 30);
        operaciones.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                //int opcionSeleccionada = operaciones.getSelectedIndex();
                //actualizarSelect(opcionSeleccionada);
            }
        });

        String[] opcPorcentaje = {"1.0"};
        porcentaje1 = new JComboBox<>(opcPorcentaje);
        porcentaje1.setBounds(400, 450, 75, 30);
        porcentaje1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                //int opcionSeleccionada = operaciones.getSelectedIndex();
                //actualizarSelect(opcionSeleccionada);
            }
        });

        porcentaje2 = new JComboBox<>(opcPorcentaje);
        porcentaje2.setBounds(500, 450, 75, 30);
        porcentaje2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                //int opcionSeleccionada = operaciones.getSelectedIndex();
                //actualizarSelect(opcionSeleccionada);
            }
        });
    }

    private void initComponents() {

        initImagenes();
        initHistogramas();
        initBotones();
        initSelectores();
        initLabels();
        Container contenedor = this.getContentPane();
        contenedor.setLayout(null);
        contenedor.add(img1);
        contenedor.add(img2);
        contenedor.add(imgR);
        contenedor.add(lbImg1);
        contenedor.add(lbImg2);
        contenedor.add(lbImgR);
        contenedor.add(lbHist1);
        contenedor.add(lbHist2);
        contenedor.add(lbHistR);
        contenedor.add(histo1);
        contenedor.add(histo2);
        contenedor.add(histoR);
        contenedor.add(btnActualizar);
        contenedor.add(btnReiniciar);
        contenedor.add(btnSalir);
        contenedor.add(operaciones);
        //contenedor.add(porcentaje1);
        //contenedor.add(porcentaje2);
        this.setVisible(true);
    }

    private void elegirImagen2() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de imagen", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        int seleccion = fileChooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            lector2 = new LectorDeImagen(path);


        }

    }

    private void actualizarImg() {
        try {
            int indx = operaciones.getSelectedIndex();
            switch (indx) {
                case 0 ->
                    operar = new Suma();
                case 1 ->
                    operar = new Resta();
                case 2 ->
                    operar = new Multiplicacion();
                case 3 ->
                    operar = new Division();
                case 4 ->
                    operar = new And();
                case 5 ->
                    operar = new Or();
                case 6 ->
                    operar = new Xor();
                case 7 ->
                    operar = new Not();
                case 8 ->
                    operar = new Igual();
                case 9 ->
                    operar = new Diferente();
                case 10 ->
                    operar = new Mayor();
                case 11 ->
                    operar = new MayorIgual();
                case 12 ->
                    operar = new Menor();
                case 13 ->
                    operar = new MenorIgual();
            }
    
            BufferedImage imgTemp = operar.operar(lector1.getBufferedImagen(), lector2.getBufferedImagen());
            int[] data = operar.extraerData();
    
            imgR.addImagen(imgTemp);
            histoR.setData(data);    
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Error al procesar la imagen.\n La imagen 2 debe ser del mismo tamaño o mas grande que la imagen 1\n Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
  
}
