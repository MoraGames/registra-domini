package it.unimib.sd2024.types;

public enum CollectionOperation {
	CREATE, //CREATE <collection-name> ON-KEY <data-field>
	SELECT, //SELECT <collection-name> (+query on 2nd line)
	DELETE; //DELETE <collection-name>

	public static boolean contains(String operation) {
		for (CollectionOperation op : CollectionOperation.values()) {
			if (op.name().equals(operation)) {
				return true;
			}
		}
		return false;
	}
}