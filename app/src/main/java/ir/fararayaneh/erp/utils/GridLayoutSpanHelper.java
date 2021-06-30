package ir.fararayaneh.erp.utils;

import androidx.recyclerview.widget.GridLayoutManager;
import android.util.Log;

import java.util.ArrayList;

public class GridLayoutSpanHelper {

    /**
     *
     * @param maximumSpanInOneRow //--> نشان دهنده تعداد کل خانه هایی است که در هر ردیف تعریف میشود
     * @param nonMachParentRowSpanCount //--> نشان دهنده تعداد خانه ای است که آن ردیف قرار است اشغال کند اما قرار نیست کل خانه ها را اشغال کند
     * @param positionOfNonMachParentRow شماره ردیف هایی که قرار نیست کل خانه ها را اشغال کنند یا به عبارت دیگر قرار نیست آن ردیف مچ پرنت باشد
     *                                   نکته اینکه اگر تعداد کل خانه های موجود در هر ردیف 4 تا باشد
     *                                   یک سطر میتواند بین 1 تا 4 را اشغال کند اما دقت شود که سطر بعدی اگر قرار باشد در کنار همین سطر نمایش داده شود
     *                                   باید مابقی خانه ها را اشغال نماید
     *ویو هایی که مممکن است خاموش شوند را باید به صورتی در نظر گرفت که کل سطر را در بگیرد و موجب خالی شدن ردیف نشوند
     */
    public static void setupCustomSpanPerEachRow(GridLayoutManager gridLayoutManager,int maximumSpanInOneRow,int nonMachParentRowSpanCount,ArrayList<Integer> positionOfNonMachParentRow){
        Log.i("arash", "setupCustomSpanPerEachRow: **********************************");
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (positionOfNonMachParentRow.contains(position)){
                    return nonMachParentRowSpanCount;
                } else{
                    return maximumSpanInOneRow;
                }
            }
        });

    }


    /**
     * توجه شود که مثلا تعداد ردیفهای یک چارم باید مضربی از چهار بوده و پوزیشن انها به صورت پشت سر هم باشد
     *
     * @param positionOfQuarterRow : ردیف های یک چهارم
     * @param positionOfThirdRow : ردیف های یک سوم
     * @param positionOfHalfRow : ردیف های یک دوم
     */
    public static final int SPAN_ROM_STAIRS = 12;
    public static void setupStairsCustomSpanPerEachRow(GridLayoutManager gridLayoutManager,ArrayList<Integer> positionOfQuarterRow,ArrayList<Integer> positionOfThirdRow,ArrayList<Integer> positionOfHalfRow){


        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (positionOfQuarterRow.contains(position)){
                    return 3;
                } else if (positionOfThirdRow.contains(position)){
                    return 4;
                } else if (positionOfHalfRow.contains(position)){
                    return 6;
                } else {
                    return SPAN_ROM_STAIRS;
                }
            }
        });

    }
}
