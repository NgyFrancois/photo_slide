package TP4;

import parser.Writer;

import java.io.*;
import java.util.*;

public class Main {

    public static LinkedList<Picture> all_pictures = new LinkedList<>(); // structure discutable
    public static LinkedList<Slide> verticals = new LinkedList<>();
    public static LinkedList<Slide> horizontals = new LinkedList<>();
    public static HashSet<Picture> taken = new HashSet<>();
    public static LinkedList<Slide> res = new LinkedList<>();
    public static LinkedList<Slide> all_slides = new LinkedList<>();
    public static LinkedList<Picture> verticalsPics = new LinkedList<>();
    public static Comparator<Slide> comp = new Comparator<Slide>() {
        @Override
        public int compare(Slide o1, Slide o2) {
            return Integer.compare(o1.compactTags().size(), o2.compactTags().size());
        }
    };

    public static void main(String[] args) throws IOException {
        if(args==null || args.length == 0){
            System.err.println("Le 1er argument doit être un chemin vers un fichier test");
            System.exit(1);
        }

        // PARSING
        Scanner sc = new Scanner(new File(args[0]));
        int nb_photos = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < nb_photos; i++) {
            String[] line = sc.nextLine().split(" ");
            Picture p = new Picture((line[0].equals("H")), Integer.parseInt(line[1]));
            for (int j = 0; j < Integer.parseInt(line[1]); j++)
                p.addTag(line[2+j]);
            all_pictures.add(p);
        }

        if (nb_photos > 10000) naive_method();
        else advance_method();

        // FICHIER SOLUTION
        String[] tmp = args[0].split("/");
        // *Execution depuis peff23-gr3/TP4*
        Writer writer = new Writer("Solutions/"+ tmp[tmp.length-1].replace(".txt",".sol"));
        writer.print(res.size() +"\n");
        for (Slide s: res) {
            if(s.pictures.size()==1){
                writer.print(s.pictures.getFirst().id+"\n");
            }else{
                writer.print(s.pictures.getFirst().id + " " + s.pictures.get(1).id+"\n");
            }
        }
        writer.close();
    }

    public static void naive_method(){
        // Creating slide of vertical with max difference between them (because don't matter in score)
        for (Picture p : all_pictures) {
            if (p.isHorizontal) {
                Slide s = new Slide(p);
                horizontals.add(s);
            }else{
                verticalsPics.add(p);
            }
        }

        for (int i = 0; i < verticalsPics.size()-1; i=i+2) {
            try {
                verticals.add(new Slide(verticalsPics.get(i), verticalsPics.get(i + 1)));
            }catch (Exception e){
                break; // impairs
            }
        }
        all_slides.addAll(horizontals);
        all_slides.addAll(verticals);
        all_slides.sort(comp);
        res = all_slides;
    }

    public static void advance_method(){
        // Creating slide of vertical with max difference between them (because don't matter in score)
        System.out.print("Vertical slide creation...");

        for (Picture p : all_pictures) {
            if(taken.contains(p)) continue;
            if(!p.isHorizontal){
                Picture tmp = null;
                int n = -1;
                for(Picture p2 : all_pictures){
                    if(p.id != p2.id && !p2.isHorizontal && !taken.contains(p2)
                    && Picture.score_score_correspondance(p, p2) > n){
                        tmp = p2;
                        n = Picture.score_score_correspondance(p, p2);
                    }
                }
                if (tmp != null) {
                    verticals.add(new Slide(p, tmp));
                    taken.add(p);
                    taken.add(tmp);
                }
            }else {
                horizontals.add(new Slide(p));
            }
        }
        System.out.println("\tfinished");

        all_slides.addAll(horizontals);
        all_slides.addAll(verticals);
        all_slides.sort(comp);

        // MATCHES BETWEEN SLIDES

        System.out.print("Matching slides...");
        Slide first = horizontals.isEmpty() ? all_slides.getFirst() : horizontals.getFirst();
        res.add(first);
        all_slides.remove(first);
        matching_slides(first);
        System.out.println("\tfinished");
    }

    public static void matching_slides(Slide current){
        if (all_slides.isEmpty())
            return;

        Slide max = null;
        int max_score = -1;

        for (Slide s2 : all_slides) {
            if (max_score < current.compare(s2)) {
                max_score = current.compare(s2);
                max = s2;
            }
        }

        if(max != null){
            res.add(max);
            all_slides.remove(max);
            matching_slides(max);
        }
    }

}
