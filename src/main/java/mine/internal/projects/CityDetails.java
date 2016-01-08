package mine.internal.projects;

import com.fasterxml.jackson.annotation.JsonSetter;

import mine.internal.projects.converter.Getter;

public class CityDetails {
	private long id = -1;
	private String key = null;
	private String name = null;
	private String fullName = null;
	private String iataAirportCode = null;
	private String type = null;
	private String country = null;
	private GeoPosition geoPositionDetails = null;
	private long locationId = -1;
	private boolean inEurope = false;
	private String countryCode = null;
	private boolean coreCountry = false;
	private Long distance = null;

	public CityDetails() {
	}

	@Getter
	public long getId() {
		return id;
	}

	@JsonSetter(value="_id")
	public void setId(long id) {
		this.id = id;
	}
	
	@Getter
	public String getKey() {
		return key;
	}
	
	@JsonSetter(value="key")
	public void setKey(String key) {
		this.key = key;
	}

	@Getter
	public String getName() {
		return name;
	}

	@JsonSetter(value="name")
	public void setName(String name) {
		this.name = name;
	}

	@Getter
	public String getFullName() {
		return fullName;
	}

	@JsonSetter(value="fullName")
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Getter
	public String getIataAirportCode() {
		return iataAirportCode;
	}

	@JsonSetter(value="iata_airport_code")
	public void setIataAirportCode(String iataAirportCode) {
		this.iataAirportCode = iataAirportCode;
	}

	@Getter
	public String getType() {
		return type;
	}

	@JsonSetter(value="type")
	public void setType(String type) {
		this.type = type;
	}

	@Getter
	public String getCountry() {
		return country;
	}
	
	@JsonSetter(value="country")
	public void setCountry(String country) {
		this.country = country;
	}

	@Getter
	public GeoPosition getGeoPositionDetails() {
		return geoPositionDetails;
	}

	@JsonSetter(value="geo_position")
	public void setGeoPositionDetails(GeoPosition geoPositionDetails) {
		this.geoPositionDetails = geoPositionDetails;
	}

	@Getter
	public long getLocationId() {
		return locationId;
	}

	@JsonSetter(value="locationId")
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	@Getter
	public boolean isInEurope() {
		return inEurope;
	}

	@JsonSetter(value="inEurope")
	public void setInEurope(boolean inEurope) {
		this.inEurope = inEurope;
	}

	@Getter
	public String getCountryCode() {
		return countryCode;
	}

	@JsonSetter(value="countryCode")
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Getter
	public boolean isCoreCountry() {
		return coreCountry;
	}

	@JsonSetter(value="coreCountry")
	public void setCoreCountry(boolean coreCountry) {
		this.coreCountry = coreCountry;
	}

	@Getter
	public Long getDistance() {
		return distance;
	}

	@JsonSetter(value="distance")
	public void setDistance(Long distance) {
		this.distance = distance;
	}

}
