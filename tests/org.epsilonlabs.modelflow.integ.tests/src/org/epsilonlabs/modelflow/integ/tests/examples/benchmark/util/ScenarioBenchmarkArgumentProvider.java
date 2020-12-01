/**
 * 
 */
package org.epsilonlabs.modelflow.integ.tests.examples.benchmark.util;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import io.reactivex.Observable;

/**
 * @author Betty Sanchez
 *
 */
public class ScenarioBenchmarkArgumentProvider implements ArgumentsProvider, AnnotationConsumer<ScenarioSource> {
	
	public static final Observable<?> trueFalse = Observable.fromArray(true, false);

	protected ScenarioSource scenarioSource;

	@Override
	public void accept(ScenarioSource scenarioSource) {
		this.scenarioSource = scenarioSource;
	}
	
	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
		Set<? extends Enum<?>> enumConstants = getEnumConstants(context);
		ScenarioSource.Mode mode = scenarioSource.mode();
		String[] enums = scenarioSource.names();
		if (enums.length > 0) {
			Set<String> uniqueNames = stream(enums).collect(toSet());
			enumConstants.removeIf(constant -> !mode.select(constant, uniqueNames));
		}
		return Observable.fromIterable(enumConstants).flatMap(scenario -> 
		 	trueFalse.flatMap(tracing -> 
		 		Observable.range(1, scenarioSource.times()).map(iteration -> 
		 			new Object[] {scenario, tracing, iteration}
		 		)
		 	)
		).map(Arguments::of).toList().blockingGet().stream();
	}
	
	@SuppressWarnings({ "unchecked" })
	private <E extends Enum<E>> Set<? extends E> getEnumConstants(ExtensionContext context) {
		Class<E> enumClass = (Class<E>) scenarioSource.value();
		return EnumSet.allOf(enumClass);
	}

}