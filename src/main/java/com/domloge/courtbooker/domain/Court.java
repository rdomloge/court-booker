package com.domloge.courtbooker.domain;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Court {
	
	/*
	 * [{"Available":1,"SectorNames":["Court 1"],"SectorReference":"16","Sequence":1},
	 * {"Available":1,"SectorNames":["Court 2"],"SectorReference":"17","Sequence":2}]
	 */

	@JsonProperty("SectorNames")
	private String[] sectorName;
	
	@JsonProperty("SectorReference")
	private String sectorReference;
	
	@JsonProperty("Sequence")
	private int sequence;
	
	@JsonProperty("Available")
	private int available;


	public String[] getSectorName() {
		return sectorName;
	}

	public void setSectorName(String[] sectorName) {
		this.sectorName = sectorName;
	}

	public String getSectorReference() {
		return sectorReference;
	}

	public void setSectorReference(String sectorReference) {
		this.sectorReference = sectorReference;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "Court [sectorName=" + Arrays.toString(sectorName) + ", sectorReference=" + sectorReference
				+ ", sequence=" + sequence + ", available=" + available + "]";
	}

	
}
