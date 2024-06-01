package TP4;

import java.util.HashSet;

public class Picture {
    public boolean isHorizontal;
    public int nb_tags;
    public HashSet<String> tags; // structure discutable
    public int id;
    public static int acc=0;

    public Picture(boolean isHorizontal, int nb_tags) {
        this.isHorizontal = isHorizontal;
        this.nb_tags = nb_tags;
        tags = new HashSet<>();
        this.id = acc;
        acc ++;
    }

    public void addTag(String tag){
        tags.add(tag);
    }

    @Override
    public String toString() {
        return (isHorizontal ? "H" : "V") + " id="  + id + " nb=" + nb_tags + " " + tags.toString();
    }

    /** Retourne le nbr de tags en commun **/
    public int score_correspondance(Picture p){
        HashSet<String> diff = new HashSet<>(this.tags);
        diff.removeAll(p.tags);
        return diff.size();
    }
    /** Same as score_correspondance **/
    public static int score_score_correspondance(Picture p1, Picture p2){
        return p1.score_correspondance(p2);
    }
}
