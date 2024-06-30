package it.unimib.sd2024.models;

public class DomainInfo {
	public String name;
	public DomainStatus status;
	
	public DomainInfo(String name, DomainStatus status) {
		this.name = name;
		this.status = status;
	}

	@Override
	public String toString() {
		return "DomainInfo = {\n\tname=" + name + ",\n\tstatus=" + status + "\n}";
	}
}
