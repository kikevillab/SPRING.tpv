package wrappers;

import java.util.List;

import org.springframework.data.domain.Page;

public class UserPageWrapper extends PageWrapper {

    private List<UserWrapper> content;

    public UserPageWrapper() {

    }

    public UserPageWrapper(List<UserWrapper> content, Page<?> page) {
        super(page);
        this.content = content;
    }

    public List<UserWrapper> getContent() {
        return content;
    }

    public void setContent(List<UserWrapper> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "UserPageWrapper [content=" + content + "]";
    }

}
