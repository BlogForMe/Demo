package androidx.recyclerview.usbu.usb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.usbu.R;
import androidx.recyclerview.usbu.card.CardManager;

import android.os.Bundle;


public class USBReadActivity extends AppCompatActivity {

    private CardManager mCardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_s_b_read);

        mCardManager = new CardManager();
        mCardManager.init(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCardManager.destroy();
    }
}
