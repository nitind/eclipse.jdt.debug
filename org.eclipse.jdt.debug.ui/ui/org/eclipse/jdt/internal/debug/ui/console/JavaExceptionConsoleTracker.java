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
package org.eclipse.jdt.internal.debug.ui.console;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.ui.console.IHyperlink;
import org.eclipse.ui.console.IOConsole;
import org.eclipse.ui.console.PatternMatchEvent;

/**
 * creates JavaExceptionHyperLinks
 * 
 * @since 3.1
 */
public class JavaExceptionConsoleTracker extends JavaConsoleTracker {
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.console.IPatternMatchListenerDelegate#matchFound(org.eclipse.ui.console.PatternMatchEvent)
     */
    public void matchFound(PatternMatchEvent event) {
        try {
            int offset = event.getOffset();
            int length = event.getLength();
            IOConsole console = getConsole();
            String exceptionName;
            exceptionName = console.getDocument().get(offset, length - 1);
            IHyperlink link = new JavaExceptionHyperLink(console, exceptionName);
            getConsole().addHyperlink(link, offset, length - 1);
        } catch (BadLocationException e) {
        }
    }
}