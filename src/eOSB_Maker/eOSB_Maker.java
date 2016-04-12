package eOSB_Maker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class eOSB_Maker {
	public static void main(String[] args) {
		new Builder();
	}

	public static String updateJarFile(String sourceFileName, File questionDirectory, String password, String expiration) throws IOException {
		String outputFileName = eOSB_Maker.generateName(sourceFileName);
		File outputFile = new File(outputFileName);
		System.out.println("creating jar file: " + outputFileName);
		
		if (outputFile.createNewFile()) {
			JarOutputStream targetJarOutputStream = null;
			Writer writer = null;			
			boolean jarUpdated = false;
			
			try {
				targetJarOutputStream = new JarOutputStream(new FileOutputStream(outputFile));
				writer = new OutputStreamWriter(targetJarOutputStream);
				
				eOSB_Maker.addQuestionsToJar(questionDirectory, targetJarOutputStream);
				eOSB_Maker.addJarEntriesToJar(sourceFileName, targetJarOutputStream);
				eOSB_Maker.addMetadata(password, "eOSB/game/data/password.txt", targetJarOutputStream, writer);
				eOSB_Maker.addMetadata(expiration, "eOSB/game/data/expiration.txt", targetJarOutputStream, writer);

				jarUpdated = true;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				targetJarOutputStream.close();
				writer.close();

				if (!jarUpdated) {
					outputFile.delete();
					return "failed :(";
				}
			}

			return outputFile.getAbsolutePath();
			
		}
		else {
			outputFile.delete();
			return "failed :(";
		}
	}
	
	private static String generateName(String sourceFileName) {
		String currentTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		
		String outputFileName;
		if (sourceFileName.contains(".jar")) {
			outputFileName = sourceFileName.substring(0, sourceFileName.length() - 4) + "_" + currentTime + ".jar";
		}
		else {
			outputFileName = sourceFileName + currentTime + ".jar";
		}
		
		return outputFileName;
	}
	
	private static void addQuestionsToJar(File questionDirectory, JarOutputStream outputStream) {
		for (File file : questionDirectory.listFiles()) {
			eOSB_Maker.addFileToJar(file, "eOSB/game/data/questions/" + file.getName(), outputStream);
		}		
	}
	
	private static void addFileToJar(File file, String path, JarOutputStream outputStream) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int bytesRead = 0;
			JarEntry entry = new JarEntry(path);
			outputStream.putNextEntry(entry);
			while ((bytesRead = fis.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			System.out.println(entry.getName() + " added.");
		}
		 catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void addMetadata(String data, String path, JarOutputStream outputStream, Writer writer) {
		JarEntry entry = new JarEntry(path);
		try {
			outputStream.putNextEntry(entry);

			writer.write(data);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void addJarEntriesToJar(String sourceFileName, JarOutputStream outputStream) {
		JarFile source = null;
		
		try {
			source = new JarFile(sourceFileName);
	
			Enumeration<JarEntry> jarEntries = source.entries();
			while (jarEntries.hasMoreElements()) {
				JarEntry entry = (JarEntry) jarEntries.nextElement();
				
				if (entry.getName().contains("password.txt") ||
						entry.getName().contains("expiration.txt") ||
						entry.getName().contains("questions/")) {
					continue;
				}
			
				InputStream entryInputStream = source.getInputStream(entry);
				outputStream.putNextEntry(entry);
				
				byte[] buffer = new byte[1024];
				int bytesRead = 0;
				while ((bytesRead = entryInputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (source != null) {				
				try {
					source.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
