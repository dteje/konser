package com.visual.conserapp.AlertFactory;

public class AlertFactory {

    public AlertParent generateAlert(String alertType){
        AlertParent alert = null;
        if(alertType.equals("nullIngredient")) {
            return new  AlertNullingredient();
        } else if (alertType.equals("RepetitionSandwich")){
            return new AlertRepetitionSandwich();
        } else if (alertType.equals("EmptySandwich")){
            return new AlertEmptySandwich();
        }

        return alert;
    }
}


