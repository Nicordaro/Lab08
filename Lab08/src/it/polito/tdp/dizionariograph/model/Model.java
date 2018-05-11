package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionariograph.db.WordDAO;

public class Model {

	private WordDAO wdao = new WordDAO();
	private List<Word> paroleAll = new ArrayList<Word>();
	private Graph<Word, DefaultEdge> graph;

	private List<Word> neighbours = new ArrayList<Word>();
	private List<String> neighboursString = new ArrayList<String>();

	public void createGraph(int numeroLettere) {
		this.inizializzaParole(numeroLettere);
		graph = new SimpleGraph<>(DefaultEdge.class);
		for (Word w : this.paroleAll) {
			graph.addVertex(w);
		}
		this.connetti();
	}

	public List<String> displayNeighbours(String parolaInserita) {
		this.neighbours.removeAll(neighbours);
		this.neighboursString.removeAll(neighboursString);
		Word w = new Word(parolaInserita);

		if (graph.containsVertex(w)) {
			for (Word w2 : this.paroleAll) {
				if (sonoconnessi(w, w2)) {
					this.neighbours.add(w2);
					this.neighboursString.add(w2.getParola());
				}

			}

		}
		return this.neighboursString;
	}

	public int findMaxDegree() {
		Word w = this.contavicini();
		return this.displayNeighbours(w.getParola()).size() - 1;
	}

	public void inizializzaParole(int numeroLettere) {
		paroleAll.removeAll(paroleAll);
		for (String s : wdao.getAllWordsFixedLength(numeroLettere)) {
			paroleAll.add(new Word(s));
		}
	}

	public Graph<Word, DefaultEdge> getGraph() {
		return graph;
	}

	public void connetti() {
		for (Word w : this.paroleAll) {
			for (Word w2 : this.paroleAll) {
				if (!w.equals(w2)) {// escludo coppie(ao, ao) per escludere i loop
					if (sonoconnessi(w, w2)) {
						graph.addEdge(w, w2);
					}
				}
			}
		}
	}

	private boolean sonoconnessi(Word w, Word w2) {
		char[] c1 = w.getParola().toCharArray();
		char[] c2 = w2.getParola().toCharArray();
		int count = 0;

		for (int i = 0; i < c1.length; i++) {
			if (c1[i] != c2[i]) {
				count++;
			}
		}
		if (count > 1)
			return false;
		else
			return true;
	}

	public Word contavicini() {
		int numViciniMax = -1;
		Word wordMax = null;
		for (Word w : this.paroleAll) {
			int numVicini = this.displayNeighbours(w.getParola()).size();
			if (numVicini > numViciniMax) {
				numViciniMax = numVicini;
				wordMax = w;
			}
		}
		return wordMax;
	}

}
