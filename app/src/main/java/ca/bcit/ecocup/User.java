package ca.bcit.ecocup;

public class User {
    private Long points;
    private History historys;

    public Long getPoints(){
        return this.points;
    }
    public void setPoints(Long points){
        this.points = points;
    }

    public void setHistorys(History history){
        this.historys = history;
    }
    public History getHistorys(){
        return this.historys;
    }
}
