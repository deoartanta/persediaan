package com.persediaan.de.data;

public interface ManualBookListener {
    public void onNext(int index);
    public void onLewati();
    public void onFinish(int index);
}
