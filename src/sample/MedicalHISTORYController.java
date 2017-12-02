//package sample;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.RadioButton;
//import javafx.scene.control.TextField;
//import javafx.scene.control.ToggleGroup;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//
//public class MedicalHISTORYController {
//
//    private DbConnection dc;
//
//
//
//    //controller for medical history
//    @FXML
//    private TextField History_Hicno;
//
//    @FXML
//    private RadioButton immuneYes,antiYes,mrsaYes,copdYes,cancerYes,tuberYes,diabetesYes,pregnantYes,hivYes,obesityYes,a1cYes;
//
//    @FXML
//    private ToggleGroup immune,antibiotics, mrsa,copd,cancer,tuberculosis,diabetes,pregnant,obesity,a1c,hiv;
//
//
//    private Integer  IMMUNE, ANTIBIOTICS, MRSA, COPD, CANCER, TUBERCULOSIS, DIABETES, PREGNANT, OBESITY, A1C, HIV;
//
//    @FXML
//    void updateMedicalHistory(ActionEvent event) {
//
//        //IMMUNE SUPPRESSIVE MEDICINE
//        if(immuneYes.isSelected()){ IMMUNE = 1; System.out.println(IMMUNE); } else { IMMUNE = 0; }
//
//        //ANTIBIOTICS
//        if(antiYes.isSelected()){ ANTIBIOTICS = 1;System.out.println(ANTIBIOTICS); } else { ANTIBIOTICS = 0; }
//
//        //MRSA
//        if(mrsaYes.isSelected()){ MRSA = 1; } else { MRSA = 0; }
//
//        //COPD
//        if(copdYes.isSelected()){ COPD = 1; } else { COPD = 0; }
//
//        //CANCER
//        if(cancerYes.isSelected()){ CANCER = 1; } else { CANCER = 0;}
//
//        //TUBERCULOSIS
//        if(tuberYes.isSelected()){ TUBERCULOSIS = 1; } else { TUBERCULOSIS = 0;}
//
//        //DIABETES
//        if(diabetesYes.isSelected()){ DIABETES = 1; }else { DIABETES = 0; }
//
//        //PREGNANT
//        if(pregnantYes.isSelected()){ PREGNANT = 1; } else { PREGNANT = 0;}
//
//        //HIV
//        if(hivYes.isSelected()){ HIV = 1; } else {HIV = 0;}
//
//        //OBESITY
//        if(obesityYes.isSelected()){ OBESITY = 1; } else { OBESITY = 0;}
//
//        //A1C
//        if(a1cYes.isSelected()){ A1C = 1; } else { A1C = 0;}
//
//
//        try{
//
//            Connection conn = dc.Connect();
//
//            String query = "INSERT INTO Medical_History VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
//
//            PreparedStatement preparedStatement = conn.prepareStatement(query);
//
//            preparedStatement.setString(1, History_Hicno.getText());
//            preparedStatement.setInt(2, IMMUNE);
//            preparedStatement.setInt(3, ANTIBIOTICS);
//            preparedStatement.setInt(4, MRSA);
//            preparedStatement.setInt(5, COPD);
//            preparedStatement.setInt(6, HIV);
//            preparedStatement.setInt(7, CANCER);
//            preparedStatement.setInt(8, TUBERCULOSIS);
//            preparedStatement.setInt(9, DIABETES);
//            preparedStatement.setInt(10, PREGNANT);
//            preparedStatement.setInt(11, OBESITY);
//            preparedStatement.setInt(12, A1C);
//
//
//            preparedStatement.execute();
//
//            conn.close();
//
//
//        }catch (Exception e){
//            System.err.print(e);
//        }
//
//
//
//    }
//
//
//
//}
