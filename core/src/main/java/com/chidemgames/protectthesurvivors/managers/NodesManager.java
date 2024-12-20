package com.chidemgames.protectthesurvivors.managers;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.chidemgames.protectthesurvivors.gameobjects.Node;

public class NodesManager {
	
	private static int[][] nodesMap;
	
	private Node[] nodes;
	
	public static final int CONNECTED = 1;
	public static final int CONNECTABLE = 2;
	public static final int CONNECTING = 3;
	public static final int NOT_CONNECTED = 0;
	public static final int NOT_EXIST = -1;
	
	private static NodesManager INSTANCE = new NodesManager();
	
	public static NodesManager getInstance(){
		return INSTANCE;
	}
	
	public void createNodes(World world, Vector2[] positions){
		
		this.nodes = new Node[positions.length];
		for (int i = 0; i < positions.length; i++) {
			this.nodes[i] = new Node(world, new Vector2(((float)((int)(positions[i].x * 10))) / 10, ((float)((int)(positions[i].y * 10))) / 10));
			this.nodes[i].setId(i);
		}
			
		System.out.println("");
		
		getInstance().heapSort(nodes);
		getInstance().sortLayers(nodes);
		getInstance().sortSecondaryIds(nodes);
		getInstance().createMatriz();
		
		int lastLayer = nodes[nodes.length - 1].getLayer();
		ArrayList<Integer> ids = getInstance().getNodesByLayer(lastLayer);
		for (int id : ids) {
			getInstance().getNode(id).setLastNode(true);
		}
		
	}

	
	public void createMatriz(){
		nodesMap = new int[nodes.length][nodes.length];
		
		for (Node node : this.nodes) {
			
			int layer = node.getLayer();
			int pos = node.getPosInLayer();
			int id = node.getId();
			int idA, idB, idBrotherA, idBrotherB;
			
			idBrotherA = getInstance().getIdByPosLayer(layer, pos + 1);
			idBrotherB = getInstance().getIdByPosLayer(layer, pos - 1);
			if (idBrotherA != NOT_EXIST && layer > 1) nodesMap[id][idBrotherA] = CONNECTABLE;
			if (idBrotherB != NOT_EXIST && layer > 1) nodesMap[id][idBrotherB] = CONNECTABLE;
		
			int sizeNextLayer = getInstance().getNodesByLayer(layer + 1).size();
			int sizePrevLayer = getInstance().getNodesByLayer(layer - 1).size();
			
			switch(getInstance().getNodesByLayer(layer).size()){
			
				case 2:
									
					if (sizeNextLayer == 3){
						idA = getInstance().getIdByPosLayer(layer + 1, pos);
						idB = getInstance().getIdByPosLayer(layer + 1, pos + 1);
						if (idA != NOT_EXIST) nodesMap[id][idA] = CONNECTABLE;
						if (idB != NOT_EXIST) nodesMap[id][idB] = CONNECTABLE;
					}
			
					break;
					
				case 3:
					
					if (sizeNextLayer == 2){
						idA = getInstance().getIdByPosLayer(layer + 1, pos);
						idB = getInstance().getIdByPosLayer(layer + 1, pos - 1);
						if (idA != NOT_EXIST) nodesMap[id][idA] = CONNECTABLE;
						if (idB != NOT_EXIST) nodesMap[id][idB] = CONNECTABLE;
					}
					
					break;
			
			}
			
		}
		
	}
	
	public int[][] getMatriz(){
		return nodesMap;
	}
	
	public void setStatusNode(Node nodeA, Node nodeB, int status){
		nodesMap[nodeA.getId()][nodeB.getId()] = status;
	}
	
	public Node getNode(int index){
		return this.nodes[index];
	}
	
	public void unlinkNodes(Node nodeA, Node nodeB){
		nodesMap[nodeA.getId()][nodeB.getId()] = NOT_CONNECTED;
	}
	
	public boolean isLinked(Node nodeA, Node nodeB){
		return nodesMap[nodeA.getId()][nodeB.getId()] == CONNECTED;
	}
	
	public int getStatus(int idA, int idB){
		
		return nodesMap[idA][idB];
		
	}
	
	public ArrayList<Integer> getNodesByStatus(int nodeId, int status){
		
		ArrayList<Integer> ids = new ArrayList<Integer>();
		
		for (int i = 0; i < nodesMap[nodeId].length; i++){
			if (nodesMap[nodeId][i] == status){
				ids.add(i);
			}
		}
		
		return ids;
	}
	
	public ArrayList<Integer> getNodesByLayer(int layer){
			
		ArrayList<Integer> ids = new ArrayList<Integer>();
		
		for (Node node: nodes){
			if (node.getLayer() == layer){
				ids.add(node.getId());
			}
		}
		
		return ids; 
	}
	
	public int getIdByPosLayer(int layer, int pos){
		
		ArrayList<Integer> ids = getInstance().getNodesByLayer(layer);
		int id = NOT_EXIST;
		
		for (int i : ids) {
			if (getInstance().getNode(i).getPosInLayer() == pos){
				id = i;
				System.out.println("Id: " + getInstance().getNode(i).getPosInLayer() + " Layer: " + layer + " pos: " + pos + " id: " + id);
			}
		}

		return id;
	}
	
	public boolean inLastLayer(int id){
		
		Node n = nodes[nodes.length - 1];
		int lastLayer = n.getLayer();
		if (getInstance().getNode(id).getLayer() == lastLayer){
			return true;
		}
		return false;
	}
	
	public Node[] getNodes(ArrayList<Integer> ids){
		
		Node[] nodes = new Node[ids.size()];
		
		for (int i = 0; i < ids.size() ; i++) {
			
			nodes[i] = getInstance().getNode(ids.get((ids.size() - 1) - i));
			
		}
		
		return nodes;
	}
	
	public void sortLayers(Node[] v){
		for (Node nodep : v) {
			for (int i = 0; i < v.length; i++) {
				if (nodep.getPosition().y > v[i].getPosition().y){
					if (i > 0 && v[i].getPosition().y > v[i - 1].getPosition().y){
						nodep.camada += 1;
					}
					if (i == 0){
						nodep.camada += 1;	
					}
				}
			}
		}
	}
	
	public void sortSecondaryIds(Node[] v){
		
		int lastLayer = v[v.length - 1].getLayer();
		
		for (int layer = 1; layer <= lastLayer; layer++){
			
			ArrayList<Integer> ids = getInstance().getNodesByLayer(layer);
			int pos = 0;
			Node[] nodes = getInstance().getNodes(ids);
			
			for (Node node : nodes) {
				pos++;
				node.setPosInLayer(pos);
			}
		}
	}
	
	public void heapSort(Node[] v) {
		
		int n = v.length; 
		buildMaxHeap(v, n);

		for (int i = v.length - 1; i > 0; i--) { 
			troca(v, i, 0); 
			buildMaxHeap(v, --n);
		}
		
		for (int i = 0; i < v.length; i++) {
			v[i].setId(i);
		}
	}

	
	private void buildMaxHeap(Node[] v, int n) { 
		for (int i = n / 2 - 1; i >= 0; i--) 
			maxHeapify(v, i, n); 

	}

	
	private void maxHeapify(Node[] vetor, int pos, int tamanhoVetor) { 
		
		int max = 2 * pos + 1;
		int right = max + 1; 
		
		if (max < tamanhoVetor) {
			
			if (right < tamanhoVetor && isMax(vetor[max].getPosition(), vetor[right].getPosition()) == 1) 
				max = right;
			
			if (isMax(vetor[max].getPosition(), vetor[pos].getPosition()) == 2) {  
				troca(vetor, max, pos);
				maxHeapify(vetor, max, tamanhoVetor);
			}
		}
	}
	
	public int isMax(Vector2 v1, Vector2 v2){
		int diferencas1 = 0, diferencas2 = 0;
		
		if (v1.x < v2.x){
			diferencas1 ++;
		} else {
			if (v2.x < v1.x){
				diferencas2 ++;
			}
		}
		
		if (v1.y < v2.y){
			diferencas1 ++;
		} else {
			if (v2.y < v1.y){
				diferencas2 ++;
			}
		}
		
		if (diferencas1 == diferencas2){
			if (v1.x > v2.x && v1.y < v2.y){
				diferencas1++;
			}
			if (v2.x > v1.x && v2.y < v1.y){
				diferencas2++;
			}
		}

		return (Math.max(diferencas1, diferencas2) == diferencas1)? 1 : 2;
	}

	public void troca(Node[] v, int max, int pai) {
		Node aux = v[max];
		v[max] = v[pai];
		v[pai] = aux;
	}

}
