package outils;

import java.util.LinkedList;

public class NodeWeighted {
    // The int n and String name are just arbitrary attributes
    // we've chosen for our nodes these attributes can of course
    // be whatever you need
    public int n;
    public String name;
    private boolean visited;
    LinkedList<EdgeWeighted> edges;
    int x;
    int y;
    String objet;

    public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<EdgeWeighted> getEdges() {
		return edges;
	}

	public void setEdges(LinkedList<EdgeWeighted> edges) {
		this.edges = edges;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getObjet() {
		return objet;
	}

	public void setObjet(String objet) {
		this.objet = objet;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public NodeWeighted(int n, String name, int x, int y, String objet) {
        this.n = n;
        this.name = name;
        visited = false;
        edges = new LinkedList<>();
        this.x=x;
        this.y=y;
        this.objet=objet;    
    }
    
    public NodeWeighted(int n, String name) {
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