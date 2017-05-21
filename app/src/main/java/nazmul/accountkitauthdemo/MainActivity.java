package nazmul.accountkitauthdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN);
        // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        configurationBuilder.setDefaultCountryCode("BD");
        configurationBuilder.setReadPhoneStateEnabled(true);
        configurationBuilder.setReceiveSMS(true);
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
                showErrorActivity(loginResult.getError());
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                } else {
                    toastMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode().substring(0, 10));
                }

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.

                // Success! Start your next activity...
                goToMyLoggedInActivity();
            }
            AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                @Override
                public void onSuccess(final Account account) {
                    String accountKitId = account.getId();
                    String phoneNumber = account.getPhoneNumber().toString();
                    Toast.makeText(MainActivity.this, ""+phoneNumber, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(final AccountKitError error) {
                    Log.e("fail: ", "onError: "+error.toString() );
                    Toast.makeText(MainActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                    // Handle Error
                }
            });

            // Surface the result to your user in an appropriate way.
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
        }
    }

    private void goToMyLoggedInActivity() {
        Intent intent=new Intent(MainActivity.this,ActivityAfterLogin.class);
        startActivity(intent);

    }

    private void showErrorActivity(AccountKitError error) {
        Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
    }
}
