package com.redhat.ceylon.compiler.java.language;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import ceylon.language.Resource;

import com.redhat.ceylon.compiler.java.metadata.Ceylon;
import com.redhat.ceylon.compiler.java.metadata.Class;
import com.redhat.ceylon.compiler.java.metadata.Ignore;
import com.redhat.ceylon.compiler.java.metadata.SatisfiedTypes;

@Ceylon(major = 8)
@Class(extendsType="ceylon.language::Object")
@SatisfiedTypes({
    "ceylon.language::Resource"
})
public class ZipResource implements Resource {

    @Ignore
    protected final ceylon.language.Resource$impl $ceylon$language$Resource$this;
    private final File zipFile;
    private final String path;
    private long size = -1;
    private final String fullPath;

    public ZipResource(File zip, String path) {
        this.zipFile = zip;
        this.path = path;
        $ceylon$language$Resource$this = new ceylon.language.Resource$impl(this);
        fullPath = zip.toURI() + "!" + path;
    }

    @Ignore @Override
    public ceylon.language.Resource$impl $ceylon$language$Resource$impl() {
        return $ceylon$language$Resource$this;
    }

    @Override
    public java.lang.String getName() {
        String name = $ceylon$language$Resource$this.getName();
        int p = name.lastIndexOf('!');
        if (p >= 0) {
            name = name.substring(p + 1);
        }
        return name;
    }

    @Override
    public long getSize() {
        if (size == -1) {
            try (ZipFile zip = new ZipFile(zipFile)) {
                final ZipEntry e = zip.getEntry(path);
                size = e.getSize();
            } catch (IOException ex) {
                throw new ceylon.language.Exception(new ceylon.language.String(
                        "Calculating size of CAR resource " + getUri()), ex);
            }
        }
        return size;
    }

    @Override
    public java.lang.String getUri() {
        return fullPath;
    }

    @Override
    public java.lang.String textContent() {
        return textContent(textContent$encoding());
    }

    @Override
    public java.lang.String textContent$encoding() {
        return $ceylon$language$Resource$this.textContent$encoding();
    }

    @Override
    public java.lang.String textContent(java.lang.String enc) {
        try (ZipFile zip = new ZipFile(zipFile)) {
            final ZipEntry e = zip.getEntry(path);
            try (final InputStream stream = zip.getInputStream(e);
                    final InputStreamReader reader = new InputStreamReader(stream, enc)){
                final char[] buf = new char[16384];
                final StringBuilder sb = new StringBuilder();
                while (reader.ready()) {
                    int read = reader.read(buf);
                    sb.append(buf, 0, read);
                }
                return sb.toString();
            }
        } catch (IOException ex) {
            throw new ceylon.language.Exception(new ceylon.language.String(
                    "Reading text content of CAR resource " + getUri()), ex);
        }
    }
    
    @Override
    public String toString() {
        return $ceylon$language$Resource$this.toString();
    }

}
