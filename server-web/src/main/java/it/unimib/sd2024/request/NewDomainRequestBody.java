package it.unimib.sd2024.request;

import it.unimib.sd2024.models.DomainRequestAction;

public class NewDomainRequestBody {
	private String domainName;
    private DomainRequestAction requestAction;
    private Long userId;
    private int monthsDuration;

	public NewDomainRequestBody() {
		this(null, null, null, 0);
	}
    public NewDomainRequestBody(String name, Long userId) {
        this(name, DomainRequestAction.ACQUIRING, userId, 0);
    }
	public NewDomainRequestBody(String name, DomainRequestAction status, Long userId, int monthDuration) {
		this.domainName = name;
		this.requestAction = status;
        this.userId = userId;
        this.monthsDuration = monthDuration;
	}

	public String getDomainName() {
		return this.domainName;
	}

	public void setDomainName(String name) {
		this.domainName = name;
	}

	public DomainRequestAction getRequestAction() {
		return this.requestAction;
	}

	public void setRequestAction(DomainRequestAction status) {
		this.requestAction = status;
	}

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getMonthsDuration() {
        return this.monthsDuration;
    }

    public void setMonthsDuration(int monthDuration) {
        this.monthsDuration = monthDuration;
    }
}
