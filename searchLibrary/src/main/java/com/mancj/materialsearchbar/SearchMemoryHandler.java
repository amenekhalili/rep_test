package com.mancj.materialsearchbar;


import java.util.LinkedList;


public class SearchMemoryHandler {

    private static LinkedList<String> memoryList = new LinkedList<>();

    public static void addSearchValue(String searchValue) {
        if(memoryList.contains(searchValue)){
           return;
        }
        memoryList.addFirst(searchValue);
    }

    public static LinkedList<String> getSearchList(){
        return memoryList;
    }

}
