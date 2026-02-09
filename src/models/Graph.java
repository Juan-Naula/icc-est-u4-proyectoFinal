package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class Graph<T> {
    //List <Node<T>> nodes;
    
    //Listado de nodos conocidos
    //1ra forma
    //private List<Node<T>> conocidos;
    
    //2da forma
    private Map<Node<T>, List<Node<T>>> mapa; 

    public Graph() {
        //this.nodes = new ArrayList<Node<T>>();
        this.mapa = new HashMap<Node<T>, List<Node<T>>>();
    }

    //Insertar un vertice o nodo
    public void addNode(Node<T> node){
        mapa.putIfAbsent(node, new ArrayList<>());
    }

    //Grafo no dirigido
    public void addEdge(Node<T> n1, Node<T> n2){
        addNode(n1);
        addNode(n2);
        //List<Node<T>> listadoNodo = mapa.get(n1);
        //listadoNodo.add(n2);
        mapa.get(n1).add(n2);
        mapa.get(n2).add(n1);
    }

    public void addEdgeActualizado(Node<T> n1, Node<T> n2){
        addNode(n1);
        addNode(n2);
        mapa.get(n1).add(n2);
        //Del mapa obtengo el listado
        //get(n1) -> Listado de N1
        //add(n2) -> Agrega N2 al Listado de N1
    }

    public void printGraph(){
        for (Map.Entry<Node<T>, List<Node<T>>> entry : mapa.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            for (Node<T> neighbor : entry.getValue()) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }

    public List<Node<T>> getNeighbors(Node<T> n) {
        return mapa.getOrDefault(n, List.of());
    }

    public List<Node<T>> getNeighborsActualizado(Node<T> n) {
        return mapa.getOrDefault(n, List.of());
    }

    public void bfs(Node<T> start){
        Set<Node<T>> visitados = new LinkedHashSet<>();
        Queue<Node<T>> queue = new LinkedList<>();

        visitados.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            //Para romper, se vacia la cola
            Node<T> current = queue.poll();
            System.out.print(current.getValue() + " ");

            for (Node<T> conocido : getNeighborsActualizado(current)) {
                if (!visitados.contains(conocido)) {
                    visitados.add(conocido);
                    queue.add(conocido);
                }
            }
        }

    }

    public void dfs(Node<T> start){
        Set<Node<T>> visitados = new LinkedHashSet<>();
        dfsRecursive(start, visitados);
    }

    private void dfsRecursive(Node<T> current, Set<Node<T>> visitados) {
        visitados.add(current);
        System.out.print(current.getValue() + " ");

        for (Node<T> conocido : getNeighborsActualizado(current)) {
            if (!visitados.contains(conocido)) {
                dfsRecursive(conocido, visitados);
            }
        }
    }

    public List<Node<T>> getNodes() {
        return new ArrayList<>(mapa.keySet());
    }

    public void clear() {
        mapa.clear();
    }
}
