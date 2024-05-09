package Model;

public class Links {
    private boolean previous;
    private String current;
    private String next;

    public boolean isPrevious() {
        return previous;
    }

    public void setPrevious(boolean previous) {
        this.previous = previous;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Links{" +
                "previous=" + previous +
                ", current='" + current + '\'' +
                ", next='" + next + '\'' +
                '}';
    }
}
