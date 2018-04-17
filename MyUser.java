package edu.fsu.cs.mobile.safereader;

import java.util.Date;
import java.util.HashMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yannes on 7/10/17.
 */

public class MyUser {

    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_LASTLOGIN = "lastlogin";

    private String mUserName;
    private String mUserEmail;
    private Date mLastLogin;

    public MyUser() {}

    public MyUser(String username, String email, Date lastLogin) {
        mUserName = username;
        mUserEmail = email;
        mLastLogin = lastLogin;
    }

    public MyUser(Map<String, Object> map) {
        mUserName = (String) map.get(USER_NAME);
        mUserEmail = (String) map.get(USER_EMAIL);
        mLastLogin = new Date((long) map.get(USER_LASTLOGIN));
    }

    public void setUserName(String username) {
        mUserName = username;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserEmail(String email) {
        mUserEmail = email;
    }

    public String getUserEmail() {
        return mUserEmail;
    }

    public void setLastLogin(Date lastLogin) {
        mLastLogin = lastLogin;
    }

    public Date getLastLogin() {
        return mLastLogin;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(USER_NAME, mUserName);
        result.put(USER_EMAIL, mUserEmail);
        result.put(USER_LASTLOGIN, mLastLogin.getTime());

        return result;
    }

    public static MyUser fromDataSnapshot(DataSnapshot userSnapshot) {
        String username = (String) userSnapshot.child(USER_NAME).getValue();
        String email = (String) userSnapshot.child(USER_EMAIL).getValue();
        long lastLogin = (long) userSnapshot.child(USER_LASTLOGIN).getValue();

        return new MyUser(username, email, new Date(lastLogin));
    }
}
