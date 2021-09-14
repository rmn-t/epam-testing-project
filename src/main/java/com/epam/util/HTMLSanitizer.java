package com.epam.util;

/**
 * The purpose of the class is to prevent unwanted symbols from user input getting into the database.
 * Class doesn't need to have instance, thus it has private constructor. All the methods for outer use
 * should be declared as static.
 */
public class HTMLSanitizer {
    private HTMLSanitizer() {}

    /**
     * Method replaces symbols "<" and ">" with their html analogs in order to prevent inputs like
     * <script>alert('some text');</script> and similar ones.
     * @param input the text to be cleaned
     * @return empty string if the initial input is null, otherwise return cleaned string
     */
    public static String cleanLtMt(String input) {
        if (input == null) {
            return "";
        } else {
            return input.replaceAll("<","&lt;").replaceAll(">","&gt;");
        }
    }
}
