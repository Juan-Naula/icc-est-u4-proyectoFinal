import models.Graph;
import models.Node;
import view.MapFrame;

import javax.swing.*;
import java.awt.*;

public class App {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Image mapImage = new ImageIcon(
                    App.class.getResource("/mapa.png")
            ).getImage();

            if (mapImage.getWidth(null) == -1) {
                JOptionPane.showMessageDialog(
                        null,
                        "No se pudo cargar la imagen del mapa",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Graph<String> graph = new Graph<>();

            Node<String> A = new Node<>("A", 250, 180);
            Node<String> B = new Node<>("B", 420, 180);
            Node<String> C = new Node<>("C", 420, 330);
            Node<String> D = new Node<>("D", 250, 330);
            Node<String> E = new Node<>("E", 340, 260);

            graph.addNode(A);
            graph.addNode(B);
            graph.addNode(C);
            graph.addNode(D);
            graph.addNode(E);

            graph.addEdge(A, B);
            graph.addEdge(B, C);
            graph.addEdge(C, D);
            graph.addEdge(D, A);
            graph.addEdge(A, E);
            graph.addEdge(E, C);

            new MapFrame(mapImage, graph);
        });
    }
}
