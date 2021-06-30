package ir.fararayaneh.erp.IBase.common_base;

import io.reactivex.disposables.Disposable;


public interface IDisposable {
    void addDisposable(Disposable disposable);
    void clearDisposable() ;
}

