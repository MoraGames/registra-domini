package it.unimib.sd2024.request;

public class RenewDomainByNameRequestBody {
	private Long userId;
	private int monthsDuration;

	public RenewDomainByNameRequestBody() {
		this(null, 0);
	}
	public RenewDomainByNameRequestBody(Long userId, int monthDuration) {
		this.userId = userId;
		this.monthsDuration = monthDuration;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUser(Long userId) {
		this.userId = userId;
	}

	public int getMonthsDuration() {
		return this.monthsDuration;
	}

	public void setMonthsDuration(int monthDuration) {
		this.monthsDuration = monthDuration;
	}

	@Override
	public String toString() {
		return "RenewDomainByNameRequestBody = {\n\tuserId=" + userId + ",\n\tmonthsDuration=" + monthsDuration + "\n}";
	}
}
