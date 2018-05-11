package it.polito.tdp.dizionariograph;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.dizionariograph.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioGraphController {

	Model model = new Model();

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtLettere"
	private TextField txtLettere; // Value injected by FXMLLoader

	@FXML // fx:id="txtParola"
	private TextField txtParola; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void handleGrade(ActionEvent event) {
		txtResult.clear();
		txtResult.appendText(
				"La parola che ha più vicini è " + model.contavicini().getParola() + ", ne ha " + model.findMaxDegree()
						+ " e i suoi vicini sono: " + model.displayNeighbours(model.contavicini().getParola()));

	}

	@FXML
	void handleGraph(ActionEvent event) {
		txtResult.clear();
		int num = Integer.parseInt(txtLettere.getText());
		model.createGraph(num);
		txtResult.setText("Generato grafo con parole composte da " + num + " lettere.");

	}

	@FXML
	void handleNear(ActionEvent event) {
		txtResult.clear();
		String trova = txtParola.getText();
		int num = Integer.parseInt(txtLettere.getText());
		if (trova.length() != num) {
			txtResult.appendText("Parola inserita di lunghezza diversa da lunghezza specificata nel primo campo.");
		}
		List<String> risultato = model.displayNeighbours(trova);
		for (String s : risultato) {
			txtResult.appendText(s + "\n");
		}
	}

	@FXML
	void handleReset(ActionEvent event) {
		txtResult.clear();
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtLettere != null : "fx:id=\"txtLettere\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
		assert txtParola != null : "fx:id=\"txtParola\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";

	}
}
