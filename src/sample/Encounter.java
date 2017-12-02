package sample;


//this is a model class, holding getters, setters, and properties

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Encounter   {


    private final StringProperty Encounter_ID;
    private final StringProperty HICNO;
    private final StringProperty Primary_Physician;
    private final StringProperty Discharge_Status;
    private final StringProperty Discharge_DTG;
    private final StringProperty Admission_DTG;
    private final StringProperty PROC_Codes;
    private final StringProperty DIAG_POA_Codes;
    private final StringProperty Admitted;




    //defualt contructor
    public Encounter(String encounter_id, String hicno, String physician, String dis_status, String dis_date,
                   String admission_date, String proc_codes, String diag, String admitted){

        this.Encounter_ID = new SimpleStringProperty(encounter_id);
        this.HICNO = new SimpleStringProperty(hicno);
        this.Primary_Physician = new SimpleStringProperty(physician);
        this.Discharge_Status = new SimpleStringProperty(dis_status);
        this.Discharge_DTG = new SimpleStringProperty(dis_date);
        this.Admission_DTG = new SimpleStringProperty(admission_date);
        this.PROC_Codes = new SimpleStringProperty(proc_codes);
        this.DIAG_POA_Codes = new SimpleStringProperty(diag);
        this.Admitted = new SimpleStringProperty(admitted);

    }


    //Getters
    public String getEncounter_ID(){return  Encounter_ID.get();}

    public String getHICNO(){return  HICNO.get();}

    public String getPrimary_Physician(){return  Primary_Physician.get();}

    public String getDischarge_Status(){return  Discharge_Status.get();}

    public String getDischarge_DTG(){return  Discharge_DTG.get();}

    public String getAdmission_DTG(){return  Admission_DTG.get();}

    public String getPROC_Codes(){return  PROC_Codes.get();}

    public String getDIAG_POA_Codes(){return  DIAG_POA_Codes.get();}

    public String getAdmitted(){return  Admitted.get();}





    //Setters


    public void setEncounter_ID(String encounter_id){ Encounter_ID.setValue(encounter_id);}

    public void setHICNO(String hicno){ HICNO.setValue(hicno);}

    public void setPrimary_Physician(String primary_physician){ Primary_Physician.setValue(primary_physician);}

    public void setDischarge_Status(String discharge_status){ Discharge_Status.setValue(discharge_status);}

    public void setDischarge_DTG(String discharge_dtg){ Discharge_DTG.setValue(discharge_dtg);}

    public void setAdmission_DTG(String admission_dtg){ Admission_DTG.setValue(admission_dtg);}

    public void setPROC_Codes(String proc_codes){ PROC_Codes.setValue(proc_codes);}

    public void setDIAG_POA_Codes(String diag_poa_codes){ DIAG_POA_Codes.setValue(diag_poa_codes);}

    public void setAdmitted(String admitted){ Admitted.setValue(admitted);}





    //Proptery Values




    public StringProperty EncounterProperty() {
        return Encounter_ID;
    }

    public StringProperty HicnoProperty() {
        return HICNO;
    }

    public StringProperty PhysicianProperty() {
        return Primary_Physician;
    }

    public StringProperty DischargeStatusProperty() {
        return Discharge_Status;
    }

    public StringProperty DischargeDateProperty() {
        return Discharge_DTG;
    }

    public StringProperty AdmissionDateProperty() {
        return Admission_DTG;
    }

    public StringProperty ProcProperty() {
        return PROC_Codes;
    }

    public StringProperty DIAGProperty() {
        return DIAG_POA_Codes;
    }

    public StringProperty AdmittedProperty() {
        return Admitted;
    }





}
