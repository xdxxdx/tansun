package com.xdx.demo.cache;

import java.io.Serializable;

public interface ICache<T> {
    T getItem(String key) ;
    void setItem(String key, T item) ;
}
