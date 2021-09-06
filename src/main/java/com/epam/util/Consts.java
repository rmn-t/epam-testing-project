package com.epam.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class Consts {
    public final Logger logger = LoggerFactory.getLogger(Consts.class);

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SUBJECT = "subject";
    public static final String COMPLEXITY = "scale";
    public static final String DURATION = "duration_sec";
    public static final String COUNT = "questionsNum";

    public static final String[] VALID_COLUMNS_FOR_ORDER_BY = {
        ID + " ASC", NAME + " ASC", SUBJECT  + " ASC", COMPLEXITY + " ASC", DURATION + " ASC", COUNT + " ASC",
        ID + " DESC", NAME + " DESC", SUBJECT  + " DESC", COMPLEXITY + " DESC", DURATION + " DESC", COUNT + " DESC"
    };

    public static List<String> getVALID_COLUMNS_FOR_TEST_ORDER_BY() {
        return Arrays.asList(VALID_COLUMNS_FOR_ORDER_BY);
    }

}
