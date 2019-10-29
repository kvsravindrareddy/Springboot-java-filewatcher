package com.ravindra.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class FileWatcher {
	private String dirPath;
	
	private String filePath;
	
	public String getDirPath() {
		return dirPath;
	}
	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Bean
	public void watchFile() {
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
			Paths.get(dirPath).register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
//			while (true) {
			File path = new File(filePath);
			if (path.exists()) {
				System.out.println("File is exist in the given directory");
			}
			
			WatchKey wk = watchService.take();
			wk.pollEvents().forEach(event -> {
				final Path changed = (Path) event.context();
				System.out.println(changed);
				if (changed.endsWith("test.csv")) {
					System.out.println("My file has changed");
				}
			});
			boolean valid = wk.reset();
			if (!valid) {
				System.out.println("Key has been unregisterede");
			}
//			}
		} catch (IOException | InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		FileWatcher fileWatch = new FileWatcher();
		fileWatch.watchFile();
	}
}