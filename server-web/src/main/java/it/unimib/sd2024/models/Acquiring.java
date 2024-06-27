package it.unimib.sd2024.models;

import java.util.Date;

public class Acquiring {
    private User user;
	private Date startAquisitionDate;
    private Date finishAquisitionDate;

    public Acquiring(User user, Date startAquisitionDate) {
        this.user = user;
        this.startAquisitionDate = startAquisitionDate;
        this.finishAquisitionDate = null;
    }

    public User getUser() {
        return this.user;
    }

    public Date getStartAquisitionDate() {
        return this.startAquisitionDate;
    }

    public Date getFinishAquisitionDate() {
        return this.finishAquisitionDate;
    }

    public void setFinishAquisitionDate(Date finishAquisitionDate) {
        this.finishAquisitionDate = finishAquisitionDate;
    }
}