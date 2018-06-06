package com.visual.conserapp.AlertFactory;

public class AlertFactory {

    public AlertParent generateAlert(String alertType){
        AlertParent alert = null;
        if(alertType.equals("nullIngredient")) {
            alert = new AlertNullingredient();
        } else if (alertType.equals("RepetitionSandwich")){
            alert = new AlertRepetitionSandwich();
        } else if (alertType.equals("EmptySandwich")){
            alert = new AlertEmptySandwich();
        }

        return alert;
    }
}
