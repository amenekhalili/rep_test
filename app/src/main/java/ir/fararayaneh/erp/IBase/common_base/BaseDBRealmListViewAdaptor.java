package ir.fararayaneh.erp.IBase.common_base;

import android.widget.BaseAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Objects;

import io.realm.OrderedRealmCollection;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;


//todo improve change in attention of realm update  :   implementation 'io.realm:android-adapters:3.1.0'

/**
 * the number and type and order of realm results
 * that use in ordinary manner should be same in search manner
 * because all data passed from onBind view holder
 *
 * here only listen to change, and do not separate add or edit or ...
 */

public abstract class BaseDBRealmListViewAdaptor  extends BaseAdapter {

    @Nullable
    protected OrderedRealmCollection<RealmModel>[] adapterData;
    private final ArrayList<OrderedRealmCollectionChangeListener> listenerList = new ArrayList<>();

    public BaseDBRealmListViewAdaptor(@Nullable OrderedRealmCollection[] data) {

        this.adapterData = data;


        //do not use requireNotNull for data[0]
        if ( data!=null) {
            createListenerList();
                for (int i = 0; i < Objects.requireNonNull(adapterData).length ; i++) {
                    addListener(adapterData[i],listenerList.get(i));
                }
        }
    }

    private void createListenerList() {
        listenerList.clear();
        for (int i = 0; i < Objects.requireNonNull(adapterData).length; i++) {
            listenerList.add(createListener());
        }
    }

    private OrderedRealmCollectionChangeListener createListener() {
        return (collection, changeSet) -> BaseDBRealmListViewAdaptor.this.notifyDataSetChanged();
    }

    /**
     *attach listeners to table
     */
    private void addListener(@NonNull OrderedRealmCollection data,OrderedRealmCollectionChangeListener listener) {
        if (data instanceof RealmResults) {
            RealmResults results = (RealmResults) data;
            //noinspection unchecked
            results.addChangeListener(listener);
        } else if (data instanceof RealmList) {
            RealmList list = (RealmList) data;
            list.addChangeListener(listener);
        }
    }



    /**
     *detach listeners from table
     */
    private void removeListener(@NonNull OrderedRealmCollection data,OrderedRealmCollectionChangeListener listener) {
        if (data instanceof RealmResults) {
            RealmResults results = (RealmResults) data;
            //noinspection unchecked
            results.removeChangeListener(listener);
        } else if (data instanceof RealmList) {
            RealmList list = (RealmList) data;
            //noinspection unchecked
            list.removeChangeListener(listener);
        }
    }

    /**
     * Returns how many items are in the data set.
     *
     * @return the number of items.
     */
    @Override
    public int getCount() {
        //noinspection ConstantConditions
        return isDataValid() ? adapterData[0].size(): 0;
    }


    /**
     * caution : only for use item of first realResult
     */
    @Override
    public Object getItem(int position) {
        //noinspection ConstantConditions
        return isDataValid() ? adapterData[0].get(position) : null;

    }

    public RealmModel getCorrectItem(int positionOfRealmData, int positionOfRow) {
        if (positionOfRealmData >= 0 && positionOfRow >= 0) {
            // To avoid exception, return null if there are some extra positions that the
            // child adapter is adding in getItemCount (e.g: to display footer view in recycler view)
            //do not use requireNotNull for adapterData[positionOfRealmData]
            if (adapterData[positionOfRealmData] != null && positionOfRow >= adapterData[positionOfRealmData].size())
                return null;
            //noinspection ConstantConditions
            return isDataValid() ? adapterData[positionOfRealmData].get(positionOfRow) : null;
        }
        return null;
    }


    /**
     * Get the row id associated with the specified position in the list. Note that item IDs are not stable so you
     * cannot rely on the item ID being the same after {@link #notifyDataSetChanged()} or
     * {@link #updateData(OrderedRealmCollection[])} has been called.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        // TODO: find better solution once we have unique IDs
        return position;
    }

    /**
     * Updates the data associated with the Adapter.
     *
     * Note that RealmResults and RealmLists are "live" views, so they will automatically be updated to reflect the
     * latest changes. This will also trigger {@code notifyDataSetChanged()} to be called on the adapter.
     *
     * This method is therefore only useful if you want to display data based on a new query without replacing the
     * adapter.
     *
     * @param data the new {@link OrderedRealmCollection} to display.
     */
    @SuppressWarnings("WeakerAccess")
    public void updateData(@Nullable OrderedRealmCollection[] data) {

            if (isDataValid()) {
                for (int i = 0; i < Objects.requireNonNull(adapterData).length ; i++) {
                    removeListener(adapterData[i],listenerList.get(i));
                }}

            adapterData = data;

        if (isDataValid()) {
            createListenerList();
            for (int i = 0; i < Objects.requireNonNull(adapterData).length ; i++) {
                addListener(adapterData[i],listenerList.get(i));
            }
        }
        notifyDataSetChanged();
    }

    private boolean isDataValid() {
        //do not use requireNotNull for adapterData
        if(adapterData!=null){
            return adapterData[0].isValid();
        }
        return false;
    }


}
