package textproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Holgersson {

    public static final String[] REGIONS = {"blekinge", "bohuslän", "dalarna", "dalsland", "gotland", "gästrikland",
            "halland", "hälsingland", "härjedalen", "jämtland", "lappland", "medelpad", "närke", "skåne", "småland",
            "södermanland", "uppland", "värmland", "västerbotten", "västergötland", "västmanland", "ångermanland",
            "öland", "östergötland"};

    public static void main(String[] args) throws FileNotFoundException {

        var ps = new ArrayList<TextProcessor>();
        ps.add(new SingleWordCounter("nils"));
        ps.add(new SingleWordCounter("norge"));
        ps.add(new MultiWordCounter(REGIONS));

        Scanner s = new Scanner(new File("lab2/res/nilsholg.txt"));
        s.findWithinHorizon("\uFEFF", 1);
        s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\")+"); // se handledning

        var u = new Scanner(new File("lab2/res/undantagsord.txt"));
        final var forbiddenWords =
                u.findAll("(\\S+)\\s").map(r -> r.group(1)).collect(Collectors.toSet());

        ps.add(new GeneralWordCounter(forbiddenWords));

        while (s.hasNext()) {
            String word = s.next().toLowerCase();

            for (var p : ps) {
                p.process(word);
            }
        }

        s.close();
        for (var p : ps) {
            long t0 = System.nanoTime();
            p.report();
            long t1 = System.nanoTime();
            System.out.println("tid: " + (t1 - t0) / 1000000.0 + " ms");
        }
    }
}