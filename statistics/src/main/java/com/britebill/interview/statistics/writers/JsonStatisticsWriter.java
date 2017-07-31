package com.britebill.interview.statistics.writers;

import com.britebill.interview.statistics.beans.Statistics;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class JsonStatisticsWriter implements StatisticsWriter {

    private static final Log log = LogFactory.getLog( XmlStatisticsWriter.class );

    public void write( Statistics statistics, File file ) {
        if ( Objects.isNull( statistics ) || Objects.isNull( file ) ) return;
        try {
            new ObjectMapper().writeValue( file, statistics );
        } catch ( JsonGenerationException e ) {
            log.error("Problem generating json file", e );
        } catch ( JsonMappingException e ) {
            log.error("Problem mapping json file", e );
        } catch (IOException e) {
            log.error("Problem writing json file", e );
        }
    }
}
