package com.testingandroid.model;

import android.databinding.Observable;
import android.databinding.ObservableField;

/**
 * Created by Parth Dave on 15/3/17.
 * Spaceo Technologies Pvt Ltd.
 * parthd.spaceo@gmail.com
 */

public class LoginModel {
    public ObservableField<String> username = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
}
