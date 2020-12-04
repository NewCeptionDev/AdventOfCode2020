package day4;

import java.util.Set;

/**
 * Constants for the Requirement of Task 2
 */
public class PassportRequirements {
    public static final int BIRTH_YEAR_LOWER = 1920;
    public static final int BIRTH_YEAR_UPPER = 2002;
    public static final int ISSUE_YEAR_LOWER = 2010;
    public static final int ISSUE_YEAR_UPPER = 2020;
    public static final int EXPIRATION_DATE_LOWER = 2020;
    public static final int EXPIRATION_DATE_UPPER = 2030;
    public static final int HEIGHT_METRIC_LOWER = 150;
    public static final int HEIGHT_METRIC_UPPER = 193;
    public static final int HEIGHT_IMPERIAL_LOWER = 59;
    public static final int HEIGHT_IMPERIAL_UPPER = 76;
    public static final int HAIR_COLOR_CODE_LENGTH = 6;
    public static final Set<String> EYE_COLORS = Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
    public static final int PASSPORT_ID_LENGTH = 9;
}
