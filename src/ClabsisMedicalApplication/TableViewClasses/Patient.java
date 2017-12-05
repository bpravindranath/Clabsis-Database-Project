package ClabsisMedicalApplication.TableViewClasses;


//this is a model class, holding getters, setters, and properties

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Patient {


    private final StringProperty HICNO;
    private final StringProperty NAME;
    private final StringProperty DOB;
    private final StringProperty SEX;
    private final StringProperty CCN_ID;


    //defualt contructor
    public Patient(String hicno, String name, String dob, String sex, String ccn_id){
        this.HICNO = new SimpleStringProperty(hicno);
        this.NAME = new SimpleStringProperty(name);
        this.DOB = new SimpleStringProperty(dob);
        this.SEX = new SimpleStringProperty(sex);
        this.CCN_ID = new SimpleStringProperty(ccn_id);
    }


   //Getters
    public String getHICNO(){return  HICNO.get();}

    public String getName(){return  NAME.get();}

    public String getDOB(){return  DOB.get();}

    public String getSEX(){return  SEX.get();}

    public String getCCN_ID(){return  CCN_ID.get();}



    //Setters


    public void setHICNO(String hicno){ HICNO.setValue(hicno);}

    public void setName(String name){ NAME.setValue(name);}

    public void setDOB(String dob){ DOB.setValue(dob);}

    public void setSEX(String sex){ SEX.setValue(sex);}

    public void setCCN_ID(String ccn_id){ CCN_ID.setValue(ccn_id);}



    //Proptery Values




    public StringProperty HICNOProperty() {
        return HICNO;
    }

    public StringProperty NAMEProperty() {
        return NAME;
    }

    public StringProperty DOBProperty() {
        return DOB;
    }

    public StringProperty SEXProperty() {
        return SEX;
    }

    public StringProperty CCN_IDProperty() {
        return CCN_ID;
    }



}
