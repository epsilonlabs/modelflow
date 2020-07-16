package gradle.modelflow;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public enum Scenario {

	CLEAN(1), NO_CHANGES(2), SOURCE_MODEL(3), INTERMEDIATE_MODEL(4), TEMPLATES(5), NON_PROTECTED_OUTPUTS(6), PROTECTED_OUTPUTS(7);
	
	private Integer id;
	
	Scenario(Integer id) {
		this.id = id;
	}
	
	public Runnable getModifications(Path root){
		switch (this) {
		case CLEAN:
			return new Runnable() {
				@Override public void run() {}
			};
		case NO_CHANGES:
			return new Runnable() {
				@Override public void run() {}
			};
		case SOURCE_MODEL:
			return new Runnable() {
				@Override public void run() {
					modifyModel(root, Paths.get("resources", "m", "component.model"), "actuatorDiffPort", "diffPort");
				}
			};
		case INTERMEDIATE_MODEL:
			return new Runnable() {
				@Override public void run() {
					modifyModel(root, Paths.get("resources", "m", "variant.model"), "value=\"15\"", "value=\"19\"");	
				}
			};
		case TEMPLATES:
			return new Runnable() {
				@Override public void run() {
					addComment(root, Paths.get("resources", "mmt", "generate.egx"));
				}
			};
		case NON_PROTECTED_OUTPUTS:
			return new Runnable() {
				@Override public void run() {
					addComment(root, Paths.get("src-gen", "reactive", "ComponentSystem.java"));
				}
			};
		case PROTECTED_OUTPUTS:
			return new Runnable() {
				@Override public void run() {
					protectedRegionChange(root, Paths.get("src-gen", "reactive", "ComponentSystem.java"));
				}
			};
		default:
			return null;
		}
	}
	
	public static Scenario get(Integer id){
		for (Scenario s : Scenario.values()) {
			if (s.id == id) {
				return s;
			}
		}
		throw new IllegalStateException("Invalid Id");
	}
	protected void addComment(Path root, Path fileRelativePath) {
		Path filePath = root.resolve(fileRelativePath);
		File file = filePath.toAbsolutePath().toFile();
		assertTrue("File not found", file.exists());
	//	String hash1 = Hasher.computeHashForFile(file);
		try (FileWriter fw = new FileWriter(file, true)) {
			BufferedWriter out = new BufferedWriter(fw);
			out.newLine();
			out.write("//explicit modification");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	//	String hash2 = Hasher.computeHashForFile(fileRelativePath.toFile());
	//	assertNotEquals("hashes are the same", hash1, hash2);
	}
	
	protected void protectedRegionChange(Path root, Path fileRelativePath) {
		Path filePath = root.resolve(fileRelativePath);
		File file = filePath.toFile();
		assertTrue("File not found", file.exists());
//		String hash1 = Hasher.computeHashForFile(file);
	
		BufferedReader reader;
		StringBuffer inputBuffer = new StringBuffer();
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			boolean first = true;
			while ((line = reader.readLine()) != null) {
				if (line.trim() != null && line.trim().equals("// TODO logic")) {
					line = line.replace("// TODO logic", "System.out.println(\"Modified\");");
				}
				inputBuffer.append((!first?"\n":"") + line);

				first=false;
			}
			reader.close();
			String modifiedString = inputBuffer.toString();
			FileOutputStream fileOut = new FileOutputStream(file);
	        fileOut.write(modifiedString.getBytes());
	        fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		String hash2 = Hasher.computeHashForFile(filePath.toFile());
//		assertNotEquals("hashes are the same", hash1, hash2);
	}
	
	protected void modifyModel(Path root, Path fileRelativePath, String regex, String value) {
		Path filePath = root.resolve(fileRelativePath);
		File file = filePath.toFile();
		assertTrue("File not found", file.exists());
//		String hash1 = Hasher.computeHashForFile(file);
	
		BufferedReader reader;
		StringBuffer inputBuffer = new StringBuffer();
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			boolean done = false;
			while ((line = reader.readLine()) != null) {
				if (!done && line.contains(regex)) {
					line = line.replace(regex, value);
					done = true;
				}
				inputBuffer.append(line + "\n");
			}
			reader.close();
			String modifiedString = inputBuffer.toString();
			FileOutputStream fileOut = new FileOutputStream(file);
	        fileOut.write(modifiedString.getBytes());
	        fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		String hash2 = Hasher.computeHashForFile(filePath.toFile());
//		assertNotEquals("hashes are the same", hash1, hash2);
	}
}
