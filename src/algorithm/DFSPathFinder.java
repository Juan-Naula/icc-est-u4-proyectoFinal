package algorithm;

import java.util.*;

import models.Graph;
import models.Node;
import models.PathResult;

public class DFSPathFinder<T> implements PathFinder<T> {

    @Override
    public PathResult<T> find(Graph<T> graph, Node<T> start, Node<T> end) {

        List<Node<T>> visitados = new ArrayList<>();
        Map<Node<T>, Node<T>> parent = new HashMap<>();
        Set<Node<T>> visitedSet = new HashSet<>();

        parent.put(start, null);

        boolean found = dfsRecursive(graph, start, end, visitedSet, visitados, parent);

        if (found) {
            return new PathResult<>(visitados, buildPath(parent, end));
        }

        return new PathResult<>(visitados, List.of());
    }

    private boolean dfsRecursive(
            Graph<T> graph,
            Node<T> current,
            Node<T> end,
            Set<Node<T>> visitedSet,
            List<Node<T>> visitados,
            Map<Node<T>, Node<T>> parent
    ) {
        visitedSet.add(current);
        visitados.add(current);

        if (current.equals(end)) {
            return true; // destino encontrado
        }

        for (Node<T> neighbor : graph.getNeighborsActualizado(current)) {
            if (!visitedSet.contains(neighbor)) {
                parent.put(neighbor, current);
                boolean found = dfsRecursive(graph, neighbor, end, visitedSet, visitados, parent);
                if (found) return true; // cortar recursión
            }
        }
        return false;
    }

    private List<Node<T>> buildPath(Map<Node<T>, Node<T>> parent, Node<T> end) {
        List<Node<T>> path = new ArrayList<>();
        for (Node<T> at = end; at != null; at = parent.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
}
