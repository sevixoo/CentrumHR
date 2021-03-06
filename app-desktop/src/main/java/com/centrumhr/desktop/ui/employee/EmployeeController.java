package com.centrumhr.desktop.ui.employee;

import com.centrumhr.application.Message;
import com.centrumhr.desktop.ui.employee.presenter.EmployeePresenter;
import com.centrumhr.data.model.employment.EmployeeEntity;
import com.centrumhr.desktop.CentrumHRApplication;
import com.centrumhr.desktop.core.Controller;
import com.centrumhr.desktop.ui.common.SimpleAskDialog;
import com.centrumhr.desktop.ui.employee.data.EmployeeVM;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Seweryn on 27.09.2016.
 */
public class EmployeeController extends Controller implements EmployeePresenter.View {

    @FXML Pane container;
    @FXML Button addEmployeeButton;
    @FXML Button editEmployeeButton;
    @FXML Button deleteEmployeeButton;

    @Inject EmployeePresenter mPresenter;

    EmployeeListController employeeListController;

    public EmployeeController() {
        super("layout/employee_controller.fxml");
    }

    @FXML public void initialize(){
        CentrumHRApplication.getInstance().getLoggedAccountComponent().inject(this);
        mPresenter.setView(this);
        editEmployeeButton.setDisable(true);
        deleteEmployeeButton.setDisable(true);
        addEmployeeButton.setOnAction( event -> displayAddEmployeeScreen() );
        editEmployeeButton.setOnAction( event -> displayEditEmployeeScreen() );
        deleteEmployeeButton.setOnAction(event -> displayDeleteEmployeeDialog() );
        displayEmployeeList();
    }

    private void onSelectEmployee(EmployeeVM employeeVM){
        if(employeeVM == null) {
            editEmployeeButton.setDisable(true);
            deleteEmployeeButton.setDisable(true);
        }else{
            editEmployeeButton.setDisable(false);
            deleteEmployeeButton.setDisable(false);
        }
    }

    public void displayEmployeeList(){
        employeeListController = new EmployeeListController();
        employeeListController.setListener( this::onSelectEmployee );
        displayComponent( container , employeeListController ); 
    }

    public void displayDeleteEmployeeDialog(){
        SimpleAskDialog dialog = new SimpleAskDialog("Usun�� pracownika?");
        dialog.startForResult( this );
        if(dialog.getResult() == RESULT_OK){
            mPresenter.deleteEmployee( employeeListController.getSelectedEmployeeID() );
        }
    }

    public void displayEditEmployeeScreen(){
        AddEmployeeController dialog = new AddEmployeeController();
        dialog.editEmployee( employeeListController.getSelectedEmployeeID() );
        dialog.startForResult( this );
        if(dialog.getResult() == RESULT_OK){
            refreshListData(dialog.getResultData());
        }
    }

    public void displayAddEmployeeScreen() {
        AddEmployeeController dialog = new AddEmployeeController();
        dialog.startForResult( this );
        if(dialog.getResult() == RESULT_OK){
            refreshListData(dialog.getResultData());
        }
    }

    @Override
    public void onUserDeleted() {
        displayEmployeeList();
    }

    private void refreshListData(List<EmployeeVM> employees){
        for( EmployeeVM employee  : employees){
            employeeListController.displayAddedEmployee(employee);
        }
    }

}
