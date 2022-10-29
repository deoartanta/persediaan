package com.persediaan.de.data;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Currency {

    String symbol,grouping_separator;
    double number;

    public Currency(String symbol, String grouping_separator) {
        this.symbol = symbol;
        this.grouping_separator = grouping_separator;
        this.number = number;
    }

    public String setFormatNumber(Double number){
        DecimalFormat kursID= (DecimalFormat) DecimalFormat.getNumberInstance();
        DecimalFormatSymbols satuanKursID = new DecimalFormatSymbols();

        satuanKursID.setCurrencySymbol("Rp. ");
//        satuanKursID.setMonetaryDecimalSeparator(',');
        satuanKursID.setGroupingSeparator('.');

        kursID.setDecimalFormatSymbols(satuanKursID);
        return kursID.format(number);
    }
    public String setFormatCurrency(double number){
        DecimalFormat kursID= (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols satuanKursID = new DecimalFormatSymbols();

        satuanKursID.setCurrencySymbol("Rp. ");
        satuanKursID.setMonetaryDecimalSeparator(',');
        satuanKursID.setGroupingSeparator('.');

        kursID.setDecimalFormatSymbols(satuanKursID);
        return kursID.format(number);
    }
}
