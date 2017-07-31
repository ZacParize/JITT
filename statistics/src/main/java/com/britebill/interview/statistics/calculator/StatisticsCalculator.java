package com.britebill.interview.statistics.calculator;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticsCalculator {

    private List<String> getTotalWords( List<String> data ) {
        if ( Objects.isNull( data ) ) return null;
        final List<String> result = new LinkedList<>();
        data.stream().filter( string -> !Objects.isNull( string ) && string.length() > 0 ).forEach( string -> {
            result.addAll( Arrays.asList( string.toLowerCase().split("\\s+") ) );
        } );
        return result;
    }

    public Long getTotalNumberOfWords( List<String> data ) {
        List<String> totalWords = this.getTotalWords( data );
        return Objects.isNull( totalWords ) ? 0 : (long) totalWords.size();
    }

    public Long getTotalNumberOfUniqueWords( List<String> data ) {
        List<String> totalWords = this.getTotalWords( data );
        return Objects.isNull( totalWords ) ? 0 : (long) new HashSet<>( totalWords ).size() ;
    }

    public Long getAverageCharactersPerWord( List<String> data ) {
        List<String> totalWords = this.getTotalWords( data );
        long result = 0;
        if ( !Objects.isNull( totalWords ) && totalWords.size() != 0 ) {
            result = totalWords.stream().map( String::toString ).collect( Collectors.joining () ).length() / totalWords.size();
        }
        return result;
    }

    public String getMostRepeatedWord( List<String> data ) {
        List<String> totalWords = this.getTotalWords( data );
        String result = null;
        if ( !Objects.isNull( totalWords ) && totalWords.size() != 0 ) {
            Long wordCount = null;
            HashMap<String, Long> tempMap = new HashMap<>();
            for ( String string : totalWords ) {
                tempMap.put( string, ( wordCount = tempMap.get( string ) ) == null ? 1L : ++wordCount );
            }
            SortedSet<Map.Entry<String, Long>> sortedSet = new TreeSet<>( ( e1, e2 ) -> e2.getValue().compareTo( e1.getValue() ) );
            sortedSet.addAll( tempMap.entrySet() );
            result = sortedSet.first().getKey();
        }
        return result;
    }

}
