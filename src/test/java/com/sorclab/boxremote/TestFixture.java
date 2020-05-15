package com.sorclab.boxremote;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TestFixture
{
    public static InputStream getFileInputStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }
}
