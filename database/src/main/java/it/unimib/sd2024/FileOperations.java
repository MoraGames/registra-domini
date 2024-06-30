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
		try {
			Path path = Paths.get(filePath);
			return Files.exists(path);
		} finally {
			readLock.unlock();
		}
	}

	public static JsonObject read(String filePath) {
		ReentrantReadWriteLock.ReadLock readLock = getLock(filePath).readLock();
        readLock.lock();
        try {
			JsonReader jsonReader = Json.createReader(new StringReader(Files.readString(Paths.get(filePath))));
			return jsonReader.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error reading file");
			return null;
		} finally {
			readLock.unlock();
		}
	}

	public static void write(String filePath, JsonObject data) {
		ReentrantReadWriteLock.WriteLock writeLock = getLock(filePath).writeLock();
        writeLock.lock();
        try {
			StringWriter stringWriter = new StringWriter();
			JsonWriter jsonWriter = Json.createWriter(stringWriter);
			jsonWriter.write(data);
			Files.writeString(Paths.get(filePath), stringWriter.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error writing file");
		} finally {
			writeLock.unlock();
		}
	}

	public static void create(String filePath) {
        ReentrantReadWriteLock.WriteLock writeLock = getLock(filePath).writeLock();
        writeLock.lock();
        try {
			Path path = Paths.get(filePath);
			if (!Files.exists(path)) {
				try {
					Files.createFile(path);
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("Error creating file");
				}
			} else {
				System.out.println("File already exists");
			}
		} finally {
			writeLock.unlock();
		}
    }

    public static void delete(String filePath) throws IOException {
		ReentrantReadWriteLock.WriteLock writeLock = getLock(filePath).writeLock();
		writeLock.lock();
		try {
			Path path = Paths.get(filePath);
			if (Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("Error deleting file");
				}
			} else {
				System.out.println("File does not exist");
			}
		} finally {
			writeLock.unlock();
		}
    }
}