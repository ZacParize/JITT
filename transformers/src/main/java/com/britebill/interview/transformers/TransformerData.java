package com.britebill.interview.transformers;

import java.util.*;

public class TransformerData {
    public List<String> transformData( List<String> src ) {
        if ( Objects.isNull( src ) ) return null;
        final Set<String> result = new TreeSet<>( Comparator.comparingInt( String::length ) );
        src.stream().filter( string -> !Objects.isNull( string ) && string.length() > 0 ).forEach( string -> {
            result.addAll( Arrays.asList( string.toUpperCase().split("\\s+") ) );
        } );
        return new ArrayList<>( result );
    }
}
