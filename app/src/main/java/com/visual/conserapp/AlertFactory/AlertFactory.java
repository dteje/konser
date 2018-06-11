package com.visual.conserapp.AlertFactory;

public class AlertFactory {

    public AlertParent generateAlert(AlertEnum alertType){

        switch(alertType){

            case NULL_INGREDIENT:
                return new AlertNullingredient();
            case REPETITION_SANDWICH:
                return new AlertRepetitionSandwich();
            case EMPTY_SANDWICH:
                return new AlertEmptySandwich();
            default:
                return new AlertNullingredient();
        }

    }
}
