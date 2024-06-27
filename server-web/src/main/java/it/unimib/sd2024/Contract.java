package it.unimib.sd2024;

import java.util.Date;

public class Contract {
    private Date acquisitionDate;
    private Date expirationDate;

    public Contract(Date acquisitionDate, Date expirationDate) {
        this.acquisitionDate = acquisitionDate;
        this.expirationDate = expirationDate;
    }

    public Date getAcquisitionDate() {
        return this.acquisitionDate;
    }

    public Date getExpirationDate() {
        return this.expirationDate;
    }
}
