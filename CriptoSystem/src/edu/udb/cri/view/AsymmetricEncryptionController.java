package edu.udb.cri.view;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import edu.udb.cri.utils.UtilMessage;
import edu.udb.cri.utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AsymmetricEncryptionController {
	
	private URL keyStoreUrl;

	private String keyStorePass = UtilMessage.getMensaje("edu.udb.cri.keystore.pass");

	// Fields for crypt
	@FXML
	private Button cifrarButton;
	@FXML
	private Button restaurarButton;
	@FXML
	private TextArea messageText;
	@FXML
	private TextArea certText;
	@FXML
	private TextArea publicKey;
	@FXML
	private TextArea textoCifrado;
	@FXML
	private ComboBox<String> certList;
	@FXML
	private ComboBox<String> algoritmList;


	// Fields for decrypt
	@FXML
	private Button decryptButton;
	@FXML
	private Button restaurarButtonDecrypt;
	@FXML
	private TextArea messageTextDecrypt;
	@FXML
	private TextArea certTextDecrypt;
	@FXML
	private TextArea originalMessage;
	@FXML
	private ComboBox<String> certListDecrypt;
	@FXML
	private ComboBox<String> algoritmListDecrypt;
	@FXML
	PasswordField passPhaseField;

	public AsymmetricEncryptionController() {

	}

	@FXML
	private void initialize() {
		initializeGui();
		try {
			File keyStore = new File(UtilMessage.getMensaje("edu.udb.cri.keystore.path.resources.keystore"));
			keyStoreUrl = keyStore.toURI().toURL();
			certList.getItems().addAll(Utils.getAllNameCerts(keyStoreUrl, keyStorePass));
			certListDecrypt.getItems().addAll(Utils.getAllNameCerts(keyStoreUrl, keyStorePass));
			algoritmList.getItems().addAll(Utils.getAllAsymmetricAlgoritm());
			algoritmListDecrypt.getItems().addAll(Utils.getAllAsymmetricAlgoritm());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void resetFields() {
		messageText.setText("");
		certList.setValue("");
		algoritmList.setValue("");
	}
	
	public void resetFieldsDecrypt() {
		messageTextDecrypt.setText("");
		certListDecrypt.setValue("");
		algoritmListDecrypt.setValue("");
		passPhaseField.setText("");
	}
	
	public void restablecerDatos() {
		try {
			Alert alert = new Alert(AlertType.CONFIRMATION,
					UtilMessage.getMensaje("edu.udb.cri.system.alert.confirm.reset"), ButtonType.YES,
					ButtonType.CANCEL);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				resetFields();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.showAndWait();
		}
	}
	
	public void restablecerDatosDecrypt() {
		try {
			Alert alert = new Alert(AlertType.CONFIRMATION,
					UtilMessage.getMensaje("edu.udb.cri.system.alert.confirm.reset"), ButtonType.YES,
					ButtonType.CANCEL);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				resetFieldsDecrypt();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.showAndWait();
		}
	}
	
	public void initializeGui() {

		URL imgCipher = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.asimetric"));
		Image imageCipher = new Image(imgCipher.toString());
		cifrarButton.setGraphic(new ImageView(imageCipher));
		cifrarButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				cifrarMensaje();
			}
		});


		URL imgReset = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.reset"));
		Image imageReset = new Image(imgReset.toString());
		restaurarButton.setGraphic(new ImageView(imageReset));
		restaurarButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				restablecerDatos();
			}
		});
		
		restaurarButtonDecrypt.setGraphic(new ImageView(imageReset));
		restaurarButtonDecrypt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				restablecerDatosDecrypt();
			}
		});
		
		
		URL imgDecryp = getClass().getResource(UtilMessage.getMensaje("edu.udb.cri.system.icon.asimetric.decrypt"));
		Image imageDecrypt = new Image(imgDecryp.toString());
		decryptButton.setGraphic(new ImageView(imageDecrypt));
		decryptButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				cifrarMensaje();
			}
		});
		
	}
	
	public void cifrarMensaje() {
		try {
			
			boolean valid = true;
			String nameCert = certList.getValue();
			String algoritmo = algoritmList.getValue();
			String msg = messageText.getText();

			if (msg == null || msg.isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.asymmetric.texto"));
				alert.showAndWait();
			} else if (algoritmo == null || algoritmo.isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.asymmetric.algoritmo"));
				alert.showAndWait();
			} else if (nameCert == null || nameCert.isEmpty()) {
				valid = false;
				Alert alert = new Alert(AlertType.ERROR,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.error.asymmetric.cert"));
				alert.showAndWait();
			}

			if (valid == true) {
				Alert alert = new Alert(AlertType.CONFIRMATION,
						UtilMessage.getMensaje("edu.udb.cri.system.alert.confirm.cifrar.cert"), ButtonType.YES,
						ButtonType.CANCEL);
				alert.showAndWait();

				if (alert.getResult() == ButtonType.YES) {
					//String message = messageText.getText();
					
				}
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.showAndWait();
		}
	}

}
