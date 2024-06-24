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
    private BufferedImage bufferedImage;
    private BufferedImage imagenAcumulada;
    private boolean isAcumilativo;
    
    /**
     * Constructor que inicializa el panel con una imagen.
     * 
     * @param imagen La imagen a mostrar en el panel.
     */
    public PanelImagen(Image imagen) {
        super();
        this.imagen = imagen;
        this.isAcumilativo =false;
        BufferImage();
        this.setSize(imagen.getWidth(null), imagen.getHeight(null));
    }
    
    /**
     * Método para agregar una nueva imagen al panel.
     * 
     * @param imagen La nueva imagen a mostrar en el panel.
     */
    public void addImagen(Image imagen) {
        this.imagen = imagen;
        BufferImage();
        repaint(); // Repintar el panel para mostrar la nueva imagen
    }  
    
    public Image getImage(){
        return this.imagen;
    }
    
    public void BufferImage() {
        if (imagen instanceof BufferedImage) {
            this.bufferedImage = (BufferedImage) imagen;
        }
        
        // Create a new buffered image with the same dimensions and type as the original image
        BufferedImage bufferedImage = new BufferedImage(imagen.getWidth(null), imagen.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        
        // Draw the original image onto the buffered image
        Graphics g = bufferedImage.createGraphics();
        g.drawImage(imagen, 0, 0, null);
        g.dispose();
        
        this.bufferedImage = bufferedImage;
    }
    
    public BufferedImage getBufferImage(){
        return this.bufferedImage;
    }

    /**
     * Método para dibujar la imagen en el panel.
     * 
     * @param g El contexto gráfico donde se dibujará la imagen.
     */
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g); // Llamar al método de la clase base para pintar el fondo
    	
        Image imagenTemp = isAcumilativo ? imagenAcumulada : imagen ;

    	// Dibujar la imagen en su tamaño normal
    	if (imagenTemp != null) {
            g.drawImage(imagenTemp, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    
    }

    public void cambiarAcumulativo(double[] datos, boolean isAcumilativo){
        BufferImage();
        int ancho = bufferedImage.getWidth();
        int alto = bufferedImage.getHeight();
        int pixel=0;

        BufferedImage imgTemp = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        for (int xImg=0; xImg<ancho;xImg++){
            for(int yImg=0; yImg<alto; yImg++){
                pixel = bufferedImage.getRGB(xImg, yImg);
                int rojo = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >> 8;
                int azul = pixel & 0x000000ff;
                //System.out.println("Rojo " + rojo + " datos[rojo] " + datos[rojo]);    
                rojo *= datos[rojo];
                azul *= datos[azul];
                verde *= datos[verde];
                
                //System.out.println(rojo);
                pixel = (255 << 24) | ((int) (rojo) << 16) | ((int) (verde) << 8) | (int) (azul);

                imgTemp.setRGB(xImg, yImg, pixel);
            }
        }
        this.imagenAcumulada = imgTemp;
        this.isAcumilativo=isAcumilativo;

        repaint();
    }
}
