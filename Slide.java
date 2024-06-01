package TP4;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

public class Slide {
    public LinkedList<Picture> pictures = new LinkedList<>();

    public Slide(Picture ... pics) {
        pictures.addAll(Arrays.asList(pics));
    }

    public Slide(LinkedList<Picture> pictures) {
        this.pictures.addAll(pictures);
    }

    /* On mix les hashsets de toutes les photos dans le slide */
    public HashSet<String> compactTags() {
        HashSet<String> h = new HashSet<>();
        for(Picture p : pictures)
            h.addAll(p.tags);

        return h;
    }

    /* On fait le minimum ente les tags en commun, le nombre de tags dans s1 (pas dans s2)
       et le nombre de tags dans s2 (pas dans s1) */
    public int compare(Slide s2) {
        HashSet<String> s1= this.compactTags();
        s1.removeAll(s2.compactTags());
        int commun = this.compactTags().size()-s1.size();
        int s2_NOT_s1 = s2.compactTags().size()-commun;
        return Math.min(commun, Math.min(s1.size(),s2_NOT_s1 ));
    }

    @Override
    public String toString() {
        return "Slide{pictures=" + pictures + "}";
    }

}
