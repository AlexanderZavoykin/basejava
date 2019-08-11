package com.gmail.aazavoykin.util;

import com.gmail.aazavoykin.model.AbstractSection;
import com.gmail.aazavoykin.model.Resume;
import com.gmail.aazavoykin.model.TextSection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.gmail.aazavoykin.ResumeTestData.*;

public class JsonParserTest {

    @Test
    public void read() {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Assert.assertEquals(RESUME_1, JsonParser.read(json, Resume.class));
    }

    @Test
    public void writeSection() {
        AbstractSection ts = new TextSection("body");
        String json = JsonParser.write(ts, AbstractSection.class);
        System.out.println(json);
        Assert.assertEquals(ts, JsonParser.read(json, AbstractSection.class));
    }

}