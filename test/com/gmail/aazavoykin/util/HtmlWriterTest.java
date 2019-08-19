package com.gmail.aazavoykin.util;

import com.gmail.aazavoykin.ResumeTestData;
import com.gmail.aazavoykin.model.AbstractSection;
import com.gmail.aazavoykin.model.SectionType;
import org.junit.Before;
import org.junit.Test;

public class HtmlWriterTest {
    private AbstractSection section;

    @Before
    public void setUp() {
        section = ResumeTestData.RESUME_1.getSection(SectionType.EXPERIENCE);
    }

    @Test
    public void toHtml() {
        System.out.println(HtmlWriter.toHtml(SectionType.EXPERIENCE, section));
    }
}