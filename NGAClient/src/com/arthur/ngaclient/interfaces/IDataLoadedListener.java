package com.arthur.ngaclient.interfaces;

public interface IDataLoadedListener {
	public void onPostFinished(Object obj);

	public void onPostError(Integer status);
}
