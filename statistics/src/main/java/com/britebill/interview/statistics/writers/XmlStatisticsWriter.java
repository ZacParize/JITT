package com.britebill.interview.statistics.writers;

import com.britebill.interview.statistics.beans.Statistics;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.Objects;


public class XmlStatisticsWriter implements StatisticsWriter {

    private static final Log log = LogFactory.getLog( XmlStatisticsWriter.class );

    public void write(Statistics statistics, File file) {
        if ( Objects.isNull( statistics ) || Objects.isNull( file ) ) return;
        try {
            JAXBContext.newInstance( Statistics.class ).createMarshaller().marshal( statistics, file );
        } catch ( JAXBException e ) {
            log.error("Problem writing xml file", e );
        }
    }
}
