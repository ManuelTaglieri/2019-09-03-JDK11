/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	if (this.boxPorzioni.getValue()==null) {
    		txtResult.setText("Inserire una porzione da cui calcolare il cammino");
    		return;
    	}
    	try {
    		int n = Integer.parseInt(txtPassi.getText());
    		if (n<1) {
    			txtResult.setText("Inserire un valore di passi almeno pari ad 1");
    			return;
    		}
    		List<String> cammino = new LinkedList<>(this.model.getCamminoMax(n, this.boxPorzioni.getValue()));
    		if (cammino.isEmpty()) {
    			txtResult.setText("Cammino con i passi selezionati non disponibile");
    			return;
    		}
    		txtResult.appendText("Cammino di massimo peso:\n");
    		for (String s : cammino) {
    			txtResult.appendText(s+"\n");
    		}
    		txtResult.appendText("Peso del cammino: "+this.model.getPesoMax());
    	} catch (NumberFormatException e) {
    		txtResult.setText("Errore: inserire un valore numerico intero come numero di passi.");
    	}
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	if (this.boxPorzioni.getValue()==null) {
    		txtResult.setText("Inserire una porzione di cui calcolare le correlate");
    		return;
    	}
    	Map<String, Integer> connessioni = this.model.getConnessioni(this.boxPorzioni.getValue());
    	this.txtResult.setText("Elenco delle correlate:\n");
    	for (String s : connessioni.keySet()) {
    		txtResult.appendText(s+" - "+connessioni.get(s)+"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	try {
    		int calorie = Integer.parseInt(txtCalorie.getText());
    		if (calorie<1) {
    			txtResult.setText("Inserire almeno un valore pari ad 1 per le calorie");
    			return;
    		}
    		this.model.creaGrafo(calorie);
    		txtResult.appendText("Grafo creato!\n");
    		txtResult.appendText("# Vertici: "+this.model.getNumVertici()+"\n");
    		txtResult.appendText("# Archi: "+this.model.getNumArchi());
    		btnCorrelate.setDisable(false);
    		btnCammino.setDisable(false);
    		boxPorzioni.getItems().addAll(this.model.getVertici());
    		
    	} catch (NumberFormatException e) {
    		txtResult.setText("Errore: inserire un valore numerico intero come calorie");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
