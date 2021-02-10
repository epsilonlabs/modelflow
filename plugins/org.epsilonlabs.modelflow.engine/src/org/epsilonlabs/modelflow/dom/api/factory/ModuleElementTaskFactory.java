/**
 * 
 */
package org.epsilonlabs.modelflow.dom.api.factory;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.Return;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.annotation.Param;
import org.epsilonlabs.modelflow.dom.ast.ITaskModuleElement;
import org.epsilonlabs.modelflow.exception.MFInstantiationException;
import org.epsilonlabs.modelflow.exception.MFRuntimeException;
import org.epsilonlabs.modelflow.exception.MFUnknownPropertyException;
import org.epsilonlabs.modelflow.execution.context.IModelFlowContext;
import org.epsilonlabs.modelflow.execution.graph.node.ITaskNode;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author bea
 *
 */
public class ModuleElementTaskFactory implements IInstanceFactory<ITaskInstance, ITaskNode>{

	protected Class<?> clazz;
	protected ITaskModuleElement declaration;
	protected ITaskInstance instance;
	protected ITaskNode node;

	@Override
	public ITaskInstance create(Class<? extends ITaskInstance> factory, ITaskNode node, IModelFlowContext ctx) throws MFRuntimeException {
		this.clazz = factory;
		this.node = node;
		this.declaration = node.getModuleElement();
		Injector injector = Guice.createInjector();
		injector.getAllBindings();
		instance = (ITaskInstance) injector.getInstance(clazz);
		injector.injectMembers(instance);
		configure(ctx);
		return instance;
	}	
	protected void configure(IModelFlowContext ctx) throws MFRuntimeException {
		List<Method> paramMethods = Stream.of(clazz.getMethods())
				.filter(m -> m.getAnnotationsByType(Param.class).length != 0)
				.collect(Collectors.toList());

		/* Iterate over the declaration properties */
		Iterator<Entry<NameExpression, ModuleElement>> iterator = declaration.getParameters().entrySet().iterator();
		while (iterator.hasNext()) {

			// EVALUATE PARAMS FROM DECLARATION
			
			Entry<NameExpression, ModuleElement> prop = iterator.next();
			final String key = prop.getKey().getName();
			
			Object value = prop.getValue();
			if (value instanceof IExecutableModuleElement) {
				try {
					value = ((IExecutableModuleElement) value).execute(ctx);
					if (value instanceof Return) {
						value = ((Return) value).getValue();
					}
				} catch (EolRuntimeException e) {
					throw new MFRuntimeException(e);
				}
			}
			
			// ATTEMPT TO CONFIGURE INSTANCE
			final Object val = value;
			Collection<Method> potentialMatchingMethods = paramMethods.stream().filter(m -> {
				/* same key */
				return m.getAnnotationsByType(Param.class)[0].key().equals(key)
						/* one argument */
						&& m.getParameterTypes().length == 1
						/* assignable from value */
						&& (isAssignableFrom(m, val));
			}).collect(Collectors.toSet());
			
			if (potentialMatchingMethods.isEmpty()) {
				/* Unknown Property */
				throw new MFUnknownPropertyException(key, node.getName());
			}
			
			tryToAssignProperty(key, value, potentialMatchingMethods, ctx);	
		}
	}
	
	protected boolean isAssignableFrom(Method m, Object value) {
		return m.getParameterTypes()[0].isAssignableFrom(value.getClass())
				|| (m.getParameterTypes()[0].equals(File.class) 
						&& value.getClass().equals(String.class));
	}
	
	protected void tryToAssignProperty(String key, Object value, Collection<Method> potentialMatchingMethods, IModelFlowContext ctx)
			throws MFInstantiationException {
		for (Method method : potentialMatchingMethods) {
			Class<?> paramType = method.getParameterTypes()[0];
			Object assignableValue = getAssignableValue(value, paramType, ctx);
			if (isAssignableFrom(method, assignableValue)) {
				try {
					method.invoke(instance, assignableValue);
					return;
				} catch (Exception e) {
					throw new MFInstantiationException(e);
				}
			}
		}
		String msg = "Unable to assign property %s to task %s";
		throw new MFInstantiationException(String.format(msg, key, node.getName()));
	}
	
	protected Object getAssignableValue(Object value, Class<?> paramType, IModelFlowContext ctx) throws MFInstantiationException {
		// Utility for files
		if (File.class.isAssignableFrom(paramType) && value instanceof String){
			IRelativePathResolver relativePathResolver = ctx.getModule().getCompilationContext().getRelativePathResolver();
			String fileLocation = relativePathResolver.resolve((String) value);
			value = new File(fileLocation);	
		} 
		return value;
	}
	
}
