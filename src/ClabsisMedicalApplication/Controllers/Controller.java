package ClabsisMedicalApplication.Controllers;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ClabsisMedicalApplication.Model.DbConnection;
import ClabsisMedicalApplication.TableViewClasses.Patient;


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
    public TextField patient_sex, patient_birth,patient_name,hicno,hospital_ccn;

    //initalize observable list to hold data from database
    private ObservableList<Patient> data;

    private DbConnection dc;


    @FXML
    final ObservableList patientOptions = FXCollections.observableArrayList();
    @FXML
    public ComboBox patientComboBox;



    @FXML
    final ObservableList currentPatient = FXCollections.observableArrayList();
    @FXML
    public ComboBox currentpatientComboBox;



    //data for medical history pop up
    @FXML
    private TextField History_Hicno;

    @FXML
    private RadioButton immuneYes,antiYes,mrsaYes,copdYes,cancerYes,tuberYes,diabetesYes,pregnantYes,hivYes,obesityYes,a1cYes,a1cNo;

    @FXML
    private RadioButton immuneNo,antiNo,mrsaNo,copdNo,hivNo,cancerNo,tuberNo,diabetesNo,pregnantNo,obesityNo;



    @FXML
    private ToggleGroup immune,antibiotics, mrsa,copd,cancer,tuberculosis,diabetes,pregnant,obesity,a1c,hiv;


    private Integer  IMMUNE, ANTIBIOTICS, MRSA, COPD, CANCER, TUBERCULOSIS, DIABETES, PREGNANT, OBESITY, A1C, HIV;


    @FXML
    private Label immunoLabel,tuberLabel,anitLabel,diabetesLabel,mrsaLabel,pregnantLabel,copdLabel,obesityLabel,hivLabel,a1cLabel,cancerLabel;



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

        //    resets textfields on loading the database table
            patient_sex.setText("");
            patient_birth.setText("");
            patient_name.setText("");
            hicno.setText("");
            hospital_ccn.setText("");

            resetMedical();

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

                //function that automatically initialize new patient data to 0, but for right now user needs to upload data
                //there is not automatic filling of data
               // initializePatientMedicalHistory(hicno.getText());

                 LoadPatientData();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Patient Information Added ");
                alert.setHeaderText(null);
                alert.setContentText("Patient Information Successfully Added");
                alert.showAndWait();

            } catch (Exception e) {
                System.err.print(e);
            }


        }

    }



    @FXML
    public void MedicalHistory(ActionEvent event){


        try{
            fxmlLoader = new FXMLLoader(getClass().getResource("/ClabsisMedicalApplication/Views/Medical_History.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            window = new Stage();

            window.setTitle("Medical History");
            window.setScene(new Scene(root));
            window.show();


        }catch (Exception e){
            System.err.print(e);
        }

    }

    @FXML
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

                String query = "UPDATE Patient SET HICNO = ?, NAME = ?, DOB= ?, SEX  = ?, CCN_ID = ? WHERE HICNO = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, hicno.getText());
                preparedStatement.setString(2, patient_name.getText());
                preparedStatement.setString(3, patient_birth.getText());
                preparedStatement.setString(4, patient_sex.getText());
                preparedStatement.setString(5, hospital_ccn.getText());
                preparedStatement.setString(6, hicno.getText());


                preparedStatement.executeUpdate();

                conn.close();

                LoadPatientData();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Patient Information Updated ");
                alert.setHeaderText(null);
                alert.setContentText("Patient Information Successfully Updated");
                alert.showAndWait();


            } catch (Exception e) {
                System.err.print(e);
            }

        }

    }



    @FXML
    public void medicalUpdate(ActionEvent event){


        String Hicno = (String)currentpatientComboBox.getSelectionModel().getSelectedItem();

        if(History_Hicno.getText().isEmpty() || Hicno.isEmpty()){


            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Medical History Updated Error!");
            alert.setHeaderText(null);
            alert.setContentText("Medical History Could Not Be Updated. Please make sure all fields are filled");
            alert.showAndWait();



        }else {


            //IMMUNE SUPPRESSIVE MEDICINE
            if (immuneYes.isSelected()) {
                IMMUNE = 1;
                System.out.println(IMMUNE);
            } else {
                IMMUNE = 0;
            }

            //ANTIBIOTICS
            if (antiYes.isSelected()) {
                ANTIBIOTICS = 1;
                System.out.println(ANTIBIOTICS);
            } else {
                ANTIBIOTICS = 0;
            }

            //MRSA
            if (mrsaYes.isSelected()) {
                MRSA = 1;
            } else {
                MRSA = 0;
            }

            //COPD
            if (copdYes.isSelected()) {
                COPD = 1;
            } else {
                COPD = 0;
            }

            //CANCER
            if (cancerYes.isSelected()) {
                CANCER = 1;
            } else {
                CANCER = 0;
            }

            //TUBERCULOSIS
            if (tuberYes.isSelected()) {
                TUBERCULOSIS = 1;
            } else {
                TUBERCULOSIS = 0;
            }

            //DIABETES
            if (diabetesYes.isSelected()) {
                DIABETES = 1;
            } else {
                DIABETES = 0;
            }

            //PREGNANT
            if (pregnantYes.isSelected()) {
                PREGNANT = 1;
            } else {
                PREGNANT = 0;
            }

            //HIV
            if (hivYes.isSelected()) {
                HIV = 1;
            } else {
                HIV = 0;
            }

            //OBESITY
            if (obesityYes.isSelected()) {
                OBESITY = 1;
            } else {
                OBESITY = 0;
            }

            //A1C
            if (a1cYes.isSelected()) {
                A1C = 1;
            } else {
                A1C = 0;
            }


            try {


                Connection conn = dc.Connect();

                String query = "UPDATE Medical_History SET ImmunoSuppressive_Medications = ?, Antibiotics =?," +
                        " MRSA=?,COPD=?,HIV=?,Cancer=?,Tuberculosis=?,Diabetes=?,Pregnant=?,Obesity=?,A1C_Level_Low = ? WHERE HICID = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);


                preparedStatement.setInt(1, IMMUNE);
                preparedStatement.setInt(2, ANTIBIOTICS);
                preparedStatement.setInt(3, MRSA);
                preparedStatement.setInt(4, COPD);
                preparedStatement.setInt(5, HIV);
                preparedStatement.setInt(6, CANCER);
                preparedStatement.setInt(7, TUBERCULOSIS);
                preparedStatement.setInt(8, DIABETES);
                preparedStatement.setInt(9, PREGNANT);
                preparedStatement.setInt(10, OBESITY);
                preparedStatement.setInt(11, A1C);
                preparedStatement.setString(12, Hicno);


                preparedStatement.execute();

                conn.close();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Medical History Updated");
                alert.setHeaderText(null);
                alert.setContentText("Medical History Successfully Updated");
                alert.showAndWait();


            } catch (Exception e) {
                System.err.print(e);
            }
        }


    }

    @FXML
    void addMedicalHistory(ActionEvent event) {


        String Hicno = (String)currentpatientComboBox.getSelectionModel().getSelectedItem();

        if(History_Hicno.getText().isEmpty() || Hicno.isEmpty()){


            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Adding Medical History Error!");
            alert.setHeaderText(null);
            alert.setContentText("Medical History Could Not Be Added. Please make sure all fields are filled");
            alert.showAndWait();



        }else {

            //IMMUNE SUPPRESSIVE MEDICINE
            if (immuneYes.isSelected()) {
                IMMUNE = 1;
                System.out.println(IMMUNE);
            } else {
                IMMUNE = 0;
            }

            //ANTIBIOTICS
            if (antiYes.isSelected()) {
                ANTIBIOTICS = 1;
                System.out.println(ANTIBIOTICS);
            } else {
                ANTIBIOTICS = 0;
            }

            //MRSA
            if (mrsaYes.isSelected()) {
                MRSA = 1;
            } else {
                MRSA = 0;
            }

            //COPD
            if (copdYes.isSelected()) {
                COPD = 1;
            } else {
                COPD = 0;
            }

            //CANCER
            if (cancerYes.isSelected()) {
                CANCER = 1;
            } else {
                CANCER = 0;
            }

            //TUBERCULOSIS
            if (tuberYes.isSelected()) {
                TUBERCULOSIS = 1;
            } else {
                TUBERCULOSIS = 0;
            }

            //DIABETES
            if (diabetesYes.isSelected()) {
                DIABETES = 1;
            } else {
                DIABETES = 0;
            }

            //PREGNANT
            if (pregnantYes.isSelected()) {
                PREGNANT = 1;
            } else {
                PREGNANT = 0;
            }

            //HIV
            if (hivYes.isSelected()) {
                HIV = 1;
            } else {
                HIV = 0;
            }

            //OBESITY
            if (obesityYes.isSelected()) {
                OBESITY = 1;
            } else {
                OBESITY = 0;
            }

            //A1C
            if (a1cYes.isSelected()) {
                A1C = 1;
            } else {
                A1C = 0;
            }


            try {

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


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Medical History Added ");
                alert.setHeaderText(null);
                alert.setContentText("Medical History Successfully Added");
                alert.showAndWait();


            } catch (Exception e) {
                System.err.print(e);
            }
        }

    }


    @FXML
    private void initializePatientMedicalHistory(String patient_hicno){

        //IMMUNE SUPPRESSIVE MEDICINE
        IMMUNE = 0;

        //ANTIBIOTICS
        ANTIBIOTICS = 0;

        //MRSA
        MRSA = 0;

        //COPD
        COPD = 0;

        //CANCER
        CANCER = 0;

        //TUBERCULOSIS
        TUBERCULOSIS = 0;

        //DIABETES
        DIABETES = 0;

        //PREGNANT
        PREGNANT = 0;

        //HIV
        HIV = 0;

        //OBESITY
        OBESITY = 0;

        //A1C
        A1C = 0;

        try {

            Connection conn = dc.Connect();

            String query = "INSERT INTO Medical_History VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, patient_hicno);
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


        } catch (Exception e) {
            System.err.print(e);
        }


    }



    @FXML
    public void menuAnalysisClicks(ActionEvent event) {

        try{

            Parent record = FXMLLoader.load(getClass().getResource("/ClabsisMedicalApplication/Views/Analysis.fxml"));

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



            Parent record = FXMLLoader.load(getClass().getResource("/ClabsisMedicalApplication/Views/Encounter_and_Record.fxml"));

            Scene recordScene = new Scene(record);

            Stage stage = (Stage) Patient_History.getScene().getWindow();

            stage.setScene(recordScene);

            stage.show();



        }catch (Exception e){
            System.err.print(e);
        }

    }


    @FXML
    public void fillMedicalForm(ActionEvent event){


        try  {

            Connection conn = dc.Connect();

            String query ="SELECT * FROM csmith131db.Medical_History WHERE HICID = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, (String)currentpatientComboBox.getSelectionModel().getSelectedItem());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){


                History_Hicno.setText(rs.getString("HICID"));

                if(rs.getInt("ImmunoSuppressive_Medications") == 0){ immuneNo.setSelected(true);
                }else{immuneYes.setSelected(true);}

                if(rs.getInt("Antibiotics") == 0){ antiNo.setSelected(true);
                }else{antiYes.setSelected(true);}

                if(rs.getInt("MRSA") == 0){ mrsaNo.setSelected(true);
                }else{mrsaYes.setSelected(true);}

                if(rs.getInt("COPD") == 0){ copdNo.setSelected(true);
                }else{copdYes.setSelected(true);}

                if(rs.getInt("HIV") == 0){ hivNo.setSelected(true);
                }else{hivYes.setSelected(true);}

                if(rs.getInt("Cancer") == 0){ cancerNo.setSelected(true);
                }else{cancerYes.setSelected(true);}

                if(rs.getInt("Tuberculosis") == 0){ tuberNo.setSelected(true);
                }else{tuberYes.setSelected(true);}

                if(rs.getInt("Diabetes") == 0){ diabetesNo.setSelected(true);
                }else{diabetesYes.setSelected(true);}

                if(rs.getInt("Pregnant") == 0){ pregnantNo.setSelected(true);
                }else{pregnantYes.setSelected(true);}

                if(rs.getInt("Obesity") == 0){ obesityNo.setSelected(true);
                }else{obesityYes.setSelected(true);}

                if(rs.getInt("A1C_Level_Low") == 0){ a1cNo.setSelected(true);
                }else{a1cYes.setSelected(true);}





            }

            conn.close();
            preparedStatement.close();



        } catch (Exception e){
            System.err.println(e);
        }

    }



    private void loadPatientBox() {

        try {
            Connection conn = dc.Connect();

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM csmith131db.Patient");

            while(rs.next()){

                currentPatient.add(rs.getString("HICNO"));

            }

            currentpatientComboBox.getItems().addAll(currentPatient);


        } catch (Exception e) {

            System.err.print(e);
        }

    }


    public void fillPatientForm(String HicID){
        String hicnonumber = null;

        try  {

            Connection conn = dc.Connect();

            String query ="SELECT * FROM csmith131db.Patient WHERE HICNO = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, HicID);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){

                hicno.setText(rs.getString("HICNO"));
                patient_name.setText(rs.getString("NAME"));
                patient_birth.setText(rs.getString("DOB"));
                patient_sex.setText(rs.getString("SEX"));
                hospital_ccn.setText(rs.getString("CCN_ID"));

                hicnonumber = rs.getString("HICNO");

            }


            getMedicalHistory(hicnonumber);


            conn.close();
            preparedStatement.close();



        } catch (Exception e){
            System.err.println(e);
        }

    }


    @FXML
    public void tablePatientClick(MouseEvent mouseEvent){

        if(tablePatient.getSelectionModel().getSelectedItem() != null){


            int index = tablePatient.getSelectionModel().selectedIndexProperty().get();
            String hicno = null;

            try{


                Connection conn = dc.Connect();
                String query = "SELECT * FROM csmith131db.Patient LIMIT ? ,1";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, index);

                ResultSet rs = preparedStatement.executeQuery();

                while(rs.next()){

                    hicno = rs.getString("HICNO");

                }

                getMedicalHistory(hicno);
                fillPatientForm(hicno);



                conn.close();

                rs.close();

            }catch(Exception e){

            }

        }
    }






    private void getMedicalHistory(String hicno){

        try{


            Connection conn = dc.Connect();
            String  query = "SELECT * FROM csmith131db.Medical_History WHERE HICID IN (\n" +
                    "  SELECT HICID\n" +
                    "  FROM csmith131db.Patient\n" +
                    "  WHERE HICNO = ? AND HICID = ?)";

            PreparedStatement  preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, hicno);
            preparedStatement.setString(2, hicno);

            ResultSet rs = preparedStatement.executeQuery();



            while(rs.next()){


                if(rs.getInt("ImmunoSuppressive_Medications")== 0){ immunoLabel.setText("NO");
                }else {immunoLabel.setText("Yes"); }

                if(rs.getInt("Antibiotics") == 0){ anitLabel.setText("NO");
                }else {anitLabel.setText("YES"); }


                if(rs.getInt("MRSA") == 0){ mrsaLabel.setText("NO");
                }else {mrsaLabel.setText("YES"); }


                if(rs.getInt("COPD") == 0){ copdLabel.setText("NO");
                }else {copdLabel.setText("YES"); }

                if(rs.getInt("HIV") == 0){ hivLabel.setText("NO");
                }else {hivLabel.setText("YES"); }

                if(rs.getInt("Cancer") == 0){ cancerLabel.setText("NO");
                }else {cancerLabel.setText("Yes"); }

                if(rs.getInt("Tuberculosis") == 0){ tuberLabel.setText("NO");
                }else {tuberLabel.setText("YES"); }

                if(rs.getInt("Diabetes") == 0){ diabetesLabel.setText("NO");
                }else {diabetesLabel.setText("YES"); }


                if(rs.getInt("Pregnant") == 0){ pregnantLabel.setText("NO");
                }else {pregnantLabel.setText("YES"); }

                if(rs.getInt("Obesity") == 0){ obesityLabel.setText("NO");
                }else {obesityLabel.setText("YES"); }

                if(rs.getInt("A1C_Level_Low") == 0){ a1cLabel.setText("NO");
                }else {a1cLabel.setText("YES"); }



            }


        }catch (Exception e){
            System.err.println(e);
        }

    }




    private void resetMedical(){

        immunoLabel.setText(" ");

        anitLabel.setText(" ");

        mrsaLabel.setText(" ");

        copdLabel.setText(" ");

        hivLabel.setText(" ");

        cancerLabel.setText(" ");

        tuberLabel.setText(" ");

        diabetesLabel.setText(" ");

        pregnantLabel.setText(" ");

        obesityLabel.setText(" ");

        a1cLabel.setText(" ");




    }



}