/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.debug.testplugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.ui.memory.IMemoryRendering;
import org.eclipse.debug.ui.memory.IMemoryRenderingTypeDelegate;

/**
 * Test memory rendering type delegate.
 * 
 * @since 3.1
 */
public class RenderingTypeDelegate implements IMemoryRenderingTypeDelegate {

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.memory.IMemoryRenderingTypeDelegate#createRendering(java.lang.String)
	 */
	public IMemoryRendering createRendering(String id) throws CoreException {
		return null;
	}

}