package com.gmail.aazavoykin.util;

import com.gmail.aazavoykin.ResumeTestData;
import com.gmail.aazavoykin.model.AbstractSection;
import com.gmail.aazavoykin.model.SectionType;
import org.junit.Before;
import org.junit.Test;

import java.time.YearMonth;

public class HtmlUtilTest {
    private AbstractSection section;

    @Before
    public void setUp() {
        section = ResumeTestData.RESUME_1.getSection(SectionType.EXPERIENCE);
    }

    @Test
    public void toHtml() {
        System.out.println(HtmlUtil.toHtml(SectionType.EXPERIENCE, section));
        System.out.println(HtmlUtil.toHtml(YearMonth.of(1990, 8)));
    }


}