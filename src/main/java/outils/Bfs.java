package outils;

public class Bfs {


	/**
	Breadth First Search (BFS) est un algo de parcours des noeuds d'un graphe.

	Il necessite une classe principale qui contient l'algo et une classe (appelée State) qui décrit la situation à chaque étape .

	- La classe State contient les attributs essentiels afin de décrire la situation. Il est important de bien parametrer la methode .equals() afin de n'inclure que le strict minimum: 
	l'attribut qu'on cherche à optimiser, et l'attribut qui décrit le chemin parcouru. (voir 2022 day16 pour un exemple).
	- La classe principale contient un main qui lance la methode bfs():

	private int bfs(valeurs initiales){

	//on crée un etat initial
	State stateInitial = new State();
	//remplir l'objet stateInitial avec les valeurs initiales

		//La queue contiendra tous les noeuds(=les states) à visiter
		Queue<State> queue = new LinkedList<>();
		queue.add(stateInitial);

		//on ne visitera que les noeuds nouveaux afin de ne pas tourner en rond, donc on a besoin de sauvegarder l'ensemble des noeuds deja visités
		Set<State> visitedStates = new HashSet<>();
		visitedStates.add(stateInitial);

	while (!queue.isEmpty()) { //on continue tant que la queue contient encore des noeuds à visiter (mais on peut spécifier un arrêt apres si on a trouvé un noeud cherché)

	State stateActuel = queue.poll();//on recupere le noeud à étudier


	if (condition sur un attribut de State) { //condition d'arrêt si on cherche un noeud spécifique (si on cherche à maximiser qqchose, on peut analyser tous les noeuds, sauvegarder le max et renvoyer ce max à la fin: attention dans ce cas il faudra peut être ajouter des optimisation basées sur des hypothèses pour gagner du temps)
	//fini
	}else{

	//Traitement des différents cas: en général chaque cas interessant permet de générer un nouveau State qu'il faudra analyser par la suite

	 //pour un cas donné, on l'ajoute à la queue si c'est un nouveau noeud
	 if (!visitedStates.contains(nextState) ) {
				queue.add(nextState);
				visitedStates.add(nextState);
		}




	}
	}

	 */
}
