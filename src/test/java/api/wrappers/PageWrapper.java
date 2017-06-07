package api.wrappers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public abstract class PageWrapper {

    private int number;

    private int numberOfElements;

    private int size;

    private Sort sort;

    private long totalElements;

    private int totalPages;

    private boolean first;

    private boolean last;

    public PageWrapper() {
    }

    public PageWrapper(Page<?> page) {
        this.number = page.getNumber();
        this.numberOfElements = page.getNumberOfElements();
        this.size = page.getSize();
        this.sort = page.getSort();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.first = page.isFirst();
        this.last = page.isLast();
    }

    public boolean isFirst() {
        return first;
    }

    public boolean isLast() {
        return last;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean hasContent() {
        return numberOfElements > 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (first ? 1231 : 1237);
        result = prime * result + (last ? 1231 : 1237);
        result = prime * result + number;
        result = prime * result + numberOfElements;
        result = prime * result + size;
        result = prime * result + ((sort == null) ? 0 : sort.hashCode());
        result = prime * result + (int) (totalElements ^ (totalElements >>> 32));
        result = prime * result + totalPages;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PageWrapper other = (PageWrapper) obj;
        if (first != other.first)
            return false;
        if (last != other.last)
            return false;
        if (number != other.number)
            return false;
        if (numberOfElements != other.numberOfElements)
            return false;
        if (size != other.size)
            return false;
        if (sort == null) {
            if (other.sort != null)
                return false;
        } else if (!sort.equals(other.sort))
            return false;
        if (totalElements != other.totalElements)
            return false;
        if (totalPages != other.totalPages)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "number=" + number + ", numberOfElements=" + numberOfElements + ", size=" + size + ", sort=" + sort + ", totalElements="
                + totalElements + ", totalPages=" + totalPages + ", first=" + first + ", last=" + last;
    }

}
