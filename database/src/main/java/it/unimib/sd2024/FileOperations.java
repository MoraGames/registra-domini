package it.unimib.sd2024;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

public class FileOperations {
	private static final ConcurrentHashMap<String, ReentrantReadWriteLock> lockMap = new ConcurrentHashMap<>();

	private static ReentrantReadWriteLock getLock(String filePath) {
		ReentrantReadWriteLock lock = lockMap.get(filePath);
		if (lock == null) {
			ReentrantReadWriteLock newLock = new ReentrantReadWriteLock();
			lock = lockMap.putIfAbsent(filePath, newLock);
			if (lock == null) {
				lock = newLock;
			}
		}
		
		return lock;
	}

	public static boolean exist(String filePath) {
		ReentrantReadWriteLock.ReadLock readLock = getLock(filePath).readLock();
		readLock.lock();

		// Check if the file exists and then ensure to release the lock
		try {
			Path path = Paths.get(filePath);
			return Files.exists(path);
		} finally {
			readLock.unlock();
		}
	}

	public static JsonObject read(String filePath) throws IOException {
		ReentrantReadWriteLock.ReadLock readLock = getLock(filePath).readLock();
		readLock.lock();
		
		// Read the file and return the JsonObject. If fails, release the lock
		try {
			JsonReader jsonReader = Json.createReader(new StringReader(Files.readString(Paths.get(filePath))));
			return jsonReader.readObject();
		} finally {
			readLock.unlock();
		}
	}

	public static void write(String filePath, JsonObject data) throws IOException {
		ReentrantReadWriteLock.WriteLock writeLock = getLock(filePath).writeLock();
		writeLock.lock();
		
		// Convert the JsonObject to data contained in a StringWriter
		StringWriter stringWriter = new StringWriter();
		JsonWriter jsonWriter = Json.createWriter(stringWriter);
		jsonWriter.write(data);
		
		// Write the data on the file. If fails, close the writer and release the lock
		try {
			Files.writeString(Paths.get(filePath), stringWriter.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} finally {
			jsonWriter.close();
			writeLock.unlock();
		}
	}

	public static void create(String filePath, String keyField) throws IOException {
		ReentrantReadWriteLock.WriteLock writeLock = getLock(filePath).writeLock();
		writeLock.lock();
		
		// Obtain the path of the file
		Path path = Paths.get(filePath);

		// Prepare the JsonReader to create the JsonObject that will be written on the file
		JsonReader jsonReader = Json.createReader(new StringReader("{\"keyField\": \"" + keyField + "\", \"data\": []}"));

		// Write the JsonObject on the new file. If fails, close the reader and release the lock
		try {
			// Check if the file already exists
			if (Files.exists(path)) {
				throw new IOException("File already exists");
			}

			// Create the file and write the JsonObject on it
			Files.createFile(path);
			write(filePath, jsonReader.readObject());
		} finally {
			jsonReader.close();
			writeLock.unlock();
		}
	}

	public static void delete(String filePath) throws IOException {
		ReentrantReadWriteLock.WriteLock writeLock = getLock(filePath).writeLock();
		writeLock.lock();

		// Delete the existing file. If fails, release the lock
		try {
			// Check if the file exists
			Path path = Paths.get(filePath);
			if (!Files.exists(path)) {
				throw new IOException("File does not exist");
			}

			// Delete the file
			Files.delete(path);
		} finally {
			writeLock.unlock();
		}
	}
}