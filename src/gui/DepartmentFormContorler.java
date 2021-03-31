package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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
import model.exceptions.ValidationException;
import model.service.DepartmentService;

/**
 * @author silvio
 *
 */
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
		}catch(ValidationException e) {
			setErrorMessages(e.getErros());
		}
	}
	
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listeners : dataChangeListeners) {
			listeners.onDataChanged();
		}
		
	}


	private Department getFormaData() {
		Department obj = new Department();
		
		ValidationException exception = new ValidationException("Validation error ");
		
		obj.setId(Utils.tryParseToInt(txtid.getText()));
		
		if(txtname.getText() == null || txtname.getText().trim().equals("")) {
			exception.addError("name", "Field can´t empty");
		}
		obj.setName(txtname.getText());
		
		if(exception.getErros().size() > 0) {
			throw exception;
		}
		
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

	private void setErrorMessages(Map<String,String> errors) {
		Set<String> fields = errors.keySet();
		if(fields.contains("name")) {
			lableErrorName.setText(errors.get("name"));
		}
	}

}
