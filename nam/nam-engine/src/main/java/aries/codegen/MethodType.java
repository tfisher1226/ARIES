package aries.codegen;



public enum MethodType {
	GetAllAsList,
	GetAllAsMap,
	GetAsItem,
	GetAsItemById,
	GetAsListByIds,
	GetAsItemByKey,
	GetAsListByKeys,
	GetAsListByCriteria,
	GetAsList,
	GetAsMapByKeys,
	GetMatchingAsList,
	GetMatchingAsMap,
	Set,
	Unset,
	AddAsItem,
	AddAsList,
	AddAsMap,
	RemoveAsItem,
	RemoveAsItemById,
	RemoveAsListByIds,
	RemoveAsItemByKey,
	RemoveAsListByKeys,
	RemoveAsListByCriteria,
	RemoveAsList,
	//RemoveAsMap,
	RemoveMatchingAsList,
	RemoveAll;
	
	public boolean isReadOperation() { 
		return name().startsWith("Get"); 
	}

	public boolean isWriteOperation() { 
		return !isReadOperation(); 
	}

}
