package ir.fararayaneh.erp.IBase.common_base.form_base;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import android.util.AttributeSet;

import java.util.ArrayList;

import ir.fararayaneh.erp.IBase.common_base.BaseActivity;
import ir.fararayaneh.erp.IBase.common_base.BaseViewWithRecycle;
import ir.fararayaneh.erp.IBase.common_base.IClickCollectionDataListener;
import ir.fararayaneh.erp.R;

/**
 * for those forms that get data from user (Time , task,....)
 */
public abstract class BaseViewCollectionDataForm extends BaseViewWithRecycle {

    protected IClickCollectionDataListener collectionDataListener;
    protected GridLayoutManager gridLayoutManager;
    public void setCollectionDataListner(IClickCollectionDataListener collectionDataListener) {
        this.collectionDataListener = collectionDataListener;
    }

    public BaseViewCollectionDataForm(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseViewCollectionDataForm(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected BaseViewCollectionDataForm(@NonNull BaseActivity activity) {
        super(activity);
    }


    @Override
    protected void setupRecyclers() {
       /*چون میخواهیم ریسایکل به صورت کاستوم و از پرزنتر ست شود
        اینجا کاری انجام نمیدهیم
        دقت شود که متد
         setupCustomLayoutManager()
        حتما قبل از
        setRecyclerAdaptor
        از پرزنتر صدا زده شود
       */
    }

    public GridLayoutManager getLayoutManager() {
        return gridLayoutManager;
    }

    public abstract void setupCustomLayoutManager(int maximumSpanInOneRow, int nonMachParentRowSpanCount, ArrayList<Integer> positionOfNonMachParentRow);

    public abstract void setupStairCustomLayoutManager(ArrayList<Integer> positionOfQuarterRow,ArrayList<Integer> positionOfThirdRow,ArrayList<Integer> positionOfHalfRow);

    public abstract void showHideBTNDelete(boolean showHide);


}
