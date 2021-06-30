package ir.fararayaneh.erp.utils;


import android.util.Log;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;

import java.util.ArrayList;
import java.util.List;

import ir.fararayaneh.erp.IBase.common_base.IListeners;


/**
 * deprecated
 */
public class CompositeUnRegisterListener {

    private static List<IListeners> listenersList = new ArrayList<>();

    public void add(IListeners iListeners) {
        listenersList.add(iListeners);
    }


    public void unRegistersAll() {

        Stream.of(listenersList).forEach(new Consumer<IListeners>() {
            @Override
            public void accept(IListeners iListeners) {
                iListeners = null;
            }
        });

        listenersList.clear();
    }

}
