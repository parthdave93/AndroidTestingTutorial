package com.testingandroid.login;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.testingandroid.R;
import com.testingandroid.databinding.ActivityLoginBinding;
import com.testingandroid.model.LoginModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    LoginModel loginModel;
    ActivityLoginBinding activityLoginBinding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginModel = new LoginModel();
    
        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        activityLoginBinding.setLoginModel(loginModel);
    }
    
    public void onLoginClick(View view){
        if (checkBlank(loginModel.username)) {
            setError(activityLoginBinding.edUsername, getString(R.string.msg_enter_valid_email));
        } else if (!isValidEmail(activityLoginBinding.edUsername)) {
            setError(activityLoginBinding.edUsername, getString(R.string.msg_enter_valid_email));
            return;
        } else if (checkBlank(loginModel.password)) {
            unSetErrorRight(activityLoginBinding.edUsername, R.drawable.ic_email);
            setError(activityLoginBinding.edPassword, getString(R.string.msg_enter_valid_password));
        } else if (loginModel.password.get().length() < 6) {
            unSetErrorRight(activityLoginBinding.edUsername, R.drawable.ic_email);
            setError(activityLoginBinding.edPassword, getString(R.string.msg_enter_valid_password));
        } else {
            unSetErrorRight(activityLoginBinding.edUsername, R.drawable.ic_email);
            unSetErrorRight(activityLoginBinding.edPassword, R.drawable.ic_password);
           /* if (!noInternetConnection()) {
                loginPresenter = new LoginPresenter(this, this);
                loginPresenter.requestLogin(loginModel, false);
            }*/
        }
    }
    
    public boolean checkBlank(ObservableField<String> str) {
        return str == null ? true : str.get() == null ? true : str.get().trim().length() > 0 ? false : true;
    }
    
    public void setError(EditText editText, String str) {
        editText.requestFocus();
        editText.setError(str);
    }
    
    public void unSetErrorRight(EditText editText, @DrawableRes int resId) {
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, resId, 0);
    }
    
    public boolean isValidEmail(EditText editText) {
        
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
        Matcher matcher = pattern.matcher(editText.getText().toString());
        
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean noInternetConnection(){
        return false;
    }
    
}
