package ir.fararayaneh.erp.IBase.common_base;


public interface IWeekViews<V extends BaseView> {
     V getView() ;
     boolean checkNull();
}
