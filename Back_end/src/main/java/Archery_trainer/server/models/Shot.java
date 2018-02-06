package Archery_trainer.server.models;

import java.sql.Date;
import java.sql.Time;

public class Shot {
    private int id;
    private Date date;
    private Time time;
    private int score;
    private int athleteNo;

    //Name of the corresponding database table
    private static final String TABLENAME = "Shot";

    public Shot(int _id, Date _date, Time _time, int _score, int _athleteNo) {
        id = _id;
        date = _date;
        time = _time;
        score = _score;
        athleteNo = _athleteNo;
    }

    public Shot(Date _date, Time _time, int _score, int _athleteNo) {
        id = -1;
        date = _date;
        time = _time;
        score = _score;
        athleteNo = _athleteNo;
    }

    public Shot(Date _date, Time _time, int _athleteNo) {
        id = -1;
        date = _date;
        time = _time;
        score = 0;
        athleteNo = _athleteNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAthleteNo() {
        return athleteNo;
    }

    public void setAthleteNo(int athleteNo) {
        athleteNo = athleteNo;
    }

    public static String getTableName() {
        return TABLENAME;
    }

    @Override
    public String toString() {
        return "Shot " + id + ", date: " + date + ", time: " + time
                + ", score: " + score + ", athleteId: " + athleteNo;
    }
}
