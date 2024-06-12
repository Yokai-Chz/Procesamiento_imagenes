package main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import vista.FramePrincipal;

public class App {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de imagen", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        int seleccion = fileChooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println(path);
            // Crear un JFrame principal
            JFrame frame = new JFrame("Aplicación Principal");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 500);

            // Crear un JDesktopPane para contener los JInternalFrames
            JDesktopPane desktopPane = new JDesktopPane();
            

            // Crear un JInternalFrame utilizando la clase FramePrincipal
            FramePrincipal internalFrame = new FramePrincipal(path);
            
            // Agregar el JInternalFrame al JDesktopPane
            desktopPane.add(internalFrame);

            
            // Agregar el JDesktopPane al JFrame principal
            frame.add(desktopPane);

            // Establecer el tamaño y hacer visible el JFrame principal
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);

        }

    }
}
