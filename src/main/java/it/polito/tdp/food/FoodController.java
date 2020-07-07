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
    	String nome = boxPorzioni.getValue();
    	int passi = 0;
    	if(nome=="" || txtPassi.getText()=="") {
    		txtResult.appendText("Devi selezionare un tipo di porzione dal box e il numero di passi");
    		return;
    	}
    	try {
    		passi = Integer.parseInt(txtPassi.getText());
    	}catch(NumberFormatException nfe) {
    		nfe.printStackTrace();
    		throw new RuntimeException("Puoi inserire solo numeri interi all'interno del campo N");
    	}

    	txtResult.appendText("Cerco cammino peso massimo iniziando da "+nome+"...\n");
    	List<VicinoPeso> cammino = this.model.getCammino(passi, nome);
    	if(this.model.pesoCammino(cammino)==0) {
    		txtResult.appendText("Non ho trovato un cammino di lunghezza "+passi);
    		return;
    	}
    	for(VicinoPeso vp : cammino) {
    		txtResult.appendText(vp.getVicino()+"\n");
    	}
    	txtResult.appendText("PESO TOTALE CAMMINO: "+this.model.pesoCammino(cammino));
    	
    	
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	String porzione = boxPorzioni.getValue();
    	if(porzione==null) {
    		txtResult.appendText("Devi selezionare un tipo di porzione!");
    		return;
    	}
    	txtResult.appendText("Cerco porzioni correlate a "+porzione+"...\n");
    	List<VicinoPeso> vicini = this.model.getVicini(porzione);
    	for(VicinoPeso vp : vicini) {
    		txtResult.appendText(vp.getVicino()+",  PESO: "+vp.getPeso()+"\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...\n");
    	int calorie = 0;
    	if(txtCalorie.getText()=="") {
    		txtResult.appendText("Devi inserire un numero nel campo calorie");
    		return;
    	}
    	try {
    		calorie = Integer.parseInt(txtCalorie.getText());
    	}catch(NumberFormatException nfe) {
    		nfe.printStackTrace();
    		throw new RuntimeException("Devi inserire solo numeri interi");
    	}
    	this.model.creaGrafo(calorie);
		txtResult.appendText("GRAFO CREATO CON SUCCESSO! \n # VERTICI: "+this.model.getSizeVertici()+"\n # ARCHI: "+this.model.getSizeArchi());
		boxPorzioni.getItems().addAll(this.model.getVertexSet());
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
