package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class RecordController implements Initializable {
    @FXML
    public Pane Encounter_Record;
    public TextField HicnoNumber, EncounterID, PrimaryPhysician,DischargeStatus,ProcedureCode,DiagnosisCodes,Admitted;
    public DatePicker AdmissionDate,DischargeDate;
    @FXML
    public TableView<Encounter> tableEncounter;
    @FXML
    public TableColumn<Encounter,String> columnEncounter;
    @FXML
    public TableColumn<Encounter,String> columnHICNO;
    @FXML
    public TableColumn<Encounter,String> columnPrimary;
    @FXML
    public TableColumn<Encounter,String> columnDischarge;
    @FXML
    public TableColumn<Encounter,String> columnDischargeDate;
    @FXML
    public TableColumn<Encounter,String> columnAdmission;
    @FXML
    public TableColumn<Encounter,String> columnProcedure;
    @FXML
    public TableColumn<Encounter,String> columnDIAG;
    @FXML
    public TableColumn<Encounter,String> columnAdmitted;



    Stage window;
    FXMLLoader fxmlLoader;

    //initalize observable list to hold data from database
    private ObservableList<Encounter> data;

    private DbConnection dc;


    @Override
    public void initialize(URL url, ResourceBundle rb){

        //TODO
        dc = new DbConnection();

        loadEncounter();
    }

    @FXML //loads patient table
    private void loadEncounter(){

        try{

            Connection conn = dc.Connect();

            data = FXCollections.observableArrayList();


            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM csmith131db.Encounter");

            columnEncounter.setCellValueFactory(new PropertyValueFactory<>("Encounter_ID"));
            columnHICNO.setCellValueFactory(new PropertyValueFactory<>("HICNO"));
            columnPrimary.setCellValueFactory(new PropertyValueFactory<>("Primary_Physician"));
            columnDischarge.setCellValueFactory(new PropertyValueFactory<>("Discharge_Status"));
            columnDischargeDate.setCellValueFactory(new PropertyValueFactory<>("Discharge_DTG"));
            columnAdmission.setCellValueFactory(new PropertyValueFactory<>("Admission_DTG"));
            columnProcedure.setCellValueFactory(new PropertyValueFactory<>("PROC_Codes"));
            columnDIAG.setCellValueFactory(new PropertyValueFactory<>("DIAG_POA_Codes"));
            columnAdmitted.setCellValueFactory(new PropertyValueFactory<>("Admitted"));


            while(rs.next()){

                //get string from db

                data.add(new Encounter(
                        rs.getString("Encounter_ID"),
                        rs.getString("HICNO"),
                        rs.getString("Primary_Physician"),
                        rs.getString("Discharge_Status"),
                        rs.getString("Discharge_DTG"),
                        rs.getString("Admission_DTG"),
                        rs.getString("PROC_Codes"),
                        rs.getString("DIAG_POA_Codes"),
                        rs.getString("Admitted")));
                tableEncounter.setItems(data);
            }


            conn.close();
            rs.close();


        }catch (Exception e){
            System.err.print(e);
        }



    }


    @FXML
    public void menuClicks(ActionEvent event) {

        try{

            Parent record = FXMLLoader.load(getClass().getResource("Patient.fxml"));

            Scene recordScene = new Scene(record);


            Stage stage = (Stage) Encounter_Record.getScene().getWindow();

            stage.setScene(recordScene);
            stage.show();

        }catch (Exception e){
            System.err.print(e);
        }

    }

    public void encounterClick(ActionEvent event){
        try{

            fxmlLoader = new FXMLLoader(getClass().getResource("Encounter.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            window = new Stage();

            window.setTitle("Encounter");
            window.setScene(new Scene(root));
            window.show();



        }catch (Exception e){
            System.err.print(e);
        }

    }



    public void encounterADD(ActionEvent event){

        if(EncounterID.getText().isEmpty() && HicnoNumber.getText().isEmpty() && PrimaryPhysician.getText().isEmpty()
                && DischargeStatus.getText().isEmpty() && DischargeDate.getEditor().getText().isEmpty()
                && AdmissionDate.getEditor().getText().isEmpty()
                && ProcedureCode.getText().isEmpty() && DiagnosisCodes.getText().isEmpty()
                && Admitted.getText().isEmpty()) {

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Input Error...");
                        alert.setHeaderText(null);
                        alert.setContentText("Please Make sure all fields are filled in and Encounter ID and HICNO are unique");
                        alert.showAndWait();

        } else {

            try {


                Connection conn = dc.Connect();

                String query = "INSERT INTO Encounter VALUES (?,?,?,?,?,?,?,?,?)";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, EncounterID.getText());
                preparedStatement.setString(2, HicnoNumber.getText());
                preparedStatement.setString(3, PrimaryPhysician.getText());
                preparedStatement.setString(4, DischargeStatus.getText());
                preparedStatement.setString(5, ((TextField) DischargeDate.getEditor()).getText());
                preparedStatement.setString(6, ((TextField) AdmissionDate.getEditor()).getText());
                preparedStatement.setString(7, ProcedureCode.getText());
                preparedStatement.setString(8, DiagnosisCodes.getText());
                preparedStatement.setString(9, Admitted.getText());



                preparedStatement.execute();

                conn.close();

//                Load Encounter Table

            } catch (Exception e) {
                System.err.print(e);
            }
        }

    }


    public void encounterUpdate(ActionEvent event){

        if(EncounterID.getText().isEmpty() && HicnoNumber.getText().isEmpty() && PrimaryPhysician.getText().isEmpty()
                && DischargeStatus.getText().isEmpty() && DischargeDate.getEditor().getText().isEmpty()
                && AdmissionDate.getEditor().getText().isEmpty()
                && ProcedureCode.getText().isEmpty() && DiagnosisCodes.getText().isEmpty()
                && Admitted.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Update Error...");
            alert.setHeaderText(null);
            alert.setContentText("Please Make sure all fields are filled in and Encounter ID and HICNO are unique");
            alert.showAndWait();

        } else {


            try {


                Connection conn = dc.Connect();

                String query = "UPDATE Encounter SET Encounter_ID = ?, Primary_Physician  = ?, Discharge_Status = ?," +
                        "Discharge_DTG = ?, Admission_DTG = ?, PROC_Codes = ?, DIAG_POA_Codes = ?, Admitted =? WHERE HICNO = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, EncounterID.getText());
                preparedStatement.setString(2, PrimaryPhysician.getText());
                preparedStatement.setString(3, DischargeStatus.getText());
                preparedStatement.setString(4, ((TextField) DischargeDate.getEditor()).getText());
                preparedStatement.setString(5, ((TextField) AdmissionDate.getEditor()).getText());
                preparedStatement.setString(6, ProcedureCode.getText());
                preparedStatement.setString(7, DiagnosisCodes.getText());
                preparedStatement.setString(8, Admitted.getText());
                preparedStatement.setString(9, HicnoNumber.getText());


                preparedStatement.executeUpdate();

                conn.close();

                //LoadPatientData();

            } catch (Exception e) {
                System.err.print(e);
            }

        }

    }


}




