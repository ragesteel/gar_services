package ru.gt2.rusref;

import junit.framework.Assert;
import org.junit.Test;

public class FilenamesTest {
    @Test(expected = NullPointerException.class)
    public void testNull() throws Exception {
        Filenames.replaceExtension(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullExtension() throws Exception {
        Filenames.replaceExtension("", null);
    }

    @Test
    public void testReplace() {
        Assert.assertEquals("filename.serialized", Filenames.replaceExtension("filename.xml", "serialized"));
    }

    @Test
    public void testReplaceWoExt() {
        Assert.assertEquals("filename.serialized", Filenames.replaceExtension("filename", "serialized"));
    }
}
