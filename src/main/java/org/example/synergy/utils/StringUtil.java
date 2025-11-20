/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@UtilityClass
public class StringUtil {
    
    private static final Pattern LETTERS_UNICODE_PATTERN = Pattern.compile("^\\p{L}+$");
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    
    /**
     * Escapes special SQL LIKE pattern characters (%, _, \) in the input string
     * @param string input string to escape
     * @return escaped string or null if input is blank
     */
    public String escapeSqlCommand(String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        
        StringBuilder stringBuilder = new StringBuilder();
        int length = string.length();
        for (int i = 0; i < length; ++i) {
            char c = string.charAt(i);
            switch (c) {
                case '\\':
                    stringBuilder.append("\\\\");
                    break;
                case '%':
                    stringBuilder.append("\\%");
                    break;
                case '_':
                    stringBuilder.append("\\_");
                    break;
                default:
                    stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }
    
    /**
     * Appends a string followed by system-specific line separator to StringBuilder
     * @param sb StringBuilder to append to
     * @param str string to be appended
     */
    public void appendLine(StringBuilder sb, String str) {
        sb.append(str).append(System.lineSeparator());
    }
    
    /**
     * Normalizes string by removing diacritical marks and converting to lowercase
     * @param input string to normalize
     * @return normalized string or original if input is blank
     */
    public String normalizeString(String input) {
        if (StringUtils.isBlank(input)) {
            return input;
        }
        
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{M}", "");
        return normalized.toLowerCase();
    }
    
    /**
     * Normalizes a list of strings by removing diacritical marks from each element
     * @param inputs list of strings to normalize
     * @return list of normalized strings
     */
    public List<String> normalizeStrings(List<String> inputs) {
        return inputs.stream()
            .map(StringUtil::normalizeString)
            .collect(Collectors.toList());
    }
    
    /**
     * Converts Vietnamese characters 'đ/Đ' to 'd/D'
     * @param input string containing Vietnamese characters
     * @return converted string or empty string if input is blank
     */
    public String removeVietnameseAccent(String input) {
        if (StringUtils.isBlank(input)) {
            return "";
        }
        
        input = input.replace('đ', 'd');
        input = input.replace('Đ', 'D');
        return input;
    }
    
    /**
     * Formats a string for use in a SQL LIKE clause with wildcards.
     *
     * @param input The input string to be formatted.
     * @return A formatted string with wildcards (e.g., "%input%").
     */
    public String formatLikeClause(String input) {
        if (input == null) {
            return "%%"; // Return wildcards for null input
        }
        return String.format(Locale.ENGLISH, "%%%s%%", escapeSqlCommand(input));
    }
    
    public String addBrackets(String input) {
        return "[" + input + "]";
    }
    
    public boolean isLettersUnicodeOnly(String text) {
        if (StringUtils.isBlank(text)) {
            return false;
        }
        return LETTERS_UNICODE_PATTERN.matcher(text).matches();
    }
    
    public boolean isValidEmailFormat(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    public String buildKey(String... parts) {
        return Arrays.stream(parts)
            .filter(StringUtils::isNotBlank)
            .map(String::trim)
            .collect(Collectors.joining("_"));
    }
    
    public List<String> removeNullValues(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }
        
        return list.stream()
            .filter(Objects::nonNull)
            .filter(value -> !"null".equalsIgnoreCase(value.trim()))
            .toList();
    }
}
