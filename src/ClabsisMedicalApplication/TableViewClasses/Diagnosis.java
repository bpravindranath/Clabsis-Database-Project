package ClabsisMedicalApplication.TableViewClasses;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Diagnosis {



    private final StringProperty Infection_Type;
    private final StringProperty Diagnosis_DTG;
    private final StringProperty Insertion_Site;
    private final StringProperty Inserting_Provider;
    private final StringProperty Insertion_Circumstance;
    private final StringProperty Catheter_Type;
    private final StringProperty Procedure_List_Follow;
    private final StringProperty Patient_Location;
    private final StringProperty DTG_Inserted;
    private final StringProperty DTG_Removed;




    //defualt contructor
    public Diagnosis(String infection, String diagnosis, String site, String provider, String circumstance,
                     String catheter, String follow, String location, String inserted, String removed){

        this.Infection_Type = new SimpleStringProperty(infection);
        this.Diagnosis_DTG = new SimpleStringProperty(diagnosis);
        this.Insertion_Site = new SimpleStringProperty(site);
        this.Inserting_Provider = new SimpleStringProperty(provider);
        this.Insertion_Circumstance = new SimpleStringProperty(circumstance);
        this.Catheter_Type = new SimpleStringProperty(catheter);
        this.Procedure_List_Follow = new SimpleStringProperty(follow);
        this.Patient_Location = new SimpleStringProperty(location);
        this.DTG_Inserted = new SimpleStringProperty(inserted);
        this.DTG_Removed = new SimpleStringProperty(removed);

    }


    //Getters
    public String getInfection_Type(){return  this.Infection_Type.get();}

    public String getDiagnosis_DTG(){return  Diagnosis_DTG.get();}

    public String getInsertion_Site(){return this.Insertion_Site.get();}

    public String getInserting_Provider(){return  this.Inserting_Provider.get();}

    public String getInsertion_Circumstance(){return  this.Insertion_Circumstance.get();}

    public String getCatheter_Type(){return  this.Catheter_Type.get();}

    public String getProcedure_List_Follow(){return  this.Procedure_List_Follow.get();}

    public String getPatient_Location(){return  this.Patient_Location.get();}

    public String getDTG_Inserted(){return  this.DTG_Inserted.get();}

    public String getDTG_Removed(){return  this.DTG_Removed.get();}





    //Setters


    public void setInfection_Type(String infection_type){ Infection_Type.setValue(infection_type);}

    public void setDiagnosis_DTG(String diagnosis_dtg){ Diagnosis_DTG.setValue(diagnosis_dtg);}

    public void setInsertion_Site(String insertion_site){ Insertion_Site.setValue(insertion_site);}

    public void setInserting_Provider(String inserting_provider){ Inserting_Provider.setValue(inserting_provider);}

    public void setInsertion_Circumstance(String insertion_circumstance){ Insertion_Circumstance.setValue(insertion_circumstance);}

    public void setCatheter_Type(String catheter_type){ Catheter_Type.setValue(catheter_type);}

    public void setProcedure_List_Follow(String procedure_list_follow  ){ Procedure_List_Follow.setValue(procedure_list_follow);}

    public void setPatient_Location(String patient_location){ Patient_Location.setValue(patient_location);}

    public void setDTG_Inserted(String dtg_inserted){ DTG_Inserted.setValue(dtg_inserted);}

    public void setDTG_Removed(String dtg_removed){ DTG_Removed.setValue(dtg_removed);}





    //Proptery Values




    public StringProperty InfectionPropert() {
        return Infection_Type;
    }

    public StringProperty Diagnosis() {
        return Diagnosis_DTG;
    }

    public StringProperty InsertionProperty() {
        return Insertion_Site;
    }

    public StringProperty InsertingProperty() {
        return Inserting_Provider;
    }

    public StringProperty CircumstanceProperty() {
        return Insertion_Circumstance;
    }

    public StringProperty CatheterPropert() {
        return Catheter_Type;
    }

    public StringProperty ProcedureListProperty() {
        return Procedure_List_Follow;
    }

    public StringProperty PatientLocationProperty() {
        return Patient_Location;
    }

    public StringProperty InsertedProperty() {
        return DTG_Inserted;
    }

    public StringProperty RemovedProperty() {
        return DTG_Removed;
    }









}
