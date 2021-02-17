package com.cronxparser;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("No arguments passed!");
            System.exit(0);
        } else if (args[0].length() < 6) {
            System.out.println("Expected 6 attributes but only found:" + args[0].length());
            System.exit(0);
        }

        CronParser cronParser = new CronParser();
        System.out.println(cronParser.parseExpression(args[0]));
    }
}
