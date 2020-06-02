package vn.byt.qlds.model.report.DCX.DCX14;

import vn.byt.qlds.model.report.DCX.DCX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DCX14 extends DCX {
    public Map<Integer, Item> items = new HashMap<>();

    public DCX14 addSub(Integer addressID, String addressName, DCX14Sub dcx14Sub) {
        if (items.get(addressID) == null) {
            Item item = new Item();
            item.name = addressName;
            item.addressId = addressID;
            items.put(addressID, item);
        }
        items.get(addressID).dcx14Subs.add(dcx14Sub);

        return this;
    }

    public static class Item {
        public String name;
        public Integer addressId;
        public List<DCX14Sub> dcx14Subs = new ArrayList<>();
    }
}
