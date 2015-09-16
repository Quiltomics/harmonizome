package edu.mssm.pharm.maayanlab.Harmonizome.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import edu.mssm.pharm.maayanlab.Harmonizome.net.UrlCodec;

@Entity
@Table(name = "attribute")
public class Attribute implements BioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	/* From datasets
	 * ------------- */
	@Column(name = "name_from_dataset")
	private String nameFromDataset;
	
	@Column(name = "id_from_dataset")
	private String idFromDataset;
	
	@Column(name = "description_from_dataset")
	@Type(type = "text")
	private String descriptionFromDataset;

	@Column(name = "url_from_dataset", length = 2083)
	private String urlFromDataset;
	
	/* From naming authority
	 * --------------------- */
	@Column(name = "name_from_naming_authority")
	private String nameFromNamingAuthority;
	
	@Column(name = "id_from_naming_authority")
	private String idFromNamingAuthority;
	
	@Column(name = "description_from_naming_authority")
	@Type(type = "text")
	private String descriptionFromNamingAuthority;

	@Column(name = "url_from_naming_authority", length = 2083)
	private String urlFromNamingAuthority;
	
	/* Foreign key relationships
	 * ------------------------- */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "attribute_type_fk")
	private AttributeType attributeType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "naming_authority_fk")
	private NamingAuthority namingAuthority;

	/* Utilities
	 * --------- */
	public static final String ENDPOINT = "attribute";
	
	public Attribute() {
	}

	public int getId() {
		return id;
	}

	public String getNameFromDataset() {
		return nameFromDataset;
	}

	public void setNameFromDataset(String nameFromDataset) {
		this.nameFromDataset = nameFromDataset;
	}

	public String getIdFromDataset() {
		return idFromDataset;
	}

	public void setIdFromDataset(String idFromDataset) {
		this.idFromDataset = idFromDataset;
	}

	public String getDescriptionFromDataset() {
		return descriptionFromDataset;
	}

	public void setDescriptionFromDataset(String descriptionFromDataset) {
		this.descriptionFromDataset = descriptionFromDataset;
	}

	public String getUrlFromDataset() {
		return urlFromDataset;
	}

	public void setUrlFromDataset(String urlFromDataset) {
		this.urlFromDataset = urlFromDataset;
	}

	public String getNameFromNamingAuthority() {
		return nameFromNamingAuthority;
	}

	public void setNameFromNamingAuthority(String nameFromNamingAuthority) {
		this.nameFromNamingAuthority = nameFromNamingAuthority;
	}

	public String getIdFromNamingAuthority() {
		return idFromNamingAuthority;
	}

	public void setIdFromNamingAuthority(String idFromNamingAuthority) {
		this.idFromNamingAuthority = idFromNamingAuthority;
	}

	public String getDescriptionFromNamingAuthority() {
		return descriptionFromNamingAuthority;
	}

	public void setDescriptionFromNamingAuthority(String descriptionFromNamingAuthority) {
		this.descriptionFromNamingAuthority = descriptionFromNamingAuthority;
	}

	public String getUrlFromNamingAuthority() {
		return urlFromNamingAuthority;
	}

	public void setUrlFromNamingAuthority(String urlFromNamingAuthority) {
		this.urlFromNamingAuthority = urlFromNamingAuthority;
	}

	public AttributeType getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(AttributeType attributeType) {
		this.attributeType = attributeType;
	}

	public NamingAuthority getNamingAuthority() {
		return namingAuthority;
	}

	public void setNamingAuthority(NamingAuthority namingAuthority) {
		this.namingAuthority = namingAuthority;
	}

	/* Utility functions
	 * ----------------- */
	@Transient
	public String getKey() {
		return "name";
	}
	
	@Transient
	public String getValue() {
		return nameFromDataset;
	}
	
	@Transient
	public String getUrlEncodedValue() {
		return UrlCodec.encode(nameFromDataset);
	}

	@Transient
	public String getEndpoint() {
		return ENDPOINT;
	}
}
