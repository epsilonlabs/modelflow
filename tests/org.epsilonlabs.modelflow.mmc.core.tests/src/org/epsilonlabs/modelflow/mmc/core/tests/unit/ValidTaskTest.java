/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.core.tests.unit;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.epsilonlabs.modelflow.dom.api.AbstractTaskInstance;
import org.epsilonlabs.modelflow.dom.api.TaskDefinitionValidator;
import org.epsilonlabs.modelflow.mmc.core.task.FileReaderTask;
import org.epsilonlabs.modelflow.mmc.core.task.PrintTask;
import org.epsilonlabs.modelflow.mmc.core.task.SleepTask;
import org.epsilonlabs.modelflow.mmc.core.task.XsdValidationTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ValidTaskTest {

    @Parameters( name = "{index}: validate({0})" )
    public static Collection<Class<? extends AbstractTaskInstance>> data() {
        return Arrays.asList(
        		FileReaderTask.class, PrintTask.class, XsdValidationTask.class, SleepTask.class
    		);
    }

    private Class<? extends AbstractTaskInstance> taskClass;

    public ValidTaskTest(Class<? extends AbstractTaskInstance> taskClass) {
    	this.taskClass = taskClass;
    }
    
    @Test
    public void test() {
        assertEquals(true, new TaskDefinitionValidator(taskClass).isValid());
    }
   
}
