package it.polito.tdp.food.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	public Graph<String, DefaultWeightedEdge> grafo;
	public FoodDao dao;
	
	public Model() {
		this.dao = new FoodDao();
	}
	
	public void creaGrafo(int calorie) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, this.dao.getPorzioni(calorie));
		
		for (Adiacenza a : this.dao.getArchi()) {
			if (this.grafo.containsVertex(a.getS1()) && this.grafo.containsVertex(a.getS2())) {
				Graphs.addEdge(grafo, a.getS1(), a.getS2(), a.getPeso());
			}
		}
		
	}
	
	public List<String> getVertici() {
		List<String> risultato = new LinkedList<>(this.grafo.vertexSet());
		Collections.sort(risultato);
		return risultato;
	}
	
	public Map<String, Integer> getConnessioni(String s) {
		Map<String, Integer> risultato = new HashMap<>();
		for (String vicini : Graphs.neighborListOf(grafo, s)) {
			risultato.put(vicini, (int) this.grafo.getEdgeWeight(this.grafo.getEdge(s, vicini)));
		}
		return risultato;
	}

	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNumArchi() {
		return this.grafo.edgeSet().size();
	}
	
	
	
}
