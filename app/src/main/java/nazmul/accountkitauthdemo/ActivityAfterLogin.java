package nazmul.accountkitauthdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;

public class ActivityAfterLogin extends AppCompatActivity {
    TextView phoneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        phoneText= (TextView) findViewById(R.id.phoneText);

        AccessToken accessToken = AccountKit.getCurrentAccessToken();

        if (accessToken != null) {
            AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                @Override
                public void onSuccess(final Account account) {
                    String accountKitId = account.getId();
                    String phoneNumber = account.getPhoneNumber().toString();
                    phoneText.setText(""+phoneNumber +" "+accountKitId);
                    Toast.makeText(ActivityAfterLogin.this, ""+phoneNumber, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(final AccountKitError error) {
                    // Handle Error
                }
            });
            //Handle Returning User
        } else {
            //Handle new or logged out user
        }

    }
}
