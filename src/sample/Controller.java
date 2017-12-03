package sample;


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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Controller implements Initializable  {


    public javafx.scene.control.MenuBar menu;
    public Pane Patient_History;

    Stage window;
    FXMLLoader fxmlLoader;

    @FXML
    private Pane pane;

    @FXML
    private AnchorPane anchorPane;

    public javafx.scene.control.Menu Opener;

    @FXML
    private TableView<Patient> tablePatient;
    @FXML
    private TableColumn<Patient,String> columnHICNO;
    @FXML
    private TableColumn<Patient,String> columnNAME;
    @FXML
    private TableColumn<Patient,String> columnDOB;
    @FXML
    private TableColumn<Patient,String> columnSEX;
    @FXML
    private TableColumn<Patient,String> columnCCN_ID;
    @FXML
    private Button ButtonLoad;

    @FXML
    public TextField patient_sex, patient_birth,patient_name,hicno,hospital_ccn;

    //initalize observable list to hold data from database
    private ObservableList<Patient> data;

    private DbConnection dc;


    @FXML
    final ObservableList patientOptions = FXCollections.observableArrayList();
    @FXML
    public ComboBox patientComboBox;

    //controller for medical history
    @FXML
    private TextField History_Hicno;

    @FXML
    private RadioButton immuneYes,antiYes,mrsaYes,copdYes,cancerYes,tuberYes,diabetesYes,pregnantYes,hivYes,obesityYes,a1cYes;

    @FXML
    private ToggleGroup immune,antibiotics, mrsa,copd,cancer,tuberculosis,diabetes,pregnant,obesity,a1c,hiv;


    private Integer  IMMUNE, ANTIBIOTICS, MRSA, COPD, CANCER, TUBERCULOSIS, DIABETES, PREGNANT, OBESITY, A1C, HIV;


    @FXML
    private Label hello;



    @Override
    public void initialize(URL url, ResourceBundle rb){


        //TODO
        dc = new DbConnection();



        LoadPatientData();

        loadPatientBox();

    }


    @FXML //loads patient table
    private void LoadPatientData(){

       try{

           Connection conn = dc.Connect();

           data = FXCollections.observableArrayList();


           ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM csmith131db.Patient");


           columnHICNO.setCellValueFactory(new PropertyValueFactory<>("HICNO"));
           columnNAME.setCellValueFactory(new PropertyValueFactory<>("NAME"));
           columnDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
           columnSEX.setCellValueFactory(new PropertyValueFactory<>("SEX"));
           columnCCN_ID.setCellValueFactory(new PropertyValueFactory<>("CCN_ID"));

           while(rs.next()){

               //get string from db

              data.add(new Patient(
                      rs.getString("HICNO"),
                      rs.getString("NAME"),
                      rs.getString("DOB"),
                      rs.getString("SEX"),
                      rs.getString("CCN_ID")));
               tablePatient.setItems(data);
           }


          conn.close();
           rs.close();


       }catch (Exception e){
            System.err.print(e);
       }



    }

    @FXML //function to  patient
    public void addPatient(ActionEvent event){

        if(hicno.getText().isEmpty() || patient_name.getText().isEmpty() || patient_birth.getText().isEmpty()
                || patient_sex.getText().isEmpty() || hospital_ccn.getText().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validate Fields");
                alert.setHeaderText(null);
                alert.setContentText("Please Make sure all fields are filled in and HICNO is unique");
                alert.showAndWait();

        } else {

            try {


                Connection conn = dc.Connect();

                String query = "INSERT INTO Patient VALUES (?,?,?,?,?)";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, hicno.getText());
                preparedStatement.setString(2, patient_name.getText());
                preparedStatement.setString(3, patient_birth.getText());
                preparedStatement.setString(4, patient_sex.getText());
                preparedStatement.setString(5, hospital_ccn.getText());


                preparedStatement.execute();

                conn.close();

                LoadPatientData();

            } catch (Exception e) {
                System.err.print(e);
            }

        }

    }




    public void MedicalHistory(ActionEvent event){

        hello.setText("HELLO");

        try{
            fxmlLoader = new FXMLLoader(getClass().getResource("Medical_History.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            window = new Stage();

            window.setTitle("Medical History");
            window.setScene(new Scene(root));
            window.show();



        }catch (Exception e){
            System.err.print(e);
        }

    }


    public void updatePatient(ActionEvent event){


        if(hicno.getText().isEmpty() || patient_name.getText().isEmpty() || patient_birth.getText().isEmpty()
                || patient_sex.getText().isEmpty() || hospital_ccn.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setHeaderText(null);
            alert.setContentText("Please Make sure all fields are filled in and HICNO is unique");
            alert.showAndWait();

        } else {


            try {


                Connection conn = dc.Connect();

                String query = "UPDATE Patient SET NAME = ?, DOB= ?, SEX  = ?, CCN_ID = ? WHERE HICNO = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, patient_name.getText());
                preparedStatement.setString(2, patient_birth.getText());
                preparedStatement.setString(3, patient_sex.getText());
                preparedStatement.setString(4, hospital_ccn.getText());
                preparedStatement.setString(5, hicno.getText());


                preparedStatement.executeUpdate();

                conn.close();

                LoadPatientData();

            } catch (Exception e) {
                System.err.print(e);
            }

        }

    }


    @FXML
    void updateMedicalHistory(ActionEvent event) {

        //IMMUNE SUPPRESSIVE MEDICINE
        if(immuneYes.isSelected()){ IMMUNE = 1; System.out.println(IMMUNE); } else { IMMUNE = 0; }

        //ANTIBIOTICS
        if(antiYes.isSelected()){ ANTIBIOTICS = 1;System.out.println(ANTIBIOTICS); } else { ANTIBIOTICS = 0; }

        //MRSA
        if(mrsaYes.isSelected()){ MRSA = 1; } else { MRSA = 0; }

        //COPD
        if(copdYes.isSelected()){ COPD = 1; } else { COPD = 0; }

        //CANCER
        if(cancerYes.isSelected()){ CANCER = 1; } else { CANCER = 0;}

        //TUBERCULOSIS
        if(tuberYes.isSelected()){ TUBERCULOSIS = 1; } else { TUBERCULOSIS = 0;}

        //DIABETES
        if(diabetesYes.isSelected()){ DIABETES = 1; }else { DIABETES = 0; }

        //PREGNANT
        if(pregnantYes.isSelected()){ PREGNANT = 1; } else { PREGNANT = 0;}

        //HIV
        if(hivYes.isSelected()){ HIV = 1; } else {HIV = 0;}

        //OBESITY
        if(obesityYes.isSelected()){ OBESITY = 1; } else { OBESITY = 0;}

        //A1C
        if(a1cYes.isSelected()){ A1C = 1; } else { A1C = 0;}


        try{

            Connection conn = dc.Connect();

            String query = "INSERT INTO Medical_History VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, History_Hicno.getText());
            preparedStatement.setInt(2, IMMUNE);
            preparedStatement.setInt(3, ANTIBIOTICS);
            preparedStatement.setInt(4, MRSA);
            preparedStatement.setInt(5, COPD);
            preparedStatement.setInt(6, HIV);
            preparedStatement.setInt(7, CANCER);
            preparedStatement.setInt(8, TUBERCULOSIS);
            preparedStatement.setInt(9, DIABETES);
            preparedStatement.setInt(10, PREGNANT);
            preparedStatement.setInt(11, OBESITY);
            preparedStatement.setInt(12, A1C);


            preparedStatement.execute();

            conn.close();


        }catch (Exception e){
            System.err.print(e);
        }


    }




    @FXML
    public void menuAnalysisClicks(ActionEvent event) {

        try{

            Parent record = FXMLLoader.load(getClass().getResource("Analysis.fxml"));

            Scene recordScene = new Scene(record);

            Stage stage = (Stage) Patient_History.getScene().getWindow();

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

            Stage stage = (Stage) Patient_History.getScene().getWindow();

            stage.setScene(recordScene);

            stage.show();



        }catch (Exception e){
            System.err.print(e);
        }

    }


    private void loadPatientBox() {

        try {
            Connection conn = dc.Connect();

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM csmith131db.Patient");

            while(rs.next()){

                patientOptions.add(rs.getString("HICNO"));

            }

            patientComboBox.getItems().addAll(patientOptions);


        } catch (Exception e) {

            System.err.print(e);
        }

    }

    @FXML
    public void fillPatientForm(ActionEvent event){


        try  {

            Connection conn = dc.Connect();

            String query ="SELECT * FROM csmith131db.Patient WHERE HICNO = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, (String)patientComboBox.getSelectionModel().getSelectedItem());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){

                hicno.setText(rs.getString("HICNO"));
                patient_name.setText(rs.getString("NAME"));
                patient_birth.setText(rs.getString("DOB"));
                patient_sex.setText(rs.getString("SEX"));
                hospital_ccn.setText(rs.getString("CCN_ID"));


            }


            conn.close();
            preparedStatement.close();



        } catch (Exception e){
            System.err.println(e);
        }

    }




}