package vista;

import java.awt.*;
import javax.swing.*;

public class PanelHistograma extends JPanel {

    private int[] data; // Arreglo de datos para el histograma
    private int total;
    private double[] datos = new double[256];
    private double[] datosAcumulados = new double[256];
    private Color c;
    private boolean isAcumilativo;

    public PanelHistograma(int[] data) {
        this.data = data;
        this.isAcumilativo = false;
    }

    public PanelHistograma(int[] data, Color c) {
        this.data = data;
        this.c = c;
    }

    public void setData(int[] newData, Color color) {
        this.data = newData;
        this.c = color;
        repaint(); // Vuelve a dibujar el panel con los nuevos datos
    }

    public void setData(int[] newData) {
        this.data = newData;
        repaint(); // Vuelve a dibujar el panel con los nuevos datos
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        prepararDatos();
        // Dibujar ejes X e Y
        drawAxes(g);
        // Dibujar el histograma
        drawHistogram(g);
    }

    private void prepararDatos() {
        int contador = 0;
        for (int i = 0; i < data.length; i++) {
            contador += data[i];

        }
        total = contador;
        double suma = 0;
        for (int i = 0; i < data.length; i++) {
            datos[i] = (double) data[i] / total;
            suma += datos[i];
            datosAcumulados[i] = suma;
        }
    }

    private void drawAxes(Graphics g) {
        g.setColor(Color.BLACK);
        // Dibujar eje X
        g.drawLine(10, getHeight() - 20, getWidth() - 10, getHeight() - 20);

        g.drawLine(35, getHeight() - 20, 35, getHeight() - 10);
        g.drawString("0", 35, getHeight());

        // Calcular la posición x para la marca de 127
        int x127 = 35 + 127;
        g.drawLine(x127, getHeight() - 20, x127, getHeight() - 10);
        g.drawString("127", x127, getHeight()); // Valor de 127

        // Calcular la posición x para la marca del valor 255
        int x255 = 35 + 255; // Marca de 255
        g.drawLine(x255, getHeight() - 20, x255, getHeight() - 10);
        g.drawString("255", x255 - 10, getHeight()); // Valor de 255

        // Dibujar eje Y
        g.drawLine(30, 10, 30, getHeight() - 10);

        // Dibujar marcas en el eje Y (cada 100 unidades)
        int markSpacingY = (getHeight() - 30) / 4;
        double values = getMaxCount() / 4;
        for (int i = 0; i <= 4; i++) {
            int y = getHeight() - (i * markSpacingY) - 20;
            g.drawLine(25, y, 30, y);
            g.drawString(String.format("%.2f", values * i), 0, y); // Valor de 255

        }
    }

    private void drawHistogram(Graphics g) {
        if (data == null || data.length != 256) {
            System.out.println("Error: El arreglo de datos debe tener una longitud de 256.");
            return;
        }
        // Calcular la altura máxima del histograma
        double yHeight = getHeight() - 30;
        double maxCount = getMaxCount();
        double scaleFactor = (double) yHeight / maxCount;
        int yOffset = getHeight() - 20;

        g.setColor(c);
        for (int i = 0; i < 256; i++) {

            int barHeight = 0;

            barHeight = isAcumilativo ? (int) (yOffset - (datosAcumulados[i] * scaleFactor))
                    : (int) (yOffset - (datos[i] * scaleFactor));

            if (barHeight != yOffset) {
                g.drawLine(35 + i, yOffset, 35 + i, barHeight);
            }
        }

    }

    private double getMaxCount() {
        double max = 0;
        double[] newDatos;

        newDatos = isAcumilativo ? datosAcumulados : datos;

        for (double value : newDatos) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public double[] cambiarAcumulativo(boolean isAcumilativo) {
        this.isAcumilativo = isAcumilativo;
        repaint();
        return datosAcumulados;
    }
}
