package me.tolek.util;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public static boolean evaluateRegex(@NotNull String regex, @NotNull String matchAgainst, int flags) {

        Pattern pattern = Pattern.compile(regex, flags);
        Matcher matcher = pattern.matcher(matchAgainst);
        return matcher.find();

    }

    public static boolean evaluateRegex(@NotNull String regex, @NotNull String matchAgainst) {

        return evaluateRegex(regex, matchAgainst, Pattern.CASE_INSENSITIVE);

    }

}
