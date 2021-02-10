package org.epsilonlabs.modelflow.mmc.gmf.tests.unit;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.epsilonlabs.modelflow.dom.api.ITaskInstance;
import org.epsilonlabs.modelflow.dom.api.TaskDefinitionValidator;
import org.epsilonlabs.modelflow.mmc.gmf.task.GenerateDiagramCodeTask;
import org.epsilonlabs.modelflow.mmc.gmf.task.GmfMap2GmfGenTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ValidTaskTest {

    @Parameters( name = "{index}: validate({0})" )
    public static Collection<Class<? extends ITaskInstance>> data() {
        return Arrays.asList(
        		GenerateDiagramCodeTask.class,
        		GmfMap2GmfGenTask.class
    		);
    }

    private Class<? extends ITaskInstance> taskClass;

    public ValidTaskTest(Class<? extends ITaskInstance> taskClass) {
    	this.taskClass = taskClass;
    }
    
    @Test
    public void test() {
        assertEquals(true, new TaskDefinitionValidator(taskClass).isValid());
    }
    
}
