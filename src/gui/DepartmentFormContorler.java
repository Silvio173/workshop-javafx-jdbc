package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listener.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.service.DepartmentService;

public class DepartmentFormContorler implements Initializable{
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	private DepartmentService service;
	
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
	public void btSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormaData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}catch(DbException e) {
			Alerts.showAlert("Error Saving Objeto", null, e.getMessage(),AlertType.ERROR);
		}
	}
	
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listeners : dataChangeListeners) {
			listeners.onDataChanged();
		}
		
	}


	private Department getFormaData() {
		Department obj = new Department();
		obj.setId(Utils.tryParseToInt(txtid.getText()));
		obj.setName(txtname.getText());
		return obj;
	}



	@FXML
	public void btCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();;
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
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtid.setText(String.valueOf(entity.getId()));
		txtname.setText(String.valueOf(entity.getName()));
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}



}
