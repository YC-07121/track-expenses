package service.impl;

import java.util.List;

import dao.ItemDao;
import dao.impl.ItemDaoImpl;
import model.Item;
import service.ItemService;

public class ItemServiceImpl implements ItemService {
    ItemDao itemDao = new ItemDaoImpl();

    @Override
    public Boolean record(Item item) {
        if (item.getPrice() <= 0) {
            return false;
        }
        itemDao.add_item(item);
        return true;
    }

    @Override
    public List<Item> find_by_id(int itemID) {
        return itemDao.select_by_id(itemID);
    }

    @Override
    public List<Item> find_by_type(String type) {
        return itemDao.select_by_type(type);
    }

    @Override
    public List<Item> find_by_recorderID(int recorderID) {
        return itemDao.select_by_recorderID(recorderID);
    }

    @Override
    public Boolean update(Item item) {
        if (item.getPrice() <= 0) {
            return false;
        }
        itemDao.update(item);
        return true;
    }

    @Override
    public Boolean delete_by_id(int itemID) {
        if (itemDao.select_by_id(itemID) == null) {
            return false;
        }
        itemDao.delete_by_id(itemID);
        return true;
    }

}
