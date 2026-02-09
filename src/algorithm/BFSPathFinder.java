package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import models.Graph;
import models.Node;
import models.PathResult;

public class BFSPathFinder<T> implements PathFinder<T> {

    @Override
    public PathResult<T> find(Graph<T> graph, Node<T> start, Node<T> end) {
        Queue<Node<T>> queue = new LinkedList<>();
        Set<Node<T>> visited = new HashSet<>();

        Map<Node<T>, Node<T>> parente = new HashMap<>();
        List<Node<T>> visitados = new ArrayList<>();

        queue.add(start);
        visited.add(start);
        parente.put(start, null);

        while (!queue.isEmpty()) {
            Node<T> current = queue.poll();
            visitados.add(current);

            if (current.equals(end)) {
                return new PathResult<>(visitados, buildPath(parente, end));
            }

            for (Node<T> conocido : graph.getNeighborsActualizado(current)) {
                if (!visited.contains(conocido)) {
                    visited.add(conocido);
                    parente.put(conocido, current);
                    queue.add(conocido);
                }
            }

        }
        return new PathResult<>(visitados, List.of());
        
    }

    private List<Node<T>> buildPath(Map<Node<T>,Node<T>> parente, Node end) {
        List<Node<T>> path = new ArrayList<>();
        for (Node<T> at = end; at != null; at = parente.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
    
}
