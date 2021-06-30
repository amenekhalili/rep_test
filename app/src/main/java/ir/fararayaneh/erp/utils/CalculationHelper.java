package ir.fararayaneh.erp.utils;

import android.content.Context;
import android.util.Log;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.regex.Pattern;

import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;

public class CalculationHelper {

    private static final int Common_SCALE = 30;

    /**
     * محاسبه طول مقدار بخش صحیح عدد چه منفی چه مثبت
     * علامت منفی در محاسبات لحاظ نمیشود
     * این موضوع که ایا عدد ما valid
     * است یا خیر در خارج از اینجا باید محاسبه شود
     */
    public static int getIntegralLengthBigDecimalValue(BigDecimal bigDecimal) {

        String value = bigDecimal.abs().toPlainString();

        if (value.contains(Commons.DOT)) {
            return value.substring(0, value.indexOf(Commons.DOT)).length();
        } else if (value.contains(Commons.POWER_SIGN)) {
            return value.substring(0, value.indexOf(Commons.POWER_SIGN)).length();
        } else {
            return value.length();
        }
    }


    /**
     * 05.5 is false
     * 0.5 is true
     * 5.5 is false
     */
    public static boolean isIntegralABSBigDecimalValueZero(BigDecimal bigDecimal) {
        return bigDecimal.abs().toPlainString().startsWith(Commons.ZERO_STRING);
    }


    /**
     * ممکن مقدار ما منفی باشد اما یک مقدار VALID نباشد
     *
     * Compares this {@code BigDecimal} with {@code val}. Returns one of the
     * three values {@code 1}, {@code 0}, or {@code -1}. The method behaves as
     * if {@code this.subtract(val)} is computed. If this difference is > 0 then
     * 1 is returned, if the difference is < 0 then -1 is returned, and if the
     * difference is 0 then 0 is returned. This means, that if two decimal
     * instances are compared which are equal in value but differ in scale, then
     * these two instances are considered as equal.
     */
    public static boolean isNegativeValue(String value) {
        return new BigDecimal(value).compareTo(new BigDecimal(Commons.ZERO_STRING)) < 0;
        //return value.startsWith(Commons.ONE_DASH);
    }


    /**
     * * means "zero-or-more",
     * so \d* and [0-9]* mean "zero or more numbers".
     * ? means "zero-or-one". Neither of those qualifiers means exactly one.
     * Most languages also let you use {m,n} to mean "between m and n" (ie: {1,2} means "between 1 and 2")
     * <p>
     * . is not valid
     * - is not valid
     * 0.5 is  valid
     * 05.5 is  valid
     * -05.5 is  valid
     * 50.50 is  valid
     */
    public static boolean numberValidation(String value) {
        return Pattern.matches("^[\\-]?[0-9]{1,1000000}[.]?[0-9]*$", value.trim());
    }


    public static String negateValue(String value) {
        String baseVal = value.trim();
        if(numberValidation(baseVal)){
            return new BigDecimal(baseVal).negate().toPlainString();
        } else {
            return Commons.ZERO_STRING;
        }

    }



    /**
     * check my value is 0 or 0.0 or 0.00 or 0..0 or .........
     * here do not check number validation
     */
    public static boolean isNumberZero(String value) {

        final boolean[] isValueZero = {true};
        char[] myString = value.trim().toCharArray();
        for (char c : myString) {
            String innerStr = String.valueOf(c);
            if (!innerStr.equals(Commons.ZERO_STRING) && !innerStr.equals(Commons.DOT)) {
                isValueZero[0] = false;
            }
        }
        return isValueZero[0];
    }


    /**
     * چک میکند که آیا طول بخش غیر صحیح
     * از حد مجاز بیشتر است یا خیر
     */
    private static boolean isProperFractionSize(BigDecimal value, Context context, boolean isMoney) {

        String stVal = value.toPlainString();

        if (!value.toPlainString().contains(Commons.DOT)) {
            return true;
        } else {
            Log.i("arash", "isProperFractionSize: " + stVal.substring(stVal.indexOf(Commons.DOT) + 1));
            Log.i("arash", "isProperFractionSize: " + stVal.substring(stVal.indexOf(Commons.DOT) + 1).length());
            if (isMoney) {
                return stVal.substring(stVal.indexOf(Commons.DOT) + 1).length() <= SharedPreferenceProvider.getPrecisionPriceLength(context);
            } else {
                return stVal.substring(stVal.indexOf(Commons.DOT) + 1).length() <= SharedPreferenceProvider.getPrecisionAmountLength(context);
            }
        }
    }

    public static String plusAnyAndRoundNonMoneyValue(String baseValue, String plusValue, Context context) {

        String myBase = baseValue.trim();
        String myPlus = plusValue.trim();

        if (numberValidation(myBase) && numberValidation(myPlus)) {
            BigDecimal bigDecimal = new BigDecimal(myBase);
            BigDecimal addedBigDecimal = new BigDecimal(myBase).add(new BigDecimal(myPlus));
            return bigDecimal
                    .add(new BigDecimal(myPlus))
                    .round(new MathContext(isIntegralABSBigDecimalValueZero(addedBigDecimal) ? (getIntegralLengthBigDecimalValue(addedBigDecimal) + SharedPreferenceProvider.getPrecisionAmountLength(context) - 1) : (getIntegralLengthBigDecimalValue(addedBigDecimal) + SharedPreferenceProvider.getPrecisionAmountLength(context))))
                    .toPlainString();
        }
        return Commons.ZERO_STRING;
    }



    public static String plusAnyWithOutRoundValue(String baseValue, String plusValue) {

        String myBase = baseValue.trim();
        String myPlus = plusValue.trim();

        if (numberValidation(myBase) && numberValidation(myPlus)) {
            return new BigDecimal(myBase).add(new BigDecimal(myPlus)).toPlainString();
        }
        return Commons.ZERO_STRING;
    }

    public static String minusAnyWithOutRoundValueMinZero(String baseValue, String minusValue) {

        String myBase = baseValue.trim();
        String myMinus = minusValue.trim();

        if (numberValidation(myBase) && numberValidation(myMinus)) {
            String calculatedValue =new  BigDecimal(myBase).subtract(new BigDecimal(myMinus)).toPlainString();
            if (isNegativeValue(calculatedValue)){
                return Commons.ZERO_STRING;
            }
            return calculatedValue;
        }

        return Commons.ZERO_STRING;
    }





    public static String plusAnyAndRoundMoneyValue(String baseValue, String plusValue, Context context) {

        String myBase = baseValue.trim();
        String myPlus = plusValue.trim();

        if (numberValidation(myBase) && numberValidation(myPlus)) {
            BigDecimal bigDecimal = new BigDecimal(myBase);
            BigDecimal addedBigDecimal = new BigDecimal(myBase).add(new BigDecimal(myPlus));
            return bigDecimal
                    .add(new BigDecimal(myPlus))
                    .round(new MathContext(isIntegralABSBigDecimalValueZero(addedBigDecimal) ? (getIntegralLengthBigDecimalValue(addedBigDecimal) + SharedPreferenceProvider.getPrecisionPriceLength(context) - 1) : (getIntegralLengthBigDecimalValue(addedBigDecimal) + SharedPreferenceProvider.getPrecisionPriceLength(context))))
                    .toPlainString();
        }
        return Commons.ZERO_STRING;
    }

    public static String minusAnyAndRoundNonMoneyValueMinZero(String baseValue, String minusValue, Context context) {
        String calculatedValue = minusAnyAndRoundNonMoneyValue(baseValue, minusValue, context);
        return isNegativeValue(calculatedValue) ? Commons.ZERO_STRING : calculatedValue;
    }

    public static String minusAnyAndRoundMoneyValueMinZero(String baseValue, String minusValue, Context context) {
        String calculatedValue = minusAnyAndRoundMoneyValue(baseValue, minusValue, context);
        return isNegativeValue(calculatedValue) ? Commons.ZERO_STRING : calculatedValue;
    }


    public static String minusAnyAndRoundNonMoneyValue(String baseValue, String minusValue, Context context) {

        String myBase = baseValue.trim();
        String myMinus = minusValue.trim();

        if (numberValidation(myBase) && numberValidation(myMinus)) {
            BigDecimal bigDecimal = new BigDecimal(myBase);
            BigDecimal minusBigDecimal = new BigDecimal(myBase).subtract(new BigDecimal(myMinus));

            return bigDecimal
                    .subtract(new BigDecimal(myMinus))
                    .round(new MathContext(isIntegralABSBigDecimalValueZero(minusBigDecimal) ? (getIntegralLengthBigDecimalValue(minusBigDecimal) + SharedPreferenceProvider.getPrecisionAmountLength(context) - 1) : (getIntegralLengthBigDecimalValue(minusBigDecimal) + SharedPreferenceProvider.getPrecisionAmountLength(context))))
                    .toPlainString();
        }
        return Commons.ZERO_STRING;
    }

    /**
     * minus 1 from value if my value is valid
     */
    public static String minusAnyAndRoundMoneyValue(String baseValue, String minusValue, Context context) {

        String myBase = baseValue.trim();
        String myMinus = minusValue.trim();

        if (numberValidation(myBase) && numberValidation(myMinus)) {
            BigDecimal bigDecimal = new BigDecimal(myBase);
            BigDecimal minusBigDecimal = new BigDecimal(myBase).subtract(new BigDecimal(myMinus));

            return bigDecimal
                    .subtract(new BigDecimal(myMinus))
                    .round(new MathContext(isIntegralABSBigDecimalValueZero(minusBigDecimal) ? (getIntegralLengthBigDecimalValue(minusBigDecimal) + SharedPreferenceProvider.getPrecisionPriceLength(context) - 1) : (getIntegralLengthBigDecimalValue(minusBigDecimal) + SharedPreferenceProvider.getPrecisionPriceLength(context))))
                    .toPlainString();
        }
        return Commons.ZERO_STRING;
    }


    /**
     * (baseValue*multipleValue)/dividedValue
     */
    public static String multipleAnyDividedAnyAndRoundNonMoneyValue(String baseValue, String multipleValue, String dividedValue, Context context) {

        String myBase = baseValue.trim();
        String myMultiple = multipleValue.trim();
        String myDivided = dividedValue.trim();

        if (numberValidation(myBase) && numberValidation(myMultiple) && numberValidation(myDivided)) {

            if (!isNumberZero(myDivided)) {
                BigDecimal calculatedBigDecimal = new BigDecimal(myBase)
                        .multiply(new BigDecimal(myMultiple))
                        .divide(new BigDecimal(myDivided), Common_SCALE, RoundingMode.HALF_UP);

                BigDecimal finalBigDecimal = new BigDecimal(myBase)
                        .multiply(new BigDecimal(myMultiple))
                        .divide(new BigDecimal(myDivided), Common_SCALE, RoundingMode.HALF_UP)
                        .round(new MathContext(isIntegralABSBigDecimalValueZero(calculatedBigDecimal) ? (getIntegralLengthBigDecimalValue(calculatedBigDecimal) + SharedPreferenceProvider.getPrecisionAmountLength(context) - 1) : (getIntegralLengthBigDecimalValue(calculatedBigDecimal) + SharedPreferenceProvider.getPrecisionAmountLength(context))));
                //چون فقط زمانی مقدار اعشار ما بزرگتر از حد لازم میشود که همه اعداد قبل از اعشار صفر است لذا در این شرایط صفر برمیگردانیم
                return isProperFractionSize(finalBigDecimal, context, false) ? finalBigDecimal.toPlainString() : Commons.ZERO_STRING;
            } else {
                return Commons.ZERO_STRING;
            }

        }
        return Commons.ZERO_STRING;
    }

    /**
     * (baseValue*multipleValue)/dividedValue
     */
    public static String multipleAnyDividedAnyAndRoundMoneyValue(String baseValue, String multipleValue, String dividedValue, Context context) {

        String myBase = baseValue.trim();
        String myMultiple = multipleValue.trim();
        String myDivided = dividedValue.trim();

        if (numberValidation(myBase) && numberValidation(myMultiple) && numberValidation(myDivided)) {

            if (!isNumberZero(myDivided)) {
                BigDecimal calculatedBigDecimal = new BigDecimal(myBase)
                        .multiply(new BigDecimal(myMultiple))
                        .divide(new BigDecimal(myDivided), Common_SCALE, RoundingMode.HALF_UP);

                BigDecimal finalBigDecimal = new BigDecimal(myBase)
                        .multiply(new BigDecimal(myMultiple))
                        .divide(new BigDecimal(myDivided), Common_SCALE, RoundingMode.HALF_UP)
                        .round(new MathContext(isIntegralABSBigDecimalValueZero(calculatedBigDecimal) ? (getIntegralLengthBigDecimalValue(calculatedBigDecimal) + SharedPreferenceProvider.getPrecisionPriceLength(context) - 1) : (getIntegralLengthBigDecimalValue(calculatedBigDecimal) + SharedPreferenceProvider.getPrecisionPriceLength(context))));
                //چون فقط زمانی مقدار اعشار ما بزرگتر از حد لازم میشود که همه اعداد قبل از اعشار صفر است لذا در این شرایط صفر برمیگردانیم
                return isProperFractionSize(finalBigDecimal, context, true) ? finalBigDecimal.toPlainString() : Commons.ZERO_STRING;
            } else {
                return Commons.ZERO_STRING;
            }

        }
        return Commons.ZERO_STRING;
    }


    /**
     * محاسبه مبلغ مالیت
     * ((((1-discountPercentag/100)*currencyTotalValue)-currencyDiscount2)*taxPercentage/100)
     */
    public static String currencyTaxValueCalculation(String discountPercentage, String currencyTotalValue, String taxPercentage, String currencyDiscount2, Context context) {

        String myDiscountPercentage = discountPercentage.trim();
        String myTaxPercentage = taxPercentage.trim();
        String myCurrencyTotalValue = currencyTotalValue.trim();
        String myCurrencyDiscount2 = currencyDiscount2.trim();


        if (numberValidation(myDiscountPercentage) && numberValidation(myCurrencyTotalValue) && numberValidation(myTaxPercentage) && numberValidation(myCurrencyDiscount2)) {
            BigDecimal myFinalDiscountPercentageDecimal = new BigDecimal(Commons.PLUS_INT).subtract(new BigDecimal(myDiscountPercentage).divide(new BigDecimal(Commons.HUNDRED_STRING), Common_SCALE, RoundingMode.HALF_UP));
            BigDecimal myFinalTaxPercentageDecimal = new BigDecimal(myTaxPercentage).divide(new BigDecimal(Commons.HUNDRED_STRING), Common_SCALE, RoundingMode.HALF_UP);
            BigDecimal myFinalCurrencyValue = myFinalDiscountPercentageDecimal.multiply(new BigDecimal(myCurrencyTotalValue)).subtract(new BigDecimal(myCurrencyDiscount2));

            BigDecimal calculatedBigDecimal = myFinalCurrencyValue.multiply(myFinalTaxPercentageDecimal);

            BigDecimal finalBigDecimal =
                    myFinalCurrencyValue.multiply(myFinalTaxPercentageDecimal)
                            .round(new MathContext(isIntegralABSBigDecimalValueZero(calculatedBigDecimal) ? (getIntegralLengthBigDecimalValue(calculatedBigDecimal) + SharedPreferenceProvider.getPrecisionPriceLength(context) - 1) : (getIntegralLengthBigDecimalValue(calculatedBigDecimal) + SharedPreferenceProvider.getPrecisionPriceLength(context))));
            //چون فقط زمانی مقدار اعشار ما بزرگتر از حد لازم میشود که همه اعداد قبل از اعشار صفر است لذا در این شرایط صفر برمیگردانیم
            return isProperFractionSize(finalBigDecimal, context, true) ? finalBigDecimal.toPlainString() : Commons.ZERO_STRING;
        }
        return Commons.NULL_INTEGER;//because we check NULL_INTEGER in my activity
    }

    /**
     * ((((1-discountPercentag/100)*currencyTotalValue)-currencydiscount2)*(1+taxPercentage/100))
     */
    public static String currencyNetValueCalculation(String discountPercentage, String currencyTotalValue, String taxPercentage, String currencyDiscount2, Context context) {


        String myDiscountPercentage = discountPercentage.trim();
        String myTaxPercentage = taxPercentage.trim();
        String myCurrencyTotalValue = currencyTotalValue.trim();
        String myCurrencyDiscount2 = currencyDiscount2.trim();


        if (numberValidation(myDiscountPercentage) && numberValidation(myCurrencyTotalValue) && numberValidation(myTaxPercentage) && numberValidation(myCurrencyDiscount2)) {
            BigDecimal myFinalDiscountPercentageDecimal = new BigDecimal(Commons.PLUS_INT).subtract(new BigDecimal(myDiscountPercentage).divide(new BigDecimal(Commons.HUNDRED_STRING), Common_SCALE, RoundingMode.HALF_UP));
            BigDecimal myFinalTaxPercentageDecimal = new BigDecimal(Commons.PLUS_INT).add(new BigDecimal(myTaxPercentage).divide(new BigDecimal(Commons.HUNDRED_STRING), Common_SCALE, RoundingMode.HALF_UP));
            BigDecimal myFinalCurrencyValue = myFinalDiscountPercentageDecimal.multiply(new BigDecimal(myCurrencyTotalValue)).subtract(new BigDecimal(myCurrencyDiscount2));

            BigDecimal calculatedBigDecimal = myFinalCurrencyValue.multiply(myFinalTaxPercentageDecimal);

            BigDecimal finalBigDecimal =
                    myFinalCurrencyValue.multiply(myFinalTaxPercentageDecimal)
                            .round(new MathContext(isIntegralABSBigDecimalValueZero(calculatedBigDecimal) ? (getIntegralLengthBigDecimalValue(calculatedBigDecimal) + SharedPreferenceProvider.getPrecisionPriceLength(context) - 1) : (getIntegralLengthBigDecimalValue(calculatedBigDecimal) + SharedPreferenceProvider.getPrecisionPriceLength(context))));
            //چون فقط زمانی مقدار اعشار ما بزرگتر از حد لازم میشود که همه اعداد قبل از اعشار صفر است لذا در این شرایط صفر برمیگردانیم
            return isProperFractionSize(finalBigDecimal, context, true) ? finalBigDecimal.toPlainString() : Commons.ZERO_STRING;
        }
        return Commons.NULL_INTEGER;//because we check NULL_INTEGER in my activity
    }

}
