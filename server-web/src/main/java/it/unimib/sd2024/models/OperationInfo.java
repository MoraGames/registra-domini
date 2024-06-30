package it.unimib.sd2024.models;

public class OperationInfo {
	public Long ownerId;
	public String domainName;
	public OperationType type;
	public float cost;

	public OperationInfo(User owner, Domain domain, OperationType type, float cost) {
		this.ownerId = owner.getId();
		this.domainName = owner.getName();
		this.type = type;
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "OperationInfo = {\n\townerId=" + ownerId + ",\n\tdomainName=" + domainName + ",\n\ttype=" + type + ",\n\tcost=" + cost + "\n}";
	}
}
