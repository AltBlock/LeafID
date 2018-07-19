package com.surya.loginTest.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.assertj.core.internal.Doubles;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

@Document(collection = "plant")

public class Plant {
	
	@Id
	private String id;
	
	private String imageUrl;
	private String functionPoint;	
	private String kingdom;
	private String subKingdom;
	private String superDivision;
	private String division;
	private String phylum;
	private String className;
	private String subClass;
	private String order;
	private String family;
	private String genus;
	private String species;
	private String commonName;
	private String description;
	private double[] distanceDifferenceFromMean;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getFunctionPoint() {
		return functionPoint;
	}
	public void setFunctionPoint(String functionPoint) {
		this.functionPoint = functionPoint;
	}
	public String getKingdom() {
		return kingdom;
	}
	public void setKingdom(String kingdom) {
		this.kingdom = kingdom;
	}
	public String getSubKingdom() {
		return subKingdom;
	}
	public void setSubKingdom(String subKingdom) {
		this.subKingdom = subKingdom;
	}
	public String getSuperDivision() {
		return superDivision;
	}
	public void setSuperDivision(String superDivision) {
		this.superDivision = superDivision;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getPhylum() {
		return phylum;
	}
	public void setPhylum(String phylum) {
		this.phylum = phylum;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSubClass() {
		return subClass;
	}
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getGenus() {
		return genus;
	}
	public void setGenus(String genus) {
		this.genus = genus;
	}
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Double> getDistanceDifferenceFromMean() {
		return new LinkedList (Arrays.asList(this.distanceDifferenceFromMean));
	}
	public void setDistanceDifferenceFromMean(List<Double> distanceDifferenceFromMean) {
		this.distanceDifferenceFromMean=new double[distanceDifferenceFromMean.size()];
		 for (int i = 0; i < this.distanceDifferenceFromMean.length; i++) {
			 this.distanceDifferenceFromMean[i] = distanceDifferenceFromMean.get(i).doubleValue();  // java 1.4 style
			 }
	}
	@Override 
	public String toString()
	{
		return this.commonName+" "+this.className;
	}
	
}
