package group18;

public abstract class Item extends Entity_Stationary{
    public int value;
    public int count;
    public int limit;
    Item_Class type;

    public int getValue() {
        return value;
    }

    public void setValue(int newValue) {
        value = newValue;
    }

    public int getCount() {
        return count;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int newLimit) {
        limit = newLimit;
    }
}
