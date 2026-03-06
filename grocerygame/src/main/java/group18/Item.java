package group18;

public abstract class Item extends Entity_Stationary{
    public int value;
    Item_Class type;

    public int getValue() {
        return value;
    }

    public void setValue(int newValue) {
        value = newValue;
    }
}
