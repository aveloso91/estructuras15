package material;

import java.util.ArrayList;

/**
 * Created by Alejandro on 15/6/16.
 */
public class Movie {
    private String title;
    private int year;
    private java.util.List<Float> score = new ArrayList<>();
    private java.util.List<String> type;

    public Movie(String title, int year, java.util.List<Float> score, java.util.List<String> type) {
        this.title = title;
        this.year = year;
        this.score = score;
        this.type = type;
    }

    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Float getScore() {
        Float result  = Float.valueOf(0);
        for(Float s : this.score){
            result = result + s;
        }
        result = result / this.score.size();
        return result;
    }

    public void addScore(Float score){
        this.score.add(score);
    }

    public java.util.List<String> getType() {
        return type;
    }

    public void setType(java.util.List<String> type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", score=" + getScore() +
                ", type=" + type +
                "}\n";
    }
}
