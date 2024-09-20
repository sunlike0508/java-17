package abstractclass;

public class Test implements Abc{
    private Abc delegate;

    @Override
    public void a() {
        delegate.a();
    }


}

interface Next<T> {
    <T> int next(T t);
}

class ItemIncreaseNext<T> implements Next<T> {

    private Item<T> item;
    private int cursor = 0;

    ItemIncreaseNext(Item<T> item) {
        this.item = item;
    }

    @Override
    public <T> int next(T t) {
        return item.get(cursor);
    }
}

abstract class Item<T> {
    abstract <T> int get(int key);
}

class IntItem extends Item<Integer> implements Next<Item<Integer>> {

    private ItemIncreaseNext<Integer> delegator = new ItemIncreaseNext<>(this);

    @Override
    <T> int get(int key) {
        return 3;
    }


    @Override
    public <T> int next(T t) {
        return delegator.next(t);
    }
}
