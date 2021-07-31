package androidx.recyclerview.usbu.card;

import android.content.Context;

/**
 * Created by Administrator on 2018/7/23.
 */

public interface CardInterface {

    void init(Context context);

    void openDevice();

    void colseDevice();

    void readCardInfo();


}
