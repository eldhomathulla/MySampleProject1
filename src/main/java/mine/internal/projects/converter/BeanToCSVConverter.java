package mine.internal.projects.converter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class provide functionalities for converting a java bean(s) to its CSV equivalent
 * @author Eldho Mathulla
 *
 */
public class BeanToCSVConverter {
	private char delimiter = ',';
	private Map<Class, List<Method>> methodListCache = new HashMap<Class, List<Method>>();
	private String objectName = "__";
	private static final Logger LOGGER = Logger.getGlobal();

	public BeanToCSVConverter() {
	}

	/**
	 * 
	 * @param delimiter the delimter for the outpuut value
	 * @param objectName the object name if any, else leave it as ""
	 */
	public BeanToCSVConverter(char delimiter, String objectName) {
		this.delimiter = delimiter;
		this.objectName = objectName;
	}

	/**
	 * Converts a list of objects to csv and write it into a file
	 * @param objectList the list of objects
	 * @param fileName the file name of output csv file
	 */
	public <T> void convertToCSVFile(List<T> objectList, String fileName) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));) {
			if (objectList.isEmpty()) {
				throw new CommonException("Empty List supplied. CSV has not been generated");
			}
			writer.write(parseObjectHeaderAsCSV(objectList.get(0)) + "\n");
			objectList.forEach((T object) -> {
				try {
					writer.write(parseObjectValuesAsCSV(object) + "\n");
				} catch (Exception e) {
					throw new CommonException("Exception encountered while handling the object " + object, e);
				}
			});
		} catch (IOException e) {
			throw new CommonException(e);
		}
	}

	/**
	 * Converts a single object into its CSV equivalent
	 * @param obj
	 * @return
	 */
	public String parseObjectValuesAsCSV(Object obj) {
		LOGGER.info("Parsing the object");
		List<Object> values = parseValues(obj);
		LOGGER.info("Converting the object fields to csv values");
		return convertListToCSVValues(values);
	}

	/**
	 * parses the header info from the give object
	 * @param obj
	 * @return
	 */
	public String parseObjectHeaderAsCSV(Object obj) {
		List<Object> headerValues = parseHeaderInfo(obj);
		return convertListToCSVValues(headerValues);
	}

	private String convertListToCSVValues(List<Object> values) {
		String csvValues = "";
		if (!values.isEmpty()) {
			Iterator<Object> valueIterator = values.iterator();
			csvValues = "\"" + valueIterator.next() + "\"";
			while (valueIterator.hasNext()) {
				csvValues = csvValues + delimiter + "\"" + valueIterator.next() + "\"";
			}
		}
		return csvValues;
	}

	private void parseValues(Object object, List<Object> csvElements) {
		if (object == null) {
			csvElements.add(null);
			return;
		}
		Class objectClass = object.getClass();
		List<Method> getterMethods = parseGetterMehtods(objectClass);
		if (getterMethods.isEmpty()) {
			csvElements.add(object);
		} else {
			getterMethods.forEach((Method getterMethod) -> {
				try {
					parseValues(getterMethod.invoke(object), csvElements);
				} catch (Exception e) {
					logMethodInvocationExceptions(object, getterMethod, e);
				}
			});
		}
	}

	private List<Object> parseValues(Object object) {
		List<Object> values = new LinkedList<>();
		parseValues(object, values);
		return values;
	}

	private void logMethodInvocationExceptions(Object object, Method getterMethod, Exception e) {
		throw new CommonException("Error while invoking method : " + getterMethod.getName() + " on the class type: " + object.getClass().getName(), e);
	}

	private List<Method> parseGetterMehtods(Class objectClass) {
		List<Method> getterMethods;
		if (methodListCache.containsKey(objectClass)) {
			getterMethods = methodListCache.get(objectClass);
		} else {
			Method[] methods = objectClass.getMethods();
			getterMethods = Arrays.stream(methods).filter((Method method) -> containsGetterAnnotation(method)).collect(Collectors.toList());
			methodListCache.put(objectClass, getterMethods);
		}
		return getterMethods;
	}
	/*
	 * private List<Method> sortMethodList(List<Method> methods) { List<Integer>
	 * methodRankingOrder = methods.stream().map((Method method) ->
	 * method.getAnnotation(Getter.class).value()).collect(Collectors.toList());
	 * int listLength = methods.size(); List<Method> sortedMethodList = new
	 * ArrayList<>(listLength); for (int i = 0; i < listLength; i++) { int
	 * currentMethodPostion = methodRankingOrder.get(0);
	 * 
	 * } }
	 */

	private boolean containsGetterAnnotation(Method method) {
		Annotation[] annotations = method.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(Getter.class)) {
				return true;
			}
		}
		return false;
	}

	private List<Object> parseHeaderInfo(Object object) {
		List<Object> header = new LinkedList<>();
		parseHeaderInfo(object, objectName, header);
		return header;
	}

	private void parseHeaderInfo(Object object, String objectName, List<Object> header) {
		if (object == null) {
			header.add(objectName);
			return;
		}
		List<Method> getterMethods = parseGetterMehtods(object.getClass());
		if (getterMethods.isEmpty()) {
			header.add(objectName);
		} else {
			getterMethods.forEach((Method getterMethod) -> {
				String prefix = !"".equals(objectName) ? objectName + "_" : "";
				try {
					parseHeaderInfo(getterMethod.invoke(object), prefix + getterMethod.getName().replaceFirst("get|is", ""), header);
				} catch (Exception e) {
					logMethodInvocationExceptions(object, getterMethod, e);
				}
			});
		}
	}

}
