package com.cincom.plato.rule;

import com.cincom.plato.Data;

public interface IBaseRule
{
	void selected(Data data);
	Boolean isRelated(Data data1, Data data2);
	void relate(Data data1, Data data2);
}
