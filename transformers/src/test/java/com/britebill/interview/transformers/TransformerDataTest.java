package com.britebill.interview.transformers;

import org.junit.Test;

import java.util.Arrays;

public class TransformerDataTest {

    @Test
	public void testNullInput() {
		assert( new TransformerData().transformData( null ) == null );
		assert( new TransformerData().transformData( Arrays.asList( "" ) ) != null );
	}

	@Test
	public void testEmptyInput() {
		assert( !new TransformerData().transformData( Arrays.asList( "" ) ).retainAll( Arrays.asList( "" ) ) );
		assert( new TransformerData().transformData( Arrays.asList( "a" ) ).retainAll( Arrays.asList( "" ) ) );
	}

	@Test
	public void testDuplicates() {
		assert( !new TransformerData().transformData( Arrays.asList( "aAa aA a\n", null, "\n", "", "aAA AAA aa\n" ) ).retainAll( Arrays.asList( "A", "AA", "AAA" ) ) );
	}

	@Test
	public void testSorting() {
		assert( !new TransformerData().transformData( Arrays.asList( "aaaaa aAa a\n", null, "\n", "","aaaaaaaa", "aAA AAA aa\n" ) ).retainAll( Arrays.asList( "A", "AA", "AAA", "AAAAA", "AAAAAAAA" ) ) );
	}

}
