package ir.fararayaneh.erp.utils;

import android.app.Activity;

import androidx.appcompat.widget.AppCompatEditText;

import android.content.Context;
import android.text.TextUtils;

import com.annimon.stream.Stream;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;

public class EditTextCheckHelper {


    public static boolean checkWithRaiseError(AppCompatEditText editText, Activity activity) {

        if (TextUtils.isEmpty(Objects.requireNonNull(editText.getText()).toString().trim())) {
            editText.setError(activity.getString(R.string.empty_edit_text));
            editText.requestFocus();
            return false;
        } else {
            return true;
        }

    }

    public static boolean checkWithoutRaiseError(AppCompatEditText editText) {
        return !TextUtils.isEmpty(Objects.requireNonNull(editText.getText()).toString().trim());
    }

    public static String concatHandler(String... strings) {
        AtomicReference<String> myString = new AtomicReference<>("");
        Stream.of(strings).forEach(s -> myString.set(myString + s + "\n"));
        return myString.get().trim();
    }

    public static String fullCarPlaqueHelper(String plaqueNumber) {
        if (plaqueNumber.length() == Commons.PLAQUE_LENGTH && !plaqueNumber.contains("-")) {
            return plaqueNumber.substring(0, 2) + "\u202B" + plaqueNumber.substring(2, 3) + "\u202C" + plaqueNumber.substring(3, 6) + "-" + plaqueNumber.substring(6) + "\u202B" + "ایران" + "\u202C";
        }
        return plaqueNumber;
    }


}
