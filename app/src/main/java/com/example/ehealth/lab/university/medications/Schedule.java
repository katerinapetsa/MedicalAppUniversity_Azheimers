package com.example.ehealth.lab.university.medications;

/**
 * Create the type Schedule.
 *
 * @author Stavroula Kousparou
 */

public class Schedule {

    int id, duration, days, timesInDay;
    String startDate;
    String time1, time2, time3, time4, time5, time6, time7, time8, time9, time10, time11, time12;

    public Schedule(int id, String startDate, int duration, int days, int timesInDay, String time1,
                    String time2, String time3, String time4, String time5, String time6, String time7,
                    String time8, String time9, String time10, String time11, String time12){

        this.id = id;
        this.startDate = startDate;
        this.duration = duration;
        this.days = days;
        this.timesInDay = timesInDay;

        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
        this.time4 = time4;
        this.time5 = time5;
        this.time6 = time6;
        this.time7 = time7;
        this.time8 = time8;
        this.time9 = time9;
        this.time10 = time10;
        this.time11 = time11;
        this.time12 = time12;

    }


    public Schedule(){}

    // get methods
    public int getId() {
        return id;
    }

    public String getStartDate() {
        return startDate;
    }

    public int getDuration() {
        return duration;
    }

    public int getDays() {
        return days;
    }

    public int getTimesInDay() {
        return timesInDay;
    }

    public String getTime1() {
        return time1;
    }

    public String getTime2() {
        return time2;
    }

    public String getTime3() {
        return time3;
    }

    public String getTime4() {
        return time4;
    }

    public String getTime5() {
        return time5;
    }

    public String getTime6() {
        return time6;
    }

    public String getTime7() {
        return time7;
    }

    public String getTime8() {
        return time8;
    }

    public String getTime9() {
        return time9;
    }

    public String getTime10() {
        return time10;
    }

    public String getTime11() {
        return time11;
    }

    public String getTime12() {
        return time12;
    }
}
