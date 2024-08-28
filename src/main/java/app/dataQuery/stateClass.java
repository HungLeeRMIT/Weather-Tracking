package app.dataQuery;

public class stateClass {
    String state_id;
    String stateName;

    public stateClass(String state_id, String stateName){
        this.state_id = state_id;
        this.stateName = stateName;
    }

    public String getState_id(){
        return state_id;
    }

    public String getStateName(){
        return stateName;
    }
}
