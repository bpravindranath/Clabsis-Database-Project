package sample;


import java.awt.Label;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import sample.Query.*;

import java.util.IdentityHashMap;
import java.util.ResourceBundle;


public class QueryController implements Initializable  {

    public Pane Query;

    @FXML
    final ObservableList associatedOptions = FXCollections.observableArrayList();

    @FXML
    public ComboBox txtCHICNOO;


    @FXML
    final ObservableList riskOptions = FXCollections.observableArrayList();

    @FXML
    public ComboBox txtPHICNOO;


    @FXML
    final ObservableList encounterOptions = FXCollections.observableArrayList();

    @FXML
    public ComboBox txtEncounterIDD;


    @FXML
    private Button ButtonLoad;

    private DbConnection dc;
    @FXML
    private Label label;



    @FXML
    private TableView<InfectionRatio> tableInfectionRatio;
    @FXML
    private TableColumn<InfectionRatio,String> columnInfectionRatio;
    @FXML
    private RadioButton rdoInfectionRatio;
    @FXML
    public TextField infection_ratio;
    //initalize observable list to hold data from database
    private ObservableList<InfectionRatio> data;


    @FXML
    private TableView<ChecklistFollowed> tableChecklistFollowed;
    @FXML
    private TableColumn<ChecklistFollowed,String> columnChecklistFollowed;
    @FXML
    private RadioButton rdoChecklistFollowed;
    @FXML
    private RadioButton rdoCFNo;
    @FXML
    private RadioButton rdoCFYes;
    @FXML
    private RadioButton rdoCFRoutine;
    @FXML
    private RadioButton rdoCFEmergency;
    private ObservableList<ChecklistFollowed> dataCF;


    @FXML
    private TableView<PatientLocation> tableLocationList;
    @FXML
    private TableColumn<PatientLocation,String> columnPatientLocation;
    @FXML
    private RadioButton rdoLocationList;
    @FXML
    private RadioButton rdoLNo;
    @FXML
    private RadioButton rdoLYes;
    @FXML
    private RadioButton rdoLRoutine;
    @FXML
    private RadioButton rdoLEmergency;
    private ObservableList<PatientLocation> dataPL;

    @FXML
    private TableView<DoclistCL> tableDoclistCL;
    @FXML
    private TableColumn<DoclistCL,String> columnDoclistCL_InsPro;
    @FXML
    private TableColumn<DoclistCL,String> columnDoclistCL_EnId;
    @FXML
    private TableColumn<DoclistCL,String> columnDoclistCL_InfType;
    @FXML
    private RadioButton  rdoDoclistCL;
    private ObservableList<DoclistCL> dataCL;

    @FXML
    private TableView<PatientRiskFactor> tablePatientRiskFactor;
    @FXML
    private TableColumn<PatientRiskFactor,String> columnPatientRiskFactor;
    @FXML
    private RadioButton rdoPatientRiskFactor;
    @FXML
    public TextField txtPHICNO;
    private ObservableList<PatientRiskFactor> dataPR;


    @FXML
    private TableView<CatheterList> tableCatheterList;
    @FXML
    private TableColumn<CatheterList,String> columnCatheterList_HICNO;
    @FXML
    private TableColumn<CatheterList,String> columnCatheterList_Catheter_Type;
    @FXML
    private TableColumn<CatheterList,String> columnCatheterList_Ins_Provider;
    @FXML
    private RadioButton  rdoCatheterList;
    @FXML
    public TextField txtEncounterID;
    private ObservableList<CatheterList> dataCatheterList;


    @FXML
    private TableView<CLABSIList> tableCLABSIList;
    @FXML
    private TableColumn<CLABSIList,String> columnCLABSIList_Infection_Type;
    @FXML
    private TableColumn<CLABSIList,String> columnCLABSIList_Enc_ID;
    @FXML
    private TableColumn<CLABSIList,String> columnCLABSIList_HICNo;
    @FXML
    private RadioButton  rdoCLABSIList;
    @FXML
    public TextField txtCHICNO;
    private ObservableList<CLABSIList> dataCLABSIList;

    @Override
    public void initialize(URL url, ResourceBundle rb){

        //TODO
        dc = new DbConnection();

        loadAssociatedBox();

        loadRiskBox();

        loadCatherterBox();
    }

    private void loadRiskBox() {

        try {
            Connection conn = dc.Connect();

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM csmith131db.Patient");

            while(rs.next()){

                riskOptions.add(rs.getString("HICNO"));

            }

            txtPHICNOO.getItems().addAll(riskOptions);


        } catch (Exception e) {

            System.err.print(e);
        }

    }


    private void loadAssociatedBox() {

        try {
            Connection conn = dc.Connect();

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM csmith131db.Patient");

            while(rs.next()){

                associatedOptions.add(rs.getString("HICNO"));

            }

            txtCHICNOO.getItems().addAll(associatedOptions);


        } catch (Exception e) {

            System.err.print(e);
        }

    }

    private void loadCatherterBox() {

        try {
            Connection conn = dc.Connect();

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM csmith131db.Encounter");

            while(rs.next()){

                encounterOptions.add(rs.getString("Encounter_ID"));

            }

            txtEncounterIDD.getItems().addAll(encounterOptions);


        } catch (Exception e) {

            System.err.print(e);
        }

    }

    @FXML
    private void LoadInfectionRatioData(){

        try{

            Connection conn = dc.Connect();

            data = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*)/SUM(TIMESTAMPDIFF(DAY, DTG_Inserted,DTG_Removed)) AS Infection_Per_Day FROM csmith131db.Procedure, csmith131db.Infection");

            columnInfectionRatio.setCellValueFactory(new PropertyValueFactory<>("INFECTION_RATIO"));
            while(rs.next()){
                //get string from db
                data.add(new InfectionRatio(rs.getString("Infection_Per_Day")));
                tableInfectionRatio.setItems(data);
            }
            conn.close();
            rs.close();
        }catch (Exception e){
            System.err.print(e);
        }
    }

    public void EnableControls(ActionEvent event){


        if(rdoChecklistFollowed.isSelected())
        {
            rdoCFYes.setDisable(false);
            rdoCFNo.setDisable(false);
            rdoCFEmergency.setDisable(false);
            rdoCFRoutine.setDisable(false);

        }else
        {
            rdoCFYes.setDisable(true);
            rdoCFNo.setDisable(true);
            rdoCFEmergency.setDisable(true);
            rdoCFRoutine.setDisable(true);
        }

        if(rdoLocationList.isSelected())
        {
            rdoLYes.setDisable(false);
            rdoLNo.setDisable(false);
            rdoLEmergency.setDisable(false);
            rdoLRoutine.setDisable(false);

        }else
        {
            rdoLYes.setDisable(true);
            rdoLNo.setDisable(true);
            rdoLEmergency.setDisable(true);
            rdoLRoutine.setDisable(true);
        }

        if(rdoPatientRiskFactor.isSelected())
        {
            txtPHICNOO.setDisable(false);
        }else
        {
            txtPHICNOO.setDisable(true);
        }

        if(rdoCatheterList.isSelected())
        {
            txtEncounterIDD.setDisable(false);
        }else
        {
            txtEncounterIDD.setDisable(true);
        }

        if(rdoCLABSIList.isSelected())
        {

            txtCHICNOO.setDisable(false);
        }else
        {

            txtCHICNOO.setDisable(true);
        }


    }




    public void LoadQueryValue(ActionEvent event){

        try{

            dc = new DbConnection();

            if(rdoInfectionRatio.isSelected()) {
                tableInfectionRatio.setVisible(true);
                tableChecklistFollowed.setVisible(false);
                tableDoclistCL.setVisible(false);
                tablePatientRiskFactor.setVisible(false);
                tableCatheterList.setVisible(false);
                tableCLABSIList.setVisible(false);
                tableLocationList.setVisible(false);
                LoadInfectionRatioData();
            }

            if(rdoChecklistFollowed.isSelected())
            {
                tableInfectionRatio.setVisible(false);
                tableChecklistFollowed.setVisible(true);
                tableDoclistCL.setVisible(false);
                tablePatientRiskFactor.setVisible(false);
                tableCatheterList.setVisible(false);
                tableCLABSIList.setVisible(false);
                tableLocationList.setVisible(false);
                LoadChecklistFollowedData();
            }

            if(rdoDoclistCL.isSelected())
            {
                tableInfectionRatio.setVisible(false);
                tableChecklistFollowed.setVisible(false);
                tableDoclistCL.setVisible(true);
                tablePatientRiskFactor.setVisible(false);
                tableCatheterList.setVisible(false);
                tableCLABSIList.setVisible(false);
                tableLocationList.setVisible(false);
                LoadDoclistCLData();
            }
            if(rdoPatientRiskFactor.isSelected()) {
                tablePatientRiskFactor.setVisible(true);
                tableInfectionRatio.setVisible(false);
                tableChecklistFollowed.setVisible(false);
                tableDoclistCL.setVisible(false);
                tableCatheterList.setVisible(false);
                tableCLABSIList.setVisible(false);
                tableLocationList.setVisible(false);
                LoadPatientRiskFactorData();
            }

            if(rdoCatheterList.isSelected()) {
                tableCatheterList.setVisible(true);
                tableInfectionRatio.setVisible(false);
                tableChecklistFollowed.setVisible(false);
                tableDoclistCL.setVisible(false);
                tablePatientRiskFactor.setVisible(false);
                tableCLABSIList.setVisible(false);
                tableLocationList.setVisible(false);
                LoadCatheterlistData();
            }

            if(rdoCLABSIList.isSelected()) {
                tableCLABSIList.setVisible(true);
                tableCatheterList.setVisible(false);
                tableInfectionRatio.setVisible(false);
                tableChecklistFollowed.setVisible(false);
                tableDoclistCL.setVisible(false);
                tablePatientRiskFactor.setVisible(false);
                tableLocationList.setVisible(false);
                LoadCLASBIListData();
            }

            if(rdoLocationList.isSelected()) {
                tableLocationList.setVisible(true);
                tableCLABSIList.setVisible(false);
                tableCatheterList.setVisible(false);
                tableInfectionRatio.setVisible(false);
                tableChecklistFollowed.setVisible(false);
                tableDoclistCL.setVisible(false);
                tablePatientRiskFactor.setVisible(false);
                LoadPatientLocationData();
            }


        }catch (Exception e){
            System.err.print(e);
        }

    }

    @FXML
    private void LoadChecklistFollowedData(){

        try{

            String cfYesOrNo = "N";
            String cfRoutOrEmer = "Routine";
            if(rdoCFYes.isSelected())
                cfYesOrNo = "Y";
            if(rdoCFEmergency.isSelected())
                cfRoutOrEmer = "Emergency";

            Connection conn = dc.Connect();

            dataCF = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT (SELECT COUNT(*) FROM csmith131db.Procedure WHERE Procedure_List_Follow = '" + cfYesOrNo  + "')/(SELECT COUNT(*) FROM csmith131db.Procedure WHERE Insertion_Circumstance='" + cfRoutOrEmer + "') As Checklist_Followed;" );

            columnChecklistFollowed.setCellValueFactory(new PropertyValueFactory<>("Checklist_Followed"));
            while(rs.next()){
                //get string from db
                dataCF.add(new ChecklistFollowed(rs.getString("Checklist_Followed")));
                tableChecklistFollowed.setItems(dataCF);
            }
            conn.close();
            rs.close();
        }catch (Exception e){
            System.err.print(e);
        }
    }

    @FXML
    private void LoadDoclistCLData(){

        try{

            Connection conn = dc.Connect();

            dataCL = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT Inserting_Provider, EN_ID, Infection_Type FROM (csmith131db.Procedure JOIN Infection on EN_ID = E_ID) WHERE Infection_Type = 'local' OR Infection_type = 'systemic';");

            columnDoclistCL_InsPro.setCellValueFactory(new PropertyValueFactory<>("InsPro"));
            columnDoclistCL_EnId.setCellValueFactory(new PropertyValueFactory<>("EnId"));
            columnDoclistCL_InfType.setCellValueFactory(new PropertyValueFactory<>("InfType"));

            while(rs.next()){
                //get string from db
                dataCL.add(new DoclistCL(rs.getString("Inserting_Provider"),rs.getString("EN_ID"),rs.getString("Infection_Type")));
                tableDoclistCL.setItems(dataCL);
            }
            conn.close();
            rs.close();
        }catch (Exception e){
            System.err.print(e);
        }
    }

    @FXML
    private void LoadPatientRiskFactorData(){

        try{
            String prhicno = (String) txtPHICNOO.getSelectionModel().getSelectedItem();
            Connection conn = dc.Connect();

            dataPR = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT ImmunoSuppressive_Medications + Antibiotics + MRSA + COPD + HIV + Cancer + Tuberculosis + Diabetes + Pregnant + Obesity + A1C_Level_Low as Patient_Risk_Factor FROM (Patient JOIN Medical_History on HICNO=HICID) WHERE HICNO='" +  prhicno + "';");

            columnPatientRiskFactor.setCellValueFactory(new PropertyValueFactory<>("PR_Factor"));
            while(rs.next()){
                //get string from db
                dataPR.add(new PatientRiskFactor(rs.getString("Patient_Risk_Factor")));
                tablePatientRiskFactor.setItems(dataPR);
            }
            conn.close();
            rs.close();
        }catch (Exception e){
            System.err.print(e);
        }
    }


    @FXML
    private void LoadCatheterlistData(){

        try{

            String enID = (String) txtEncounterIDD.getSelectionModel().getSelectedItem();
            Connection conn = dc.Connect();

            dataCatheterList = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT HICNO, Catheter_Type, Inserting_Provider FROM (Encounter JOIN csmith131db.Procedure on Encounter_ID = EN_ID) WHERE EN_ID='" + enID + "';");

            columnCatheterList_HICNO.setCellValueFactory(new PropertyValueFactory<>("HICNO"));
            columnCatheterList_Catheter_Type.setCellValueFactory(new PropertyValueFactory<>("CatType"));
            columnCatheterList_Ins_Provider.setCellValueFactory(new PropertyValueFactory<>("InsPro"));

            while(rs.next()){
                //get string from db
                dataCatheterList.add(new CatheterList(rs.getString("HICNO"),rs.getString("Catheter_Type"),rs.getString("Inserting_Provider")));
                tableCatheterList.setItems(dataCatheterList);
            }
            conn.close();
            rs.close();
        }catch (Exception e){
            System.err.print(e);
        }
    }


    @FXML
    private void LoadCLASBIListData(){

        try{

            String hNo = (String) txtCHICNOO.getSelectionModel().getSelectedItem();
            Connection conn = dc.Connect();

            dataCLABSIList = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT Infection_Type , Encounter_ID, HICNO FROM Infection,Encounter WHERE E_ID = Encounter_ID AND HICNO='" + hNo + "';");

            columnCLABSIList_Infection_Type.setCellValueFactory(new PropertyValueFactory<>("InfType"));
            columnCLABSIList_Enc_ID.setCellValueFactory(new PropertyValueFactory<>("EncID"));
            columnCLABSIList_HICNo.setCellValueFactory(new PropertyValueFactory<>("HICNO"));

            while(rs.next()){
                //get string from db
                dataCLABSIList.add(new CLABSIList(rs.getString("Infection_Type"),rs.getString("Encounter_ID"),rs.getString("HICNO")));
                tableCLABSIList.setItems(dataCLABSIList);
            }
            conn.close();
            rs.close();
        }catch (Exception e){
            System.err.print(e);
        }
    }

    @FXML
    private void LoadPatientLocationData(){

        try{

            String cfYesOrNo = "N";
            String cfRoutOrEmer = "Routine";
            if(rdoLYes.isSelected())
                cfYesOrNo = "Y";
            if(rdoLEmergency.isSelected())
                cfRoutOrEmer = "Emergency";

            Connection conn = dc.Connect();

            dataPL = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("SELECT Patient_Location " +
                    "FROM csmith131db.Procedure " +
                    "WHERE Procedure_List_Follow='" + cfYesOrNo + "' AND Insertion_Circumstance='" + cfRoutOrEmer + "';" );

            columnPatientLocation.setCellValueFactory(new PropertyValueFactory<>("Patient_Location"));
            while(rs.next()){
                //get string from db
                dataPL.add(new PatientLocation(rs.getString("Patient_Location")));
                tableLocationList.setItems(dataPL);
            }
            conn.close();
            rs.close();
        }catch (Exception e){
            System.err.print(e);
        }
    }

    @FXML
    public void menuAnalysisClicks(ActionEvent event) {

        try{

            Parent record = FXMLLoader.load(getClass().getResource("Analysis.fxml"));

            Scene recordScene = new Scene(record);

            Stage stage = (Stage) Query.getScene().getWindow();

            stage.setScene(recordScene);

            stage.show();


        }catch (Exception e){
            System.err.print(e);
        }

    }

    @FXML
    public void menuRECORDClicks(ActionEvent event) {

        try{



            Parent record = FXMLLoader.load(getClass().getResource("Encounter_and_Record.fxml"));

            Scene recordScene = new Scene(record);

            Stage stage = (Stage) Query.getScene().getWindow();

            stage.setScene(recordScene);

            stage.show();



        }catch (Exception e){
            System.err.print(e);
        }

    }

    @FXML
    public void menuHOMEClicks(ActionEvent event) {

        try{

            Parent record = FXMLLoader.load(getClass().getResource("Patient.fxml"));

            Scene recordScene = new Scene(record);


            Stage stage = (Stage) Query.getScene().getWindow();

            stage.setScene(recordScene);
            stage.show();

        }catch (Exception e){
            System.err.print(e);
        }

    }



}