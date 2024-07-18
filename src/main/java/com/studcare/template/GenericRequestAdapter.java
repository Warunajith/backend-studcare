package com.studcare.template;

public interface GenericRequestAdapter<T1, T2> {
	T2 adapt(T1 requestDTO);
}
