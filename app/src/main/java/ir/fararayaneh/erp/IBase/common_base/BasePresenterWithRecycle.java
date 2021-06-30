package ir.fararayaneh.erp.IBase.common_base;

import android.content.Context;

import java.lang.ref.WeakReference;

import ir.fararayaneh.erp.data.models.middle.IntentModel;


public abstract class BasePresenterWithRecycle<V extends BaseViewWithRecycle> extends BasePresenter<V> {

    public BasePresenterWithRecycle(WeakReference<V> weekView, Context context, boolean shouldHaveIntent) {
        super(weekView, context, shouldHaveIntent);
    }

    @Override
    public void onCreate(IntentModel intentModel) {
        setRecyclAdaptor();
        workForLoadPage();
        setLazyLoad();
    }

    protected abstract void setRecyclAdaptor();

    protected abstract void workForLoadPage();

    protected abstract void setLazyLoad();
}
