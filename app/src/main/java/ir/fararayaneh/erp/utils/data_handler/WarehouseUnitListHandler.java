package ir.fararayaneh.erp.utils.data_handler;

import android.content.Context;

import com.annimon.stream.Stream;

import java.util.ArrayList;

import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;

import static ir.fararayaneh.erp.commons.Commons.SPLITTER_OF_CONFIGS;

public class WarehouseUnitListHandler {

    public static boolean showGoodUnit(Context context, String warehouseId) {
        String stringWarehouseList = SharedPreferenceProvider.getDisplayableWarehouseList(context);
        if (stringWarehouseList.contains(Commons.SPLITTER_OF_CONFIGS)) {
            ArrayList<String> warehouseList = new ArrayList<>();
            Stream.of(stringWarehouseList.split(SPLITTER_OF_CONFIGS)).forEach(warehouseList::add);
            return warehouseList.contains(warehouseId);
        }
        return false;
    }
}
