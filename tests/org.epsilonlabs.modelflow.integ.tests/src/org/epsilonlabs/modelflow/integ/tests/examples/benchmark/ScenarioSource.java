/**
 * 
 */
package org.epsilonlabs.modelflow.integ.tests.examples.benchmark;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;
import java.util.function.BiPredicate;

import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.NullEnum;

/**
 * @author Betty Sanchez
 *
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(ScenarioBenchmarkArgumentProvider.class)
public @interface ScenarioSource {

	Class<? extends Enum<?>> value() default NullEnum.class;

	String[] names() default {};
	
	int times() default 1;

	Mode mode() default Mode.INCLUDE;

	enum Mode {

		INCLUDE((name, names) -> names.contains(name)),

		EXCLUDE((name, names) -> !names.contains(name)),

		MATCH_ALL((name, patterns) -> patterns.stream().allMatch(name::matches)),

		MATCH_ANY((name, patterns) -> patterns.stream().anyMatch(name::matches));

		private final BiPredicate<String, Set<String>> selector;

		Mode(BiPredicate<String, Set<String>> selector) {
			this.selector = selector;
		}

		boolean select(Enum<?> constant, Set<String> names) {
			return selector.test(constant.name(), names);
		}

	}

}
