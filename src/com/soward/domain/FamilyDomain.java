
/**
 * Title: FamilyDomain.java
 * Description: HomeTeaching
 * Date: Jun 28, 2007
 * Copyright: Copyright (c) 2007, Soward Inc.
 * @author Scott Soward
 * */
package com.soward.domain;

import java.util.ArrayList;

public class FamilyDomain {
//	private static DomainContr 
	public static final String FATHER = "0001";
	public static final String MOTHER = "0002";
	public static final String SON = "0003";
	public static final String DAUGHTER = "0004";
	public static final String UNCLE = "0005";
	public static final String AUNT = "0006";
	public String domainVal;
	public String domainType;

	public FamilyDomain(){}
	public ArrayList<FamilyDomain> getList(String currFamVal){
		ArrayList<FamilyDomain> fd = new ArrayList<FamilyDomain>();
		FamilyDomain temp0 = new FamilyDomain();
		FamilyDomain temp1 = new FamilyDomain();
		FamilyDomain temp2 = new FamilyDomain();
		FamilyDomain temp3 = new FamilyDomain();
		FamilyDomain temp4 = new FamilyDomain();
		FamilyDomain temp5 = new FamilyDomain();
		FamilyDomain currFamDom = new FamilyDomain();
		currFamDom.domainVal = currFamVal;
		currFamDom.domainType = getDomainTypeForVal(currFamVal);
		temp0.domainType = "FATHER";
		temp1.domainType = "MOTHER";
		temp2.domainType = "SON";
		temp3.domainType = "DAUGHTER";
		temp4.domainType = "UNCLE";
		temp5.domainType = "AUNT";
		temp0.domainVal = FATHER;
		temp1.domainVal = MOTHER;
		temp2.domainVal = SON;
		temp3.domainVal = DAUGHTER;
		temp4.domainVal = UNCLE;
		temp5.domainVal = AUNT;
		fd.add(currFamDom);
		fd.add(temp0);
		fd.add(temp1);
		fd.add(temp2);
		fd.add(temp3);
		fd.add(temp4);
		fd.add(temp5);
		return fd;

	}
	private String getDomainTypeForVal(String memDom) {
		String domType = "";
		if(memDom!=null){
			if(memDom.equalsIgnoreCase(FATHER)){domType = "FATHER";}
			if(memDom.equalsIgnoreCase(MOTHER)){domType = "MOTHER";}
			if(memDom.equalsIgnoreCase(SON)){domType = "SON";}
			if(memDom.equalsIgnoreCase(DAUGHTER)){domType = "DAUGHTER";}
			if(memDom.equalsIgnoreCase(UNCLE)){domType = "UNCLE";}
			if(memDom.equalsIgnoreCase(UNCLE)){domType = "UNCLE";}
		}
		return domType;
	}
	public ArrayList<FamilyDomain> getAllList(){
		ArrayList<FamilyDomain> fd = new ArrayList<FamilyDomain>();
		FamilyDomain temp0 = new FamilyDomain();
		FamilyDomain temp1 = new FamilyDomain();
		FamilyDomain temp2 = new FamilyDomain();
		FamilyDomain temp3 = new FamilyDomain();
		FamilyDomain temp4 = new FamilyDomain();
		FamilyDomain temp5 = new FamilyDomain();
		temp0.domainType = "FATHER";
		temp1.domainType = "MOTHER";
		temp2.domainType = "SON";
		temp3.domainType = "DAUGHTER";
		temp4.domainType = "UNCLE";
		temp5.domainType = "AUNT";
		temp0.domainVal = FATHER;
		temp1.domainVal = MOTHER;
		temp2.domainVal = SON;
		temp3.domainVal = DAUGHTER;
		temp4.domainVal = UNCLE;
		temp5.domainVal = AUNT;
		fd.add(temp0);
		fd.add(temp1);
		fd.add(temp2);
		fd.add(temp3);
		fd.add(temp4);
		fd.add(temp5);
		return fd;

	}
	/**
	 * @return the domainType
	 */
	public String getDomainType() {
		return domainType;
	}
	/**
	 * @param domainType the domainType to set
	 */
	public void setDomainType(String domainType) {
		this.domainType = domainType;
	}
	/**
	 * @return the domainVal
	 */
	public String getDomainVal() {
		return domainVal;
	}
	/**
	 * @param domainVal the domainVal to set
	 */
	public void setDomainVal(String domainVal) {
		this.domainVal = domainVal;
	}
	public static void main(String args[]){

	}
}
