package eOSB_Maker;

import java.io.File;
import java.io.FileInputStream;
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

	public static String updateJarFile(String original, List<File> questionFiles, File passwordFile, File expirationFile) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		File tmpJarFile = new File(original + timeStamp);
		System.out.println("creating jar file: " + original + timeStamp);
		if (tmpJarFile.createNewFile()) {
			System.out.println(tmpJarFile.getName() + " created... (tmpJarFile)");
			JarFile jarFile = new JarFile(original);
			boolean jarUpdated = false;
			System.out.println("original: " + original);

			try {
				JarOutputStream tempJarOutputStream = new JarOutputStream(new FileOutputStream(tmpJarFile));

				try {
					// Added the new files to the jar.
					for (int i = 0; i < questionFiles.size(); i++) {
						File file = questionFiles.get(i);
						FileInputStream fis = new FileInputStream(file);
						try {
							byte[] buffer = new byte[1024];
							int bytesRead = 0;
							JarEntry entry = new JarEntry("eOSB/game/data/questions" + File.separator + file.getName());
							tempJarOutputStream.putNextEntry(entry);
							while ((bytesRead = fis.read(buffer)) != -1) {
								tempJarOutputStream.write(buffer, 0, bytesRead);
							}

							System.out.println(entry.getName() + " added.");
						} finally {
							fis.close();
						}
					}
					
					FileInputStream fis = new FileInputStream(passwordFile);
					try {
						byte[] buffer = new byte[1024];
						int bytesRead = 0;
						JarEntry entry = new JarEntry("eOSB/game/data" + File.separator + passwordFile.getName());
						tempJarOutputStream.putNextEntry(entry);
						while ((bytesRead = fis.read(buffer)) != -1) {
							tempJarOutputStream.write(buffer, 0, bytesRead);
						}

						System.out.println(entry.getName() + " added.");
					}
					finally {
						fis.close();
					}
					
					fis = new FileInputStream(expirationFile);
					try {
						byte[] buffer = new byte[1024];
						int bytesRead = 0;
						JarEntry entry = new JarEntry("eOSB/game/data" + File.separator + expirationFile.getName());
						tempJarOutputStream.putNextEntry(entry);
						while ((bytesRead = fis.read(buffer)) != -1) {
							tempJarOutputStream.write(buffer, 0, bytesRead);
						}

						System.out.println(entry.getName() + " added.");
					}
					finally {
						fis.close();
					}

					// Copy original jar file to the temporary one.
					Enumeration jarEntries = jarFile.entries();
					while (jarEntries.hasMoreElements()) {
						JarEntry entry = (JarEntry) jarEntries.nextElement();
						
						if (entry.getName().contains("password.txt") ||
								entry.getName().contains("expiration.txt") ||
								entry.getName().contains("questions/")) {
							continue;
						}
						
						InputStream entryInputStream = jarFile.getInputStream(entry);
						tempJarOutputStream.putNextEntry(entry);
						byte[] buffer = new byte[1024];
						int bytesRead = 0;
						while ((bytesRead = entryInputStream.read(buffer)) != -1) {
							tempJarOutputStream.write(buffer, 0, bytesRead);
						}
					}

					jarUpdated = true;
				} catch (Exception ex) {
					ex.printStackTrace();
					tempJarOutputStream.putNextEntry(new JarEntry("stub"));
				} finally {
					tempJarOutputStream.close();
					System.out.println(tmpJarFile.getAbsolutePath() + " path");
					System.out.println(tmpJarFile.getName() + " closed.");
				}

			} finally {
				jarFile.close();
				System.out.println(jarFile.getName() + " closed.");

				if (!jarUpdated) {
					tmpJarFile.delete();
				}
			}

			if (jarUpdated) {				
				passwordFile.delete();
				expirationFile.delete();
				
				return tmpJarFile.getAbsolutePath();
			}
			else {
				return "failed :( Reason: jarUpdated is false.";
			}
		}
		else {
			return "failed :( Reason: tmpJarFile.createNewFile returned false.";
		}
	}
}
