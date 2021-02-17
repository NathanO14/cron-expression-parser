package com.cronxparser;

public class CronParser {

    public String parseExpression(String expression) {
        String[] cronStrings = expression.split("\\s");
        String minute = extractNumberRange(cronStrings[0], 0, 60);
        String hour = extractNumberRange(cronStrings[1], 0, 23);
        String dayOfMonth = extractNumberRange(cronStrings[2], 1, 31);
        String month = extractNumberRange(cronStrings[3], 1, 12);
        String dayOfWeek = extractNumberRange(cronStrings[4], 1, 7);
        String command = cronStrings[5];

        return formattedOutput(minute, hour, dayOfMonth, month, dayOfWeek, command);
    }

    String formattedOutput(String minute, String hour, String dayOfMonth, String month, String dayOfWeek,
                           String command) {
        StringBuilder sb = new StringBuilder();

        sb.append("minute\t\t\t").append(minute).append("\n");
        sb.append("hour\t\t\t").append(hour).append("\n");
        sb.append("dayOfMonth\t\t").append(dayOfMonth).append("\n");
        sb.append("month\t\t\t").append(month).append("\n");
        sb.append("day of week\t\t").append(dayOfWeek).append("\n");
        sb.append("command\t\t\t").append(command);

        return sb.toString();
    }

    String extractNumberRange(String input, int min, int max) {
        StringBuilder sb = new StringBuilder();
        int lowest = min, highest = max;

        if (isNumeric(input)) {
            return cronOutputFromNumber(input, min, max);
        }

        if (input.contains(",")) {
            return cronOutputFromCommaList(input, min, max, sb);
        }

        if (input.contains("*/")) {
            return cronOutputFromDivider(input, sb, min, max);
        }

        if (!input.equals("*") && input.contains("-")) {
            String[] splitStr = input.split("-");

            int selectedLowest = Integer.valueOf(splitStr[0]);
            if (selectedLowest >= lowest) {
                lowest = selectedLowest;
            }

            int selectedHighest = Integer.valueOf(splitStr[splitStr.length - 1]);
            if (selectedHighest <= max) {
                highest = selectedHighest;
            }
        }

        return cronOutputAll(lowest, highest, sb);
    }

    String cronOutputFromNumber(String input, int min, int max) {
        int number = Integer.valueOf(input);

        if (number > max) {
            number = max;
        } else if (number < min) {
            number = min;
        }

        return String.valueOf(number);
    }

    String cronOutputAll(int lowest, int highest, StringBuilder sb) {
        // Default case is all times between minimum and maximum values
        for (int i = lowest; i <= highest; i++) {
            sb.append(i);
            sb.append(" ");
        }

        return sb.toString().trim();
    }

    boolean isNumeric(String str) {
        try {
            Integer.valueOf(str);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    String cronOutputFromDivider(String input, StringBuilder sb, int min, int max) {
        int rangeDivider = Integer.valueOf(input.split("/")[1]);

        // If rangeDivider is zero, return everything between min and max range
        if (rangeDivider == 0) {
            return cronOutputAll(min, max, sb);
        }

        int currentCount = min;

        while (currentCount < max) {
            sb.append(currentCount);
            sb.append(" ");
            currentCount += rangeDivider;
        }

        return sb.toString().trim();
    }

    String cronOutputFromCommaList(String input, int min, int max, StringBuilder sb) {
        // When values are comma separated, loop through list
        for (String s : input.split(",")) {
            if (Integer.valueOf(s) >= min && Integer.valueOf(s) <= max) {
                sb.append(s);
                sb.append(" ");
            }
        }

        return sb.toString().trim();
    }
}
