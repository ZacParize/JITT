package com.britebill.interview.statistics;

import com.britebill.interview.statistics.beans.Statistics;
import com.britebill.interview.statistics.calculator.StatisticsCalculator;
import com.britebill.interview.statistics.writers.JsonStatisticsWriter;
import com.britebill.interview.statistics.writers.XmlStatisticsWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.xml.bind.JAXBContext;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RunWith( DataProviderRunner.class )
public class StatisticsTest {

    @Test
    public void testXmlGeneration() throws Exception {
        String standart = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Statistics><TotalNumberOfWords>408</TotalNumberOfWords><TotalNumberOfUniqueWords>226</TotalNumberOfUniqueWords><AverageCharactersPerWord>6</AverageCharactersPerWord><MostRepeatedWord>de</MostRepeatedWord></Statistics>";
        String tmpFile = this.getClass().getClassLoader().getResource("" ).getPath() + "tmp.xml";
        new XmlStatisticsWriter().write( ( Statistics ) JAXBContext.newInstance( Statistics.class ).createUnmarshaller().unmarshal( new StringReader( standart ) ), new File( tmpFile ) );
        String verifiable = Files.lines( Paths.get( tmpFile ) ).collect( Collectors.joining() );
        new File( tmpFile ).delete();
        assert( verifiable.equals( standart ) );
    }

    @Test
    public void testJsonGeneration() throws IOException {
        String standart = "{\"totalNumberOfWords\":408,\"totalNumberOfUniqueWords\":226,\"averageCharactersPerWord\":6,\"mostRepeatedWord\":\"de\"}";
        String tmpFile = this.getClass().getClassLoader().getResource("").getPath() + "tmp.json";
        new JsonStatisticsWriter().write( new ObjectMapper().readValue( standart, Statistics.class ), new File( tmpFile) );
        String verifiable = Files.lines( Paths.get( tmpFile ) ).collect( Collectors.joining() );
        new File( tmpFile ).delete();
        assert( verifiable.equals( standart ) );
    }

    @DataProvider
    public static Object[][] testTotalNumberOfWordsData() {
        return new Object[][] {
                { Arrays.asList( "a\n", "\n" ), 1 },
                { Arrays.asList( "a\n", null ), 1 },
                { Arrays.asList( "a\n", "" ), 1 },
                { Arrays.asList( "aadd add add\n", "\n" ), 3 },
                { Arrays.asList( "aadd  add  add\n", "aa dd asasdsd     sadsdas\n" ), 7 },
                { Arrays.asList( "aadd  add  add\n", null, "aa dd asasdsd     sadsdas\n" ), 7 },
                { Arrays.asList( "aadd  add  add\n", "\n", "aa dd asasdsd     sadsdas\n" ), 7 },
                { Arrays.asList( "aadd  add  add\n", "", "aa dd asasdsd     sadsdas\n" ), 7 },
                { Arrays.asList( "", "\n", null ), 0 }
        };
    }

    @Test
    @UseDataProvider( "testTotalNumberOfWordsData" )
    public void testTotalNumberOfWords( List<String> data, int standart ) {
        assert( new StatisticsCalculator().getTotalNumberOfWords( data ) == standart );
    }

    @DataProvider
    public static Object[][] testTotalNumberOfUniqueWordsData() {
        return new Object[][] {
                { Arrays.asList( "a\n", "\n" ), 1 },
                { Arrays.asList( "a\n", null ), 1 },
                { Arrays.asList( "a\n", "" ), 1 },
                { Arrays.asList( "Add aDd add\n", "aDD   add\n", "\n" ), 1 },
                { Arrays.asList( "AAdd  add  add\n", "aa dd aSasdsd     sadsdas\n" ), 6 },
                { Arrays.asList( "aDd  dd  add\n", null, "aAd dd dd     dd\n" ), 3 },
                { Arrays.asList( "aDd  dd  add\n", "\n", "AAd dd dd     dd\n" ), 3 },
                { Arrays.asList( "adD  Dd  aDD\n", "", "AAd dd dd     dd\n" ), 3 },
                { Arrays.asList( "", "\n", null ), 0 }
        };
    }

    @Test
    @UseDataProvider( "testTotalNumberOfUniqueWordsData" )
    public void testTotalNumberOfUniqueWords( List<String> data, int standart ) {
        assert( new StatisticsCalculator().getTotalNumberOfUniqueWords( data ) == standart );
    }

    @DataProvider
    public static Object[][] testAverageCharactersPerWordData() {
        return new Object[][] {
                { Arrays.asList( "a\n", "\n" ), 1L },
                { Arrays.asList( "a\n", null ), 1L },
                { Arrays.asList( "a\n", "" ), 1L },
                { Arrays.asList( "add add add\n", "add   add\n", "\n" ), 3L },
                { Arrays.asList( "aa  add  add\n", "asadsdas\n" ), 4L },
                { Arrays.asList( "aa  add  add\n", "assd", "asadsdas\n" ), 4L },
                { Arrays.asList( "aa  add  add\n", "\n", "", null, "assd", "asadsdas\n" ), 4L },
                { Arrays.asList( "aa  add  add", "\n", "", null, "assd", "asadsdas" ), 4L },
                { Arrays.asList( "", "\n", null ), 0L }
        };
    }

    @Test
    @UseDataProvider( "testAverageCharactersPerWordData" )
    public void testAverageCharactersPerWord( List<String> data, long standart ) {
        assert( new StatisticsCalculator().getAverageCharactersPerWord( data ) == standart );
    }

    @DataProvider
    public static Object[][] testMostRepeatedWordData() {
        return new Object[][] {
                { Arrays.asList( "a\n", "\n" ), "a" },
                { Arrays.asList( "a\n", null ), "a" },
                { Arrays.asList( "a\n", "" ), "a" },
                { Arrays.asList( "Add aDd add\n", "aDD   add\n", "\n" ), "add" },
                { Arrays.asList( "AAdd  add  add\n", "aa dd aSasdsd     sadsdas\n" ), "add" },
                { Arrays.asList( "aDd  DD  add\n", null, "aAd Dd dD     dd\n" ), "dd" },
                { Arrays.asList( "aDd  DD  add\n", "\n", "AAd Dd dD     dd\n" ), "dd" },
                { Arrays.asList( "adD  Dd  aDD\n", "", "AAd Dd dD     dd\n" ), "dd" },
                { Arrays.asList( "", "\n", null ), null }
        };
    }

    @Test
    @UseDataProvider( "testMostRepeatedWordData" )
    public void testMostRepeatedWord( List<String> data, String standart ) {
        String word = new StatisticsCalculator().getMostRepeatedWord( data );
        assert( ( Objects.isNull( word ) && Objects.isNull( standart ) ) || word.equals( standart ) );
    }

}



