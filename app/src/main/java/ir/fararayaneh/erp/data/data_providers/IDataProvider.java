package ir.fararayaneh.erp.data.data_providers;


import ir.fararayaneh.erp.data.models.IModels;

public interface IDataProvider<T> {
    T  data(IModels iModels);
}
