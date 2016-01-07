package org.evreyatlanta.controls;

import android.view.View;

public interface OnAdapterItemBindListener<T> {
	public Object bind(View view, T item);
}
