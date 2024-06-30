package it.unimib.sd2024;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import it.unimib.sd2024.types.CollectionOperation;
import it.unimib.sd2024.types.DocumentOperation;
import it.unimib.sd2024.types.SearchCondition;

public class Query {
    private CollectionOperation collectionOperation; // [CREATE, SELECT, DELETE]
    private String collectionName;
    private String collectionKeyField; // Used if collectionOperation is CREATE
    private DocumentOperation documentOperation; // [INSERT, SEARCH, REMOVE] // Used if collectionOperation is SELECT
    private String document; // Used if documentOperation is INSERT // Used if collectionOperation is SELECT
    private List<SearchCondition> searchConditions; // Used if documentOperation is SEARCH // Used if collectionOperation is SELECT
    private String documentId; // Used if documentOperation is DELETE // Used if collectionOperation is SELECT

    public Query(List<String> queryStrings) {
        parseQuery(queryStrings);
    }

    private void parseQuery(List<String> queryStrings) {
        if (queryStrings == null || queryStrings.isEmpty()) {
            throw new IllegalArgumentException("Query strings must not be null or empty");
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
            throw new IllegalArgumentException("Invalid query operation: " + firstLine);
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
                throw new IllegalArgumentException("Invalid secondary query operation: " + secondLine);
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
        this.document = line.substring("INSERT ".length()).trim();
    }

    private void parseSearchOperation(String line) {
        documentOperation = DocumentOperation.SEARCH;
        searchConditions = new ArrayList<>();
        String[] parts = line.split(" ");
        for (int i = 1; i < parts.length; i += 3) {
            String field = removeQuotes(parts[i]);
            String operand = parts[i + 1];
            String value = removeQuotes(parts[i + 2]);
            searchConditions.add(new SearchCondition(field, operand, value));
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

    public String getDocument() {
        return this.document;
    }

    public List<SearchCondition> getSearchConditions() {
        return this.searchConditions;
    }

    public String getDocumentId() {
        return this.documentId;
    }

    /*
    
    public void execute(PrintWriter out) {
        switch (collectionOperation) {
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
        // Implement CREATE operation
        if (collectionName != null && collectionKeyField != null) {
            // Check if collection already exists
            String filePath = collectionName + ".json";
            if (!FileOperations.exist(filePath)) {
                String initialJson = "{\"keyField\": \"" + collectionKeyField + "\", \"data\": []}";
                FileOperations.write(filePath, initialJson);
                out.println("Created collection '" + collectionName + "' with key field '" + collectionKeyField + "'");
            } else {
                out.println("Collection '" + collectionName + "' already exists");
            }
        } else {
            out.println("Invalid CREATE operation: Missing collectionName or collectionKeyField");
        }
    }

    private void handleSelectOperation(PrintWriter out) {
        // Implement SELECT operation
        if (collectionName != null) {
            String filePath = collectionName + ".json";
            switch (documentOperation) {
                case INSERT:
                    if (document != null) {
                        insertDocument(filePath, document, out);
                    } else {
                        out.println("Invalid INSERT operation: Missing document");
                    }
                    break;
                case SEARCH:
                    if (searchConditions != null && !searchConditions.isEmpty()) {
                        searchDocuments(filePath, searchConditions, out);
                    } else {
                        out.println("Invalid SEARCH operation: Missing search conditions");
                    }
                    break;
                case REMOVE:
                    if (documentId != null) {
                        removeDocument(filePath, documentId, out);
                    } else {
                        out.println("Invalid REMOVE operation: Missing documentId");
                    }
                    break;
                default:
                    out.println("Unsupported operation: " + documentOperation);
            }
        } else {
            out.println("Invalid SELECT operation: Missing collectionName");
        }
    }

    private void handleDeleteOperation(PrintWriter out) {
        // Implement DELETE operation
        if (collectionName != null) {
            String filePath = collectionName + ".json";
            if (FileOperations.exist(filePath)) {
                FileOperations.delete(filePath);
                out.println("Deleted collection '" + collectionName + "'");
            } else {
                out.println("Collection '" + collectionName + "' does not exist");
            }
        } else {
            out.println("Invalid DELETE operation: Missing collectionName");
        }
    }

    private void insertDocument(String filePath, String document, PrintWriter out) {
        // Read current JSON array from file
        String currentJson = FileOperations.read(filePath);
        if (currentJson != null) {
            // Append new document to JSON array
            String updatedJson = appendToJsonArray(currentJson, document);
            // Write updated JSON array back to file
            if (updatedJson != null) {
                FileOperations.write(filePath, updatedJson);
                out.println("Inserted document into '" + collectionName + "'");
            } else {
                out.println("Failed to insert document into '" + collectionName + "'");
            }
        } else {
            out.println("Failed to read collection '" + collectionName + "'");
        }
    }

    private String appendToJsonArray(String currentJson, String newDocument) {
        // Simulate appending to JSON array
        // In a real implementation, use JSON libraries to handle array operations
        // Here, we simply concatenate strings for demonstration purposes
        if (currentJson.endsWith("]")) {
            return currentJson.substring(0, currentJson.length() - 1) + "," + newDocument + "]";
        }
        return null;
    }

    private void searchDocuments(String filePath, List<SearchCondition> conditions, PrintWriter out) {
        // Read current JSON array from file
        String currentJson = FileOperations.read(filePath);
        if (currentJson != null) {
            // Simulate searching documents based on conditions
            // In a real implementation, parse JSON and filter based on conditions
            // Here, we simply print out the conditions for demonstration purposes
            out.println("Search conditions: " + conditions);
        } else {
            out.println("Failed to read collection '" + collectionName + "'");
        }
    }

    private void removeDocument(String filePath, String documentId, PrintWriter out) {
        // Read current JSON array from file
        String currentJson = FileOperations.read(filePath);
        if (currentJson != null) {
            // Simulate removing document with specified ID
            // In a real implementation, parse JSON and remove based on ID
            // Here, we simply print out the document ID for demonstration purposes
            out.println("Removed document from '" + collectionName + "' with ID: " + documentId);
        } else {
            out.println("Failed to read collection '" + collectionName + "'");
        }
    }

    */
}