package org.evreyatlanta.sidur;

import android.view.View;

public interface OnAdapterItemBindListener<T> {
	public Object bind(View view, T item);
}
