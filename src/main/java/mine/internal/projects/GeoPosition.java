package mine.internal.projects;

import com.fasterxml.jackson.annotation.JsonSetter;

import mine.internal.projects.converter.Getter;

public class GeoPosition {
	private double latitude = 0.0;
	private double longtitude = 0.0;

	public GeoPosition() {
	}

	@Getter
	public double getLatitude() {
		return latitude;
	}

	@JsonSetter("latitude")
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Getter
	public double getLongtitude() {
		return longtitude;
	}

	@JsonSetter("longitude")
	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

}
