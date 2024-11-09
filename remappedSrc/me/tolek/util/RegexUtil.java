package me.tolek.util;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexUtil {

    public static boolean evaluateRegex(@NotNull String regex, @NotNull String matchAgainst, int flags) {
        if (!validateRegex(regex).value1) return false;
        Pattern pattern = Pattern.compile(regex, flags);
        Matcher matcher = pattern.matcher(matchAgainst);
        return matcher.find();
    }

    public static boolean evaluateRegex(@NotNull String regex, @NotNull String matchAgainst) {
        return evaluateRegex(regex, matchAgainst, Pattern.CASE_INSENSITIVE);
    }

    public static Tuple<Boolean, String> validateRegex(@NotNull String regex) {
        // Check if a given RegEx is valid.
        try {
            Pattern.compile(regex);
        } catch (PatternSyntaxException exception) { // Pattern.compile() returns an error if the RegEx is invalid.
            return new Tuple<>(false, exception.getDescription());
        }

        return new Tuple<>(true, "");
    }

}
