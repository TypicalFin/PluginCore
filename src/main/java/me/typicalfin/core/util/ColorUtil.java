package me.typicalfin.core.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class ColorUtil {

    private final char COLOR_CHAR = ChatColor.COLOR_CHAR;

    public String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', translateHexColorCodes(message));
    }

    public List<String> translateList(List<String> list) {
        final List<String> temp = new ArrayList<>();
        list.forEach(s -> temp.add(translate(s)));

        return temp;
    }

    public String translateHexColorCodes(String message) {
        final Pattern hexPattern = Pattern.compile("&#([A-Fa-f\\d]{6})");
        final Matcher matcher = hexPattern.matcher(message);

        final StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

        while (matcher.find()) {
            final String group = matcher.group(1);

            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }

        return matcher.appendTail(buffer).toString();
    }

}
