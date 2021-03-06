/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.atl.tests.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;

import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.TaskDefinitionValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ValidTaskTest {

    @Parameters( name = "{index}: validate({0})" )
    public static Collection<Class<? extends ITaskInstance>> data() {
        return Arrays.asList(
        		
    		);
    }

    private Class<? extends ITaskInstance> taskClass;

    public ValidTaskTest(Class<? extends ITaskInstance> taskClass) {
    	this.taskClass = taskClass;
    }
    
    @Test
    public void test() {
        TaskDefinitionValidator taskDefinitionValidator = new TaskDefinitionValidator(taskClass);
        if (!taskDefinitionValidator.isValid()) {
        	fail(taskDefinitionValidator.getMessages().toString());
        }
		assertEquals(true, taskDefinitionValidator.isValid());
    }
}
