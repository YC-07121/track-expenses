package service;

import java.util.List;

import model.Item;

public interface ItemService {

    Boolean record(Item item);

    List<Item> find_by_id(int itemID);

    List<Item> find_by_type(String type);

    List<Item> find_by_recorderID(int recorderID);

    Boolean update(Item item);

    Boolean delete_by_id(int itemID);
}
