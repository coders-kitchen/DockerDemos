package com.coders_kitchen.pherousa.atom;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AtomFeedEntryBuilderTest {

    AtomFeedEntryBuilder cut = new AtomFeedEntryBuilder();

    @Test
    public void testAtomFeedEntryBuilder() throws Exception {
        cut.setPort("1234");
        cut.setResource("resource");

        String expectedResult = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><entry xmlns=\"http://www.w3.org/2005/Atom\"><link href=\"http://localhost:1234/resource/test123\" rel=\"related\"/></entry>";
        String actualResult = cut.buildAtomFeedEntry("test123");
        assertThat(actualResult, is(expectedResult));
    }
}