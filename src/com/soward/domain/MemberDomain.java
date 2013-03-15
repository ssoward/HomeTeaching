
/**
 * Title: MemberDomain.java
 * Description: HomeTeaching
 * Date: Jun 28, 2007
 * Copyright: Copyright (c) 2007, Soward Inc.
 * @author Scott Soward
 * */
package com.soward.domain;

import java.util.ArrayList;

public class MemberDomain {
	public static final String ELDER = "0001";
	public static final String HIGHPRIEST = "0002";
	public static final String DEACON = "0003";
	public static final String TEACHER = "0004";
	public static final String PRIEST = "0005";
	public static final String UNORDAINED = "0006";
	public String domainVal;
	public String domainType;

	public MemberDomain(){}
	// ordering list based on current memdomain type, returns list with
	// variable memDom as first in list.
	public ArrayList<MemberDomain> getList(String memDom){
		ArrayList<MemberDomain> fd = getAllList();//new ArrayList<MemberDomain>();
		MemberDomain currMemDom = new MemberDomain();
		currMemDom.domainVal = memDom;
		currMemDom.domainType = getDomainTypeForVal(memDom);
		//put current memdomain at the front
		fd.add(0,currMemDom);
		return fd;

	}
	private String getDomainTypeForVal(String memDom) {
		String domType = "";
		if(memDom!=null){
			if(memDom.equalsIgnoreCase(ELDER)){
				domType = "ELDER";
			}
			else if(memDom.equalsIgnoreCase(HIGHPRIEST)){
				domType = "HIGHPRIEST";
			}
			else if(memDom.equalsIgnoreCase(DEACON)){
				domType = "DEACON";
			}
			else if(memDom.equalsIgnoreCase(UNORDAINED)){
				domType = "UNORDAINED";
			}
		}
		return domType;
	}
	public ArrayList<MemberDomain> getAllList(){
		ArrayList<MemberDomain> fd = new ArrayList<MemberDomain>();
		MemberDomain temp0 = new MemberDomain();
		MemberDomain temp1 = new MemberDomain();
		MemberDomain temp2 = new MemberDomain();
		MemberDomain temp3 = new MemberDomain();
		MemberDomain temp4 = new MemberDomain();
		MemberDomain temp5 = new MemberDomain();
		temp0.domainType = "ELDER";
		temp1.domainType = "HIGHPRIEST";
		temp2.domainType = "DEACON";
		temp3.domainType = "TEACHER";
		temp4.domainType = "PRIEST";
		temp5.domainType = "UNORDAINED";

		temp0.domainVal = ELDER;
		temp1.domainVal = HIGHPRIEST;
		temp2.domainVal = DEACON;
		temp3.domainVal = TEACHER;
		temp4.domainVal = PRIEST;
		temp5.domainVal = UNORDAINED;
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
}
