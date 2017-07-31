package com.britebill.interview.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileDataReader {

    private static final Log log = LogFactory.getLog( FileDataReader.class );

    public List<String> readData () {
        List<String> data = new ArrayList<>();
        try {
            String mainPath = Paths.get( ClassLoader.getSystemResource(".").toURI() ).toString();
            data = Files.lines( Paths.get( mainPath , "/data/data.example" ) ).collect( Collectors.toList() );
        } catch ( IOException | URISyntaxException e ) {
            log.error("Problem reading data file", e );
        }
        return data;
    }
}
