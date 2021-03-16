package crevice;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Map;
import java.util.HashMap;

import javax.swing.JFileChooser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *
 * @author Loïc Vital
 *
 */

public class SystemManager {

	private File configFile;
	private Map<String, File> projectFiles;

	private JFileChooser surfaceChooser;
	private JFileChooser outputChooser;

	public SystemManager() {
		initFiles();

		buildSurfaceChooser();
		buildOutputChooser();
	}

	private void initFiles() {
		configFile = new File("config.json");
		projectFiles = new HashMap<String, File>();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode tree = mapper.readTree(configFile);

			JsonNode nodeSurface = tree.get("surfacePath");
			if(nodeSurface == null) projectFiles.put("surfacePath", null);
			else projectFiles.put("surfacePath", new File(nodeSurface.asText())); 

			JsonNode nodeOutput = tree.get("outputPath");
			if(nodeOutput == null) projectFiles.put("outputPath", null);
			else projectFiles.put("outputPath", new File(nodeOutput.asText()));

		} catch(FileNotFoundException e) {
			projectFiles.put("surfacePath", null);
			projectFiles.put("outputPath", null);
			saveConfig();

		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void buildSurfaceChooser() {
		surfaceChooser = new JFileChooser();
		surfaceChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		surfaceChooser.setMultiSelectionEnabled(false);
	}

	private void buildOutputChooser() {
		outputChooser = new JFileChooser();
		outputChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		outputChooser.setMultiSelectionEnabled(false);
	}

	public void setSurfaceFile(File f) {
		projectFiles.put("surfacePath", f);
	}

	public File getSurfaceFile() {
		return projectFiles.get("surfacePath");
	}

	public void setOutputDir(File f) {
		projectFiles.put("outputPath", f);
	}

	public File getOutputDir() {
		return projectFiles.get("outputPath");
	}

	public void saveConfig() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.writeValue(configFile, projectFiles);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public JFileChooser getSurfaceChooser() {
		return surfaceChooser;
	}

	public JFileChooser getOutputChooser() {
		return outputChooser;
	}

}
