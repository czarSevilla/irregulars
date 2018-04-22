package czar.english.irregulars.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PropertiesTester<T> {
	
	public static final Random random = new Random(1243214719847981347L);

	public static <T> boolean verify(Class<T> t)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		Object obj = t.newInstance();
		Method[] methods = t.getDeclaredMethods();
		Map<String, Method> getters = new HashMap<>();
		Map<String, Method> setters = new HashMap<>();
		for (Method method : methods) {
			String attrName = method.getName().substring(3);
			if (method.getName().startsWith("get")) {
				getters.put(attrName, method);
			} else if (method.getName().startsWith("set")) {
				setters.put(attrName, method);
			}
			if (method.getName().startsWith("is")) {
				attrName = method.getName().substring(2);
				getters.put(attrName, method);
			}
		}
		for (String key : setters.keySet()) {
			Method getter = getters.get(key);
			Method setter = setters.get(key);

			System.out.println(setter.getParameterTypes()[0].getName());
			Object value = getValue(setter.getParameterTypes()[0]);

			if (value == null) {
				return false;
			}

			setter.invoke(obj, value);

			Object returned = getter.invoke(obj);

			assertThat(value, equalTo(returned));

		}
		return true;
	}

	public static Object getValue(Class<? extends Object> paramType)
			throws InstantiationException, IllegalAccessException {
		if (paramType.isAssignableFrom(String.class)) {
			return String.format("cadena%d", random.nextInt());
		} else if (paramType.isAssignableFrom(Long.class)) {
			return random.nextLong();
		} else if (paramType.isAssignableFrom(Date.class)) {
			return new Date(random.nextLong());
		} else if (paramType.isAssignableFrom(BigDecimal.class)) {
			return new BigDecimal(String.valueOf(random.nextDouble()));
		} else if (paramType.isAssignableFrom(Integer.class)) {
			return random.nextInt();
		} else if (paramType.isAssignableFrom(Boolean.class)) {
			return random.nextInt() % 2 == 0;
		} else if (paramType.isAssignableFrom(boolean.class)) {
			return random.nextInt() % 2 == 0;
		} else if (paramType.isAssignableFrom(int.class)) {
			return random.nextInt();
		} else {
			return paramType.newInstance();
		}
	}
}
