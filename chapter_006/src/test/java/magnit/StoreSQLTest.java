package magnit;

import magnit.models.Entry;
import org.junit.Test;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StoreSQLTest {
    private String pathSource   = "src/main/resources/source.xml";
    private String pathDest     = "src/main/resources/dest.xml";
    private String pathScheme   = "src/main/resources/scheme.xsl";
    private final int entries   = 1_000_000;
    @Test
    public void test() throws TransformerException {
        try (StoreSQL sql = new StoreSQL(new Config())) {
            ArrayList<Entry> temp = new ArrayList<>(entries);
            for (int i = 0; i < entries; i++) {
                temp.add(new Entry(i + 1));
            }
            Entry[] expexted = new Entry[this.entries];
            expexted = temp.toArray(expexted);
            sql.generate(this.entries);
            List<Entry> listEntry = sql.getListEntry();
            assertThat(listEntry.toArray(), is(expexted));
            File source = new File(pathSource);
            StoreXML storeXML = new StoreXML(source);
            storeXML.save(listEntry);
            ConvertXSQT xsqt = new ConvertXSQT();
            xsqt.convert(source, new File(pathDest), new File(pathScheme));
            List<Entry> list = new ParserXML().parse(pathDest);
            assertThat(list.toArray(), is(expexted));
            int sum = (1 + entries) * entries / 2;
            int expextedSum = list.stream().mapToInt(Entry::getValue).sum();
            //assertThat(sum, is(expextedSum));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}