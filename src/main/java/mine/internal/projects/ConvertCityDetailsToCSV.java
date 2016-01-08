package mine.internal.projects;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import mine.internal.projects.converter.BeanToCSVConverter;
import mine.internal.projects.converter.CommonException;

public class ConvertCityDetailsToCSV {
	private static final Logger LOGGER = Logger.getGlobal();
	private static final String API_URL = "http://api.goeuro.com/api/v2/position/suggest/en";

	protected ConvertCityDetailsToCSV() {
	}

	public static void main(String[] args) {
		try {
			LOGGER.info("Execution has started");
			if (args.length != 0) {
				final String cityName = args[0];
				List<CityDetails> cityDetails = fetchCityDetails(cityName);
				if (cityDetails.isEmpty()) {
					LOGGER.severe("No Cities where found");
					return;
				}
				LOGGER.info("Number of city entries: " + cityDetails.size());
				BeanToCSVConverter beanToCSVConverter = new BeanToCSVConverter(',', "");
				LOGGER.info(beanToCSVConverter.parseObjectHeaderAsCSV(cityDetails.get(0)));
				beanToCSVConverter.convertToCSVFile(cityDetails, "CityDetails_" + cityName + ".csv");
			} else {
				LOGGER.info("Please specify a city");
			}
			LOGGER.info("Execution has been completed Successfully");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Execution Failure", e);
		}
	}

	private static List<CityDetails> fetchCityDetails(String cityName) {
		if (!validateCityName(cityName)) {
			throw new CommonException("Invalid charcters found in the City Name. Only alphabets are allowed");
		}
		LOGGER.info("City Name: " + cityName);
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonJsonProvider.class);
		Client rsClient = ClientBuilder.newClient(clientConfig);
		WebTarget webTarget = rsClient.target(API_URL).path(cityName);
		Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
		final int responseStatus = response.getStatus();
		if (responseStatus != 200) {
			LOGGER.severe("Response Data: " + response.readEntity(String.class));
			throw new CommonException("Problem while fetching the city details for the city: " + cityName + "\nResponse Code: " + responseStatus);
		} else {
			LOGGER.info("Request was succesful");
		}
		return response.readEntity(new GenericType<List<CityDetails>>() {
		});
	}

	private static boolean validateCityName(String cityName) {
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		Matcher matcher = pattern.matcher(cityName);
		return matcher.matches();
	}

}
