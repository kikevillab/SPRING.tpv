package api.wrappers;

import java.util.List;

import org.springframework.data.domain.Page;

import wrappers.TicketReferenceCreatedWrapper;

public class TicketReferenceCreatedPageWrapper extends PageWrapper {

    private List<TicketReferenceCreatedWrapper> content;

    public TicketReferenceCreatedPageWrapper() {
    }

    public TicketReferenceCreatedPageWrapper(List<TicketReferenceCreatedWrapper> content, Page<?> page) {
        super(page);
        this.content = content;
    }

    public List<TicketReferenceCreatedWrapper> getContent() {
        return content;
    }

    public void setContent(List<TicketReferenceCreatedWrapper> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TicketReferenceCreatedPageWrapper [" + super.toString() + ", content=" + content + "]";
    }

}
