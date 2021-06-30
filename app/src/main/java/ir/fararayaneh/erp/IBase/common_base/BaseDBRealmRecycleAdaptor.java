package ir.fararayaneh.erp.IBase.common_base;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollection;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;
import ir.fararayaneh.erp.commons.CommonViewTypeReport;

//todo improve change in attention of realm update in Gradle --->>>     implementation 'io.realm:android-adapters:3.1.0'

/**
 * the number and type and order of realm results
 * that use in ordinary manner should be same in search manner
 * because all data passed from onBind view holder
 */

public abstract class BaseDBRealmRecycleAdaptor extends RecyclerView.Adapter {

    protected ArrayList<String> insertedRowIds = new ArrayList<>();//for find that this row is inserted or not and set proper view type (only for main realm collection (adapterData[0])
    protected ArrayList<String> editedRowIds = new ArrayList<>();//for find that this row is edited or not and set proper view type (only for main realm collection (adapterData[0])
    private boolean hasAutoUpdates, updateOnModification;
    private final ArrayList<OrderedRealmCollectionChangeListener> listenerList = new ArrayList<>();
    @Nullable
    private OrderedRealmCollection[] adapterData;

    /**
     * This is equivalent to {@code RealmRecyclerViewAdapter(data, autoUpdate, true)}.
     *
     * @param data       collection data to be used by this adapter.
     * @param autoUpdate when it is {@code false}, the adapter won't be automatically updated when collection data
     *                   changes.
     *                   <p>
     *                   <p>
     *                   position data[0] is main data, and other is helper data
     */
    public BaseDBRealmRecycleAdaptor(boolean autoUpdate, @Nullable OrderedRealmCollection[] data) {
        this(autoUpdate, true, data);
    }

    /**
     * @param data                 collection data to be used by this adapter.
     * @param autoUpdate           when it is {@code false}, the adapter won't be automatically updated when collection data
     *                             changes.
     * @param updateOnModification when it is {@code true}, this adapter will be updated when deletions, insertions or
     *                             modifications happen to the collection data. When it is {@code false}, only
     *                             deletions and insertions will trigger the updates. This param will be ignored if
     *                             {@code autoUpdate} is {@code false}.
     *                             <p>
     *                             <p>
     *                             position data[0] is main data, and other is helper data
     */
    public BaseDBRealmRecycleAdaptor(boolean autoUpdate,
                                     boolean updateOnModification, @Nullable OrderedRealmCollection[] data) {
        this.hasAutoUpdates = autoUpdate;
        this.updateOnModification = updateOnModification;
        this.adapterData = data;
        if (hasAutoUpdates && data != null) {
            createListenerList();
        }
    }

    private void createListenerList() {
        listenerList.clear();
        for (int i = 0; i < Objects.requireNonNull(adapterData).length; i++) {
            if (i == 0) {
                listenerList.add(createMainListener());
            } else {
                listenerList.add(createNonMainListener(i));
            }
        }
    }

    /**
     * create listener for main realm table -->>(adapterData[0])
     *
     * always range.length = 1 in my project, because change were done by socket and in one by one manner
     */
    private OrderedRealmCollectionChangeListener createMainListener() {
        return new OrderedRealmCollectionChangeListener() {
            @Override
            public void onChange(@NonNull Object collection, @NonNull OrderedCollectionChangeSet changeSet) {
                if (changeSet.getState() == OrderedCollectionChangeSet.State.INITIAL) {
                    notifyDataSetChanged();
                    return;
                }
                // For deletions, the adapter has to be notified in reverse order.(no need here ...but ...)
                OrderedCollectionChangeSet.Range[] deletions = changeSet.getDeletionRanges();
                for (int i = deletions.length - 1; i >= 0; i--) {
                    OrderedCollectionChangeSet.Range range = deletions[i];
                    notifyItemRangeRemoved(range.startIndex + dataOffset(), range.length);
                }

                OrderedCollectionChangeSet.Range[] insertions = changeSet.getInsertionRanges();
                for (OrderedCollectionChangeSet.Range range : insertions) {
                    customNotifyItemRangeInserted(range.startIndex + dataOffset(), range.length);
                    customNotifyUserForNewRow(range.startIndex + dataOffset());
                    notifyItemRangeInserted(range.startIndex + dataOffset(), range.length);
                }

                if (!updateOnModification) {
                    return;
                }

                OrderedCollectionChangeSet.Range[] modifications = changeSet.getChangeRanges();
                for (OrderedCollectionChangeSet.Range range : modifications) {
                    customNotifyItemRangeEdited(range.startIndex + dataOffset(),  range.length);
                    customNotifyUserForChangeRow(range.startIndex + dataOffset());
                    notifyItemRangeChanged(range.startIndex + dataOffset(), range.length);
                }
            }
        };
    }


    /**
     * create listener for Non main realm data -->>(adapterData[i] && i>0)
     * here we do not now that witch position of main data is changed
     * @param positionOfTable : witch non main database were be changed
     *
     *                       به دلیل اینکه پوزیشن تغییرات جداول غیر اصلی با پوزیشن سطرهای ریسایکلر برابر نیست
     *                        در اینجا چیزی نوتیفای نمیشود
     */
    private OrderedRealmCollectionChangeListener createNonMainListener(int positionOfTable) {


        return new OrderedRealmCollectionChangeListener() {
            @Override
            public void onChange(@NonNull Object collection, @NonNull OrderedCollectionChangeSet changeSet) {
                if (changeSet.getState() == OrderedCollectionChangeSet.State.INITIAL) {
                    notifyDataSetChanged();
                    return;
                }
                OrderedCollectionChangeSet.Range[] deletions = changeSet.getDeletionRanges();
                for (int i = deletions.length - 1; i >= 0; i--) {
                    OrderedCollectionChangeSet.Range range = deletions[i];
                    notifyNonMainItemAnyChanged(collection,positionOfTable,range.startIndex + dataOffset(), range.length);
                }

                OrderedCollectionChangeSet.Range[] insertions = changeSet.getInsertionRanges();
                for (OrderedCollectionChangeSet.Range range : insertions) {
                    notifyNonMainItemAnyChanged(collection,positionOfTable,range.startIndex + dataOffset(), range.length);
                }

                if (!updateOnModification) {
                    return;
                }

                OrderedCollectionChangeSet.Range[] modifications = changeSet.getChangeRanges();
                for (OrderedCollectionChangeSet.Range range : modifications) {
                    notifyNonMainItemAnyChanged(collection,positionOfTable,range.startIndex + dataOffset(), range.length);
                }
            }
        };
    }




    /**
     * attach listeners to table
     */
    private void addListener(@NonNull OrderedRealmCollection data, OrderedRealmCollectionChangeListener listener) {
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
     * detach listeners from table
     */
    private void removeListener(@NonNull OrderedRealmCollection data, OrderedRealmCollectionChangeListener listener) {
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
     * Returns the number of header elements before the Realm collection elements. This is needed so
     * all indexes reported by the {@link OrderedRealmCollectionChangeListener} can be adjusted
     * correctly. Failing to override can cause the wrong elements to animate and lead to runtime
     * exceptions.
     *
     * @return The number of header elements in the RecyclerView before the collection elements. Default is {@code 0}.
     */
    public int dataOffset() {
        return 0;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (hasAutoUpdates && isDataValid()) {
            for (int i = 0; i < Objects.requireNonNull(adapterData).length; i++) {
                addListener(adapterData[i], listenerList.get(i));
            }
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull final RecyclerView recyclerView) {
        Log.i("arash", "onDetachedFromRecyclerView: start");
        super.onDetachedFromRecyclerView(recyclerView);
        if (hasAutoUpdates && isDataValid()) {
            for (int i = 0; i < Objects.requireNonNull(adapterData).length; i++) {
                removeListener(adapterData[i], listenerList.get(i));
            }
        }
        Log.i("arash", "onDetachedFromRecyclerView: stop");

    }



    /**
     * item count is size of firs realmData in adapterData
     */
    @Override
    public int getItemCount() {
        //noinspection ConstantConditions
        if (isDataValid()) {
            Log.i("arash", "getItemCount: start" + adapterData[0].size());
        }
        return isDataValid() ? adapterData[0].size() : 0;
    }


    /**
     * Returns the item in the underlying data associated with the specified position.
     * <p>
     * This method will return {@code null} if the Realm instance has been closed or the index
     * is outside the range of valid adapter data (which e.g. can happen if {@link #getItemCount()}
     * is modified to account for header or footer views.
     * <p>
     * Also, this method does not take into account any header views. If these are present, modify
     * the {@code index} parameter accordingly first.
     *
     * @param positionOfRealmData witch realm position in adapterData
     * @param positionOfRow       witch row position in realmData
     * @return the item at the specified position or {@code null} if the position does not exists or
     * the adapter data are no longer valid.
     * یک رکورد برمیگرداند که ممکن است نال باشد
     */
    @SuppressWarnings("WeakerAccess")
    @Nullable
    protected Object getItem(int positionOfRealmData, int positionOfRow) {
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
     * Returns data associated with this adapter.
     *
     * @return adapter data. یک جدول برمیگرداند که ممکن است نال باشد
     */
    @Nullable
    protected OrderedRealmCollection getData(int positionOfRealmData) {
        return Objects.requireNonNull(adapterData)[positionOfRealmData];
    }

    /**
     * Updates the data associated to the Adapter. Useful when the query has been changed.
     * If the query does not change you might consider using the automaticUpdate feature.
     *
     * @param data the new {@link OrderedRealmCollection} to display.
     */
    @SuppressWarnings("WeakerAccess")
    protected void updateData(@Nullable OrderedRealmCollection[] data) {
        if (hasAutoUpdates) {
            if (isDataValid()) {
                for (int i = 0; i < Objects.requireNonNull(adapterData).length; i++) {
                    removeListener(adapterData[i], listenerList.get(i));
                }
            }
            adapterData = data;
            if (isDataValid()) {
                createListenerList();
                for (int i = 0; i < Objects.requireNonNull(adapterData).length; i++) {
                    addListener(adapterData[i], listenerList.get(i));
                }
            }
            injectNewDataCollectionWereDone();
            notifyDataSetChanged();
        }
    }


    private boolean isDataValid() {
        //do not use requireNotNull for adapterData
        if (adapterData != null) {
            return adapterData[0].isValid();
        }
        return false;
    }

    //----------------------------------------------------------------------------
    public void injectNewDataCollection(@Nullable OrderedRealmCollection[] data) {
        updateData(data);
    }
    //----------------------------------------------------------------------------

    /**
     * because new row maybe edited after insert, and be in two list(editedRowIds,insertedRowIds)
     * we check edited rows before inserted rows
     */
    protected int setupRowReportViewType(String guid, boolean isAccessDenied, boolean isDeleted) {
        if (isAccessDenied) {
            return CommonViewTypeReport.ACCESS_DENIED_VIEW_TYPE;
        }
        if (isDeleted) {
            return CommonViewTypeReport.DELETED_VIEW_TYPE;
        }
        if (editedRowIds.contains(guid)) {
            return CommonViewTypeReport.EDITED_VIEW_TYPE;
        }
        if (insertedRowIds.contains(guid)) {
            return CommonViewTypeReport.INSERTED_VIEW_TYPE;
        }
        return CommonViewTypeReport.NORMAL_VIEW_TYPE;
    }
    //----------------------------------------------------------------------------

    protected abstract void customNotifyItemRangeEdited(int startPosition, int length);

    protected abstract void customNotifyItemRangeInserted(int startPosition, int length);

    protected abstract void customNotifyUserForNewRow(int position);

    protected abstract void customNotifyUserForChangeRow(int position);

    protected abstract void injectNewDataCollectionWereDone();

    protected abstract void notifyNonMainItemAnyChanged(Object collection, int positionOfChangedTable, int startPosition, int length);




}
