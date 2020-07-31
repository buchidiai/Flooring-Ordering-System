/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 *
 * @author louie
 */
public class Util {

    public static String getTodaysDate() {

        LocalDate today = LocalDate.now();

        String[] day = Util.formatDate(today).split("/");
        String orderDate = day[0] + day[1] + day[2];
        return orderDate;
    }

    public static String addPercentage(BigDecimal big) {

        String templateFormat = "code:%1$s-%2$s-100";
        return big + "%";
    }

    public static String formatDate(LocalDate date, int daysToAdd) {

        return date.plusDays(daysToAdd).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

    }

    public static String cleanDate(String str) {

        return str.replaceAll("/", "").trim();

    }

    public static String formatDate(LocalDate date) {

        return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

    }

    public static String replaceSpecialCharacters(String str) {

        return str.replaceAll("/[^,;a-zA-Z0-9_-]|[,;]$/s", "").trim();

    }

    public static String BigDecimalToString(BigDecimal big) {
        return "" + big;
    }

    public static String appendToMoney(BigDecimal money) {

        String currency = money.compareTo(BigDecimal.ZERO) <= 0 ? "$" : money.compareTo(BigDecimal.ONE) >= 0 ? "$" : "₵";

        return "" + (currency + money);

    }

    public static String capitalizeFirstWord(String str) {

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static boolean isNumeric(String strNum) {

        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

}
