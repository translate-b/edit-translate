package de.fungate.translate.edit;

import com.google.common.base.Function;
import de.fungate.translate.core.models.SourceLanguage;
import de.fungate.translate.core.models.Translation;
import de.fungate.translate.core.services.Curler;
import de.fungate.translate.core.services.Translator;
import de.fungate.translate.core.services.curlers.ApacheHTTPCurler;
import de.fungate.translate.core.services.translators.*;
import dnl.utils.text.table.TextTable;

import java.util.Scanner;

import static com.google.common.collect.Iterables.toArray;
import static com.google.common.collect.Iterables.transform;

public class Program {

    private static final Curler curler = new ApacheHTTPCurler();
    private static final Translator dictcc = new DictccTranslator(curler);
    private static final Translator pons = new PonsTranslator(curler);
    private static final Translator google = new GoogleTranslator(curler);
    private static final Translator leo = new LeoTranslator(curler);
    private static final Translator woerterbuch = new WoerterbuchTranslator(curler);

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (true) {
            System.out.println("Enter term to translate or nothing to quit:");
            String term = cin.nextLine();
            if (term.isEmpty()) {
                break;
            }

            System.out.println("German->English:");
            printTranslations(term, SourceLanguage.GERMAN);
            System.out.println();

            System.out.println("English->German");
            printTranslations(term, SourceLanguage.ENGLISH);
            System.out.println();
        }
    }

    private static void printTranslations(String term, SourceLanguage source) {
        tableForTranslations(dictcc.translate(term, source)).printTable();
    }

    public static TextTable tableForTranslations(Iterable<Translation> translations) {
        final String[] COLUMN_NAMES = {"English", "German"};
        Object[][] table = toArray(transform(translations, new Function<Translation, Object[]>() {
            public Object[] apply(Translation input) {
                return new Object[]{
                        input.getEnglish(),
                        input.getGerman()
                };
            }
        }), Object[].class);
        return new TextTable(COLUMN_NAMES, table);
    }
}