package com.ravindra.service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class CSVReaderService {

	public String getCsvData() {

		Path filePath = Paths.get("D://Learning/");
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
			WatchKey watchKey = filePath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
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
		} catch (IOException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Path path = Paths.get("D://Learning/test.csv");
		try (Stream<String> stream = Files.lines(path)) {
			stream.forEach(strm -> {
				if (strm.contains("|")) {
					Arrays.asList(strm.split("/|")).forEach(csvData -> {
						System.out.println(csvData);
					});
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Hello CSV : " + path.getFileName().toString();
	}
}