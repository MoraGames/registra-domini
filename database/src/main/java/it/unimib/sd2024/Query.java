package it.unimib.sd2024;

public class Query {
    private CollectionOperation collectionOperation; // [CREATE, SELECT, DELETE]
    private String collectionName;
    private DocumentOperation documentOperation; // [INSERT, SEARCH, REMOVE] // Used if collectionOperation is SELECT
    private JsonObject document; // Used if documentOperation is INSERT // Used if collectionOperation is SELECT
    private String searchConditions; // Used if documentOperation is SEARCH // Used if collectionOperation is SELECT
    private String doumentId; // Used if documentOperation is DELETE // Used if collectionOperation is SELECT
}