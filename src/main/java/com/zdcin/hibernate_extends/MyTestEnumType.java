package com.zdcin.hibernate_extends;

import com.mysql_hibernate_special_type.hibernate_extends.AbstractEnumType;
import com.zdcin.entity.TestEachType.TestEnum;

public class MyTestEnumType extends AbstractEnumType<TestEnum>{

	@Override
	public Class<TestEnum> returnedClass() {
		return TestEnum.class;
	}

	@Override
	protected Object valueOf(String cellContent) {
		return TestEnum.valueOf(cellContent);
	}

	@Override
	protected String name(TestEnum value) {
		return value.name();
	}
}
