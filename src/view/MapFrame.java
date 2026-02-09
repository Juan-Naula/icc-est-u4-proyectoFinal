package view;

import algorithm.*;
import models.*;
import util.TimeLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MapFrame extends JFrame {

    private final MapPanel mapPanel;
    private final Graph<String> graph;

    private Node<String> startNode;
    private Node<String> endNode;

    private final PathFinder<String> bfs = new BFSPathFinder<>();
    private final PathFinder<String> dfs = new DFSPathFinder<>();

    private Node<String> draggingNode = null;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;
    private int nodeCounter = 0;
    
    private Node<String> nodeForConnection = null;

    public MapFrame(Image mapImage, Graph<String> graph) {
        this.graph = graph;
        this.nodeCounter = graph.getNodes().size();

        setTitle("BFS vs DFS - Mapa");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mapPanel = new MapPanel(mapImage, graph);

        JButton bfsBtn = new JButton("BFS");
        JButton dfsBtn = new JButton("DFS");
        JCheckBox explorationMode = new JCheckBox("Modo exploración", true);

        bfsBtn.addActionListener(e -> runAlgorithm(bfs, explorationMode.isSelected()));
        dfsBtn.addActionListener(e -> runAlgorithm(dfs, explorationMode.isSelected()));

        JPanel controls = new JPanel();
        controls.add(bfsBtn);
        controls.add(dfsBtn);
        controls.add(explorationMode);

        JToggleButton editToggle = new JToggleButton("Modo Edición");
        JToggleButton connectionMode = new JToggleButton("Modo Conexión");
        controls.add(editToggle);
        controls.add(connectionMode);

        JButton clearNodes = new JButton("Limpiar nodos");
        clearNodes.addActionListener(e -> {
            graph.clear();
            mapPanel.setResult(null, true);
            mapPanel.setStartNode(null);
            mapPanel.setEndNode(null);
            startNode = null;
            endNode = null;
            mapPanel.repaint();
        });
        controls.add(clearNodes);

        add(mapPanel, BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);

        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (editToggle.isSelected()) {
                    Node<String> found = findNodeAt(e.getX(), e.getY());
                    if (found != null) {
                        draggingNode = found;
                        dragOffsetX = found.getX() - e.getX();
                        dragOffsetY = found.getY() - e.getY();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                draggingNode = null;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Node<String> clicked = findNodeAt(e.getX(), e.getY());

                // Modo Conexión
                if (connectionMode.isSelected()) {
                    if (clicked == null) {
                        System.out.println("No hay nodo en esa posición");
                        return;
                    }
                    
                    if (nodeForConnection == null) {
                        // Primer nodo
                        nodeForConnection = clicked;
                        System.out.println("Primer nodo seleccionado: " + nodeForConnection);
                    } else if (nodeForConnection == clicked) {
                        // Clic en el mismo nodo cancela
                        nodeForConnection = null;
                        System.out.println("Selección cancelada");
                    } else {
                        // Segundo nodo: conectar sin validaciones
                        graph.addEdge(nodeForConnection, clicked);
                        System.out.println("✓ Conectado: " + nodeForConnection + " <-> " + clicked);
                        mapPanel.repaint();
                        nodeForConnection = null;
                    }
                }
                // Modo Edición
                else if (editToggle.isSelected()) {
                    if (clicked == null) {
                        nodeCounter++;
                        Node<String> newNode = new Node<>("N" + nodeCounter, e.getX(), e.getY());
                        graph.addNode(newNode);
                        System.out.println("Nodo creado: N" + nodeCounter);
                        mapPanel.repaint();
                    }
                } 
                // Modo selección de inicio/destino
                else {
                    if (clicked == null) return;

                    if (startNode == null) {
                        startNode = clicked;
                        mapPanel.setStartNode(startNode);
                        System.out.println("Inicio: " + startNode);
                    } else if (endNode == null) {
                        endNode = clicked;
                        mapPanel.setEndNode(endNode);
                        System.out.println("Destino: " + endNode);
                    } else {
                        startNode = clicked;
                        endNode = null;
                        mapPanel.setStartNode(startNode);
                        mapPanel.setEndNode(null);
                        System.out.println("Nuevo inicio: " + startNode);
                    }
                }
            }
        });

        mapPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (editToggle.isSelected() && draggingNode != null) {
                    draggingNode.setX(e.getX() + dragOffsetX);
                    draggingNode.setY(e.getY() + dragOffsetY);
                    mapPanel.repaint();
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void runAlgorithm(PathFinder<String> finder, boolean exploration) {

        if (startNode == null || endNode == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona nodo inicio y destino",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String algorithmName = finder instanceof BFSPathFinder ? "BFS" : "DFS";

        long startTime = System.nanoTime();
        PathResult<String> result = finder.find(graph, startNode, endNode);
        long endTime = System.nanoTime();

        TimeLogger.log(algorithmName, endTime - startTime);

        mapPanel.setStartNode(startNode);
        mapPanel.setEndNode(endNode);
        mapPanel.setResult(result, exploration);

        if (result.getPath().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró ruta entre los nodos seleccionados.",
                    "Resultado",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private Node<String> findNodeAt(int x, int y) {
        for (Node<String> node : graph.getNodes()) {
            int dx = x - node.getX();
            int dy = y - node.getY();
            if (Math.sqrt(dx * dx + dy * dy) <= 16) {
                return node;
            }
        }
        return null;
    }
}

    


