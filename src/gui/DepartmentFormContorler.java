package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormContorler implements Initializable{
	
	private Department entity; 
	
	@FXML
	private TextField txtid; 
	@FXML
	private TextField txtname; 
	@FXML
	private Label lableErrorName; 
	@FXML
	private Button btSave; 
	@FXML
	private Button btCancel; 
	
	@FXML
	public void btSaveAction() {
		System.out.println("btSalveAction");
	}
	
	@FXML
	public void btCancelAction() {
		System.out.println("btCancelAction");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();

		
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtid);
		Constraints.setTextFieldMaxLength(txtname, 30);
		
	}
	
	public void setDepartment(Department entity) {
		this.entity = entity;
	}

	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtid.setText(String.valueOf(entity.getId()));
		txtname.setText(String.valueOf(entity.getName()));
	}

}
