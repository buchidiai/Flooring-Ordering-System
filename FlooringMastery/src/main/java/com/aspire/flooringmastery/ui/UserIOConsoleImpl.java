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

                this.print("Press q to quit");
                value = in.nextLine();

                if (value.toLowerCase().charAt(0) == 'q') {
                    return null;
                } else {
                    if (value.length() == 1) {
                        this.print("Must be a valid currency amount");
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

                }

            } catch (Exception e) {

                this.print("Must be a valid currency amount, try again!");
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

                    month = Integer.parseInt(results[0]);
                    day = Integer.parseInt(results[1]);
                    year = Integer.parseInt(results[2]);

                    //validate date
                    LocalDate orderDate = LocalDate.of(year, month, day);

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

                    month = Integer.parseInt(results[0]);
                    day = Integer.parseInt(results[1]);
                    year = Integer.parseInt(results[2]);

                    LocalDate today = LocalDate.now();

                    //validate date
                    LocalDate orderDate = LocalDate.of(year, month, day);

                    if (orderDate.isEqual(today)) {
                        this.print("Orders can't be placed on same day basis, Please try " + Util.formatDate(today, 1) + " or a later date.");
                        continue;
                    }

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
