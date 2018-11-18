package magnit;

import magnit.models.Entries;
import magnit.models.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import java.io.File;
import java.util.List;


public class StoreXML {
    private static final Logger LOG = LoggerFactory.getLogger(StoreXML.class);
    private File target;
    public StoreXML(File target) {
        this.target = target;
    }

    public void save(List<Entry> list) {
        try {
            JAXBContext context = JAXBContext.newInstance(Entries.class);
            Marshaller jaxbMarshaller = context.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(new Entries(list), target);
        } catch (JAXBException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
