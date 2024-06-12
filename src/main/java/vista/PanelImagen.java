package vista;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Clase que representa un panel para mostrar una imagen.
 */
public class PanelImagen extends JPanel {
    private Image imagen;
    
    /**
     * Constructor que inicializa el panel con una imagen.
     * 
     * @param imagen La imagen a mostrar en el panel.
     */
    public PanelImagen(Image imagen) {
        super();
        this.imagen = imagen;
        this.setSize(imagen.getWidth(null), imagen.getHeight(null));
    }
    
    /**
     * Método para agregar una nueva imagen al panel.
     * 
     * @param imagen La nueva imagen a mostrar en el panel.
     */
    public void addImagen(Image imagen) {
        this.imagen = imagen;
        repaint(); // Repintar el panel para mostrar la nueva imagen
    }
    
    public Image getImage(){
        return this.imagen;
    }
    
    public BufferedImage getBufferImage() {
        if (imagen instanceof BufferedImage) {
            return (BufferedImage) imagen;
        }
        
        // Create a new buffered image with the same dimensions and type as the original image
        BufferedImage bufferedImage = new BufferedImage(imagen.getWidth(null), imagen.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        
        // Draw the original image onto the buffered image
        Graphics g = bufferedImage.createGraphics();
        g.drawImage(imagen, 0, 0, null);
        g.dispose();
        
        return bufferedImage;
    }
    
    /**
     * Método para dibujar la imagen en el panel.
     * 
     * @param g El contexto gráfico donde se dibujará la imagen.
     */
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g); // Llamar al método de la clase base para pintar el fondo
    	
    	// Dibujar la imagen en su tamaño normal
    	if (imagen != null) {
            g.drawImage(imagen, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}
