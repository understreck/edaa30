package textproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BookReaderApplication {
    public static final String[] REGIONS = {"blekinge", "bohuslän", "dalarna", "dalsland", "gotland", "gästrikland",
            "halland", "hälsingland", "härjedalen", "jämtland", "lappland", "medelpad", "närke", "skåne", "småland",
            "södermanland", "uppland", "värmland", "västerbotten", "västergötland", "västmanland", "ångermanland",
            "öland", "östergötland"};

    public static void main(String[] args) throws FileNotFoundException {
        Scanner nils = new Scanner(new File("lab3/nilsholg.txt"));
        nils.findWithinHorizon("\uFEFF", 1);
        nils.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\")+"); // se handledning

        var f = new Scanner(new File("lab3/undantagsord.txt"));
        final var forbiddenWords =
                f.findAll("(\\S+)\\s").map(r -> r.group(1)).collect(Collectors.toSet());

        var nilsCounter = new GeneralWordCounter(forbiddenWords);
        nils.forEachRemaining(nilsCounter::process);

        var controller = new BookReaderController(nilsCounter);
    }
}
