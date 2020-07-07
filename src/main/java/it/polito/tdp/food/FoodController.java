/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.VicinoPeso;
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
    	txtResult.appendText("Cerco cammino peso massimo...\n");
    	int N = 0;
    	try {
    		N = Integer.parseInt(txtPassi.getText());
    	}catch(NumberFormatException nfe) {
    		nfe.printStackTrace();
    		txtResult.appendText("Devi inserire un numero intero nella casella delle calorie");
    		return;
    	}
    	String partenza = this.boxPorzioni.getValue();
    	if(partenza==null) {
    		txtResult.appendText("Devi selezionare un tipo di porzione");
    		return;
    	}
    	List<String> cammino = this.model.camminoMassimo(N, partenza);
    	if(cammino.isEmpty()) {
    		txtResult.appendText("Non è stato trovato nessun cammino");
    		return;
    	}
    	txtResult.appendText("CAMMINO: \n");
    	for(String s : cammino) {
    		txtResult.appendText(s+"\n");
    	}
    	txtResult.appendText("PESO TOTALE: "+this.model.pesoCammino(cammino));
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco porzioni correlate...\n");
    	String porzione = this.boxPorzioni.getValue();
    	if(porzione==null) {
    		txtResult.appendText("Devi selezionare un tipo di porzione");
    		return;
    	}
    	List<VicinoPeso> connessi = this.model.getVicini(porzione);
		txtResult.appendText("CONNESSI: \n");
    	for(VicinoPeso vp : connessi) {
    		txtResult.appendText(vp.getVicino()+", peso: "+vp.getPeso()+"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	this.boxPorzioni.getItems().clear();
    	txtResult.appendText("Creazione grafo...\n");
    	int calorie = 0;
    	try {
    		calorie = Integer.parseInt(txtCalorie.getText());
    	}catch(NumberFormatException nfe) {
    		nfe.printStackTrace();
    		txtResult.appendText("Devi inserire un numero intero nella casella delle calorie");
    		return;
    	}
    	this.model.creaGrafo(calorie);
    	txtResult.appendText("GRAFO CREATO!\n NUMERO VERTICI: "+this.model.verticiSize()+"\nNUMERO ARCHI: "+this.model.archiSize()+"\n");
    	List<String> porzioni = this.model.tipiPorzione(calorie);
    	this.boxPorzioni.getItems().addAll(porzioni);
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
