package model;

import java.util.Comparator;

public class ComparatorCoefficientAndPoints implements Comparator<Team> {
    @Override
    public int compare(Team o1, Team o2) {
        if(o1.getRatingUEFA() > o2.getRatingUEFA()) return 1;
        else if(o1.getRatingUEFA() < o2.getRatingUEFA()) return -1;
        else if (o1.getPoints() > o2.getPoints()) return 1;
        else if (o1.getPoints() < o2.getPoints()) return -1;
        else return Integer.compare(o1.getScore(), o2.getScore());
    }
}
