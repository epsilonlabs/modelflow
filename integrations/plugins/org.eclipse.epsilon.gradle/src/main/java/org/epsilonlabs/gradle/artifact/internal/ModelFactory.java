package org.epsilonlabs.gradle.artifact.internal;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.epsilon.eol.models.IModel;
import org.epsilonlabs.gradle.artifact.Model;
import org.epsilonlabs.gradle.artifact.RuntimeModel;

import com.google.common.collect.Maps;

public class ModelFactory {

	private static ModelFactory factory;

	private Map<Class<? extends Model>, Class<? extends Factory>> factories = Maps.<Class<? extends Model>, Class<? extends Factory>> newHashMap();
	
	private ModelFactory(){}
	
	public static ModelFactory getInstance(){
		if (factory == null) {
			factory = new ModelFactory();
		}
		return factory;
	}
	
	public Map<Class<? extends Model>, Class<? extends Factory>> getFactories(){
		return factories;
	}
	
	public void registerRuntime(Class<? extends Model> forModel, Class<? extends Factory> factory) {
		factories.put(forModel, factory);
	}
	
	public void register(Class<? extends Model> forModel, Class<? extends Factory> factory) {
		factories.put(forModel, factory);
	}
	
	public Factory getRuntime(Class<? extends RuntimeModel> f) {
		try {
			Class<? extends Factory> class1 = factories.get(f);
			return class1.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public IModel create(Model m) {
		Class<? extends Model> class1 = m.getClass();
		try {
			for (Entry<Class<? extends Model>, Class<? extends Factory>> f : factories.entrySet()) {
				Class<? extends Model> key = f.getKey();
				if (key.isAssignableFrom(class1))
					return f.getValue().newInstance().get(m, null);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}