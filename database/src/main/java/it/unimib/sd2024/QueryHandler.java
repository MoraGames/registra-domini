package it.unimib.sd2024;

import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;

import it.unimib.sd2024.types.DocumentOperation;
import it.unimib.sd2024.types.SearchCondition;

public class QueryHandler {

    public static void execute(Query query, PrintWriter out) {
        switch (query.getCollectionOperation()) {
            case CREATE:
                handleCreateOperation(query, out);
                break;
            case SELECT:
                handleSelectOperation(query, out);
                break;
            case DELETE:
                handleDeleteOperation(query, out);
                break;
            default:
                out.println("Unsupported operation: " + query.getCollectionOperation());
        }
    }

    private static void handleCreateOperation(Query query, PrintWriter out) {
        String collectionName = query.getCollectionName();
        String collectionKeyField = query.getCollectionKeyField();

        if (collectionName != null && collectionKeyField != null) {
            String filePath = collectionName + ".json";
            if (!FileOperations.exist(filePath)) {
                JsonObject initialData = Json.createObjectBuilder()
                                            .add("keyField", collectionKeyField)
                                            .add("data", Json.createArrayBuilder().build())
                                            .build();
                FileOperations.write(filePath, initialData);
                out.println("Created collection '" + collectionName + "' with key field '" + collectionKeyField + "'");
            } else {
                out.println("Collection '" + collectionName + "' already exists");
            }
        } else {
            out.println("Invalid CREATE operation: Missing collectionName or collectionKeyField");
        }
    }

    private static void handleSelectOperation(Query query, PrintWriter out) {
        String collectionName = query.getCollectionName();
        DocumentOperation documentOperation = query.getDocumentOperation();

        if (collectionName != null) {
            String filePath = collectionName + ".json";
            switch (documentOperation) {
                case INSERT:
                    handleInsertOperation(query, filePath, out);
                    break;
                case SEARCH:
                    handleSearchOperation(query, filePath, out);
                    break;
                case REMOVE:
                    handleRemoveOperation(query, filePath, out);
                    break;
                default:
                    out.println("Unsupported document operation: " + documentOperation);
            }
        } else {
            out.println("Invalid SELECT operation: Missing collectionName");
        }
    }

    private static void handleDeleteOperation(Query query, PrintWriter out) {
        String collectionName = query.getCollectionName();

        if (collectionName != null) {
            String filePath = collectionName + ".json";
            if (FileOperations.exist(filePath)) {
                try {
                    FileOperations.delete(filePath);
                    out.println("Deleted collection '" + collectionName + "'");
                } catch (Exception e) {
                    out.println("Error deleting collection '" + collectionName + "': " + e.getMessage());
                }
            } else {
                out.println("Collection '" + collectionName + "' does not exist");
            }
        } else {
            out.println("Invalid DELETE operation: Missing collectionName");
        }
    }

    private static void handleInsertOperation(Query query, String filePath, PrintWriter out) {
        String document = query.getDocument();

        if (document != null) {
            JsonObject jsonDocument = Json.createReader(new StringReader(document)).readObject();
            JsonObject collectionData = FileOperations.read(filePath);

            if (collectionData != null) {
                List<JsonObject> dataList = JsonUtil.getJsonArray(collectionData, "data");

                dataList.add(jsonDocument);

                JsonObject updatedCollectionData = Json.createObjectBuilder(collectionData)
                                                    .remove("data")
                                                    .add("data", JsonUtil.toJsonArray(dataList))
                                                    .build();

                FileOperations.write(filePath, updatedCollectionData);
                out.println("Inserted document into collection '" + query.getCollectionName() + "'");
            } else {
                out.println("Collection '" + query.getCollectionName() + "' not found");
            }
        } else {
            out.println("Invalid INSERT operation: Missing document");
        }
    }

    private static void handleSearchOperation(Query query, String filePath, PrintWriter out) {
        List<SearchCondition> searchConditions = query.getSearchConditions();
        JsonObject collectionData = FileOperations.read(filePath);

        if (collectionData != null) {
            List<JsonObject> dataList = JsonUtil.getJsonArray(collectionData, "data");
            List<JsonObject> results = new ArrayList<>();

            for (JsonObject doc : dataList) {
                if (satisfiesConditions(doc, searchConditions)) {
                    results.add(doc);
                }
            }

            out.println("Search results:");
            for (JsonObject result : results) {
                out.println(result.toString());
            }
        } else {
            out.println("Collection '" + query.getCollectionName() + "' not found");
        }
    }

    private static boolean satisfiesConditions(JsonObject document, List<SearchCondition> conditions) {
        for (SearchCondition condition : conditions) {
            String field = condition.getField();
            String operand = condition.getOperand();
            String value = condition.getValue();

            // Implement your logic for checking each condition
            // Example: checking equality
            if (!document.getString(field, "").equals(value)) {
                return false;
            }
        }
        return true;
    }

    private static void handleRemoveOperation(Query query, String filePath, PrintWriter out) {
        String documentId = query.getDocumentId();
        JsonObject collectionData = FileOperations.read(filePath);

        if (collectionData != null) {
            List<JsonObject> dataList = JsonUtil.getJsonArray(collectionData, "data");

            Iterator<JsonObject> iterator = dataList.iterator();
            boolean removed = false;

            while (iterator.hasNext()) {
                JsonObject doc = iterator.next();
                if (doc.getString("id", "").equals(documentId)) {
                    iterator.remove();
                    removed = true;
                    break;
                }
            }

            if (removed) {
                JsonObject updatedCollectionData = Json.createObjectBuilder(collectionData)
                                                    .remove("data")
                                                    .add("data", JsonUtil.toJsonArray(dataList))
                                                    .build();

                FileOperations.write(filePath, updatedCollectionData);
                out.println("Removed document from collection '" + query.getCollectionName() + "'");
            } else {
                out.println("Document with id '" + documentId + "' not found in collection '" + query.getCollectionName() + "'");
            }
        } else {
            out.println("Collection '" + query.getCollectionName() + "' not found");
        }
    }
}