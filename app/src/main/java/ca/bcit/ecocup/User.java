package ca.bcit.ecocup;

public class User {
    private Long points;
    private String history;

    public Long getPoints(){
        return this.points;
    }
    public void setPoints(Long points){
        this.points = points;
    }

    public void setHistory(String history){
        this.history = history;
    }
    public String getHistory(){
        return this.history;
    }
}
