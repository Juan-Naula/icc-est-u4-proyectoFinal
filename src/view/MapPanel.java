package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;

import javax.swing.JPanel;

import models.Graph;
import models.Node;
import models.PathResult;

public class MapPanel extends JPanel{
    private Image mapImage;
    private Graph graph;
    private PathResult result;
    private boolean showExploration = true;
    private Node startNode;
    private Node endNode;
    private Node connectionNode;
    private Node connectStart;
    private int previewX;
    private int previewY;
    private boolean showPreview = false;

    public MapPanel(Image mapImage, Graph graph) {
        this.mapImage = mapImage;
        this.graph = graph;

        setPreferredSize(new Dimension(
                mapImage.getWidth(null),
                mapImage.getHeight(null)
        ));
    }

    public void setResult(PathResult result, boolean showExploration) {
        this.result = result;
        this.showExploration = showExploration;
        repaint();
    }

    public void setStartNode(Node start) {
        this.startNode = start;
        repaint();
    }

    public void setEndNode(Node end) {
        this.endNode = end;
        repaint();
    }

    public void setConnectionNode(Node node) {
        this.connectionNode = node;
        repaint();
    }

    public void setConnectStart(Node node) {
        this.connectStart = node;
        repaint();
    }

    public void setPreview(int x, int y, boolean show) {
        this.previewX = x;
        this.previewY = y;
        this.showPreview = show;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(mapImage, 0, 0, this);

        if (graph == null) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        g2.setColor(Color.GRAY);
        for (Object o : graph.getNodes()) {
            Node node = (Node) o;
            for (Object nObj : graph.getNeighbors(node)) {
                Node neighbor = (Node) nObj;
                g2.drawLine(
                        node.getX(), node.getY(),
                        neighbor.getX(), neighbor.getY()
                );
            }
        }

        for (Object o : graph.getNodes()) {
            Node node = (Node) o;
            if (node.equals(startNode)) {
                g2.setColor(Color.GREEN);
            } else if (node.equals(endNode)) {
                g2.setColor(Color.MAGENTA);
            } else {
                g2.setColor(Color.BLUE);
            }
            g2.fillOval(
                    node.getX() - 6,
                    node.getY() - 6,
                    12,
                    12
            );
            if (connectionNode != null && node.equals(connectionNode)) {
                g2.setColor(Color.ORANGE);
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(node.getX() - 10, node.getY() - 10, 20, 20);
                g2.setStroke(new BasicStroke(2));
            }
        }

        if (showPreview && connectStart != null) {
            g2.setColor(new Color(0, 120, 215));
            float[] dash = {6f, 6f};
            g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0));
            g2.drawLine(connectStart.getX(), connectStart.getY(), previewX, previewY);
        }

        if (result == null) return;

        if (showExploration) {
            g2.setColor(new Color(255, 165, 0));
            for (Object o : result.getVisitados()) {
                Node node = (Node) o;
                g2.fillOval(
                        node.getX() - 6,
                        node.getY() - 6,
                        12,
                        12
                );
            }
        }

        List<Node> path = result.getPath();
        if (path != null && path.size() > 0) {
            g2.setColor(Color.RED);
            for (int i = 0; i < path.size() - 1; i++) {
            Node a = path.get(i);
            Node b = path.get(i + 1);
            g2.drawLine(
            a.getX(), a.getY(),
            b.getX(), b.getY()
        );
    }
        }
    }
}
