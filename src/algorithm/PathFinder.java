package algorithm;

import models.Graph;
import models.Node;
import models.PathResult;

public interface PathFinder<T> {
    PathResult<T> find(Graph<T> graph, Node<T> start, Node<T> end);

    
}
