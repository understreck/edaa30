package textproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Holgersson {

    public static final String[] REGIONS = {"blekinge", "bohuslän", "dalarna", "dalsland", "gotland", "gästrikland",
            "halland", "hälsingland", "härjedalen", "jämtland", "lappland", "medelpad", "närke", "skåne", "småland",
            "södermanland", "uppland", "värmland", "västerbotten", "västergötland", "västmanland", "ångermanland",
            "öland", "östergötland"};

    public static void main(String[] args) throws FileNotFoundException {

        var ps = new ArrayList<SingleWordCounter>();
        ps.add(new SingleWordCounter("nils"));
        ps.add(new SingleWordCounter("norge"));

        Scanner s = new Scanner(new File("lab2/res/nilsholg.txt"));
        s.findWithinHorizon("\uFEFF", 1);
        s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning

        while (s.hasNext()) {
            String word = s.next().toLowerCase();

            for(var p : ps) {
                p.process(word);
            }
        }

        s.close();

        for(var p : ps) {
            p.report();
        }
    }
}