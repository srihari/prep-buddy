package org.apache.prepbuddy.transformation;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ColumnSplitterByFieldLengthsTest {
    @Test
    public void shouldSplitTheGivenColumnByTheGivenLengths() {
        String[] inputRecord = "FirstName LastName MiddleName,850".split(",");

        ColumnSplitterByFieldLengths splitColumn = new ColumnSplitterByFieldLengths(Arrays.asList(9, 9), false);
        String[] actualValue = splitColumn.apply(inputRecord, 0);

        assertEquals("FirstName, LastName,850", StringUtils.join(actualValue, ","));
    }
}