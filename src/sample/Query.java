package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Query {

    public static class InfectionRatio    {
        private final StringProperty INFECTION_RATIO;

        public InfectionRatio(String infectionRatio)
        {
            this.INFECTION_RATIO =  new SimpleStringProperty(infectionRatio);
        }

        //Getters
        public String getInfectionRatio(){return  INFECTION_RATIO.get();}

        //Setters

        public void setInfectionRatio(String infectRatio){ INFECTION_RATIO.setValue(infectRatio);}

        public StringProperty INFECTION_RATIOProperty() {
            return INFECTION_RATIO;
        }

    }
    public static class ChecklistFollowed {
        private final StringProperty Checklist_Followed;

        public ChecklistFollowed(String checklistFollow) {
            this.Checklist_Followed = new SimpleStringProperty(checklistFollow);
        }
        //Getters
        public String getChecklistFollowed() {
            return Checklist_Followed.get();
        }
        //Setters
        public void setChecklistFollowed(String ChecklistFollow) {
            Checklist_Followed.setValue(ChecklistFollow);
        }
        public StringProperty Checklist_FollowedProperty() {
            return Checklist_Followed;
        }
    }

    public static class DoclistCL {
        private final StringProperty InsPro;
        private final StringProperty EnId;
        private final StringProperty InfType;

        public DoclistCL(String InsProvider, String Encounter_ID, String Infection_Type) {
            this.InsPro = new SimpleStringProperty(InsProvider);
            this.EnId = new SimpleStringProperty(Encounter_ID);
            this.InfType = new SimpleStringProperty(Infection_Type);
        }

        //Getters
        public String getInsPro() {
            return InsPro.get();
        }
        public String getEnId() {
            return EnId.get();
        }
        public String getInfType() {
            return InfType.get();
        }

        //Setters
        public void setInsPro(String InsProvider) {
            InsPro.setValue(InsProvider);
        }
        public void setEnId(String Encounter_ID) { EnId.setValue(Encounter_ID); }
        public void setInfType(String Infection_Type) {
            InfType.setValue(Infection_Type);
        }

        public StringProperty DoclistInsPro_CLProperty() {
            return InsPro;
        }
        public StringProperty DoclistEnId_CLProperty() {
            return EnId;
        }
        public StringProperty DoclistInfType_CLProperty() {
            return InfType;
        }
    }


    public static class PatientRiskFactor    {
        private final StringProperty PR_Factor;

        public PatientRiskFactor(String prFactor)
        {
            this.PR_Factor =  new SimpleStringProperty(prFactor);
        }

        //Getters
        public String getPatientRiskFactor(){return  PR_Factor.get();}

        //Setters

        public void setPatientRiskFactor(String prFactor){ PR_Factor.setValue(prFactor);}

        public StringProperty PR_FactorProperty() {
            return PR_Factor;
        }

    }


    public static class CatheterList {
        private final StringProperty InsPro;
        private final StringProperty CatType;
        private final StringProperty HICNO;

        public CatheterList(String hNo, String cType, String insPro) {
            this.HICNO = new SimpleStringProperty(hNo);
            this.CatType = new SimpleStringProperty(cType);
            this.InsPro = new SimpleStringProperty(insPro);
        }

        //Getters
        public String getHICNO() {
            return HICNO.get();
        }
        public String getCatType() {
            return CatType.get();
        }
        public String getInsPro() {
            return InsPro.get();
        }

        //Setters
        public void setHICNO(String hNo) {
            HICNO.setValue(hNo);
        }
        public void setCatType(String cType) { CatType.setValue(cType); }
        public void setInsPro(String insPro) {
            InsPro.setValue(insPro);
        }

        public StringProperty CatheterListHICNOProperty() {
            return HICNO;
        }
        public StringProperty CatheterListCatTypeProperty() {
            return CatType;
        }
        public StringProperty CatheterListInsProProperty() {
            return InsPro;
        }
    }

    public static class CLABSIList {
        private final StringProperty InfType;
        private final StringProperty EncID;
        private final StringProperty HICNO;

        public CLABSIList(String iType, String eID, String hNo) {
            this.InfType = new SimpleStringProperty(iType);
            this.EncID = new SimpleStringProperty(eID);
            this.HICNO = new SimpleStringProperty(hNo);
        }

        //Getters
        public String getInfType() {
            return InfType.get();
        }
        public String getEncID() {
            return EncID.get();
        }
        public String getHICNO() {
            return HICNO.get();
        }

        //Setters
        public void setInfType(String iType) {
            InfType.setValue(iType);
        }
        public void setEncID(String eID) { EncID.setValue(eID); }
        public void setHICNO(String hNo) {
            HICNO.setValue(hNo);
        }

        public StringProperty CLABSIListInfTypeProperty() {
            return InfType;
        }
        public StringProperty CLABSIListEncIDProperty() {
            return EncID;
        }
        public StringProperty CLABSIListHICNOProperty() {
            return HICNO;
        }
    }


    public static class PatientLocation1 {
        private final StringProperty PL;

        public PatientLocation1(String pLocation) {
            this.PL = new SimpleStringProperty(pLocation);
        }
        //Getters
        public String getPatientLocation() {
            return PL.get();
        }
        //Setters
        public void setPatientLocation(String pLocation) {
            PL.setValue(pLocation);
        }
        public StringProperty Patient_LocationProperty() {
            return PL;
        }
    }


    public static class PatientLocation {
        private final StringProperty Patient_Location;

        public PatientLocation(String pLocation) {
            this.Patient_Location = new SimpleStringProperty(pLocation);
        }
        //Getters
        public String getPatient_Location() {
            return Patient_Location.get();
        }
        //Setters
        public void setPatient_Location(String pLocation) {
            Patient_Location.setValue(pLocation);
        }

        public StringProperty Patient_LocationProperty() {
            return Patient_Location;
        }
    }

}
