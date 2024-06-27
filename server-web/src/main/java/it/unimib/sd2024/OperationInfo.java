package it.unimib.sd2024;

import java.util.Date;

public class OperationInfo {
    public long ownerId;
    public String domainName;
    public Date acquisitionDate;
    public Date expirationDate;
    public String type;
    public float cost;

    public OperationInfo(User owner, Domain domain, Contract contract, String type, float cost) {
        this.ownerId = owner.getId();
        this.domainName = owner.getName();
        this.acquisitionDate = contract.getAcquisitionDate();
        this.expirationDate = contract.getExpirationDate();
        this.type = type;
        this.cost = cost;
    }
}
