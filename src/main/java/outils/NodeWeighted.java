package outils;

import java.util.LinkedList;

public class NodeWeighted {
    // The int n and String name are just arbitrary attributes
    // we've chosen for our nodes these attributes can of course
    // be whatever you need
    int n;
    String name;
    private boolean visited;
    LinkedList<EdgeWeighted> edges;
    int x;
    int y;
    String objet;

    NodeWeighted(int n, String name, int x, int y, String objet) {
        this.n = n;
        this.name = name;
        visited = false;
        edges = new LinkedList<>();
        this.x=x;
        this.y=y;
        this.objet=objet;    
    }
    
    NodeWeighted(int n, String name) {
        this.n = n;
        this.name = name;
        visited = false;
        edges = new LinkedList<>();   
    }



	@Override
	public String toString() {
		return "NodeWeighted [x=" + x + ", y=" + y + ", objet=" + objet + "]";
	}

	boolean isVisited() {
        return visited;
    }

    void visit() {
        visited = true;
    }

    void unvisit() {
        visited = false;
    }
    
    

    
}