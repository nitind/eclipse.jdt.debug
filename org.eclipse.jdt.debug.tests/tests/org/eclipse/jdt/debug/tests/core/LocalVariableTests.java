package org.eclipse.jdt.debug.tests.core;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */

import java.util.ArrayList;
import java.util.List;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.jdt.debug.core.IJavaDebugTarget;
import org.eclipse.jdt.debug.core.IJavaObject;
import org.eclipse.jdt.debug.core.IJavaStackFrame;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.eclipse.jdt.debug.core.IJavaVariable;
import org.eclipse.jdt.debug.eval.EvaluationManager;
import org.eclipse.jdt.debug.eval.IAstEvaluationEngine;
import org.eclipse.jdt.debug.eval.IEvaluationResult;
import org.eclipse.jdt.debug.tests.AbstractDebugTest;

public class LocalVariableTests extends AbstractDebugTest {
	
	public LocalVariableTests(String name) {
		super(name);
	}

	public void testSimpleVisibility() throws Exception {
		String typeName = "LocalVariablesTests";
		
		createLineBreakpoint(7, typeName);		
		
		IJavaThread thread= null;
		try {
			thread= launch(typeName);
			assertNotNull("Breakpoint not hit within timeout period", thread);

			IJavaStackFrame frame = (IJavaStackFrame)thread.getTopStackFrame();
			IJavaVariable[] vars = frame.getLocalVariables();
			assertEquals("Should be no visible locals", 0, vars.length);
			
			stepOver(frame);
			stepOver(frame);
			
			vars = frame.getLocalVariables();
			assertEquals("Should be one visible local", 1, vars.length);			
			assertEquals("Visible var should be 'i1'", "i1", vars[0].getName());
			
			stepOver(frame);
			stepOver(frame);
			
			vars = frame.getLocalVariables();
			assertEquals("Should be two visible locals", 2, vars.length);			
			assertEquals("Visible var 1 should be 'i1'", "i1", vars[0].getName());			
			assertEquals("Visible var 2 should be 'i2'", "i2", vars[1].getName());

		} finally {
			terminateAndRemove(thread);
			removeAllBreakpoints();
		}		
	}
	
	public void testEvaluationAssignments() throws Exception {
		String typeName = "LocalVariablesTests";
		
		createLineBreakpoint(11, typeName);		
		
		IJavaThread thread= null;
		try {
			thread= launch(typeName);
			assertNotNull("Breakpoint not hit within timeout period", thread);

			IJavaStackFrame frame = (IJavaStackFrame)thread.getTopStackFrame();
			IJavaDebugTarget target = (IJavaDebugTarget)frame.getDebugTarget();
			IVariable i1 = frame.findVariable("i1");
			assertNotNull("Could not find variable 'i1'", i1);
			assertEquals("'i1' value should be '0'", target.newValue(0), i1.getValue());
			
			IVariable i2 = frame.findVariable("i2");
			assertNotNull("Could not find variable 'i2'", i2);
			assertEquals("'i2' value should be '1'", target.newValue(1), i2.getValue());
						
			IEvaluationResult result = evaluate("i1 = 73;", frame);			
			// the value should have changed
			assertEquals("'i1' value should be '73'", target.newValue(73), i1.getValue());
			
			result = evaluate("i2 = i1;", frame);
			// the value should have changed
			assertEquals("'i2' value should be '73'", target.newValue(73), i2.getValue());
			
		} finally {
			terminateAndRemove(thread);
			removeAllBreakpoints();
		}		
	}		
}
