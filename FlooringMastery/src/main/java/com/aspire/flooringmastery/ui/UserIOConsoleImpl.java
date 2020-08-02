/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.ui;

import com.aspire.flooringmastery.util.Util;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Scanner;

/**
 *
 * @author louie
 */
public class UserIOConsoleImpl implements UserIO {

    final private Scanner in = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        this.print(prompt);
        return in.nextLine();
    }

    @Override
    public boolean readYesOrNo(String prompt) {

        String value = "";

        boolean valid = true;
        boolean yesOrNo = false;

        while (valid) {
            try {

                this.print(prompt);

                value = in.nextLine();

                if (value.toLowerCase().charAt(0) == 'y' || value.toLowerCase().charAt(0) == 'n') {

                    if (value.toLowerCase().charAt(0) == 'y') {
                        yesOrNo = true;
                        valid = false;
                    } else if (value.toLowerCase().charAt(0) == 'n') {
                        yesOrNo = false;
                        valid = false;
                    }

                } else {
                    this.print("You must enter (Yes/No)");
                    continue;

                }

            } catch (Exception e) {

                this.print("You must enter (Yes/No)");
                continue;
            }

        }

        return yesOrNo;

    }

    @Override
    public int readInt(String prompt) {

        int response = 0;
        boolean valid = true;

        while (valid) {

            try {

                String stringValue = this.readString(prompt);
                response = Integer.parseInt(stringValue);

                valid = false;

            } catch (Exception e) {

                this.print("Value must be a number");

            }

        }

        return response;

    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int response = 0;
        boolean valid = true;

        while (valid) {

            try {

                this.print(prompt + " (" + min + "-" + max + ")");

                String stringValue = in.nextLine();

                response = Integer.parseInt(stringValue);

                if (response <= 0 || response >= (max + 1)) {
                    continue;
                }

                valid = false;

            } catch (Exception e) {

                this.print("Value entered must be from " + min + " to " + max);
                continue;
            }

        }

        return response;

    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {

        BigDecimal result = new BigDecimal("0");

        String value = "";

        boolean valid = true;

        while (valid) {
            try {

                this.print(prompt);
                value = in.nextLine();

                if (value.length() == 1) {
                    this.print("Input not valid");
                    continue;
                } else {
                    result = new BigDecimal(value);

                    //check if negative
                    if (result.signum() == -1) {
                        this.print("Must be a positive number");
                        continue;
                    } else {
                        //not negative
                        result = new BigDecimal(value).setScale(2, RoundingMode.CEILING);
                        valid = false;
                    }

                }

            } catch (Exception e) {

                this.print("Input not valid");
                continue;
            }

        }

        return result;
    }

    @Override
    public String readDate(String Prompt) {

        boolean isValid = true;
        int month = 0;
        int day = 0;
        int year = 0;
        String response = "";

        String[] results = new String[3];

        while (isValid) {
            response = this.readString(Prompt);
            try {

                if (!(response.contains("/"))) {
                    this.print("Please enter the correct date format");
                    continue;
                } else if (response.contains("/")) {
                    results = response.split("/");
                }

                if (results.length != 3) {
                    this.print("Please enter the correct date format");
                    continue;
                } else {

                    if (results[2].length() < 4 || results[2].length() > 4) {
                        this.print("Please enter a correct year format");
                        continue;

                    }

                    //1-12
                    month = Integer.parseInt(results[0]);
                    //1-31
                    day = Integer.parseInt(results[1]);

                    year = Integer.parseInt(results[2]);

                    String DigitMonth = String.format("%02d", month);
                    String DigitDay = String.format("%02d", day);

                    //validate date
                    LocalDate orderDate = LocalDate.of(year, month, day);

                    response = DigitMonth + "/" + DigitDay + "/" + year;

                    //today.
                    isValid = false;
                }
            } catch (Exception e) {
                //add invalid date exception
                this.print("Please enter the correct date format");
                continue;
            }
        }
        System.out.println("response date return " + response);
        return response;
    }

    @Override
    public String readDateForOrders(String Prompt) {

        boolean isValid = true;
        int month = 0;
        int day = 0;
        int year = 0;
        String response = "";

        String[] results = new String[3];

        while (isValid) {
            response = this.readString(Prompt);
            try {

                if (!(response.contains("/"))) {
                    this.print("Please enter the correct date format");
                    continue;
                } else if (response.contains("/")) {
                    results = response.split("/");
                }

                if (results.length != 3) {
                    this.print("Please enter the correct date format");
                    continue;
                } else {

                    if (results[2].length() < 4 || results[2].length() > 4) {
                        this.print("Please enter a correct year format");
                        continue;

                    }

                    month = Integer.parseInt(results[0]);
                    day = Integer.parseInt(results[1]);
                    year = Integer.parseInt(results[2]);

                    String DigitMonth = String.format("%02d", month);
                    String DigitDay = String.format("%02d", day);

                    LocalDate today = LocalDate.now();

                    //validate date
                    LocalDate orderDate = LocalDate.of(year, month, day);

                    //restricts dates that are the same as current day
                    if (orderDate.isBefore(today) || orderDate.isEqual(today)) {
                        this.print("Orders can't be on or before present day, Please try " + Util.formatDate(today, 1) + " or a later date.");
                        this.print("");
                        continue;
                    }

                    response = DigitMonth + "/" + DigitDay + "/" + year;

                    //today.
                    isValid = false;
                }
            } catch (Exception e) {
                //add invalid date exception
                this.print("Please enter the correct date format");
                continue;
            }
        }
        return response;
    }

}
