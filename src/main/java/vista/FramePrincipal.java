package vista;

import RGB.*;
import modelo.*;
import ModeloColor.*;
import ModeloInverso.*;
import Binarizacion.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FramePrincipal extends JInternalFrame {

    //Se inicializan todos los objetos que apareceran en el JFrame
    private JMenuBar menuBar;
    private JMenu menuArchivo, menuImagen, menuVisualizar, menuGuardar, menuHistograma;
    private JMenuItem itemAbrir, itemReiniciar, itemImagenProcesadaVs, itemCanal1Vs, itemCanal2Vs, itemCanal3Vs, itemOperaciones;
    private JMenuItem itemImagenProcesadaSv, itemCanal1Sv, itemCanal2Sv, itemCanal3Sv;
    private JMenuItem itemHistograma, itemHistogramaAcumulativo;
    private LectorDeImagen lector;
    private JComboBox<String> opcionesPrincipales, opcionesSecundarias;
    private JButton botonActualizar;
    private JLabel lbImgPrc, lbCanal1, lbCanal2, lbCanal3;
    private JSlider slider1, slider2;
    private JTextField valSlider1, valSlider2;

    //Se crea la variable estrategia para su uso del patron de diseño
    private ProcesarImagen estrategia;

    private PanelImagen panelOrg, img1, img2, img3;

    private int id = 1;

    private PanelHistograma histograma1, histograma2, histograma3;

    public FramePrincipal(String path) {
        /*
        Toma el lector generado con la direcion path del chooseFile e inicializa
        los componenetes del frame, coloca la ventana en el centro y no es 
        modificable su tamaño
         */
        super("Visor de imagen ", true, true, true, true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.lector = new LectorDeImagen(path);
        initComponents();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, 600);

    }

    private void initLabels(){
        lbImgPrc = new JLabel("Imagen Procesada");
        lbImgPrc.setBounds(20, 500, 200, 20);
        lbImgPrc.setFont(new Font("Tahoma", Font.BOLD, 16));

        lbCanal1 = new JLabel("Canal 1");
        lbCanal1.setBounds(320, 500, 200, 30);
        lbCanal1.setFont(new Font("Tahoma", Font.BOLD, 16));

        lbCanal2 = new JLabel("Canal 2");
        lbCanal2.setBounds(620, 500, 200, 30);
        lbCanal2.setFont(new Font("Tahoma", Font.BOLD, 16));

        lbCanal3 = new JLabel("Canal 1");
        lbCanal3.setBounds(920, 500, 200, 30);
        lbCanal3.setFont(new Font("Tahoma", Font.BOLD, 16));
        
    }

    private void initMenuBar() {
        // Se crean el menu bar y se deja escuchando 
        //los botones en caso de querer actualizar una imagen.
        menuBar = new JMenuBar();
        menuArchivo = new JMenu("Archivo");

        itemAbrir = new JMenuItem("Abrir nueva imagen");
        itemAbrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirChoose();
            }
        });

        menuGuardar = new JMenu("Guardar imagen");
        itemImagenProcesadaSv = createMenuItemGuardar("Imagen procesada", 1);
        itemCanal1Sv = createMenuItemGuardar("Canal 1", 2);
        itemCanal2Sv = createMenuItemGuardar("Canal 2", 3);
        itemCanal3Sv = createMenuItemGuardar("Canal 3", 4);
        
        menuGuardar.add(itemImagenProcesadaSv);
        menuGuardar.add(itemCanal1Sv);
        menuGuardar.add(itemCanal2Sv);
        menuGuardar.add(itemCanal3Sv);


        menuArchivo.add(itemAbrir);
        menuArchivo.add(menuGuardar);

        menuBar.add(menuArchivo);

        menuImagen = new JMenu("Imagen");

        itemReiniciar = new JMenuItem("Reiniciar imagen");
        itemReiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarImg();
            }
        });
        
        menuVisualizar = new JMenu("Visualizar imagen");
        itemImagenProcesadaVs = createMenuItemVisualizador("Imagen procesada", 1);
        itemCanal1Vs = createMenuItemVisualizador("Canal 1", 2);
        itemCanal2Vs = createMenuItemVisualizador("Canal 2", 3);
        itemCanal3Vs = createMenuItemVisualizador("Canal 3", 4);

        menuVisualizar.add(itemImagenProcesadaVs);
        menuVisualizar.add(itemCanal1Vs);
        menuVisualizar.add(itemCanal2Vs);
        menuVisualizar.add(itemCanal3Vs);


        itemOperaciones = new JMenuItem("Operaciones");
        itemOperaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFrameOperaciones();
            }
        });


        menuHistograma = new JMenu("Histogramas");

        itemHistogramaAcumulativo = new JMenuItem("Histograma Acumulativo");
        itemHistogramaAcumulativo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                convertirHistograma(true);
            }
        });

        itemHistograma = new JMenuItem("Histograma");
        itemHistograma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                convertirHistograma(false);
            }
        });

        menuHistograma.add(itemHistogramaAcumulativo);
        menuHistograma.add(itemHistograma);

        menuImagen.add(itemOperaciones);
        menuImagen.add(itemReiniciar);
        menuImagen.add(menuVisualizar);
        menuBar.add(menuImagen);
        menuBar.add(menuHistograma);
    }

    private void initSelectores() {
        //Inicializar selector para convertir 
        String[] opcionesPrin = {" ", "Extraer Canales", "Modelos de color", "Regresar A RGB", "Binarizar Imagen"};
        opcionesPrincipales = new JComboBox<>(opcionesPrin);
        opcionesPrincipales.setBounds(20, 20, 225, 30);
        opcionesPrincipales.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                int opcionSeleccionada = opcionesPrincipales.getSelectedIndex();
                actualizarSelect(opcionSeleccionada);
            }
        });

        String[] opcionesSec = {""};
        opcionesSecundarias = new JComboBox<>(opcionesSec);
        opcionesSecundarias.setBounds(20, 60, 225, 30);
        opcionesSecundarias.setVisible(false);
        opcionesSecundarias.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                actualizarSegundoSelect();
            }
        });
    }

    private void initImagenes() {
        //Inicializar los componentes del marco de la imagen 
        panelOrg = new PanelImagen(lector.getImagen());
        panelOrg.setBounds(20, 230, 250, 250);

        img1 = new PanelImagen(lector.getImagen());
        img1.setBounds(320, 230, 250, 250);

        img2 = new PanelImagen(lector.getImagen());
        img2.setBounds(620, 230, 250, 250);

        img3 = new PanelImagen(lector.getImagen());
        img3.setBounds(920, 230, 250, 250);
    }

    private void initBotones() {
        botonActualizar = new JButton("Actualizar");
        botonActualizar.setBounds(1220, 30, 100, 30);
        botonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarImg();
            }
        });
    }

    private void initSliders() {
        slider1 = new JSlider(JSlider.HORIZONTAL, 0, 255, 127);
        slider1.setPaintTicks(true);
        slider1.setPaintLabels(true);
        slider1.setBounds(15, 110, 200, 40);
        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Obtenemos el valor del JSlider y lo establecemos en el JTextField
                valSlider1.setText(String.valueOf(slider1.getValue()));
            }
        });
        slider1.setVisible(false);

        slider2 = new JSlider(JSlider.HORIZONTAL, 0, 255, 127);
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(true);
        slider2.setBounds(15, 150, 200, 40);
        slider2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Obtenemos el valor del JSlider y lo establecemos en el JTextField
                valSlider2.setText(String.valueOf(slider2.getValue()));
            }
        });
        slider2.setVisible(false);

        valSlider1 = new JTextField("127");
        valSlider1.setBounds(215, 110, 30, 25);
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
                    JOptionPane.showMessageDialog(FramePrincipal.this, "Por favor, introduce un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        valSlider1.setVisible(false);

        valSlider2 = new JTextField("127");
        valSlider2.setBounds(215, 150, 30, 25);
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
                    JOptionPane.showMessageDialog(FramePrincipal.this, "Por favor, introduce un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        valSlider2.setVisible(false);

    }

    private void initHistogramas() {
        int[] lista = {1, 2, 4, 5, 2, 4, 9};
        histograma1 = new PanelHistograma(lista);
        histograma1.setBounds(290, 50, 300, 150);
        histograma1.setVisible(false);

        histograma2 = new PanelHistograma(lista);
        histograma2.setBounds(600, 50, 300, 150);
        histograma2.setVisible(false);

        histograma3 = new PanelHistograma(lista);
        histograma3.setBounds(910, 50, 300, 150);
        histograma3.setVisible(false);
    }

    private void initComponents() {
        initLabels();
        initMenuBar();
        initSelectores();
        initImagenes();
        initBotones();
        initSliders();
        initHistogramas();

        // Crear contenedor y establecer diseño
        Container contenedor = this.getContentPane();
        contenedor.setLayout(null);
        contenedor.add(menuBar);
        contenedor.add(lbImgPrc);
        contenedor.add(lbCanal1);
        contenedor.add(lbCanal2);
        contenedor.add(lbCanal3);
        contenedor.add(opcionesPrincipales);
        contenedor.add(opcionesSecundarias);
        contenedor.add(slider1);
        contenedor.add(valSlider1);
        contenedor.add(slider2);
        contenedor.add(valSlider2);
        contenedor.add(botonActualizar);
        contenedor.add(panelOrg);
        contenedor.add(img1);
        contenedor.add(img2);
        contenedor.add(img3);
        contenedor.add(histograma1);
        contenedor.add(histograma2);
        contenedor.add(histograma3);

        this.setJMenuBar(menuBar);
        this.setVisible(true);
    }

    private JMenuItem createMenuItemVisualizador(String text, final int arg) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                visualizarImagen(arg);
            }
        });
        return menuItem;
    }

    private JMenuItem createMenuItemGuardar(String text, final int arg) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarImg(arg);
            }
        });
        return menuItem;
    }

    private void visualizarImagen(int arg) {
        BufferedImage imagenAux = null;
        switch (arg) {
            case 1 ->
                imagenAux = panelOrg.getBufferImage();
            case 2 ->
                imagenAux = img1.getBufferImage();
            case 3 ->
                imagenAux = img2.getBufferImage();
            case 4 ->
                imagenAux = img3.getBufferImage();
        }
        String nombre = "Imagen" + id;
        FrameImagen imagenNueva = new FrameImagen(nombre, imagenAux);
        imagenNueva.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Agrega el nuevo InternalFrame al JDesktopPane del FramePrincipal
        this.getParent().add(imagenNueva);
        imagenNueva.toFront();

        id++;

    }

    private void abrirChoose() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de imagen", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        int seleccion = fileChooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            lector.setPath(path);
            lector.leerBufferedImagen();
            panelOrg.addImagen(lector.getImagen());
            img1.addImagen(lector.getImagen());
            img2.addImagen(lector.getImagen());
            img3.addImagen(lector.getImagen());
        }

    }

    private void reiniciarImg() {
        panelOrg.addImagen(lector.getImagen());
    }

    private void guardarImg(int arg) {
        // Crear un selector de archivos
        JFileChooser fileChooser = new JFileChooser();
        BufferedImage imagenAux = null;
        switch (arg) {
            case 1 ->
                imagenAux = panelOrg.getBufferImage();
            case 2 ->
                imagenAux = img1.getBufferImage();
            case 3 ->
                imagenAux = img2.getBufferImage();
            case 4 ->
                imagenAux = img3.getBufferImage();
        }
        fileChooser.setSelectedFile(new File("imagenProcesada.png"));
        // Mostrar el selector de archivos
        int result = fileChooser.showSaveDialog(null);

        // Verificar si el usuario seleccionó un archivo
        if (result == JFileChooser.APPROVE_OPTION) {
            // Obtener la imagen desde algún lugar (por ejemplo, una imagen que ya tienes)

            // Obtener la ruta y el nombre del archivo seleccionado por el usuario
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            // Guardar la imagen en el archivo especificado por el usuario
            saveImageToFile(imagenAux, filePath);
        }
    }

    private void saveImageToFile(BufferedImage image, String filePath) {
        try {
            // Obtener la extensión del archivo
            String formatName = "png"; // En este ejemplo se guarda como PNG

            // Escribir la imagen en el archivo
            ImageIO.write(image, formatName, new File(filePath));

            // Mostrar un mensaje de éxito
            JOptionPane.showMessageDialog(null, "La imagen se ha guardado exitosamente.");
        } catch (IOException ex) {
            // Mostrar un mensaje de error si ocurre alguna excepción al guardar la imagen
            JOptionPane.showMessageDialog(null, "Error al guardar la imagen: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarSelect(int opcion) {
        opcionesSecundarias.setVisible(true);
        String[] op = new String[]{"RGB", "RGB Grises"};
        String[] op2 = new String[]{"RGB a CMY", "RGB to YIQ", "RGB to HSI", "RGB to HSV", "RGB to LAB"};
        String[] op3 = new String[]{"CMY a RGB", "YIQ a RGB", "HSI a RGB", "HSV a RGB", "LAB a RGB"};
        String[] op4 = new String[]{"Un Umbral", "Dos Umbrales", "Invertir Binarizacion"};
        opcionesSecundarias.removeAllItems();
        if(opcion != 4){
            slider1.setVisible(false);
            valSlider1.setVisible(false);
            slider2.setVisible(false);
            valSlider2.setVisible(false);
        }
        switch (opcion) {
            case 1:
                for (String o : op) {
                    opcionesSecundarias.addItem(o);
                }
                break;
            case 2:
                for (String o : op2) {
                    opcionesSecundarias.addItem(o);
                }
                break;
            case 3:
                for (String o : op3) {
                    opcionesSecundarias.addItem(o);
                }
                break;
            case 4:
                slider1.setVisible(true);
                valSlider1.setVisible(true);
                for (String o : op4) {
                    opcionesSecundarias.addItem(o);
                }
                break;
            default:
                opcionesSecundarias.setVisible(false);
                break;
        }

    }
    
    private void actualizarSegundoSelect(){
        int op = opcionesPrincipales.getSelectedIndex();
        int op2 = opcionesSecundarias.getSelectedIndex();
        if (op ==4 && op2 ==1){
            slider2.setVisible(true);
            valSlider2.setVisible(true);
        }
        else{
            slider2.setVisible(false);
            valSlider2.setVisible(false);
        }

    }

    private void actualizarImg() {
        int opcion = opcionesPrincipales.getSelectedIndex();
        int opcion2 = opcionesSecundarias.getSelectedIndex();
        convertirHistograma(false);
        switch (opcion) {
            case 1 -> 
                extraerRGB(opcion2);
            case 2 ->
                cambiarModelo(opcion2);
            case 3 ->
                regresarModelo(opcion2);
            case 4 ->
                binarizarImagen(opcion2);
            default ->
                opcionesSecundarias.setVisible(false);
        }
        

    }

    private void extraerRGB(int canal) {
        if (canal == 0) {
            estrategia = new ExtraerCanal();
        } else {
            estrategia = new RGBGris();
        }
        BufferedImage imgPrc = lector.getBufferedImagen();
        BufferedImage[] img = estrategia.convertir(imgPrc);
        int[][] data = estrategia.extraerData();
        
        img1.addImagen(img[0]);
        img2.addImagen(img[1]);
        img3.addImagen(img[2]);
        panelOrg.addImagen(img[3]);
        
        histograma1.setData(data[0], Color.RED);
        histograma2.setData(data[1], Color.GREEN);
        histograma3.setData(data[2], Color.BLUE);

        histograma1.setVisible(true);
        histograma2.setVisible(true);
        histograma3.setVisible(true);

    }

    private void cambiarModelo(int modelo) {
        switch (modelo) {
            default ->
                estrategia = new RGBtoCMYK();
            case 1 ->
                estrategia = new RGBtoYIQ();
            case 2 ->
                estrategia = new RGBtoHSI();
            case 3 ->
                estrategia = new RGBtoHSV();
            case 4 ->
                estrategia = new RGBtoLAB();
        }
        BufferedImage imgPrc = lector.getBufferedImagen();
        BufferedImage[] img = estrategia.convertir(imgPrc);
        int[][] data = estrategia.extraerData();
        img1.addImagen(img[0]);
        img2.addImagen(img[1]);
        img3.addImagen(img[2]);
        panelOrg.addImagen(img[3]);
        histograma1.setData(data[0], Color.BLUE);
        histograma2.setData(data[1], Color.BLUE);
        histograma3.setData(data[2], Color.BLUE);

        histograma1.setVisible(true);
        histograma2.setVisible(true);
        histograma3.setVisible(true);

    }

    private void regresarModelo(int modelo) {
        switch (modelo) {
            default ->
                estrategia = new CMYKtoRGB();
            case 1 -> 
                estrategia = new YIQtoRGB();
            case 2 ->
                estrategia = new HSItoRGB();
            case 3 -> 
                estrategia = new HSVtoRGB();
            case 4 ->
                estrategia = new LABtoRGB();
            
        }
        BufferedImage imgPrc = lector.getBufferedImagen();
        BufferedImage[] img = estrategia.convertir(imgPrc);
        int[][] data = estrategia.extraerData();
        img1.addImagen(img[0]);
        img2.addImagen(img[1]);
        img3.addImagen(img[2]);
        panelOrg.addImagen(img[3]);
        histograma1.setData(data[0], Color.BLUE);
        histograma2.setData(data[1], Color.BLUE);
        histograma3.setData(data[2], Color.BLUE);

        histograma1.setVisible(true);
        histograma2.setVisible(true);
        histograma3.setVisible(true);
    }

    private void binarizarImagen(int modelo) {
        int[] umbral;
        switch (modelo) {
            default:
                estrategia = new Umbral();
                umbral = new int[]{slider1.getValue()};
                break;
            case 1:
                estrategia = new DosUmbral();
                umbral = new int[]{slider1.getValue(), slider2.getValue()};
                break;
            case 2:
                estrategia = new InvertirBinarizacion();
                umbral = new int[]{slider1.getValue()};
                break;
        }
        BufferedImage imgPrc = lector.getBufferedImagen();
        BufferedImage[] img = estrategia.convertir(imgPrc, umbral);
        int[][] data = estrategia.extraerData();
        img1.addImagen(img[0]);
        img2.addImagen(img[1]);
        img3.addImagen(img[2]);
        panelOrg.addImagen(img[3]);
        histograma1.setData(data[0], Color.BLUE);
        histograma2.setData(data[1], Color.BLUE);
        histograma3.setData(data[2], Color.BLUE);

        histograma1.setVisible(true);
        histograma2.setVisible(true);
        histograma3.setVisible(true);
    }
    
    private void abrirFrameOperaciones(){
        FrameOperaciones frame = new FrameOperaciones(lector.getPath());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Agrega el nuevo InternalFrame al JDesktopPane del FramePrincipal
        this.getParent().add(frame);
        frame.toFront();
    }

    private void convertirHistograma(boolean isAcumilativo){
        
        double[][] datos = new double[3][256];
        datos[0]= histograma1.cambiarAcumulativo(isAcumilativo);
        datos[1]= histograma2.cambiarAcumulativo(isAcumilativo);
        datos[2]= histograma3.cambiarAcumulativo(isAcumilativo);

        img1.cambiarAcumulativo(datos[0], isAcumilativo);
        img2.cambiarAcumulativo(datos[1], isAcumilativo);
        img3.cambiarAcumulativo(datos[2], isAcumilativo);

    }
}