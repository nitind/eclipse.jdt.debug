package org.eclipse.jdt.debug.tests.core;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */

/**
 * Tests runtime classpath entry creation/restoration.
 */
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.debug.tests.AbstractDebugTest;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;

public class RuntimeClasspathEntryTests extends AbstractDebugTest {
	
	public RuntimeClasspathEntryTests(String name) {
		super(name);
	}

	public void testProjectEntry() throws Exception {
		IProject project = getJavaProject().getProject();
		IRuntimeClasspathEntry entry = JavaRuntime.newProjectRuntimeClasspathEntry(getJavaProject());
	
		assertEquals("Paths should be equal", project.getFullPath(), entry.getPath());
		assertEquals("Resources should be equal", project, entry.getResource());
		assertEquals("Should be of type project", IRuntimeClasspathEntry.PROJECT, entry.getType());
		assertEquals("Should be a user entry", IRuntimeClasspathEntry.USER_CLASSES, entry.getClasspathProperty());
		
		String memento = entry.getMemento();
		IRuntimeClasspathEntry restored = JavaRuntime.newRuntimeClasspathEntry(memento);
		assertEquals("Entries should be equal", entry, restored);
	}
	
	public void testJRELIBVariableEntry() throws Exception {
		IClasspathEntry[] cp = getJavaProject().getRawClasspath();
		IClasspathEntry cpe = null;
		for (int i = 0; i < cp.length; i++) {
			if (cp[i].getEntryKind() == IClasspathEntry.CPE_VARIABLE && cp[i].getPath().equals(new Path(JavaRuntime.JRELIB_VARIABLE))) {
				cpe = cp[i];
				break;
			}
		}
		assertNotNull("Did not find a variable entry", cpe);
		IRuntimeClasspathEntry entry = JavaRuntime.newRuntimeClasspathEntry(cpe);
	
		assertEquals("Paths should be equal", cpe.getPath(), entry.getPath());
		assertNull("Resource should be null", entry.getResource());
		assertEquals("Should be of type varirable", IRuntimeClasspathEntry.VARIABLE, entry.getType());
		assertEquals("Should be a standard entry", IRuntimeClasspathEntry.STANDARD_CLASSES, entry.getClasspathProperty());
		
		String memento = entry.getMemento();
		IRuntimeClasspathEntry restored = JavaRuntime.newRuntimeClasspathEntry(memento);
		assertEquals("Entries should be equal", entry, restored);
		
		IVMInstall vm = JavaRuntime.getDefaultVMInstall();
		LibraryLocation lib = vm.getLibraryLocation();
		if (lib == null) {
			lib = vm.getVMInstallType().getDefaultLibraryLocation(vm.getInstallLocation());
		}
		IPath sourcePath = lib.getSystemLibrarySourcePath();
		IPath rootPath = lib.getPackageRootPath();
		
		assertEquals("Source attachment path did not resolve properly", sourcePath.toOSString(), entry.getResolvedSourceAttachmentPath());
		assertEquals("Source root path did not resolve properly", rootPath.toOSString(), entry.getResolvedSourceAttachmentRootPath());
		
	}	
	
}
