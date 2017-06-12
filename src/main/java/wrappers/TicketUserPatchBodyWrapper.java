package wrappers;

public class TicketUserPatchBodyWrapper {
    private Long userMobile;

    public TicketUserPatchBodyWrapper() {
    }

    public Long getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(Long userMobile) {
        this.userMobile = userMobile;
    }

    @Override
    public String toString() {
        return "TicketUserPatchWrapper [userMobile=" + userMobile + "]";
    }

}
