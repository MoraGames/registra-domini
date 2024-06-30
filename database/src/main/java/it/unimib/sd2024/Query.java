package it.unimib.sd2024;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import it.unimib.sd2024.types.CollectionOperation;
import it.unimib.sd2024.types.DocumentOperation;
import it.unimib.sd2024.types.SearchCondition;

public class Query {
	public static final String COLLECTION_FILES_PATH = "./collections/";

	private CollectionOperation collectionOperation; // [CREATE, SELECT, DELETE]
	private String collectionName;
	private String collectionKeyField; // Used if collectionOperation is CREATE
	private DocumentOperation documentOperation; // [INSERT, SEARCH, REMOVE] // Used if collectionOperation is SELECT
	private JsonObject document; // Used if documentOperation is INSERT // Used if collectionOperation is SELECT
	private List<SearchCondition> searchConditions; // Used if documentOperation is SEARCH // Used if collectionOperation is SELECT
	private String documentId; // Used if documentOperation is DELETE // Used if collectionOperation is SELECT

	public Query(List<String> queryStrings) throws IllegalArgumentException {
		parseQuery(queryStrings);
	}

	private void parseQuery(List<String> queryStrings) throws IllegalArgumentException {
		if (queryStrings == null || queryStrings.isEmpty()) {
			throw new IllegalArgumentException("[ERROR] Invalid QueryStrings: must not be null or empty");
		}
		if ((queryStrings.size() != 1 && !queryStrings.get(0).startsWith("SELECT")) || (queryStrings.size() != 2 && queryStrings.get(0).startsWith("SELECT"))){
			throw new IllegalArgumentException("[ERROR] Invalid QueryStrings: must have 1 command for CREATE and DELETE operations, 2 commands for SELECT operations");
		}

		// First line for CREATE, DELETE, SELECT operations (collection operations)
		String firstLine = queryStrings.get(0).trim();
		if (firstLine.startsWith("CREATE")) {
			parseCreateOperation(firstLine);
		} else if (firstLine.startsWith("DELETE")) {
			parseDeleteOperation(firstLine);
		} else if (firstLine.startsWith("SELECT")) {
			parseSelectOperation(firstLine);
		} else {
			throw new IllegalArgumentException("[ERROR] Invalid collection operation: '" + firstLine.substring(0, firstLine.indexOf(" ")) + "' does not exist");
		}

		// Second line for INSERT, SEARCH, REMOVE operations (document operations | the row is present only if the first line is SELECT)
		if (this.collectionOperation == CollectionOperation.SELECT && queryStrings.size() > 1) {
			String secondLine = queryStrings.get(1).trim();
			if (secondLine.startsWith("INSERT")) {
				parseInsertOperation(secondLine);
			} else if (secondLine.startsWith("SEARCH")) {
				parseSearchOperation(secondLine);
			} else if (secondLine.startsWith("REMOVE")) {
				parseRemoveOperation(secondLine);
			} else {
				throw new IllegalArgumentException("[ERROR] Invalid document operation: '" + secondLine.substring(0, firstLine.indexOf(" ")) + "' does not exist");
			}
		}
	}

	private void parseCreateOperation(String line) {
		this.collectionOperation = CollectionOperation.CREATE;
		String[] parts = line.split(" ");
		this.collectionName = removeQuotes(parts[1]);
		this.collectionKeyField = removeQuotes(parts[3]);
	}

	private void parseDeleteOperation(String line) {
		this.collectionOperation = CollectionOperation.DELETE;
		String[] parts = line.split(" ");
		this.collectionName = removeQuotes(parts[1]);
	}

	private void parseSelectOperation(String line) {
		this.collectionOperation = CollectionOperation.SELECT;
		String[] parts = line.split(" ");
		this.collectionName = removeQuotes(parts[1]);
	}

	private void parseInsertOperation(String line) {
		this.documentOperation = DocumentOperation.INSERT;
		this.document = Json.createReader(new StringReader(line.substring("INSERT ".length()).trim())).readObject();
	}

	private void parseSearchOperation(String line) throws IllegalArgumentException {
		documentOperation = DocumentOperation.SEARCH;
		searchConditions = new ArrayList<>();
		String[] parts = line.split(" ");
		for (int i = 1; i < parts.length; i += 3) {
			String field = removeQuotes(parts[i]);
			String operand = parts[i + 1];
			String value = removeQuotes(parts[i + 2]);
			try {
				searchConditions.add(new SearchCondition(field, operand, value));
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("[ERROR] Invalid search condition: " + e.getMessage());
			}
		}
	}

	private void parseRemoveOperation(String line) {
		this.documentOperation = DocumentOperation.REMOVE;
		this.documentId = removeQuotes(line.substring("REMOVE ".length()).trim());
	}

	private String removeQuotes(String value) {
		if (value.startsWith("\"") && value.endsWith("\"")) {
			return value.substring(1, value.length() - 1);
		}
		return value;
	}

	public CollectionOperation getCollectionOperation() {
		return this.collectionOperation;
	}

	public String getCollectionName() {
		return this.collectionName;
	}

	public String getCollectionKeyField() {
		return this.collectionKeyField;
	}

	public DocumentOperation getDocumentOperation() {
		return this.documentOperation;
	}

	public JsonObject getDocument() {
		return this.document;
	}

	public List<SearchCondition> getSearchConditions() {
		return this.searchConditions;
	}

	public String getDocumentId() {
		return this.documentId;
	}

	public void execute(PrintWriter out) {
		switch (this.collectionOperation) {
			case CREATE:
				handleCreateOperation(out);
				break;
			case SELECT:
				handleSelectOperation(out);
				break;
			case DELETE:
				handleDeleteOperation(out);
				break;
			default:
				out.println("Unsupported operation: " + collectionOperation);
		}
	}

	private void handleCreateOperation(PrintWriter out) {
		if (this.collectionName != null && this.collectionKeyField != null) {
			String filePath = COLLECTION_FILES_PATH + this.collectionName + ".json";
			try {
				FileOperations.create(filePath, this.collectionKeyField);
				out.println("[SUCCESS] Created collection '" + this.collectionName + "' with key field '" + this.collectionKeyField + "'");
			} catch (IOException e) {
				out.println("[FAIL] Failed to create collection '" + this.collectionName + "': " + e.getMessage());
			}
		} else {
			out.println("[ERROR] Malformed collection operation (CREATE): collectionName or collectionKeyField is null");
		}
	}

	private void handleSelectOperation(PrintWriter out) {
		if (this.collectionName != null) {
			String filePath = COLLECTION_FILES_PATH + this.collectionName + ".json";
			switch (this.documentOperation) {
				case INSERT:
					if (this.document != null) {
						insertDocument(filePath, out);
					} else {
						out.println("[ERROR] Malformed document operation (INSERT): document is null");
					}
					break;
				case SEARCH:
					if (this.searchConditions != null && !this.searchConditions.isEmpty()) {
						searchDocuments(filePath, out);
					} else {
						out.println("[ERROR] Malformed document operation (SEARCH): searchConditions is null or empty");
					}
					break;
				case REMOVE:
					if (this.documentId != null) {
						removeDocument(filePath, out);
					} else {
						out.println("[ERROR] Malformed document operation (REMOVE): documentId is null");
					}
					break;
				default:
					out.println("[ERROR] Invalid document operation: '" + this.documentOperation + "'' does not exist");
			}
		} else {
			out.println("[ERROR] Malformed collection operation (SELECT): collectionName is null");
		}
	}

	private void handleDeleteOperation(PrintWriter out) {
		if (this.collectionName != null) {
			String filePath = COLLECTION_FILES_PATH + this.collectionName + ".json";
			try {
				FileOperations.delete(filePath);
				out.println("[SUCCESS] Deleted collection '" + this.collectionName + "'");
			} catch (IOException e) {
				out.println("[FAIL] Failed to delete collection '" + this.collectionName + "': " + e.getMessage());
			}
		} else {
			out.println("[ERROR] Malformed collection operation (DELETE): collectionName is null");
		}
	}

	private void insertDocument(String filePath, PrintWriter out) {
		// Read current JSON from file. If fails, return immediately
		JsonObject jsonFile;
		try {
			jsonFile = FileOperations.read(filePath);
		} catch (IOException e) {
			out.println("[FAIL] Failed to read collection '" + this.collectionName + "': " + e.getMessage());
			return;
		}

		// Append the new document to the JSON array 'data' and update the JSON object
		JsonArray jsonArray = jsonFile.getJsonArray("data");
		jsonArray.add(this.document);
		jsonFile.put("data", jsonArray);

		// Overwrite the JSON file with the updated JSON array
		try {
			FileOperations.write(filePath, jsonFile);
			out.println("[SUCCESS] Inserted document into '" + this.collectionName + "'");
		} catch (IOException e) {
			out.println("[FAIL] Failed to insert document into '" + this.collectionName + "': " + e.getMessage());
		}
	}

	private void searchDocuments(String filePath, PrintWriter out) {
		// Read current JSON array from file. If fails, return immediately
		JsonObject jsonFile;
		try {
			jsonFile = FileOperations.read(filePath);
		} catch (IOException e) {
			out.println("[FAIL] Failed to read collection '" + this.collectionName + "': " + e.getMessage());
			return;
		}

		// Iterate over each document in the JSON array and check if it satisfies all search conditions
		JsonArray jsonArray = jsonFile.getJsonArray("data");
		JsonArray result = Json.createArrayBuilder().build();
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject document = jsonArray.getJsonObject(i);
			boolean isMatch = true;
			for (SearchCondition condition : this.searchConditions) {
				String fieldValue = document.getString(condition.getField(), "");
				if (!condition.isSatisfiedBy(fieldValue)) {
					isMatch = false;
					break;
				}
			}
			// If all conditions are satisfied, add the document to the result array
			if (isMatch) {
				result.add(document);
			}
		}
		out.println("[SUCCESS] search result: " + result);
	}

	private void removeDocument(String filePath, PrintWriter out) {
		// Read current JSON array from file. If fails, return immediately
		JsonObject jsonFile;
		try {
			jsonFile = FileOperations.read(filePath);
		} catch (IOException e) {
			out.println("[FAIL] Failed to read collection '" + this.collectionName + "': " + e.getMessage());
			return;
		}

		// Iterate over each document in the JSON array and remove the document with the specified ID
		JsonArray jsonArray = jsonFile.getJsonArray("data");
		JsonArray result = Json.createArrayBuilder().build();
		boolean found = false;
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject document = jsonArray.getJsonObject(i);
			if (!document.getString(this.collectionKeyField, "").equals(this.documentId)) {
				result.add(document);
			} else {
				found = true;
			}
		}

		if(found) {
			jsonFile.put("data", result);
			try {
				FileOperations.write(filePath, jsonFile);
				out.println("[SUCCESS] Removed document from '" + this.collectionName + "'");
			} catch (IOException e) {
				out.println("[FAIL] Failed to remove document from '" + this.collectionName + "': " + e.getMessage());
			}
		} else {
			out.println("[FAIL] Document with '" + this.collectionKeyField + "'='" + this.documentId + "' not found in '" + this.collectionName + "'");
		}
	}

	@Override
	public String toString() {
		return "Query = {\n\tcollectionOperation=" + collectionOperation + ",\n\tcollectionName=" + collectionName
				+ ",\n\tcollectionKeyField=" + collectionKeyField + ",\n\tdocumentOperation=" + documentOperation
				+ ",\n\tdocument=" + document + ",\n\tsearchConditions=" + searchConditions + ",\n\tdocumentId="
				+ documentId + "\n}";
	}
}