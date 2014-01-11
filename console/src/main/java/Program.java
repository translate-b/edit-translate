import de.fungate.translate.core.models.SourceLanguage;
import de.fungate.translate.core.services.curlers.ApacheHTTPCurler;
import de.fungate.translate.core.services.translators.DictccTranslator;

public class Program {
    public static void main(String[] args) {
        System.out.println(new DictccTranslator(new ApacheHTTPCurler()).translate("wand", SourceLanguage.GERMAN));
    }
}