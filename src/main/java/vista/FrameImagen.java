package vista;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class FrameImagen extends JInternalFrame {
    private BufferedImage image;

    public FrameImagen(String titulo, BufferedImage image) {
        super(titulo, true, true, true, true);
        this.image = image;
        setSize(image.getWidth(), image.getHeight());

        JLabel label = new JLabel(new ImageIcon(image));
        add(label);
        setLocation(50, 50); // Establece la ubicación inicial del internal frame
        setVisible(true);
    }

    public BufferedImage getImage(){
        return this.image;
    }
    

}
