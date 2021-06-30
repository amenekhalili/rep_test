package ir.fararayaneh.erp.dagger;


import javax.inject.Singleton;
import dagger.Component;
import ir.fararayaneh.erp.IBase.common_base.BaseView;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.dagger.modules.PicasoModule;

/**
 * for all injection to Baseview
 */

@Component(modules = { PicasoModule.class})
//@Singleton
public interface Appcomponent3 {
    void injectBaseView(BaseView baseView);
    void injectBaseRecHolder(BaseRecHolder baseRecHolder);
}
