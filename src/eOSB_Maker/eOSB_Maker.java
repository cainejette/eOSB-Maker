package eOSB_Maker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class eOSB_Maker {
	public static void main(String[] args) {
		Builder builder = new Builder();
	}

	public static String updateJarFile(String sourceFileName, List<File> questionFiles, File passwordFile, File expirationFile) throws IOException {
		String currentTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		
		File outputFile;
		String outputFileName;
		if (sourceFileName.contains(".jar")) {
			outputFileName = sourceFileName.substring(0, sourceFileName.length() - 4) + currentTime + ".jar";
		}
		else {
			outputFileName = sourceFileName + currentTime + ".jar";
		}
		
		outputFile = new File(outputFileName);
		System.out.println("creating jar file: " + outputFileName);
		
		if (outputFile.createNewFile()) {
			System.out.println(outputFile.getName() + " created.");
			
			JarFile sourceJarFile = new JarFile(sourceFileName);
			boolean jarUpdated = false;
			System.out.println("original: " + sourceFileName);

			try {
				JarOutputStream targetJarOutputStream = new JarOutputStream(new FileOutputStream(outputFile));

				try {
					eOSB_Maker.addQuestionsToJar(questionFiles, targetJarOutputStream);
					eOSB_Maker.addFileToJar(passwordFile, "eOSB/game/data" + File.separator + passwordFile.getName(), targetJarOutputStream);
					eOSB_Maker.addFileToJar(expirationFile, "eOSB/game/data" + File.separator + expirationFile.getName(), targetJarOutputStream);
					eOSB_Maker.addJarEntriesToJar(sourceJarFile, targetJarOutputStream);

					jarUpdated = true;
				} catch (Exception ex) {
					ex.printStackTrace();
					targetJarOutputStream.putNextEntry(new JarEntry("stub"));
				} finally {
					targetJarOutputStream.close();
					System.out.println(outputFile.getAbsolutePath() + " path");
					System.out.println(outputFile.getName() + " closed.");
				}

			} finally {
				sourceJarFile.close();
				System.out.println(sourceJarFile.getName() + " closed.");

				if (!jarUpdated) {
					outputFile.delete();
				}
			}

			if (jarUpdated) {				
				passwordFile.delete();
				expirationFile.delete();
				
				return outputFile.getAbsolutePath();
			}
			else {
				return "failed :( Reason: jarUpdated is false.";
			}
		}
		else {
			return "failed :( Reason: tmpJarFile.createNewFile returned false.";
		}
	}
	
	private static void addQuestionsToJar(List<File> files, JarOutputStream outputStream) {
		for (File file : files) {
			eOSB_Maker.addFileToJar(file, "eOSB/game/data/questions" + File.separator + file.getName(), outputStream);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static void addJarEntriesToJar(JarFile source, JarOutputStream outputStream) {
		Enumeration jarEntries = source.entries();
		while (jarEntries.hasMoreElements()) {
			JarEntry entry = (JarEntry) jarEntries.nextElement();
			
			if (entry.getName().contains("password.txt") ||
					entry.getName().contains("expiration.txt") ||
					entry.getName().contains("questions/")) {
				continue;
			}
			
			try {
				InputStream entryInputStream = source.getInputStream(entry);
				outputStream.putNextEntry(entry);
				
				byte[] buffer = new byte[1024];
				int bytesRead = 0;
				while ((bytesRead = entryInputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
