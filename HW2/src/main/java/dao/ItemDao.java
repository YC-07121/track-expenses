package dao;

import java.util.List;

import model.Item;

public interface ItemDao {

    void add_item(Item item);

    List<Item> select_by_id(int itemID);

    List<Item> select_by_type(String type);

    List<Item> select_by_recorderID(int recorderID);

    void update(Item item);

    void delete_by_id(int itemID);
}
