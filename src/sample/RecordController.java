package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

import javax.swing.*;
import javax.xml.transform.Result;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class RecordController implements Initializable {
    @FXML
    public Pane Encounter_Record;

    //Variables for Encounter
    @FXML
    public TextField HicnoNumber, EncounterID, PrimaryPhysician,DischargeStatus,ProcedureCode,DiagnosisCodes,Admitted,AdmissionDate,DischargeDate;


    //Variables For Procedure
    @FXML
    public TextField ProcedureEncounterID,InsertionSite,InsertingProvider,ProcedureList,PatientLocation,InsertionCircumstances,CatheterType, RemovedDate,InsertionDate;



    //Variables for Infection
    @FXML
    public TextField InfectionEncounterID,InfectionType,DiagnosisDate;


    @FXML
    public TableView<Diagnosis> tableDiagnosis;

    @FXML
    public TableColumn<Diagnosis,String> columnInfectionType,columnDiagnosisDate,columnInsertionSite,columndAdministrator,columnInsertionType,columnCircumstance,columnCath,columnProcedures,columnLocation,columnInserted,columnRemoved;

    @FXML
    public TableView<Encounter> tableEncounter;
    @FXML
    public TableColumn<Encounter,String> columnEncounter,columnHICNO,columnPrimary,columnDischarge,columnDischargeDate,columnAdmission,columnProcedure,columnDIAG,columnAdmitted;




    @FXML
    final ObservableList hicnoOptions = FXCollections.observableArrayList();

    @FXML
    public ComboBox hicComboBox;

    @FXML
    final ObservableList procedureOptions = FXCollections.observableArrayList();

    @FXML
    public ComboBox exisistingPatientsComboBox;



    @FXML
    final ObservableList encounterOptions = FXCollections.observableArrayList();

    @FXML
    public ComboBox encounterCombo;


    @FXML
    final ObservableList infectionOptions = FXCollections.observableArrayList();

    @FXML
    public ComboBox exisistingEncountersCombo;



    @FXML
    public Button infectionButton;

    Stage window;
    FXMLLoader fxmlLoader;

    //initalize observable list to hold data from database
    private ObservableList<Encounter> data;

    private ObservableList<Diagnosis> diagnosis_data;

    private DbConnection dc;



    @Override
    public void initialize(URL url, ResourceBundle rb){

        //TODO
        dc = new DbConnection();

        loadEncounter();

        loadEncounterBox();

        loadProcedureBox();

        loadInfectionBox();

        loadProcedureFormBox();

        loadProceduresandInfections();

    }

    private void loadProcedureBox() {

        try {
            Connection conn = dc.Connect();

            ResultSet rs = conn.createStatement().executeQuery("SELECT *" +
                    "FROM csmith131db.Infection, csmith131db.`Procedure` WHERE csmith131db.`Infection`.E_ID = csmith131db.`Procedure`.EN_ID");

            while(rs.next()){

                encounterOptions.add(rs.getString("E_ID"));

            }

            encounterCombo.getItems().addAll(encounterOptions);

            conn.close();


        } catch (Exception e) {

            System.err.print(e);
        }

    }


    private void loadProcedureFormBox() {

        try {
            Connection conn = dc.Connect();

            ResultSet rs = conn.createStatement().executeQuery("SELECT *" +
                    "FROM csmith131db.`Procedure`");

            while(rs.next()){

                procedureOptions.add(rs.getString("EN_ID"));

            }

            exisistingPatientsComboBox.getItems().addAll(procedureOptions);

            conn.close();


        } catch (Exception e) {

            System.err.print(e);
        }

    }

    @FXML
    public void tableClick(MouseEvent mouseEvent){

        if(tableEncounter.getSelectionModel().getSelectedItem() != null){


            int index = tableEncounter.getSelectionModel().selectedIndexProperty().get();

            try{

                Connection conn = dc.Connect();
                String query = "SELECT * FROM Encounter LIMIT ? ,1";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, index);

                ResultSet rs = preparedStatement.executeQuery();

                while(rs.next()){

                    System.out.println(rs.getString("Encounter_ID"));

                }

                conn.close();

                rs.close();

            }catch(Exception e){

            }

        }
    }

    @FXML
    public void infectionClick(){

        try{

            fxmlLoader = new FXMLLoader(getClass().getResource("Infection.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            window = new Stage();

            window.setTitle("Infection");
            window.setScene(new Scene(root));
            window.show();


        }catch (Exception e){
            System.err.print(e);
        }

    }


    private void loadEncounterBox() {

        try {
            Connection conn = dc.Connect();

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM csmith131db.Encounter");

            while(rs.next()){

                hicnoOptions.add(rs.getString("HICNO"));

            }

            hicComboBox.getItems().addAll(hicnoOptions);


        } catch (Exception e) {

            System.err.print(e);
        }

    }

    private void loadInfectionBox() {

        try {
            Connection conn = dc.Connect();

            ResultSet rs = conn.createStatement().executeQuery("SELECT *" +
                    "FROM csmith131db.Infection, csmith131db.`Procedure` WHERE csmith131db.`Infection`.E_ID = csmith131db.`Procedure`.EN_ID");
            while(rs.next()){

                infectionOptions.add(rs.getString("E_ID"));

            }

            exisistingEncountersCombo.getItems().addAll(infectionOptions);


        } catch (Exception e) {

            System.err.print(e);
        }

    }

    @FXML
    public void fillInfectionForm(ActionEvent event){


        try  {

            Connection conn = dc.Connect();

            String query ="SELECT * FROM csmith131db.Infection WHERE E_ID = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, (String)exisistingEncountersCombo.getSelectionModel().getSelectedItem());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){



                InfectionEncounterID.setText(rs.getString("E_ID"));
                InfectionType.setText(rs.getString("Infection_Type"));
                DiagnosisDate.setText( rs.getString("Diagnosis_DTG"));





            }

            conn.close();
            preparedStatement.close();



        } catch (Exception e){
            System.err.println(e);
        }

    }

    @FXML
    public void fillEncounterForm(ActionEvent event){

        try  {

            Connection conn = dc.Connect();

            String query ="SELECT * FROM csmith131db.Encounter WHERE HICNO = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, (String)hicComboBox.getSelectionModel().getSelectedItem());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){



                EncounterID.setText(rs.getString("Encounter_ID"));
                HicnoNumber.setText( rs.getString("HICNO"));
                PrimaryPhysician.setText(rs.getString("Primary_Physician"));
                DischargeStatus.setText(rs.getString("Discharge_Status"));
                DischargeDate.setText(rs.getString("Discharge_DTG"));
                AdmissionDate.setText(rs.getString("Admission_DTG"));
                ProcedureCode.setText(rs.getString("PROC_Codes"));
                DiagnosisCodes.setText(rs.getString("DIAG_POA_Codes"));
                Admitted.setText(rs.getString("Admitted"));

            }

            conn.close();
            preparedStatement.close();



        } catch (Exception e){
            System.err.println(e);
        }

    }

    @FXML
    public void fillProcedureForm(ActionEvent event){

        try  {

            Connection conn = dc.Connect();

            String query ="SELECT * FROM csmith131db.`Procedure` WHERE EN_ID = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, (String)exisistingPatientsComboBox.getSelectionModel().getSelectedItem());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){


                ProcedureEncounterID.setText(rs.getString("EN_ID"));
                InsertionSite.setText( rs.getString("Insertion_Site"));
                InsertingProvider.setText(rs.getString("Inserting_Provider"));
                ProcedureList.setText(rs.getString("Procedure_List_Follow"));
                PatientLocation.setText(rs.getString("Patient_Location"));
                InsertionCircumstances.setText(rs.getString("Insertion_Circumstance"));
                CatheterType.setText(rs.getString("Catheter_Type"));
                RemovedDate.setText(rs.getString("DTG_Removed"));
                InsertionDate.setText(rs.getString("DTG_Inserted"));

            }

            conn.close();
            preparedStatement.close();



        } catch (Exception e){
            System.err.println(e);
        }

    }


    @FXML
    private void resetProcedureandInfectionTable(ActionEvent event){
        encounterCombo.getSelectionModel().clearSelection();
        encounterCombo.setItems(encounterOptions);
        loadProceduresandInfections();
    }


    @FXML
    public void fillProcedureandInfectionTable(ActionEvent event){


        try{


            Connection conn = dc.Connect();

            diagnosis_data = FXCollections.observableArrayList();


            String query = "SELECT Infection_Type,Diagnosis_DTG,Insertion_Site,Inserting_Provider,Insertion_Circumstance,Catheter_Type,\n" +
                    "      Procedure_List_Follow,Patient_Location,DTG_Inserted,DTG_Removed\n" +
                    "FROM csmith131db.Infection, csmith131db.`Procedure` WHERE csmith131db.`Infection`.E_ID = ? AND csmith131db.`Procedure`.EN_ID = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, (String)encounterCombo.getSelectionModel().getSelectedItem());
            preparedStatement.setString(2, (String)encounterCombo.getSelectionModel().getSelectedItem());


            ResultSet rs = preparedStatement.executeQuery();


            columnInfectionType.setCellValueFactory(new PropertyValueFactory<>("Infection_Type"));
            columnDiagnosisDate.setCellValueFactory(new PropertyValueFactory<>("Diagnosis_DTG"));
            columnInsertionSite.setCellValueFactory(new PropertyValueFactory<>("Insertion_Site"));
            columndAdministrator.setCellValueFactory(new PropertyValueFactory<>("Inserting_Provider"));
            columnCircumstance.setCellValueFactory(new PropertyValueFactory<>("Insertion_Circumstance"));
            columnCath.setCellValueFactory(new PropertyValueFactory<>("Catheter_Type"));
            columnProcedures.setCellValueFactory(new PropertyValueFactory<>("Procedure_List_Follow"));
            columnLocation.setCellValueFactory(new PropertyValueFactory<>("Patient_Location"));
            columnInserted.setCellValueFactory(new PropertyValueFactory<>("DTG_Inserted"));
            columnRemoved.setCellValueFactory(new PropertyValueFactory<>("DTG_Removed"));



            while(rs.next()){

                diagnosis_data.add(new Diagnosis(
                        rs.getString("Infection_Type"),
                        rs.getString("Diagnosis_DTG"),
                        rs.getString("Insertion_Site"),
                        rs.getString("Inserting_Provider"),
                        rs.getString("Insertion_Circumstance"),
                        rs.getString("Catheter_Type"),
                        rs.getString("Procedure_List_Follow"),
                        rs.getString("Patient_Location"),
                        rs.getString("DTG_Inserted"),
                        rs.getString("DTG_Removed")
                ));

                if(diagnosis_data.isEmpty()){

                   tableDiagnosis.setItems(diagnosis_data);


                } else {
                    tableDiagnosis.setItems(diagnosis_data);
                }
            }


            conn.close();
            rs.close();


        }catch (Exception e){
            System.err.print(e);
        }


    }


    @FXML
    public void fillEncounterColumn(ActionEvent event){


        try  {

            Connection conn = dc.Connect();

            data = FXCollections.observableArrayList();


            String query ="SELECT * FROM csmith131db.Encounter WHERE HICNO = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, (String)hicComboBox.getSelectionModel().getSelectedItem());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){

                data.add(new Encounter(
                        rs.getString("Encounter_ID"),
                        rs.getString("HICNO"),
                        rs.getString("Primary_Physician"),
                        rs.getString("Discharge_Status"),
                        rs.getString("Discharge_DTG"),
                        rs.getString("Admission_DTG"),
                        rs.getString("PROC_Codes"),
                        rs.getString("DIAG_POA_Codes"),
                        rs.getString("Admitted")
                ));
                tableEncounter.setItems(data);

            }

            conn.close();
            preparedStatement.close();



        } catch (Exception e){
            System.err.println(e);
        }

    }

    @FXML
    private void resetEncounterTable(){
    hicComboBox.getSelectionModel().clearSelection();
    hicComboBox.setItems(hicnoOptions);
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
                        rs.getString("Admitted")
                ));
                tableEncounter.setItems(data);
            }


            conn.close();
            rs.close();


        }catch (Exception e){
            System.err.print(e);
        }



    }

     //loads patient table
    private void loadProceduresandInfections(){


        try{


            Connection conn = dc.Connect();

            diagnosis_data = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery("SELECT Infection_Type,Diagnosis_DTG,Insertion_Site,Inserting_Provider,Insertion_Circumstance,Catheter_Type,\n" +
                    "      Procedure_List_Follow,Patient_Location,DTG_Inserted,DTG_Removed\n" +
                    "FROM csmith131db.Infection JOIN csmith131db.`Procedure` WHERE  Infection.E_ID = `Procedure`.EN_ID");


            columnInfectionType.setCellValueFactory(new PropertyValueFactory<>("Infection_Type"));
            columnDiagnosisDate.setCellValueFactory(new PropertyValueFactory<>("Diagnosis_DTG"));
            columnInsertionSite.setCellValueFactory(new PropertyValueFactory<>("Insertion_Site"));
            columndAdministrator.setCellValueFactory(new PropertyValueFactory<>("Inserting_Provider"));
            columnCircumstance.setCellValueFactory(new PropertyValueFactory<>("Insertion_Circumstance"));
            columnCath.setCellValueFactory(new PropertyValueFactory<>("Catheter_Type"));
            columnProcedures.setCellValueFactory(new PropertyValueFactory<>("Procedure_List_Follow"));
            columnLocation.setCellValueFactory(new PropertyValueFactory<>("Patient_Location"));
            columnInserted.setCellValueFactory(new PropertyValueFactory<>("DTG_Inserted"));
            columnRemoved.setCellValueFactory(new PropertyValueFactory<>("DTG_Removed"));



            while(rs.next()){


//                System.out.println(rs.getString("Diagnosis_DTG"));
                //get string from db

                diagnosis_data.add(new Diagnosis(
                        rs.getString("Infection_Type"),
                        rs.getString("Diagnosis_DTG"),
                        rs.getString("Insertion_Site"),
                        rs.getString("Inserting_Provider"),
                        rs.getString("Insertion_Circumstance"),
                        rs.getString("Catheter_Type"),
                        rs.getString("Procedure_List_Follow"),
                        rs.getString("Patient_Location"),
                        rs.getString("DTG_Inserted"),
                        rs.getString("DTG_Removed")
                ));
                tableDiagnosis.setItems(diagnosis_data);
            }


            conn.close();
            rs.close();


        }catch (Exception e){
            System.err.print(e);
        }



    }



    @FXML
    public void menuHOMEClicks(ActionEvent event) {

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

    @FXML
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

    @FXML
    public void encounterADD(ActionEvent event){

        if(EncounterID.getText().isEmpty() || HicnoNumber.getText().isEmpty() || PrimaryPhysician.getText().isEmpty()
                || DischargeStatus.getText().isEmpty() || DischargeDate.getText().isEmpty()
                || AdmissionDate.getText().isEmpty()
                || ProcedureCode.getText().isEmpty() || DiagnosisCodes.getText().isEmpty()
                || Admitted.getText().isEmpty()) {

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
                preparedStatement.setString(5, DischargeDate.getText());
                preparedStatement.setString(6, AdmissionDate.getText());
                preparedStatement.setString(7, ProcedureCode.getText());
                preparedStatement.setString(8, DiagnosisCodes.getText());
                preparedStatement.setString(9, Admitted.getText());



                preparedStatement.execute();

                conn.close();

                loadEncounter();

            } catch (Exception e) {
                System.err.print(e);
            }
        }

    }

    @FXML
    public void encounterUpdate(ActionEvent event){

        if(EncounterID.getText().isEmpty() || HicnoNumber.getText().isEmpty() || PrimaryPhysician.getText().isEmpty()
                || DischargeStatus.getText().isEmpty() || DischargeDate.getText().isEmpty()
                || AdmissionDate.getText().isEmpty()
                || ProcedureCode.getText().isEmpty() || DiagnosisCodes.getText().isEmpty()
                || Admitted.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Update Error...");
            alert.setHeaderText(null);
            alert.setContentText("Please Make sure all fields are filled in and Encounter ID and HICNO are unique");
            alert.showAndWait();

        } else {


            try {


                Connection conn = dc.Connect();

                String query = "UPDATE Encounter SET  Primary_Physician  = ?, Discharge_Status = ?," +
                        "Discharge_DTG = ?, Admission_DTG = ?, PROC_Codes = ?, DIAG_POA_Codes = ?, Admitted =? WHERE HICNO = ? AND Encounter_ID =?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);

                preparedStatement.setString(1, PrimaryPhysician.getText());
                preparedStatement.setString(2, DischargeStatus.getText());
                preparedStatement.setString(3, DischargeDate.getText());
                preparedStatement.setString(4, AdmissionDate.getText());
                preparedStatement.setString(5, ProcedureCode.getText());
                preparedStatement.setString(6, DiagnosisCodes.getText());
                preparedStatement.setString(7, Admitted.getText());
                preparedStatement.setString(8, HicnoNumber.getText());
                preparedStatement.setString(9, EncounterID.getText());


                preparedStatement.executeUpdate();

                conn.close();
                preparedStatement.close();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Patient Encounter Updated ");
                alert.setHeaderText(null);
                alert.setContentText("Patient Encounter Successfully Updated");
                alert.showAndWait();

            } catch (Exception e) {
                System.err.print(e);
            }

        }

    }
    @FXML
    public void procedureClick(ActionEvent event){

        try{

            fxmlLoader = new FXMLLoader(getClass().getResource("Procedure.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            window = new Stage();

            window.setTitle("Procedure");
            window.setScene(new Scene(root));
            window.show();



        }catch (Exception e){
            System.err.print(e);
        }

    }
    @FXML
    public void procedureAdd(ActionEvent event){

        if(ProcedureEncounterID.getText().isEmpty() || InsertionSite.getText().isEmpty() || InsertingProvider.getText().isEmpty()
                || InsertionCircumstances.getText().isEmpty() || CatheterType.getText().isEmpty()
                || ProcedureList.getText().isEmpty() || PatientLocation.getText().isEmpty() || InsertionDate.getText().isEmpty()
                || RemovedDate.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Add Procedure Error...");
            alert.setHeaderText(null);
            alert.setContentText("Please Make sure all fields are filled in and Insertion ID is unique");
            alert.showAndWait();

        } else {

            try {


                Connection conn = dc.Connect();

                String query = "INSERT INTO `Procedure` VALUES (?,?,?,?,?,?,?,?,?)";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, ProcedureEncounterID.getText());
                preparedStatement.setString(2, InsertionSite.getText());
                preparedStatement.setString(3, InsertingProvider.getText());
                preparedStatement.setString(4, InsertionCircumstances.getText());
                preparedStatement.setString(5, CatheterType.getText());
                preparedStatement.setString(6, ProcedureList.getText());
                preparedStatement.setString(7, PatientLocation.getText());
                preparedStatement.setString(8, InsertionDate.getText());
                preparedStatement.setString(9, RemovedDate.getText());



                preparedStatement.execute();

                conn.close();
                preparedStatement.close();


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Patient Procedure Added ");
                alert.setHeaderText(null);
                alert.setContentText("Patient Procedure Successfully Added");
                alert.showAndWait();


            } catch (Exception e) {
                System.err.print(e);
            }
        }

    }
    @FXML
    public void procedureUpdate(ActionEvent event){

        if(ProcedureEncounterID.getText().isEmpty() || InsertionSite.getText().isEmpty() || InsertingProvider.getText().isEmpty()
                || InsertionCircumstances.getText().isEmpty() || CatheterType.getText().isEmpty()
                || ProcedureList.getText().isEmpty() || PatientLocation.getText().isEmpty() || InsertionDate.getText().isEmpty()
                || RemovedDate.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Update Procedure Error...");
            alert.setHeaderText(null);
            alert.setContentText("Please Make sure all fields are filled in and Insertion ID is unique");
            alert.showAndWait();

        } else {


            try {


                Connection conn = dc.Connect();

                String query = "UPDATE `Procedure` SET Insertion_Site  = ?, Inserting_Provider = ?," +
                        "Insertion_Circumstance = ?, Catheter_Type = ?, Procedure_List_Follow = ?, Patient_Location = ?, DTG_Inserted =?, DTG_Removed =?  WHERE EN_ID = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);


                preparedStatement.setString(1, InsertionSite.getText());
                preparedStatement.setString(2, InsertingProvider.getText());
                preparedStatement.setString(3, InsertionCircumstances.getText());
                preparedStatement.setString(4, CatheterType.getText());
                preparedStatement.setString(5, ProcedureList.getText());
                preparedStatement.setString(6, PatientLocation.getText());
                preparedStatement.setString(7, InsertionDate.getText());
                preparedStatement.setString(8, RemovedDate.getText());
                preparedStatement.setString(9, ProcedureEncounterID.getText());


                preparedStatement.executeUpdate();

                conn.close();
                preparedStatement.close();


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Patient Procedure Update ");
                alert.setHeaderText(null);
                alert.setContentText("Patient Procedure Successfully Update");
                alert.showAndWait();

            } catch (Exception e) {
                System.err.print(e);
            }

        }

    }

    @FXML
    public void infectionAdd(ActionEvent event){

        if(InfectionEncounterID.getText().isEmpty() || InfectionType.getText().isEmpty()
                || DiagnosisDate.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Add Infection Error...");
            alert.setHeaderText(null);
            alert.setContentText("Please Make sure all fields are filled in and Infection Encounter ID is unique");
            alert.showAndWait();

        } else {

            try {


                Connection conn = dc.Connect();

                String query = "INSERT INTO Infection VALUES (?,?,?)";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, InfectionEncounterID.getText());
                preparedStatement.setString(2, InfectionType.getText());
                preparedStatement.setString(3, DiagnosisDate.getText());


                preparedStatement.execute();

                conn.close();
                preparedStatement.close();


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Patient Infection Added ");
                alert.setHeaderText(null);
                alert.setContentText("Patient Infection Successfully Added");
                alert.showAndWait();


            } catch (Exception e) {
                System.err.print(e);
            }
        }

    }
    @FXML
    public void infectionUpdate(ActionEvent event){

        if(InfectionEncounterID.getText().isEmpty() || InfectionType.getText().isEmpty()
                || DiagnosisDate.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Update Infection Error...");
            alert.setHeaderText(null);
            alert.setContentText("Please Make sure all fields are filled in and Infection Encounter ID is unique");
            alert.showAndWait();

        } else {


            try {


                Connection conn = dc.Connect();

                String query = "UPDATE Infection SET Infection_Type  = ?, Diagnosis_DTG = ?  WHERE E_ID = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, InfectionType.getText());
                preparedStatement.setString(2, DiagnosisDate.getText());
                preparedStatement.setString(3, InfectionEncounterID.getText());

                preparedStatement.executeUpdate();


                conn.close();
                preparedStatement.close();



                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Patient Infection Updated ");
                alert.setHeaderText(null);
                alert.setContentText("Patient Infection Successfully Updated");
                alert.showAndWait();



            } catch (Exception e) {
                System.err.print(e);
            }

        }

    }

    @FXML
    public void menuAnalysisClicks(ActionEvent event) {

        try{

            Parent record = FXMLLoader.load(getClass().getResource("Analysis.fxml"));

            Scene recordScene = new Scene(record);

            Stage stage = (Stage) Encounter_Record.getScene().getWindow();

            stage.setScene(recordScene);

            stage.show();


        }catch (Exception e){
            System.err.print(e);
        }

    }


}




