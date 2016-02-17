package eOSB_Maker;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class JarFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		return f.getName().endsWith("jar");
	}

	@Override
	public String getDescription() {
		return "JAR files";
	}
}
